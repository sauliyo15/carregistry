package com.sauliyo15.carregistry.service.impl;

import com.sauliyo15.carregistry.entity.BrandEntity;
import com.sauliyo15.carregistry.model.Brand;
import com.sauliyo15.carregistry.repository.BrandRepository;
import com.sauliyo15.carregistry.service.BrandService;
import com.sauliyo15.carregistry.service.converters.BrandConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private BrandConverter brandConverter;

    @Override
    @Async
    public CompletableFuture<List<Brand>> getBrands() throws Exception {
        long starTime = System.currentTimeMillis();
        List<BrandEntity> brandEntityList = brandRepository.findAll();
        if (brandEntityList.isEmpty()) {
            throw new Exception("No brands found");
        }
        List<Brand> brandList = brandConverter.toBrandList(brandEntityList);
        long endTime = System.currentTimeMillis();
        log.info("Total time elapsed Getting: " + (endTime-starTime));
        return CompletableFuture.completedFuture(brandList);
    }


    @Override
    public Brand getBrandById(Integer id) throws Exception {
        Optional<BrandEntity> brandEntityOptional = brandRepository.findById(id);
        return brandConverter.toBrand(brandEntityOptional.orElseThrow(() -> new Exception("Brand not found with ID: " + id)));
    }

    @Override
    public Brand addBrand(Brand brand) throws Exception {
        return brandConverter.toBrand(brandRepository.save(brandConverter.toBrandEntity(brand)));
    }

    @Override
    public Brand updateBrand(Integer id, Brand brand) throws Exception {
        brandRepository.findById(id).orElseThrow(() -> new Exception("Brand not found with ID: " + id));
        BrandEntity brandEntity = brandConverter.toBrandEntity(brand);
        brandEntity.setId(id);
        return brandConverter.toBrand(brandRepository.save(brandEntity));
    }

    public void deleteBrand(Integer id) throws Exception {
        brandRepository.findById(id).orElseThrow(() -> new Exception("Brand not found with ID: " + id));
        brandRepository.deleteById(id);
    }
}
