package studentcompany.sportgest.daos;
//TODO all

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    public static final String COLUMN_DATE           = "DATE";
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
        final ArrayList<Training> resTraining = new ArrayList<>();
        long id;
        String title;
        String description;
        int date;
        int totalDuration;
        long teamId;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME, null );
        res.moveToFirst();

        //Parse data
        while(res.isAfterLast() == false) {
            id = res.getLong(res.getColumnIndex(COLUMN_ID));
            title = res.getString(res.getColumnIndex(COLUMN_TITLE));
            description = res.getString(res.getColumnIndex(COLUMN_DESCRIPTION));
            date = res.getInt(res.getColumnIndex(COLUMN_DATE));
            totalDuration = res.getInt(res.getColumnIndex(COLUMN_TOTAL_DURATION));
            teamId = res.getLong(res.getColumnIndex(COLUMN_TEAM_ID));
            resTraining.add(new Training(id, title, description, date, totalDuration,
                    team_dao.getById(teamId)));
            res.moveToNext();
        }

        //Sorting
        Collections.sort(resTraining, new Comparator<Training>() {
            @Override
            public int compare(Training lhs, Training rhs) {
                return lhs.getTitle().compareTo(rhs.getTitle());
            }
        });

        return resTraining;
    }

    @Override
    public Training getById(long id) throws GenericDAOException {

        //aux variables;
        Training resTraining;
        String title;
        String description;
        int date;
        int totalDuration;
        long teamId;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + "=" + id, null );
        res.moveToFirst();

        //Parse data
        title = res.getString(res.getColumnIndex(COLUMN_TITLE));
        description = res.getString(res.getColumnIndex(COLUMN_DESCRIPTION));
        date = res.getInt(res.getColumnIndex(COLUMN_DATE));
        totalDuration = res.getInt(res.getColumnIndex(COLUMN_TOTAL_DURATION));
        teamId = res.getLong(res.getColumnIndex(COLUMN_TEAM_ID));
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
                new String[] { Long.toString(object.getId()) });
        return true;
    }

    public boolean deleteById(long id) {

        int deletedCount = db.delete(TABLE_NAME,
                COLUMN_ID + " = ? ",
                new String[] { Long.toString(id) });
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
                new String[] { Long.toString(object.getId()) } );
        return true;
    }

    public int numberOfRows(){
        return (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
    }

    @Override
    public boolean exists(Training object) throws GenericDAOException {

        if(object==null)
            return false;

        int fields = 0;
        String tmpString;
        int tmpInt;
        long tmpLong;

        StringBuilder statement = new StringBuilder("SELECT * FROM "+ TABLE_NAME +" where ");
        if ((tmpLong = object.getId()) >= 0) {
            statement.append(COLUMN_ID + "=" + tmpLong);
            fields++;
        }
        if ((tmpString = object.getTitle()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_TITLE + " = '" + tmpString + "'");
            fields++;
        }
        if ((tmpString = object.getDescription()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_DESCRIPTION + " = '" + tmpString + "'");
            fields++;
        }
        if ((tmpInt = object.getDate()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_DATE + " = " + tmpInt );
            fields++;
        }
        if ((tmpInt = object.getTotalDuration()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_TOTAL_DURATION + " = " + tmpInt );
            fields++;
        }
        if ((tmpLong = object.getTeam().getId()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_TEAM_ID + " = " + tmpLong );
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
    public List<Training> getByCriteria(Training object) throws GenericDAOException {

        if(object==null)
            return null;

        List<Training> resTraining = new ArrayList<>();
        int fields = 0;
        String tmpString;
        int tmpInt;
        long tmpLong;

        StringBuilder statement = new StringBuilder("SELECT * FROM "+ TABLE_NAME +" where ");
        if ((tmpLong = object.getId()) >= 0) {
            statement.append(COLUMN_ID + "=" + tmpLong);
            fields++;
        }
        if ((tmpString = object.getTitle()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_TITLE + " LIKE '%" + tmpString + "%'");
            fields++;
        }
        if ((tmpString = object.getDescription()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_DESCRIPTION + " LIKE '%" + tmpString + "%'");
            fields++;
        }
        if ((tmpInt = object.getDate()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_DATE + " = " + tmpInt );
            fields++;
        }
        if ((tmpInt = object.getTotalDuration()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_TOTAL_DURATION + " = " + tmpInt );
            fields++;
        }
        if ((tmpLong = object.getTeam().getId()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_TEAM_ID + " = " + tmpLong );
            fields++;
        }

        if (fields > 0) {

            long id;
            String title;
            String description;
            int date;
            int totalDuration;
            long teamId;

            Cursor res = db.rawQuery( statement.toString(), null );
            if(res.moveToFirst())

                while(res.isAfterLast() == false) {
                    id = res.getLong(res.getColumnIndex(COLUMN_ID));
                    title = res.getString(res.getColumnIndex(COLUMN_TITLE));
                    description = res.getString(res.getColumnIndex(COLUMN_DESCRIPTION));
                    date = res.getInt(res.getColumnIndex(COLUMN_DATE));
                    totalDuration = res.getInt(res.getColumnIndex(COLUMN_TOTAL_DURATION));
                    teamId = res.getLong(res.getColumnIndex(COLUMN_TEAM_ID));
                    resTraining.add(new Training(id, title, description, date, totalDuration,
                            team_dao.getById(teamId)));
                    res.moveToNext();
                }
        }


        return resTraining;
    }
}
