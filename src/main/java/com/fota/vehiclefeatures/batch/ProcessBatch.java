package com.fota.vehiclefeatures.batch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

        JdbcTemplate jdbcTemplate;

        @Value("${fota.batchfilepath}")
        String batchFilePath;

        @Value("${fota.processedfilepath}")
        String batchProcessedFilePath;

        @Autowired
        public ProcessBatch(JdbcTemplate jdbcTemplate){
            this.jdbcTemplate = jdbcTemplate;
        }

        @Scheduled(fixedRate = 10000)
        public void processBatchFiles() {
            log.info("Polling at {}", dateFormat.format(new Date()));
            listAllFiles(batchFilePath);


        }

    // Uses Files.walk method
    public void listAllFiles(String path){
        try(Stream<Path> paths = Files.walk(Paths.get(path),1)) {
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    try {
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
                            log.info("Soft Code file " + filePath.getFileName().toString() +" completed!");
                            moveFileToProcessed(filePath);
                        }else if (isValidHardCodeFileName(filePath)){
                            List<String> lines = readContent(filePath);

                            for(String line: lines){
                                String []strings = line.split(",");
                                try {
                                    jdbcTemplate.execute("INSERT INTO VEHICLE_HARD_SOFT_CODES (VIN,SOFT_HARD_CODE, SOFT_HARD_FLAG) VALUES ('" + strings[0] + "','" + strings[1] + "','H')");
                                }catch(DuplicateKeyException e){
                                }
                            }
                            log.info("Hard Code file " + filePath.getFileName().toString() +" completed!");
                            //move file
                            moveFileToProcessed(filePath);
                        }else{
                            log.error("File name incorrect : " + filePath.getFileName());
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

    public void moveFileToProcessed(Path sourceFilePath) {

        Path targetFilePath = Paths.get(batchProcessedFilePath + sourceFilePath.getFileName().toString());

        try {
            Files.move(sourceFilePath, targetFilePath);
        } catch (FileAlreadyExistsException ex) {
            log.error("Target file already exists");
        } catch (IOException ex) {
            log.error("I/O error: %s%n", ex);
        }
    }


    public List<String> readContent(Path filePath) throws IOException{
        log.info("read file " + filePath);
        List<String> lines = Files.readAllLines(filePath);
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
