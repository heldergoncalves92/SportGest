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

public class Player_DAO extends GenericDAO<Player> implements IGenericDAO<Player>{
    //Database name
    private SQLiteDatabase db;

    //Dependencies DAOs
    private Team_DAO          team_dao;
    //private Position_DAO      position_dao;

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
    public final static String COLUMN_PREFERRED_FOOT = "PREFERRED_FOOT";
    public final static String COLUMN_NUMBER = "NUMBER";
    public final static String COLUMN_TEAM_ID = "TEAM_ID";
    //public final static String COLUMN_BETTER_POSITION = "BETTER_POSITION";

    //Create table
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NICKNAME + " TEXT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_NATIONALITY + " TEXT, " +
            COLUMN_MARITAL_STATUS + " TEXT, " +
            COLUMN_BIRTHDATE + " TEXT, " +
            COLUMN_HEIGHT + " INTEGER, " +
            COLUMN_WEIGHT + " INTEGER, " +
            COLUMN_GENDER + " TEXT, " +
            COLUMN_ADDRESS + " TEXT, " +
            COLUMN_PHOTO + " TEXT, " +
            COLUMN_EMAIL + " TEXT, " +
            COLUMN_PREFERRED_FOOT + " INTEGER, " +
            COLUMN_NUMBER + " INTEGER, " +
            COLUMN_TEAM_ID + " INTEGER, " +
            //COLUMN_BETTER_POSITION + " INTEGER, " +
            "FOREIGN KEY(" + COLUMN_TEAM_ID + ") REFERENCES "+Team_DAO.TABLE_NAME+"("+Team_DAO.COLUMN_ID+")); ";
            //"FOREIGN KEY(" + COLUMN_BETTER_POSITION + ") REFERENCES "+Position_DAO.TABLE_NAME+"("+Position_DAO.COLUMN_ID+"));";

    //Drop table
    public static  final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + "; ";

    public Player_DAO(Context context) {
        this.db = MyDB.getInstance(context).db;
        this.team_dao = new Team_DAO(context);
        //this.position_dao = new Position_DAO(context);
    }

    @Override
    public List<Player> getAll() throws GenericDAOException {
        //aux variables
        ArrayList<Player> resPlayer = new ArrayList<>();
        long id;
        String nickname;
        String name;
        String nationality;
        String marital_status;
        String dob;
        int height;
        float weight;
        String address;
        String gender;
        String photo;
        String email;
        String prefered_foot;
        int number;
        long team;
        //long position;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME, null );
        res.moveToFirst();

        //Parse data
        while(res.isAfterLast() == false) {
            id = res.getLong(res.getColumnIndex(COLUMN_ID));
            nickname = res.getString(res.getColumnIndex(COLUMN_NICKNAME));
            name = res.getString(res.getColumnIndex(COLUMN_NAME));
            nationality = res.getString(res.getColumnIndex(COLUMN_NATIONALITY));
            marital_status = res.getString(res.getColumnIndex(COLUMN_MARITAL_STATUS));
            dob=res.getString(res.getColumnIndex(COLUMN_BIRTHDATE));
            height = res.getInt(res.getColumnIndex(COLUMN_HEIGHT));
            weight = res.getFloat(res.getColumnIndex(COLUMN_WEIGHT));
            address= res.getString(res.getColumnIndex(COLUMN_ADDRESS));
            gender= res.getString(res.getColumnIndex(COLUMN_GENDER));
            photo= res.getString(res.getColumnIndex(COLUMN_PHOTO));
            email= res.getString(res.getColumnIndex(COLUMN_EMAIL));
            prefered_foot = res.getString(res.getColumnIndex(COLUMN_PREFERRED_FOOT));
            number= res.getInt(res.getColumnIndex(COLUMN_NUMBER));
            team=res.getLong(res.getColumnIndex(COLUMN_TEAM_ID));

            resPlayer.add(new Player(id,nickname,name,nationality,marital_status,dob,height,weight,address,gender,photo,email,prefered_foot,number,
                    team_dao.getById(team),null));
            res.moveToNext();
        }
        return resPlayer;

    }

