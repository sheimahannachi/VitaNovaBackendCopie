package com.example.vitanovabackend.Controllers;

import com.example.vitanovabackend.DAO.Entities.ERole;
import com.example.vitanovabackend.DAO.Entities.User;
import com.example.vitanovabackend.DAO.Repositories.UserRepository;
import com.example.vitanovabackend.Payload.Request.LoginRequest;
import com.example.vitanovabackend.Payload.Request.ResetPasswordRequest;
import com.example.vitanovabackend.Payload.Request.SignupRequest;
import com.example.vitanovabackend.Payload.Response.MessageResponse;
import com.example.vitanovabackend.Payload.Response.UserInfoResponse;
import com.example.vitanovabackend.Security.services.UserDetailsImpl;
import com.example.vitanovabackend.Service.IUserService;
import com.example.vitanovabackend.Service.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;



    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtService jwtService;

    IUserService services;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {

        User user = services.loginUser(loginRequest.getUsername(), loginRequest.getPassword());

        if (user != null) {
            return ResponseEntity.ok().body(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
    @CrossOrigin("**")
    @PostMapping("/generateToken")
    public ResponseEntity<UserInfoResponse> authenticateAndGetToken(@RequestBody LoginRequest authRequest) {
        UserInfoResponse response = new UserInfoResponse();
        System.out.println(authRequest);
        User user = services.loginUser(authRequest.getUsername(), authRequest.getPassword());
        System.out.println(user);
        System.out.println("generating token : ");
        if (user != null) {

            // Authenticate user

            ResponseCookie jwtCookie = jwtService.generateJwtCookie(user);
            HttpHeaders headers = new HttpHeaders();
            System.out.println(jwtCookie);
            return ResponseEntity.ok().headers(headers).header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .body(new UserInfoResponse(user.getIdUser(),
                            user.getUsername(),
                            user.getRole().toString(),
                            user.getEmail(),
                            jwtCookie.getValue()
                    ));


        }
return (ResponseEntity<UserInfoResponse>) ResponseEntity.badRequest();
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));
user.setDateOfBirth(LocalDate.parse(signUpRequest.getDateOfBirth()));

user.setGender(signUpRequest.getGender());
user.setLastName(signUpRequest.getLastName());
user.setFirstName(signUpRequest.getFirstName());
user.setPicture(signUpRequest.getPicture());
user.setWeight(signUpRequest.getWeight());
user.setHeight(signUpRequest.getHeight());
        String strRoles = signUpRequest.getRole();
        System.out.println("strroles " +strRoles);
        if(strRoles.equals(ERole.ADMIN.toString()))user.setRole(ERole.ADMIN);
        else if(strRoles.equals(ERole.USER.toString()))user.setRole(ERole.USER);
        System.out.println(strRoles.equals(ERole.ADMIN.toString()));
        System.out.println(user.getPicture()+user.getFirstName()+user.getLastName());
        System.out.println("userRole : " + user.getRole());
        System.out.println("roleadmin " + ERole.ADMIN.toString());
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @GetMapping("/signout")
    public ResponseEntity<?> logoutUser(HttpServletRequest request, HttpServletResponse response) {
        ResponseCookie cookie = jwtService.getCleanJwtCookie();
        System.out.println("signing out : ");
        // Set the expiration date of the cookie to a past time to delete it
        cookie = ResponseCookie.from(cookie.getName(), cookie.getValue())
                .path(cookie.getPath())
                .maxAge(0)  // Set maxAge to 0 to delete the cookie
                .httpOnly(true)
                .build();

        // Add the cookie to the response with maxAge=0 to delete it
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok().body(new MessageResponse("You've been signed out!"));
    }



    @GetMapping("/getuserfromtoken")
    public ResponseEntity<?>GetUserFromToken(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("jwtToken")) {
                    String jwtToken = cookie.getValue();
                    System.out.println(jwtToken);
                    String username = jwtService.extractUsername(jwtToken); // Assuming you have a method to extract username from JWT token
                 //   System.out.println("username : "+username +" from token "+ jwtToken );
                    User user = services.loadUserByUsername(username); // Retrieve user from database based on username
                 //   System.out.println(user.getEmail());

                    return ResponseEntity.ok(user);

                }

            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // JWT token not found in cookie
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/reset-password")
    public User resetPassword(@RequestBody ResetPasswordRequest request) {
        return services.ResetPassword(request.getEmail(), request.getPassword());

    }

}