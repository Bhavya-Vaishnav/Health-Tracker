package com.bhavya.healthTracker.service;


import com.bhavya.healthTracker.entity.User;
import com.bhavya.healthTracker.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean saveNewUser(User user) {
        try {
            user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            log.error("Error while creating User", e);
        }
        return false;
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User findByName(String name) {
        return userRepository.findByName(name);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(User user){
        userRepository.delete(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User findOneById(ObjectId id){
        return userRepository.findOneById(id);
    }

}
