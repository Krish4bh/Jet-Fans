package com.Jet_Fans.web.service;


import com.Jet_Fans.web.entity.User;
import com.Jet_Fans.web.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public List<User> getAll() {
        return userRepo.findAll();
    }

    public User getById(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new RuntimeException("User with id " + id + " not found."));
    }

    public void createUser(User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new RuntimeException("Email can not be empty");
        }

        userRepo.findByEmail(user.getEmail()).ifPresent(existingUser -> {
            throw new RuntimeException("User with email " + existingUser.getEmail() + " already exists.");
        });
        userRepo.save(user);
    }

    public void updateUser(User user) {
        User userFromDb = userRepo.findById(user.getId()).orElseThrow(() -> new RuntimeException("User with id " + user.getId() + " not found."));

        if (user.getName() != null) {
            userFromDb.setName(user.getName());
        }
        if (user.getEmail() != null) {
            userFromDb.setEmail(user.getEmail());
        }
        if (user.getPhone() != null && user.getPhone().matches("\\d{10}")) {   // "\\d{10}" is a regex for phone digits validation.
            userFromDb.setPhone(user.getPhone());
        }
        if (user.getAddress() != null) {
            userFromDb.setAddress(user.getAddress());
        }
        if (user.getPassword() != null) {
            userFromDb.setPassword(user.getPassword());
        }

        userRepo.save(userFromDb);
    }

    public void deleteUser(Long id) {
        if (!userRepo.existsById(id)) {
            throw new RuntimeException("User with id " + id + " not found.");
        }
        userRepo.deleteById(id);
    }

    public User verifyUser(String email, String password) {
        Optional<User> optionalUser = userRepo.findByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Invalid email address entered");
        }

        User user = optionalUser.get();

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password entered");
        }

        return user;
    }
}
