package com.fota.vehiclefeatures.batch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.dao.DuplicateKeyException;
@Component
public class ProcessBatch {

        private static final Logger log = LoggerFactory.getLogger(ProcessBatch.class);

        private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

        @Autowired
        JdbcTemplate jdbcTemplate;

        @Scheduled(fixedRate = 10000)
        public void processBatchFiles() {
            log.info("The time is now {}", dateFormat.format(new Date()));
//            File folder = new File("C:\\fota_batch");
//            System.out.println("reading files before Java8 - Using listFiles() method");
//            listAllFiles(folder);
//            System.out.println("-------------------------------------------------");
            System.out.println("reading files Java8 - Using Files.walk() method");
            listAllFiles("C:\\fota_batch");

        }
    // Uses listFiles before java8 method
    public void listAllFiles(File folder){
        System.out.println("In listAllfiles(File) method");
        File[] fileNames = folder.listFiles();
        for(File file : fileNames){
            // if directory call the same method again
            if(file.isDirectory()){
                listAllFiles(file);
            }else{
                try {
                    readContent(file);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }
    }
    // Uses Files.walk method
    public void listAllFiles(String path){
        System.out.println("In listAllfiles(String path) method");


        try(Stream<Path> paths = Files.walk(Paths.get(path),1)) {
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    try {
                        System.out.println("========= File name is ======== " + filePath.getFileName());
                        if (isValidSoftCodeFileName(filePath)){

                            List<String> lines = readContent(filePath);

                            for(String line: lines){
                                String []strings = line.split(",");
                                try {
                                    jdbcTemplate.execute("INSERT INTO VEHICLE_HARD_SOFT_CODES (VIN,SOFT_HARD_CODE, SOFT_HARD_FLAG) VALUES ('" + strings[0] + "','" + strings[1] + "','S')");
                                }catch(DuplicateKeyException e){

                                }
                            }
                            //move file
                            System.out.println("Soft Code file " + filePath.getFileName().toString() +" completed!");
                            moveFileToProcessed(filePath);
                        }else if (isValidHardCodeFileName(filePath)){
                            List<String> lines = readContent(filePath);

                            for(String line: lines){
                                String []strings = line.split(",");
                                try {
                                    jdbcTemplate.execute("INSERT INTO VEHICLE_HARD_SOFT_CODES (VIN,SOFT_HARD_CODE, SOFT_HARD_FLAG) VALUES ('" + strings[0] + "','" + strings[1] + "','H')");
                                }catch(DuplicateKeyException e){
                                    //System.out.println("inserted row already exist H ==========");
                                }
                            }
                            //move file
                            System.out.println("Hard Code file " + filePath.getFileName().toString() +" completed!");
                            moveFileToProcessed(filePath);
                        }else{
                            System.out.println("File name incorrect : " + filePath.getFileName());
                        }

                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void moveFileToProcessed(Path sourceFilePath) {

       // Path  = Paths.get(path.toString());
        Path targetFilePath = Paths.get("C:\\fota_batch\\processed\\"+ sourceFilePath.getFileName().toString());

        System.out.println("++++++++++++ The file will move from "+ sourceFilePath.toString() + " to " + sourceFilePath.toString());

        try {
            Files.move(sourceFilePath, targetFilePath);
        } catch (FileAlreadyExistsException ex) {
            System.out.println("Target file already exists");
        } catch (IOException ex) {
            System.out.format("I/O error: %s%n", ex);
        }
    }

    public void readContent(File file) throws IOException{
        System.out.println("read file " + file.getCanonicalPath() );
        try(BufferedReader br  = new BufferedReader(new FileReader(file))){
            String strLine;
            // Read lines from the file, returns null when end of stream
            // is reached
            while((strLine = br.readLine()) != null){
                System.out.println("Line is - " + strLine);
            }
        }
    }

    public List<String> readContent(Path filePath) throws IOException{
        System.out.println("read file " + filePath);
        List<String> lines = Files.readAllLines(filePath);
//        for(String line: fileList)
//            System.out.println("" + line);
        return lines;

    }
    public boolean isValidSoftCodeFileName(Path filePath){
        if (filePath.getFileName().toString().startsWith("soft_")){
            return true;
        }
        return false;
    }
    public boolean isValidHardCodeFileName(Path filePath){
        if (filePath.getFileName().toString().startsWith("hard_")){
            return true;
        }
        return false;
    }
}
