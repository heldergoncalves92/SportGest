package studentcompany.sportgest.daos;
//TODO all

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import studentcompany.sportgest.daos.db.MyDB;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Training;

public class Training_DAO extends GenericDAO<Training> implements IGenericDAO<Training>{
    //Database name
    private SQLiteDatabase db;

    //Dependencies DAOs
    private Team_DAO team_dao;

    //Table names
    public static final String TABLE_NAME            = "TRAINING";

    //Table columns
    public static final String COLUMN_ID             = "ID";
    public static final String COLUMN_TITLE          = "TITLE";
    public static final String COLUMN_DESCRIPTION    = "DESCRIPTION";
    public static final String COLUMN_DATE           = "\"DATE\"";
    public static final String COLUMN_TOTAL_DURATION = "TOTAL_DURATION";
    public static final String COLUMN_TEAM_ID        = "TEAM_ID";

    //Create table
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID             + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_TITLE          + " TEXT NOT NULL, " +
            COLUMN_DESCRIPTION    + " TEXT, " +
            COLUMN_DATE           + " INTEGER NOT NULL, " +
            COLUMN_TOTAL_DURATION + " INTEGER, " +
            COLUMN_TEAM_ID        + " INTEGER NOT NULL, " +
            "FOREIGN KEY("+COLUMN_TEAM_ID+") REFERENCES "+Team_DAO.TABLE_NAME+"("+Team_DAO.COLUMN_ID+"));";

    //Drop table
    public static  final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + "; ";

    public Training_DAO(Context context) {
        this.db = MyDB.getInstance(context).db;
        this.team_dao = new Team_DAO(context);
    }

    @Override
    public ArrayList<Training> getAll() throws GenericDAOException {

        //aux variables;
        ArrayList<Training> resTraining = new ArrayList<>();
        int id;
        String title;
        String description;
        int date;
        int totalDuration;
        int teamId;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME, null );
        res.moveToFirst();

        //Parse data
        while(res.isAfterLast() == false) {
            id = res.getInt(res.getColumnIndex(COLUMN_ID));
            title = res.getString(res.getColumnIndex(COLUMN_TITLE));
            description = res.getString(res.getColumnIndex(COLUMN_DESCRIPTION));
            date = res.getInt(res.getColumnIndex(COLUMN_DATE));
            totalDuration = res.getInt(res.getColumnIndex(COLUMN_TOTAL_DURATION));
            teamId = res.getInt(res.getColumnIndex(COLUMN_TEAM_ID));
            resTraining.add(new Training(id, title, description, date, totalDuration,
                    team_dao.getById(teamId)));
            res.moveToNext();
        }

        return resTraining;
    }

    @Override
    public Training getById(int id) throws GenericDAOException {

        //aux variables;
        Training resTraining;
        String title;
        String description;
        int date;
        int totalDuration;
        int teamId;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + "=" + id, null );
        res.moveToFirst();

        //Parse data
        title = res.getString(res.getColumnIndex(COLUMN_TITLE));
        description = res.getString(res.getColumnIndex(COLUMN_DESCRIPTION));
        date = res.getInt(res.getColumnIndex(COLUMN_DATE));
        totalDuration = res.getInt(res.getColumnIndex(COLUMN_TOTAL_DURATION));
        teamId = res.getInt(res.getColumnIndex(COLUMN_TEAM_ID));
        resTraining = new Training(id, title, description, date, totalDuration,
                team_dao.getById(teamId));

        return resTraining;
    }

    @Override
    public long insert(Training object) throws GenericDAOException {

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, object.getTitle());
        contentValues.put(COLUMN_DESCRIPTION, object.getDescription());
        contentValues.put(COLUMN_DATE, object.getDate());
        contentValues.put(COLUMN_TOTAL_DURATION, object.getTotalDuration());
        contentValues.put(COLUMN_TEAM_ID, object.getTeam().getId());

        return db.insert(TABLE_NAME, null, contentValues);
    }

    @Override
    public boolean delete(Training object) throws GenericDAOException {
        int deletedCount = db.delete(TABLE_NAME,
                COLUMN_ID + " = ? ",
                new String[] { Integer.toString(object.getId()) });
        return true;
    }

    public boolean deleteById(int id) {

        int deletedCount = db.delete(TABLE_NAME,
                COLUMN_ID + " = ? ",
                new String[] { Integer.toString(id) });
        return true;
    }

    @Override
    public boolean update(Training object) throws GenericDAOException {

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, object.getTitle());
        contentValues.put(COLUMN_DESCRIPTION, object.getDescription());
        contentValues.put(COLUMN_DATE, object.getDate());
        contentValues.put(COLUMN_TOTAL_DURATION, object.getTotalDuration());
        contentValues.put(COLUMN_TEAM_ID, object.getTeam().getId());

        db.update(TABLE_NAME,
                contentValues,
                COLUMN_ID + " = ? ",
                new String[] { Integer.toString(object.getId()) } );
        return true;
    }

    public int numberOfRows(){
        return (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
    }

    @Override
    public boolean exists(Training object) throws GenericDAOException {
        //TODO implement exists
        return false;
    }

    @Override
    public List<Training> getByCriteria(Training object) throws GenericDAOException {
        //TODO implement getByCriteria
        return null;
    }
}
