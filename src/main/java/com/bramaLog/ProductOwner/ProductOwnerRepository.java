package com.bramaLog.ProductOwner;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface ProductOwnerRepository extends MongoRepository<ProductOwnerEntity, UUID> {}
