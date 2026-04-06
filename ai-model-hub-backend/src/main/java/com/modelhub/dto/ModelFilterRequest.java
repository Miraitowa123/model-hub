package com.modelhub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModelFilterRequest {

    private Set<String> providers;
    private Set<String> categories;
    private Set<String> capabilities;
    private Set<String> contextLengths;
    private String search;
    private String sort;
    private String order;
    private Integer page;
    private Integer size;

    public Integer getPage() {
        return page != null && page >= 0 ? page : 0;
    }

    public Integer getSize() {
        return size != null && size > 0 ? Math.min(size, 100) : 20;
    }

    public String getSort() {
        return sort != null ? sort : "updatedAt";
    }

    public String getOrder() {
        return order != null && order.equalsIgnoreCase("asc") ? "asc" : "desc";
    }

}
