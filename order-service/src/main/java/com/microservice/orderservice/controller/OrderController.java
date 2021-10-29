package com.microservice.orderservice.controller;

import com.microservice.orderservice.client.InventoryClient;
import com.microservice.orderservice.dto.OrderDTO;
import com.microservice.orderservice.model.Order;
import com.microservice.orderservice.model.OrderLineItem;
import com.microservice.orderservice.repository.OrderLineItemRepository;
import com.microservice.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreaker;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

@RefreshScope
@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Value("${test.name}")
    private String name;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderLineItemRepository lineItemRepository;

    @Autowired
    private InventoryClient inventoryClient;

    @Autowired
    private Resilience4JCircuitBreakerFactory jCircuitBreakerFactory;

    @Autowired
    private ExecutorService traceableExecutorService;

    @GetMapping("/test")
    public String getRefreshedScope() {
        return name;
    }

    @GetMapping("/all")
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @PostMapping("/placeOrder")
    public String placeOrder(@RequestBody OrderDTO orderDTO) {
        jCircuitBreakerFactory.configureExecutorService(traceableExecutorService);
        Resilience4JCircuitBreaker circuitBreaker = jCircuitBreakerFactory.create("inventory");
        java.util.function.Supplier<Boolean> booleanSupplier = () -> orderDTO.getLineItems().stream()
                .allMatch(lineItem -> {
                    return inventoryClient.checkStock(lineItem.getCode());
                });
        boolean allItemsInStock = circuitBreaker.run(booleanSupplier, throwable -> handleErrorCase(throwable));

        if (allItemsInStock) {
            Order order = new Order();

            order.setCustomerId(orderDTO.getCustomerId());
            order.setCustomerName(orderDTO.getCustomerName());
            order.setOrderNumber(UUID.randomUUID().toString());
            List<OrderLineItem> orderLineItems = new ArrayList<>();
            for (OrderLineItem lineItem : orderDTO.getLineItems()) {
                lineItem.setId(UUID.randomUUID().toString());
                OrderLineItem savedItem = lineItemRepository.save(lineItem);
                orderLineItems.add(savedItem);
            }
            order.setLineItems(orderLineItems);
            orderRepository.save(order);
            return order.getOrderNumber();
        }
        return "Out Of Stock";
    }

    private Boolean handleErrorCase(Throwable throwable) {
        System.out.println(throwable.getMessage());
        return false;
    }
}