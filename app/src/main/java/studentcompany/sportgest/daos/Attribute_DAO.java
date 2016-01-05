package studentcompany.sportgest.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import studentcompany.sportgest.daos.db.MyDB;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Attribute;
import studentcompany.sportgest.domains.Exercise;

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
        long id;
        String type;
        String name;
        int deleted;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM "+TABLE_NAME, null );
        res.moveToFirst();

        //Parse data
        while(res.isAfterLast() == false) {
            id = res.getLong(res.getColumnIndexOrThrow(COLUMN_ID));
            type = res.getString(res.getColumnIndexOrThrow(COLUMN_TYPE));
            name = res.getString(res.getColumnIndexOrThrow(COLUMN_NAME));
            deleted = res.getInt(res.getColumnIndexOrThrow(COLUMN_DELETED));
            resAttribute.add(new Attribute(id, type, name, deleted));
            res.moveToNext();
        }

        res.close();

        return resAttribute;
    }

    @Override
    public Attribute getById(long id) throws GenericDAOException {
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

        res.close();
        return resAttribute;
    }

    @Override
    public long insert(Attribute object) throws GenericDAOException {

        if(object==null)
            return -1;

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TYPE, object.getType());
        contentValues.put(COLUMN_NAME, object.getName());
        contentValues.put(COLUMN_DELETED, object.getDeleted());

        return db.insert(TABLE_NAME, null, contentValues);
    }

    @Override
    public boolean delete(Attribute object) throws GenericDAOException {

        if(object==null)
            return false;

        return deleteById(object.getId());
    }

    @Override
    public boolean deleteById(long id){
        return db.delete(TABLE_NAME,
                COLUMN_ID + " = ? ",
                new String[]{Long.toString(id)}) > 0;
    }

    @Override
    public boolean update(Attribute object) throws GenericDAOException {

        if(object==null)
            return false;

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TYPE, object.getType());
        contentValues.put(COLUMN_NAME, object.getName());
        contentValues.put(COLUMN_DELETED, object.getDeleted());
        db.update(TABLE_NAME,
                contentValues,
                COLUMN_ID + " = ? ",
                new String[] { Long.toString(object.getId()) } );
        return true;
    }

    @Override
    public int numberOfRows(){
        return (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
    }

    @Override
    public boolean exists(Attribute object) throws GenericDAOException {

        if(object==null)
            return false;

        int fields = 0;
        String tmpString;
        int tmpInt;
        long tmpLong;

        StringBuilder statement = new StringBuilder("SELECT * FROM "+ TABLE_NAME +" where ");
        if ((tmpLong = object.getId()) >= 0) {
            statement.append(COLUMN_ID + "=" + tmpLong);
            fields++;
        }
        if ((tmpString = object.getType()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_TYPE + " = '" + tmpString + "'");
            fields++;
        }
        if ((tmpString = object.getName()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_NAME + " = '" + tmpString + "'");
            fields++;
        }
        if ((tmpInt = object.getDeleted()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_DELETED + " = " + tmpInt);
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
    public List<Attribute> getByCriteria(Attribute object) throws GenericDAOException {

        if(object==null)
            return null;

        List<Attribute> resAttribute = new ArrayList<>();
        int fields = 0;
        String tmpString;
        int tmpInt;
        long tmpLong;

        StringBuilder statement = new StringBuilder("SELECT * FROM "+ TABLE_NAME +" where ");
        if ((tmpLong = object.getId()) >= 0) {
            statement.append(COLUMN_ID + "=" + tmpLong);
            fields++;
        }
        if ((tmpString = object.getType()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_TYPE + " LIKE '%" + tmpString + "%'");
            fields++;
        }
        if ((tmpString = object.getName()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_NAME + " LIKE '%" + tmpString + "%'");
            fields++;
        }
        if ((tmpInt = object.getDeleted()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_DELETED + " = " + tmpInt );
            fields++;
        }

        if (fields > 0) {

            long id;
            String type;
            String name;
            int deleted;

            Cursor res = db.rawQuery( statement.toString(), null );
            if(res.moveToFirst())

                while(res.isAfterLast() == false) {
                    id = res.getLong(res.getColumnIndexOrThrow(COLUMN_ID));
                    type = res.getString(res.getColumnIndexOrThrow(COLUMN_TYPE));
                    name = res.getString(res.getColumnIndexOrThrow(COLUMN_NAME));
                    deleted = res.getInt(res.getColumnIndexOrThrow(COLUMN_DELETED));
                    resAttribute.add(new Attribute(id, type, name, deleted));
                    res.moveToNext();
                }
            res.close();
        }


        return resAttribute;
    }
}
