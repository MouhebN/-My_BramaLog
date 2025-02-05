package com.bramaLog.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<CreateProductResponse> addProduct(@RequestBody CreateProductRequest createProductRequest) {
        CreateProductResponse savedProduct = productService.addProduct(createProductRequest);
        return ResponseEntity.ok(savedProduct);
    }

    @GetMapping
    public ResponseEntity<List<CreateProductResponse>> getAllProducts() {
        List<CreateProductResponse> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreateProductResponse> getProductById(@PathVariable String id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CreateProductResponse> updateProduct(
            @PathVariable String id,
            @RequestBody CreateProductRequest updatedProductRequest) {
        CreateProductResponse updatedProduct = productService.updateProduct(id, updatedProductRequest);
        return ResponseEntity.ok(updatedProduct);
    }
    
    @PatchMapping("/{productId}/backlog/{pbiId}/status")
    public ResponseEntity<CreateProductResponse> updatePbiStatus(
            @PathVariable String productId,
            @PathVariable String pbiId,
            @RequestBody UpdatePbiStatusRequest statusRequest) {
        CreateProductResponse updatedProduct = productService.updatePbiStatus(productId, pbiId, statusRequest);
        return ResponseEntity.ok(updatedProduct);
    }
    @PatchMapping("/{productId}/backlog/{pbiId}")
    public ResponseEntity<CreateProductResponse> updatePbi(
            @PathVariable String productId,
            @PathVariable String pbiId,
            @RequestBody UpdatePbiRequest updatePbiRequest) {
        CreateProductResponse updatedProduct = productService.updatePbi(productId, pbiId, updatePbiRequest);
        return ResponseEntity.ok(updatedProduct);
    }
}
