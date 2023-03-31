package com.nontrust.authwithspringsecurity.api.v1.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.Collections;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Component
public class Response {
    final static String SUCCESS = "success";
    final static String FAIL = "fail";


    @Getter
    @Builder
    private static class Body{
        private int state;
        private String result;
        private String message;
        private Errors errors;
        private Object data;
    }
    public ResponseEntity<?> success(String msg, Object data, HttpStatus status){
        Body body = Body.builder()
                .result(SUCCESS)
                .data(data)
                .message(msg)
                .state(status.value())
                .build();
        return ResponseEntity.ok(body);
    }
    public ResponseEntity<?> success(String msg){
        return success(msg, Collections.emptyList(), OK);
    }

    public ResponseEntity<?> fail(String msg, Object data, HttpStatus status){
        Body body = Body.builder()
                .result(FAIL)
                .data(data)
                .message(msg)
                .state(status.value())
                .build();
        return ResponseEntity.ok(body);
    }

    public ResponseEntity<?> fail(String msg){
        return success(msg, Collections.emptyList(), OK);
    }

    public ResponseEntity<?> invalidFields(Errors errors) {
        Body body = Body.builder()
                .result(FAIL)
                .data(Collections.emptyList())
                .message("")
                .state(BAD_REQUEST.value())
                .errors(errors)
                .build();
        return ResponseEntity.ok(body);
    }

}
