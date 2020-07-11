package org.shop.service;

import lombok.AllArgsConstructor;
import org.shop.db.OrdersRepository;
import org.shop.db.entity.OrderEntity;
import org.shop.dto.OrderDetailDto;
import org.shop.dto.OrderDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class OrderServiceImpl implements OrdersService {

    private OrdersRepository ordersRepository;

    @Override
    public List<OrderDto> findAll() {
        return ordersRepository
                .findAll()
                .stream()
                .map(OrderEntity::convert)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto findOrderBy(long id) {
        return ordersRepository.findOrder(id).convert();
    }

    @Override
    public long saveOrder(OrderDto orderDto) {
        return ordersRepository.insertOrder(orderDto.convert());
    }

    @Override
    public void deleteOrder(long orderId) {
        ordersRepository.deleteOrder(orderId);
    }

    @Override
    public List<OrderDto> findOrdersWithPriceGreaterThen(double price) {
        return findAll()
                .stream()
                .filter(order -> order.getOrderDetails().stream().anyMatch(detail -> detail.getPrice() > price))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> findBigOrders() {
        return findAll().stream().filter(order -> order.getOrderDetails().size() > 3).collect(Collectors.toList());
    }

    /*Here I assume orderPrics is sum of prices in each details */
    @Override
    public double findOrderPrice(long orderId) {
        return findOrderBy(orderId)
                .getOrderDetails()
                .stream()
                .map(OrderDetailDto::getPrice)
                .reduce(Double::sum)
                .orElseThrow(() -> new RuntimeException("Price not found"));
    }
}
