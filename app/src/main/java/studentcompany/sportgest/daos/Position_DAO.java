package studentcompany.sportgest.daos;
//TODO all

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Position;

public class Position_DAO extends GenericDAO<Position> implements IGenericDAO<Position>{
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
    public List<Position> getAll() throws GenericDAOException {
        return null;
    }

    @Override
    public Position getById(int id) throws GenericDAOException {
        return null;
    }

    @Override
    public boolean insert(Position object) throws GenericDAOException {
        return false;
    }

    @Override
    public boolean delete(Position object) throws GenericDAOException {
        return false;
    }

    @Override
    public boolean update(Position object) throws GenericDAOException {
        return false;
    }

    @Override
    public boolean exists(Position object) throws GenericDAOException {
        return false;
    }

    @Override
    public List<Position> getByCriteria(Position object) throws GenericDAOException {
        return null;
    }
}
