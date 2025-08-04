package com.reaksa.demo.dto.Supplier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierDto {
    private String name;
    private String address;
    private String rating;
    private String phone;
    private String email;
}
