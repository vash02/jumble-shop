package com.jumble.productservice;

import com.jumble.productservice.model.Product;
import com.jumble.productservice.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    @Transactional
    public void testConcurrentUpdate() throws InterruptedException {
        Long productId = 1L;
        Product updatedDetails1 = new Product("Product1","description1", 100.0, 100);
        Product updatedDetails2 = new Product("Product2","description2", 200.0, 200);

        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(() -> productService.updateProduct(productId, updatedDetails1));
        executor.submit(() -> productService.updateProduct(productId, updatedDetails2));

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
    }
}
