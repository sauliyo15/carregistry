package com.sauliyo15.carregistry.service;

import com.sauliyo15.carregistry.controller.dtos.JwtResponse;
import com.sauliyo15.carregistry.controller.dtos.LoginRequest;
import com.sauliyo15.carregistry.controller.dtos.SignRequest;

public interface AuthenticationService {

    JwtResponse signup (SignRequest request) throws Exception;

    JwtResponse login (LoginRequest request);
}
