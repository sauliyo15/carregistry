package com.sauliyo15.carregistry.controller;

import com.sauliyo15.carregistry.controller.dtos.BrandRequest;
import com.sauliyo15.carregistry.controller.dtos.BrandResponse;
import com.sauliyo15.carregistry.controller.dtos.CarResponse;
import com.sauliyo15.carregistry.controller.mappers.BrandMapper;
import com.sauliyo15.carregistry.model.Brand;
import com.sauliyo15.carregistry.model.Car;
import com.sauliyo15.carregistry.service.BrandService;
import com.sauliyo15.carregistry.service.impl.JwtService;
import com.sauliyo15.carregistry.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BrandController.class)
public class BrandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BrandService brandService;

    @MockBean
    private BrandMapper brandMapper;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserServiceImpl userService;


    @Test
    @WithMockUser(username = "test", password = "test", roles = "VENDOR")
    void getBrands_test() throws Exception {

        //Given
        Brand brand1 = new Brand();
        brand1.setName("AUDI");

        Brand brand2 = new Brand();
        brand2.setName("BMW");

        List<Brand> brandList = List.of(brand1, brand2);

        BrandResponse brandResponse1 = new BrandResponse();
        brandResponse1.setName("AUDI");

        BrandResponse brandResponse2 = new BrandResponse();
        brandResponse2.setName("BMW");

        List<BrandResponse> brandResponseList = List.of(brandResponse1, brandResponse2);

        CompletableFuture<List<Brand>> completableBrandList = CompletableFuture.completedFuture(brandList);

        //When
        when(brandService.getBrands()).thenReturn(completableBrandList);
        when(brandMapper.toBrandListResponse(brandList)).thenReturn(brandResponseList);

        //Then
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/brands"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("AUDI"))
                .andExpect(jsonPath("$[1].name").value("BMW"));
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "VENDOR")
    void getBrands_test_ko() throws Exception {

        //Given

        //When
        when(brandService.getBrands()).thenThrow(new RuntimeException("Error al obtener marcas"));

        //Then
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/brands"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "VENDOR")
    void getBrandById_test() throws Exception {

        //Given
        Brand brand = new Brand();
        brand.setName("AUDI");

        BrandResponse brandResponse = new BrandResponse();
        brandResponse.setName("AUDI");

        //When
        when(brandService.getBrandById(1)).thenReturn(brand);
        when(brandMapper.toBrandResponse(brand)).thenReturn(brandResponse);

        //Then
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/brands/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("AUDI"));
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "VENDOR")
    void getBrandById_test_ko() throws Exception {

        //Given

        //When
        when(brandService.getBrandById(1)).thenThrow(new RuntimeException("Error al obtener la marca"));

        //Then
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/brands/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "VENDOR")
    void deleteBrand_success() throws Exception {

        //Given
        int brandId = 1;

        //When
        doNothing().when(brandService).deleteBrand(brandId);

        //Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/brands/" + brandId))
                .andExpect(status().isNoContent());

    }
}
