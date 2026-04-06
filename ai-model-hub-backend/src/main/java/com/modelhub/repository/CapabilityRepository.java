package com.modelhub.repository;

import com.modelhub.entity.Capability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface CapabilityRepository extends JpaRepository<Capability, Long> {

    Optional<Capability> findByName(String name);

    boolean existsByName(String name);

    @Query("SELECT c FROM Capability c LEFT JOIN FETCH c.models WHERE c.id = :id")
    Optional<Capability> findByIdWithModels(Long id);

    @Query("SELECT c, COUNT(m) FROM Capability c LEFT JOIN c.models m GROUP BY c")
    List<Object[]> findAllWithModelCount();

}
