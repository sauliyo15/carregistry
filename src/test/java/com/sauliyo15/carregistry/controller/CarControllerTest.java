package com.sauliyo15.carregistry.controller;

import com.sauliyo15.carregistry.controller.dtos.BrandRequest;
import com.sauliyo15.carregistry.controller.dtos.BrandResponse;
import com.sauliyo15.carregistry.controller.dtos.CarRequest;
import com.sauliyo15.carregistry.controller.dtos.CarResponse;
import com.sauliyo15.carregistry.controller.mappers.CarMapper;
import com.sauliyo15.carregistry.model.Brand;
import com.sauliyo15.carregistry.model.Car;
import com.sauliyo15.carregistry.service.CarService;
import com.sauliyo15.carregistry.service.impl.JwtService;
import com.sauliyo15.carregistry.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CarController.class)
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private CarService carService;

    @MockBean
    private CarMapper carMapper;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserServiceImpl userService;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }


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

    @Test
    @WithMockUser(username = "test", password = "test", roles = "VENDOR")
    void addCar_test() throws Exception {

        //Given
        CarRequest carRequest = new CarRequest();
        carRequest.setModel("A3");

        Car carToAdd = new Car();
        carToAdd.setModel("A3");

        Car carAdded = new Car();
        carAdded.setId(1);
        carAdded.setModel("A3");

        CarResponse carResponse = new CarResponse();
        carResponse.setId(1);
        carResponse.setModel("A3");

        //When
        when(carMapper.toCar(carRequest)).thenReturn(carToAdd);
        when(carService.addCar(carToAdd)).thenReturn(carAdded);
        when(carMapper.toCarResponse(carAdded)).thenReturn(carResponse);

        //Then
        mockMvc.perform(MockMvcRequestBuilders.post("/cars")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"model\":\"A3\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.model").value("A3"));
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "VENDOR")
    void addCar_test_ko() throws Exception {

        //Given
        CarRequest carRequest = new CarRequest();
        carRequest.setModel("A3");

        Car carToAdd = new Car();
        carToAdd.setModel("A3");

        //When
        when(carMapper.toCar(carRequest)).thenReturn(carToAdd);
        when(carService.addCar(carToAdd)).thenThrow(new RuntimeException("Error adding car"));

        //Then
        mockMvc.perform(MockMvcRequestBuilders.post("/cars")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"model\":\"A3\"}"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "VENDOR")
    void updateCar_test() throws Exception {

        //Given
        int carId = 1;

        CarRequest carRequest = new CarRequest();
        carRequest.setModel("A3");

        Car carToUpdate = new Car();
        carToUpdate.setModel("A3");

        Car carUpdated = new Car();
        carUpdated.setId(carId);
        carUpdated.setModel("A3");

        CarResponse carResponse = new CarResponse();
        carResponse.setId(carId);
        carResponse.setModel("A3");

        //When
        when(carMapper.toCar(carRequest)).thenReturn(carToUpdate);
        when(carService.updateCar(carId, carToUpdate)).thenReturn(carUpdated);
        when(carMapper.toCarResponse(carUpdated)).thenReturn(carResponse);

        //Then
        mockMvc.perform(MockMvcRequestBuilders.put("/cars/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"model\":\"A3\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.model").value("A3"));
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "VENDOR")
    void updateCar_test_ko() throws Exception {

        //Given
        int carId = 1;

        CarRequest carRequest = new CarRequest();
        carRequest.setModel("A3");

        Car carToUpdate = new Car();
        carToUpdate.setModel("A3");

        //When
        when(carMapper.toCar(carRequest)).thenReturn(carToUpdate);
        when(carService.updateCar(carId, carToUpdate)).thenThrow(new RuntimeException("Error updating car"));

        //Then
        mockMvc.perform(MockMvcRequestBuilders.put("/cars/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"model\":\"A3\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "VENDOR")
    void deleteCar_test() throws Exception {

        //Given
        int carId = 1;

        //When
        doNothing().when(carService).deleteCar(carId);

        //Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/cars/" + carId).with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "VENDOR")
    void deleteCar_test_ko() throws Exception {

        //Given
        int carId = 1;

        //When
        doThrow(new RuntimeException("Car not found")).when(carService).deleteCar(carId);

        //Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/cars/" + carId).with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "VENDOR")
    void addCars_test() throws Exception {

        //Given
        CarRequest carRequest1 = new CarRequest();
        carRequest1.setModel("A3");

        CarRequest carRequest2 = new CarRequest();
        carRequest2.setModel("Q5");

        List<CarRequest> carRequestList = List.of(carRequest1, carRequest2);

        Car carToAdd1 = new Car();
        carToAdd1.setModel("A3");

        Car carToAdd2 = new Car();
        carToAdd2.setModel("Q5");

        List<Car> carListToAdd = List.of(carToAdd1, carToAdd2);

        Car carAdded1 = new Car();
        carAdded1.setModel("A3");

        Car carAdded2 = new Car();
        carAdded2.setModel("Q5");

        List<Car> carListAdded = List.of(carAdded1, carAdded2);

        CarResponse carResponse1 = new CarResponse();
        carResponse1.setModel("A3");

        CarResponse carResponse2 = new CarResponse();
        carResponse2.setModel("Q5");

        List<CarResponse> carResponseList = List.of(carResponse1, carResponse2);

        //When
        when(carMapper.toCarList(carRequestList)).thenReturn(carListToAdd);
        when(carService.addCars(carListToAdd)).thenReturn(CompletableFuture.completedFuture(carListAdded));
        when(carMapper.toCarListResponse(carListAdded)).thenReturn(carResponseList);

        //Then
        mockMvc.perform(MockMvcRequestBuilders.post("/carss")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{\"model\":\"A3\"},{\"model\":\"Q5\"}]"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].model").value("A3"))
                .andExpect(jsonPath("$[1].model").value("Q5"));
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "VENDOR")
    void addCars_test_ko() throws Exception {

        //Given
        CarRequest carRequest1 = new CarRequest();
        carRequest1.setModel("A3");

        CarRequest carRequest2 = new CarRequest();
        carRequest2.setModel("Q5");

        List<CarRequest> carRequestList = List.of(carRequest1, carRequest2);

        //When
        when(carMapper.toCarList(carRequestList)).thenReturn(List.of(new Car(), new Car()));
        when(carService.addCars(anyList())).thenThrow(new RuntimeException("Error adding cars"));

        //Then
        mockMvc.perform(MockMvcRequestBuilders.post("/carss")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{\"model\":\"A3\"},{\"model\":\"Q5\"}]"))
                .andExpect(status().isNotFound());
    }

}


