package com.bramaLog.Product;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.UUID;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR, componentModel = "spring", imports = UUID.class)
public interface ProductMapper {

    @Mapping(target = "id", expression = "java(UUID.randomUUID().toString())")
    @Mapping(target = "estimation", ignore = true)
    ProductEntity toEntity(CreateProductRequest createProductRequest);

    CreateProductResponse toDto(ProductEntity productEntity);
}

