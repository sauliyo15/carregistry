package com.sauliyo15.carregistry.service.converters;

import com.sauliyo15.carregistry.entity.BrandEntity;
import com.sauliyo15.carregistry.model.Brand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class BrandConverterTest {

    @InjectMocks
    private BrandConverter brandConverter;


    @Test
    void toBrand_test() {

        //Given
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setId(1);
        brandEntity.setName("Audi");
        brandEntity.setWarranty(3);
        brandEntity.setCountry("Alemania");

        Brand brand = new Brand();
        brand.setId(1);
        brand.setName("Audi");
        brand.setWarranty(3);
        brand.setCountry("Alemania");

        //When
        Brand result = brandConverter.toBrand(brandEntity);

        //Then
        assertEquals(result.getId(), brand.getId());
        assertEquals(result.getName(), brand.getName());
        assertEquals(result.getWarranty(), brand.getWarranty());
        assertEquals(result.getCountry(), brand.getCountry());
    }

    @Test
    void toBrandEntity_test() {

        //Given
        Brand brand = new Brand();
        brand.setId(1);
        brand.setName("Audi");
        brand.setWarranty(3);
        brand.setCountry("Alemania");

        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setId(1);
        brandEntity.setName("Audi");
        brandEntity.setWarranty(3);
        brandEntity.setCountry("Alemania");

        //When
        BrandEntity result = brandConverter.toBrandEntity(brand);

        //Then
        assertEquals(result.getId(), brandEntity.getId());
        assertEquals(result.getName(), brandEntity.getName());
        assertEquals(result.getWarranty(), brandEntity.getWarranty());
        assertEquals(result.getCountry(), brandEntity.getCountry());
    }

    @Test
    void toBrandList_test() {

        // Given
        BrandEntity brandEntity1 = new BrandEntity();
        brandEntity1.setId(1);
        brandEntity1.setName("Audi");
        brandEntity1.setWarranty(3);
        brandEntity1.setCountry("Alemania");

        BrandEntity brandEntity2 = new BrandEntity();
        brandEntity2.setId(2);
        brandEntity2.setName("BMW");
        brandEntity2.setWarranty(5);
        brandEntity2.setCountry("Alemania");

        List<BrandEntity> brandEntityList = new ArrayList<>();
        brandEntityList.add(brandEntity1);
        brandEntityList.add(brandEntity2);

        Brand brand1 = new Brand();
        brand1.setId(1);
        brand1.setName("Audi");
        brand1.setWarranty(3);
        brand1.setCountry("Alemania");

        Brand brand2 = new Brand();
        brand2.setId(2);
        brand2.setName("BMW");
        brand2.setWarranty(5);
        brand2.setCountry("Alemania");

        List<Brand> brandList = new ArrayList<>();
        brandList.add(brand1);
        brandList.add(brand2);

        // When
        List<Brand> result = brandConverter.toBrandList(brandEntityList);

        // Then
        assertEquals(result, brandList);
        }


}
