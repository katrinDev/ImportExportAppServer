package org.project.services;

import org.project.dao.ItemDAO;
import org.project.entities.Item;
import org.project.interfaces.Service;

import java.util.List;

public class ItemService implements Service<Item> {
    private static final ItemDAO itemDAO = new ItemDAO();

    @Override
    public Item findEntity(int id) {
        return itemDAO.findById(id);
    }

    @Override
    public int saveEntity(Item item) {

        return itemDAO.save(item);
    }

    @Override
    public void deleteEntity(Item item) {
        itemDAO.delete(item);
    }

    @Override
    public void updateEntity(Item item) {
        itemDAO.update(item);
    }

    @Override
    public List<Item> findAllEntities() {
        return itemDAO.findAll();
    }
}
