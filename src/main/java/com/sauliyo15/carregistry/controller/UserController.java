package com.sauliyo15.carregistry.controller;

import com.sauliyo15.carregistry.exception.UserNotFoundException;
import com.sauliyo15.carregistry.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Slf4j
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/users/{id}/addImage")
    @PreAuthorize("hasAnyRole('CLIENT','VENDOR')")
    @Operation(summary = "Save image user", description = "Save an image on existing user")
    public ResponseEntity<String> addImage(@PathVariable Integer id, @RequestParam("imageFile") MultipartFile imageFile) {

        try {
            String filename = Objects.requireNonNull(imageFile.getOriginalFilename()).toLowerCase();
            if (!(filename.endsWith(".png") || filename.endsWith(".jpg"))) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid file type. Only .png and .jpg are allowed.");
            }
            userService.addUserImage(id, imageFile);
            return ResponseEntity.ok("Image successfully saved.");
        }
        catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/users/{id}/getImage")
    @PreAuthorize("hasAnyRole('CLIENT','VENDOR')")
    @Operation(summary = "Get image user", description = "Get an image of existing user")
    public ResponseEntity<byte[]> getImage (@PathVariable Integer id) {

        try {
            byte[] imageBytes = userService.getUserImage(id);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        }
        catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
