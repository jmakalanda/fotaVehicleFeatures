package com.fota.vehiclefeatures.service;

import com.fota.vehiclefeatures.entity.Feature;
import com.fota.vehiclefeatures.entity.Vehicle;
import com.fota.vehiclefeatures.entity.VinFeature;
import com.fota.vehiclefeatures.repository.FeatureRequirementRepo;
import com.fota.vehiclefeatures.repository.VehicleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public Page<Feature> getAllFeatureRequirements(Pageable pageable){
        //return featureRequirementRepo.getAllFeatures();
        return featureRequirementRepo.getAllFeatures(pageable);
    }

    public Page<Vehicle> getAllVehicleCodes(Pageable pagable){
        return vehicleRepo.getAll(pagable);
    }


    public List<VinFeature> getAllInstallableFeatures(String vin){
        List<Feature> featureList = featureRequirementRepo.getAllFeatures();
        List<VinFeature> vinFeatureList = new ArrayList<>();
        for(Feature feature: featureList){
            vinFeatureList = Stream.concat(vinFeatureList.stream() ,featureRequirementRepo.getFeatureRequirement(vin,feature.getFeatureCode()).stream().filter(vinFeature -> vinFeature.getIsCompatible().equals("yes") )).collect(Collectors.toList());
        }

        return vinFeatureList;
    }

    public List<VinFeature> getAllIncompatibleFeatures(String vin){
        List<Feature> featureList = featureRequirementRepo.getAllFeatures();
        List<VinFeature> vinFeatureList = new ArrayList<>();

        for(Feature feature: featureList){
            vinFeatureList = Stream.concat(vinFeatureList.stream() ,featureRequirementRepo.getFeatureRequirement(vin,feature.getFeatureCode()).stream().filter(vinFeature -> vinFeature.getIsCompatible().equals("no") )).collect(Collectors.toList());
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
            compatibleIncompatibleVINList = Stream.concat(compatibleIncompatibleVINList.stream(), featureRequirementRepo.getFeatureRequirement(vin.getVin(), feature).stream().filter(vinFeature -> vinFeature.getIsCompatible().equals("yes"))).collect(Collectors.toList());
        }


        return compatibleIncompatibleVINList;
    }
    public List<VinFeature> getAllVINsWithIncompatibleFeatures(String feature){
        List<Vehicle> vehicleList = vehicleRepo.getAll();
        List<VinFeature> compatibleIncompatibleVINList = new ArrayList<>();

        for(Vehicle vin: vehicleList) {
            compatibleIncompatibleVINList = Stream.concat(compatibleIncompatibleVINList.stream(), featureRequirementRepo.getFeatureRequirement(vin.getVin(), feature).stream().filter(vinFeature -> vinFeature.getIsCompatible().equals("no"))).collect(Collectors.toList());
        }


        return compatibleIncompatibleVINList;
    }
}
