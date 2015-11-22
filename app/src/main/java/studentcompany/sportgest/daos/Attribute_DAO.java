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
import studentcompany.sportgest.domains.Attribute;

public class Attribute_DAO extends GenericDAO<Attribute> implements IGenericDAO<Attribute>{
    //Database name
    private SQLiteDatabase db;

    //Table names
    public static final String TABLE_NAME         = "ATTRIBUTE";

    //Table columns
    public static final String COLUMN_ID          = "ID";
    public static final String COLUMN_TYPE        = "TYPE";
    public static final String COLUMN_NAME        = "NAME";
    public static final String COLUMN_DELETED     = "DELETED";

    //Create table
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID      + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_TYPE    + " TEXT NOT NULL, " +
            COLUMN_NAME    + " TEXT NOT NULL, " +
            COLUMN_DELETED + " INTEGER NOT NULL);";

    //Drop table
    public static  final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + "; ";

    public Attribute_DAO(Context context) {
        this.db = MyDB.getInstance(context).db;
    }

    @Override
    public ArrayList<Attribute> getAll() throws GenericDAOException {
        //aux variables;
        ArrayList<Attribute> resAttribute = new ArrayList<>();
        int id;
        String type;
        String name;
        int deleted;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM "+TABLE_NAME, null );
        res.moveToFirst();

        //Parse data
        while(res.isAfterLast() == false) {
            id = res.getInt(res.getColumnIndexOrThrow(COLUMN_ID));
            type = res.getString(res.getColumnIndexOrThrow(COLUMN_TYPE));
            name = res.getString(res.getColumnIndexOrThrow(COLUMN_NAME));
            deleted = res.getInt(res.getColumnIndexOrThrow(COLUMN_DELETED));
            resAttribute.add(new Attribute(id, type, name, deleted));
            res.moveToNext();
        }

        return resAttribute;
    }

    @Override
    public Attribute getById(int id) throws GenericDAOException {
        //aux variables;
        Attribute resAttribute;
        String type;
        String name;
        int deleted;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_ID+"="+id, null );
        res.moveToFirst();

        //Parse data
        type = res.getString(res.getColumnIndexOrThrow(COLUMN_TYPE));
        name = res.getString(res.getColumnIndexOrThrow(COLUMN_NAME));
        deleted = res.getInt(res.getColumnIndexOrThrow(COLUMN_DELETED));
        resAttribute = new Attribute(id, type, name, deleted);

        return resAttribute;
    }

    @Override
    public long insert(Attribute object) throws GenericDAOException {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TYPE, object.getType());
        contentValues.put(COLUMN_NAME, object.getName());
        contentValues.put(COLUMN_DELETED, object.getDeleted());

        return db.insert(TABLE_NAME, null, contentValues);
    }

    @Override
    public boolean delete(Attribute object) throws GenericDAOException {
        int deletedCount = db.delete(TABLE_NAME,
                COLUMN_ID + " = ? ",
                new String[] { Integer.toString(object.getId()) });
        return true;
    }

    public boolean deleteById(int id){
        int deletedCount = db.delete(TABLE_NAME,
                COLUMN_ID + " = ? ",
                new String[] { Integer.toString(id) });
        return true;
    }

    @Override
    public boolean update(Attribute object) throws GenericDAOException {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TYPE, object.getType());
        contentValues.put(COLUMN_NAME, object.getName());
        contentValues.put(COLUMN_DELETED, object.getDeleted());
        db.update(TABLE_NAME,
                contentValues,
                COLUMN_ID + " = ? ",
                new String[] { Integer.toString(object.getId()) } );
        return true;
    }

    public int numberOfRows(){
        return (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
    }

    @Override
    public boolean exists(Attribute object) throws GenericDAOException {
        //TODO implement exists
        return false;
    }

    @Override
    public List<Attribute> getByCriteria(Attribute object) throws GenericDAOException {
        //TODO implement getByCriteria
        return null;
    }
}
