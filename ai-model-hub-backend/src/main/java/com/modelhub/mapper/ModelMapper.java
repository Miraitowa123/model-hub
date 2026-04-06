package com.modelhub.mapper;

import com.modelhub.dto.*;
import com.modelhub.entity.*;
import org.mapstruct.*;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ModelMapper {

    @Mapping(target = "categories", expression = "java(mapCategoriesToStrings(model.getCategories()))")
    @Mapping(target = "capabilities", expression = "java(mapCapabilitiesToStrings(model.getCapabilities()))")
    @Mapping(target = "inputPrice", source = "pricing.inputPrice")
    @Mapping(target = "outputPrice", source = "pricing.outputPrice")
    @Mapping(target = "priceUnit", source = "pricing.priceUnit")
    ModelDto toDto(Model model);

    @Mapping(target = "categories", expression = "java(mapCategoriesToDtos(model.getCategories()))")
    @Mapping(target = "capabilities", expression = "java(mapCapabilitiesToDtos(model.getCapabilities()))")
    @Mapping(target = "inputPrice", source = "pricing.inputPrice")
    @Mapping(target = "outputPrice", source = "pricing.outputPrice")
    @Mapping(target = "priceUnit", source = "pricing.priceUnit")
    ModelDetailDto toDetailDto(Model model);

    @Mapping(target = "modelCount", expression = "java(provider.getModels() != null ? provider.getModels().size() : 0)")
    ProviderDto toProviderDto(Provider provider);

    @Mapping(target = "modelCount", expression = "java(category.getModels() != null ? category.getModels().size() : 0)")
    CategoryDto toCategoryDto(Category category);

    @Mapping(target = "modelCount", expression = "java(capability.getModels() != null ? capability.getModels().size() : 0)")
    CapabilityDto toCapabilityDto(Capability capability);

    default Set<String> mapCategoriesToStrings(Set<Category> categories) {
        if (categories == null) return null;
        return categories.stream()
                .map(Category::getDisplayName)
                .collect(Collectors.toSet());
    }

    default Set<String> mapCapabilitiesToStrings(Set<Capability> capabilities) {
        if (capabilities == null) return null;
        return capabilities.stream()
                .map(Capability::getDisplayName)
                .collect(Collectors.toSet());
    }

    default Set<CategoryDto> mapCategoriesToDtos(Set<Category> categories) {
        if (categories == null) return null;
        return categories.stream()
                .map(this::toCategoryDto)
                .collect(Collectors.toSet());
    }

    default Set<CapabilityDto> mapCapabilitiesToDtos(Set<Capability> capabilities) {
        if (capabilities == null) return null;
        return capabilities.stream()
                .map(this::toCapabilityDto)
                .collect(Collectors.toSet());
    }

}
