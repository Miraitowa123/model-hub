package com.modelhub.repository;

import com.modelhub.entity.Model;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long>, JpaSpecificationExecutor<Model> {

    @EntityGraph(attributePaths = {"provider", "categories", "capabilities"})
    Page<Model> findAll(Specification<Model> spec, Pageable pageable);

    @EntityGraph(attributePaths = {"provider", "categories", "capabilities"})
    Optional<Model> findById(Long id);

    @EntityGraph(attributePaths = {"provider", "categories", "capabilities"})
    Optional<Model> findByModelId(String modelId);

    @Query("SELECT COUNT(m) FROM Model m WHERE m.isActive = true")
    long countActiveModels();

    @Query("SELECT m FROM Model m WHERE m.isActive = true AND " +
           "(:search IS NULL OR LOWER(m.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(m.description) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Model> searchByKeyword(@Param("search") String search, Pageable pageable);

    @Query("SELECT m FROM Model m " +
           "LEFT JOIN FETCH m.provider " +
           "LEFT JOIN FETCH m.categories " +
           "LEFT JOIN FETCH m.capabilities " +
           "WHERE m.id = :id")
    Optional<Model> findByIdWithDetails(@Param("id") Long id);

}
