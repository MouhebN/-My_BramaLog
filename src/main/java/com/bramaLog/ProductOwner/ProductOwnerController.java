package com.bramaLog.ProductOwner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/product-owners")
public class ProductOwnerController {

    private final ProductOwnerService productOwnerService;

    @Autowired
    public ProductOwnerController(ProductOwnerService productOwnerService) {
        this.productOwnerService = productOwnerService;
    }

    @PostMapping
    public ResponseEntity<ProductOwnerEntity> addProductOwner(@RequestBody ProductOwnerEntity productOwner) {
        ProductOwnerEntity savedOwner = productOwnerService.addProductOwner(productOwner);
        return ResponseEntity.ok(savedOwner);
    }

    @GetMapping
    public ResponseEntity<List<ProductOwnerEntity>> getAllProductOwners() {
        List<ProductOwnerEntity> owners = productOwnerService.getAllProductOwners();
        return ResponseEntity.ok(owners);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductOwnerEntity> getProductOwnerById(@PathVariable UUID id) {
        Optional<ProductOwnerEntity> owner = productOwnerService.getProductOwnerById(id);
        return owner.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
