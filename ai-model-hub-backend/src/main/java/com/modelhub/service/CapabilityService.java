package com.modelhub.service;

import com.modelhub.dto.CapabilityDto;

import java.util.List;

public interface CapabilityService {

    List<CapabilityDto> getAllCapabilities();

    CapabilityDto getCapabilityById(Long id);

}
