package com.selflearn.backend.user;

import com.selflearn.backend.user.models.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findByEmail(String email);
}
