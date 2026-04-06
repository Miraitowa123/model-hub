package com.modelhub.controller;

import com.modelhub.dto.ApiResponse;
import com.modelhub.dto.CapabilityDto;
import com.modelhub.service.CapabilityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/capabilities")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class CapabilityController {

    private final CapabilityService capabilityService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CapabilityDto>>> getAllCapabilities() {
        log.debug("GET /api/v1/capabilities");
        List<CapabilityDto> capabilities = capabilityService.getAllCapabilities();
        return ResponseEntity.ok(ApiResponse.success(capabilities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CapabilityDto>> getCapabilityById(
            @PathVariable Long id) {
        log.debug("GET /api/v1/capabilities/{}", id);
        CapabilityDto capability = capabilityService.getCapabilityById(id);
        return ResponseEntity.ok(ApiResponse.success(capability));
    }

}
