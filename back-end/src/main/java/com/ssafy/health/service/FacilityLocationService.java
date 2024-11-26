package com.ssafy.health.service;

import com.ssafy.health.dto.request.FacilityLocationRequest;
import com.ssafy.health.dto.response.FacilityLocationResponse;

public interface FacilityLocationService {
    FacilityLocationResponse getFacilityLocationById(Long id);
    void createFacilityLocation(FacilityLocationRequest request);
    void updateFacilityLocation(FacilityLocationRequest request);
}
