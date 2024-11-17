package com.sauliyo15.carregistry.controller.mapper;

import com.sauliyo15.carregistry.controller.dtos.SignRequest;
import com.sauliyo15.carregistry.controller.mappers.UserMapper;
import com.sauliyo15.carregistry.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserMapperTest {

    @InjectMocks
    private UserMapper userMapper;

    @Test
    void toUser_test() {

        //Given
        SignRequest signRequest = new SignRequest();
        signRequest.setName("User");
        signRequest.setMail("user@ejemplo.com");
        signRequest.setPassword("user123");

        User user = new User();
        user.setName("User");
        user.setMail("user@ejemplo.com");
        user.setPassword("user123");

        //When
        User result = userMapper.toUser(signRequest);

        //Then
        assertEquals(result.getName(), user.getName());
        assertEquals(result.getMail(), user.getMail());
        assertEquals(result.getPassword(), user.getPassword());
    }
}
