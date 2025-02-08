package com.surya.orderservice.client;

import com.surya.orderservice.dto.Inventory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "inventoryClient", url = "http://localhost:8082/api/inventory/")
public interface InventoryClient {

    @GetMapping("/isInStock")
    boolean isInStock(@RequestParam String productId, @RequestParam long qty);

    @PostMapping("/decreaseProductStock")
    Inventory decreaseProductStock(@RequestBody Inventory inventory);

}
