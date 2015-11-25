package studentcompany.sportgest.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
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
        int exerciseId;
        int attributeId;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME, null );
        res.moveToFirst();

        //Parse data
        while(res.isAfterLast() == false) {
            exerciseId = res.getInt(res.getColumnIndex(COLUMN_EXERCISE_ID));
            attributeId = res.getInt(res.getColumnIndex(COLUMN_ATTRIBUTE_ID));
            resList.add(
                    new Pair<>(
                            attribute_dao.getById(attributeId),
                            exercise_dao.getById(exerciseId)));
            res.moveToNext();
        }

        return resList;
    }

    @Override
    public List<Pair<Attribute, Exercise>> getByFirstId(int id) throws GenericDAOException {
        //aux variables;
        ArrayList<Pair<Attribute, Exercise>> resList = new ArrayList<>();
        Attribute attribute;
        int exerciseId;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ATTRIBUTE_ID + "=" + id, null );
        res.moveToFirst();

        //Parse data
        attribute = attribute_dao.getById(id);
        while(res.isAfterLast() == false) {
            exerciseId = res.getInt(res.getColumnIndex(COLUMN_EXERCISE_ID));
            resList.add(
                    new Pair<>(
                            attribute,
                            exercise_dao.getById(exerciseId)));
            res.moveToNext();
        }

        return resList;
    }

    @Override
    public List<Pair<Attribute, Exercise>> getBySecondId(int id) throws GenericDAOException {
        //aux variables;
        ArrayList<Pair<Attribute, Exercise>> resList = new ArrayList<>();
        Exercise exercise;
        int attributeId;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_EXERCISE_ID + "=" + id, null );
        res.moveToFirst();

        //Parse data
        exercise = exercise_dao.getById(id);
        while(res.isAfterLast() == false) {
            attributeId = res.getInt(res.getColumnIndex(COLUMN_ATTRIBUTE_ID));
            resList.add(
                    new Pair<>(
                            attribute_dao.getById(attributeId),
                            exercise));
            res.moveToNext();
        }

        return resList;
    }

    @Override
    public long insert(Pair<Attribute, Exercise> object) throws GenericDAOException {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ATTRIBUTE_ID, object.getFirst().getId());
        contentValues.put(COLUMN_EXERCISE_ID, object.getSecond().getId());

        return db.insert(TABLE_NAME, null, contentValues);
    }

    @Override
    public boolean delete(Pair<Attribute, Exercise> object) throws GenericDAOException {
        int deletedCount = db.delete(TABLE_NAME,
                COLUMN_ATTRIBUTE_ID + " = ? , " + COLUMN_EXERCISE_ID + " = ? ",
                new String[] { Integer.toString(object.getFirst().getId()), Integer.toString(object.getSecond().getId()) });
        return true;
    }

    @Override
    public boolean exists(Pair<Attribute, Exercise> object) throws GenericDAOException {
        if(object==null)
            return false;

        int fields = 0;
        int tmpInt;

        StringBuilder statement = new StringBuilder("SELECT * FROM "+ TABLE_NAME +" where ");
        if ((tmpInt = object.getFirst().getId()) >= 0) {
            statement.append(COLUMN_ATTRIBUTE_ID + "=" + tmpInt);
            fields++;
        }
        if ((tmpInt = object.getSecond().getId()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_EXERCISE_ID + " = " + tmpInt);
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
