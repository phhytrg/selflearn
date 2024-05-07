package com.selflearn.backend.user.services;

import com.selflearn.backend.user.models.User;

import java.util.UUID;

public interface UserService {
    User fetchUserById(UUID id);
    User fetchUserByEmail(String email);
    User saveUser(User user);
    User getReferenceById(UUID userId);
    User getCurrentUser();
}
