package com.sauliyo15.carregistry.controller;

import com.sauliyo15.carregistry.controller.dtos.LoginRequest;
import com.sauliyo15.carregistry.controller.dtos.SignRequest;
import com.sauliyo15.carregistry.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    @PostMapping("/signup")
    @Operation(summary = "User Signup", description = "Registers a new user in the system.")
    public ResponseEntity<?> singup (@RequestBody SignRequest request) {
        log.info("Attempting to register a new user.");
        try {
            return ResponseEntity.ok(authenticationService.signup(request));
        }
        catch (Exception e) {
            log.error("Error during user registration", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Get a car by ID", description = "Returns the details of a specific car given its ID.")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        log.info("Attempting to authenticate user.");
        try {
            return ResponseEntity.ok(authenticationService.login(request));
        } catch (Exception e) {
            log.error("Error during user authentication", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
