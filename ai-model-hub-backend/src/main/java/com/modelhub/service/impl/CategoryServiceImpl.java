package com.modelhub.service.impl;

import com.modelhub.dto.CategoryDto;
import com.modelhub.entity.Category;
import com.modelhub.exception.ResourceNotFoundException;
import com.modelhub.mapper.ModelMapper;
import com.modelhub.repository.CategoryRepository;
import com.modelhub.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<CategoryDto> getAllCategories() {
        log.debug("Fetching all categories");
        return categoryRepository.findAll().stream()
                .map(modelMapper::toCategoryDto)
                .sorted(Comparator.comparing(CategoryDto::getDisplayName))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        log.debug("Fetching category by id: {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        return modelMapper.toCategoryDto(category);
    }

}
