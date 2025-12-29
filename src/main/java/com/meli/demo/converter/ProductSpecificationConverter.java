package com.meli.demo.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.demo.dto.ProductSpecificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductSpecificationConverter {

    @Autowired
    private ObjectMapper objectMapper;

    public ProductSpecificationDTO fromJson(String json) {
        if (json == null) return null;
        try {
            return objectMapper.readValue(json, ProductSpecificationDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao desserializar specification", e);
        }
    }


}
