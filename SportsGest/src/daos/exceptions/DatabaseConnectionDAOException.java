/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daos.exceptions;

/**
 *
 * @author duarteduarte
 */
public class DatabaseConnectionDAOException extends GenericDAOException {

    public DatabaseConnectionDAOException() {
        super();
    }

    public DatabaseConnectionDAOException(String message) {
        super(message);
    }

    public DatabaseConnectionDAOException(Throwable t) {
        super(t);
    }

    public DatabaseConnectionDAOException(String message, Throwable t) {
        super(message, t);
    }
}
