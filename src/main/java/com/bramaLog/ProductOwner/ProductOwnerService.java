package com.bramaLog.ProductOwner;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductOwnerService {

    private final ProductOwnerRepository productOwnerRepository;

    @Autowired
    public ProductOwnerService(ProductOwnerRepository productOwnerRepository) {
        this.productOwnerRepository = productOwnerRepository;
    }

    public ProductOwnerEntity addProductOwner(ProductOwnerEntity productOwner) {
        return productOwnerRepository.save(productOwner);
    }

    public List<ProductOwnerEntity> getAllProductOwners() {
        return productOwnerRepository.findAll();
    }

    public Optional<ProductOwnerEntity> getProductOwnerById(UUID id) {
        return productOwnerRepository.findById(id);
    }
}
