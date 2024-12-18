package com.sauliyo15.carregistry.controller;

import com.sauliyo15.carregistry.controller.dtos.BrandRequest;
import com.sauliyo15.carregistry.controller.dtos.BrandResponse;
import com.sauliyo15.carregistry.controller.dtos.CarRequest;
import com.sauliyo15.carregistry.controller.dtos.CarResponse;
import com.sauliyo15.carregistry.controller.mappers.BrandMapper;
import com.sauliyo15.carregistry.model.Brand;
import com.sauliyo15.carregistry.model.Car;
import com.sauliyo15.carregistry.service.BrandService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BrandController.class)
public class BrandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private BrandService brandService;

    @MockBean
    private BrandMapper brandMapper;

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
    void addBrand_test() throws Exception {

        //Given
        BrandRequest brandRequest = new BrandRequest();
        brandRequest.setName("Audi");

        Brand brandToAdd = new Brand();
        brandToAdd.setName("Audi");

        Brand brandAdded = new Brand();
        brandAdded.setId(1);
        brandAdded.setName("Audi");

        BrandResponse brandResponse = new BrandResponse();
        brandResponse.setId(1);
        brandResponse.setName("Audi");

        //When
        when(brandMapper.toBrand(brandRequest)).thenReturn(brandToAdd);
        when(brandService.addBrand(brandToAdd)).thenReturn(brandAdded);
        when(brandMapper.toBrandResponse(brandAdded)).thenReturn(brandResponse);

        //Then
        mockMvc.perform(MockMvcRequestBuilders.post("/brands")
                        .with(csrf()) // CSRF token for POST requests
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Audi\"}")) // Simulate JSON body
                .andExpect(status().isOk()) // Verify status 200
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Audi"));
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "VENDOR")
    void addBrand_test_ko() throws Exception {

        //Given
        BrandRequest brandRequest = new BrandRequest();
        brandRequest.setName("Audi");

        Brand brandToAdd = new Brand();
        brandToAdd.setName("Audi");

        //When
        when(brandMapper.toBrand(brandRequest)).thenReturn(brandToAdd);
        when(brandService.addBrand(brandToAdd)).thenThrow(new RuntimeException("Error adding brand"));

        //Then
        mockMvc.perform(MockMvcRequestBuilders.post("/brands")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Audi\"}"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "VENDOR")
    void updateBrand_test() throws Exception {

        //Given
        int brandId = 1;

        BrandRequest brandRequest = new BrandRequest();
        brandRequest.setName("AUDI");

        Brand brandToUpdate = new Brand();
        brandToUpdate.setName("AUDI");

        Brand brandUpdated = new Brand();
        brandUpdated.setId(brandId);
        brandUpdated.setName("AUDI");

        BrandResponse brandResponse = new BrandResponse();
        brandResponse.setId(brandId);
        brandResponse.setName("AUDI");

        //When
        when(brandMapper.toBrand(brandRequest)).thenReturn(brandToUpdate);
        when(brandService.updateBrand(brandId, brandToUpdate)).thenReturn(brandUpdated);
        when(brandMapper.toBrandResponse(brandUpdated)).thenReturn(brandResponse);

        //Then
        mockMvc.perform(MockMvcRequestBuilders.put("/brands/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"AUDI\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("AUDI"));
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "VENDOR")
    void updateBrand_test_ko() throws Exception {

        //Given
        int brandId = 1;

        BrandRequest brandRequest = new BrandRequest();
        brandRequest.setName("AUDI");

        Brand brandToUpdate = new Brand();
        brandToUpdate.setName("AUDI");

        //When
        when(brandMapper.toBrand(brandRequest)).thenReturn(brandToUpdate);
        when(brandService.updateBrand(brandId, brandToUpdate)).thenThrow(new RuntimeException("Error updating brand"));

        //Then
        mockMvc.perform(MockMvcRequestBuilders.put("/brands/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"AUDI\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "VENDOR")
    void deleteBrand_test() throws Exception {

        //Given
        int brandId = 1;

        //When
        doNothing().when(brandService).deleteBrand(brandId);

        //Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/brands/" + brandId).with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "VENDOR")
    void deleteBrand_test_ko() throws Exception {

        //Given
        int brandId = 1;

        //When
        doThrow(new RuntimeException("Brand not found")).when(brandService).deleteBrand(brandId);

        //Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/brands/" + brandId).with(csrf()))
                .andExpect(status().isNotFound());
    }
}
