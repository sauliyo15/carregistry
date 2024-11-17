package com.sauliyo15.carregistry.controller.mapper;

import com.sauliyo15.carregistry.controller.dtos.CarRequest;
import com.sauliyo15.carregistry.controller.dtos.CarResponse;
import com.sauliyo15.carregistry.controller.mappers.CarMapper;
import com.sauliyo15.carregistry.model.Brand;
import com.sauliyo15.carregistry.model.Car;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CarMapperTest {

    @InjectMocks
    private CarMapper carMapper;


    @Test
    void toCarResponse_test() {

        //Given
        Brand brand = new Brand();
        brand.setName("Audi");

        Car car = new Car();
        car.setId(1);
        car.setBrand(brand);
        car.setModel("A4");
        car.setMilleage(1000);
        car.setPrice(25000);
        car.setYear(2022);
        car.setDescription("Deportivo con extras");
        car.setColour("Azul eléctrico");
        car.setFuelType("Gasolina");
        car.setNumDoors(3);

        CarResponse carResponse = new CarResponse();
        carResponse.setId(1);
        carResponse.setBrand(brand.getName());
        carResponse.setModel("A4");
        carResponse.setMilleage(1000);
        carResponse.setPrice(25000);
        carResponse.setYear(2022);
        carResponse.setDescription("Deportivo con extras");
        carResponse.setColour("Azul eléctrico");
        carResponse.setFuelType("Gasolina");
        carResponse.setNumDoors(3);

        //When
        CarResponse result = carMapper.toCarResponse(car);

        //Then
        assertEquals(result.getId(), carResponse.getId());
        assertEquals(result.getBrand(), carResponse.getBrand());
        assertEquals(result.getModel(), carResponse.getModel());
        assertEquals(result.getMilleage(), carResponse.getMilleage());
        assertEquals(result.getPrice(), carResponse.getPrice());
        assertEquals(result.getYear(), carResponse.getYear());
        assertEquals(result.getDescription(), carResponse.getDescription());
        assertEquals(result.getColour(), carResponse.getColour());
        assertEquals(result.getFuelType(), carResponse.getFuelType());
        assertEquals(result.getNumDoors(), carResponse.getNumDoors());
    }

    @Test
    void toCar_test() {

        //Given
        Brand brand = new Brand();
        brand.setName("Audi");

        CarRequest carRequest = new CarRequest();
        carRequest.setBrand("Audi");
        carRequest.setModel("A4");
        carRequest.setMilleage(1000);
        carRequest.setPrice(25000);
        carRequest.setYear(2022);
        carRequest.setDescription("Deportivo con extras");
        carRequest.setColour("Azul eléctrico");
        carRequest.setFuelType("Gasolina");
        carRequest.setNumDoors(3);

        Car car = new Car();
        car.setBrand(brand);
        car.setModel("A4");
        car.setMilleage(1000);
        car.setPrice(25000);
        car.setYear(2022);
        car.setDescription("Deportivo con extras");
        car.setColour("Azul eléctrico");
        car.setFuelType("Gasolina");
        car.setNumDoors(3);

        //When
        Car result = carMapper.toCar(carRequest);

        //Then
        assertEquals(result.getBrand(), car.getBrand());
        assertEquals(result.getModel(), car.getModel());
        assertEquals(result.getMilleage(), car.getMilleage());
        assertEquals(result.getPrice(), car.getPrice());
        assertEquals(result.getYear(), car.getYear());
        assertEquals(result.getDescription(), car.getDescription());
        assertEquals(result.getColour(), car.getColour());
        assertEquals(result.getFuelType(), car.getFuelType());
        assertEquals(result.getNumDoors(), car.getNumDoors());
    }

    @Test
    void toCarListResponse_test() {

        //Given
        Brand brand = new Brand();
        brand.setName("Audi");

        Car car = new Car();
        car.setId(1);
        car.setBrand(brand);
        car.setModel("A4");
        car.setMilleage(1000);
        car.setPrice(25000);
        car.setYear(2022);
        car.setDescription("Deportivo con extras");
        car.setColour("Azul eléctrico");
        car.setFuelType("Gasolina");
        car.setNumDoors(3);

        Car car2 = new Car();
        car2.setId(2);
        car2.setBrand(brand);
        car2.setModel("A4");
        car2.setMilleage(1000);
        car2.setPrice(25000);
        car2.setYear(2022);
        car2.setDescription("Deportivo con extras");
        car2.setColour("Azul eléctrico");
        car2.setFuelType("Gasolina");
        car2.setNumDoors(3);

        List<Car> carList = List.of(car, car2);

        CarResponse carResponse = new CarResponse();
        carResponse.setId(1);
        carResponse.setBrand(brand.getName());
        carResponse.setModel("A4");
        carResponse.setMilleage(1000);
        carResponse.setPrice(25000);
        carResponse.setYear(2022);
        carResponse.setDescription("Deportivo con extras");
        carResponse.setColour("Azul eléctrico");
        carResponse.setFuelType("Gasolina");
        carResponse.setNumDoors(3);

        CarResponse carResponse2 = new CarResponse();
        carResponse2.setId(2);
        carResponse2.setBrand(brand.getName());
        carResponse2.setModel("A4");
        carResponse2.setMilleage(1000);
        carResponse2.setPrice(25000);
        carResponse2.setYear(2022);
        carResponse2.setDescription("Deportivo con extras");
        carResponse2.setColour("Azul eléctrico");
        carResponse2.setFuelType("Gasolina");
        carResponse2.setNumDoors(3);

        List<CarResponse> carResponseList = List.of(carResponse, carResponse2);

        //When
        List<CarResponse> result = carMapper.toCarListResponse(carList);

        //Then
        assertEquals(result, carResponseList);
    }

    @Test
    void toCarList_test() {

        //Given
        CarRequest carRequest = new CarRequest();
        carRequest.setBrand("Audi");
        carRequest.setModel("A4");
        carRequest.setMilleage(1000);
        carRequest.setPrice(25000);
        carRequest.setYear(2022);
        carRequest.setDescription("Deportivo con extras");
        carRequest.setColour("Azul eléctrico");
        carRequest.setFuelType("Gasolina");
        carRequest.setNumDoors(3);

        CarRequest carRequest2 = new CarRequest();
        carRequest2.setBrand("Audi");
        carRequest2.setModel("A4");
        carRequest2.setMilleage(1000);
        carRequest2.setPrice(25000);
        carRequest2.setYear(2022);
        carRequest2.setDescription("Deportivo con extras");
        carRequest2.setColour("Azul eléctrico");
        carRequest2.setFuelType("Gasolina");
        carRequest2.setNumDoors(3);

        List<CarRequest> carRequestList = List.of(carRequest, carRequest2);

        Brand brand = new Brand();
        brand.setName("Audi");

        Car car = new Car();
        car.setBrand(brand);
        car.setModel("A4");
        car.setMilleage(1000);
        car.setPrice(25000);
        car.setYear(2022);
        car.setDescription("Deportivo con extras");
        car.setColour("Azul eléctrico");
        car.setFuelType("Gasolina");
        car.setNumDoors(3);

        Car car2 = new Car();
        car2.setBrand(brand);
        car2.setModel("A4");
        car2.setMilleage(1000);
        car2.setPrice(25000);
        car2.setYear(2022);
        car2.setDescription("Deportivo con extras");
        car2.setColour("Azul eléctrico");
        car2.setFuelType("Gasolina");
        car2.setNumDoors(3);

        List<Car> carList = List.of(car, car2);

        //When
        List<Car> result = carMapper.toCarList(carRequestList);

        //Then
        assertEquals(result, carList);
    }
}
