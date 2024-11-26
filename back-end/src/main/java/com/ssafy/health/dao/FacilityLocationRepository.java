package com.ssafy.health.dao;

import com.ssafy.health.domain.FacilityLocation;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FacilityLocationRepository {
    FacilityLocation getFacilityLocationById(Long id);
    void createFacilityLocation(FacilityLocation facilityLocation);
    void updateFacilityLocation(FacilityLocation facilityLocation);
    void deleteFacilityLocation(Long id);
}
