package com.bramaLog.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public CreateProductResponse addProduct(CreateProductRequest createProductRequest) {
        ProductEntity productEntity = productMapper.toEntity(createProductRequest);
        ProductEntity.Estimation estimation = calculateEstimation(productEntity.getBacklog());
        productEntity.setEstimation(estimation);
        ProductEntity savedEntity = productRepository.save(productEntity);
        return productMapper.toDto(savedEntity);
    }

    public CreateProductResponse updateProduct(UUID id, CreateProductRequest updatedProductRequest) {
        return productRepository.findById(id).map(existingProduct -> {
            ProductEntity updatedProduct = productMapper.toEntity(updatedProductRequest);
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setBacklog(updatedProduct.getBacklog());

            ProductEntity.Estimation newEstimation = calculateEstimation(updatedProduct.getBacklog());
            existingProduct.setEstimation(newEstimation);

            ProductEntity savedEntity = productRepository.save(existingProduct);
            return productMapper.toDto(savedEntity);
        }).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public List<CreateProductResponse> getAllProducts() {
        return productRepository.findAll().stream().map(productMapper::toDto).toList();
    }

    public Optional<CreateProductResponse> getProductById(UUID id) {
        return productRepository.findById(id).map(productMapper::toDto);
    }

    private ProductEntity.Estimation calculateEstimation(List<ProductEntity.Pbi> backlog) {
        ProductEntity.Estimation estimation = new ProductEntity.Estimation();

        double totalCost = backlog.stream()
                .mapToDouble(ProductEntity.Pbi::getEstimatedCost)
                .sum();

        int totalStoryPoints = backlog.stream()
                .mapToInt(ProductEntity.Pbi::getStoryPoints)
                .sum();
        String totalDuration = totalStoryPoints + " days";

        estimation.setTotalCost(totalCost);
        estimation.setTotalDuration(totalDuration);

        return estimation;
    }

    public CreateProductResponse updatePbiStatus(UUID productId, UUID pbiId, UpdatePbiStatusRequest statusRequest) {
        return productRepository.findById(productId).map(product -> {
            ProductEntity.Pbi targetPbi = product.getBacklog().stream()
                    .filter(pbi -> pbi.getId().equals(pbiId))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("PBI not found"));

            targetPbi.setStatus(statusRequest.status());
            ProductEntity savedEntity = productRepository.save(product);
            return productMapper.toDto(savedEntity);
        }).orElseThrow(() -> new RuntimeException("Product not found"));
    }
}
