package com.bramaLog.Client;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.InjectionStrategy;
import java.util.UUID;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, imports = UUID.class)
public interface ClientMapper {

    @Mapping(target = "id", expression = "java(UUID.randomUUID().toString())")
    ClientEntity toEntity(ClientRequestDTO requestDTO);

    ClientResponseDTO toResponseDto(ClientEntity clientEntity);
}
