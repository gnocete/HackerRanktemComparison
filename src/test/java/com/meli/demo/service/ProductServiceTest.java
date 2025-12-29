package com.meli.demo.service;

import com.meli.demo.dto.CreateProductDTO;
import com.meli.demo.dto.RecoveryProductDTO;
import com.meli.demo.dto.UpdateProductDTO;
import com.meli.demo.entities.Product;
import com.meli.demo.mapper.ProductMapper;
import com.meli.demo.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private com.fasterxml.jackson.databind.ObjectMapper objectMapper;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(productService, "objectMapper", objectMapper);
    }

    @Test
    void whenGetByIdExists_thenReturnDto() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Produto A");
        product.setPrice(BigDecimal.valueOf(10.0));

        RecoveryProductDTO dto = new RecoveryProductDTO();
        dto.setId(1L);
        dto.setName("Produto A");
        dto.setPrice(BigDecimal.valueOf(10.0));

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productMapper.mapProductToRecoveryProductDTO(product)).thenReturn(dto);

        RecoveryProductDTO result = productService.getProductbyId(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Produto A", result.getName());
        assertEquals(BigDecimal.valueOf(10.0), result.getPrice());
        verify(productRepository).findById(1L);
        verify(productMapper).mapProductToRecoveryProductDTO(product);
    }

    @Test
    void whenGetByIdNotFound_thenThrow() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> productService.getProductbyId(99L));
        verify(productRepository).findById(99L);
    }

    @Test
    void createProduct_savesAndReturnsDto() {
        CreateProductDTO toSave = new CreateProductDTO();
        toSave.setName("Novo");
        toSave.setPrice(BigDecimal.valueOf(5.00));

        Product saved = new Product();
        saved.setId(2L);
        saved.setName("Novo");
        saved.setPrice(BigDecimal.valueOf(5.00));

        RecoveryProductDTO dto = new RecoveryProductDTO();
        dto.setId(2L);
        dto.setName("Novo");
        dto.setPrice(BigDecimal.valueOf(5.00));

        when(productRepository.save(any(Product.class))).thenReturn(saved);
        when(productMapper.mapProductToRecoveryProductDTO(saved)).thenReturn(dto);

        RecoveryProductDTO result = productService.createProduct(toSave);

        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals("Novo", result.getName());
        assertEquals(BigDecimal.valueOf(5.00), result.getPrice());

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).save(captor.capture());
        assertEquals("Novo", captor.getValue().getName());
        verify(productMapper).mapProductToRecoveryProductDTO(saved);
    }

    @Test
    void updateProduct_updatesWhenExists() {
        Product existing = new Product();
        existing.setId(3L);
        existing.setName("Old");
        existing.setPrice(BigDecimal.valueOf(7.00));

        UpdateProductDTO updateDto = new UpdateProductDTO();
        updateDto.setName("Updated");
        updateDto.setPrice(BigDecimal.valueOf(9.00));

        Product saved = new Product();
        saved.setId(3L);
        saved.setName("Updated");
        saved.setPrice(BigDecimal.valueOf(9.00));

        RecoveryProductDTO dto = new RecoveryProductDTO();
        dto.setId(3L);
        dto.setName("Updated");
        dto.setPrice(BigDecimal.valueOf(9.00));

        when(productRepository.findById(3L)).thenReturn(Optional.of(existing));
        when(productRepository.save(any(Product.class))).thenReturn(saved);
        when(productMapper.mapProductToRecoveryProductDTO(saved)).thenReturn(dto);

        RecoveryProductDTO result = productService.updateProductPart(3L, updateDto);

        assertNotNull(result);
        assertEquals("Updated", result.getName());
        assertEquals(BigDecimal.valueOf(9.00), result.getPrice());
        verify(productRepository).findById(3L);
        verify(productRepository).save(any(Product.class));
        verify(productMapper).mapProductToRecoveryProductDTO(saved);
    }

    @Test
    void deleteProduct_callsRepository() {
        when(productRepository.existsById(4L)).thenReturn(true);
        doNothing().when(productRepository).deleteById(4L);

        productService.deleteProductById(4L);

        verify(productRepository).existsById(4L);
        verify(productRepository).deleteById(4L);
    }
}