package studentcompany.sportgest.daos;
//TODO all

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Role;

public class Role_DAO extends GenericDAO<Role> implements IGenericDAO<Role>{
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
    public List<Role> getAll() throws GenericDAOException {
        return null;
    }

    @Override
    public Role getById(int id) throws GenericDAOException {
        return null;
    }

    @Override
    public boolean insert(Role object) throws GenericDAOException {
        return false;
    }

    @Override
    public boolean delete(Role object) throws GenericDAOException {
        return false;
    }

    @Override
    public boolean update(Role object) throws GenericDAOException {
        return false;
    }

    @Override
    public boolean exists(Role object) throws GenericDAOException {
        return false;
    }

    @Override
    public List<Role> getByCriteria(Role object) throws GenericDAOException {
        return null;
    }
}
