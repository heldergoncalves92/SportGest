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
import studentcompany.sportgest.domains.PlayerPosition;
import studentcompany.sportgest.domains.Position;
import android.database.sqlite.SQLiteDatabase;

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
        int id;
        int playerId;
        int positionId;
        int value;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM "+TABLE_NAME, null );
        res.moveToFirst();

        //Parse data
        while(res.isAfterLast() == false) {
            id = res.getInt(res.getColumnIndexOrThrow(COLUMN_ID));
            playerId = res.getInt(res.getColumnIndexOrThrow(COLUMN_PLAYER_ID));
            positionId = res.getInt(res.getColumnIndexOrThrow(COLUMN_POSITION_ID));
            value = res.getInt(res.getColumnIndexOrThrow(COLUMN_VALUE));
           
            resPlayerPosition.add(new PlayerPosition(id,
                    player_dao.getById(playerId),
                    position_dao.getById(positionId),
                    value));
        }

        return resPlayerPosition;
    }

    @Override
    public PlayerPosition getById(int id) throws GenericDAOException {
        PlayerPosition resPlayerPosition;
        int playerId;
        int positionId;
        int value;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM "+TABLE_NAME, null );
        res.moveToFirst();


            playerId = res.getInt(res.getColumnIndexOrThrow(COLUMN_PLAYER_ID));
            positionId = res.getInt(res.getColumnIndexOrThrow(COLUMN_POSITION_ID));
            value = res.getInt(res.getColumnIndexOrThrow(COLUMN_VALUE));

            resPlayerPosition = new PlayerPosition(id,
                    player_dao.getById(playerId),
                    position_dao.getById(positionId),
                    value);

        return resPlayerPosition;
    }

    @Override
    public long insert(PlayerPosition object) throws GenericDAOException {

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PLAYER_ID, object.getPlayer().getId());
        contentValues.put(COLUMN_POSITION_ID, object.getPosition().getId());
        contentValues.put(COLUMN_VALUE, object.getValue());
        return db.insert(TABLE_NAME, null, contentValues);
    }

    @Override
    public boolean delete(PlayerPosition object) throws GenericDAOException {

        int deletedCount = db.delete(TABLE_NAME,
                COLUMN_ID + " = ? ",
                new String[] { Integer.toString(object.getId()) });
        return true;
    }

    public boolean deleteById(int id){
        return db.delete(TABLE_NAME,
                COLUMN_ID + " = ? ",
                new String[] { Integer.toString(id) }) > 0 ? true : false;
    }

    @Override
    public boolean update(PlayerPosition object) throws GenericDAOException {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PLAYER_ID, object.getPlayer().getId());
        contentValues.put(COLUMN_POSITION_ID, object.getPosition().getId());
        contentValues.put(COLUMN_VALUE, object.getValue());
        return db.update(TABLE_NAME,
                contentValues,
                COLUMN_ID + " = ? ",
                new String[]{Integer.toString(object.getId())}) >0 ? true : false ;

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

        StringBuilder statement = new StringBuilder("SELECT * FROM "+ TABLE_NAME +" where ");
        if ((tmpInt = object.getId()) >= 0) {
            statement.append(COLUMN_ID + "=" + tmpInt);
            fields++;
        }
        if ((tmpInt = object.getPlayer().getId()) <= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_PLAYER_ID + " = " + tmpInt + "");
            fields++;
        }
        if ((tmpInt = object.getPosition().getId()) <= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_POSITION_ID + " = " + tmpInt + "");
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

        StringBuilder statement = new StringBuilder("SELECT * FROM "+ TABLE_NAME +" where ");
        if ((tmpInt = object.getId()) >= 0) {
            statement.append(COLUMN_ID + "=" + tmpInt);
            fields++;
        }
        if ((tmpInt = object.getPlayer().getId()) <= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_PLAYER_ID + " = " + tmpInt + "");
            fields++;
        }
        if ((tmpInt = object.getPosition().getId()) <= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_POSITION_ID + " = " + tmpInt + "");
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
                    int id = res.getInt(res.getColumnIndexOrThrow(COLUMN_ID));
                    int  playerId = res.getInt(res.getColumnIndexOrThrow(COLUMN_PLAYER_ID));
                    int positionId = res.getInt(res.getColumnIndexOrThrow(COLUMN_POSITION_ID));
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