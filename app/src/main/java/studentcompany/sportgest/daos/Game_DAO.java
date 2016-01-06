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
    public static final String COLUMN_DATE                  = "DATE";
    public static final String COLUMN_REPORT                = "REPORT";
    public static final String COLUMN_HOME_SCORE            = "HOME_SCORE";
    public static final String COLUMN_VISITOR_SCORE         = "VISITOR_SCORE";
    public static final String COLUMN_DURATION              = "DURATION";

    //Create table
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (\n" +
                COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, \n" +
                COLUMN_HOME_TEAMID + " INTEGER, \n" +
                COLUMN_VISITOR_TEAMID + " INTEGER, \n" +
                COLUMN_DATE + " INTEGER, \n" +
                COLUMN_REPORT + " TEXT, \n" +
                COLUMN_HOME_SCORE + " INTEGER, \n" +
                COLUMN_VISITOR_SCORE + " INTEGER, \n" +
                COLUMN_DURATION + " INTEGER, \n" +
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
        long id;
        long home_team;
        long visitor_team;
        long date;
        String report;
        int home_score;
        int visitor_score;
        float duration;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME, null );
        res.moveToFirst();

        //Parse data
        while(res.isAfterLast() == false) {
            id = res.getLong(res.getColumnIndex(COLUMN_ID));
            home_team = res.getLong(res.getColumnIndex(COLUMN_HOME_TEAMID));
            visitor_team = res.getLong(res.getColumnIndex(COLUMN_VISITOR_TEAMID));
            date = res.getLong(res.getColumnIndex(COLUMN_DATE));
            report = res.getString(res.getColumnIndex(COLUMN_REPORT));
            home_score = res.getInt(res.getColumnIndex(COLUMN_HOME_SCORE));
            visitor_score = res.getInt(res.getColumnIndex(COLUMN_VISITOR_SCORE));
            duration = res.getFloat(res.getColumnIndex(COLUMN_DURATION));

            resGame.add(new Game(id,team_dao.getById(home_team),team_dao.getById(visitor_team), date, report,
                    home_score,visitor_score, duration));
            res.moveToNext();
        }

        return resGame;
    }

    @Override
    public Game getById(long id) throws GenericDAOException {
        //aux variables;
        Game resGame;
        long home_teamid;
        long visitor_teamid;
        long date;
        String report;
        int home_score;
        int visitor_score;
        float duration;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + "=" + id, null );
        res.moveToFirst();

        //Parse data
        id = res.getLong(res.getColumnIndex(COLUMN_ID));
        home_teamid = res.getLong(res.getColumnIndex(COLUMN_HOME_TEAMID));
        visitor_teamid = res.getLong(res.getColumnIndex(COLUMN_VISITOR_TEAMID));
        date = res.getLong(res.getColumnIndex(COLUMN_DATE));
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

        if(object==null)
            return -1;

        ContentValues contentValues = new ContentValues();
        if(object.getHome_team()==null)
            contentValues.put(COLUMN_HOME_TEAMID, object.getHome_team().getId());
        else
            contentValues.putNull(COLUMN_HOME_TEAMID);

        if(object.getVisitor_team()==null)
            contentValues.put(COLUMN_VISITOR_TEAMID, object.getVisitor_team().getId());
        else
            contentValues.putNull(COLUMN_VISITOR_TEAMID);

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
        if(object==null)
            return false;

        return deleteById(object.getId());
    }

    public boolean deleteById(long id) {
        return db.delete(TABLE_NAME,
                COLUMN_ID + " = ? ",
                new String[]{Long.toString(id)}) > 0;
    }

    @Override
    public boolean update(Game object) throws GenericDAOException {

        ContentValues contentValues = new ContentValues();
        if(object.getHome_team()==null)
            contentValues.put(COLUMN_HOME_TEAMID, object.getHome_team().getId());
        else
            contentValues.putNull(COLUMN_HOME_TEAMID);

        if(object.getVisitor_team()==null)
            contentValues.put(COLUMN_VISITOR_TEAMID, object.getVisitor_team().getId());
        else
            contentValues.putNull(COLUMN_VISITOR_TEAMID);

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
                new String[] { Long.toString(object.getId()) } );
        return true;
    }

    @Override
    public int numberOfRows(){
        return (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
    }

    @Override
    public boolean exists(Game object) throws GenericDAOException {

        if(object==null)
            return false;

        int fields = 0;
        String tmpString;
        int tmpInt;
        long tmpLong;
        float tmpFloat;

        StringBuilder statement = new StringBuilder("SELECT * FROM "+ TABLE_NAME +" where ");
        if ((tmpLong = object.getId()) > 0) {
            statement.append(COLUMN_ID + "=" + tmpLong);
            fields++;
        }
        if(object.getHome_team() != null) {
            if ((tmpLong = object.getHome_team().getId()) > 0) {
                statement.append(((fields != 0) ? " AND " : "") + COLUMN_HOME_TEAMID + " = " + tmpLong);
                fields++;
            }
        }
        if(object.getVisitor_team() != null) {
            if ((tmpLong = object.getVisitor_team().getId()) >= 0) {
                statement.append(((fields != 0) ? " AND " : "") + COLUMN_VISITOR_TEAMID + " = " + tmpLong);
                fields++;
            }
        }
        if ((tmpLong = object.getDate()) > 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_DATE + " = " + tmpLong );
            fields++;
        }
        if ((tmpString = object.getReport()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_REPORT + " = '" + tmpString + "'");
            fields++;
        }
        if ((tmpInt = object.getHome_score()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_HOME_SCORE + " = " + tmpInt );
            fields++;
        }
        if ((tmpInt = object.getVisitor_score()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_VISITOR_SCORE + " = " + tmpInt );
            fields++;
        }if ((tmpFloat = object.getDuration()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_DURATION + " = " + tmpFloat );
            fields++;
        }

        if (fields > 0) {
            Cursor res = db.rawQuery(statement.toString(), null);
            return res.moveToFirst();
        }
        else
            return false;
    }

    @Override
    public List<Game> getByCriteria(Game object) throws GenericDAOException {

        if(object==null)
            return null;

        List<Game> resGame = new ArrayList<>();
        int fields = 0;
        String tmpString;
        int tmpInt;
        long tmpLong;
        float tmpFloat;

        StringBuilder statement = new StringBuilder("SELECT * FROM "+ TABLE_NAME +" where ");
        if ((tmpLong = object.getId()) > 0) {
            statement.append(COLUMN_ID + "=" + tmpLong);
            fields++;
        }
        if(object.getHome_team() != null) {
            if ((tmpLong = object.getHome_team().getId()) >= 0) {
                statement.append(((fields != 0) ? " AND " : "") + COLUMN_HOME_TEAMID + " = " + tmpLong);
                fields++;
            }
        }
        if(object.getVisitor_team() != null) {
            if ((tmpLong = object.getVisitor_team().getId()) >= 0) {
                statement.append(((fields != 0) ? " AND " : "") + COLUMN_VISITOR_TEAMID + " = " + tmpLong);
                fields++;
            }
        }
        if ((tmpLong = object.getDate()) > 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_DATE + " = " + tmpLong );
            fields++;
        }
        if ((tmpString = object.getReport()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_REPORT + " LIKE '%" + tmpString + "%'");
            fields++;
        }
            if ((tmpInt = object.getHome_score()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_HOME_SCORE + " = " + tmpInt );
            fields++;
        }
        if ((tmpInt = object.getVisitor_score()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_VISITOR_SCORE + " = " + tmpInt );
            fields++;
        }if ((tmpFloat = object.getDuration()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_DURATION + " = " + tmpFloat );
            fields++;
        }

        if (fields > 0) {

            long id;
            long home_teamid;
            long visitor_teamid;
            long date;
            String report;
            int home_score;
            int visitor_score;
            float duration;

            Cursor res = db.rawQuery( statement.toString(), null );
            if(res.moveToFirst())

                while(res.isAfterLast() == false) {
                    id = res.getInt(res.getColumnIndex(COLUMN_ID));
                    home_teamid = res.getLong(res.getColumnIndex(COLUMN_HOME_TEAMID));
                    visitor_teamid = res.getLong(res.getColumnIndex(COLUMN_VISITOR_TEAMID));
                    date = res.getLong(res.getColumnIndex(COLUMN_DATE));
                    report = res.getString(res.getColumnIndex(COLUMN_REPORT));
                    home_score = res.getInt(res.getColumnIndex(COLUMN_HOME_SCORE));
                    visitor_score = res.getInt(res.getColumnIndex(COLUMN_VISITOR_SCORE));
                    duration = res.getFloat(res.getColumnIndex(COLUMN_DURATION));
                    resGame.add(new Game(id,team_dao.getById(home_teamid),team_dao.getById(visitor_teamid), date, report,
                            home_score,visitor_score, duration));
                    res.moveToNext();
                }
        }


        return resGame;
    }
}
