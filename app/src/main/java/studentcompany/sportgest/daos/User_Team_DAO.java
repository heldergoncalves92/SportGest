package studentcompany.sportgest.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import studentcompany.sportgest.daos.db.MyDB;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Team;
import studentcompany.sportgest.domains.User;

public class User_Team_DAO extends GenericPairDAO<User, Team> implements IGenericPairDAO<User, Team> {
    //Database name
    private SQLiteDatabase db;

    //Dependencies DAOs
    private User_DAO user_dao;
    private Team_DAO team_dao;

    //Table names
    public static final String TABLE_NAME = "USER_TEAM";

    //Table columns
    public static final String COLUMN_USER_ID = "USER_ID";
    public static final String COLUMN_TEAM_ID = "TEAM_ID";

    //Create table
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_USER_ID + " INTEGER NOT NULL, " +
            COLUMN_TEAM_ID + " INTEGER NOT NULL, " +
            "PRIMARY KEY (" + COLUMN_USER_ID + ", " + COLUMN_TEAM_ID + "), " +
            "FOREIGN KEY(" + COLUMN_USER_ID + ") REFERENCES " + User_DAO.TABLE_NAME + "(" + User_DAO.COLUMN_ID + "), " +
            "FOREIGN KEY(" + COLUMN_TEAM_ID + ") REFERENCES " + Team_DAO.TABLE_NAME + "(" + Team_DAO.COLUMN_ID + "));";

    //Drop table
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + "; ";

    public User_Team_DAO(Context context) {
        this.db = MyDB.getInstance(context).db;
        this.user_dao = new User_DAO(context);
        this.team_dao = new Team_DAO(context);
    }


    @Override
    public List<Pair<User, Team>> getAll() throws GenericDAOException {
        //aux variables;
        ArrayList<Pair<User, Team>> resList = new ArrayList<>();
        long userId;
        long teamId;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME, null );
        res.moveToFirst();

        //Parse data
        while(!res.isAfterLast()) {
            userId = res.getLong(res.getColumnIndex(COLUMN_USER_ID));
            teamId = res.getLong(res.getColumnIndex(COLUMN_TEAM_ID));
            resList.add(
                    new Pair<>(
                            user_dao.getById(userId),
                            team_dao.getById(teamId)));
            res.moveToNext();
        }
        res.close(); // Close the Cursor
        return resList;
    }


    @Override
    public List<Team> getByFirstId(long id) throws GenericDAOException {
        //aux variables;
        ArrayList<Team> resList = new ArrayList<>();
        long teamId;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USER_ID + "=" + id, null );
        res.moveToFirst();

        //Parse data
        while(!res.isAfterLast()) {
            teamId = res.getLong(res.getColumnIndex(COLUMN_TEAM_ID));
            resList.add(team_dao.getById(teamId));
            res.moveToNext();
        }
        res.close(); // Close the Cursor
        return resList;
    }

    @Override
    public List<User> getBySecondId(long id) throws GenericDAOException {
        //aux variables;
        ArrayList<User> resList = new ArrayList<>();
        long userId;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_TEAM_ID + "=" + id, null );
        res.moveToFirst();

        //Parse data
        while(!res.isAfterLast()) {
            userId = res.getLong(res.getColumnIndex(COLUMN_USER_ID));
            resList.add(user_dao.getById(userId));
            res.moveToNext();
        }
        res.close(); // Close the Cursor
        return resList;
    }

    @Override
    public long insert(Pair<User, Team> object) throws GenericDAOException {

        if(object==null)
            return -1;

        if(object.getFirst() == null || object.getSecond() == null)
            return -1;

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_ID, object.getFirst().getId());
        contentValues.put(COLUMN_TEAM_ID, object.getSecond().getId());

        return db.insert(TABLE_NAME, null, contentValues);
    }

    @Override
    public boolean delete(Pair<User, Team> object) throws GenericDAOException {

        if(object==null)
            return false;

        if(object.getFirst() == null || object.getSecond() == null)
            return false;

        return db.delete(TABLE_NAME,
                COLUMN_USER_ID + " = ? AND " + COLUMN_TEAM_ID + " = ? ",
                new String[] { Long.toString(object.getFirst().getId()), Long.toString(object.getSecond().getId()) })  > 0;
    }

    @Override
    public boolean exists(Pair<User, Team> object) throws GenericDAOException {
        if(object==null)
            return false;

        if(object.getFirst()==null || object.getSecond() == null)
            return false;

        StringBuilder statement = new StringBuilder("SELECT * FROM "+ TABLE_NAME +" WHERE ");
        statement.append(COLUMN_USER_ID).append("=").append(object.getFirst().getId());
        statement.append(" AND ").append(COLUMN_TEAM_ID).append("=").append(object.getSecond().getId());

        Cursor res = db.rawQuery(statement.toString(), null);

        boolean re = res.moveToFirst();
        res.close(); // Close the Cursor
        return re;
    }
}


