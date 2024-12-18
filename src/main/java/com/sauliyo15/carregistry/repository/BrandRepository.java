package com.sauliyo15.carregistry.repository;

import com.sauliyo15.carregistry.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<BrandEntity, Integer> {

    Optional<BrandEntity> findByName(String name);
}
