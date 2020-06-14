package com.fota.vehiclefeatures.service;

import com.fota.vehiclefeatures.entity.Feature;
import com.fota.vehiclefeatures.entity.Vehicle;
import com.fota.vehiclefeatures.entity.VinFeature;
import com.fota.vehiclefeatures.repository.FeatureRequirementRepo;
import com.fota.vehiclefeatures.repository.VehicleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class VehicleFeatureService {

    @Autowired
    FeatureRequirementRepo featureRequirementRepo;
    @Autowired
    VehicleRepo vehicleRepo;
    public List<Feature> getAllFeatureRequirements(){
        return featureRequirementRepo.getAllFeatures();
    }

    public List<Vehicle> getAllVehicleCodes(){
        return vehicleRepo.getAll();
    }


    public List<VinFeature> getAllInstallableFeatures(String vin){
        List<Feature> featureList = featureRequirementRepo.getAllFeatures();
        List<VinFeature> vinFeatureList = new ArrayList<>();

        for(Feature feature: featureList){
            vinFeatureList = Stream.concat(vinFeatureList.stream() ,featureRequirementRepo.getFeatureRequirement(vin,feature.getFeatureCode()).stream().filter(vinFeature -> vinFeature.getIsCompatible().equals("IS") )).collect(Collectors.toList());
        }

        return vinFeatureList;
    }

    public List<VinFeature> getAllIncompatibleFeatures(String vin){
        List<Feature> featureList = featureRequirementRepo.getAllFeatures();
        List<VinFeature> vinFeatureList = new ArrayList<>();

        for(Feature feature: featureList){
            vinFeatureList = Stream.concat(vinFeatureList.stream() ,featureRequirementRepo.getFeatureRequirement(vin,feature.getFeatureCode()).stream().filter(vinFeature -> vinFeature.getIsCompatible().equals("NOT") )).collect(Collectors.toList());
        }

        return vinFeatureList;
    }

    public List<VinFeature> getAllInstallableAndIncompatibleFeatures(String vin){
        List<Feature> featureList = featureRequirementRepo.getAllFeatures();
        List<VinFeature> vinFeatureList = new ArrayList<>();

        for(Feature feature: featureList){
            vinFeatureList = Stream.concat(vinFeatureList.stream() ,featureRequirementRepo.getFeatureRequirement(vin,feature.getFeatureCode()).stream()).collect(Collectors.toList());
        }

        return vinFeatureList;
    }

    public List<VinFeature> getAllVINsWithCompatibleAndIncompatibleFeatures(String feature){
        List<Vehicle> vehicleList = vehicleRepo.getAll();
        List<VinFeature> compatibleIncompatibleVINList = new ArrayList<>();

            for(Vehicle vin: vehicleList) {
                compatibleIncompatibleVINList = Stream.concat(compatibleIncompatibleVINList.stream(), featureRequirementRepo.getFeatureRequirement(vin.getVin(), feature).stream()).collect(Collectors.toList());
            }


        return compatibleIncompatibleVINList;
    }

    public List<VinFeature> getAllVINsWithCompatibleFeatures(String feature){
        List<Vehicle> vehicleList = vehicleRepo.getAll();
        List<VinFeature> compatibleIncompatibleVINList = new ArrayList<>();

        for(Vehicle vin: vehicleList) {
            compatibleIncompatibleVINList = Stream.concat(compatibleIncompatibleVINList.stream(), featureRequirementRepo.getFeatureRequirement(vin.getVin(), feature).stream().filter(vinFeature -> vinFeature.getIsCompatible().equals("IS"))).collect(Collectors.toList());
        }


        return compatibleIncompatibleVINList;
    }
    public List<VinFeature> getAllVINsWithIncompatibleFeatures(String feature){
        List<Vehicle> vehicleList = vehicleRepo.getAll();
        List<VinFeature> compatibleIncompatibleVINList = new ArrayList<>();

        for(Vehicle vin: vehicleList) {
            compatibleIncompatibleVINList = Stream.concat(compatibleIncompatibleVINList.stream(), featureRequirementRepo.getFeatureRequirement(vin.getVin(), feature).stream().filter(vinFeature -> vinFeature.getIsCompatible().equals("NOT"))).collect(Collectors.toList());
        }


        return compatibleIncompatibleVINList;
    }
}
