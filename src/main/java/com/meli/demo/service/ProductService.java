package com.meli.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.demo.dto.CreateProductDTO;
import com.meli.demo.dto.ProductComparisonDTO;
import com.meli.demo.dto.RecoveryProductDTO;
import com.meli.demo.dto.UpdateProductDTO;
import com.meli.demo.mapper.ProductMapper;
import com.meli.demo.repository.ProductRepository;
import com.meli.demo.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ObjectMapper objectMapper;

    public List<ProductComparisonDTO> compareProducts(List<Long> productIds) {
        // Busca todos os produtos pelos IDs fornecidos
        List<Product> products = productRepository.findAllById(productIds);

        if (products.isEmpty()) {
            throw new IllegalArgumentException("Nenhum produto encontrado para os IDs fornecidos.");
        }

        // Define o primeiro produto da lista como referência
        Product reference = products.get(0);

        // Encontra o produto mais barato e o mais bem avaliado
        Product cheapest = products.stream()
                .min(Comparator.comparing(Product::getPrice))
                .orElseThrow();

        Product bestRated = products.stream()
                .max(Comparator.comparing(Product::getRating))
                .orElseThrow();

        List<ProductComparisonDTO> result = new ArrayList<>();
        for (Product product : products) {
            if (!product.getId().equals(reference.getId())) {
                ProductComparisonDTO dto = new ProductComparisonDTO();
                dto.setReferenceProductName(reference.getName());
                dto.setComparedProductName(product.getName());
                dto.setReferenceProductPrice(reference.getPrice().doubleValue());
                dto.setComparedProductPrice(product.getPrice().doubleValue());
                dto.setReferenceProductRating(reference.getRating().doubleValue());
                dto.setComparedProductRating(product.getRating().doubleValue());
                dto.setCheapestProductName(cheapest.getName());
                dto.setBestRatedProductName(bestRated.getName());
                result.add(dto);
            }
        }
        return result;
    }

    public RecoveryProductDTO createProduct(CreateProductDTO createProductDTO) {

        String specificationJson = null;
        try {
            specificationJson = objectMapper.writeValueAsString(createProductDTO.getSpecification());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao serializar specification", e);
        }

        Product product = Product.builder()
                .name(createProductDTO.getName())
                .imageUrl(createProductDTO.getImageUrl())
                .description(createProductDTO.getDescription())
                .price(createProductDTO.getPrice())
                .rating(createProductDTO.getRating())
                .specification(specificationJson)
                .availability(createProductDTO.getAvailability())
                .build();

        Product productSaved = productRepository.save(product);

        return productMapper.mapProductToRecoveryProductDTO(productSaved);
    }

    public RecoveryProductDTO getProductbyId(Long productId) {

        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Produto não encontrado."));

        return productMapper.mapProductToRecoveryProductDTO(product);

    }

    public RecoveryProductDTO updateProductPart(Long productId, UpdateProductDTO updateProductDTO){

        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Produto não encontrado."));

        if(updateProductDTO.getName() != null) {
            product.setName(updateProductDTO.getName());
        }
        if(updateProductDTO.getImageUrl() != null) {
            product.setImageUrl(updateProductDTO.getImageUrl());
        }
        if(updateProductDTO.getPrice() != null) {
            product.setPrice(updateProductDTO.getPrice());
        }
        if(updateProductDTO.getDescription() != null) {
            product.setDescription(updateProductDTO.getDescription());
        }
        if(updateProductDTO.getSpecification() != null) {
            try {
                product.setSpecification(objectMapper.writeValueAsString(updateProductDTO.getSpecification()));
            } catch (Exception e) {
                throw new RuntimeException("Erro ao serializar specification", e);
            }
        }
        if(updateProductDTO.getAvailability() != null) {
            product.setAvailability(updateProductDTO.getAvailability());
        }

        return productMapper.mapProductToRecoveryProductDTO(productRepository.save(product));

    }

    public void deleteProductById(Long productId) {
        if(!productRepository.existsById(productId)) {
            throw new NoSuchElementException("Produto de id: " + productId + " não encontrado.");
        }
        productRepository.deleteById(productId);
    }
}