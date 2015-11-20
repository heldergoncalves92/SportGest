package studentcompany.sportgest.daos;
//TODO all

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Exercise;

public class Exercise_DAO extends GenericDAO<Exercise> implements IGenericDAO<Exercise>{
    //Database name
    private SQLiteDatabase db;

    //Table names
    public static final String TABLE_NAME         = "";

    //Table columns
    public static final String COLUMN_ID          = "";

    //Create table
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            "";

    //Drop table
    public static  final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + "; ";

    @Override
    public List<Exercise> getAll() throws GenericDAOException {
        return null;
    }

    @Override
    public Exercise getById(int id) throws GenericDAOException {
        return null;
    }

    @Override
    public boolean insert(Exercise object) throws GenericDAOException {
        return false;
    }

    @Override
    public boolean delete(Exercise object) throws GenericDAOException {
        return false;
    }

    @Override
    public boolean update(Exercise object) throws GenericDAOException {
        return false;
    }

    @Override
    public boolean exists(Exercise object) throws GenericDAOException {
        return false;
    }

    @Override
    public List<Exercise> getByCriteria(Exercise object) throws GenericDAOException {
        return null;
    }
}
