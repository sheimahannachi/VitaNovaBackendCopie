package com.example.vitanovabackend.Controllers;

import com.example.vitanovabackend.DAO.Entities.ERole;
import com.example.vitanovabackend.DAO.Entities.Gender;
import com.example.vitanovabackend.DAO.Entities.IPAdresses;
import com.example.vitanovabackend.DAO.Entities.User;
import com.example.vitanovabackend.DAO.Repositories.IpAdressesRepository;
import com.example.vitanovabackend.DAO.Repositories.UserRepository;
import com.example.vitanovabackend.Payload.Request.LoginRequest;
import com.example.vitanovabackend.Payload.Request.ResetPasswordRequest;
import com.example.vitanovabackend.Payload.Response.MessageResponse;
import com.example.vitanovabackend.Payload.Response.UserInfoResponse;
import com.example.vitanovabackend.Service.EmailService;
import com.example.vitanovabackend.Service.IUserService;
import com.example.vitanovabackend.Service.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
    @Autowired
    private EmailService emailService;
    private final IpAdressesRepository ipAdressesRepository;


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
        if(authRequest!=null){
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
                    ));}


        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // JWT token not found in cookie
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestParam String username,
                                          @RequestParam String email,
                                          @RequestParam String password,
                                          @RequestParam String role,
                                          @RequestParam Gender gender,
                                          @RequestParam int height,
                                          @RequestParam int weight,
                                          @RequestParam String dateOfBirth,
                                          @RequestParam String firstName,
                                          @RequestParam String lastName,
                                          @RequestParam boolean verified,
                                          @RequestParam String phone,
                                          @RequestPart("file") MultipartFile picture) throws IOException {

        if (userRepository.existsByUsername(username)) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(email)) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }



        User user = new User(username,
                email,
                encoder.encode(password));


        String dateOfBirthString = dateOfBirth.substring(0, 10);
        dateOfBirth = String.valueOf(LocalDate.parse(dateOfBirthString));
        user.setDateOfBirth(LocalDate.parse(dateOfBirth));
        user.setGender(gender);
        user.setLastName(lastName);
        user.setFirstName(firstName);

        if (picture != null) {
            String pictureFileName = picture.getOriginalFilename();
            Path tempFilePath = Files.createTempFile("temp-picture", pictureFileName);
            File tempFile = tempFilePath.toFile();
            picture.transferTo(tempFile);

            user.setPicture(pictureFileName);

            tempFile.delete();
        }

        user.setWeight(weight);
        user.setHeight(height);
        user.setPhone(phone);

        ERole userRole = ERole.valueOf(role.toUpperCase());
        user.setRole(userRole);

        IPAdresses ipAdresses= new IPAdresses();
        ipAdresses.setValue(emailService.getWANIPAddress());
        ipAdresses.setLocation(emailService.getLocationFromIPAddress(ipAdresses.getValue()));
        ipAdressesRepository.save(ipAdresses);
        user.getIpAdresses().add(ipAdresses);


        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }


    @GetMapping("/signout")
    public ResponseEntity<?> logoutUser(HttpServletRequest request, HttpServletResponse response) {
        ResponseCookie cookie = jwtService.getCleanJwtCookie();
        System.out.println("signing out : ");
        cookie = ResponseCookie.from(cookie.getName(), cookie.getValue())
                .path(cookie.getPath())
                .maxAge(0)
                .httpOnly(true)
                .build();

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
    @PutMapping("/resetPassword")
    public User resetPassword(@RequestBody ResetPasswordRequest request) {
        return services.ResetPassword(request.getEmail(), request.getPassword());

    }
    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/resetPasswordPhone")
    public User resetPasswordPhone(@RequestBody ResetPasswordRequest request) {
        return services.ResetPasswordPhone(request.getPhone(), request.getPassword());

    }

    @GetMapping("/checkUsername")
    public boolean checkUsernameExists(@RequestParam String username) {
        return userRepository.existsByUsername(username);
    }

    @GetMapping("/checkEmail")
    public boolean checkEmailExists(@RequestParam String email) {
        return userRepository.existsByEmail(email);
    }
}

