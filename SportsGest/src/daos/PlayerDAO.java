/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import daos.db.IConnectionBroker;
import daos.domains.Player;
import daos.exceptions.DatabaseConnectionDAOException;
import daos.exceptions.GenericDAOException;
import daos.exceptions.StatementExecuteDAOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author duarteduarte
 */
public class PlayerDAO extends GenericDAO<Player> implements IGenericDAO<Player> {

    /**
     * Table name
     */
    private final static String TABLE_NAME = "player";
    /**
     * Column names
     */
    private final static String COL_NICKNAME = "NICKNAME";
    private final static String COL_NAME = "NAME";
    private final static String COL_NATIONALITY = "NATIONALITY";
    private final static String COL_MARITAL_STATUS = "MARITAL_STATUS";
    private final static String COL_BIRTHDATE = "BIRTHDATE";
    private final static String COL_HEIGHT = "HEIGHT";
    private final static String COL_WEIGHT = "WEIGHT";
    private final static String COL_ADDRESS = "ADDRESS";
    private final static String COL_GENDER = "GENDER";
    private final static String COL_PHOTO = "PHOTO";
    private final static String COL_EMAIL = "EMAIL";
    private final static String COL_PREFERED_FOOT = "PREFERED_FOOT";
    private final static String COL_NUMBER = "NUMBER";
    private final static String COL_TEAM_ID = "TEAM_ID";
    /**
     * Prepared statements
     */
    static String INSERT_STATEMENT = "insert into " + TABLE_NAME
            + " ( "
            + COL_NICKNAME + ","
            + COL_NAME + ","
            + COL_NATIONALITY + ","
            + COL_MARITAL_STATUS + ","
            + COL_BIRTHDATE + ","
            + COL_HEIGHT + ","
            + COL_WEIGHT + ","
            + COL_ADDRESS + ","
            + COL_GENDER + ","
            + COL_PHOTO + ","
            + COL_EMAIL + ","
            + COL_PREFERED_FOOT + ","
            + COL_NUMBER + ","
            + COL_TEAM_ID + ") "
            + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    static String DELETE_STATEMENT = "delete from " + TABLE_NAME
            + " where NICKNAME=?";
    static String UPDATE_STATEMENT = "update " + TABLE_NAME + " set "
            + COL_NICKNAME + "=?, "
            + COL_NAME + "=?, "
            + COL_NATIONALITY + "=?, "
            + COL_MARITAL_STATUS + "=?, "
            + COL_BIRTHDATE + "=?, "
            + COL_HEIGHT + "=?, "
            + COL_WEIGHT + "=?, "
            + COL_ADDRESS + "=?, "
            + COL_GENDER + "=?, "
            + COL_PHOTO + "=?, "
            + COL_EMAIL + "=?"
            + COL_PREFERED_FOOT + "=?, "
            + COL_NUMBER + "=?, "
            + COL_TEAM_ID + "=? "
            + "where NICKNAME=?";

    public PlayerDAO(IConnectionBroker cb) {
        this.cb = cb;
    }

    @Override
    public List<Player> getAll() throws DatabaseConnectionDAOException, StatementExecuteDAOException {

        Statement pstmt = null;
        List<Player> players = new ArrayList<Player>();
        ResultSet playerRS = null;

        TeamDAO teamDAO = new TeamDAO(this.cb);

        Connection conn = cb.getConnection();

        try {
            System.out.println("isClosed = " + conn.isClosed());
            pstmt = conn.createStatement();
            playerRS = pstmt.executeQuery("SELECT * FROM " + TABLE_NAME);
            Player player = null;
            while (playerRS.next()) {

                int teamId = playerRS.getInt(COL_TEAM_ID);
                Team t = teamDAO.getById(teamId);

                player = new Player(
                        playerRS.getString(COL_NICKNAME),
                        playerRS.getString(COL_NAME),
                        playerRS.getString(COL_NATIONALITY),
                        playerRS.getString(COL_MARITAL_STATUS),
                        playerRS.getString(COL_BIRTHDATE),
                        playerRS.getInt(COL_HEIGHT),
                        playerRS.getFloat(COL_WEIGHT),
                        playerRS.getString(COL_ADDRESS),
                        playerRS.getString(COL_GENDER),
                        playerRS.getString(COL_PHOTO),
                        playerRS.getString(COL_EMAIL),
                        playerRS.getString(COL_PREFERED_FOOT),
                        playerRS.getString(COL_NUMBER),
                        t);
                players.add(player);
            }
        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Nao consegui abrir ligacao a BD", sqle);
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (playerRS != null) {
                    playerRS.close();
                }
            } catch (SQLException sqle) {
                throw new DatabaseConnectionDAOException("Nao consegui abrir ligacao a BD", sqle);
            }
        }

