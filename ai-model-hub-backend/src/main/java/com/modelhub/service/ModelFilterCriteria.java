package com.modelhub.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModelFilterCriteria {

    private Set<String> providers;
    private Set<String> categories;
    private Set<String> capabilities;
    private Set<String> contextRanges;
    private String search;
    private String sortBy;
    private String sortOrder;

}
