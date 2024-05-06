package com.example.vitanovabackend.Controllers;

import com.example.vitanovabackend.DAO.Entities.*;
import com.example.vitanovabackend.DAO.Repositories.IpAddressesRepository;
import com.example.vitanovabackend.DAO.Repositories.UserRepository;
import com.example.vitanovabackend.Payload.Request.LoginRequest;
import com.example.vitanovabackend.Payload.Request.ResetPasswordRequest;
import com.example.vitanovabackend.Payload.Response.MessageResponse;
import com.example.vitanovabackend.Payload.Response.UserInfoResponse;
import com.example.vitanovabackend.Service.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;


@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    CartService cartService;



    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtService jwtService;
    @Autowired
    MiscService miscService;
    IUserService services;
    @Autowired
    private EmailService emailService;
    private final IpAddressesRepository ipAddressesRepository;


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
        if (authRequest != null) {
            User user = services.loginUser(authRequest.getUsername(), authRequest.getPassword());

            if (user != null) {
                String jwtToken = jwtService.generateToken(user.getUsername());
                HttpHeaders headers = new HttpHeaders();
                headers.add("Authorization", "Bearer " + jwtToken);

                return ResponseEntity.ok().headers(headers)
                        .body(new UserInfoResponse(user.getIdUser(),
                                user.getUsername(),
                                user.getRole().toString(),
                                user.getEmail(),
                                jwtToken
                        ));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @CrossOrigin("**")
    @PostMapping("/LoginGoogle")
    public ResponseEntity<UserInfoResponse> LoginGoogle(@RequestParam("email") String email) {
        System.out.println(email);
        if (email != null) {
User user = userRepository.findByEmail(email);
            System.out.println("generating token : ");
            if (user != null) {
                String jwtToken = jwtService.generateToken(user.getUsername());
                HttpHeaders headers = new HttpHeaders();
                headers.add("Authorization", "Bearer " + jwtToken);

                return ResponseEntity.ok().headers(headers)
                        .body(new UserInfoResponse(user.getIdUser(),
                                user.getUsername(),
                                user.getRole().toString(),
                                user.getEmail(),
                                jwtToken
                        ));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
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

            // Copy the file to the desired location
            Path destinationPath = Paths.get("C:\\xampp\\htdocs\\cats", pictureFileName);
            Files.copy(tempFilePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);

            user.setPicture(pictureFileName);

            tempFile.delete();
        }

        user.setWeight(weight);
        user.setHeight(height);
        user.setPhone(phone);
        user.setPlan(Plan.FREE);
        ERole userRole = ERole.valueOf(role.toUpperCase());
        user.setRole(userRole);
Cart cart = new Cart();
user.setCart(cart);
        IPAdresses ipAdresses= new IPAdresses();
        ipAdresses.setValue(emailService.getWANIPAddress());
        ipAdresses.setLocation(emailService.getLocationFromIPAddress(ipAdresses.getValue()));
        ipAdresses.setLocation(null);
        ipAdresses.setUser(user);
        userRepository.save(user);
        ipAddressesRepository.save(ipAdresses);



        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }







    @PostMapping("/GoogleSignup")
    public ResponseEntity<?> googleSignup(@RequestParam String username,
                                          @RequestParam String email,
                                          @RequestParam String password,
                                          @RequestParam String role,
                                          @RequestParam String gender,
                                          @RequestParam String dateOfBirth,
                                          @RequestParam String firstName,
                                          @RequestParam String lastName,
                                          @RequestParam String phone,
                                          @RequestParam("picture") String pictureUrl) throws IOException {

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || role.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Missing required parameters"));
        }

        if (userRepository.existsByUsername(username) || userRepository.existsByEmail(email)) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username or email is already taken!"));
        }
        password=username;
        System.out.println("pass "+ password);
        User user = new User(username, email, encoder.encode(password));

        user.setDateOfBirth(LocalDate.parse(dateOfBirth.substring(0, 10)));
        user.setGender(Gender.MAN);
        user.setLastName(lastName);
        user.setFirstName(firstName);

        user.setPhone(null);
        user.setPlan(Plan.FREE);
        ERole userRole = ERole.valueOf(role.toUpperCase());
        user.setRole(userRole);
        Cart cart = new Cart();
        user.setCart(cart);

        String pictureFileName = getFileNameFromUrl(pictureUrl);
        Path destinationPath = Paths.get("C:\\xampp\\htdocs\\cats", pictureFileName+".jpg");
        downloadFile(pictureUrl, destinationPath);
        user.setPicture(pictureFileName+".jpg");

        userRepository.save(user);
        IPAdresses ipAdresses = new IPAdresses();
        ipAdresses.setValue(emailService.getWANIPAddress());
        ipAdresses.setLocation(emailService.getLocationFromIPAddress(ipAdresses.getValue()));
        ipAdresses.setUser(user);
        ipAddressesRepository.save(ipAdresses);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    private String getFileNameFromUrl(String url) {
        String[] parts = url.split("/");
        return parts[parts.length - 1];
    }

    private void downloadFile(String url, Path destinationPath) throws IOException {
        URL website = new URL(url);
        ReadableByteChannel rbc = Channels.newChannel(website.openStream());
        FileOutputStream fos = new FileOutputStream(destinationPath.toFile());
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
    }











    @GetMapping("/signout")
    public ResponseEntity<?> logoutUser(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Authorization", "");

        // Clear sessionStorage
        HttpSession session = request.getSession();
        session.invalidate();

        return ResponseEntity.ok().body(new MessageResponse("You've been signed out!"));
    }





    @GetMapping("/getuserfromtoken")
    public ResponseEntity<?> getUserFromToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwtToken = authHeader.substring(7);
            System.out.println(jwtToken);
            String username = jwtService.extractUsername(jwtToken);
            System.out.println("USERNAME : " + username );

            if (username != null) {
                User user = services.loadUserByUsername(username);
                if (user != null) {
                    return ResponseEntity.ok(user);
                }
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // JWT token not found in Authorization header
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

    @GetMapping("/CheckIpAddress")
    public boolean IpAdressCheck(@RequestParam("username") String username , @RequestParam("ipAdress") String ip ){
        User user = userRepository.findByUsername(username);
        System.out.println("adresse ip : " + ip);
        System.out.println("username : " + username);

        if (user != null) {



            IPAdresses ipAdresses = ipAddressesRepository.findByUserAndValue(user, ip);

            return ipAdresses != null;

        }
        return false;
    }

    @CrossOrigin("*")
    @GetMapping("/AddIpAddress")
    public void AddIpAddress(@RequestParam("qcxBb0ipkpAM") String EncryptedUsername, @RequestParam("ipAdress") String ip, HttpServletResponse response) throws IOException {
        String encryptedText = EncryptedUsername.replace(' ', '+');

        String username = miscService.decrypt(encryptedText);

        User user = userRepository.findByUsername(username);
        if (user != null) {
            IPAdresses ipAdresses2 = ipAddressesRepository.findByUserAndValue(user, emailService.getWANIPAddress());
if(ipAdresses2==null){
            IPAdresses ipAdresses = new IPAdresses();
            ipAdresses.setValue(ip);
            ipAdresses.setLocation(emailService.getLocationFromIPAddress(ip));
            ipAdresses.setUser(user);
            ipAddressesRepository.save(ipAdresses);
            String redirectUrl = "http://localhost:4200/login?verificationLinkClicked=true";
            response.sendRedirect(redirectUrl);
        } }else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }




    }