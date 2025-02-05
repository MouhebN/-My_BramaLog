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

        productEntity.getBacklog().forEach(pbi -> {
            if (pbi.getStatus() == null) {
                pbi.setStatus(PbiStatus.A_DISCUTER);
            }
        });
        ProductEntity.Estimation estimation = calculateEstimation(productEntity.getBacklog());
        productEntity.setEstimation(estimation);
        ProductEntity savedEntity = productRepository.save(productEntity);
        return productMapper.toDto(savedEntity);
    }

    public CreateProductResponse updateProduct(String id, CreateProductRequest updatedProductRequest) {
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

    public Optional<CreateProductResponse> getProductById(String id) {
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

    public CreateProductResponse updatePbiStatus(String productId, String pbiId, UpdatePbiStatusRequest statusRequest) {
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

    public CreateProductResponse updatePbi(String productId, String pbiId, UpdatePbiRequest updatePbiRequest) {
        return productRepository.findById(productId).map(product -> {  // Use productId as String
            // Find the PBI to update
            ProductEntity.Pbi targetPbi = product.getBacklog().stream()
                    .filter(pbi -> pbi.getId().equals(pbiId))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("PBI not found"));

            // Update only provided fields
            if (updatePbiRequest.title() != null) targetPbi.setTitle(updatePbiRequest.title());
            if (updatePbiRequest.description() != null) targetPbi.setDescription(updatePbiRequest.description());
            if (updatePbiRequest.priority() != null) targetPbi.setPriority(updatePbiRequest.priority());
            if (updatePbiRequest.storyPoints() != null) targetPbi.setStoryPoints(updatePbiRequest.storyPoints());
            if (updatePbiRequest.estimatedCost() != null) targetPbi.setEstimatedCost(updatePbiRequest.estimatedCost());
            if (updatePbiRequest.businessValue() != null) targetPbi.setBusinessValue(updatePbiRequest.businessValue());
            if (updatePbiRequest.acceptanceCriteria() != null) targetPbi.setAcceptanceCriteria(updatePbiRequest.acceptanceCriteria());
            if (updatePbiRequest.status() != null) targetPbi.setStatus(updatePbiRequest.status());

            // Save the updated product with modified PBI
            ProductEntity savedEntity = productRepository.save(product);
            return productMapper.toDto(savedEntity);
        }).orElseThrow(() -> new RuntimeException("Product not found"));
    }
}
