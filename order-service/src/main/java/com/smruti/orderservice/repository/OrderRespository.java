package com.smruti.orderservice.repository;

import com.smruti.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRespository extends JpaRepository<Order,Long> {
}
