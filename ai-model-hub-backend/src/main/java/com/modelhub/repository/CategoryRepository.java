package com.modelhub.repository;

import com.modelhub.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);

    boolean existsByName(String name);

    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.models WHERE c.id = :id")
    Optional<Category> findByIdWithModels(Long id);

    @Query("SELECT c, COUNT(m) FROM Category c LEFT JOIN c.models m GROUP BY c")
    List<Object[]> findAllWithModelCount();

}
