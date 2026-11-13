package com.shopzone.controller;

import com.shopzone.model.Product;
import com.shopzone.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductRepository products;
    public ProductController(ProductRepository products){this.products=products;}

    @GetMapping
    public List<Product> all(@RequestParam(required=false) String category,
                             @RequestParam(required=false) String q) {
        if(category!=null && !category.isBlank()) return products.findByCategoryIgnoreCase(category);
        if(q!=null && !q.isBlank()) return products.findByNameContainingIgnoreCase(q);
        return products.findAll();
    }
    @GetMapping("/{id}")
    public Product one(@PathVariable Long id) {
        return products.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found"));
    }
}
