package com.sauliyo15.carregistry.repository;

import com.sauliyo15.carregistry.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByMail(String mail);
}
