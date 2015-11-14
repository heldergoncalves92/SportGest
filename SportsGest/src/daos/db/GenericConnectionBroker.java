/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daos.db;

import daos.exceptions.DatabaseConnectionDAOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

/**
 *
 * @author duarteduarte
 */
public abstract class GenericConnectionBroker implements IConnectionBroker {

    protected ArrayList<ConnectionListItem> connections = new ArrayList<ConnectionListItem>(10);

    public static IConnectionBroker getInstance(Properties props) {
        throw new UnsupportedOperationException("Not implemented");
    }

    protected Connection getLeastUsedConnection() {
        Connection conn = null;
        int minConn = Integer.MAX_VALUE;
        for (ConnectionListItem cli : this.connections) {
            if (cli != null && cli.getInUseBy() < minConn) {
                minConn = cli.getInUseBy();
                conn = cli.getConn();
            }
        }
        return conn;
    }

    /**
     *
     */
    @Override
    public boolean init() {
        return true;
    }

    @Override
    public boolean destroy() {
        Connection conn = null;
        boolean res = false;

        for (ConnectionListItem cli : this.connections) {
            if (cli != null && (conn = cli.getConn()) != null) {
                try {
                    if (conn != null && !conn.isClosed()) {

                        conn.close();
                    }
                } catch (SQLException sqle) {
                    System.out.println("Exception thrown when closing connection: " + sqle.toString());
                }
            }
        }

        return res;
    }

    protected abstract Connection openNewConnection() throws DatabaseConnectionDAOException;

    @Override
    public Connection getConnection() throws DatabaseConnectionDAOException {
        Connection conn = null;
        if (connections.isEmpty()) {
            ConnectionListItem cli = new ConnectionListItem();
            cli.setConn(this.openNewConnection());
            cli.addUsedBy();
            connections.add(cli);
            conn = cli.getConn();
        } else {
            conn = getLeastUsedConnection();
        }

        return conn;
    }

    protected class ConnectionListItem {

        Connection conn;
        int inUseBy = 0;

        public Connection getConn() {
            return conn;
        }

        public void setConn(Connection conn) {
            this.conn = conn;
        }

        public int getInUseBy() {
            return this.inUseBy;
        }

        public void addUsedBy() {
            this.inUseBy++;
        }

        public void removeUsedBy() {
            this.inUseBy--;
        }
    }

    @Override
    public abstract boolean setProperties(Properties props);
}
