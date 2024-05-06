package com.example.vitanovabackend.Service;

import com.example.vitanovabackend.DAO.Entities.PersonalGoals;
import com.example.vitanovabackend.DAO.Entities.User;
import com.example.vitanovabackend.DAO.Repositories.PersonalGoalsRepository;
import com.example.vitanovabackend.DAO.Repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PersonalGoalsRepository personalGoalsRepository;


    private final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    @Autowired
    private EmailService emailService;

    @Override
    public User AddUser(User user) {
        return userRepository.save(user);
    }


    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public User DeleteGoal(User user){
        long idPg=user.getPersonalGoals().getIdPG();
        user.setPersonalGoals(null);
        userRepository.save(user);
        if(personalGoalsRepository.findById(idPg).isPresent()) {
            PersonalGoals personalGoals1 = personalGoalsRepository.findById(idPg).get();
            personalGoalsRepository.delete(personalGoals1);
        }
return user;
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
    public User ResetPasswordPhone(String Phone, String password) {
        User user = userRepository.findByPhone(Phone);
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);

    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return user;
    }

@Override
    public  User GetUserByGoal(PersonalGoals personalGoals){
        return userRepository.findByPersonalGoals(personalGoals);
}

@Override
    public User GetUserByEmail(String email) {
return userRepository.findByEmail(email);

}



    }
