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
import studentcompany.sportgest.domains.Record;

public class Record_DAO extends GenericDAO<Record> implements IGenericDAO<Record>{
    //Database name
    private SQLiteDatabase db;

    //Dependencies DAOs
    private Training_DAO training_dao;
    private Exercise_DAO exercise_dao;
    private Attribute_DAO attribute_dao;
    private Player_DAO player_dao;
    private User_DAO user_dao;

    //Table names
    public static final String TABLE_NAME           = "RECORD";

    //Table columns
    public static final String COLUMN_ID            = "ID";
    public static final String COLUMN_DATE          = "DATE";
    public static final String COLUMN_VALUE         = "VALUE";
    public static final String COLUMN_STATE         = "STATE";
    public static final String COLUMN_TRAINING_ID   = "TRAINING_ID";
    public static final String COLUMN_EXERCISE_ID   = "EXERCISE_ID";
    public static final String COLUMN_ATTRIBUTE_ID  = "ATTRIBUTE_ID";
    public static final String COLUMN_PLAYER_ID     = "PLAYER_ID";
    public static final String COLUMN_USER_ID       = "USER_ID";

    //Create table
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID           + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_DATE         + " INTEGER NOT NULL, " +
            COLUMN_VALUE        + " REAL NOT NULL, " +
            COLUMN_STATE        + " INTEGER NOT NULL, " +
            COLUMN_TRAINING_ID  + " INTEGER NOT NULL, " +
            COLUMN_EXERCISE_ID  + " INTEGER NOT NULL, " +
            COLUMN_ATTRIBUTE_ID + " INTEGER NOT NULL, " +
            COLUMN_PLAYER_ID    + " INTEGER NOT NULL, " +
            COLUMN_USER_ID      + " INTEGER NOT NULL, " +
            "FOREIGN KEY("+COLUMN_TRAINING_ID +") REFERENCES "+Training_DAO.TABLE_NAME+"("+Training_DAO.COLUMN_ID+"), " +
            "FOREIGN KEY("+COLUMN_EXERCISE_ID +") REFERENCES "+Exercise_DAO.TABLE_NAME+"("+Exercise_DAO.COLUMN_ID+"), " +
            "FOREIGN KEY("+COLUMN_ATTRIBUTE_ID+") REFERENCES "+Attribute_DAO.TABLE_NAME+"("+Attribute_DAO.COLUMN_ID+"), " +
            "FOREIGN KEY("+COLUMN_PLAYER_ID   +") REFERENCES "+Player_DAO.TABLE_NAME+"("+Player_DAO.COLUMN_ID+"), " +
            "FOREIGN KEY("+COLUMN_USER_ID     +") REFERENCES "+User_DAO.TABLE_NAME+"("+User_DAO.COLUMN_ID+"));";

    //Drop table
    public static  final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + "; ";

    public Record_DAO(Context context) {
        this.db = MyDB.getInstance(context).db;
        this.training_dao = new Training_DAO(context);
        this.exercise_dao = new Exercise_DAO(context);
        this.attribute_dao = new Attribute_DAO(context);
        this.player_dao = new Player_DAO(context);
        this.user_dao = new User_DAO(context);
    }

    @Override
    public ArrayList<Record> getAll() throws GenericDAOException {

        //aux variables;
        ArrayList<Record> resRecord = new ArrayList<>();
        long id;
        long date;
        float value;
        int state;
        long trainingId;
        long exerciseId;
        long attributeId;
        long playerId;
        long userId;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME, null );
        res.moveToFirst();

        //Parse data
        while(res.isAfterLast() == false) {
            id = res.getLong(res.getColumnIndex(COLUMN_ID));
            date = res.getLong(res.getColumnIndex(COLUMN_DATE));
            value = res.getFloat(res.getColumnIndex(COLUMN_VALUE));
            state = res.getInt(res.getColumnIndex(COLUMN_VALUE));
            trainingId = res.getLong(res.getColumnIndex(COLUMN_TRAINING_ID));
            exerciseId = res.getLong(res.getColumnIndex(COLUMN_EXERCISE_ID));
            attributeId = res.getLong(res.getColumnIndex(COLUMN_ATTRIBUTE_ID));
            playerId = res.getLong(res.getColumnIndex(COLUMN_PLAYER_ID));
            userId = res.getLong(res.getColumnIndex(COLUMN_USER_ID));
            resRecord.add(new Record(id, date, value, state,
                    training_dao.getById(trainingId),
                    exercise_dao.getById(exerciseId),
                    attribute_dao.getById(attributeId),
                    player_dao.getById(playerId),
                    user_dao.getById(userId)));
            res.moveToNext();
        }
        res.close();

