package com.fota.vehiclefeatures.repository;

import com.fota.vehiclefeatures.entity.FeatureRequirement;
import com.fota.vehiclefeatures.entity.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Component
public class VehicleRepo {
    private static final Logger log = LoggerFactory.getLogger(VehicleRepo.class);
    @Autowired
    JdbcTemplate jdbcTemplate;
    public Page<Vehicle> getAll(Pageable pageable){

        String rowCountSql = "SELECT count(DISTINCT(VIN)) " +
                                "FROM VEHICLE_HARD_SOFT_CODES ";

        int total = jdbcTemplate.queryForObject(
                        rowCountSql,
                        (rs, rowNum) -> rs.getInt(1)
                );

        List<Vehicle> vinList = jdbcTemplate.query(
                "SELECT DISTINCT(VIN) " +
                        "FROM VEHICLE_HARD_SOFT_CODES " +
                        "LIMIT " + pageable.getPageSize() + " " +
                        "OFFSET " + pageable.getOffset()
                ,
                (rs, rowNum) -> new Vehicle(rs.getString("VIN"))
        );
        return new PageImpl<>(vinList, pageable, total);
    }
    public List<Vehicle> getAll(){

        List<Vehicle> vinList = jdbcTemplate.query(
                "SELECT DISTINCT(VIN) " +
                        "FROM VEHICLE_HARD_SOFT_CODES "
                ,
                (rs, rowNum) -> new Vehicle(rs.getString("VIN"))
        );
        return vinList;
    }

}
