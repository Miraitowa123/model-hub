package com.modelhub.service;

import com.modelhub.dto.ProviderDto;

import java.util.List;

public interface ProviderService {

    List<ProviderDto> getAllProviders();

    ProviderDto getProviderById(Long id);

}
