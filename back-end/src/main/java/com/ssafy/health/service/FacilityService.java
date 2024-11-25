package com.ssafy.health.service;

import com.ssafy.health.dto.request.FacilityRequest;
import com.ssafy.health.dto.response.FacilityResponse;

import java.util.List;

public interface FacilityService {
    List<FacilityResponse> getAllFacilities();
    FacilityResponse getFacilityById(Long id);
    void createFacility(FacilityRequest request);
    void updateFacility(Long id, FacilityRequest request);
    void deleteFacility(Long id);
}
