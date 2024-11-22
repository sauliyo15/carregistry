package com.sauliyo15.carregistry.service;

import com.sauliyo15.carregistry.entity.UserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {

    UserEntity addUser(UserEntity newUser);
    void addUserImage(Integer id, MultipartFile file) throws IOException;
    byte[] getUserImage(Integer id);
}
