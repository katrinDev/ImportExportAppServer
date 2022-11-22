package org.project.services;

import org.project.dao.UserDAO;
import org.project.entities.User;
import org.project.interfaces.Service;

import java.util.List;

public class UserService implements Service<User> {
    private static final UserDAO userDAO = new UserDAO();

    @Override
    public User findEntity(int id) {
        return userDAO.findById(id);
    }

    @Override
    public void saveEntity(User user) {
        userDAO.save(user);
    }

    @Override
    public void deleteEntity(User user) {
        userDAO.delete(user);
    }

    @Override
    public void updateEntity(User user) {
        userDAO.update(user);
    }

    @Override
    public List<User> findAllEntities() {
        return userDAO.findAll();
    }
}
