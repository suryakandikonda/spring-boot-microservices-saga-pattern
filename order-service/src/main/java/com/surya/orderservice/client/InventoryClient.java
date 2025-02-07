package com.surya.orderservice.client;

import com.surya.orderservice.dto.InventoryRequest;
import com.surya.orderservice.dto.InventoryResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

public interface InventoryClient {

    final String INVENTORY_API = "/api/inventory";

    @GetExchange(INVENTORY_API+"/isInStock")
    boolean isInStock(@RequestParam String productId, @RequestParam Long quantity);

}
