package com.reaksa.demo.mapper;

import com.reaksa.demo.dto.Supplier.SupplierDto;
import com.reaksa.demo.dto.Supplier.SupplierResponseDto;
import com.reaksa.demo.dto.Supplier.UpdateSupplierDto;
import com.reaksa.demo.entity.Supplier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SupplierMapper {

    public Supplier toEntity(SupplierDto dto) {
        Supplier entity = new Supplier();

        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());
        entity.setRating(dto.getRating());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());

        return entity;
    }

    public SupplierResponseDto toDto(Supplier supplier) {
        SupplierResponseDto dto = new SupplierResponseDto();

        dto.setId(supplier.getId());
        dto.setSupplierName(supplier.getName());
        dto.setAddress(supplier.getAddress());
        dto.setRating(supplier.getRating());
        dto.setPhone(supplier.getPhone());
        dto.setEmail(supplier.getEmail());
        dto.setCreatedAt(supplier.getCreatedAt());
        dto.setUpdatedAt(supplier.getUpdatedAt());

        return dto;
    }

    public List<SupplierResponseDto> toDtoList(List<Supplier> entities) {
        if (entities == null ) {
            return new ArrayList<>();
        }

        return entities.stream()
                .map(supplier -> this.toDto(supplier))
                .collect(Collectors.toList());
    }

    public void updateEntityFromDto(Supplier entity, UpdateSupplierDto dto) {
        if (entity == null ||  dto == null) {
            return;

        }
        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());
        entity.setRating(dto.getRating());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
    }
}
