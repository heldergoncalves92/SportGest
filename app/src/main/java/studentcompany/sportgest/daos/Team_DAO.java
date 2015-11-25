package studentcompany.sportgest.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import studentcompany.sportgest.daos.db.MyDB;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Team;

public class Team_DAO extends GenericDAO<Team> implements IGenericDAO<Team>{
    //Database name
    private SQLiteDatabase db;

    //Table names
    public static final String TABLE_NAME         = "TEAM";

    //Table columns
    public static final String COLUMN_ID          = "ID";
    public static final String COLUMN_NAME        = "NAME";
    public static final String COLUMN_DESCRIPTION = "DESCRIPTION";
    public static final String COLUMN_LOGO        = "LOGO";
    public static final String COLUMN_SEASON      = "SEASON";
    public static final String COLUMN_IS_COM      = "IS_COM";

    //Create table
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID          + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME        + " TEXT NOT NULL, " +
            COLUMN_DESCRIPTION + " TEXT, " +
            COLUMN_LOGO        + " TEXT, " +
            COLUMN_SEASON      + " INTEGER NOT NULL, " +
            COLUMN_IS_COM      + " INTEGER NOT NULL);";

    //Drop table
    public static  final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + "; ";

    public Team_DAO(Context context) {
        this.db = MyDB.getInstance(context).db;
    }


    @Override
    public ArrayList<Team> getAll() throws GenericDAOException {

        //aux variables;
        ArrayList<Team> resTeam = new ArrayList<>();
        int id;
        String name;
        String description;
        String logo;
        int season;
        int is_com;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME, null );
        res.moveToFirst();

        //Parse data
        while(res.isAfterLast() == false) {
            id = res.getInt(res.getColumnIndex(COLUMN_ID));
            name = res.getString(res.getColumnIndex(COLUMN_NAME));
            description = res.getString(res.getColumnIndex(COLUMN_DESCRIPTION));
            logo = res.getString(res.getColumnIndex(COLUMN_LOGO));
            season = res.getInt(res.getColumnIndex(COLUMN_SEASON));
            is_com = res.getInt(res.getColumnIndex(COLUMN_IS_COM));
            resTeam.add(new Team(id, name, description, logo, season, is_com));
            res.moveToNext();
        }

        return resTeam;
    }

    @Override
    public Team getById(int id) throws GenericDAOException {

        //aux variables;
        Team resTeam;
        String name;
        String description;
        String logo;
        int season;
        int is_com;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + "=" + id, null );
        res.moveToFirst();

        //Parse data
        id = res.getInt(res.getColumnIndex(COLUMN_ID));
        name = res.getString(res.getColumnIndex(COLUMN_NAME));
        description = res.getString(res.getColumnIndex(COLUMN_DESCRIPTION));
        logo = res.getString(res.getColumnIndex(COLUMN_LOGO));
        season = res.getInt(res.getColumnIndex(COLUMN_SEASON));
        is_com = res.getInt(res.getColumnIndex(COLUMN_IS_COM));
        resTeam = new Team(id, name, description, logo, season, is_com);

        return resTeam;
    }

    @Override
    public long insert(Team object) throws GenericDAOException {

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME       , object.getName());
        contentValues.put(COLUMN_DESCRIPTION, object.getDescription());
        contentValues.put(COLUMN_LOGO       , object.getLogo());
        contentValues.put(COLUMN_SEASON     , object.getSeason());
        contentValues.put(COLUMN_IS_COM     , object.getIs_com());

        return db.insert(TABLE_NAME, null, contentValues);
    }

    @Override
    public boolean delete(Team object) throws GenericDAOException {
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
    boolean deleteById(int id) {
        return false;
    }

    @Override
    public boolean update(Team object) throws GenericDAOException {

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME       , object.getName());
        contentValues.put(COLUMN_DESCRIPTION, object.getDescription());
        contentValues.put(COLUMN_LOGO       , object.getLogo());
        contentValues.put(COLUMN_SEASON     , object.getSeason());
        contentValues.put(COLUMN_IS_COM     , object.getIs_com());

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
    int numberOfRows() {
        return 0;
    }

    @Override
    public boolean exists(Team object) throws GenericDAOException {

        if(object==null)
            return false;

        int fields = 0;
        String tmpString;
        int tmpInt;
        float tmpFloat;

        StringBuilder statement = new StringBuilder("SELECT * FROM "+ TABLE_NAME +" where ");
        if ((tmpInt = object.getId()) >= 0) {
            statement.append(COLUMN_ID + "=" + tmpInt);
            fields++;
        }
        if ((tmpString = object.getName()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_NAME + " = '" + tmpString + "'");
            fields++;
        }
        if ((tmpString = object.getDescription()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_DESCRIPTION + " = '" + tmpString + "'");
            fields++;
        }
        if ((tmpString = object.getLogo()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_LOGO + " = '" + tmpString + "'");
            fields++;
        }
        if ((tmpInt = object.getSeason()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_SEASON + " = " + tmpInt );
            fields++;
        }
        if ((tmpInt = object.getIs_com()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_IS_COM + " = " + tmpInt );
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
    public List<Team> getByCriteria(Team object) throws GenericDAOException {

        if(object==null)
            return null;

        List<Team> resTeam = new ArrayList<>();
        int fields = 0;
        String tmpString;
        int tmpInt;
        float tmpFloat;

        StringBuilder statement = new StringBuilder("SELECT * FROM "+ TABLE_NAME +" where ");
        if ((tmpInt = object.getId()) >= 0) {
            statement.append(COLUMN_ID + "=" + tmpInt);
            fields++;
        }
        if ((tmpString = object.getName()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_NAME + " LIKE '%" + tmpString + "%'");
            fields++;
        }
        if ((tmpString = object.getDescription()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_DESCRIPTION + " LIKE '%" + tmpString + "%'");
            fields++;
        }
        if ((tmpString = object.getLogo()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_LOGO + " LIKE '%" + tmpString + "%'");
            fields++;
        }
        if ((tmpInt = object.getSeason()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_SEASON + " = " + tmpInt );
            fields++;
        }
        if ((tmpInt = object.getIs_com()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_IS_COM + " = " + tmpInt );
            fields++;
        }

        if (fields > 0) {

            int id;
            String name;
            String description;
            String logo;
            int season;
            int is_com;

            Cursor res = db.rawQuery( statement.toString(), null );
            if(res.moveToFirst())

                while(res.isAfterLast() == false) {
                    id = res.getInt(res.getColumnIndex(COLUMN_ID));
                    name = res.getString(res.getColumnIndex(COLUMN_NAME));
                    description = res.getString(res.getColumnIndex(COLUMN_DESCRIPTION));
                    logo = res.getString(res.getColumnIndex(COLUMN_LOGO));
                    season = res.getInt(res.getColumnIndex(COLUMN_SEASON));
                    is_com = res.getInt(res.getColumnIndex(COLUMN_IS_COM));
                    resTeam.add(new Team(id, name, description, logo, season, is_com));
                    res.moveToNext();
                }
        }


        return resTeam;
    }
}
