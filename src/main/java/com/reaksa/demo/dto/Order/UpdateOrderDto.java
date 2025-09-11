package com.reaksa.demo.dto.Order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.reaksa.demo.common.annotations.ValidEnum;
import com.reaksa.demo.common.enums.OrderStatus;
import lombok.Data;

@Data
public class UpdateOrderDto {
    @JsonProperty("status")
    @ValidEnum(enumClass = OrderStatus.class, message = "Value must be one of PENDING, FAILED, SUCCESS")
    private String status;
}
