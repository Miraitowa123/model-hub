package com.modelhub.service;

import com.modelhub.dto.*;

public interface ModelService {

    PagedResponse<ModelDto> getModels(ModelFilterRequest filterRequest);

    ModelDetailDto getModelById(Long id);

    ModelDetailDto getModelByModelId(String modelId);

    ModelStatsDto getStats();

}