        return players;
    }

    @Override
    public boolean insert(Player object) throws StatementExecuteDAOException, DatabaseConnectionDAOException {
        PreparedStatement pstmt = null;
        boolean result = false;
        try {
            Connection conn = this.cb.getConnection();
            pstmt = conn.prepareStatement(PlayerDAO.INSERT_STATEMENT, new String[]{COL_NICKNAME});

            pstmt.setString(1, object.getNickname());
            pstmt.setString(2, object.getName());
            pstmt.setString(3, object.getNationality());
            pstmt.setString(4, object.getMarital_status());
            pstmt.setString(5, object.getBirthDate());
            pstmt.setInt(6, object.getHeight());
            pstmt.setFloat(7, object.getWeight());
            pstmt.setString(8, object.getAddress());
            pstmt.setString(9, object.getGender());
            pstmt.setString(10, object.getPhoto());
            pstmt.setString(11, object.getEmail());
            pstmt.setString(12, object.getPreferedFoot());
            pstmt.setInt(13, object.getNumber());
            pstmt.setString(14, object.getTeam().getId());

            // Do the insertion, check number of rows updated
            System.out.println("Query: " + pstmt.toString());
            pstmt.execute();
            //Statement s = conn.createStatement();
            //ResultSet filmeKeys = s.executeQuery("select last_insert_rowid();");
            ResultSet playerKeys = pstmt.getGeneratedKeys();
            int idPlayer = -1;
            if ((result = pstmt.getUpdateCount() != 0) && playerKeys.next()) {
                idPlayer = playerKeys.getInt(1);
                System.out.println(pstmt.getUpdateCount() + " rows updated");
                System.out.println("Inserted " + TABLE_NAME + " with ID: " + idPlayer
                );

                pstmt.close();
            }

        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Error executing insert statement ", sqle);

        } catch (DatabaseConnectionDAOException ex) {
            System.err.println(PlayerDAO.class.getName() + " [SEVERE] " + ex.toString());
            Logger.getLogger(PlayerDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        return result;
    }

    @Override
    public boolean delete(Player object) throws StatementExecuteDAOException, DatabaseConnectionDAOException {
        boolean result = false;
        try {
            Connection conn = this.cb.getConnection();
            PreparedStatement pstmt
                    = conn.prepareStatement(PlayerDAO.DELETE_STATEMENT);
            pstmt.setInt(1, object.getId());
            pstmt.execute();
            System.out.println(pstmt.getUpdateCount() + " rows updated");
            pstmt.close();

        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Error executing delete statement ", sqle);

        } catch (DatabaseConnectionDAOException ex) {
            Logger.getLogger(PlayerDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    @Override
    public boolean update(Player object) throws StatementExecuteDAOException, DatabaseConnectionDAOException {
        boolean result = false;
        try {
            Connection conn = this.cb.getConnection();
            PreparedStatement pstmt
                    = conn.prepareStatement(PlayerDAO.UPDATE_STATEMENT);

            pstmt.setString(1, object.getNickname());
            pstmt.setString(2, object.getName());
            pstmt.setString(3, object.getNationality());
            pstmt.setString(4, object.getMarital_status());
            pstmt.setString(5, object.getBirthDate());
            pstmt.setInt(6, object.getHeight());
            pstmt.setFloat(7, object.getWeight());
            pstmt.setString(8, object.getAddress());
            pstmt.setString(9, object.getGender());
            pstmt.setString(10, object.getPhoto());
            pstmt.setString(11, object.getEmail());
            pstmt.setString(12, object.getPreferedFoot());
            pstmt.setInt(13, object.getNumber());
            pstmt.setString(14, object.getTeam().getId());
            pstmt.execute();

            result = pstmt.getUpdateCount() > 0;

        } catch (DatabaseConnectionDAOException ex) {
            Logger.getLogger(PlayerDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Error executing update statement ", sqle);
        }

        return result;
    }

    @Override
    public Player getById(String nickname) throws DatabaseConnectionDAOException, StatementExecuteDAOException {
        Player player = null;

        TeamDAO produtoraDAO = new TeamDAO(cb);

        Connection conn = cb.getConnection();
        try {
            PreparedStatement pstmt = null;
            pstmt = conn.prepareStatement("SELECT * FROM " + TABLE_NAME + " where " + COL_NICKNAME + " = ?");
            pstmt.setString(1, nickname);
            pstmt.execute();
            ResultSet playerRs = pstmt.getResultSet();
            while (playerRs.next()) {
                int teamId = playerRs.getInt(COL_TEAM_ID);

                Team t = TeamDAO.getById(teamId);

                player = new Player(
                        playerRs.getString(COL_NICKNAME),
                        playerRs.getString(COL_NAME),
                        playerRs.getString(COL_NATIONALITY),
                        playerRs.getString(COL_MARITAL_STATUS),
                        playerRs.getString(COL_BIRTHDATE),
                        playerRs.getInt(COL_HEIGHT),
                        playerRs.getFloat(COL_WEIGHT),
                        playerRs.getString(COL_ADDRESS),
                        playerRs.getString(COL_GENDER),
                        playerRs.getString(COL_PHOTO),
                        playerRs.getString(COL_EMAIL),
                        playerRs.getString(COL_PREFERED_FOOT),
                        playerRs.getString(COL_NUMBER),
                        t);
            }
        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Error getting filme by id ", sqle);

        }

        return player;
    }

    @Override
    public boolean exists(Player object) throws GenericDAOException {
        Connection conn = cb.getConnection();
        int fields = 0;
        String tmpString = null;
        int tmpInt = 0;
        double tmpFloat = 0.0f;
        double tmpDouble = 0.0;
        long tmpLong = 0;
        boolean result = false;
        try {
            StringBuilder statement = new StringBuilder("SELECT * FROM " + TABLE_NAME + " where ");
            if ((tmpString = object.getNickname()) != null) {
                statement.append(COL_NICKNAME + "=" + tmpString);
                fields++;
            }
            if ((tmpString = object.getName()) != null) {
                statement.append(COL_NAME + "=" + tmpString);
                fields++;
            }
            if ((tmpString = object.getNationality()) != null) {
                statement.append(COL_NATIONALITY + "=" + tmpString);
                fields++;
            }
            if ((tmpString = object.getMarital_status()) != null) {
                statement.append(COL_MARITAL_STATUS + "=" + tmpString);
                fields++;
            }
            if ((tmpString = object.getBirthDate()) != null) {
                statement.append(COL_BIRTHDATE + "=" + tmpString);
                fields++;
            }
            if ((tmpInt = object.getHeight()) > 0) {
                statement.append(COL_HEIGHT + "=" + tmpInt);
                fields++;
            }
            if ((tmpFloat = object.getWeight()) > 0) {
                statement.append(COL_WEIGHT + "=" + tmpFloat);
                fields++;
            }
            if ((tmpString = object.getAddress()) != null) {
                statement.append(COL_ADDRESS + "=" + tmpString);
                fields++;
            }
            if ((tmpString = object.getGender()) != null) {
                statement.append(COL_GENDER + "=" + tmpString);
                fields++;
            }
            if ((tmpString = object.getPhoto()) != null) {
                statement.append(COL_PHOTO + "=" + tmpString);
                fields++;
            }
            if ((tmpString = object.getEmail()) != null) {
                statement.append(COL_EMAIL + "=" + tmpString);
                fields++;
            }
            if ((tmpString = object.getPreferedFoot()) != null) {
                statement.append(COL_PREFERED_FOOT + "=" + tmpString);
                fields++;
            }
            if ((tmpInt = object.getNumber()) > 0) {
                statement.append(COL_NUMBER + "=" + tmpInt);
                fields++;
            }
            if ((tmpInt = object.getTeam().getId()) > 0) {
                statement.append(COL_TEAM_ID + "=" + tmpInt);
                fields++;
            }
            if (fields > 0) {
                Statement stmt = conn.createStatement();
                stmt.executeQuery(statement.toString());
                ResultSet filmeRs = stmt.getResultSet();
                result = filmeRs.next();

            }
        } catch (SQLException sqle) {
        }
        return result;
    }

    @Override
    public List<Player> getByCriteria(Player object) throws GenericDAOException {
        List<Player> filmes = new ArrayList<>();
        Connection conn = cb.getConnection();
        int fields = 0;
        String tmpString = null;
        int tmpInt = 0;
        float tmpFloat = 0f;
        double tmpDouble = 0;
        long tmpLong = 0;
        boolean result = false;
        Statement stmt = null;
        PreparedStatement filmeStmt = null;

        try {
            StringBuilder statement = new StringBuilder("SELECT " + COL_NICKNAME + " FROM " + TABLE_NAME + " where ");
            if ((tmpString = object.getNickname()) != null) {
                if (tmpString.length() < 10) {
                    statement.append(((fields != 0) ? " AND " : "") + "UPPER(" + COL_NICKNAME + ") LIKE ('%" + tmpString.toUpperCase() + "%')");
                    fields++;
                }
            }
            if ((tmpString = object.getName()) != null) {
                if (tmpString.length() < 10) {
                    statement.append(((fields != 0) ? " AND " : "") + "UPPER(" + COL_NAME + ") LIKE ('%" + tmpString.toUpperCase() + "%')");
                    fields++;
                }
            }
            if ((tmpString = object.getNationality()) != null) {
                if (tmpString.length() < 10) {
                    statement.append(((fields != 0) ? " AND " : "") + "UPPER(" + COL_NATIONALITY + ") LIKE ('%" + tmpString.toUpperCase() + "%')");
                    fields++;
                }
            }
            if ((tmpString = object.getMarital_status()) != null) {
                if (tmpString.length() < 10) {
                    statement.append(((fields != 0) ? " AND " : "") + "UPPER(" + COL_MARITAL_STATUS + ") LIKE ('%" + tmpString.toUpperCase() + "%')");
                    fields++;
                }
            }
            if ((tmpString = object.getBirthDate()) != null) {
                if (tmpString.length() < 10) {
                    statement.append(((fields != 0) ? " AND " : "") + "UPPER(" + COL_BIRTHDATE + ") LIKE ('%" + tmpString.toUpperCase() + "%')");
                    fields++;
                }
            }
            if ((tmpInt = object.getHeight()) > 0) {
                statement.append(COL_HEIGHT + "=" + tmpInt);
                fields++;
            }
            if ((tmpFloat = object.getWeight()) > 0) {
                statement.append(COL_HEIGHT + "=" + tmpFloat);
                fields++;
            }
            if ((tmpString = object.getAddress()) != null) {
                if (tmpString.length() < 10) {
                    statement.append(((fields != 0) ? " AND " : "") + "UPPER(" + COL_ADDRESS + ") LIKE ('%" + tmpString.toUpperCase() + "%')");
                    fields++;
                }
            }
            if ((tmpString = object.getGender()) != null) {
                if (tmpString.length() < 10) {
                    statement.append(((fields != 0) ? " AND " : "") + "UPPER(" + COL_GENDER + ") LIKE ('%" + tmpString.toUpperCase() + "%')");
                    fields++;
                }
            }
            if ((tmpString = object.getPhoto()) != null) {
                if (tmpString.length() < 10) {
                    statement.append(((fields != 0) ? " AND " : "") + "UPPER(" + COL_PHOTO + ") LIKE ('%" + tmpString.toUpperCase() + "%')");
                    fields++;
                }
            }
            if ((tmpString = object.getEmail()) != null) {
                if (tmpString.length() < 10) {
                    statement.append(((fields != 0) ? " AND " : "") + "UPPER(" + COL_EMAIL + ") LIKE ('%" + tmpString.toUpperCase() + "%')");
                    fields++;
                }
            }
            if ((tmpString = object.getPreferedFoot()) != null) {
                if (tmpString.length() < 10) {
                    statement.append(((fields != 0) ? " AND " : "") + "UPPER(" + COL_PREFERED_FOOT + ") LIKE ('%" + tmpString.toUpperCase() + "%')");
                    fields++;
                }
            }

            if ((tmpInt = object.getNumber()) > -1) {
                statement.append(((fields != 0) ? " AND " : "") + "UPPER(" + COL_NUMBER + ") LIKE ('%" + tmpInt + "%')");
                fields++;
            }
            if ((tmpInt = object.getTeam().getId()) > -1) {
                statement.append(((fields != 0) ? " AND " : "") + "UPPER(" + COL_TEAM_ID + ") LIKE ('%" + tmpInt + "%')");
                fields++;
            }

            if (fields > 0) {
                stmt = conn.createStatement();
                ResultSet filmeRs = stmt.executeQuery(statement.toString());

                Player player = null;
                while (filmeRs.next()) {
                    player = this.getById(filmeRs.getInt(COL_NICKNAME));
                    filmes.add(player);
                }
            }
        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Error getting Filme by criteria", sqle);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (filmeStmt != null) {
                    filmeStmt.close();
                }
            } catch (SQLException sqle) {
                throw new StatementExecuteDAOException("Error closing stmt by criteria", sqle);
            }
        }

        return filmes;
    }

    @Override
    public Player getById(int id) throws GenericDAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
