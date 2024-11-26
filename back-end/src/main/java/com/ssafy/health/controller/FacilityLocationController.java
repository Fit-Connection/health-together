package com.ssafy.health.controller;

import com.ssafy.health.dto.request.FacilityLocationRequest;
import com.ssafy.health.dto.response.FacilityLocationResponse;
import com.ssafy.health.service.FacilityLocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/facility-locations")
public class FacilityLocationController {

    private final FacilityLocationService service;

    public FacilityLocationController(FacilityLocationService service) {
        this.service = service;
    }

    @GetMapping("/{facilityId}")
    public ResponseEntity<FacilityLocationResponse> getFacilityLocationById(@PathVariable Long facilityId) {
        return ResponseEntity.ok(service.getFacilityLocationById(facilityId));
    }

    @PostMapping
    public ResponseEntity<Void> createFacilityLocation(@RequestBody FacilityLocationRequest request) {
        service.createFacilityLocation(request);
        return ResponseEntity.status(201).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateFacilityLocation(@RequestBody FacilityLocationRequest request) {
        service.updateFacilityLocation(request);
        return ResponseEntity.noContent().build();
    }
}
