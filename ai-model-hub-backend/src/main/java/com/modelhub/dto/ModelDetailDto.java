package com.modelhub.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModelDetailDto {

    private Long id;
    private String modelId;
    private String name;
    private String description;
    private Integer contextLength;
    private Boolean isActive;

    private ProviderDto provider;
    private Set<CategoryDto> categories;
    private Set<CapabilityDto> capabilities;

    private BigDecimal inputPrice;
    private BigDecimal outputPrice;
    private String priceUnit;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    private Double popularityScore;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

}
