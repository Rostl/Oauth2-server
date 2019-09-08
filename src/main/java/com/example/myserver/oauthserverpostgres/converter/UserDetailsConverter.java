package com.example.myserver.oauthserverpostgres.converter;

import com.example.myserver.oauthserverpostgres.model.Role;
import com.example.myserver.oauthserverpostgres.model.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserDetailsConverter implements Converter<User, UserDetails> {

    @Override
    public UserDetails convert(User user) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRoles()
                        .stream()
                        .map(Role::getName)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()))
                .build();
    }

}