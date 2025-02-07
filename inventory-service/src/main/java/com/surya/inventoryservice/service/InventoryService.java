package com.surya.inventoryservice.service;

import com.surya.inventoryservice.model.Inventory;
import com.surya.inventoryservice.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public Inventory findByProductId(String productId) {
        log.trace("findByProductId()");
        try {
            return inventoryRepository.findByProductId(productId);
        } catch (Exception e) {
            log.error("findByProductId() Exception:{}", e.getMessage());
        }
        return null;
    }

    public Inventory save(Inventory inventory) {
        log.trace("save()");
        try {
            return inventoryRepository.save(inventory);
        } catch (Exception e) {
            log.error("save() Exception:{}", e.getMessage());
        }
        return inventory;
    }

    public Inventory createInventory(Inventory inventory) {
        log.trace("createInventory()");
        try {
            Inventory existInventory = findByProductId(inventory.getProductId());
            if(existInventory != null) {
                inventory.setStockLeft(existInventory.getStockLeft() + inventory.getStockLeft());
                inventory.setId(existInventory.getId());
            }
            else
                inventory.setProductId(UUID.randomUUID().toString());

            return save(inventory);
        } catch (Exception e) {
            log.error("createInventory() Exception: {}", e.getMessage());
        }
        return inventory;
    }

    public boolean isInStock(String productId, long qty) {
        log.trace("isInStock()");
        try {
            Inventory inventory = findByProductId(productId);
            if(inventory != null && inventory.getStockLeft() >= qty) {
                return true;
            }
        } catch (Exception e) {
            log.error("isInStock() Exception: {}", e.getMessage());
        }
        return false;
    }

    public Inventory decreaseProductStock(String productId, long qty) {
        log.trace("decreaseProductStock()");
        try {
            Inventory inventory = findByProductId(productId);
            if(inventory != null) {
                inventory.setStockLeft(inventory.getStockLeft() - qty);
            }
            return save(inventory);
        } catch (Exception e) {
            log.error("decreaseProductStock() Exception: {}", e.getMessage());
        }
        return null;
    }

}
