package com.ssafy.health.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ProfileDto {
    private int userId;
    private String name;
    private String location;
    private String sports;
    private String profileImage;
}
