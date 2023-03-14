package com.nontrust.authwithspringsecurity.api.v1.controller;


import com.nontrust.authwithspringsecurity.api.v1.dto.request.UserRequestDto;
import com.nontrust.authwithspringsecurity.api.v1.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
@RestController
public class UsersController {

    private final UsersService usersService;

    @PostMapping("login")
    public String login(){

        return "hello world!";
    }

    @PostMapping("sign-up")
    public ResponseEntity<?> signUp(@RequestBody UserRequestDto.SignUp signUp){
        log.warn("sign up", signUp);
        return usersService.SignUp(signUp);
    }

}
