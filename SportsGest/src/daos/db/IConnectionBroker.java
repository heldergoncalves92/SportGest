/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daos.db;

import daos.exceptions.DatabaseConnectionDAOException;
import java.sql.Connection;
import java.util.Properties;

/**
 *
 * @author duarteduarte
 */
public interface IConnectionBroker {

    Connection getConnection() throws DatabaseConnectionDAOException;

    boolean destroy();

    boolean init();

    boolean setProperties(Properties ps);
}
