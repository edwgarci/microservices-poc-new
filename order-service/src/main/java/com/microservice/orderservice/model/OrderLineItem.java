package com.microservice.orderservice.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;

@Document(value = "orderlineitems")
@Builder
@Data
public class OrderLineItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String code;
    private BigDecimal price;
    private Integer quantity;
}
