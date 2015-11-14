/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daos.db;

import daos.exceptions.DatabaseConnectionDAOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author duarteduarte
 */
public class SQLiteConnectionBroker extends GenericConnectionBroker {

    private static IConnectionBroker instance;
    private Properties ps;

    private SQLiteConnectionBroker(Properties props) {
        this.ps = props;
    }

    public static IConnectionBroker getInstance(Properties props) {
        if (instance == null) {
            instance = new SQLiteConnectionBroker(props);
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

        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + this.ps.getProperty("sqlite.datafile"));
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");

        return c;
    }

    @Override
    public boolean init() {
        PreparedStatement pstmt = null;
        try {
            FileInputStream fs = new FileInputStream(this.ps.getProperty("sqlite.schemascript"));
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
            Logger.getLogger(SQLiteConnectionBroker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SQLiteConnectionBroker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SQLiteConnectionBroker.class.getName()).log(Level.SEVERE, null, ex);
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
        File f = new File(this.ps.getProperty("sqlite.dropscript"));
        return f.delete();
    }

    private void loadProperties() {
        FileInputStream fs = null;
        IConnectionBroker cb = null;
        try {
            fs = new FileInputStream("props/sqlite.properties");
            this.ps = new Properties();
            ps.load(fs);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SQLiteConnectionBroker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SQLiteConnectionBroker.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public boolean setProperties(Properties ps) {
        this.ps = ps;
        return true;
    }
}
