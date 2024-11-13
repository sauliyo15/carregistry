package com.sauliyo15.carregistry.repository;

import com.sauliyo15.carregistry.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<CarEntity, Integer> {

}
