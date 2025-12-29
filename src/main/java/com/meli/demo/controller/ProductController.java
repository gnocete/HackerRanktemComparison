package com.meli.demo.controller;

import com.meli.demo.dto.CreateProductDTO;
import com.meli.demo.dto.ProductComparisonDTO;
import com.meli.demo.dto.RecoveryProductDTO;
import com.meli.demo.dto.UpdateProductDTO;
import com.meli.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<RecoveryProductDTO> createProduct(@RequestBody CreateProductDTO productDTO) {
        return new ResponseEntity<>(productService.createProduct(productDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<RecoveryProductDTO> getProductById(@PathVariable Long productId) {
        return new ResponseEntity<>(productService.getProductbyId(productId), HttpStatus.OK);
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<RecoveryProductDTO> updateProductPart(@PathVariable Long productId, @RequestBody UpdateProductDTO updateProductDTO) {
        return new ResponseEntity<>(productService.updateProductPart(productId, updateProductDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long productId) {
        productService.deleteProductById(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/compare")
    public List<ProductComparisonDTO> compareProducts(@RequestBody List<Long> productIds) {
        return productService.compareProducts(productIds);
    }

}
