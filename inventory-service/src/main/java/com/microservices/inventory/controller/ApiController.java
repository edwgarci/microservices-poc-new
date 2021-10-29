package com.microservices.inventory.controller;

import com.microservices.inventory.model.Inventory;
import com.microservices.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class ApiController {

    @Autowired
    private InventoryRepository inventoryRepository;

    @GetMapping("/all")
    public List<Inventory> getInventory(){
        return inventoryRepository.findAll();
    }

    @GetMapping("/{code}")
    Boolean isInStock(@PathVariable String code){
        Inventory object = inventoryRepository.findByCode(code).get();
        return object.getStock() > 0;
    }
}
