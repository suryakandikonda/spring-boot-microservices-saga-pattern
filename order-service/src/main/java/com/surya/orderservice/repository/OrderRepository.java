package com.surya.orderservice.repository;

import com.surya.orderservice.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

    Order findByOrderNumber(String orderNumber);

}
