package com.bramaLog.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductEntity addProduct(ProductEntity productEntity) {
        ProductEntity.Estimation estimation = calculateEstimation(productEntity.getBacklog());
        productEntity.setEstimation(estimation);
        return productRepository.save(productEntity);
    }

    public ProductEntity updateProduct(UUID id, ProductEntity updatedProduct) {
        return productRepository.findById(id).map(existingProduct -> {
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setBacklog(updatedProduct.getBacklog());

            // Recalculate estimation when the product is updated
            ProductEntity.Estimation newEstimation = calculateEstimation(updatedProduct.getBacklog());
            existingProduct.setEstimation(newEstimation);

            return productRepository.save(existingProduct);
        }).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<ProductEntity> getProductById(UUID id) {
        return productRepository.findById(id);
    }

    private ProductEntity.Estimation calculateEstimation(List<ProductEntity.Pbi> backlog) {
        ProductEntity.Estimation estimation = new ProductEntity.Estimation();


        double totalCost = backlog.stream()
                .mapToDouble(ProductEntity.Pbi::getEstimatedCost)
                .sum();

        // Calculate total duration (assuming 1 story point = 1 day)
        int totalStoryPoints = backlog.stream()
                .mapToInt(ProductEntity.Pbi::getStoryPoints)
                .sum();
        String totalDuration = totalStoryPoints + " days";

        estimation.setTotalCost(totalCost);
        estimation.setTotalDuration(totalDuration);

        return estimation;
    }
}
