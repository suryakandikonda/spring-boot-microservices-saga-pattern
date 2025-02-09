package com.surya.inventoryservice.service;

import com.surya.inventoryservice.repository.InventoryRepository;
import com.surya.microservices.dto.Inventory.InventoryRequest;
import com.surya.microservices.dto.Inventory.InventoryResponse;
import com.surya.microservices.model.Inventory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    private Inventory getInventoryByProductId(String productId) {
        log.trace("getInventoryByProductId()");
        try {
            return inventoryRepository.findByProductId(productId);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("getInventoryByProductId() Exception: {}", e.getMessage());
        }
        return null;
    }

    private Inventory save(Inventory inventory) {
        log.trace("save()");
        try {
            return inventoryRepository.save(inventory);
        } catch (Exception e) {
            log.error("save() Exception:{}", e.getMessage());
        }
        return inventory;
    }

    public InventoryResponse findByProductId(String productId) throws Exception {
        log.trace("findByProductId()");
        try {
            Inventory inventory = getInventoryByProductId(productId);
            if(inventory !=  null) {
                return new InventoryResponse(inventory.getProductId(), inventory.getProductName(), inventory.getStockLeft());
            } else throw new Exception("Inventory Not Found");
        } catch (Exception e) {
            log.error("findByProductId() Exception:{}", e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    public InventoryResponse createInventory(InventoryRequest inventoryRequest) throws Exception {
        log.trace("createInventory()");
        try {
            Inventory existInventory = getInventoryByProductId(inventoryRequest.productId());
            if(existInventory != null) {
                existInventory.setStockLeft(existInventory.getStockLeft() + inventoryRequest.qty());
            }
            else {
                existInventory = Inventory.builder().productId(UUID.randomUUID().toString())
                                    .productName(inventoryRequest.productName())
                                    .stockLeft(inventoryRequest.qty())
                                    .build();
            }

            existInventory = save(existInventory);
            return new InventoryResponse(existInventory.getProductId(), existInventory.getProductName(), existInventory.getStockLeft());
        } catch (Exception e) {
            log.error("createInventory() Exception: {}", e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    public boolean isInStock(String productId, long qty) {
        log.trace("isInStock()");
        try {
            Inventory inventory = getInventoryByProductId(productId);
            if(inventory != null && inventory.getStockLeft() >= qty) {
                return true;
            }
        } catch (Exception e) {
            log.error("isInStock() Exception: {}", e.getMessage());
        }
        return false;
    }

    public InventoryResponse decreaseProductStock(InventoryRequest inventoryRequest) {
        log.trace("decreaseProductStock()");
        try {
            Inventory inventory = getInventoryByProductId(inventoryRequest.productId());
            if(inventory != null) {
                inventory.setStockLeft(inventory.getStockLeft() - inventoryRequest.qty());
            }
            inventory = save(inventory);
            return new InventoryResponse(inventory.getProductId(), inventory.getProductName(), inventory.getStockLeft());
        } catch (Exception e) {
            log.error("decreaseProductStock() Exception: {}", e.getMessage());
        }
        return null;
    }

    public InventoryResponse increaseProductStock(InventoryRequest inventoryRequest) {
        log.trace("increaseProductStock()");
        try {
            Inventory inventory = getInventoryByProductId(inventoryRequest.productId());
            if(inventory != null) {
                inventory.setStockLeft(inventory.getStockLeft() + inventoryRequest.qty());
            }
            inventory = save(inventory);
            return new InventoryResponse(inventory.getProductId(), inventory.getProductName(), inventory.getStockLeft());
        } catch (Exception e) {
            log.error("increaseProductStock() Exception: {}", e.getMessage());
        }
        return null;
    }

}
