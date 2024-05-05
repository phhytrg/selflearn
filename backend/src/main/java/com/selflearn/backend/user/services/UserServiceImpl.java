package com.selflearn.backend.user.services;

import com.selflearn.backend.user.UserRepository;
import com.selflearn.backend.user.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    @Override
    public User fetchUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User Not Found with id: " + id));
    }
    @Override
    public User fetchUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User Not Found with email: " + email));
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getReferenceById(UUID userId) {
        return userRepository.getReferenceById(userId);
    }
}
