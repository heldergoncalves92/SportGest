/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import daos.exceptions.GenericDAOException;
import java.util.List;

/**
 *
 * @author duarteduarte
 */
public interface IGenericDAO<T> {

    public List<T> getAll() throws GenericDAOException;

    public T getById(int id) throws GenericDAOException;

    public boolean insert(T object) throws GenericDAOException;

    public boolean delete(T object) throws GenericDAOException;

    public boolean update(T object) throws GenericDAOException;

    public boolean exists(T object) throws GenericDAOException;

    public List<T> getByCriteria(T object) throws GenericDAOException;
}
