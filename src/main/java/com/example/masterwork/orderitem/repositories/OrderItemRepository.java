package com.example.masterwork.orderitem.repositories;

import com.example.masterwork.orderitem.models.OrderItem;
import org.springframework.data.repository.CrudRepository;

public interface OrderItemRepository extends CrudRepository<OrderItem, Integer> {

}
