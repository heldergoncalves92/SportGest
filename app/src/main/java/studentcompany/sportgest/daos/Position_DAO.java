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
import studentcompany.sportgest.domains.Role;
import studentcompany.sportgest.domains.User;

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
        long id;
        String name;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM "+TABLE_NAME, null );
        res.moveToFirst();

        //Parse data
        while(res.isAfterLast() == false) {
            id = res.getLong(res.getColumnIndexOrThrow(COLUMN_ID));
            name = res.getString(res.getColumnIndexOrThrow(COLUMN_NAME));
            Position.add(new Position(id, name));
            res.moveToNext();
        }

        return Position;
    }

    @Override
    public Position getById(long id) throws GenericDAOException {
         Position resPosition;
        String name;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_ID+"="+id, null );
        res.moveToFirst();

        //Parse data
        if(res.getCount()==1)
        {
            name = res.getString(res.getColumnIndexOrThrow(COLUMN_NAME));
            res.close(); // Close the cursor
            return new Position(id,name);
        }
        else {
            res.close(); // Close the cursor
            return null;
        }
    }

    @Override
    public long insert(Position object) throws GenericDAOException {

        if(object==null)
            return -1;

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, object.getName());

        return db.insert(TABLE_NAME, null, contentValues);
    }

    @Override
    public boolean delete(Position object) throws GenericDAOException {

        if(object==null)
            return false;

        return deleteById(object.getId());
    }

    public boolean deleteById(long id){
        return db.delete(TABLE_NAME,
                COLUMN_ID + " = ? ",
                new String[]{Long.toString(id)}) > 0;
    }

    @Override
    public boolean update(Position object) throws GenericDAOException {

        if(object==null)
            return false;

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, object.getName());
        db.update(TABLE_NAME,
                contentValues,
                COLUMN_ID + " = ? ",
                new String[] { Long.toString(object.getId()) } );
        return true;
    }

    @Override
    public int numberOfRows() {
        return (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
    }

    @Override
    public boolean exists(Position object) throws GenericDAOException {

        if(object==null)
            return false;

        int fields = 0;
        String tmpString;
        long tmpLong;

        StringBuilder statement = new StringBuilder("SELECT * FROM "+ TABLE_NAME +" where ");
        if ((tmpLong = object.getId()) >= 0) {
            statement.append(COLUMN_ID + "=" + tmpLong);
            fields++;
        }
        if ((tmpString = object.getName()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_NAME + " = '" + tmpString + "'");
            fields++;
        }

        if (fields > 0) {
            Cursor res = db.rawQuery(statement.toString(), null);
            return res.moveToFirst();
        }
        else
            return false;
    }

    @Override
    public List<Position> getByCriteria(Position object) throws GenericDAOException {

        if(object==null)
            return null;

        List<Position> resPosition = new ArrayList<>();
        int fields = 0;
        String tmpString;
        long tmpLong;

        StringBuilder statement = new StringBuilder("SELECT * FROM "+ TABLE_NAME +" where ");
        if ((tmpLong = object.getId()) >= 0) {
            statement.append(COLUMN_ID + "=" + tmpLong);
            fields++;
        }
        if ((tmpString = object.getName()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_NAME + " LIKE '%" + tmpString + "%'");
            fields++;
        }

        if (fields > 0) {

            long id;
            String name;

            Cursor res = db.rawQuery( statement.toString(), null );
            if(res.moveToFirst())

                while(res.isAfterLast() == false) {
                    id = res.getLong(res.getColumnIndex(COLUMN_ID));
                    name = res.getString(res.getColumnIndex(COLUMN_NAME));
                    resPosition.add(new Position(id, name));
                    res.moveToNext();
                }
        }


        return resPosition;
    }
}
