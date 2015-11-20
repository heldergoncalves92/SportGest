package studentcompany.sportgest.daos;
//TODO all

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Permission;

public class Permision_DAO extends GenericDAO<Permission> implements IGenericDAO<Permission>{
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
    public List<Permission> getAll() throws GenericDAOException {
        return null;
    }

    @Override
    public Permission getById(int id) throws GenericDAOException {
        return null;
    }

    @Override
    public boolean insert(Permission object) throws GenericDAOException {
        return false;
    }

    @Override
    public boolean delete(Permission object) throws GenericDAOException {
        return false;
    }

    @Override
    public boolean update(Permission object) throws GenericDAOException {
        return false;
    }

    @Override
    public boolean exists(Permission object) throws GenericDAOException {
        return false;
    }

    @Override
    public List<Permission> getByCriteria(Permission object) throws GenericDAOException {
        return null;
    }
}
