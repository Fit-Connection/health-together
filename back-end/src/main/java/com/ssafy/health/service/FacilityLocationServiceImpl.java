package com.ssafy.health.service;

import com.ssafy.health.dao.FacilityLocationRepository;
import com.ssafy.health.domain.FacilityLocation;
import com.ssafy.health.dto.request.FacilityLocationRequest;
import com.ssafy.health.dto.response.FacilityLocationResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FacilityLocationServiceImpl implements FacilityLocationService {

    private final FacilityLocationRepository repository;

    public FacilityLocationServiceImpl(FacilityLocationRepository repository) {
        this.repository = repository;
    }

    @Override
    public FacilityLocationResponse getFacilityLocationById(Long id) {
        FacilityLocation location = repository.getFacilityLocationById(id);
        return new FacilityLocationResponse(
                location.getFacilityId(),
                location.getLatitude(),
                location.getLongitude(),
                location.getStatus()
        );
    }

    @Override
    public void createFacilityLocation(FacilityLocationRequest request) {
        FacilityLocation location = FacilityLocation.builder()
                .facilityId(request.facilityId())
                .latitude(request.latitude())
                .longitude(request.longitude())
                .status(request.status())
                .build();
        repository.createFacilityLocation(location);
    }

    @Override
    public void updateFacilityLocation(FacilityLocationRequest request) {
        FacilityLocation location = FacilityLocation.builder()
                .facilityId(request.facilityId())
                .latitude(request.latitude())
                .longitude(request.longitude())
                .status(request.status())
                .build();
        repository.updateFacilityLocation(location);
    }
}
