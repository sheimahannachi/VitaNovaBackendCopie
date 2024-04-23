package com.example.vitanovabackend.Payload.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ResetPasswordRequest {
    private String email;
    private String password;
    private String phone;

}
