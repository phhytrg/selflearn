package com.selflearn.backend.user;

import com.selflearn.backend.user.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public User fetchUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User Not Found with id: " + id));
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public List<User> fetchAllUsers() {
        return userRepository.findAll();
    }
}
