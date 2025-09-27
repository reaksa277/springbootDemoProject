package com.reaksa.demo.service;

import com.reaksa.demo.dto.Supplier.SupplierDto;
import com.reaksa.demo.dto.Supplier.SupplierResponseDto;
import com.reaksa.demo.dto.Supplier.UpdateSupplierDto;
import com.reaksa.demo.entity.Supplier;
import com.reaksa.demo.exception.model.DuplicateResourceException;
import com.reaksa.demo.exception.model.ResourceNotFoundException;
import com.reaksa.demo.mapper.SupplierMapper;
import com.reaksa.demo.model.BaseResponseModel;
import com.reaksa.demo.model.BaseResponseWithDataModel;
import com.reaksa.demo.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private SupplierMapper mapper;

    public List<SupplierResponseDto> listSupplier() {
        List<Supplier> suppliers = supplierRepository.findAll();

        return mapper.toDtoList(suppliers);
    }

    public void createSupplier(SupplierDto dto) {
        // if duplicate supplier name, then reject
        if(supplierRepository.existsByName(dto.getName())) {
            throw new DuplicateResourceException("supplier already exists with name : " +  dto.getName());
        }
        Supplier supplierEntity = mapper.toEntity(dto);
        supplierRepository.save(supplierEntity);
    }

    public void updateSupplier(Long supplierId, UpdateSupplierDto dto) {

        Supplier existingSupplier = supplierRepository.findById(supplierId)
                        .orElseThrow(() -> new ResourceNotFoundException("supplier not found with id : " + supplierId));
        mapper.updateEntityFromDto(existingSupplier, dto);

        supplierRepository.save(existingSupplier);
    }

    public void deleteSupplier(Long supplierId) {
        if(!supplierRepository.existsById(supplierId)) {
            throw new ResourceNotFoundException("supplier not found with id : " + supplierId);
        }

        supplierRepository.deleteById(supplierId);
    }

}
