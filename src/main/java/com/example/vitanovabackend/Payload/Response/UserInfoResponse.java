package com.example.vitanovabackend.Payload.Response;

import lombok.*;

import java.util.List;
@Getter
@Setter
@Data
@NoArgsConstructor
public class UserInfoResponse {
    private Long id;
    private String username;
    private String email;
    private String role;
    private String token;

    public UserInfoResponse(Long id, String username, String role, String email , String token
    ){
        this.id=id;
        this.username=username;
        this.role=role;
        this.email=email;
        this.token=token;
    }


}