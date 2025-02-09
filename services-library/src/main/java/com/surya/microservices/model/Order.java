package com.surya.microservices.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {

    @Id
    private String id;

    private String orderNumber;

    private String productId;

    private BigDecimal price;

    private Long quantity;

    private String orderStatus;

}
