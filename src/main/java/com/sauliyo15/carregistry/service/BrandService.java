package com.sauliyo15.carregistry.service;

import com.sauliyo15.carregistry.model.Brand;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface BrandService {

    CompletableFuture<List<Brand>> getBrands();
    Brand getBrandById(Integer id);
    Brand addBrand (Brand brand);
    Brand updateBrand (Integer id, Brand brand);
    void deleteBrand (Integer id);

    Brand getBrandByName(String name);

}
