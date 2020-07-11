package org.shop.db;

import lombok.AllArgsConstructor;
import org.shop.db.entity.OrderDetailEntity;
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
public class OrderDetailsRepositoryImpl implements OrderDetailsRepository {

    private DbConnection dbConnection;

    @Override
    public List<OrderDetailEntity> findAllDetails(long id) {
        String detailsQuery = String.format("SELECT * FROM shop.order_detail WHERE order_id=%s", id);
        List<OrderDetailEntity> orderDetailEntities = new ArrayList<>();
        try (Connection connection = dbConnection.connection(); Statement statement = connection.createStatement()) {
            ResultSet detailsResult = statement.executeQuery(detailsQuery);
            while (detailsResult.next()) {
                OrderDetailEntity entity = new OrderDetailEntity(
                        detailsResult.getInt("id"),
                        detailsResult.getString("name"),
                        detailsResult.getDouble("price"));
                orderDetailEntities.add(entity);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage(), e);
        }
        return orderDetailEntities;
    }

    @Override
    public void insertDetail(OrderDetailEntity orderDetail, long id) {
        String insertQuery = String.format("insert into shop.order_detail (name, price, order_id) values (\"%s\", %.3f, %d);",
                orderDetail.getName(),
                orderDetail.getPrice(),
                id);
        try (Connection connection = dbConnection.connection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(insertQuery);
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage(), e);
        }
    }
}
