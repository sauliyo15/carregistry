package com.sauliyo15.carregistry.service;

import com.sauliyo15.carregistry.model.Brand;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface BrandService {

    CompletableFuture<List<Brand>> getBrands() throws Exception;
    Brand getBrandById(Integer id) throws Exception;
    Brand addBrand (Brand brand) throws Exception;
    Brand updateBrand (Integer id, Brand brand) throws Exception;
    void deleteBrand (Integer id) throws Exception;
}
