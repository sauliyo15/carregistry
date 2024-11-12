package com.sauliyo15.carregistry.service.converters;

import com.sauliyo15.carregistry.entity.BrandEntity;
import com.sauliyo15.carregistry.model.Brand;
import com.sauliyo15.carregistry.model.Car;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class BrandConverter {
    
    public Brand toBrand(BrandEntity brandEntity) {
        Brand brand = new Brand();
        brand.setId(brandEntity.getId());
        brand.setName(brandEntity.getName());
        brand.setWarranty(brandEntity.getWarranty());
        brand.setCountry(brandEntity.getCountry());
        return brand;
    }

    public BrandEntity toBrandEntity(Brand brand) {
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setId(brand.getId());
        brandEntity.setName(brand.getName());
        brandEntity.setWarranty(brand.getWarranty());
        brandEntity.setCountry(brand.getCountry());
        return brandEntity;
    }

    public List<Brand> toBrandList(List<BrandEntity> brands) {
        return brands.stream().map(this::toBrand).toList();
    }
}
