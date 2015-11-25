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
import studentcompany.sportgest.domains.Player;
import studentcompany.sportgest.domains.Team;
import studentcompany.sportgest.domains.Position;

public class Player_DAO extends GenericDAO<Player> implements IGenericDAO<Player>{
    //Database name
    private SQLiteDatabase db;

    //Dependencies DAOs
    private Team_DAO          team_dao;
    private Position_DAO      position_dao;

    //Table names
    public static final String TABLE_NAME                   = "PLAYER";

    public final static String COLUMN_ID = "ID";
    public final static String COLUMN_NICKNAME = "NICKNAME";
    public final static String COLUMN_NAME = "NAME";
    public final static String COLUMN_NATIONALITY = "NATIONALITY";
    public final static String COLUMN_MARITAL_STATUS = "MARITAL_STATUS";
    public final static String COLUMN_BIRTHDATE = "BIRTHDATE";
    public final static String COLUMN_HEIGHT = "HEIGHT";
    public final static String COLUMN_WEIGHT = "WEIGHT";
    public final static String COLUMN_ADDRESS = "ADDRESS";
    public final static String COLUMN_GENDER = "GENDER";
    public final static String COLUMN_PHOTO = "PHOTO";
    public final static String COLUMN_EMAIL = "EMAIL";
    public final static String COLUMN_PREFERED_FOOT = "PREFERED_FOOT";
    public final static String COLUMN_NUMBER = "NUMBER";
    public final static String COLUMN_TEAM_ID = "TEAM_ID";
    public final static String COLUMN_BETTER_POSITION = "BETTER_POSITION";

    //Create table
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (\n" +
            COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, \n" +
            COLUMN_NICKNAME + " TEXT, \n" +
            COLUMN_NAME + " TEXT, \n" +
            COLUMN_NATIONALITY + " TEXT, \n" +
            COLUMN_MARITAL_STATUS + " TEXT, \n" +
            COLUMN_BIRTHDATE + " INTEGER, \n" +
            COLUMN_HEIGHT + " FLOAT, \n" +
            COLUMN_WEIGHT + " FLOAT, \n" +
            COLUMN_GENDER + " FLOAT, \n" +
            COLUMN_PHOTO + " FLOAT, \n" +
            COLUMN_ADDRESS + " TEXT, \n" +
            COLUMN_PHOTO + " TEXT, \n" +
            COLUMN_EMAIL + " TEXT, \n" +
            COLUMN_PREFERED_FOOT + " TEXT, \n" +
            COLUMN_NUMBER + " INTEGER, \n" +
            "FOREIGN KEY(" + COLUMN_TEAM_ID + ") REFERENCES TEAM(ID), \n" +
            "FOREIGN KEY(" + COLUMN_BETTER_POSITION + ") REFERENCES POSITION(ID));\n";

    //Drop table
    public static  final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + "; ";

    public Player_DAO(Context context) {
        this.db = MyDB.getInstance(context).db;
        this.team_dao = new Team_DAO(context);
        this.position_dao = new Position_DAO(context);
    }

    @Override
    public List<Player> getAll() throws GenericDAOException {
        //aux variables
        ArrayList<Player> resPlayer = new ArrayList<>();
        int id;
        String nickname;
        String name;
        String nationality;
        String marital_status;
        int dob;
        float height;
        float weight;
        String address;
        String gender;
        String photo;
        String email;
        String prefered_foot;
        int number;
        int team;
        int position;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME, null );
        res.moveToFirst();

        //Parse data
        while(res.isAfterLast() == false) {
            id = res.getInt(res.getColumnIndex(COLUMN_ID));
            nickname = res.getString(res.getColumnIndex(COLUMN_NICKNAME));
            name = res.getString(res.getColumnIndex(COLUMN_NAME));
            nationality = res.getString(res.getColumnIndex(COLUMN_NATIONALITY));
            marital_status = res.getString(res.getColumnIndex(COLUMN_MARITAL_STATUS));
            dob=res.getInt(res.getColumnIndex(COLUMN_BIRTHDATE));
            height = res.getFloat(res.getColumnIndex(COLUMN_HEIGHT));
            weight = res.getInt(res.getColumnIndex(COLUMN_WEIGHT));
            address= res.getString(res.getColumnIndex(COLUMN_ADDRESS));
            gender= res.getString(res.getColumnIndex(COLUMN_GENDER));
            photo= res.getString(res.getColumnIndex(COLUMN_PHOTO));
            email= res.getString(res.getColumnIndex(COLUMN_EMAIL));
            prefered_foot = res.getString(res.getColumnIndex(COLUMN_PREFERED_FOOT));
            number= res.getInt(res.getColumnIndex(COLUMN_NUMBER));
            team=res.getInt(res.getColumnIndex(COLUMN_TEAM_ID));
            position=res.getInt(res.getColumnIndex(COLUMN_BETTER_POSITION));

            resPlayer.add(new Player(id,nickname,name,nationality,marital_status,dob,height,weight,address,gender,photo,email,prefered_foot,number,
                    team_dao.getById(team),position_dao.getById(position)));
            res.moveToNext();
        }
        return resPlayer;

    }

