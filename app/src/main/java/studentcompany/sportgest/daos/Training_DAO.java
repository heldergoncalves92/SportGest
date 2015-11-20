package studentcompany.sportgest.daos;
//TODO all

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Training;

public class Training_DAO extends GenericDAO<Training> implements IGenericDAO<Training>{
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
    public List<Training> getAll() throws GenericDAOException {
        return null;
    }

    @Override
    public Training getById(int id) throws GenericDAOException {
        return null;
    }

    @Override
    public boolean insert(Training object) throws GenericDAOException {
        return false;
    }

    @Override
    public boolean delete(Training object) throws GenericDAOException {
        return false;
    }

    @Override
    public boolean update(Training object) throws GenericDAOException {
        return false;
    }

    @Override
    public boolean exists(Training object) throws GenericDAOException {
        return false;
    }

    @Override
    public List<Training> getByCriteria(Training object) throws GenericDAOException {
        return null;
    }
}
