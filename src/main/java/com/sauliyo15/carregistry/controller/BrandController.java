package com.sauliyo15.carregistry.controller;

import com.sauliyo15.carregistry.controller.dtos.BrandRequest;
import com.sauliyo15.carregistry.controller.dtos.BrandResponse;
import com.sauliyo15.carregistry.controller.mappers.BrandMapper;
import com.sauliyo15.carregistry.model.Brand;
import com.sauliyo15.carregistry.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Autowired
    private BrandMapper brandMapper;


    @GetMapping("/brands")
    @Operation(summary = "Get all brands", description = "Returns a list of all available brands.")
    public ResponseEntity<?> getBrands() {
        log.info("Fetching brands");
        try {
            CompletableFuture<List<Brand>> brandList = brandService.getBrands();
            List<BrandResponse> brandResponseList = brandMapper.toBrandListResponse(brandList.get());
            return ResponseEntity.ok(brandResponseList);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/brands/{id}")
    @Operation(summary = "Get a brand by ID", description = "Returns the details of a specific brand given its ID.")
    public ResponseEntity<?> getBrandById(@PathVariable Integer id) {
        log.info("Fetching brand with ID: {}", id);
        try {
            return ResponseEntity.ok(brandMapper.toBrandResponse(brandService.getBrandById(id)));
        } catch (Exception e) {
            log.error("Error fetching brand with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/brands")
    @Operation(summary = "Add new brand", description = "Allows add new brand by providing the details in the body of the request.")
    public ResponseEntity<?> addBrand(@RequestBody BrandRequest brandRequest) {
        log.info("Adding new car with details: {}", brandRequest);
        try {
            return ResponseEntity.ok(brandMapper.toBrandResponse(brandService.addBrand(brandMapper.toBrand(brandRequest))));
        }
        catch (Exception e) {
            log.error("Error adding brand", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/brands/{id}")
    @Operation(summary = "Update a brand", description = "Updates the details of an existing brand given its ID.")
    public ResponseEntity<?> updateBrand(@PathVariable Integer id, @RequestBody BrandRequest brandRequest) {
        log.info("Updating brand with ID: {}", id);
        try {
            return ResponseEntity.ok(brandMapper.toBrandResponse(brandService.updateBrand(id, brandMapper.toBrand(brandRequest))));
        } catch (Exception e) {
            log.error("Error updating brand with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/brands/{id}")
    @Operation(summary = "Delete a brand", description = "Delete a brand given its ID.")
    public ResponseEntity<?> deleteBrand(@PathVariable Integer id) {
        log.info("Deleting brand with ID: {}", id);
        try {
            brandService.deleteBrand(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error deleting brand with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
