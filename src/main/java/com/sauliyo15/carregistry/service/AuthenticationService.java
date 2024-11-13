package com.sauliyo15.carregistry.service;

import com.sauliyo15.carregistry.controller.dtos.JwtResponse;
import com.sauliyo15.carregistry.controller.dtos.LoginRequest;
import com.sauliyo15.carregistry.controller.dtos.SignRequest;
import com.sauliyo15.carregistry.model.User;

public interface AuthenticationService {

    JwtResponse signup (User user) throws Exception;

    JwtResponse login (LoginRequest request);
}
