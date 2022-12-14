package org.project.services;

import org.project.dao.OrderDAO;
import org.project.entities.Order;
import org.project.interfaces.Service;

import java.util.List;

public class OrderService implements Service<Order> {
    private static final OrderDAO orderDAO = new OrderDAO();

    @Override
    public Order findEntity(int id) {
        return orderDAO.findById(id);
    }

    @Override
    public int saveEntity(Order order) {

        return orderDAO.save(order);
    }

    @Override
    public void deleteEntity(Order order) {
        orderDAO.delete(order);
    }

    @Override
    public void updateEntity(Order order) {
        orderDAO.update(order);
    }

    @Override
    public List<Order> findAllEntities() {
        return orderDAO.findAll();
    }
}
