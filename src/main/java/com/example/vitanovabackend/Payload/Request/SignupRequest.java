package com.example.vitanovabackend.Payload.Request;

import com.example.vitanovabackend.DAO.Entities.Gender;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.File;
import java.time.LocalDate;
import java.util.Set;
@Getter
@Setter
@Data
public class SignupRequest {

    private String username;


    private String email;
    private String firstName;
    private String lastName;
    private String role;
    private String password;
    @Getter
    private Gender gender;
    private float height;
    private float weight;
    private String picture;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String dateOfBirth;
    private String phone;

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
