package com.nontrust.authwithspringsecurity.api.v1.service;

import com.nontrust.authwithspringsecurity.api.v1.dto.request.UserRequestDto;
import com.nontrust.authwithspringsecurity.api.v1.repository.UsersRepository;
import com.nontrust.authwithspringsecurity.entity.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UsersService {

    private final UsersRepository usersRepository;
    public ResponseEntity<?> SignUp(UserRequestDto.SignUp signUpDto) {
        Users users = Users.builder().email(signUpDto.getEmail()).password(signUpDto.getPassword()).build();
        usersRepository.save(users);
        return ResponseEntity.ok("성공했습니다.");
    }
}
