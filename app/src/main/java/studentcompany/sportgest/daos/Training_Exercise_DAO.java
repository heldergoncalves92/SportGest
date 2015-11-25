package studentcompany.sportgest.daos;
//TODO all

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import studentcompany.sportgest.daos.db.MyDB;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.TrainingExercise;

public class Training_Exercise_DAO extends GenericDAO<TrainingExercise> implements IGenericDAO<TrainingExercise> {
    //Database name
    private SQLiteDatabase db;

    //Dependencies DAOs
    private Training_DAO training_dao;
    private Exercise_DAO exercise_dao;

    //Table names
    public static final String TABLE_NAME         = "TRAINING_EXERCISE";

    //Table columns
    public static final String COLUMN_ID          = "ID";
    public static final String COLUMN_TRAINING_ID = "TRAINING_ID";
    public static final String COLUMN_EXERCISE_ID = "EXERCISE_ID";
    public static final String COLUMN_REPETITIONS = "REPETITIONS";

    //Create table
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_TRAINING_ID + " INTEGER NOT NULL, " +
            COLUMN_EXERCISE_ID + " INTEGER NOT NULL, " +
            COLUMN_REPETITIONS + " INTEGER, " +
            "FOREIGN KEY("+COLUMN_TRAINING_ID+") REFERENCES "+Training_DAO.TABLE_NAME+"("+Training_DAO.COLUMN_ID+"), " +
            "FOREIGN KEY("+COLUMN_EXERCISE_ID+") REFERENCES "+Exercise_DAO.TABLE_NAME+"("+Exercise_DAO.COLUMN_ID+"));";

    //Drop table
    public static  final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + "; ";

    public Training_Exercise_DAO(Context context) {
        this.db = MyDB.getInstance(context).db;
        this.training_dao = new Training_DAO(context);
        this.exercise_dao = new Exercise_DAO(context);
    }

    @Override
    public ArrayList<TrainingExercise> getAll() throws GenericDAOException {

        //aux variables;
        ArrayList<TrainingExercise> resTrainingExercise = new ArrayList<>();
        int id;
        int trainingId;
        int exerciseId;
        int repetitions;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME, null );
        res.moveToFirst();

        //Parse data
        while(res.isAfterLast() == false) {
            id = res.getInt(res.getColumnIndex(COLUMN_ID));
            trainingId = res.getInt(res.getColumnIndex(COLUMN_TRAINING_ID));
            exerciseId = res.getInt(res.getColumnIndex(COLUMN_EXERCISE_ID));
            repetitions = res.getInt(res.getColumnIndex(COLUMN_REPETITIONS));
            resTrainingExercise.add(new TrainingExercise(id,
                    training_dao.getById(trainingId),
                    exercise_dao.getById(exerciseId),
                    repetitions));
            res.moveToNext();
        }

        return resTrainingExercise;
    }

    @Override
    public TrainingExercise getById(int id) throws GenericDAOException {

        //aux variables;
        TrainingExercise resTrainingExercise;
        int trainingId;
        int exerciseId;
        int repetitions;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + "=" + id, null );
        res.moveToFirst();

        //Parse data
        id = res.getInt(res.getColumnIndex(COLUMN_ID));
        trainingId = res.getInt(res.getColumnIndex(COLUMN_TRAINING_ID));
        exerciseId = res.getInt(res.getColumnIndex(COLUMN_EXERCISE_ID));
        repetitions = res.getInt(res.getColumnIndex(COLUMN_REPETITIONS));
        resTrainingExercise = new TrainingExercise(id,
                training_dao.getById(trainingId),
                exercise_dao.getById(exerciseId),
                repetitions);

        return resTrainingExercise;
    }

    @Override
    public long insert(TrainingExercise object) throws GenericDAOException {

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TRAINING_ID, object.getTraining().getId());
        contentValues.put(COLUMN_EXERCISE_ID, object.getExercise().getId());
        contentValues.put(COLUMN_REPETITIONS, object.getRepetitions());

        return db.insert(TABLE_NAME, null, contentValues);
    }

    @Override
    public boolean delete(TrainingExercise object) throws GenericDAOException {
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
    public boolean update(TrainingExercise object) throws GenericDAOException {

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TRAINING_ID, object.getTraining().getId());
        contentValues.put(COLUMN_EXERCISE_ID, object.getExercise().getId());
        contentValues.put(COLUMN_REPETITIONS, object.getRepetitions());

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
    public boolean exists(TrainingExercise object) throws GenericDAOException {

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
        if ((tmpInt = object.getTraining().getId()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_TRAINING_ID + " = " + tmpInt );
            fields++;
        }
        if ((tmpInt = object.getExercise().getId()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_EXERCISE_ID + " = " + tmpInt );
            fields++;
        }
        if ((tmpInt = object.getRepetitions()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_REPETITIONS + " = " + tmpInt );
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
    public List<TrainingExercise> getByCriteria(TrainingExercise object) throws GenericDAOException {

        if(object==null)
            return null;

        List<TrainingExercise> resTrainingExercise = new ArrayList<>();
        int fields = 0;
        int tmpInt;

        StringBuilder statement = new StringBuilder("SELECT * FROM "+ TABLE_NAME +" where ");
        if ((tmpInt = object.getId()) >= 0) {
            statement.append(COLUMN_ID + "=" + tmpInt);
            fields++;
        }
        if ((tmpInt = object.getTraining().getId()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_TRAINING_ID + " = " + tmpInt );
            fields++;
        }
        if ((tmpInt = object.getExercise().getId()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_EXERCISE_ID + " = " + tmpInt );
            fields++;
        }
        if ((tmpInt = object.getRepetitions()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_REPETITIONS + " = " + tmpInt );
            fields++;
        }

        if (fields > 0) {

            int id;
            int trainingId;
            int exerciseId;
            int repetitions;

            Cursor res = db.rawQuery( statement.toString(), null );
            if(res.moveToFirst())

                while(res.isAfterLast() == false) {
                    id = res.getInt(res.getColumnIndex(COLUMN_ID));
                    trainingId = res.getInt(res.getColumnIndex(COLUMN_TRAINING_ID));
                    exerciseId = res.getInt(res.getColumnIndex(COLUMN_EXERCISE_ID));
                    repetitions = res.getInt(res.getColumnIndex(COLUMN_REPETITIONS));
                    resTrainingExercise.add(new TrainingExercise(id,
                            training_dao.getById(trainingId),
                            exercise_dao.getById(exerciseId),
                            repetitions));
                    res.moveToNext();
                }
        }


        return resTrainingExercise;
    }

}
