package com.abi.agro_back.service.impl;

import com.abi.agro_back.collection.User;
import com.abi.agro_back.exception.ResourceNotFoundException;
import com.abi.agro_back.repository.UserRepository;
import com.abi.agro_back.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {

        return userRepository.save(user);
    }

    @Override
    public User getUserById(String userId) {

        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User is not exists with given id : " + userId));
    }

    @Override
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    @Override
    public User updateUser(String userId, User updatedUser) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User is not exists with given id: " + userId)
        );

        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setPhone(updatedUser.getPhone());
        user.setEmail(updatedUser.getEmail());
        user.setPassword(updatedUser.getPassword());
//        user.setUsername(updatedUser.getUsername());

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(String userId) {

       User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User is not exists with given id : " + userId));

       userRepository.deleteById(userId);
    }
}
