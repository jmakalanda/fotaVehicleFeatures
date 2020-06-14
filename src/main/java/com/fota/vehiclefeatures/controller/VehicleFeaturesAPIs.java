package com.fota.vehiclefeatures.controller;

import com.fota.vehiclefeatures.entity.Feature;
import com.fota.vehiclefeatures.entity.FeatureRequirement;
import com.fota.vehiclefeatures.entity.Vehicle;
import com.fota.vehiclefeatures.entity.VinFeature;
import com.fota.vehiclefeatures.service.VehicleFeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class VehicleFeaturesAPIs {

    @Autowired
    VehicleFeatureService vehicleFeatureService;

    //gives all the features that can be installed for the corresponding vin
    @GetMapping("/fota/vehicles/{vin}/installable")
    public String getInstallableFeaturesForVehicle(@PathVariable(name = "vin") String vin,Model model) {
        model.addAttribute( "vinFeatures", vehicleFeatureService.getAllInstallableFeatures(vin));
        return "VINFeatureCompatibleIncompatible";
    }
    //gives all the features that cannot be installed for the corresponding vin
    @GetMapping("/fota/vehicles/{vin}/incompatible")
    public String getIncompatibleFeaturesForVehicle(@PathVariable(name = "vin") String vin,Model model) {
        model.addAttribute( "vinFeatures", vehicleFeatureService.getAllIncompatibleFeatures(vin));
        return "VINFeatureCompatibleIncompatible";
    }
    //gives all features that can/cannot be installed for the corresponding vin
    @GetMapping("/fota/vehicles/{vin}")
    public String getAllFeaturesForVehicle(@PathVariable(name = "vin") String vin,Model model) {
        model.addAttribute( "vinFeatures", vehicleFeatureService.getAllInstallableAndIncompatibleFeatures(vin));
        return "VINFeatureCompatibleIncompatible";
    }
    //returns a list of all vehicles
    @GetMapping("/fota/vehicles")
    public String getVehicles(Model model) {
        model.addAttribute( "vehicles", vehicleFeatureService.getAllVehicleCodes());
        return "AllVehicles";
    }

    //gives all the vins that can install the corresponding feature
    @GetMapping("/fota/features/{feature}/installable")
    public String getVehiclesForInstallableFeature(@PathVariable(name = "feature") String feature, Model model) {
        model.addAttribute( "vinFeatures", vehicleFeatureService.getAllVINsWithCompatibleFeatures(feature));
        return "FeatureVINCompatibleIncompatible";
    }
    //gives all the vins that cannot install the corresponding feature
    @GetMapping("/fota/features/{feature}/incompatible")
    public String getVehicleIncompatibleFeatures(@PathVariable(name = "feature") String feature, Model model) {
        model.addAttribute( "vinFeatures", vehicleFeatureService.getAllVINsWithIncompatibleFeatures(feature));
        return "FeatureVINCompatibleIncompatible";
    }
    //gives all vins that can/cannot install the corresponding feature
    @GetMapping("/fota/features/{feature}")
    public String getVehicleFeatures(@PathVariable(name = "feature") String feature, Model model) {
        model.addAttribute( "vinFeatures", vehicleFeatureService.getAllVINsWithCompatibleAndIncompatibleFeatures(feature));
        return "FeatureVINCompatibleIncompatible";
    }
    //returns a list of all features.
    @GetMapping("/fota/features")
    public String  getFeatures(Model model) {
        model.addAttribute( "features", vehicleFeatureService.getAllFeatureRequirements());
        return "AllFeatures";
    }

}
