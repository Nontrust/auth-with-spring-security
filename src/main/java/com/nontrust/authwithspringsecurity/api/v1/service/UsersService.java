package com.nontrust.authwithspringsecurity.api.v1.service;

import com.nontrust.authwithspringsecurity.api.v1.dto.request.UserRequestDto;
import com.nontrust.authwithspringsecurity.api.v1.dto.response.Response;
import com.nontrust.authwithspringsecurity.api.v1.repository.UsersRepository;
import com.nontrust.authwithspringsecurity.entity.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final Response response;

    public ResponseEntity<?> SignUp(UserRequestDto.SignUp signUpDto) {
        Users users = Users.builder()
                .email(signUpDto.getEmail())
                .password(signUpDto.getPassword())
                .build();
        usersRepository.save(users);
        return response.success("성공했습니다.");
    }

    public ResponseEntity<?> login(UserRequestDto.SignUp signUp) {
        Optional<Users> user = usersRepository.findByEmailAndPassword(signUp.getEmail(), signUp.getPassword());

        if(user.isEmpty()) {
            return response.fail("로그인에 실패했습니다.");
        }

        return response.success("성공했습니다.");
    }
}
