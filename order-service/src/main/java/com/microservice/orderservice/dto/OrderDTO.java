package com.microservice.orderservice.dto;

import com.microservice.orderservice.model.OrderLineItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private String customerName;
    private String customerId;
    private List<OrderLineItem> lineItems;
}
