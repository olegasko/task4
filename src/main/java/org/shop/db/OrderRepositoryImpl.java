package org.shop.db;

import lombok.AllArgsConstructor;
import org.shop.db.entity.OrderDetailEntity;
import org.shop.db.entity.OrderEntity;
import org.shop.exeptions.DatabaseException;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@PropertySource(value = {"classpath:application.properties"})
@Repository
@AllArgsConstructor
public class OrderRepositoryImpl implements OrdersRepository {

    private DbConnection dbConnection;
    private OrderDetailsRepository orderDetailsRepository;

    @Override
    public OrderEntity findOrder(long id) {
        OrderEntity order = null;
        String orderQuery = String.format("SELECT * FROM shop.order WHERE id=%s", id);
        try (Connection connection = dbConnection.connection(); Statement statement = connection.createStatement()) {
            ResultSet orderResult = statement.executeQuery(orderQuery);
            if (orderResult.next()) {
                String orderName = orderResult.getString("name");
                String orderClient = orderResult.getString("client");
                List<OrderDetailEntity> orderDetailEntities = orderDetailsRepository.findAllDetails(id);
                order = new OrderEntity(
                        id,
                        orderName,
                        orderClient,
                        orderDetailEntities);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage(), e);
        }
        if(order==null) {
            throw new RuntimeException(String.format("Order by id %d not found", id));
        } else {
            return order;
        }
    }

    @Override
    public List<OrderEntity> findAll() {
        List<OrderEntity> orderEntities = new ArrayList<>();
        OrderEntity order;
        String orderQuery = "SELECT * FROM shop.order";
        try (Connection connection = dbConnection.connection(); Statement statement = connection.createStatement()) {
            ResultSet orderResult = statement.executeQuery(orderQuery);
            while (orderResult.next()) {
                int id = orderResult.getInt("id");
                String orderName = orderResult.getString("name");
                String orderClient = orderResult.getString("client");
                List<OrderDetailEntity> orderDetailEntities = orderDetailsRepository.findAllDetails(id);
                order = new OrderEntity(
                        id,
                        orderName,
                        orderClient,
                        orderDetailEntities);
                orderEntities.add(order);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage(), e);
        }
        return orderEntities;
    }

    @Override
    public void deleteOrder(long id) {
        String deleteQuery = String.format("delete from shop.order where id=%s", id);
        try (Connection connection = dbConnection.connection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(deleteQuery);
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage(), e);
        }
    }

    @Override
    public void insertOrder(OrderEntity order) {
        String insertQuery = String.format("insert into shop.order (name, client) values (\"%s\", \"%s\")", order.getName(), order.getClient());
        try (Connection connection = dbConnection.connection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(insertQuery);
            for (OrderDetailEntity detailEntity : order.getOrderDetailEntities()) {
                orderDetailsRepository.insertDetail(detailEntity, order.getId());
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage(), e);
        }
    }
}
