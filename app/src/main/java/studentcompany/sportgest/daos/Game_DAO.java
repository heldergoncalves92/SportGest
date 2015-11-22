package studentcompany.sportgest.daos;
//TODO methods

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import studentcompany.sportgest.daos.db.MyDB;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Game;
import studentcompany.sportgest.domains.Team;

public class Game_DAO extends GenericDAO<Game> implements IGenericDAO<Game>{
    //Database name
    private SQLiteDatabase db;
    //Dependencies DAOs
    private Team_DAO          team_dao;

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
        this.team_dao = new Team_DAO(context);
    }

    @Override
    public List<Game> getAll() throws GenericDAOException {
        //aux variables
        ArrayList<Game> resGame = new ArrayList<>();
        int id;
        int home_teamid;
        int visitor_teamid;
        int date;
        String report;
        int home_score;
        int visitor_score;
        float duration;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME, null );
        res.moveToFirst();

        //Parse data
        while(res.isAfterLast() == false) {
            id = res.getInt(res.getColumnIndex(COLUMN_ID));
            home_teamid = res.getInt(res.getColumnIndex(COLUMN_HOME_TEAMID));
            visitor_teamid = res.getInt(res.getColumnIndex(COLUMN_VISITOR_TEAMID));
            date = res.getInt(res.getColumnIndex(COLUMN_DATE));
            report = res.getString(res.getColumnIndex(COLUMN_REPORT));
            home_score = res.getInt(res.getColumnIndex(COLUMN_HOME_SCORE));
            visitor_score = res.getInt(res.getColumnIndex(COLUMN_VISITOR_SCORE));
            duration = res.getFloat(res.getColumnIndex(COLUMN_DURATION));

            resGame.add(new Game(id,team_dao.getById(home_teamid),team_dao.getById(visitor_teamid), date, report,
                    home_score,visitor_score, duration));
            res.moveToNext();
        }

        return resGame;
    }

    @Override
    public Game getById(int id) throws GenericDAOException {
        //aux variables;
        Game resGame;
        int home_teamid;
        int visitor_teamid;
        int date;
        String report;
        int home_score;
        int visitor_score;
        float duration;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + "=" + id, null );
        res.moveToFirst();

        //Parse data
        id = res.getInt(res.getColumnIndex(COLUMN_ID));
        home_teamid = res.getInt(res.getColumnIndex(COLUMN_HOME_TEAMID));
        visitor_teamid = res.getInt(res.getColumnIndex(COLUMN_VISITOR_TEAMID));
        date = res.getInt(res.getColumnIndex(COLUMN_DATE));
        report = res.getString(res.getColumnIndex(COLUMN_REPORT));
        home_score = res.getInt(res.getColumnIndex(COLUMN_HOME_SCORE));
        visitor_score = res.getInt(res.getColumnIndex(COLUMN_VISITOR_SCORE));
        duration = res.getFloat(res.getColumnIndex(COLUMN_DURATION));

        resGame = new Game(id,team_dao.getById(home_teamid),team_dao.getById(visitor_teamid), date, report,
                home_score,visitor_score, duration);

        return resGame;
    }

    @Override
    public long insert(Game object) throws GenericDAOException {

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_HOME_TEAMID, object.getIdTeamHome().getId());
        contentValues.put(COLUMN_VISITOR_TEAMID,  object.getIdTeamVisitor().getId());
        contentValues.put(COLUMN_DATE, object.getDate());
        contentValues.put(COLUMN_REPORT, object.getReport());
        contentValues.put(COLUMN_DATE, object.getDate());
        contentValues.put(COLUMN_REPORT, object.getReport());
        contentValues.put(COLUMN_HOME_SCORE, object.getHome_score());
        contentValues.put(COLUMN_VISITOR_SCORE, object.getVisitor_score());
        contentValues.put(COLUMN_DURATION, object.getDuration());

        return db.insert(TABLE_NAME, null, contentValues);
    }

    @Override
    public boolean delete(Game object) throws GenericDAOException {
        int deletedCount = db.delete(TABLE_NAME,
                COLUMN_ID + " = ? ",
                new String[] { Integer.toString(object.getId()) });
        return true;
    }

    @Override
    public boolean update(Game object) throws GenericDAOException {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_HOME_TEAMID,object.getIdTeamHome().getId());
        contentValues.put(COLUMN_VISITOR_TEAMID,  object.getIdTeamVisitor().getId());
        contentValues.put(COLUMN_DATE, object.getDate());
        contentValues.put(COLUMN_REPORT, object.getReport());
        contentValues.put(COLUMN_DATE, object.getDate());
        contentValues.put(COLUMN_REPORT, object.getReport());
        contentValues.put(COLUMN_HOME_TEAMID, object.getHome_score());
        contentValues.put(COLUMN_VISITOR_TEAMID, object.getVisitor_score());
        contentValues.put(COLUMN_DURATION, object.getDuration());

        db.update(TABLE_NAME,
                contentValues,
                COLUMN_ID + " = ? ",
                new String[] { Integer.toString(object.getId()) } );
        return true;
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
