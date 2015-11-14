/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daos.db;

import daos.exceptions.DatabaseConnectionDAOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 *
 * @author duarteduarte
 */
public class ConnectionBrokerFactory {

    private ConnectionBrokerFactory() {
    }

    public static IConnectionBroker giveMeDefaultConnectionBroker() {
        return SQLiteConnectionBroker.getInstance(null);
    }

    public static IConnectionBroker giveMeConnectionBrokerFromProperties() throws DatabaseConnectionDAOException {

        FileInputStream fs = null;
        IConnectionBroker cb = null;
        try {
            fs = new FileInputStream("props/bmdb.properties");
            Properties ps = new Properties();

            ps.load(fs);

            String className = ps.getProperty("connectionbroker.impl");

            if (className != null) {
                Class c = Class.forName(className);
                if (c != null) {
                    Method m = c.getMethod("getInstance", new Class[]{Properties.class});
                    if (m != null) {
                        cb = (IConnectionBroker) (c.cast(m.invoke(c, new Object[]{null})));
                    }

                }
            }
            //PORQUE É QUE DA UM SQLite em VEZ DE ORACLE !? ;
            //visto que estou a usar um oracle;
        } catch (FileNotFoundException ex) {
            throw new DatabaseConnectionDAOException("Nao consegui encontrar o ficheiro de properties", ex);
        } catch (IOException ex) {
            throw new DatabaseConnectionDAOException("Nao consegui carregar o ficheiro de properties", ex);
        } catch (ClassNotFoundException ex) {
            throw new DatabaseConnectionDAOException("Nao consegui a classe do ConnectionBroker", ex);
        } catch (NoSuchMethodException ex) {
            throw new DatabaseConnectionDAOException("A classe nao cumpre o interface necessario", ex);
        } catch (SecurityException ex) {
            throw new DatabaseConnectionDAOException("O metodo necessario nao e publico ou nao pode ser acedido", ex);
        } catch (IllegalAccessException ex) {
            throw new DatabaseConnectionDAOException("A classe ou o metodo nao sao publicos", ex);
        } catch (IllegalArgumentException ex) {
            throw new DatabaseConnectionDAOException("O metodo nao cumpre o interface necessario", ex);
        } catch (InvocationTargetException ex) {
            throw new DatabaseConnectionDAOException("O metodo nao pode ser invocado na classe definida", ex);
        } finally {
            if (fs != null) {
                try {
                    fs.close();
                } catch (IOException ex) {
                }
            }
        }

        return cb;
    }

    public static IConnectionBroker giveMeConnectionBrokerByName(String connectionBrokerClassCannonicalName, Properties props) throws DatabaseConnectionDAOException {
        IConnectionBroker cb = null;
        try {

            String className = connectionBrokerClassCannonicalName;

            if (className != null) {
                Class c = Class.forName(className);
                if (c != null) {
                    Method m = c.getMethod("getInstance", new Class[]{Properties.class});
                    if (m != null) {
                        cb = (IConnectionBroker) (c.cast(m.invoke(c, new Object[]{props})));
                    }
                }
            }
        } catch (ClassNotFoundException ex) {
            throw new DatabaseConnectionDAOException("Nao consegui a classe do ConnectionBroker", ex);
        } catch (NoSuchMethodException ex) {
            throw new DatabaseConnectionDAOException("A classe nao cumpre o interface necessario", ex);
        } catch (SecurityException ex) {
            throw new DatabaseConnectionDAOException("O metodo necessario nao e publico ou nao pode ser acedido", ex);
        } catch (IllegalAccessException ex) {
            throw new DatabaseConnectionDAOException("A classe ou o metodo nao sao publicos", ex);
        } catch (IllegalArgumentException ex) {
            throw new DatabaseConnectionDAOException("O metodo nao cumpre o interface necessario", ex);
        } catch (InvocationTargetException ex) {
            throw new DatabaseConnectionDAOException("O metodo nao pode ser invocado na classe definida", ex);
        }

        return cb;
    }
}
