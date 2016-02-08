/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package studentcompany.sportgest.daos.exceptions;

import java.sql.SQLException;

/**
 *
 * @author duarteduarte
 */
public class StatementExecuteDAOException extends GenericDAOException {

    public StatementExecuteDAOException(String unable_to_execute_statement, SQLException sqle) {
    }
}
