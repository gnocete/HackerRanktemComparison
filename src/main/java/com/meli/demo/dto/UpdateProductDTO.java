package com.meli.demo.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProductDTO {

    private String name;
    private String imageUrl;
    private BigDecimal price;
    private String description;
    private UpdateProductDTO specification;
    private Boolean availability;
}
