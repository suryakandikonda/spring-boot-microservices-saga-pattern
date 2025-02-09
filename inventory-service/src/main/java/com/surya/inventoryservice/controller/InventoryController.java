package com.surya.inventoryservice.controller;

import com.surya.inventoryservice.service.InventoryService;
import com.surya.microservices.dto.Inventory.InventoryRequest;
import com.surya.microservices.dto.Inventory.InventoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping
    public InventoryResponse createInventory(@RequestBody InventoryRequest inventoryRequest) {
        try {
            return inventoryService.createInventory(inventoryRequest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/getByProductId")
    public InventoryResponse getByProductId(@RequestParam("productId") String productId) {
        try {
            return inventoryService.findByProductId(productId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/isInStock")
    public Boolean isInStock(@RequestParam String productId, @RequestParam long qty) {
        return inventoryService.isInStock(productId, qty);
    }

    @PostMapping("/decreaseProductStock")
    public InventoryResponse decreaseProductStock(@RequestBody InventoryRequest inventoryRequest) {
        return inventoryService.decreaseProductStock(inventoryRequest);
    }

}
