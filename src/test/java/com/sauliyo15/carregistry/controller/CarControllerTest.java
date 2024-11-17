package com.sauliyo15.carregistry.controller;

import com.sauliyo15.carregistry.controller.dtos.CarResponse;
import com.sauliyo15.carregistry.controller.mappers.CarMapper;
import com.sauliyo15.carregistry.model.Car;
import com.sauliyo15.carregistry.service.CarService;
import com.sauliyo15.carregistry.service.impl.JwtService;
import com.sauliyo15.carregistry.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CarController.class)
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @MockBean
    private CarMapper carMapper;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserServiceImpl userService;


    @Test
    @WithMockUser(username = "test", password = "test", roles = "CLIENT")
    void getCars_test() throws Exception {

        //Given
        Car car1 = new Car();
        car1.setModel("A3");

        Car car2 = new Car();
        car2.setModel("A4");

        List<Car> carList = List.of(car1, car2);

        CarResponse carResponse1 = new CarResponse();
        carResponse1.setModel("A3");

        CarResponse carResponse2 = new CarResponse();
        carResponse2.setModel("A4");

        List<CarResponse> carResponseList = List.of(carResponse1, carResponse2);

        CompletableFuture<List<Car>> completableCarList = CompletableFuture.completedFuture(carList);

        //When
        when(carService.getCars()).thenReturn(completableCarList);
        when(carMapper.toCarListResponse(carList)).thenReturn(carResponseList);

        //Then
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].model").value("A3"))
                .andExpect(jsonPath("$[1].model").value("A4"));
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "CLIENT")
    void getCars_test_ko() throws Exception {

        //Given

        //When
        when(carService.getCars()).thenThrow(new RuntimeException("Error al obtener coches"));

        //Then
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/cars"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "CLIENT")
    void getCarById_test() throws Exception {

        //Given
        Car car = new Car();
        car.setModel("A3");

        CarResponse carResponse = new CarResponse();
        carResponse.setModel("A3");

        //When
        when(carService.getCarById(1)).thenReturn(car);
        when(carMapper.toCarResponse(car)).thenReturn(carResponse);

        //Then
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/cars/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.model").value("A3"));
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "CLIENT")
    void getCarById_test_ko() throws Exception {

        //Given

        //When
        when(carService.getCarById(1)).thenThrow(new RuntimeException("Error al obtener el coche"));

        //Then
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/cars/1"))
                .andExpect(status().isNotFound());
    }
}


