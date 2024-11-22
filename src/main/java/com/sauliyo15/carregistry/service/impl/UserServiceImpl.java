package com.sauliyo15.carregistry.service.impl;

import com.sauliyo15.carregistry.entity.UserEntity;
import com.sauliyo15.carregistry.exception.UserNotFoundException;
import com.sauliyo15.carregistry.repository.UserRepository;
import com.sauliyo15.carregistry.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public UserEntity addUser(UserEntity newUser) {
        return userRepository.save(newUser);
    }

    @Override
    public void addUserImage(Integer id, MultipartFile file) throws IOException {

        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        log.info("Saving user image...");
        userEntity.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
        userRepository.save(userEntity);
    }

    @Override
    public byte[] getUserImage(Integer id) {

        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        log.info("Fetching user image...");
        return Base64.getDecoder().decode(userEntity.getImage());
    }

    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByMail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
