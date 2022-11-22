package org.project.services;

import org.project.dao.PersonDAO;
import org.project.entities.Person;
import org.project.interfaces.Service;

import java.util.List;

public class PersonService implements Service<Person> {
    private static final PersonDAO personDAO = new PersonDAO();

    @Override
    public Person findEntity(int id) {
        return personDAO.findById(id);
    }

    @Override
    public void saveEntity(Person person) {
        personDAO.save(person);
    }

    @Override
    public void deleteEntity(Person person) {
        personDAO.delete(person);
    }

    @Override
    public void updateEntity(Person person) {
        personDAO.update(person);
    }

    @Override
    public List<Person> findAllEntities() {
        return personDAO.findAll();
    }
}
