/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daos.db;

import daos.exceptions.DatabaseConnectionDAOException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.jdbc.pool.OracleDataSource;

/**
 *
 * @author duarteduarte
 */
public class OracleConnectionBroker extends GenericConnectionBroker {

    protected static IConnectionBroker instance;
    private Properties ps = null;

    private OracleConnectionBroker(Properties props) {
        this.ps = props;
    }

    public static IConnectionBroker getInstance(Properties props) {
        if (instance == null) {
            instance = new OracleConnectionBroker(props);
        } else {
            instance.setProperties(props);
        }
        return instance;
    }

    @Override
    protected Connection openNewConnection() throws DatabaseConnectionDAOException {
        if (this.ps == null) {
            this.loadProperties();
        }

        Connection conn = null;
        try {
            String url = "jdbc:oracle:thin:@" + ps.getProperty("oracle.ip") + ":" + ps.getProperty("oracle.port") + "/" + ps.getProperty("oracle.dbname");

            try {
                String url1 = System.getProperty("JDBC_URL");
                if (url1 != null) {
                    url = url1;
                }
            } catch (Exception e) {
                // If there is any security exception, ignore it
            }

            // Create an OracleDataSource instance and set properties
            OracleDataSource ods = new OracleDataSource();
            ods.setUser(ps.getProperty("oracle.user"));
            ods.setPassword(ps.getProperty("oracle.password"));
            //ods.setDatabaseName(ps.getProperty("oracle.dbname"));
            ods.setURL(url);

            // Connect to the database
            conn = ods.getConnection();
        } catch (SQLException sqle) {
            throw new DatabaseConnectionDAOException("Unable to connect to database.", sqle);
        }
        return conn;
    }

    private void loadProperties() {
        FileInputStream fs = null;
        IConnectionBroker cb = null;
        try {
            fs = new FileInputStream("props/oracle.properties");
            this.ps = new Properties();
            ps.load(fs);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OracleConnectionBroker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OracleConnectionBroker.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public boolean setProperties(Properties ps) {
        this.ps = ps;
        return true;
    }

    @Override
    public boolean init() {
        PreparedStatement pstmt = null;
        try {
            FileInputStream fs = new FileInputStream(this.ps.getProperty("oracle.schemascript"));
            BufferedReader reader = new BufferedReader(new InputStreamReader(fs));

            Connection conn = this.getLeastUsedConnection();
            String line;
            StringBuilder out = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                out.append(line).append("\n");
                if (line.endsWith(";")) {
                    System.out.println(out.toString());
                    pstmt = conn.prepareStatement(out.toString());
                    pstmt.execute();
                    out = new StringBuilder();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(OracleConnectionBroker.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OracleConnectionBroker.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } catch (IOException ex) {
            Logger.getLogger(OracleConnectionBroker.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException ex) {
                }
            }
        }
        return true;
    }

    @Override
    public boolean destroy() {
        PreparedStatement pstmt = null;
        try {
            FileInputStream fs = new FileInputStream(this.ps.getProperty("oracle.dropscript"));
            BufferedReader reader = new BufferedReader(new InputStreamReader(fs));

            Connection conn = this.getLeastUsedConnection();
            String line;
            StringBuilder out = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                out.append(line).append("\n");
                if (line.endsWith(";")) {
                    System.out.println(out.toString());
                    pstmt = conn.prepareStatement(out.toString());
                    pstmt.execute();
                    out = new StringBuilder();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(OracleConnectionBroker.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OracleConnectionBroker.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } catch (IOException ex) {
            Logger.getLogger(OracleConnectionBroker.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException ex) {
                }
            }
        }
        return true;

    }
}
