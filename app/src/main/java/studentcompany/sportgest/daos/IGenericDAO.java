/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package studentcompany.sportgest.daos;

import java.util.List;

import studentcompany.sportgest.daos.exceptions.GenericDAOException;

/**
 *
 * @author duarteduarte
 */
public interface IGenericDAO<T> {

    List<T> getAll() throws GenericDAOException;

    T getById(int id) throws GenericDAOException;

    long insert(T object) throws GenericDAOException;

    boolean delete(T object) throws GenericDAOException;

    boolean deleteById(int id) throws GenericDAOException;

    boolean update(T object) throws GenericDAOException;

    int numberOfRows() throws GenericDAOException;

    boolean exists(T object) throws GenericDAOException;

    List<T> getByCriteria(T object) throws GenericDAOException;
}