        return resRecord;
    }

    @Override
    public Record getById(long id) throws GenericDAOException {

        //aux variables;
        Record resRecord;
        long date;
        float value;
        int state;
        long trainingId;
        long exerciseId;
        long attributeId;
        long playerId;
        long userId;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + "=" + id, null );
        res.moveToFirst();

        //Parse data
        date = res.getLong(res.getColumnIndex(COLUMN_DATE));
        value = res.getFloat(res.getColumnIndex(COLUMN_VALUE));
        state = res.getInt(res.getColumnIndex(COLUMN_VALUE));
        trainingId = res.getLong(res.getColumnIndex(COLUMN_TRAINING_ID));
        exerciseId = res.getLong(res.getColumnIndex(COLUMN_EXERCISE_ID));
        attributeId = res.getLong(res.getColumnIndex(COLUMN_ATTRIBUTE_ID));
        playerId = res.getLong(res.getColumnIndex(COLUMN_PLAYER_ID));
        userId = res.getLong(res.getColumnIndex(COLUMN_USER_ID));
        resRecord = new Record(id, date, value, state,
                training_dao.getById(trainingId),
                exercise_dao.getById(exerciseId),
                attribute_dao.getById(attributeId),
                player_dao.getById(playerId),
                user_dao.getById(userId));

        res.close();
        return resRecord;
    }

    @Override
    public long insert(Record object) throws GenericDAOException {

        if(object==null)
            return -1;

        if(object.getTraining()==null || object.getExercise()==null || object.getAttribute()==null || object.getPlayer()==null)
            return -1;

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DATE        , object.getDate());
        contentValues.put(COLUMN_VALUE       , object.getValue());
        contentValues.put(COLUMN_STATE       , object.getState());
        contentValues.put(COLUMN_TRAINING_ID , object.getTraining().getId());
        contentValues.put(COLUMN_EXERCISE_ID , object.getExercise().getId());
        contentValues.put(COLUMN_ATTRIBUTE_ID, object.getAttribute().getId());
        contentValues.put(COLUMN_PLAYER_ID   , object.getPlayer().getId());

        return db.insert(TABLE_NAME, null, contentValues);
    }

    @Override
    public boolean delete(Record object) throws GenericDAOException {
        if(object==null)
            return false;

        return deleteById(object.getId());
    }

    public boolean deleteById(long id) {
        return db.delete(TABLE_NAME,
                COLUMN_ID + " = ? ",
                new String[]{Long.toString(id)}) > 0;
    }

    @Override
    public boolean update(Record object) throws GenericDAOException {

        if(object==null)
            return false;

        if(object.getTraining()==null || object.getExercise()==null || object.getAttribute()==null || object.getPlayer()==null)
            return false;

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DATE        , object.getDate());
        contentValues.put(COLUMN_VALUE       , object.getValue());
        contentValues.put(COLUMN_STATE       , object.getState());
        contentValues.put(COLUMN_TRAINING_ID , object.getTraining().getId());
        contentValues.put(COLUMN_EXERCISE_ID , object.getExercise().getId());
        contentValues.put(COLUMN_ATTRIBUTE_ID, object.getAttribute().getId());
        contentValues.put(COLUMN_PLAYER_ID   , object.getPlayer().getId());

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
    public boolean exists(Record object) throws GenericDAOException {

        if(object==null)
            return false;

        int fields = 0;
        int tmpInt;
        long tmpLong;
        float tmpFloat;

        StringBuilder statement = new StringBuilder("SELECT * FROM "+ TABLE_NAME +" where ");
        if ((tmpLong = object.getId()) >= 0) {
            statement.append(COLUMN_ID + "=" + tmpLong);
            fields++;
        }
        if ((tmpLong = object.getDate()) > 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_DATE + " = " + tmpLong );
            fields++;
        }
        if ((tmpFloat = object.getValue()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_VALUE + " = " + tmpFloat );
            fields++;
        }
        if ((tmpInt = object.getState()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_STATE + " = " + tmpInt );
            fields++;
        }
        if ((tmpLong = object.getTraining().getId()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_TRAINING_ID + " = " + tmpLong );
            fields++;
        }
        if ((tmpLong = object.getExercise().getId()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_EXERCISE_ID + " = " + tmpLong );
            fields++;
        }
        if ((tmpLong = object.getAttribute().getId()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_ATTRIBUTE_ID + " = " + tmpLong );
            fields++;
        }
        if ((tmpLong = object.getPlayer().getId()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_PLAYER_ID + " = " + tmpLong );
            fields++;
        }
        if ((tmpLong = object.getUser().getId()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_USER_ID + " = " + tmpLong );
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
    public List<Record> getByCriteria(Record object) throws GenericDAOException {

        if(object==null)
            return null;

        List<Record> resRecord = new ArrayList<>();
        int fields = 0;
        int tmpInt;
        long tmpLong;
        float tmpFloat;

        StringBuilder statement = new StringBuilder("SELECT * FROM "+ TABLE_NAME +" where ");
        if ((tmpLong = object.getId()) >= 0) {
            statement.append(COLUMN_ID + "=" + tmpLong);
            fields++;
        }
        if ((tmpLong = object.getDate()) > 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_DATE + " = " + tmpLong );
            fields++;
        }
        if ((tmpFloat = object.getValue()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_VALUE + " = " + tmpFloat );
            fields++;
        }
        if ((tmpInt = object.getState()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_STATE + " = " + tmpInt );
            fields++;
        }
        if (object.getTraining()!=null && (tmpLong = object.getTraining().getId()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_TRAINING_ID + " = " + tmpLong );
            fields++;
        }
        if (object.getExercise()!=null && (tmpLong = object.getExercise().getId()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_EXERCISE_ID + " = " + tmpLong );
            fields++;
        }
        if (object.getAttribute()!=null && (tmpLong = object.getAttribute().getId()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_ATTRIBUTE_ID + " = " + tmpLong );
            fields++;
        }
        if (object.getPlayer()!=null && (tmpLong = object.getPlayer().getId()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_PLAYER_ID + " = " + tmpLong );
            fields++;
        }
        if (object.getUser()!=null && (tmpLong = object.getUser().getId()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_USER_ID + " = " + tmpLong );
            fields++;
        }

        if (fields > 0) {

            long id;
            long date;
            float value;
            int state;
            long trainingId;
            long exerciseId;
            long attributeId;
            long playerId;
            long userId;

            Cursor res = db.rawQuery( statement.toString(), null );
            if(res.moveToFirst())

                while(res.isAfterLast() == false) {
                    id = res.getLong(res.getColumnIndex(COLUMN_ID));
                    date = res.getLong(res.getColumnIndex(COLUMN_DATE));
                    value = res.getFloat(res.getColumnIndex(COLUMN_VALUE));
                    state = res.getInt(res.getColumnIndex(COLUMN_VALUE));
                    trainingId = res.getLong(res.getColumnIndex(COLUMN_TRAINING_ID));
                    exerciseId = res.getLong(res.getColumnIndex(COLUMN_EXERCISE_ID));
                    attributeId = res.getLong(res.getColumnIndex(COLUMN_ATTRIBUTE_ID));
                    playerId = res.getLong(res.getColumnIndex(COLUMN_PLAYER_ID));
                    userId = res.getLong(res.getColumnIndex(COLUMN_USER_ID));
                    resRecord.add(new Record(id, date, value, state,
                            training_dao.getById(trainingId),
                            exercise_dao.getById(exerciseId),
                            attribute_dao.getById(attributeId),
                            player_dao.getById(playerId),
                            user_dao.getById(userId)));
                    res.moveToNext();
                }
            res.close();
        }


        return resRecord;
    }
}
