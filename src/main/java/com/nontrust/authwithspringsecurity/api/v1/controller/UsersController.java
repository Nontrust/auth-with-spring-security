package com.nontrust.authwithspringsecurity.api.v1.controller;


import com.nontrust.authwithspringsecurity.api.v1.dto.request.UserRequestDto;
import com.nontrust.authwithspringsecurity.api.v1.dto.response.Response;
import com.nontrust.authwithspringsecurity.api.v1.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
@RestController
public class UsersController {

    private final UsersService usersService;
    private final Response response;

    @PostMapping("login")
    public ResponseEntity<?> login(@Validated @RequestBody UserRequestDto.SignUp signUp, Errors errors){
        if(errors.hasErrors()){
            return response.invalidFields(errors);
        }
        return usersService.login(signUp);
    }

    @PostMapping("sign-up")
    public ResponseEntity<?> signUp(@Validated @RequestBody UserRequestDto.SignUp signUp, Errors errors){
        if(errors.hasErrors()){
            return response.invalidFields(errors);
        }
        return usersService.signUp(signUp);
    }

}
