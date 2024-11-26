package com.ssafy.health.service;

import com.ssafy.health.dao.ChallengeRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChallengeService {

    private final ChallengeRepository challengeRepository;

    public ChallengeService(ChallengeRepository challengeRepository) {
        this.challengeRepository = challengeRepository;
    }

    public String calculateMedal(int totalScore) {
        if (totalScore >= 61) return "금메달2";
        if (totalScore >= 51) return "금메달1";
        if (totalScore >= 41) return "은메달2";
        if (totalScore >= 31) return "은메달1";
        if (totalScore >= 21) return "동메달2";
        if (totalScore >= 11) return "동메달1";
        return "메달 없음";
    }

    public List<Integer> challengeList(Long userId) {
        List<Integer> list = new ArrayList<>();

        // 프로필 설정 점수
        if (challengeRepository.checkProfileCompletion(userId)) {
            list.add(1);
        } else {
            list.add(0);
        }

        int cnt = 0;
        // 모임 주최 점수
        int meetingsHosted = challengeRepository.countMeetingsHosted(userId);
        if (meetingsHosted >= 1) cnt += 1; // 1명 모집
        if (meetingsHosted >= 5) cnt += 1; // 5명 모집
        if (meetingsHosted >= 10) cnt += 1; // 10명 모집
        if(cnt==0) list.add(0);
        else list.add(cnt);

        cnt = 0;
        // 모임 참여 점수
        int meetingsParticipated = challengeRepository.countMeetingsParticipated(userId);
        if (meetingsParticipated >= 1) cnt += 1; // 1번 모임 참여
        if (meetingsParticipated >= 10) cnt += 1; // 10번 모임 참여
        if (meetingsParticipated >= 20) cnt += 1; // 20번 모임 참여
        if (meetingsParticipated >= 50) cnt += 1; // 50번 모임 참여
        if (meetingsParticipated >= 100) cnt += 1; // 100번 모임 참여

        if(cnt==0) list.add(0);
        else list.add(cnt);

        cnt = 0;
        // 친구 만들기 점수
        int friends = challengeRepository.countFriends(userId);
        if (friends >= 1) cnt += 1; // 친구 1명
        if (friends >= 10) cnt += 1; // 친구 10명
        if (friends >= 20) cnt += 1; // 친구 20명
        if (friends >= 50) cnt += 1; // 친구 50명
        if (friends >= 100) cnt += 1; // 친구 100명

        if(cnt==0) list.add(0);
        else list.add(cnt);

        return list;
    }

    public Map<String, Boolean> getChallengeStatus(Long userId) {
        Map<String, Boolean> challengeStatus = new LinkedHashMap<>();
        List<Integer> list = challengeList(userId);

        // 1. 프로필 설정 여부

        int isProfileComplete = list.get(0);
        challengeStatus.put("프로필 설정하기", isProfileComplete == 1);

        // 2. 모임 관련 챌린지
        int meetingsHosted = list.get(1);
        challengeStatus.put("모임 만들기", meetingsHosted >= 1);
        challengeStatus.put("1명 모집하기", meetingsHosted >= 2);
        challengeStatus.put("5명 모집하기", meetingsHosted >= 3);
        challengeStatus.put("10명 모집하기", meetingsHosted >= 4);

        // 3. 모임 참여 챌린지
        int meetingsParticipated = list.get(2);
        challengeStatus.put("모임 참여하기", meetingsParticipated >= 1);
        challengeStatus.put("10번 모임가기", meetingsParticipated >= 2);
        challengeStatus.put("20번 모임가기", meetingsParticipated >= 3);
        challengeStatus.put("50번 모임가기", meetingsParticipated >= 4);
        challengeStatus.put("100번 모임가기", meetingsParticipated >= 5);

        // 4. 친구 관련 챌린지
        int friendsCount = list.get(3);
        challengeStatus.put("친구 만들기", friendsCount >= 1);
        challengeStatus.put("친구 10명 만들기", friendsCount >= 2);
        challengeStatus.put("친구 20명 만들기", friendsCount >= 3);
        challengeStatus.put("친구 50명 만들기", friendsCount >= 4);
        challengeStatus.put("친구 100명 만들기", friendsCount >= 5);

        return challengeStatus;
    }
}
