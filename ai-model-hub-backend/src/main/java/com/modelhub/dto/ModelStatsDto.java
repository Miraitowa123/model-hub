package com.modelhub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModelStatsDto {

    private long totalModels;
    private long totalProviders;
    private long totalCategories;
    private long totalCapabilities;

}
