package com.modelhub.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.math.BigDecimal;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModelPricing {

    @Column(name = "input_price", precision = 10, scale = 6)
    private BigDecimal inputPrice;

    @Column(name = "output_price", precision = 10, scale = 6)
    private BigDecimal outputPrice;

    @Column(name = "price_unit", length = 10)
    private String priceUnit;

    public static ModelPricing perMillionTokens(BigDecimal inputPrice, BigDecimal outputPrice) {
        return ModelPricing.builder()
                .inputPrice(inputPrice)
                .outputPrice(outputPrice)
                .priceUnit("1M tokens")
                .build();
    }

}
