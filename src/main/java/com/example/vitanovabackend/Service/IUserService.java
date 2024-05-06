package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.DAO.Entities.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface IUserService {
    List<User> SearchByName(String name);

    List<User> SearchByLastName(String LastName);


    List<User> SearchByNameAndLastName(String name, String LastName);

    User AddUser(User user);

    User loginUser(String email, String password);

    int ArchiveUser(Long Id);

    int ActivateUser(Long Id);

    List<User> getAllUsers();

    User updateUser(User user);

    User DeleteGoal(User user);


    User ResetPassword(String Email, String password);

    User ResetPasswordPhone(String Phone, String password);

    User loadUserByUsername(String email) throws UsernameNotFoundException;

    User GetUserByEmail(String email) ;


    User GetUserByGoal(PersonalGoals personalGoals);
}
