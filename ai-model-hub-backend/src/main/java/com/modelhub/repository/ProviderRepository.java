package com.modelhub.repository;

import com.modelhub.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long> {

    Optional<Provider> findByName(String name);

    boolean existsByName(String name);

    @Query("SELECT p FROM Provider p LEFT JOIN FETCH p.models WHERE p.id = :id")
    Optional<Provider> findByIdWithModels(Long id);

    @Query("SELECT p, COUNT(m) FROM Provider p LEFT JOIN p.models m GROUP BY p")
    List<Object[]> findAllWithModelCount();

}
