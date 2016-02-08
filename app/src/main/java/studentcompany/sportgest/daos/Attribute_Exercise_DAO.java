package studentcompany.sportgest.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import studentcompany.sportgest.daos.db.MyDB;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Attribute;
import studentcompany.sportgest.domains.Exercise;

public class Attribute_Exercise_DAO extends GenericPairDAO<Attribute, Exercise> implements IGenericPairDAO<Attribute, Exercise> {
    //Database name
    private SQLiteDatabase db;

    //Dependencies DAOs
    private Attribute_DAO attribute_dao;
    private Exercise_DAO exercise_dao;

    //Table names
    public static final String TABLE_NAME = "ATTRIBUTE_EXERCISE";

    //Table columns
    public static final String COLUMN_EXERCISE_ID = "EXERCISE_ID";
    public static final String COLUMN_ATTRIBUTE_ID = "ATTRIBUTE_ID";

    //Create table
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_EXERCISE_ID + " INTEGER NOT NULL, " +
            COLUMN_ATTRIBUTE_ID + " INTEGER NOT NULL, " +
            "PRIMARY KEY (" + COLUMN_EXERCISE_ID + ", " + COLUMN_ATTRIBUTE_ID + "), " +
            "FOREIGN KEY(" + COLUMN_EXERCISE_ID + ") REFERENCES " + Exercise_DAO.TABLE_NAME + "(" + Exercise_DAO.COLUMN_ID + "), " +
            "FOREIGN KEY(" + COLUMN_ATTRIBUTE_ID + ") REFERENCES " + Attribute_DAO.TABLE_NAME + "(" + Attribute_DAO.COLUMN_ID + "));";

    //Drop table
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + "; ";

    public Attribute_Exercise_DAO(Context context) {
        this.db = MyDB.getInstance(context).db;
        this.exercise_dao = new Exercise_DAO(context);
        this.attribute_dao = new Attribute_DAO(context);
    }


    @Override
    public List<Pair<Attribute, Exercise>> getAll() throws GenericDAOException {
        //aux variables;
        ArrayList<Pair<Attribute, Exercise>> resList = new ArrayList<>();
        long exerciseId;
        long attributeId;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME, null );
        res.moveToFirst();

        //Parse data
        while(res.isAfterLast() == false) {
            exerciseId = res.getLong(res.getColumnIndex(COLUMN_EXERCISE_ID));
            attributeId = res.getLong(res.getColumnIndex(COLUMN_ATTRIBUTE_ID));
            resList.add(
                    new Pair<>(
                            attribute_dao.getById(attributeId),
                            exercise_dao.getById(exerciseId)));
            res.moveToNext();
        }

        res.close();

        return resList;
    }

    @Override
    public List<Exercise> getByFirstId(long id) throws GenericDAOException {
        //aux variables;
        ArrayList<Exercise> resList = new ArrayList<>();
        long exerciseId;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ATTRIBUTE_ID + "=" + id, null );
        res.moveToFirst();

        //Parse data
        while(res.isAfterLast() == false) {
            exerciseId = res.getLong(res.getColumnIndex(COLUMN_EXERCISE_ID));
            resList.add(exercise_dao.getById(exerciseId));
            res.moveToNext();
        }
        res.close();

        return resList;
    }

    @Override
    public List<Attribute> getBySecondId(long id) throws GenericDAOException {
        //aux variables;
        ArrayList<Attribute> resList = new ArrayList<>();
        long attributeId;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_EXERCISE_ID + "=" + id, null );
        res.moveToFirst();

        //Parse data
        while(!res.isAfterLast()) {
            attributeId = res.getLong(res.getColumnIndex(COLUMN_ATTRIBUTE_ID));
            resList.add(attribute_dao.getById(attributeId));
            res.moveToNext();
        }

        res.close();

        return resList;
    }

    @Override
    public long insert(Pair<Attribute, Exercise> object) throws GenericDAOException {

        if(object==null)
            return -1;

        if(object.getFirst() == null || object.getSecond() == null)
            return -1;

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ATTRIBUTE_ID, object.getFirst().getId());
        contentValues.put(COLUMN_EXERCISE_ID, object.getSecond().getId());

        return db.insert(TABLE_NAME, null, contentValues);
    }

    @Override
    public boolean delete(Pair<Attribute, Exercise> object) throws GenericDAOException {

        if(object==null)
            return false;

        if(object.getFirst() == null || object.getSecond() == null)
            return false;

        int deletedCount = db.delete(TABLE_NAME,
                COLUMN_ATTRIBUTE_ID + " = ? AND " + COLUMN_EXERCISE_ID + " = ? ",
                new String[] { Long.toString(object.getFirst().getId()), Long.toString(object.getSecond().getId()) });
        return true;
    }

    @Override
    public boolean exists(Pair<Attribute, Exercise> object) throws GenericDAOException {

        if(object==null)
            return false;

        if(object.getFirst() == null || object.getSecond() == null)
            return false;

        int fields = 0;
        long tmpLong;

        StringBuilder statement = new StringBuilder("SELECT * FROM "+ TABLE_NAME +" WHERE ");
        if ((tmpLong = object.getFirst().getId()) >= 0) {
            statement.append(COLUMN_ATTRIBUTE_ID + "=" + tmpLong);
            fields++;
        }
        if ((tmpLong = object.getSecond().getId()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_EXERCISE_ID + " = " + tmpLong);
            fields++;
        }

        if (fields > 0) {
            Cursor res = db.rawQuery(statement.toString(), null);
            return res.moveToFirst();
        }
        else
            return false;
    }
}
