package com.reaksa.demo.model;

import com.reaksa.demo.entity.Product;
import lombok.Getter;

import java.util.List;

@Getter
public class BaseResponseWithDataModel extends BaseResponseModel {
    private Object products;

    public BaseResponseWithDataModel(String status, String message, Object products) {
        super(status, message);
        this.products = products;
    }
}
