package com.reaksa.demo.controller;

import com.reaksa.demo.dto.Supplier.SupplierDto;
import com.reaksa.demo.dto.Supplier.SupplierResponseDto;
import com.reaksa.demo.dto.Supplier.UpdateSupplierDto;
import com.reaksa.demo.dto.base.PaginatedResponse;
import com.reaksa.demo.dto.base.Response;
import com.reaksa.demo.service.SupplierService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/suppliers")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    @GetMapping("/paginated")
    public ResponseEntity<Response> listSuppliersWithPagination(
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        PaginatedResponse<SupplierResponseDto> suppliers = supplierService.listSuppliersWithPagination(pageable);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success("200", "success", "successfully retrieved suppliers with pagination.", suppliers));
    }

    @GetMapping
    public ResponseEntity<Response> listSupplier() {
        List<SupplierResponseDto> suppliers = supplierService.listSupplier();

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success("200","success", "successfully retrieved suppliers", suppliers));
    }

    @PostMapping
    public ResponseEntity<Response> createSupplier(@Valid @RequestBody SupplierDto payload) {
        supplierService.createSupplier(payload);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Response.success("200","success", "successfully created supplier"));
    }

    @PutMapping("/{supplier_id}")
    public ResponseEntity<Response> updateSupplier(@PathVariable("supplier_id") Long supplierId, @Valid @RequestBody UpdateSupplierDto payload) {
        supplierService.updateSupplier(supplierId, payload);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success("200","success", "successfully updated supplier"));
    }

    @DeleteMapping("/{supplier_id}")
    public ResponseEntity<Response> deleteSupplier(@PathVariable("supplier_id") Long supplierId) {
        supplierService.deleteSupplier(supplierId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success("200","success", "successfully deleted supplier id: " + supplierId));
    }
}
