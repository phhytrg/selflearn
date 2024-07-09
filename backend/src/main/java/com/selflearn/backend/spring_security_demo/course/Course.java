package com.selflearn.backend.spring_security_demo.course;

import com.selflearn.backend.user.models.User;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class Course {
    private UUID id;
    private String name;
    private List<User> users;
}
