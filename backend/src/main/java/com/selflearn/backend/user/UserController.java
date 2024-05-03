package com.selflearn.backend.user;

import com.selflearn.backend.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${apiPrefix}/${apiVersion}/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

}
