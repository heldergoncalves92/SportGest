/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daos.exceptions;

/**
 *
 * @author duarteduarte
 */
public class GenericDAOException extends Exception {

    public GenericDAOException() {
        super();
    }

    public GenericDAOException(String message) {
        super(message);
    }

    public GenericDAOException(Throwable t) {
        super(t);
    }

    public GenericDAOException(String message, Throwable t) {
        super(message, t);
    }
}
