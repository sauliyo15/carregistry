package com.sauliyo15.carregistry.service.impl;

import com.sauliyo15.carregistry.entity.BrandEntity;
import com.sauliyo15.carregistry.model.Brand;
import com.sauliyo15.carregistry.repository.BrandRepository;
import com.sauliyo15.carregistry.service.converters.BrandConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BrandServiceImplTest {

    @InjectMocks
    private BrandServiceImpl brandService;

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private BrandConverter brandConverter;


    @Test
    void getBrands_test() throws Exception {

        //Given
        int brandId = 1;

        List<BrandEntity> brandEntityList = new ArrayList<>();
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setId(brandId);
        brandEntityList.add(brandEntity);

        List<Brand> brandList = new ArrayList<>();
        Brand brand = new Brand();
        brand.setId(brandId);
        brandList.add(brand);

        //When
        when(brandRepository.findAll()).thenReturn(brandEntityList);
        when(brandConverter.toBrandList(brandEntityList)).thenReturn(brandList);

        //Then
        CompletableFuture<List<Brand>> result = brandService.getBrands();
        assertEquals(brandList, result.get());
    }

    @Test
    void getBrands_test_ko() {

        // Given
        List<BrandEntity> emptyBrandEntityList = new ArrayList<>();

        // When
        when(brandRepository.findAll()).thenReturn(emptyBrandEntityList);

        // Then
        Exception exception = assertThrows(Exception.class, () -> brandService.getBrands().get());
        assertEquals("No brands found", exception.getMessage());
    }

    @Test
    void getBrandById_test() throws Exception {

        //Given
        int brandId = 1;

        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setId(brandId);

        Brand brand = new Brand();
        brand.setId(brandId);

        //When
        when(brandRepository.findById(brandId)).thenReturn(Optional.of(brandEntity));
        when(brandConverter.toBrand(brandEntity)).thenReturn(brand);

        //Then
        Brand result = brandService.getBrandById(brandId);
        assertEquals(result, brand);
    }

    @Test
    void getBrandById_test_ko() {

        //Given
        int brandId = 1;

        //When
        when(brandRepository.findById(brandId)).thenReturn(Optional.empty());

        //Then
        Exception exception = assertThrows(Exception.class, () -> brandService.getBrandById(brandId));
        assertEquals("Brand not found with ID: " + brandId, exception.getMessage());
    }

    @Test
    void addBrand_test() throws Exception {

        //Given
        int brandId = 1;

        Brand brandToSave = new Brand();

        BrandEntity brandEntityToSave = new BrandEntity();

        BrandEntity brandEntitySaved = new BrandEntity();
        brandEntitySaved.setId(brandId);

        Brand brandSaved = new Brand();
        brandSaved.setId(brandId);

        //When
        when(brandConverter.toBrandEntity(brandToSave)).thenReturn(brandEntityToSave);
        when(brandRepository.save(brandEntityToSave)).thenReturn(brandEntitySaved);
        when(brandConverter.toBrand(brandEntitySaved)).thenReturn(brandSaved);

        //Then
        Brand result = brandService.addBrand(brandToSave);
        assertEquals(brandSaved, result);
    }

    @Test
    void addBrand_test_ko() {

        //Given
        Brand brand = new Brand();

        //When
        when(brandConverter.toBrandEntity(brand)).thenThrow(new RuntimeException("Conversion failed"));

        //Then
        Exception exception = assertThrows(Exception.class, () -> brandService.addBrand(brand));
        assertEquals("Conversion failed", exception.getMessage());
    }

    @Test
    void updateBrand_test() throws Exception{

        //Given
        int brandId = 1;

        BrandEntity brandEntityFounded = new BrandEntity();

        Brand brandToUpdate = new Brand();
        BrandEntity brandEntityToUpdate = new BrandEntity();
        brandEntityToUpdate.setId(brandId);

        BrandEntity brandEntityUpdated = new BrandEntity();
        Brand brandUpdated = new Brand();

        //When
        when(brandRepository.findById(brandId)).thenReturn(Optional.of(brandEntityFounded));
        when(brandConverter.toBrandEntity(brandToUpdate)).thenReturn(brandEntityToUpdate);
        when(brandRepository.save(brandEntityToUpdate)).thenReturn(brandEntityUpdated);
        when(brandConverter.toBrand(brandEntityUpdated)).thenReturn(brandUpdated);

        //Then
        Brand result = brandService.updateBrand(brandId, brandToUpdate);
        assertEquals(brandUpdated, result);
    }

    @Test
    void updateBrand_test_ko() {

        //Given
        int brandId = 1;

        Brand brandToUpdate = new Brand();

        //When
        when(brandRepository.findById(brandId)).thenReturn(Optional.empty());

        //Then
        Exception exception = assertThrows(Exception.class, () -> brandService.updateBrand(brandId, brandToUpdate));
        assertEquals("Brand not found with ID: " + brandId, exception.getMessage());
    }

    @Test
    void deleteBrand_test() throws Exception {

        //Given
        int brandId = 1;

        BrandEntity brandEntityFounded = new BrandEntity();

        //When
        when(brandRepository.findById(brandId)).thenReturn(Optional.of(brandEntityFounded));

        //Then
        brandService.deleteBrand(brandId);
    }

    @Test
    void deleteBrand_test_ko() {

        //Given
        int brandId = 1;

        //When
        when(brandRepository.findById(brandId)).thenReturn(Optional.empty());

        //Then
        Exception exception = assertThrows(Exception.class, () -> brandService.deleteBrand(brandId));
        assertEquals("Brand not found with ID: " + brandId, exception.getMessage());
    }

    @Test
    void getBrandByName_test() throws Exception {

        //Given
        String brandName = "BMW";

        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setName(brandName);

        Brand brand = new Brand();
        brand.setName(brandName);

        //When
        when(brandRepository.findByName(brandName)).thenReturn(Optional.of(brandEntity));
        when(brandConverter.toBrand(brandEntity)).thenReturn(brand);

        //Then
        Brand result = brandService.getBrandByName(brandName);
        assertEquals(result, brand);
    }

    @Test
    void getBrandByName_test_ko() {

        //Given
        String brandName = "BMW";

        //When
        when(brandRepository.findByName(brandName)).thenReturn(Optional.empty());

        //Then
        Exception exception = assertThrows(Exception.class, () -> brandService.getBrandByName(brandName));
        assertEquals("Brand not found with NAME: " + brandName, exception.getMessage());
    }
}
