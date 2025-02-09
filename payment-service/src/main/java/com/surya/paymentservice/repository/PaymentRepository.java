package com.surya.paymentservice.repository;

import com.surya.microservices.model.Payment;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends MongoRepository<Payment, ObjectId> {

    Payment findByOrderNumber(String orderNumber);

}
