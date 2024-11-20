package com.sauliyo15.carregistry.service.impl;

import com.sauliyo15.carregistry.controller.dtos.JwtResponse;
import com.sauliyo15.carregistry.controller.dtos.LoginRequest;
import com.sauliyo15.carregistry.entity.UserEntity;
import com.sauliyo15.carregistry.model.User;
import com.sauliyo15.carregistry.repository.UserRepository;
import com.sauliyo15.carregistry.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public JwtResponse signup(User request) throws BadRequestException {
        var user = UserEntity
                .builder()
                .name(request.getName())
                .mail(request.getMail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("ROLE_CLIENT")
                .build();
        user = userService.addUser(user);
        var jwt = jwtService.generateToken(user);
        return JwtResponse.builder().token(jwt).build();
    }

    public JwtResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getMail(), request.getPassword())
        );
        var user = userRepository.findByMail(request.getMail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        var jwt = jwtService.generateToken(user);
        return JwtResponse.builder().token(jwt).build();
    }
}
