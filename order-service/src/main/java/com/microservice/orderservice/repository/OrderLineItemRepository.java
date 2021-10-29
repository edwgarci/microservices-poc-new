package com.microservice.orderservice.repository;

import com.microservice.orderservice.model.OrderLineItem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderLineItemRepository extends MongoRepository<OrderLineItem, String> {
}
