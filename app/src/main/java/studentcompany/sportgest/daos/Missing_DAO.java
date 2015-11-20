package studentcompany.sportgest.daos;
//TODO all

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Missing;

public class Missing_DAO extends GenericDAO<Missing> implements IGenericDAO<Missing>{
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
    public List<Missing> getAll() throws GenericDAOException {
        return null;
    }

    @Override
    public Missing getById(int id) throws GenericDAOException {
        return null;
    }

    @Override
    public boolean insert(Missing object) throws GenericDAOException {
        return false;
    }

    @Override
    public boolean delete(Missing object) throws GenericDAOException {
        return false;
    }

    @Override
    public boolean update(Missing object) throws GenericDAOException {
        return false;
    }

    @Override
    public boolean exists(Missing object) throws GenericDAOException {
        return false;
    }

    @Override
    public List<Missing> getByCriteria(Missing object) throws GenericDAOException {
        return null;
    }
}
