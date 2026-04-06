package com.modelhub.mapper;

import com.modelhub.dto.CapabilityDto;
import com.modelhub.dto.CategoryDto;
import com.modelhub.dto.ModelDetailDto;
import com.modelhub.dto.ModelDto;
import com.modelhub.dto.ProviderDto;
import com.modelhub.entity.Capability;
import com.modelhub.entity.Category;
import com.modelhub.entity.Model;
import com.modelhub.entity.ModelPricing;
import com.modelhub.entity.Provider;
import java.math.BigDecimal;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-29T23:04:13+0800",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.jar, environment: Java 17.0.17 (Oracle Corporation)"
)
@Component
public class ModelMapperImpl implements ModelMapper {

    @Override
    public ModelDto toDto(Model model) {
        if ( model == null ) {
            return null;
        }

        ModelDto.ModelDtoBuilder modelDto = ModelDto.builder();

        modelDto.inputPrice( modelPricingInputPrice( model ) );
        modelDto.outputPrice( modelPricingOutputPrice( model ) );
        modelDto.priceUnit( modelPricingPriceUnit( model ) );
        modelDto.id( model.getId() );
        modelDto.modelId( model.getModelId() );
        modelDto.name( model.getName() );
        modelDto.description( model.getDescription() );
        modelDto.contextLength( model.getContextLength() );
        modelDto.provider( toProviderDto( model.getProvider() ) );
        modelDto.releaseDate( model.getReleaseDate() );
        modelDto.popularityScore( model.getPopularityScore() );
        modelDto.updatedAt( model.getUpdatedAt() );

        modelDto.categories( mapCategoriesToStrings(model.getCategories()) );
        modelDto.capabilities( mapCapabilitiesToStrings(model.getCapabilities()) );

        return modelDto.build();
    }

    @Override
    public ModelDetailDto toDetailDto(Model model) {
        if ( model == null ) {
            return null;
        }

        ModelDetailDto.ModelDetailDtoBuilder modelDetailDto = ModelDetailDto.builder();

        modelDetailDto.inputPrice( modelPricingInputPrice( model ) );
        modelDetailDto.outputPrice( modelPricingOutputPrice( model ) );
        modelDetailDto.priceUnit( modelPricingPriceUnit( model ) );
        modelDetailDto.id( model.getId() );
        modelDetailDto.modelId( model.getModelId() );
        modelDetailDto.name( model.getName() );
        modelDetailDto.description( model.getDescription() );
        modelDetailDto.contextLength( model.getContextLength() );
        modelDetailDto.isActive( model.getIsActive() );
        modelDetailDto.provider( toProviderDto( model.getProvider() ) );
        modelDetailDto.releaseDate( model.getReleaseDate() );
        modelDetailDto.popularityScore( model.getPopularityScore() );
        modelDetailDto.createdAt( model.getCreatedAt() );
        modelDetailDto.updatedAt( model.getUpdatedAt() );

        modelDetailDto.categories( mapCategoriesToDtos(model.getCategories()) );
        modelDetailDto.capabilities( mapCapabilitiesToDtos(model.getCapabilities()) );

        return modelDetailDto.build();
    }

    @Override
    public ProviderDto toProviderDto(Provider provider) {
        if ( provider == null ) {
            return null;
        }

        ProviderDto.ProviderDtoBuilder providerDto = ProviderDto.builder();

        providerDto.id( provider.getId() );
        providerDto.name( provider.getName() );
        providerDto.displayName( provider.getDisplayName() );
        providerDto.description( provider.getDescription() );
        providerDto.logoUrl( provider.getLogoUrl() );
        providerDto.websiteUrl( provider.getWebsiteUrl() );

        providerDto.modelCount( provider.getModels() != null ? provider.getModels().size() : 0 );

        return providerDto.build();
    }

    @Override
    public CategoryDto toCategoryDto(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryDto.CategoryDtoBuilder categoryDto = CategoryDto.builder();

        categoryDto.id( category.getId() );
        categoryDto.name( category.getName() );
        categoryDto.displayName( category.getDisplayName() );
        categoryDto.description( category.getDescription() );
        categoryDto.icon( category.getIcon() );

        categoryDto.modelCount( category.getModels() != null ? category.getModels().size() : 0 );

        return categoryDto.build();
    }

    @Override
    public CapabilityDto toCapabilityDto(Capability capability) {
        if ( capability == null ) {
            return null;
        }

        CapabilityDto.CapabilityDtoBuilder capabilityDto = CapabilityDto.builder();

        capabilityDto.id( capability.getId() );
        capabilityDto.name( capability.getName() );
        capabilityDto.displayName( capability.getDisplayName() );
        capabilityDto.description( capability.getDescription() );
        capabilityDto.icon( capability.getIcon() );

        capabilityDto.modelCount( capability.getModels() != null ? capability.getModels().size() : 0 );

        return capabilityDto.build();
    }

    private BigDecimal modelPricingInputPrice(Model model) {
        if ( model == null ) {
            return null;
        }
        ModelPricing pricing = model.getPricing();
        if ( pricing == null ) {
            return null;
        }
        BigDecimal inputPrice = pricing.getInputPrice();
        if ( inputPrice == null ) {
            return null;
        }
        return inputPrice;
    }

    private BigDecimal modelPricingOutputPrice(Model model) {
        if ( model == null ) {
            return null;
        }
        ModelPricing pricing = model.getPricing();
        if ( pricing == null ) {
            return null;
        }
        BigDecimal outputPrice = pricing.getOutputPrice();
        if ( outputPrice == null ) {
            return null;
        }
        return outputPrice;
    }

    private String modelPricingPriceUnit(Model model) {
        if ( model == null ) {
            return null;
        }
        ModelPricing pricing = model.getPricing();
        if ( pricing == null ) {
            return null;
        }
        String priceUnit = pricing.getPriceUnit();
        if ( priceUnit == null ) {
            return null;
        }
        return priceUnit;
    }
}
