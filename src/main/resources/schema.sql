CREATE TABLE VEHICLE_HARD_SOFT_CODES (
                                           VIN varchar(50),
                                           SOFT_HARD_CODE varchar(50),
                                           SOFT_HARD_FLAG varchar(10),
                                           PRIMARY KEY (VIN, SOFT_HARD_CODE)
  );
CREATE TABLE FEATURE_REQUIREMENT (
                                         FEATURE_CODE varchar(10),
                                         SOFT_HARD_CODE varchar(50),
                                         SOFT_HARD_FLAG varchar(10),
                                         PRESENT_NOTPRESENT_FLAG varchar(10),
                                         PRIMARY KEY (FEATURE_CODE, SOFT_HARD_CODE)
);
