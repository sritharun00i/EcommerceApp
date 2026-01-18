package com.learn.Ecom.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@FeignClient(name = "product-service")
public interface ProductClient {

    @GetMapping("/api/product/{id}")
    ProductDTO getProductById(@PathVariable("id") int id);

    @PutMapping("/api/product/{id}/reduceStock")
    void reduceStock(@PathVariable("id") int id, @RequestParam("quantity") int quantity);

    record ProductDTO(
            Integer id,
            String name,
            BigDecimal price,
            Integer stockQuantity,
            Boolean productAvailable,
            String category,
            String brand,
            String description,
            String imageName,
            String imageType,
            byte[] imageData) {
    }
}
