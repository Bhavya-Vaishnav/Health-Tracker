package com.bhavya.healthtracker.service;


import com.bhavya.healthtracker.dto.userDTOs.UserResponseDTO;
import com.bhavya.healthtracker.dto.userDTOs.UserUpdateDTO;
import com.bhavya.healthtracker.entity.User;
import com.bhavya.healthtracker.enums.UserRoles;
import com.bhavya.healthtracker.exception.EmailAlreadyExistsException;
import com.bhavya.healthtracker.exception.ResourceNotFoundException;
import com.bhavya.healthtracker.exception.UnauthorizedAccessException;
import com.bhavya.healthtracker.repository.UserRepository;
import com.mongodb.DuplicateKeyException;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CacheManager cacheManager;


    @Cacheable(value = "userProfile", key = "#email")
    public UserResponseDTO getUser(String email) {
        User user = findByEmail(email);
        return toDto(user);
    }

    public User saveNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singletonList(UserRoles.ROLE_USER));
        user.setEnabled(true);
        try {
            return userRepository.save(user);
        } catch (DuplicateKeyException e) {
            throw new EmailAlreadyExistsException("Email already registered: " + user.getEmail());
        }
    }

    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with email: " + email);
        }
        return user;
    }

    @Transactional
    @CacheEvict(value = "userProfile", key = "#email")
    public UserResponseDTO updateUser(String email, UserUpdateDTO dto) {
        User userInDb = findByEmail(email);
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            userInDb.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        if (dto.getEnabled() != null) {
            userInDb.setEnabled(dto.getEnabled());
        }
        userRepository.save(userInDb);
        return toDto(userInDb);
    }

    @CacheEvict(value = "userProfile", key = "#email")
    public void deleteUser(String email, Map<String, String> payload) {
        String rawPassword = payload.get("password");
        if (rawPassword == null || rawPassword.isEmpty()) {
            throw new UnauthorizedAccessException("Password is required to delete account");
        }
        User user = findByEmail(email);
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new UnauthorizedAccessException("Incorrect password");
        }
        userRepository.delete(user);
    }

    public List<UserResponseDTO> getAllUsersAdmin() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::toDto)
                .toList();
    }

    public UserResponseDTO getUserByIdAdmin(String id) {
        User user = userRepository.findOneById(new ObjectId(id));
        if (user == null) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        return toDto(user);
    }

    public void disableUser(String id) {
        User user = userRepository.findOneById(new ObjectId(id));
        if (user == null) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        user.setEnabled(false);
        userRepository.save(user);
        evictUserProfileCache(user.getEmail());
    }

    public void deleteUserAdmin(String id) {
        User user = userRepository.findOneById(new ObjectId(id));
        if (user == null) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.delete(user);
        evictUserProfileCache(user.getEmail());
    }

    private UserResponseDTO toDto(User user) {
        return UserResponseDTO.builder()
                .name(user.getName())
                .email(user.getEmail())
                .roles(user.getRoles())
                .enable(user.isEnabled())
                .build();
    }

    private void evictUserProfileCache(String email) {
        Cache cache = cacheManager.getCache("userProfile");
        if (cache != null) {
            cache.evict(email);
        }
    }
}
