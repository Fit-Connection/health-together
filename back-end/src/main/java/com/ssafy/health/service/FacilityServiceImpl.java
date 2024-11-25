package com.ssafy.health.service;

import com.ssafy.health.dao.FacilityRepository;
import com.ssafy.health.domain.Facility;
import com.ssafy.health.dto.request.FacilityRequest;
import com.ssafy.health.dto.response.FacilityResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FacilityServiceImpl implements FacilityService {

    private final FacilityRepository facilityRepository;

    public FacilityServiceImpl(FacilityRepository facilityRepository) {
        this.facilityRepository = facilityRepository;
    }

    @Override
    public List<FacilityResponse> getAllFacilities() {
        return facilityRepository.getAllFacilities().stream()
                .map(facility -> new FacilityResponse(
                        facility.getFacilityId(),
                        facility.getName(),
                        facility.getLocation(),
                        facility.getType(),
                        facility.getContact(),
                        facility.getOperatingHours(),
                        facility.getAvailability(),
                        facility.getRatingScore(),
                        facility.getReviewCount(),
                        facility.getPopularity(),
                        facility.getCreatedAt(),
                        facility.getUpdatedAt()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public FacilityResponse getFacilityById(Long id) {
        Facility facility = facilityRepository.getFacilityById(id);
        return new FacilityResponse(
                facility.getFacilityId(),
                facility.getName(),
                facility.getLocation(),
                facility.getType(),
                facility.getContact(),
                facility.getOperatingHours(),
                facility.getAvailability(),
                facility.getRatingScore(),
                facility.getReviewCount(),
                facility.getPopularity(),
                facility.getCreatedAt(),
                facility.getUpdatedAt()
        );
    }

    @Override
    public void createFacility(FacilityRequest request) {
        Facility facility = Facility.builder()
                .name(request.name())
                .location(request.location())
                .type(request.type())
                .contact(request.contact())
                .operatingHours(request.operatingHours())
                .availability(request.availability())
                .build();
        facilityRepository.createFacility(facility);
    }

    @Override
    public void updateFacility(Long id, FacilityRequest request) {
        Facility facility = Facility.builder()
                .facilityId(id)
                .name(request.name())
                .location(request.location())
                .type(request.type())
                .contact(request.contact())
                .operatingHours(request.operatingHours())
                .availability(request.availability())
                .build();
        facilityRepository.updateFacility(facility);
    }

    @Override
    public void deleteFacility(Long id) {
        facilityRepository.deleteFacility(id);
    }
}
