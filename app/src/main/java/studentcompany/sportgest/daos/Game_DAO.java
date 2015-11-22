package studentcompany.sportgest.daos;
//TODO methods

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import studentcompany.sportgest.daos.db.MyDB;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Game;

public class Game_DAO extends GenericDAO<Game> implements IGenericDAO<Game>{
    //Database name
    private SQLiteDatabase db;

    //Table names
    public static final String TABLE_NAME                   = "GAME";

    //Table columns
    public static final String COLUMN_ID                    = "ID";
    public static final String COLUMN_HOME_TEAMID           = "HOME_TEAMID";
    public static final String COLUMN_VISITOR_TEAMID        = "VISITOR_TEAMID";
    public static final String COLUMN_DATE                  = "\"DATE\"";
    public static final String COLUMN_REPORT                = "REPORT";
    public static final String COLUMN_HOME_SCORE            = "HOME_SCORE";
    public static final String COLUMN_VISITOR_SCORE         = "VISITOR_SCORE";
    public static final String COLUMN_DURATION              = "DURATION";

    //Create table
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (\n" +
                COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, \n" +
                COLUMN_HOME_TEAMID + " INTEGER NOT NULL, \n" +
                COLUMN_VISITOR_TEAMID + " INTEGER NOT NULL, \n" +
                COLUMN_DATE + " INTEGER NOT NULL, \n" +
                COLUMN_REPORT + " TEXT, \n" +
                COLUMN_HOME_SCORE + " INTEGER, \n" +
                COLUMN_VISITOR_SCORE + " INTEGER, \n" +
                COLUMN_DURATION + " INTEGER NOT NULL, \n" +
                "FOREIGN KEY(" + COLUMN_HOME_TEAMID + ") REFERENCES TEAM(ID), \n" +
                "FOREIGN KEY(" + COLUMN_VISITOR_TEAMID + ") REFERENCES TEAM(ID));\n";

    //Drop table
    public static  final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + "; ";

    public Game_DAO(Context context) {
        this.db = MyDB.getInstance(context).db;
    }

    @Override
    public List<Game> getAll() throws GenericDAOException {
        return null;
    }

    @Override
    public Game getById(int id) throws GenericDAOException {
        return null;
    }

    @Override
    public long insert(Game object) throws GenericDAOException {
        return -1;
    }

    @Override
    public boolean delete(Game object) throws GenericDAOException {
        return false;
    }

    @Override
    public boolean update(Game object) throws GenericDAOException {
        return false;
    }

    @Override
    public boolean exists(Game object) throws GenericDAOException {
        return false;
    }

    @Override
    public List<Game> getByCriteria(Game object) throws GenericDAOException {
        return null;
    }
}
