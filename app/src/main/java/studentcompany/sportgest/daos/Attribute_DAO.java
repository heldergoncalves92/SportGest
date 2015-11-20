package studentcompany.sportgest.daos;
//TODO all

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Attribute;

public class Attribute_DAO extends GenericDAO<Attribute> implements IGenericDAO<Attribute>{
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
    public List<Attribute> getAll() throws GenericDAOException {
        return null;
    }

    @Override
    public Attribute getById(int id) throws GenericDAOException {
        return null;
    }

    @Override
    public boolean insert(Attribute object) throws GenericDAOException {
        return false;
    }

    @Override
    public boolean delete(Attribute object) throws GenericDAOException {
        return false;
    }

    @Override
    public boolean update(Attribute object) throws GenericDAOException {
        return false;
    }

    @Override
    public boolean exists(Attribute object) throws GenericDAOException {
        return false;
    }

    @Override
    public List<Attribute> getByCriteria(Attribute object) throws GenericDAOException {
        return null;
    }
}
