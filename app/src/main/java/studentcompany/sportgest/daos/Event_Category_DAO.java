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
import studentcompany.sportgest.domains.EventCategory;

public class Event_Category_DAO extends GenericDAO<EventCategory> implements IGenericDAO<EventCategory>{
    //Database name
    private SQLiteDatabase db;

    //Table names
    public static final String TABLE_NAME         = "EVENT_CATEGORY";

    //Table columns
    public static final String COLUMN_ID          = "ID";
    public static final String COLUMN_NAME    = "NAME";
    public static final String COLUMN_COLOR    = "COLOR";
    public static final String COLUMN_HASTIMESTAMP    = "HASTIMESTAMP";

    //Create table
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            COLUMN_COLOR + " INTEGER NOT NULL," +
            COLUMN_HASTIMESTAMP + " INTEGER NOT NULL," +
            COLUMN_NAME + " TEXT NOT NULL); ";

    //Drop table
    public static  final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + "; ";

    public Event_Category_DAO(Context context) {
        this.db = MyDB.getInstance(context).db;
    }

    @Override
    public List<EventCategory> getAll() throws GenericDAOException {
        ArrayList<EventCategory> eventCategories = new ArrayList<>();
        long id; int color;
        boolean hastimestamp;
        String name;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM "+TABLE_NAME, null );
        res.moveToFirst();

        //Parse data
        while(res.isAfterLast() == false) {
            id = res.getLong(res.getColumnIndexOrThrow(COLUMN_ID));
            color = res.getInt(res.getColumnIndexOrThrow(COLUMN_COLOR));
            hastimestamp = res.getInt(res.getColumnIndexOrThrow(COLUMN_HASTIMESTAMP)) > 0 ? true : false;
            name = res.getString(res.getColumnIndexOrThrow(COLUMN_NAME));
            eventCategories.add(new EventCategory(id, name,color,hastimestamp));
            res.moveToNext();
        }

        return eventCategories;
    }

    @Override
    public EventCategory getById(long id) throws GenericDAOException {
        EventCategory resEventCategory;
        String name; int color; boolean hastimestamp;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_ID+"="+id, null );
        res.moveToFirst();

        //Parse data
        if(res.getCount()==1)
        {
            name = res.getString(res.getColumnIndexOrThrow(COLUMN_NAME));
            color = res.getInt(res.getColumnIndexOrThrow(COLUMN_COLOR));
            hastimestamp = res.getInt(res.getColumnIndexOrThrow(COLUMN_HASTIMESTAMP)) > 0 ? true : false;
            res.close(); // Close the cursor
            return new EventCategory(id,name,color,hastimestamp);
        }
        else {
            res.close(); // Close the cursor
            return null;
        }
    }

    @Override
    public long insert(EventCategory object) throws GenericDAOException {

        if(object==null)
            return -1;

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, object.getName());
        contentValues.put(COLUMN_COLOR, object.getColor());
        contentValues.put(COLUMN_HASTIMESTAMP, object.hasTimestamp());

        return db.insert(TABLE_NAME, null, contentValues);
    }

    @Override
    public boolean delete(EventCategory object) throws GenericDAOException {

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
    public boolean update(EventCategory object) throws GenericDAOException {

        if(object==null)
            return false;

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, object.getName());
        contentValues.put(COLUMN_COLOR, object.getColor());
        contentValues.put(COLUMN_HASTIMESTAMP, object.hasTimestamp());
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
    public boolean exists(EventCategory object) throws GenericDAOException {

        if(object==null)
            return false;

        int fields = 0;
        String tmpString;
        long tmpLong;
        int tmpInt;
        boolean tmpBool;

        StringBuilder statement = new StringBuilder("SELECT * FROM "+ TABLE_NAME +" where ");
        if ((tmpLong = object.getId()) >= 0) {
            statement.append(COLUMN_ID + "=" + tmpLong);
            fields++;
        }
        tmpInt = object.getColor();
        statement.append(((fields != 0) ? " AND " : "") + COLUMN_COLOR + "=" + tmpInt);
        fields++;

        if ((tmpBool = object.hasTimestamp()) == true) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_HASTIMESTAMP + "=" + (tmpBool ? 1 : 0));
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
    public List<EventCategory> getByCriteria(EventCategory object) throws GenericDAOException {

        if(object==null)
            return null;

        List<EventCategory> resEventCategory = new ArrayList<>();
        int fields = 0;
        String tmpString;
        long tmpLong;
        int tmpInt;
        boolean tmpBool;

        StringBuilder statement = new StringBuilder("SELECT * FROM "+ TABLE_NAME +" where ");
        if ((tmpLong = object.getId()) >= 0) {
            statement.append(COLUMN_ID + "=" + tmpLong);
            fields++;
        }
        tmpInt = object.getColor();
        statement.append(((fields != 0) ? " AND " : "") + COLUMN_COLOR + "=" + tmpInt);
        fields++;

        if ((tmpBool = object.hasTimestamp()) == true) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_HASTIMESTAMP + "=" + (tmpBool ? 1 : 0));
            fields++;
        }
        if ((tmpString = object.getName()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_NAME + " LIKE '%" + tmpString + "%'");
            fields++;
        }

        if (fields > 0) {

            long id; int color;
            String name;
            boolean ht;

            Cursor res = db.rawQuery( statement.toString(), null );
            if(res.moveToFirst())

                while(res.isAfterLast() == false) {
                    id = res.getLong(res.getColumnIndex(COLUMN_ID));
                    color = res.getInt(res.getColumnIndex(COLUMN_COLOR));
                    name = res.getString(res.getColumnIndex(COLUMN_NAME));
                    ht = res.getInt(res.getColumnIndex(COLUMN_HASTIMESTAMP)) > 0 ? true : false;
                    resEventCategory.add(new EventCategory(id, name,color,ht));
                    res.moveToNext();
                }
        }


        return resEventCategory;
    }
}
