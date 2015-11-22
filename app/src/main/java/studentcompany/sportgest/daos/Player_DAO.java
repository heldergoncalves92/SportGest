package studentcompany.sportgest.daos;
//TODO all

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import studentcompany.sportgest.daos.db.MyDB;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Player;

public class Player_DAO extends GenericDAO<Player> implements IGenericDAO<Player>{
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

    public Player_DAO(Context context) {
        this.db = MyDB.getInstance(context).db;
    }

    @Override
    public List<Player> getAll() throws GenericDAOException {
        return null;
    }

    @Override
    public Player getById(int id) throws GenericDAOException {
        return null;
    }

    @Override
    public long insert(Player object) throws GenericDAOException {
        return -1;
    }

    @Override
    public boolean delete(Player object) throws GenericDAOException {
        return false;
    }

    @Override
    public boolean update(Player object) throws GenericDAOException {
        return false;
    }

    @Override
    public boolean exists(Player object) throws GenericDAOException {
        return false;
    }

    @Override
    public List<Player> getByCriteria(Player object) throws GenericDAOException {
        return null;
    }
}
