package com.sauliyo15.carregistry.service.impl;

import com.sauliyo15.carregistry.entity.BrandEntity;
import com.sauliyo15.carregistry.exception.BrandNotFoundException;
import com.sauliyo15.carregistry.exception.BrandsNotFoundException;
import com.sauliyo15.carregistry.model.Brand;
import com.sauliyo15.carregistry.repository.BrandRepository;
import com.sauliyo15.carregistry.service.BrandService;
import com.sauliyo15.carregistry.service.converters.BrandConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    private final BrandConverter brandConverter;


    public BrandServiceImpl(BrandRepository brandRepository, BrandConverter brandConverter) {
        this.brandRepository = brandRepository;
        this.brandConverter = brandConverter;
    }

    @Override
    @Async
    public CompletableFuture<List<Brand>> getBrands() {
        long starTime = System.currentTimeMillis();
        List<BrandEntity> brandEntityList = brandRepository.findAll();
        if (brandEntityList.isEmpty()) {
            throw new BrandsNotFoundException();
        }
        List<Brand> brandList = brandConverter.toBrandList(brandEntityList);
        long endTime = System.currentTimeMillis();
        log.info("Total time elapsed Getting: {}", endTime - starTime);
        return CompletableFuture.completedFuture(brandList);
    }

    @Override
    public Brand getBrandById(Integer id) {
        Optional<BrandEntity> brandEntityOptional = brandRepository.findById(id);
        BrandEntity brandEntity = brandEntityOptional.orElseThrow(() -> new BrandNotFoundException(id));
        return brandConverter.toBrand(brandEntity);
    }

    @Override
    public Brand addBrand(Brand brand) {
        BrandEntity brandEntity = brandConverter.toBrandEntity(brand);
        BrandEntity savedEntity = brandRepository.save(brandEntity);
        return brandConverter.toBrand(savedEntity);
    }

    @Override
    public Brand updateBrand(Integer id, Brand brand) {
        if (!brandRepository.existsById(id)) {
            throw new BrandNotFoundException(id);
        }
        BrandEntity brandEntity = brandConverter.toBrandEntity(brand);
        brandEntity.setId(id);
        return brandConverter.toBrand(brandRepository.save(brandEntity));
    }

    @Override
    public void deleteBrand(Integer id) {
        if (!brandRepository.existsById(id)) {
            throw new BrandNotFoundException(id);
        }
        brandRepository.deleteById(id);
    }

    @Override
    public Brand getBrandByName(String name) {
        Optional<BrandEntity> brandEntityOptional = brandRepository.findByName(name);
        BrandEntity brandEntity = brandEntityOptional.orElseThrow(() -> new BrandNotFoundException(name));
        return brandConverter.toBrand(brandEntity);
    }
}
