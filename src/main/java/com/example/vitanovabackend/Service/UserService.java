package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.DAO.Entities.User;
import com.example.vitanovabackend.DAO.Repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    @Override
    public User AddUser(User user) {
        return userRepository.save(user);
    }


    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }





    @Override
    public List<User> SearchByNameAndLastName(String name, String LastName){
        return userRepository.findAllByFirstNameAndLastName(name,LastName);
    }
    @Override
    public User loginUser(String username, String password) {
        User user = userRepository.findByUsername(username);

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        } else {
            return null;
        }
    }

    @Override
    public int ArchiveUser(Long Id) {
        User user = userRepository.findById(Id).get();
        if (user != null) {
            user.setArchive(true);
            userRepository.save(user);
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public List<User> SearchByName(String name) {
        return null;
    }

    public List<User> SearchByLastName(String LastName){
        return userRepository.findAllByLastName(LastName);

    }

    @Override
    public List<User> getAllUsers(){
    return userRepository.findAll();

    }

    @Override
  public int ActivateUser(Long Id){
        User user = userRepository.findById(Id).get();
        if (user != null) {
            user.setArchive(false);
            userRepository.save(user);
            return 1;
        } else {
            return 0;
        }
    }
    @Override
    public User ResetPassword(String Email,String password){
User user = userRepository.findByEmail(Email);
user.setPassword(passwordEncoder.encode(password));
return userRepository.save(user);

    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        // Retrieve user details from the repository based on the email
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return user;
    }


    }
