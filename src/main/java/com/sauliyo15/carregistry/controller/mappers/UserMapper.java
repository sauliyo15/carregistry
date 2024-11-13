package com.sauliyo15.carregistry.controller.mappers;

import com.sauliyo15.carregistry.controller.dtos.SignRequest;
import com.sauliyo15.carregistry.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toUser(SignRequest signRequest) {
        User user = new User();
        user.setName(signRequest.getName());
        user.setMail(signRequest.getMail());
        user.setPassword(signRequest.getPassword());
        return user;
    }
}
