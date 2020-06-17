package com.fota.vehiclefeatures.controller;

import com.fota.vehiclefeatures.entity.Feature;
import com.fota.vehiclefeatures.entity.FeatureRequirement;
import com.fota.vehiclefeatures.entity.Vehicle;
import com.fota.vehiclefeatures.entity.VinFeature;
import com.fota.vehiclefeatures.service.VehicleFeatureService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.awt.*;
import java.util.List;

@Controller
public class VehicleFeaturesAPIs {
    VehicleFeatureService vehicleFeatureService;

    @Autowired
    public VehicleFeaturesAPIs(VehicleFeatureService vehicleFeatureService){
        this.vehicleFeatureService = vehicleFeatureService;
    }


    //gives all the features that can be installed for the corresponding vin
    @GetMapping("/fota/vehicles/{vin}/installable")
    @ApiOperation(
            value = "Find installable features by VIN",
            notes = "gives all the features that can be installed for the corresponding vin",
            tags = {"fota"},
            produces = "application/json"

    )
    @ApiParam(name = "vin", value = "Vehicle identification number", required = true, type = "String", format = "uuid")
    @ApiResponses(value = {@ApiResponse(code = 200,message = "successful operation") , @ApiResponse(code = 404, message = "Not found")})
    @ResponseBody
    public List<VinFeature> getInstallable(@PathVariable(name = "vin") String vin) {
         return vehicleFeatureService.getAllInstallableFeatures(vin);
    }
    //gives all the features that cannot be installed for the corresponding vin
    @GetMapping("/fota/vehicles/{vin}/incompatible")
    @ApiOperation(
            value = "Find incompatible features by VIN",
            notes = "gives all the features that cannot be installed for the corresponding vin",
            tags = {"fota"},
            produces = "application/json"

    )
    @ApiParam(name = "vin", value = "Vehicle identification number", required = true, type = "String", format = "uuid")
    @ApiResponses(value = {@ApiResponse(code = 200,message = "successful operation") , @ApiResponse(code = 404, message = "Not found")})
    @ResponseBody
    public List<VinFeature> getIncompatible(@PathVariable(name = "vin") String vin) {
        return vehicleFeatureService.getAllIncompatibleFeatures(vin);
    }
    //gives all features that can/cannot be installed for the corresponding vin
    @GetMapping("/fota/vehicles/{vin}")
    @ApiOperation(
            value = "Find all features by VIN",
            notes = "gives all features that can/cannot be installed for the corresponding vin",
            tags = {"fota"},
            produces = "application/json"

    )
    @ApiParam(name = "vin", value = "Vehicle identification number", required = true, type = "String", format = "uuid")
    @ApiResponses(value = {@ApiResponse(code = 200,message = "successful operation") , @ApiResponse(code = 404, message = "Not found")})
    @ResponseBody
    public List<VinFeature> getFeatures(@PathVariable(name = "vin") String vin) {
        return vehicleFeatureService.getAllInstallableAndIncompatibleFeatures(vin);
        //return "VINFeatureCompatibleIncompatible";
    }
    //returns a list of all vehicles
    @GetMapping("/fota/vehicles")
    @ApiOperation(
            value = "Find all vehicles",
            notes = "returns a list of all vehicles",
            tags = {"fota"},
            produces = "application/json"

    )
    @ApiResponses(value = {@ApiResponse(code = 200,message = "successful operation") , @ApiResponse(code = 404, message = "Not found")})
    @ResponseBody
    public Page<Vehicle> getVehicles(Pageable pageable) {
        return vehicleFeatureService.getAllVehicleCodes(pageable);
        //return "AllVehicles";
    }

    //gives all the vins that can install the corresponding feature
    @GetMapping("/fota/features/{feature}/installable")
    @ApiOperation(
            value = "Find installable VIN's by feature code",
            notes = "gives all the vins that can install the corresponding feature",
            tags = {"fota"},
            produces = "application/json"

    )
    @ApiParam(name = "feature", value = "Correspondent feature code", required = true, type = "string", format = "string")
    @ApiResponses(value = {@ApiResponse(code = 200,message = "successful operation") , @ApiResponse(code = 404, message = "Not found")})
    @ResponseBody
    public List<VinFeature> getVinInstallable(@PathVariable(name = "feature") String feature) {
        return vehicleFeatureService.getAllVINsWithCompatibleFeatures(feature);
        //return "FeatureVINCompatibleIncompatible";
    }
    //gives all the vins that cannot install the corresponding feature
    @GetMapping("/fota/features/{feature}/incompatible")
    @ApiOperation(
            value = "Find incompatible VIN's by feature code",
            notes = "gives all the vins that cannot install the corresponding feature",
            tags = {"fota"},
            produces = "application/json"

    )
    @ApiParam(name = "feature", value = "Correspondent feature code", required = true, type = "string", format = "string")
    @ApiResponses(value = {@ApiResponse(code = 200,message = "successful operation") , @ApiResponse(code = 404, message = "Not found")})
    @ResponseBody
    public List<VinFeature> getVinIncompatible(@PathVariable(name = "feature") String feature) {
       return vehicleFeatureService.getAllVINsWithIncompatibleFeatures(feature);
    }
    //gives all vins that can/cannot install the corresponding feature
    @GetMapping("/fota/features/{feature}")
    @ApiOperation(
            value = "Find all VIN's by feature code",
            notes = "gives all the vins that can install the corresponding feature",
            tags = {"fota"},
            produces = "application/json"

    )
    @ApiParam(name = "feature", value = "Correspondent feature code", required = true, type = "string", format = "string")
    @ApiResponses(value = {@ApiResponse(code = 200,message = "successful operation") , @ApiResponse(code = 404, message = "Not found")})
    @ResponseBody
    public List<VinFeature> getAllVin(@PathVariable(name = "feature") String feature) {
        return vehicleFeatureService.getAllVINsWithCompatibleAndIncompatibleFeatures(feature);
    }
    //returns a list of all features.
    @GetMapping("/fota/features")
    @ApiOperation(
            value = "Find all features",
            notes = "returns a list of all feature codes",
            tags = {"fota"},
            produces = "application/json"

    )
    @ApiResponses(value = {@ApiResponse(code = 200,message = "successful operation") , @ApiResponse(code = 404, message = "Not found")})
    @ResponseBody
    public Page<Feature>  getFeatures(Pageable pageable) {
        return  vehicleFeatureService.getAllFeatureRequirements(pageable);
    }

}
