package com.surya.inventoryservice.controller;

import com.surya.inventoryservice.model.Inventory;
import com.surya.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping
    public Inventory createInventory(@RequestBody Inventory inventory) {
        return inventoryService.createInventory(inventory);
    }

    @GetMapping("/getByProductId")
    public Inventory getByProductId(@RequestParam("productId") String productId) {
        return inventoryService.findByProductId(productId);
    }

    @GetMapping("/isInStock")
    public Boolean isInStock(@RequestParam String productId, @RequestParam long qty) {
        return inventoryService.isInStock(productId, qty);
    }

    @PostMapping("/decreaseProductStock")
    public Inventory decreaseProductStock(@RequestBody Inventory inventory) {
        return inventoryService.decreaseProductStock(inventory.getProductId(), inventory.getQty());
    }

}
