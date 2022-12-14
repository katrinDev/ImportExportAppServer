package org.project.services;

import org.project.dao.OperationDAO;
import org.project.entities.Order;
import org.project.entities.TradeOperation;
import org.project.interfaces.Service;

import java.util.List;

public class OperationService implements Service<TradeOperation> {
    private static final OperationDAO operationDAO = new OperationDAO();

    private static int tradeId;

    public static int getTradeId() {
        return tradeId;
    }

    @Override
    public TradeOperation findEntity(int id) {
        return operationDAO.findById(id);
    }

    @Override
    public int saveEntity(TradeOperation operation) {

        tradeId = operationDAO.save(operation);
        return tradeId;
    }

    @Override
    public void deleteEntity(TradeOperation operation) {
        operationDAO.delete(operation);
    }

    @Override
    public void updateEntity(TradeOperation operation) {
        operationDAO.update(operation);
    }

    @Override
    public List<TradeOperation> findAllEntities() {
        return operationDAO.findAll();
    }
}
