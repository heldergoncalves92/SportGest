package studentcompany.sportgest.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import studentcompany.sportgest.daos.db.MyDB;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Position;

public class Position_DAO extends GenericDAO<Position> implements IGenericDAO<Position>{
    //Database name
    private SQLiteDatabase db;

    //Table names
    public static final String TABLE_NAME         = "POSITION";

    //Table columns
    public static final String COLUMN_ID          = "ID";
    public static final String COLUMN_NAME    = "NAME";

    //Create table
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            COLUMN_NAME + " TEXT NOT NULL); ";

    //Drop table
    public static  final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + "; ";

    public Position_DAO(Context context) {
        this.db = MyDB.getInstance(context).db;
    }

    @Override
    public List<Position> getAll() throws GenericDAOException {
        ArrayList<Position> Position = new ArrayList<>();
        int id;
        String name;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM "+TABLE_NAME, null );
        res.moveToFirst();

        //Parse data
        while(res.isAfterLast() == false) {
            id = res.getInt(res.getColumnIndexOrThrow(COLUMN_ID));
            name = res.getString(res.getColumnIndexOrThrow(COLUMN_NAME));
            Position.add(new Position(id, name));
            res.moveToNext();
        }

        return Position;
    }

    @Override
    public Position getById(int id) throws GenericDAOException {
         Position resPosition;
        String name;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM "+TABLE_NAME, null );
        res.moveToFirst();
        if(res.getCount()==1){
        //Parse data
            id = res.getInt(res.getColumnIndexOrThrow(COLUMN_ID));
            name = res.getString(res.getColumnIndexOrThrow(COLUMN_NAME));
            resPosition= new Position(id, name);

        return resPosition;}
        else
            return null;
    }

    @Override
    public long insert(Position object) throws GenericDAOException {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, object.getName());

        return db.insert(TABLE_NAME, null, contentValues);
    }

    @Override
    public boolean delete(Position object) throws GenericDAOException {
        int deletedCount = db.delete(TABLE_NAME,
                COLUMN_ID + " = ? ",
                new String[] { Integer.toString(object.getId()) });
        return true;
    }

    @Override
    public boolean deleteById(int id) {
        int deletedCount = db.delete(TABLE_NAME,
                COLUMN_ID + " = ? ",
                new String[] { Integer.toString(id) });
        return true;
    }

    @Override
    public boolean update(Position object) throws GenericDAOException {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, object.getName());
        db.update(TABLE_NAME,
                contentValues,
                COLUMN_ID + " = ? ",
                new String[] { Integer.toString(object.getId()) } );
        return true;
    }

    @Override
    public int numberOfRows() {
        return (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
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
