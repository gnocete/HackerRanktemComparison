package com.meli.demo.mapper;

import com.meli.demo.converter.ProductSpecificationConverter;
import com.meli.demo.dto.RecoveryProductDTO;
import com.meli.demo.entities.Product;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {

    @Autowired
    protected ProductSpecificationConverter converter;

    @Mapping(target = "specification", ignore = true)
    public abstract RecoveryProductDTO mapProductToRecoveryProductDTO(Product product);

    @AfterMapping
    protected void mapSpecification(Product product, @MappingTarget RecoveryProductDTO dto) {
        dto.setSpecification(converter.fromJson(product.getSpecification()));
    }
}