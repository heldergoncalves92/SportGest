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
import studentcompany.sportgest.domains.PlayerPosition;

public class Player_Position_DAO extends GenericDAO<PlayerPosition> implements IGenericDAO<PlayerPosition> {
    //Database name
    private SQLiteDatabase db;

    //Dependencies DAOs
    private Player_DAO          player_dao;
    private Position_DAO        position_dao;

    //Table names
    public static final String TABLE_NAME         = "PLAYER_POSITION";

    //Table columns
    public final static String COLUMN_ID = "ID"; // é a chave primaria
    public final static String COLUMN_VALUE = "VALUE";
    public final static String COLUMN_PLAYER_ID = "PLAYER_ID"; //Forgen key
    public final static String COLUMN_POSITION_ID = "TEAM_ID";//gforgen key


    //Create table
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID      + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_VALUE    + " TEXT NOT NULL, " +
            COLUMN_PLAYER_ID    + " TEXT NOT NULL, " +
            COLUMN_POSITION_ID + " INTEGER NOT NULL);";

    //Drop table
    public static  final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + "; ";

    public Player_Position_DAO(Context context) {
        this.db = MyDB.getInstance(context).db;
        this.player_dao= new Player_DAO(context);
        this.position_dao= new Position_DAO(context);

    }

    @Override
    public List<PlayerPosition> getAll() throws GenericDAOException {
        ArrayList<PlayerPosition> resPlayerPosition = new ArrayList<>();
        long id;
        long playerId;
        long positionId;
        int value;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM "+TABLE_NAME, null );
        res.moveToFirst();

        //Parse data
        while(res.isAfterLast()) {
            id = res.getLong(res.getColumnIndexOrThrow(COLUMN_ID));
            playerId = res.getLong(res.getColumnIndexOrThrow(COLUMN_PLAYER_ID));
            positionId = res.getLong(res.getColumnIndexOrThrow(COLUMN_POSITION_ID));
            value = res.getInt(res.getColumnIndexOrThrow(COLUMN_VALUE));

            if(playerId <0 || positionId < 0){
                resPlayerPosition.add(new PlayerPosition(id,
                        null,
                        null,
                        value));
            }
            else {
                resPlayerPosition.add(new PlayerPosition(id,
                        player_dao.getById(playerId),
                        position_dao.getById(positionId),
                        value));
            }

        }
        res.close();
        return resPlayerPosition;
    }

    @Override
    public PlayerPosition getById(long id) throws GenericDAOException {
        PlayerPosition resPlayerPosition;
        long playerId;
        long positionId;
        int value;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM "+TABLE_NAME, null );
        res.moveToFirst();

            if(res.getCount()==1) {
                playerId = res.getLong(res.getColumnIndexOrThrow(COLUMN_PLAYER_ID));
                positionId = res.getLong(res.getColumnIndexOrThrow(COLUMN_POSITION_ID));
                value = res.getInt(res.getColumnIndexOrThrow(COLUMN_VALUE));
                if(playerId <0 || positionId < 0) {
                    resPlayerPosition = new PlayerPosition(id,
                            null,
                            null,
                            value);
                    return resPlayerPosition;
                }
                else{
                    resPlayerPosition = new PlayerPosition(id,
                            player_dao.getById(playerId),
                            position_dao.getById(positionId),
                            value);
                    return resPlayerPosition;
                }

            }
            else{
                res.close();
                return null;
            }
    }

    @Override
    public long insert(PlayerPosition object) throws GenericDAOException {

        if(object==null)
            return -1;

        ContentValues contentValues = new ContentValues();
        if(object.getPlayer() == null){
            contentValues.put(COLUMN_PLAYER_ID, -1);
        } else {
            contentValues.put(COLUMN_PLAYER_ID,  object.getPlayer().getId());
        }
        if(object.getPosition() == null){
            contentValues.put(COLUMN_POSITION_ID, -1);
        } else {
            contentValues.put(COLUMN_POSITION_ID,  object.getPosition().getId());
        }
        contentValues.put(COLUMN_VALUE, object.getValue());
        return db.insert(TABLE_NAME, null, contentValues);
    }

    @Override
    public boolean delete(PlayerPosition object) throws GenericDAOException {

        if(object==null)
            return false;

        return deleteById(object.getId());
    }

    public boolean deleteById(long id){
        return db.delete(TABLE_NAME,
                COLUMN_ID + " = ? ",
                new String[] { Long.toString(id) }) > 0;
    }

    @Override
    public boolean update(PlayerPosition object) throws GenericDAOException {
        if(object==null)
            return false;

        ContentValues contentValues = new ContentValues();
        if(object.getPlayer() == null){
            contentValues.put(COLUMN_PLAYER_ID, -1);
        } else {
            contentValues.put(COLUMN_PLAYER_ID,  object.getPlayer().getId());
        }
        if(object.getPosition() == null){
            contentValues.put(COLUMN_POSITION_ID, -1);
        } else {
            contentValues.put(COLUMN_POSITION_ID,  object.getPosition().getId());
        }
        contentValues.put(COLUMN_VALUE, object.getValue());
        return db.update(TABLE_NAME,
                contentValues,
                COLUMN_ID + " = ? ",
                new String[]{Long.toString(object.getId())}) >0 ? true : false ;

    }

    public int numberOfRows(){
        return (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
    }

    @Override
    public boolean exists(PlayerPosition object) throws GenericDAOException {

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
        if ((tmpLong = object.getPlayer().getId()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_PLAYER_ID + " = " + tmpLong + "");
            fields++;
        }
        if ((tmpLong = object.getPosition().getId()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_POSITION_ID + " = " + tmpLong + "");
            fields++;
        }
        if ((tmpInt = object.getValue()) <= 0 ) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_VALUE + " = " + tmpInt + "");
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
    public List<PlayerPosition> getByCriteria(PlayerPosition object) throws GenericDAOException {

        if(object==null)
            return null;

        List<PlayerPosition> playerPosition = new ArrayList<>();
        int fields = 0;
        int tmpInt;
        long tmpLong;

        StringBuilder statement = new StringBuilder("SELECT * FROM "+ TABLE_NAME +" where ");
        if ((tmpLong = object.getId()) >= 0) {
            statement.append(COLUMN_ID + "=" + tmpLong);
            fields++;
        }
        if ((tmpLong = object.getPlayer().getId()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_PLAYER_ID + " = " + tmpLong + "");
            fields++;
        }
        if ((tmpLong = object.getPosition().getId()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_POSITION_ID + " = " + tmpLong + "");
            fields++;
        }
        if ((tmpInt = object.getValue()) <= 0 ) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_VALUE + " = " + tmpInt + "");
            fields++;
        }

        if (fields > 0) {


            Cursor res = db.rawQuery( statement.toString(), null );
            if(res.moveToFirst())
                while(res.isAfterLast() == false) {
                    long id = res.getLong(res.getColumnIndexOrThrow(COLUMN_ID));
                    long playerId = res.getLong(res.getColumnIndexOrThrow(COLUMN_PLAYER_ID));
                    long positionId = res.getLong(res.getColumnIndexOrThrow(COLUMN_POSITION_ID));
                    int value = res.getInt(res.getColumnIndexOrThrow(COLUMN_VALUE));

                    playerPosition.add(new PlayerPosition(id,
                            player_dao.getById(playerId),
                            position_dao.getById(positionId),
                            value));
                }
        }


        return playerPosition;
    }

}