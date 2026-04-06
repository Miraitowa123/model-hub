package com.modelhub.service.impl;

import com.modelhub.dto.CapabilityDto;
import com.modelhub.entity.Capability;
import com.modelhub.exception.ResourceNotFoundException;
import com.modelhub.mapper.ModelMapper;
import com.modelhub.repository.CapabilityRepository;
import com.modelhub.service.CapabilityService;
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
public class CapabilityServiceImpl implements CapabilityService {

    private final CapabilityRepository capabilityRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<CapabilityDto> getAllCapabilities() {
        log.debug("Fetching all capabilities");
        return capabilityRepository.findAll().stream()
                .map(modelMapper::toCapabilityDto)
                .sorted(Comparator.comparing(CapabilityDto::getDisplayName))
                .collect(Collectors.toList());
    }

    @Override
    public CapabilityDto getCapabilityById(Long id) {
        log.debug("Fetching capability by id: {}", id);
        Capability capability = capabilityRepository.findByIdWithModels(id)
                .orElseThrow(() -> new ResourceNotFoundException("Capability", "id", id));
        return modelMapper.toCapabilityDto(capability);
    }

}
