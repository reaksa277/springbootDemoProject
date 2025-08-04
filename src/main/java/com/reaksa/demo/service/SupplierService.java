package com.reaksa.demo.service;

import com.reaksa.demo.dto.Supplier.SupplierDto;
import com.reaksa.demo.dto.Supplier.SupplierResponseDto;
import com.reaksa.demo.dto.Supplier.UpdateSupplierDto;
import com.reaksa.demo.entity.Supplier;
import com.reaksa.demo.mapper.SupplierMapper;
import com.reaksa.demo.model.BaseResponseModel;
import com.reaksa.demo.model.BaseResponseWithDataModel;
import com.reaksa.demo.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private SupplierMapper mapper;

    public ResponseEntity<BaseResponseWithDataModel> listSupplier() {
        List<Supplier> suppliers = supplierRepository.findAll();

        List<SupplierResponseDto> dtos = mapper.toDtoList(suppliers);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseWithDataModel("success", "successfully list products", dtos));
    }

    public ResponseEntity<BaseResponseModel> createSupplier(SupplierDto dto) {
        // if duplicate supplier name, then reject
        if(supplierRepository.existsByName(dto.getName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new BaseResponseModel("error", "supplier already exists name: " + dto.getName()));
        }
        Supplier supplierEntity = mapper.toEntity(dto);
        supplierRepository.save(supplierEntity);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new BaseResponseModel("success", "successfully created supplier"));
    }

    public ResponseEntity<BaseResponseModel> updateSupplier(Long supplierId, UpdateSupplierDto dto) {
        Optional<Supplier> existingSupplier = supplierRepository.findById(supplierId);

        // if supplier not found return 404
        if(existingSupplier.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponseModel("error", "supplier not found id: " + supplierId));
        }

        Supplier supplier = existingSupplier.get();
        mapper.updateEntityFromDto(supplier, dto);

        supplierRepository.save(supplier);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModel("success", "successfully updated supplier"));
    }

    public ResponseEntity<BaseResponseModel> deleteSupplier(Long supplierId) {
        if(!supplierRepository.existsById(supplierId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponseModel("error", "supplier not found id: " + supplierId));
        }

        supplierRepository.deleteById(supplierId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new BaseResponseModel("success", "successfully deleted supplier"));
    }

}
