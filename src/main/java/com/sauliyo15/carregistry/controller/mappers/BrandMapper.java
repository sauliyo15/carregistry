package com.sauliyo15.carregistry.controller.mappers;

import com.sauliyo15.carregistry.controller.dtos.BrandRequest;
import com.sauliyo15.carregistry.controller.dtos.BrandResponse;
import com.sauliyo15.carregistry.model.Brand;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class BrandMapper {

    public BrandResponse toBrandResponse(Brand brand) {
        BrandResponse brandResponse = new BrandResponse();
        brandResponse.setId(brand.getId());
        brandResponse.setName(brand.getName());
        brandResponse.setWarranty(brand.getWarranty());
        brandResponse.setCountry(brand.getCountry());
        return brandResponse;
    }

    public Brand toBrand(BrandRequest brandRequest) {
        Brand brand = new Brand();
        brand.setName(brandRequest.getName());
        brand.setWarranty(brandRequest.getWarranty());
        brand.setCountry(brandRequest.getCountry());
        return brand;
    }

    public List<BrandResponse> toBrandListResponse(List<Brand> brands) {
        return brands.stream().map(this::toBrandResponse).toList();
    }
}
