package com.example.myserver.oauthserverpostgres.service;

import com.example.myserver.oauthserverpostgres.converter.UserDetailsConverter;
import com.example.myserver.oauthserverpostgres.repository.UserDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor(onConstructor = @_(@Autowired))
@Service("userDetailsService")
@Transactional
public class UserServiceImpl implements UserService {

    private final UserDetailRepository userDao;
    private final UserDetailsConverter userDetailsConverter;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        return userDao.findByUsername(name)
                .map(userDetailsConverter::convert)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private String passwordEncoderGenerator(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}