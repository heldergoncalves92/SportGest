/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package studentcompany.sportgest.daos;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import studentcompany.sportgest.daos.exceptions.DatabaseConnectionDAOException;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.daos.exceptions.StatementExecuteDAOException;
import studentcompany.sportgest.domains.DomainPojo;

public abstract class GenericPairDAO<A extends DomainPojo, B extends DomainPojo> implements IGenericPairDAO<A,B> {

    protected DataSource executeStatement(Connection conn, String sqlStatement) throws DatabaseConnectionDAOException, StatementExecuteDAOException {

        // Prepare a statement to cleanup the employees table
        try {
            Statement stmt = conn.createStatement();
            try {
                stmt.execute(sqlStatement);
            } finally {
                // Close the statement
                stmt.close();
            }
        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Unable to execute statement", sqle);
        }

        return null;
    }

    @Override
    public abstract List<Pair<A,B>> getAll() throws GenericDAOException;

    @Override
    public abstract List<B> getByFirstId(long id) throws GenericDAOException;

    @Override
    public abstract List<A> getBySecondId(long id) throws GenericDAOException;

    @Override
    public abstract long insert(Pair<A,B> object) throws GenericDAOException;

    @Override
    public abstract boolean delete(Pair<A,B> object) throws GenericDAOException;

    @Override
    public abstract boolean exists(Pair<A,B> object) throws GenericDAOException;
}
