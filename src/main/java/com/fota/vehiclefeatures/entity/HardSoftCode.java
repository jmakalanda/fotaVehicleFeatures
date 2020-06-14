package com.fota.vehiclefeatures.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class HardSoftCode {
    @Id
    private String code;
    private boolean isSoftCode;
}
