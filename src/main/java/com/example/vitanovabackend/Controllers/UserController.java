package com.example.vitanovabackend.Controllers;

import com.example.vitanovabackend.DAO.Entities.*;
import com.example.vitanovabackend.Service.IUserService;
import com.example.vitanovabackend.Service.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api/user")
@RestController
@AllArgsConstructor
public class UserController {
    JwtService jwtService;
    IUserService iUserService;
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/AddUser")
    public User addUser(@RequestBody User user) {
        return iUserService.AddUser(user);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/admin/UpdateUser")
    public User updateUser(@RequestBody User user) {
        return iUserService.updateUser(user);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/admin/AllUsers")
    public List<User> GetUsers() {
        return iUserService.getAllUsers();
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("admin/DeleteUser/{id}")
    public void DeleteUser(@PathVariable("id") long id){
        iUserService.ArchiveUser(id);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("admin/ActivateUser/{id}")
    public void ActivateUser(@PathVariable("id") long id){
        iUserService.ActivateUser(id);
    }








}
