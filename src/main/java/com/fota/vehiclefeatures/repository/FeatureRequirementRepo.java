package com.fota.vehiclefeatures.repository;

import com.fota.vehiclefeatures.entity.Feature;
import com.fota.vehiclefeatures.entity.FeatureRequirement;
import com.fota.vehiclefeatures.entity.Vehicle;
import com.fota.vehiclefeatures.entity.VinFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FeatureRequirementRepo {
    private static final Logger log = LoggerFactory.getLogger(FeatureRequirementRepo.class);
    @Autowired
    JdbcTemplate jdbcTemplate;
    public List<FeatureRequirement> getAll(){

        List<FeatureRequirement> featureRequirementList = jdbcTemplate.query(
                "SELECT FEATURE_CODE, SOFT_HARD_CODE, SOFT_HARD_FLAG,PRESENT_NOTPRESENT_FLAG FROM FEATURE_REQUIREMENT ORDER BY SOFT_HARD_FLAG, PRESENT_NOTPRESENT_FLAG",
                (rs, rowNum) -> new FeatureRequirement(rs.getString("FEATURE_CODE"), rs.getString("SOFT_HARD_CODE"), rs.getString("SOFT_HARD_FLAG"), rs.getString("PRESENT_NOTPRESENT_FLAG"))
        );
        return featureRequirementList;
    }

    public List<VinFeature> getFeatureRequirement(String vin, String feature) {
        List<VinFeature> vinFeatureList = jdbcTemplate.query(
"SELECT CASE WHEN count(*) > 0 THEN 'no' ELSE 'yes' END as AllExist " +
        "from (SELECT F.SOFT_HARD_CODE    " +
        "               FROM FEATURE_REQUIREMENT F    " +
        "               WHERE F.FEATURE_CODE ='"+ feature+"'   " +
        "                AND F.PRESENT_NOTPRESENT_FLAG='P' ) t1  " +
        "left outer join (SELECT V.SOFT_HARD_CODE    " +
        "               FROM FEATURE_REQUIREMENT F, VEHICLE_HARD_SOFT_CODES V  " +
        "               WHERE " +
        "               V.VIN = '"  + vin + "' " +
        "               AND " +
        "               F.SOFT_HARD_CODE = V.SOFT_HARD_CODE  " +
        "               AND " +
        "               V.SOFT_HARD_CODE NOT IN (SELECT FRN.SOFT_HARD_CODE FROM FEATURE_REQUIREMENT FRN WHERE  FRN.PRESENT_NOTPRESENT_FLAG='N' AND FRN.FEATURE_CODE ='"+ feature+"')  " +
        "               AND  " +
        "               F.FEATURE_CODE ='"+ feature+"')  t2 on t1.SOFT_HARD_CODE = t2.SOFT_HARD_CODE  " +
        "where t2.SOFT_HARD_CODE is null",
                (rs, rowNum) -> new VinFeature(vin, feature, rs.getString("AllExist"))
        );
        return vinFeatureList;
    }
    public List<Feature> getAllFeatures(){
        return jdbcTemplate.query("SELECT DISTINCT (FEATURE_CODE) FROM FEATURE_REQUIREMENT"
        ,(rs, rowNum) -> new Feature(rs.getString("FEATURE_CODE"))
        );
    }
    public Page<Feature> getAllFeatures(Pageable pageable){

        String rowCountSql = "SELECT count(DISTINCT (FEATURE_CODE)) FROM FEATURE_REQUIREMENT";

        int total = jdbcTemplate.queryForObject(
                rowCountSql,
                (rs, rowNum) -> rs.getInt(1)
        );

        List<Feature> vinList = jdbcTemplate.query(
                "SELECT DISTINCT (FEATURE_CODE) FROM FEATURE_REQUIREMENT " +
                        "LIMIT " + pageable.getPageSize() + " " +
                        "OFFSET " + pageable.getOffset()
                ,
                (rs, rowNum) -> new Feature(rs.getString("FEATURE_CODE"))
        );
        return new PageImpl<>(vinList, pageable, total);
    }
}
