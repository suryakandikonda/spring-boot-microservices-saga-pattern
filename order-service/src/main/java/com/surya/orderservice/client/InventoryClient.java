package com.surya.orderservice.client;

import com.surya.microservices.dto.Inventory.InventoryRequest;
import com.surya.microservices.dto.Inventory.InventoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "inventoryClient", url = "http://inventory-service:8082/api/inventory/")
public interface InventoryClient {

    @GetMapping("/isInStock")
    boolean isInStock(@RequestParam String productId, @RequestParam long qty);

    @PostMapping("/decreaseProductStock")
    InventoryResponse decreaseProductStock(@RequestBody InventoryRequest inventoryRequest);

}
