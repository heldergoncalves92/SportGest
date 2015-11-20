package studentcompany.sportgest.daos;
//TODO all

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import studentcompany.sportgest.daos.db.MyDB;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Team;

public class Team_DAO extends GenericDAO<Team> implements IGenericDAO<Team>{
    //Database name
    private SQLiteDatabase db;

    //Table names
    public static final String TABLE_NAME         = "TEAM";

    //Table columns
    public static final String COLUMN_ID          = "ID";

    //Create table
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            "";

    //Drop table
    public static  final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + "; ";

    public Team_DAO(Context context) {
        this.db = MyDB.getInstance(context).db;
    }


    @Override
    public List<Team> getAll() throws GenericDAOException {
        return null;
    }

    @Override
    public Team getById(int id) throws GenericDAOException {
        return null;
    }

    @Override
    public boolean insert(Team object) throws GenericDAOException {
        return false;
    }

    @Override
    public boolean delete(Team object) throws GenericDAOException {
        return false;
    }

    @Override
    public boolean update(Team object) throws GenericDAOException {
        return false;
    }

    @Override
    public boolean exists(Team object) throws GenericDAOException {
        return false;
    }

    @Override
    public List<Team> getByCriteria(Team object) throws GenericDAOException {
        return null;
    }
}
