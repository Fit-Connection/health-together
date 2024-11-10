package com.ssafy.health.dto.request;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String email;
    private String password;
}
