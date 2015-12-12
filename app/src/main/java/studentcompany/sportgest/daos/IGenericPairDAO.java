/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package studentcompany.sportgest.daos;

import java.util.List;

import studentcompany.sportgest.daos.exceptions.GenericDAOException;

public interface IGenericPairDAO<A,B> {

    List<Pair<A,B>> getAll() throws GenericDAOException;

    List<B> getByFirstId(long id) throws GenericDAOException;

    List<A> getBySecondId(long id) throws GenericDAOException;

    long insert(Pair<A,B> object) throws GenericDAOException;

    boolean delete(Pair<A,B> object) throws GenericDAOException;

    boolean exists(Pair<A,B> object) throws GenericDAOException;
}
