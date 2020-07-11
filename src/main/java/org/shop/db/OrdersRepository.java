package org.shop.db;

import org.shop.db.entity.OrderEntity;

import java.util.List;

/**
 * Here you need to specify
 * all methods which you think will be useful to complete your task
 */
public interface OrdersRepository {

    OrderEntity findOrder(long id);

    List<OrderEntity> findAll();

    void deleteOrder(long id);

    long insertOrder(OrderEntity order);

}
