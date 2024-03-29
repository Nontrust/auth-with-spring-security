package com.nontrust.authwithspringsecurity.api.v1.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class UserRequestDto {

    @ToString(exclude = "password")
    @Getter
    @Setter
    public static class SignUp{
        @NotEmpty(message = "아이디를 입력해 주세요.")
        private String email;
        @NotEmpty(message = "비밀번호를를 입력해 주세요.")
        private String password;
    }
}
