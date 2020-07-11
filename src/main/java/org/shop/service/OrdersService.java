package org.shop.service;

import org.shop.dto.OrderDto;

import java.util.List;

public interface OrdersService {

    /**
     * @return all orders and their details
     */
    List<OrderDto> findAll();

    /**
     * @param id - order unique identifier
     * @return order and its' details
     */
    OrderDto findOrderBy(long id);

    long saveOrder(OrderDto orderDto);

    void deleteOrder(long orderId);

    /**
     * this method should find
     */
    List<OrderDto> findOrdersWithPriceGreaterThen(double price);

    /**
     * the order is considered to be big if it has more then 3 order details
     *
     * @return
     */
    List<OrderDto> findBigOrders();

    double findOrderPrice(long orderId);

}
