package org.project.services;

import org.project.dao.CompanyDAO;
import org.project.entities.Company;
import org.project.interfaces.Service;

import java.util.List;

public class CompanyService implements Service<Company> {
    private static final CompanyDAO companyDAO = new CompanyDAO();

    @Override
    public Company findEntity(int id) {
        return companyDAO.findById(id);
    }

    @Override
    public int saveEntity(Company company) {
        return companyDAO.save(company);
    }

    @Override
    public void deleteEntity(Company company) {
        companyDAO.delete(company);
    }

    @Override
    public void updateEntity(Company company) {
        companyDAO.update(company);
    }

    @Override
    public List<Company> findAllEntities() {
        return companyDAO.findAll();
    }
}
