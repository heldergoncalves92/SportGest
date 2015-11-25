/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package studentcompany.sportgest.daos;

import java.util.List;

import studentcompany.sportgest.daos.exceptions.GenericDAOException;

public interface IGenericPairDAO<A,B> {

    List<Pair<A,B>> getAll() throws GenericDAOException;

    List<Pair<A,B>> getByFirstId(int id) throws GenericDAOException;

    List<Pair<A,B>> getBySecondId(int id) throws GenericDAOException;

    long insert(Pair<A,B> object) throws GenericDAOException;

    boolean delete(Pair<A,B> object) throws GenericDAOException;

    boolean exists(Pair<A,B> object) throws GenericDAOException;
}
