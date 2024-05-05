package com.selflearn.backend.security.services.impl;

import com.selflearn.backend.security.UserDetailsImpl;
import com.selflearn.backend.user.models.User;
import com.selflearn.backend.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.fetchUserByEmail(email);
        return UserDetailsImpl.build(user);
    }
}