    @Override
    public Player getById(int id) throws GenericDAOException {
        //aux variables
        Player resPlayer;
        String nickname;
        String name;
        String nationality;
        String marital_status;
        int dob;
        float height;
        float weight;
        String address;
        String gender;
        String photo;
        String email;
        String prefered_foot;
        int number;
        int team;
        int position;

        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + "=" + id, null );
        res.moveToFirst();

        //Parse data

            id = res.getInt(res.getColumnIndex(COLUMN_ID));
            nickname = res.getString(res.getColumnIndex(COLUMN_NICKNAME));
            name = res.getString(res.getColumnIndex(COLUMN_NAME));
            nationality = res.getString(res.getColumnIndex(COLUMN_NATIONALITY));
            marital_status = res.getString(res.getColumnIndex(COLUMN_MARITAL_STATUS));
            dob=res.getInt(res.getColumnIndex(COLUMN_BIRTHDATE));
            height = res.getFloat(res.getColumnIndex(COLUMN_HEIGHT));
            weight = res.getInt(res.getColumnIndex(COLUMN_WEIGHT));
            address= res.getString(res.getColumnIndex(COLUMN_ADDRESS));
            gender= res.getString(res.getColumnIndex(COLUMN_GENDER));
            photo= res.getString(res.getColumnIndex(COLUMN_PHOTO));
            email= res.getString(res.getColumnIndex(COLUMN_EMAIL));
            prefered_foot = res.getString(res.getColumnIndex(COLUMN_PREFERED_FOOT));
            number= res.getInt(res.getColumnIndex(COLUMN_NUMBER));
            team=res.getInt(res.getColumnIndex(COLUMN_TEAM_ID));
            position=res.getInt(res.getColumnIndex(COLUMN_BETTER_POSITION));

            resPlayer=new Player(id,nickname,name,nationality,marital_status,dob,height,weight,address,gender,photo,email,prefered_foot,number,
                    team_dao.getById(team),position_dao.getById(position));
        return resPlayer;

    }

    @Override
    public long insert(Player object) throws GenericDAOException {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, object.getId());
        contentValues.put(COLUMN_NICKNAME,  object.getNickname());
        contentValues.put(COLUMN_NAME,  object.getName());
        contentValues.put(COLUMN_NATIONALITY,  object.getNationality());
        contentValues.put(COLUMN_BIRTHDATE, object.getBirthDate());
        contentValues.put(COLUMN_HEIGHT, object.getHeight());
        contentValues.put(COLUMN_WEIGHT, object.getWeight());
        contentValues.put(COLUMN_ADDRESS, object.getAddress());
        contentValues.put(COLUMN_GENDER, object.getGender());
        contentValues.put(COLUMN_PHOTO, object.getPhoto());
        contentValues.put(COLUMN_EMAIL, object.getEmail());
        contentValues.put(COLUMN_PREFERED_FOOT, object.getNumber());
        contentValues.put(COLUMN_TEAM_ID, object.getTeam().getId());
        contentValues.put(COLUMN_BETTER_POSITION, object.getPosition().getId());

        return db.insert(TABLE_NAME, null, contentValues);
    }

    @Override
    public boolean delete(Player object) throws GenericDAOException {
        int deletedCount = db.delete(TABLE_NAME,
                COLUMN_ID + " = ? ",
                new String[] { Integer.toString(object.getId()) });
        return true;
    }

    @Override
    boolean deleteById(int id) {
        int deletedCount = db.delete(TABLE_NAME,
                COLUMN_ID + " = ? ",
                new String[] { Integer.toString(id) });
        return true;
    }

    @Override
    public boolean update(Player object) throws GenericDAOException {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, object.getId());
        contentValues.put(COLUMN_NICKNAME,  object.getNickname());
        contentValues.put(COLUMN_NAME,  object.getName());
        contentValues.put(COLUMN_NATIONALITY,  object.getNationality());
        contentValues.put(COLUMN_BIRTHDATE, object.getBirthDate());
        contentValues.put(COLUMN_HEIGHT, object.getHeight());
        contentValues.put(COLUMN_WEIGHT, object.getWeight());
        contentValues.put(COLUMN_ADDRESS, object.getAddress());
        contentValues.put(COLUMN_GENDER, object.getGender());
        contentValues.put(COLUMN_PHOTO, object.getPhoto());
        contentValues.put(COLUMN_EMAIL, object.getEmail());
        contentValues.put(COLUMN_PREFERED_FOOT, object.getNumber());
        contentValues.put(COLUMN_TEAM_ID, object.getTeam().getId());
        contentValues.put(COLUMN_BETTER_POSITION, object.getPosition().getId());

        db.update(TABLE_NAME,
                contentValues,
                COLUMN_ID + " = ? ",
                new String[]{Integer.toString(object.getId())});
        return true;
    }

    @Override
    int numberOfRows() {
        return (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
    }

    @Override
    public boolean exists(Player object) throws GenericDAOException {
        return false;
    }

    @Override
    public List<Player> getByCriteria(Player object) throws GenericDAOException {
        return null;
    }
}
