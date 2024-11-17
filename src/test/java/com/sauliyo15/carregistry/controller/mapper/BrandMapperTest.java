package com.sauliyo15.carregistry.controller.mapper;

import com.sauliyo15.carregistry.controller.dtos.BrandRequest;
import com.sauliyo15.carregistry.controller.dtos.BrandResponse;
import com.sauliyo15.carregistry.controller.mappers.BrandMapper;
import com.sauliyo15.carregistry.model.Brand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class BrandMapperTest {

    @InjectMocks
    private BrandMapper brandMapper;


    @Test
    void toBrandResponse_test() {

        //Given
        Brand brand = new Brand();
        brand.setId(1);
        brand.setName("Audi");
        brand.setWarranty(3);
        brand.setCountry("Alemania");

        BrandResponse brandResponse = new BrandResponse();
        brandResponse.setId(1);
        brandResponse.setName("Audi");
        brandResponse.setWarranty(3);
        brandResponse.setCountry("Alemania");

        //When
        BrandResponse result = brandMapper.toBrandResponse(brand);

        //Then
        assertEquals(result.getId(), brandResponse.getId());
        assertEquals(result.getName(), brandResponse.getName());
        assertEquals(result.getWarranty(), brandResponse.getWarranty());
        assertEquals(result.getCountry(), brandResponse.getCountry());
    }

    @Test
    void toBrand_test() {

        //Given
        BrandRequest brandRequest = new BrandRequest();
        brandRequest.setName("Audi");
        brandRequest.setWarranty(3);
        brandRequest.setCountry("Alemania");

        Brand brand = new Brand();
        brand.setName("Audi");
        brand.setWarranty(3);
        brand.setCountry("Alemania");

        //When
        Brand result = brandMapper.toBrand(brandRequest);

        //Then
        assertEquals(result.getName(), brand.getName());
        assertEquals(result.getWarranty(), brand.getWarranty());
        assertEquals(result.getCountry(), brand.getCountry());
    }

    @Test
    void toBrandListResponse_test() {

        //Given
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

        List<Brand> brandList = List.of(brand1, brand2);

        BrandResponse brandResponse1 = new BrandResponse();
        brandResponse1.setId(1);
        brandResponse1.setName("Audi");
        brandResponse1.setWarranty(3);
        brandResponse1.setCountry("Alemania");

        BrandResponse brandResponse2 = new BrandResponse();
        brandResponse2.setId(2);
        brandResponse2.setName("BMW");
        brandResponse2.setWarranty(5);
        brandResponse2.setCountry("Alemania");

        List<BrandResponse> brandResponseList = List.of(brandResponse1, brandResponse2);

        //When
        List<BrandResponse> result = brandMapper.toBrandListResponse(brandList);

        //Then
        assertEquals(result, brandResponseList);
    }

}
