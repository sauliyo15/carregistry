package com.sauliyo15.carregistry.service.converters;

import com.sauliyo15.carregistry.entity.BrandEntity;
import com.sauliyo15.carregistry.entity.CarEntity;
import com.sauliyo15.carregistry.model.Brand;
import com.sauliyo15.carregistry.model.Car;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarConverterTest {

    @InjectMocks
    private CarConverter carConverter;

    @Mock
    private BrandConverter brandConverter;


    @Test
    void toCar_test() {

        //Given
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setName("Audi");

        CarEntity carEntity = new CarEntity();
        carEntity.setId(1);
        carEntity.setBrand(brandEntity);
        carEntity.setModel("A4");
        carEntity.setMilleage(1000);
        carEntity.setPrice(25000);
        carEntity.setYear(2022);
        carEntity.setDescription("Deportivo con extras");
        carEntity.setColour("Azul eléctrico");
        carEntity.setFuelType("Gasolina");
        carEntity.setNumDoors(3);

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

        //When
        when(brandConverter.toBrand(brandEntity)).thenReturn(brand);

        //Then
        Car result = carConverter.toCar(carEntity);
        assertEquals(result.getId(), car.getId());
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
    void toCarEntity_test() {

        //Given
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

        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setName("Audi");

        CarEntity carEntity = new CarEntity();
        carEntity.setBrand(brandEntity);
        carEntity.setModel("A4");
        carEntity.setMilleage(1000);
        carEntity.setPrice(25000);
        carEntity.setYear(2022);
        carEntity.setDescription("Deportivo con extras");
        carEntity.setColour("Azul eléctrico");
        carEntity.setFuelType("Gasolina");
        carEntity.setNumDoors(3);

        //When
        when(brandConverter.toBrandEntity(brand)).thenReturn(brandEntity);

        //Then
        CarEntity result = carConverter.toCarEntity(car);
        assertEquals(result.getBrand(), carEntity.getBrand());
        assertEquals(result.getModel(), carEntity.getModel());
        assertEquals(result.getMilleage(), carEntity.getMilleage());
        assertEquals(result.getPrice(), carEntity.getPrice());
        assertEquals(result.getYear(), carEntity.getYear());
        assertEquals(result.getDescription(), carEntity.getDescription());
        assertEquals(result.getColour(), carEntity.getColour());
        assertEquals(result.getFuelType(), carEntity.getFuelType());
        assertEquals(result.getNumDoors(), carEntity.getNumDoors());
    }

    @Test
    void toCarList_test() {

        //Given
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setName("Audi");

        CarEntity carEntity = new CarEntity();
        carEntity.setId(1);
        carEntity.setBrand(brandEntity);
        carEntity.setModel("A4");
        carEntity.setMilleage(1000);
        carEntity.setPrice(25000);
        carEntity.setYear(2022);
        carEntity.setDescription("Deportivo con extras");
        carEntity.setColour("Azul eléctrico");
        carEntity.setFuelType("Gasolina");
        carEntity.setNumDoors(3);

        CarEntity carEntity2 = new CarEntity();
        carEntity2.setId(2);
        carEntity2.setBrand(brandEntity);
        carEntity2.setModel("A4");
        carEntity2.setMilleage(1000);
        carEntity2.setPrice(25000);
        carEntity2.setYear(2022);
        carEntity2.setDescription("Deportivo con extras");
        carEntity2.setColour("Azul eléctrico");
        carEntity2.setFuelType("Gasolina");
        carEntity2.setNumDoors(3);

        List<CarEntity> carEntityList = List.of(carEntity, carEntity2);

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

        //When
        when(brandConverter.toBrand(brandEntity)).thenReturn(brand);

        //Then
        List<Car> result = carConverter.toCarList(carEntityList);
        assertEquals(result, carList);
    }

}
