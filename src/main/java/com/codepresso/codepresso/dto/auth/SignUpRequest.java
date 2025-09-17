package com.codepresso.codepresso.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 회원가입 요청 DTO
 */
@Data
public class SignUpRequest {
    @NotBlank
    @Size(min = 4, max = 50)
    private String accountId;

    @NotBlank
    @Size(min = 6, max = 100)
    private String password;

    @NotBlank
    @Size(min = 2, max = 50)
    private String nickname;

    @NotBlank
    @Email
    private String email;
}