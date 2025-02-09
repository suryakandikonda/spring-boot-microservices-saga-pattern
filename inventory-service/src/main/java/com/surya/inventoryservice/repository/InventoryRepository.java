package com.surya.inventoryservice.repository;

import com.surya.microservices.model.Inventory;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends MongoRepository<Inventory, ObjectId> {

    Inventory findByProductId(String productId);

}
