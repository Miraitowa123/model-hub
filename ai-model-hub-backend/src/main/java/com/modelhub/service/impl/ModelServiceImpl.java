package com.modelhub.service.impl;

import com.modelhub.dto.*;
import com.modelhub.entity.Model;
import com.modelhub.exception.ResourceNotFoundException;
import com.modelhub.mapper.ModelMapper;
import com.modelhub.repository.*;
import com.modelhub.service.ModelFilterCriteria;
import com.modelhub.service.ModelService;
import com.modelhub.service.ModelSpecifications;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ModelServiceImpl implements ModelService {

    private final ModelRepository modelRepository;
    private final ProviderRepository providerRepository;
    private final CategoryRepository categoryRepository;
    private final CapabilityRepository capabilityRepository;
    private final ModelMapper modelMapper;

    @Override
    public PagedResponse<ModelDto> getModels(ModelFilterRequest filterRequest) {
        log.debug("Fetching models with filters: {}", filterRequest);

        // Build filter criteria
        ModelFilterCriteria criteria = ModelFilterCriteria.builder()
                .providers(filterRequest.getProviders())
                .categories(filterRequest.getCategories())
                .capabilities(filterRequest.getCapabilities())
                .contextRanges(filterRequest.getContextLengths())
                .search(filterRequest.getSearch())
                .build();

        // Build specification
        Specification<Model> spec = ModelSpecifications.withFilters(criteria);

        // Build sort
        Sort sort = buildSort(filterRequest.getSort(), filterRequest.getOrder());

        // Build pageable
        Pageable pageable = PageRequest.of(
                filterRequest.getPage(),
                filterRequest.getSize(),
                sort
        );

        // Execute query
        Page<Model> modelPage = modelRepository.findAll(spec, pageable);

        // Map to DTOs
        List<ModelDto> modelDtos = modelPage.getContent().stream()
                .map(modelMapper::toDto)
                .collect(Collectors.toList());

        return PagedResponse.of(
                modelDtos,
                modelPage.getNumber(),
                modelPage.getSize(),
                modelPage.getTotalElements()
        );
    }

    @Override
    public ModelDetailDto getModelById(Long id) {
        log.debug("Fetching model by id: {}", id);
        Model model = modelRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new ResourceNotFoundException("Model", "id", id));
        return modelMapper.toDetailDto(model);
    }

    @Override
    public ModelDetailDto getModelByModelId(String modelId) {
        log.debug("Fetching model by modelId: {}", modelId);
        Model model = modelRepository.findByModelId(modelId)
                .orElseThrow(() -> new ResourceNotFoundException("Model", "modelId", modelId));
        return modelMapper.toDetailDto(model);
    }

    @Override
    public ModelStatsDto getStats() {
        log.debug("Fetching model stats");
        return ModelStatsDto.builder()
                .totalModels(modelRepository.count())
                .totalProviders(providerRepository.count())
                .totalCategories(categoryRepository.count())
                .totalCapabilities(capabilityRepository.count())
                .build();
    }

    private Sort buildSort(String sortBy, String order) {
        String field = switch (sortBy) {
            case "name" -> "name";
            case "publishedAt", "releaseDate" -> "releaseDate";
            case "inputPrice" -> "pricing.inputPrice";
            case "outputPrice" -> "pricing.outputPrice";
            case "popularity" -> "popularityScore";
            case "contextLength" -> "contextLength";
            default -> "updatedAt";
        };

        Sort.Direction direction = "asc".equalsIgnoreCase(order)
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        return Sort.by(direction, field);
    }

}
