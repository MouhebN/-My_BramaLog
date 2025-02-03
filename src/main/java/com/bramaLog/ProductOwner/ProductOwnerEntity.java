package com.bramaLog.ProductOwner;

import com.bramaLog.Product.ProductEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Document(collection = "ProductOwners")
@Getter
@Setter
public class ProductOwnerEntity {
    @Id
    private UUID id = UUID.randomUUID();

    private String name;
    private String email;
    private String password;

    @DBRef
    private List<ProductEntity> products = new ArrayList<>();
}
