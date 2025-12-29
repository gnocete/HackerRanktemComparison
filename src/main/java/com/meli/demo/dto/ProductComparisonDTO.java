package com.meli.demo.dto;

import lombok.Data;

@Data
public class ProductComparisonDTO {

    private String referenceProductName;
    private Double referenceProductPrice;
    private Double referenceProductRating;

    private String comparedProductName;
    private Double comparedProductPrice;
    private Double comparedProductRating;

    private String cheapestProductName;
    private String bestRatedProductName;

}
