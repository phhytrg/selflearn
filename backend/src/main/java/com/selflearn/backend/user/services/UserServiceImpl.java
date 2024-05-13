package com.selflearn.backend.user.services;

import com.selflearn.backend.user.UserRepository;
import com.selflearn.backend.user.models.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.FetchNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User fetchUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new FetchNotFoundException("User Not Found with id: " + id, null));
    }

    @Override
    public User fetchUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getReferenceById(UUID userId) {
        return userRepository.getReferenceById(userId);
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String current = authentication.getName();
        return userRepository.findByEmail(current);
    }
}
