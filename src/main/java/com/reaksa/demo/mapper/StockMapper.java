package com.reaksa.demo.mapper;

import com.reaksa.demo.dto.Stock.StockDto;
import com.reaksa.demo.dto.Stock.StockResponseDto;
import com.reaksa.demo.entity.Stock;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StockMapper {

    public Stock toEntity(StockDto dto) {
        Stock entity = new Stock();

        entity.setProductId(dto.getProductId());
        entity.setQuantity(dto.getQuantity());

        return entity;
    }

    // update stock
/*
    public void updateEntityFromDto(Stock entity, StockDto dto) {

        if ( entity == null || dto == null ) {
            return;
        }

        entity.setProductId(dto.getProductId());
        entity.setQuantity(dto.getQuantity());
    }

 */

    public StockResponseDto toDto(Stock entity) {
        StockResponseDto dto = new StockResponseDto();

        dto.setId(entity.getId());
        dto.setProductId(entity.getProductId());
        dto.setQty(entity.getQuantity());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        return dto;
    }

    public List<StockResponseDto> toListDto(List<Stock> entities) {
        if (entities == null || entities.isEmpty()) {
            return new ArrayList<>();
        }

        return entities.stream()
                .map(stock -> this.toDto(stock))
                .collect(Collectors.toList());
    }
}
