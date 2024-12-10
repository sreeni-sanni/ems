package com.abn.ems.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AuthRequest {
    @NotEmpty
    private String username;
}
