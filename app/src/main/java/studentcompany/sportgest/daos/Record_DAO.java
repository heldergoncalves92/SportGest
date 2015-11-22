package studentcompany.sportgest.daos;
//TODO all

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Record;

public class Record_DAO extends GenericDAO<Record> implements IGenericDAO<Record>{
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
    public List<Record> getAll() throws GenericDAOException {
        return null;
    }

    @Override
    public Record getById(int id) throws GenericDAOException {
        return null;
    }

    @Override
    public long insert(Record object) throws GenericDAOException {
        return -1;
    }

    @Override
    public boolean delete(Record object) throws GenericDAOException {
        return false;
    }

    @Override
    public boolean update(Record object) throws GenericDAOException {
        return false;
    }

    @Override
    public boolean exists(Record object) throws GenericDAOException {
        return false;
    }

    @Override
    public List<Record> getByCriteria(Record object) throws GenericDAOException {
        return null;
    }
}
