package com.microservices.inventory.repository;

import com.microservices.inventory.model.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface InventoryRepository extends MongoRepository<Inventory, Long> {

    Optional<Inventory> findByCode(String code);

}
