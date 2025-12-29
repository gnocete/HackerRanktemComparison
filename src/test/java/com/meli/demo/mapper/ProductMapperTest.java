package com.meli.demo.mapper;

import com.meli.demo.controller.ProductController;
import com.meli.demo.dto.CreateProductDTO;
import com.meli.demo.dto.UpdateProductDTO;
import com.meli.demo.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void createProduct_returnsCreated_and_callsService() throws Exception {
        when(productService.createProduct(any(CreateProductDTO.class))).thenReturn(null);

        String json = "{\"name\":\"produto\",\"price\":10}";

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        verify(productService, times(1)).createProduct(any(CreateProductDTO.class));
    }

    @Test
    void getProductById_returnsOk_and_callsService() throws Exception {
        when(productService.getProductbyId(1L)).thenReturn(null);

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk());

        verify(productService, times(1)).getProductbyId(1L);
    }

    @Test
    void updateProductPart_returnsOk_and_callsService() throws Exception {
        when(productService.updateProductPart(eq(1L), any(UpdateProductDTO.class))).thenReturn(null);

        String patchJson = "{\"price\":15}";

        mockMvc.perform(patch("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patchJson))
                .andExpect(status().isOk());

        verify(productService, times(1)).updateProductPart(eq(1L), any(UpdateProductDTO.class));
    }

    @Test
    void deleteProductById_returnsNoContent_and_callsService() throws Exception {
        doNothing().when(productService).deleteProductById(1L);

        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).deleteProductById(1L);
    }
}
