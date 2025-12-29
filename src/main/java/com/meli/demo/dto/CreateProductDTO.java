package com.meli.demo.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreateProductDTO {

    private String name;
    private String imageUrl;
    private String description;
    private BigDecimal price;
    private BigDecimal rating;
    private ProductSpecificationDTO specification;
    private Boolean availability;

}
