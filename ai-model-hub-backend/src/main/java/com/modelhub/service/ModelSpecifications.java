package com.modelhub.service;

import com.modelhub.entity.*;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ModelSpecifications {

    public static Specification<Model> withFilters(ModelFilterCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Only active models
            predicates.add(cb.equal(root.get("isActive"), true));

            // Provider filter
            if (criteria.getProviders() != null && !criteria.getProviders().isEmpty()) {
                Join<Model, Provider> providerJoin = root.join("provider");
                predicates.add(providerJoin.get("name").in(criteria.getProviders()));
            }

            // Category filter
            if (criteria.getCategories() != null && !criteria.getCategories().isEmpty()) {
                Join<Model, Category> categoryJoin = root.join("categories");
                predicates.add(categoryJoin.get("name").in(criteria.getCategories()));
            }

            // Capability filter
            if (criteria.getCapabilities() != null && !criteria.getCapabilities().isEmpty()) {
                Join<Model, Capability> capabilityJoin = root.join("capabilities");
                predicates.add(capabilityJoin.get("name").in(criteria.getCapabilities()));
            }

            // Context length range filter
            if (criteria.getContextRanges() != null && !criteria.getContextRanges().isEmpty()) {
                List<Predicate> contextPredicates = new ArrayList<>();
                for (String range : criteria.getContextRanges()) {
                    int[] bounds = parseContextRange(range);
                    if (bounds != null) {
                        contextPredicates.add(cb.between(
                                root.get("contextLength"),
                                bounds[0],
                                bounds[1]
                        ));
                    }
                }
                if (!contextPredicates.isEmpty()) {
                    predicates.add(cb.or(contextPredicates.toArray(new Predicate[0])));
                }
            }

            // Search filter (name or description)
            if (criteria.getSearch() != null && !criteria.getSearch().isEmpty()) {
                String searchPattern = "%" + criteria.getSearch().toLowerCase() + "%";
                Join<Model, Provider> providerJoin = root.join("provider", JoinType.LEFT);
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("name")), searchPattern),
                        cb.like(cb.lower(root.get("description")), searchPattern),
                        cb.like(cb.lower(providerJoin.get("name")), searchPattern),
                        cb.like(cb.lower(providerJoin.get("displayName")), searchPattern)
                ));
            }

            // Ensure distinct results for many-to-many joins
            query.distinct(true);

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static int[] parseContextRange(String range) {
        if (range == null || range.isEmpty()) return null;

        String[] parts = range.split("-");
        if (parts.length != 2) return null;

        try {
            int lower = parseContextValue(parts[0].trim());
            int upper = parseContextValue(parts[1].trim());
            return new int[]{lower, upper};
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static int parseContextValue(String value) {
        value = value.toUpperCase().trim();
        if (value.endsWith("K")) {
            return Integer.parseInt(value.substring(0, value.length() - 1)) * 1000;
        } else if (value.endsWith("M")) {
            return Integer.parseInt(value.substring(0, value.length() - 1)) * 1000000;
        }
        return Integer.parseInt(value);
    }

}
