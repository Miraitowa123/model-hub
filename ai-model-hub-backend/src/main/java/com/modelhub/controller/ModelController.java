package com.modelhub.controller;

import com.modelhub.dto.*;
import com.modelhub.service.ModelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/models")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class ModelController {

    private final ModelService modelService;

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<ModelDto>>> getModels(
            @Valid ModelFilterRequest filterRequest) {
        log.debug("GET /api/v1/models with filters: {}", filterRequest);
        PagedResponse<ModelDto> models = modelService.getModels(filterRequest);
        return ResponseEntity.ok(ApiResponse.success(models));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ModelDetailDto>> getModelById(
            @PathVariable Long id) {
        log.debug("GET /api/v1/models/{}", id);
        ModelDetailDto model = modelService.getModelById(id);
        return ResponseEntity.ok(ApiResponse.success(model));
    }

    @GetMapping("/by-model-id/{modelId}")
    public ResponseEntity<ApiResponse<ModelDetailDto>> getModelByModelId(
            @PathVariable String modelId) {
        log.debug("GET /api/v1/models/by-model-id/{}", modelId);
        ModelDetailDto model = modelService.getModelByModelId(modelId);
        return ResponseEntity.ok(ApiResponse.success(model));
    }

    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<ModelStatsDto>> getStats() {
        log.debug("GET /api/v1/models/stats");
        ModelStatsDto stats = modelService.getStats();
        return ResponseEntity.ok(ApiResponse.success(stats));
    }

}
