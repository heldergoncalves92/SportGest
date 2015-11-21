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
        int userId;
        int teamId;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME, null );
        res.moveToFirst();

        //Parse data
        while(res.isAfterLast() == false) {
            userId = res.getInt(res.getColumnIndex(COLUMN_USER_ID));
            teamId = res.getInt(res.getColumnIndex(COLUMN_TEAM_ID));
            resList.add(
                    new Pair<>(
                            user_dao.getById(userId),
                            team_dao.getById(teamId)));
            res.moveToNext();
        }

        return resList;
    }


    @Override
    public List<Pair<User, Team>> getByFirstId(int id) throws GenericDAOException {
        //aux variables;
        ArrayList<Pair<User, Team>> resList = new ArrayList<>();
        User user;
        int teamId;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USER_ID + "=" + id, null );
        res.moveToFirst();

        //Parse data
        user = user_dao.getById(id);
        while(res.isAfterLast() == false) {
            teamId = res.getInt(res.getColumnIndex(COLUMN_TEAM_ID));
            resList.add(
                    new Pair<>(
                            user,
                            team_dao.getById(teamId)));
            res.moveToNext();
        }

        return resList;
    }

    @Override
    public List<Pair<User, Team>> getBySecondId(int id) throws GenericDAOException {
        //aux variables;
        ArrayList<Pair<User, Team>> resList = new ArrayList<>();
        Team team;
        int userId;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_TEAM_ID + "=" + id, null );
        res.moveToFirst();

        //Parse data
        team = team_dao.getById(id);
        while(res.isAfterLast() == false) {
            userId = res.getInt(res.getColumnIndex(COLUMN_USER_ID));
            resList.add(
                    new Pair<>(
                            user_dao.getById(userId),
                            team));
            res.moveToNext();
        }

        return resList;
    }

    @Override
    public boolean insert(Pair<User, Team> object) throws GenericDAOException {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_ID, object.getFirst().getId());
        contentValues.put(COLUMN_TEAM_ID, object.getSecond().getId());

        return db.insert(TABLE_NAME, null, contentValues) > 0 ? true : false;
    }

    @Override
    public boolean delete(Pair<User, Team> object) throws GenericDAOException {
        return db.delete(TABLE_NAME,
                COLUMN_USER_ID + " = ? , " + COLUMN_TEAM_ID + " = ? ",
                new String[] { Integer.toString(object.getFirst().getId()), Integer.toString(object.getSecond().getId()) })  > 0 ? true : false;
    }

    @Override
    public boolean exists(Pair<User, Team> object) throws GenericDAOException {
        if(object==null)
            return false;

        if(object.getFirst()==null || object.getSecond() == null)
            return false;

        StringBuilder statement = new StringBuilder("SELECT * FROM "+ TABLE_NAME +" where ");
            statement.append(COLUMN_USER_ID + "=" + object.getFirst().getId());
            statement.append(" AND " + COLUMN_TEAM_ID + "=" + object.getSecond().getId());

            Cursor res = db.rawQuery(statement.toString(), null);
            return res.moveToFirst();
    }
}


