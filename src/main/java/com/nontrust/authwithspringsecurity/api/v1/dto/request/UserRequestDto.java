package com.nontrust.authwithspringsecurity.api.v1.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class UserRequestDto {

    @ToString(exclude = "password")
    @Getter
    @Setter
    public static class SignUp{
        @NotNull
        private String email;
        @NotNull
        private String password;
    }
}
