package com.example.masterwork.order.repositories;

import com.example.masterwork.order.models.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, Integer> {

  Optional<Order> findById(Integer id);

}
