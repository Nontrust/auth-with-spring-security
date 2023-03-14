package com.nontrust.authwithspringsecurity.api.v1.dto.request;

import lombok.Getter;
import lombok.Setter;

public class UserRequestDto {

    @Getter
    @Setter
    public static class SignUp{
        private String email;
        private String password;
    }
}
