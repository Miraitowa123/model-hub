package com.modelhub.service.impl;

import com.modelhub.dto.ProviderDto;
import com.modelhub.entity.Provider;
import com.modelhub.exception.ResourceNotFoundException;
import com.modelhub.mapper.ModelMapper;
import com.modelhub.repository.ProviderRepository;
import com.modelhub.service.ProviderService;
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
public class ProviderServiceImpl implements ProviderService {

    private final ProviderRepository providerRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ProviderDto> getAllProviders() {
        log.debug("Fetching all providers");
        return providerRepository.findAll().stream()
                .map(modelMapper::toProviderDto)
                .sorted(Comparator.comparing(ProviderDto::getDisplayName))
                .collect(Collectors.toList());
    }

    @Override
    public ProviderDto getProviderById(Long id) {
        log.debug("Fetching provider by id: {}", id);
        Provider provider = providerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Provider", "id", id));
        return modelMapper.toProviderDto(provider);
    }

}