    @Override
    public Player getById(long id) throws GenericDAOException {
        //aux variables
        Player resPlayer = null;
        String nickname;
        String name;
        String nationality;
        String marital_status;
        String dob;
        int height;
        float weight;
        String address;
        String gender;
        String photo;
        String email;
        String prefered_foot;
        int number;
        long team;
        //long position;

        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + "=" + id, null );
        res.moveToFirst();

        //Parse data
        if(res.getCount()==1){
            id = res.getLong(res.getColumnIndex(COLUMN_ID));
            nickname = res.getString(res.getColumnIndex(COLUMN_NICKNAME));
            name = res.getString(res.getColumnIndex(COLUMN_NAME));
            nationality = res.getString(res.getColumnIndex(COLUMN_NATIONALITY));
            marital_status = res.getString(res.getColumnIndex(COLUMN_MARITAL_STATUS));
            dob=res.getString(res.getColumnIndex(COLUMN_BIRTHDATE));
            height = res.getInt(res.getColumnIndex(COLUMN_HEIGHT));
            weight = res.getFloat(res.getColumnIndex(COLUMN_WEIGHT));
            address= res.getString(res.getColumnIndex(COLUMN_ADDRESS));
            gender= res.getString(res.getColumnIndex(COLUMN_GENDER));
            photo= res.getString(res.getColumnIndex(COLUMN_PHOTO));
            email= res.getString(res.getColumnIndex(COLUMN_EMAIL));
            prefered_foot = res.getString(res.getColumnIndex(COLUMN_PREFERRED_FOOT));
            number= res.getInt(res.getColumnIndex(COLUMN_NUMBER));
            team=res.getLong(res.getColumnIndex(COLUMN_TEAM_ID));

            if(team>0) {
                Team teamToInsert = team_dao.getById(team);
                resPlayer = new Player(id, nickname, name, nationality, marital_status, dob, height, weight, address, gender, photo, email, prefered_foot, number,
                        teamToInsert, null);
            } else
                resPlayer=new Player(id,nickname,name,nationality,marital_status,dob,height,weight,address,gender,photo,email,prefered_foot,number,
                        null,null);
        }
        res.close();
        return resPlayer;

    }

    @Override
    public long insert(Player object) throws GenericDAOException {

        ContentValues contentValues = new ContentValues();

        if(object.getId()>0)
            contentValues.put(COLUMN_ID, object.getId());
        contentValues.put(COLUMN_NICKNAME,  object.getNickname());
        contentValues.put(COLUMN_NAME,  object.getName());
        contentValues.put(COLUMN_NATIONALITY,  object.getNationality());
        contentValues.put(COLUMN_MARITAL_STATUS,  object.getMarital_status());
        contentValues.put(COLUMN_BIRTHDATE, object.getBirthDate());
        contentValues.put(COLUMN_HEIGHT, object.getHeight());
        contentValues.put(COLUMN_WEIGHT, object.getWeight());
        contentValues.put(COLUMN_ADDRESS, object.getAddress());
        contentValues.put(COLUMN_GENDER, object.getGender());
        contentValues.put(COLUMN_PHOTO, object.getPhoto());
        contentValues.put(COLUMN_EMAIL, object.getEmail());
        contentValues.put(COLUMN_PREFERRED_FOOT, object.getPreferredFoot());
        contentValues.put(COLUMN_NUMBER, object.getNumber());
        contentValues.put(COLUMN_TEAM_ID, object.getTeam() != null ? object.getTeam().getId() : null);

        return db.insert(TABLE_NAME, null, contentValues);
    }

    @Override
    public boolean delete(Player object) throws GenericDAOException {

        if(object==null)
            return false;

        return deleteById(object.getId());
    }

    public boolean deleteById(long id){
        return db.delete(TABLE_NAME,
                COLUMN_ID + " = ? ",
                new String[]{Long.toString(id)}) > 0;
    }

    @Override
    public boolean update(Player object) throws GenericDAOException {

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, object.getId());
        contentValues.put(COLUMN_NICKNAME,  object.getNickname());
        contentValues.put(COLUMN_MARITAL_STATUS,  object.getMarital_status());
        contentValues.put(COLUMN_NAME,  object.getName());
        contentValues.put(COLUMN_NATIONALITY,  object.getNationality());
        contentValues.put(COLUMN_BIRTHDATE, object.getBirthDate());
        contentValues.put(COLUMN_HEIGHT, object.getHeight());
        contentValues.put(COLUMN_WEIGHT, object.getWeight());
        contentValues.put(COLUMN_ADDRESS, object.getAddress());
        contentValues.put(COLUMN_GENDER, object.getGender());
        contentValues.put(COLUMN_PHOTO, object.getPhoto());
        contentValues.put(COLUMN_EMAIL, object.getEmail());
        contentValues.put(COLUMN_PREFERRED_FOOT, object.getNumber());
        if(object.getTeam()!=null)
            contentValues.put(COLUMN_TEAM_ID, object.getTeam().getId());
        else
            contentValues.putNull(COLUMN_TEAM_ID);

        db.update(TABLE_NAME,
                contentValues,
                COLUMN_ID + " = ? ",
                new String[]{Long.toString(object.getId())});
        return true;
    }

    @Override
    public int numberOfRows() {
        return (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
    }

    @Override
    public boolean exists(Player object) throws GenericDAOException {

        if(object==null)
            return false;

        int fields = 0;
        String tmpString;
        int tmpInt;
        float tmpFloat;
        long tmpLong;

        StringBuilder statement = new StringBuilder("SELECT * FROM "+ TABLE_NAME +" WHERE ");
        if ((tmpLong = object.getId()) >= 0) {
            statement.append(COLUMN_ID + "=" + tmpLong);
            fields++;
        }
        if ((tmpString = object.getNickname()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_NICKNAME + " = '" + tmpString + "'");
            fields++;
        }
        if ((tmpString = object.getName()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_NAME + " = '" + tmpString + "'");
            fields++;
        }
        if ((tmpString = object.getNationality()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_NATIONALITY + " = '" + tmpString + "'");
            fields++;
        }
        if ((tmpString = object.getMarital_status()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_MARITAL_STATUS + " = '" + tmpString + "'");
            fields++;
        }
        if ((tmpString = object.getBirthDate()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_BIRTHDATE + " = " + tmpString );
            fields++;
        }
        if ((tmpFloat = object.getHeight()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_HEIGHT + " = " + tmpFloat );
            fields++;
        }
        if ((tmpFloat = object.getWeight()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_WEIGHT + " = " + tmpFloat );
            fields++;
        }
        if ((tmpString = object.getAddress()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_ADDRESS + " = '" + tmpString + "'");
            fields++;
        }
        if ((tmpString = object.getGender()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_GENDER + " = '" + tmpString + "'");
            fields++;
        }
        if ((tmpString = object.getPhoto()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_PHOTO + " = '" + tmpString + "'");
            fields++;
        }
        if ((tmpString = object.getEmail()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_EMAIL + " = '" + tmpString + "'");
            fields++;
        }
        if ((tmpString = object.getPreferredFoot()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_PREFERRED_FOOT + " = '" + tmpString + "'");
            fields++;
        }
        if ((tmpInt = object.getNumber()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_NUMBER + " = " + tmpInt );
            fields++;
        }
        if(object.getTeam() != null) {
            if ((tmpLong = object.getTeam().getId()) >= 0) {
                statement.append(((fields != 0) ? " AND " : "") + COLUMN_TEAM_ID + " = " + tmpLong);
                fields++;
            }
        }

        if (fields > 0) {
            Cursor res = db.rawQuery(statement.toString(), null);
            return res.moveToFirst();
        }
        else
            return false;
    }

    @Override
    public List<Player> getByCriteria(Player object) throws GenericDAOException {

        if(object==null)
            return null;

        List<Player> resPlayer = new ArrayList<>();
        int fields = 0;
        String tmpString;
        int tmpInt;
        float tmpFloat;
        long tmpLong;

        StringBuilder statement = new StringBuilder("SELECT * FROM "+ TABLE_NAME +" where ");
        if ((tmpLong = object.getId()) > 0) {
            statement.append(COLUMN_ID + "=" + tmpLong);
            fields++;
        }
        if ((tmpString = object.getNickname()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_NICKNAME + " LIKE '%" + tmpString + "%'");
            fields++;
        }
        if ((tmpString = object.getName()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_NAME + " LIKE '%" + tmpString + "%'");
            fields++;
        }
        if ((tmpString = object.getNationality()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_NATIONALITY + " LIKE '%" + tmpString + "%'");
            fields++;
        }
        if ((tmpString = object.getMarital_status()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_MARITAL_STATUS + " LIKE '%" + tmpString + "%'");
            fields++;
        }
        if ((tmpString = object.getBirthDate()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_BIRTHDATE + " = " + tmpString );
            fields++;
        }
        if ((tmpInt = object.getHeight()) > 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_HEIGHT + " = " + tmpInt );
            fields++;
        }
        if ((tmpFloat = object.getWeight()) > 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_WEIGHT + " = " + tmpFloat );
            fields++;
        }
        if ((tmpString = object.getAddress()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_ADDRESS + " LIKE '%" + tmpString + "%'");
            fields++;
        }
        if ((tmpString = object.getGender()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_GENDER + " LIKE '%" + tmpString + "%'");
            fields++;
        }
        if ((tmpString = object.getPhoto()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_PHOTO + " LIKE '%" + tmpString + "%'");
            fields++;
        }
        if ((tmpString = object.getEmail()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_EMAIL + " LIKE '%" + tmpString + "%'");
            fields++;
        }
        if ((tmpString = object.getPreferredFoot()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_PREFERRED_FOOT + " LIKE '%" + tmpString + "%'");
            fields++;
        }
        if ((tmpInt = object.getNumber()) > 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_NUMBER + " = " + tmpInt );
            fields++;
        }
        if (object.getTeam() != null) {
            if ((tmpLong = object.getTeam().getId()) > 0) {
                statement.append(((fields != 0) ? " AND " : "") + COLUMN_TEAM_ID + " = " + tmpLong);
                fields++;
            }
        }
        /*if (object.getPosition() != null && (tmpLong = object.getPosition().getId()) > 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_BETTER_POSITION + " = " + tmpLong );

            fields++;
        }*/

        if (fields > 0) {
            long id;
            String nickname;
            String name;
            String nationality;
            String marital_status;
            String dob;
            int height;
            float weight;
            String address;
            String gender;
            String photo;
            String email;
            String prefered_foot;
            int number;
            long team;

            Cursor res = db.rawQuery( statement.toString(), null );
            if(res.moveToFirst())

                while(res.isAfterLast() == false) {
                    id = res.getInt(res.getColumnIndex(COLUMN_ID));
                    nickname = res.getString(res.getColumnIndex(COLUMN_NICKNAME));
                    name = res.getString(res.getColumnIndex(COLUMN_NAME));
                    nationality = res.getString(res.getColumnIndex(COLUMN_NATIONALITY));
                    marital_status = res.getString(res.getColumnIndex(COLUMN_MARITAL_STATUS));
                    dob=res.getString(res.getColumnIndex(COLUMN_BIRTHDATE));
                    height = res.getInt(res.getColumnIndex(COLUMN_HEIGHT));
                    weight = res.getFloat(res.getColumnIndex(COLUMN_WEIGHT));
                    address= res.getString(res.getColumnIndex(COLUMN_ADDRESS));
                    gender= res.getString(res.getColumnIndex(COLUMN_GENDER));
                    photo= res.getString(res.getColumnIndex(COLUMN_PHOTO));
                    email= res.getString(res.getColumnIndex(COLUMN_EMAIL));
                    prefered_foot = res.getString(res.getColumnIndex(COLUMN_PREFERRED_FOOT));
                    number= res.getInt(res.getColumnIndex(COLUMN_NUMBER));
                    team=res.getLong(res.getColumnIndex(COLUMN_TEAM_ID));
                    //position=res.getLong(res.getColumnIndex(COLUMN_BETTER_POSITION));

                    resPlayer.add(new Player(id,nickname,name,nationality,marital_status,dob,height,weight,address,gender,photo,email,prefered_foot,number,
                            team_dao.getById(team),null));
                    res.moveToNext();
                }
        }


        return resPlayer;
    }
}
