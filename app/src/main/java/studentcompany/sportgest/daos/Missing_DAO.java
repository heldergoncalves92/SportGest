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
import studentcompany.sportgest.domains.Missing;

public class Missing_DAO extends GenericDAO<Missing> implements IGenericDAO<Missing>{
    //Database name
    private SQLiteDatabase db;

    //Dependencies DAOs
    private Player_DAO player_dao;
    private Training_DAO training_dao;

    //Table names
    public static final String TABLE_NAME         = "MISSING";

    //Table columns
    public static final String COLUMN_ID          = "ID";
    public static final String COLUMN_PLAYER_ID   = "PLAYER_ID";
    public static final String COLUMN_TRAINING_ID = "TRAINING_ID";
    public static final String COLUMN_DESCRIPTION = "DESCRIPTION";

    //Create table
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_PLAYER_ID + " INTEGER NOT NULL, " +
            COLUMN_TRAINING_ID + " INTEGER NOT NULL, " +
            COLUMN_DESCRIPTION + " TEXT, " +
            "FOREIGN KEY("+COLUMN_PLAYER_ID+") REFERENCES "+ Player_DAO.TABLE_NAME+"("+Player_DAO.COLUMN_ID+"), " +
            "FOREIGN KEY("+COLUMN_TRAINING_ID+") REFERENCES "+Training_DAO.TABLE_NAME+"("+Training_DAO.COLUMN_ID+"));";

    //Drop table
    public static  final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + "; ";

    public Missing_DAO(Context context) {
        this.db = MyDB.getInstance(context).db;
        this.player_dao = new Player_DAO(context);
        this.training_dao = new Training_DAO(context);
    }

    @Override
    public ArrayList<Missing> getAll() throws GenericDAOException {

        //aux variables;
        ArrayList<Missing> resMissing = new ArrayList<>();
        int id;
        int playerId;
        int trainingId;
        String description;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME, null );
        res.moveToFirst();

        //Parse data
        while(res.isAfterLast() == false) {
            id = res.getInt(res.getColumnIndex(COLUMN_ID));
            playerId = res.getInt(res.getColumnIndex(COLUMN_PLAYER_ID));
            trainingId = res.getInt(res.getColumnIndex(COLUMN_TRAINING_ID));
            description = res.getString(res.getColumnIndex(COLUMN_DESCRIPTION));
            resMissing.add(new Missing(id,
                    player_dao.getById(playerId),
                    training_dao.getById(trainingId),
                    description));
            res.moveToNext();
        }

        return resMissing;
    }

    @Override
    public Missing getById(int id) throws GenericDAOException {

        //aux variables;
        Missing resMissing;
        int playerId;
        int trainingId;
        String description;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + "=" + id, null );
        res.moveToFirst();

        //Parse data
        id = res.getInt(res.getColumnIndex(COLUMN_ID));
        playerId = res.getInt(res.getColumnIndex(COLUMN_PLAYER_ID));
        trainingId = res.getInt(res.getColumnIndex(COLUMN_TRAINING_ID));
        description = res.getString(res.getColumnIndex(COLUMN_DESCRIPTION));
        resMissing = new Missing(id,
                player_dao.getById(playerId),
                training_dao.getById(trainingId),
                description);

        return resMissing;
    }

    @Override
    public long insert(Missing object) throws GenericDAOException {

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PLAYER_ID, object.getPlayer().getId());
        contentValues.put(COLUMN_TRAINING_ID, object.getTraining().getId());
        contentValues.put(COLUMN_DESCRIPTION, object.getDescription());

        return db.insert(TABLE_NAME, null, contentValues);
    }

    @Override
    public boolean delete(Missing object) throws GenericDAOException {
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
    public boolean update(Missing object) throws GenericDAOException {

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PLAYER_ID, object.getPlayer().getId());
        contentValues.put(COLUMN_TRAINING_ID, object.getTraining().getId());
        contentValues.put(COLUMN_DESCRIPTION, object.getDescription());

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
    public boolean exists(Missing object) throws GenericDAOException {

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
        if ((tmpInt = object.getPlayer().getId()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_PLAYER_ID + " = " + tmpInt );
            fields++;
        }
        if ((tmpInt = object.getTraining().getId()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_TRAINING_ID + " = " + tmpInt );
            fields++;
        }
        if ((tmpString = object.getDescription()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_DESCRIPTION + " = '" + tmpString + "'");
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
    public List<Missing> getByCriteria(Missing object) throws GenericDAOException {

        if(object==null)
            return null;

        List<Missing> resMissing = new ArrayList<>();
        int fields = 0;
        String tmpString;
        int tmpInt;

        StringBuilder statement = new StringBuilder("SELECT * FROM "+ TABLE_NAME +" where ");
        if ((tmpInt = object.getId()) >= 0) {
            statement.append(COLUMN_ID + "=" + tmpInt);
            fields++;
        }
        if ((tmpInt = object.getPlayer().getId()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_PLAYER_ID + " = " + tmpInt );
            fields++;
        }
        if ((tmpInt = object.getTraining().getId()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_TRAINING_ID + " = " + tmpInt );
            fields++;
        }
        if ((tmpString = object.getDescription()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_DESCRIPTION + " LIKE '%" + tmpString + "%'");
            fields++;
        }

        if (fields > 0) {

            int id;
            int playerId;
            int trainingId;
            String description;

            Cursor res = db.rawQuery( statement.toString(), null );
            if(res.moveToFirst())

                while(res.isAfterLast() == false) {
                    id = res.getInt(res.getColumnIndex(COLUMN_ID));
                    playerId = res.getInt(res.getColumnIndex(COLUMN_PLAYER_ID));
                    trainingId = res.getInt(res.getColumnIndex(COLUMN_TRAINING_ID));
                    description = res.getString(res.getColumnIndex(COLUMN_DESCRIPTION));
                    resMissing.add(new Missing(id,
                            player_dao.getById(playerId),
                            training_dao.getById(trainingId),
                            description));
                    res.moveToNext();
                }
        }


        return resMissing;
    }
}
