package org.shop.db;

import org.shop.db.entity.OrderDetailEntity;

import java.util.List;

/**
 * Here you need to specify
 * all methods which you think will be useful to complete your task
 */
public interface OrderDetailsRepository {

    List<OrderDetailEntity> findAllDetails(long id);

    void insertDetail(OrderDetailEntity orderDetail, long id);
}
