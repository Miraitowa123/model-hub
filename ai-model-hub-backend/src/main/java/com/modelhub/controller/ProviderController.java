package com.modelhub.controller;

import com.modelhub.dto.ApiResponse;
import com.modelhub.dto.ProviderDto;
import com.modelhub.service.ProviderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/providers")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class ProviderController {

    private final ProviderService providerService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProviderDto>>> getAllProviders() {
        log.debug("GET /api/v1/providers");
        List<ProviderDto> providers = providerService.getAllProviders();
        return ResponseEntity.ok(ApiResponse.success(providers));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProviderDto>> getProviderById(
            @PathVariable Long id) {
        log.debug("GET /api/v1/providers/{}", id);
        ProviderDto provider = providerService.getProviderById(id);
        return ResponseEntity.ok(ApiResponse.success(provider));
    }

}
