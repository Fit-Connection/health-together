package com.ssafy.health.service;

import com.ssafy.health.dao.FacilityLocationRepository;
import com.ssafy.health.dao.FacilityRepository;
import com.ssafy.health.dao.TeamMemberRepository;
import com.ssafy.health.dao.TeamRepository;
import com.ssafy.health.domain.Facility;
import com.ssafy.health.domain.FacilityLocation;
import com.ssafy.health.domain.Team;
import com.ssafy.health.domain.TeamMember;
import com.ssafy.health.dto.request.TeamRequest;
import com.ssafy.health.dto.response.TeamResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final FacilityRepository facilityRepository;
    private final FacilityLocationRepository facilityLocationRepository;
    private final TeamMemberRepository teamMemberRepository;

    public TeamServiceImpl(
            TeamRepository teamRepository,
            FacilityRepository facilityRepository,
            FacilityLocationRepository facilityLocationRepository,
            TeamMemberRepository teamMemberRepository
    ) {
        this.teamRepository = teamRepository;
        this.facilityRepository = facilityRepository;
        this.facilityLocationRepository = facilityLocationRepository;
        this.teamMemberRepository = teamMemberRepository;
    }

    @Override
    public List<TeamResponse> getAllTeams() {
        return teamRepository.getAllTeams().stream()
                .map(team -> new TeamResponse(
                        team.teamId(),
                        team.writerId(),
                        team.writerName(),       // 추가된 필드
                        team.teamName(),
                        team.sportType(),
                        team.description(),
                        team.teamImageUrl(),
                        team.maxMembers(),
                        team.currentMembers(),
                        team.facilityId(),
                        team.facilityName(),     // 추가된 필드
                        team.facilityLocation(),     // 추가된 필드
                        team.facilityType(),     // 추가된 필드
                        team.facilityContact(),  // 추가된 필드
                        team.facilityLatitude(), // 추가된 필드
                        team.facilityLongitude(),// 추가된 필드
                        team.meetingDate(),
                        team.fee(),
                        team.ageLimit(),
                        team.preparing(),
                        team.rule(),
                        team.invitations(),
                        team.createdAt(),
                        team.updatedAt(),
                        team.isActive()
                ))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void createTeam(TeamRequest teamRequest) {
        // Step 1: Facility 저장
        Facility facility = Facility.builder()
                .name(teamRequest.facilityName())
                .location(teamRequest.facilityLocation())
                .type(teamRequest.facilityType())
                .contact(teamRequest.facilityContact())
                .build();
        System.out.println(facility);
        facilityRepository.createFacility(facility);
        Long getFacilityId = facilityRepository.getLastInsertedFacilityId();
        // Step 2: FacilityLocation 저장
        FacilityLocation facilityLocation = FacilityLocation.builder()
                .facilityId(getFacilityId)
                .latitude(teamRequest.latitude())
                .longitude(teamRequest.longitude())
                .build();
        facilityLocationRepository.createFacilityLocation(facilityLocation);

        // Step 3: Team 저장
        Team team = Team.builder()
                .writerId(teamRequest.writerId())
                .teamName(teamRequest.teamName())
                .sportType(teamRequest.sportType())
                .description(teamRequest.description())
                .teamImageUrl(teamRequest.teamImageUrl())
                .maxMembers(teamRequest.maxMembers())
                .facilityId(getFacilityId)
                .meetingDate(teamRequest.meetingDate())
                .fee(teamRequest.fee())
                .ageLimit(teamRequest.ageLimit())
                .preparing(teamRequest.preparing())
                .rule(teamRequest.rule())
                .invitations(teamRequest.invitations())
                .isActive(true)
                .build();

        // Create team and retrieve generated teamId
        teamRepository.createTeam(team);

        if (team.getTeamId() == null) {
            throw new IllegalStateException("Team ID was not generated");
        }

        // Step 4: 팀 생성자를 TeamMember로 저장
        TeamMember teamMember = TeamMember.builder()
                .teamId(team.getTeamId())
                .userId(teamRequest.writerId())
                .status("ACTIVE")
                .build();
        teamMemberRepository.addTeamMember(teamMember);
    }

    @Override
    public TeamResponse getTeamById(Long id) {
        var team = teamRepository.getTeamResponseById(id);
        return new TeamResponse(
                team.teamId(),
                team.writerId(),
                team.writerName(),       // 추가된 필드
                team.teamName(),
                team.sportType(),
                team.description(),
                team.teamImageUrl(),
                team.maxMembers(),
                team.currentMembers(),
                team.facilityId(),
                team.facilityName(),     // 추가된 필드
                team.facilityLocation(),     // 추가된 필드
                team.facilityType(),     // 추가된 필드
                team.facilityContact(),  // 추가된 필드
                team.facilityLatitude(), // 추가된 필드
                team.facilityLongitude(),// 추가된 필드
                team.meetingDate(),
                team.fee(),
                team.ageLimit(),
                team.preparing(),
                team.rule(),
                team.invitations(),
                team.createdAt(),
                team.updatedAt(),
                team.isActive()
        );
    }

    @Override
    @Transactional
    public void updateTeam(Long id, TeamRequest teamRequest) {
        // Step 1: Team 업데이트
        Team existingTeam = teamRepository.getTeamById(id);
        if (existingTeam == null) {
            throw new IllegalArgumentException("Team not found with ID: " + id);
        }

        // 기존 데이터 유지 로직 추가
        existingTeam.setTeamName(teamRequest.teamName() != null ? teamRequest.teamName() : existingTeam.getTeamName());
        existingTeam.setSportType(teamRequest.sportType() != null ? teamRequest.sportType() : existingTeam.getSportType());
        existingTeam.setDescription(teamRequest.description() != null ? teamRequest.description() : existingTeam.getDescription());
        existingTeam.setTeamImageUrl(teamRequest.teamImageUrl() != null ? teamRequest.teamImageUrl() : existingTeam.getTeamImageUrl());
        existingTeam.setMaxMembers(teamRequest.maxMembers() != null ? teamRequest.maxMembers() : existingTeam.getMaxMembers());
        existingTeam.setMeetingDate(teamRequest.meetingDate() != null ? teamRequest.meetingDate() : existingTeam.getMeetingDate());
        existingTeam.setFee(teamRequest.fee() != null ? teamRequest.fee() : existingTeam.getFee());
        existingTeam.setAgeLimit(teamRequest.ageLimit() != null ? teamRequest.ageLimit() : existingTeam.getAgeLimit());
        existingTeam.setPreparing(teamRequest.preparing() != null ? teamRequest.preparing() : existingTeam.getPreparing());
        existingTeam.setRule(teamRequest.rule() != null ? teamRequest.rule() : existingTeam.getRule());
        existingTeam.setInvitations(teamRequest.invitations() != null ? teamRequest.invitations() : existingTeam.getInvitations());
        teamRepository.updateTeam(existingTeam);

        // Step 2: Facility 업데이트
        Facility facility = facilityRepository.getFacilityById(existingTeam.getFacilityId());
        if (facility != null) {
            facility.setName(teamRequest.facilityName() != null ? teamRequest.facilityName() : facility.getName());
            facility.setLocation(teamRequest.facilityLocation() != null ? teamRequest.facilityLocation() : facility.getLocation());
            facility.setType(teamRequest.facilityType() != null ? teamRequest.facilityType() : facility.getType());
            facility.setContact(teamRequest.facilityContact() != null ? teamRequest.facilityContact() : facility.getContact());
            facilityRepository.updateFacility(facility);
        }

        // Step 3: FacilityLocation 업데이트
        FacilityLocation facilityLocation = facilityLocationRepository.getFacilityLocationById(existingTeam.getFacilityId());
        if (facilityLocation != null) {
            facilityLocation.setLatitude(teamRequest.latitude() != null ? teamRequest.latitude() : facilityLocation.getLatitude());
            facilityLocation.setLongitude(teamRequest.longitude() != null ? teamRequest.longitude() : facilityLocation.getLongitude());
            facilityLocationRepository.updateFacilityLocation(facilityLocation);
        }
    }


    @Override
    @Transactional
    public void deleteTeam(Long id) {
        // Step 1: 팀 조회
        Team team = teamRepository.getTeamById(id);
        if (team == null) {
            throw new IllegalArgumentException("Team not found with ID: " + id);
        }

        // Step 2: TeamMember 삭제
        teamRepository.deleteTeam(id);

        // Step 3: FacilityLocation 삭제
        if (team.getFacilityId() != null) {
            facilityLocationRepository.deleteFacilityLocation(team.getFacilityId());
        }

        // Step 4: Facility 삭제
        if (team.getFacilityId() != null) {
            facilityRepository.deleteFacility(team.getFacilityId());
        }

        // Step 5: Team 삭제
        teamRepository.deleteTeam(id);
    }

}
