package com.ssafy.health.dao;

import com.ssafy.health.domain.Facility;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FacilityRepository {
    List<Facility> getAllFacilities();
    Facility getFacilityById(Long id);
    void createFacility(Facility facility);
    void updateFacility(Facility facility);
    void deleteFacility(Long id);
}
