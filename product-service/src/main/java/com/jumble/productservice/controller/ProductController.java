package com.jumble.productservice.controller;

import com.jumble.productservice.model.Product;
import com.jumble.productservice.model.ProductAvailabilityRequest;
import com.jumble.productservice.model.ProductAvailabilityResponse;
import com.jumble.productservice.service.ProductAvailabilityService;
import com.jumble.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductAvailabilityService productAvailabilityService;

    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.ok(createdProduct);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        Product updatedProduct = productService.updateProduct(id, productDetails);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/availability")
    public ResponseEntity<ProductAvailabilityResponse> checkAvailability(@RequestBody ProductAvailabilityRequest request) {
        ProductAvailabilityResponse response = productAvailabilityService.checkAvailability(request);
        return ResponseEntity.ok(response);
    }
}
