package com.modelhub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProviderDto {

    private Long id;
    private String name;
    private String displayName;
    private String description;
    private String logoUrl;
    private String websiteUrl;
    private Integer modelCount;

}
