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
    public static final String COLUMN_CATEGORY    = "CATEGORY";

    //Create table
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            COLUMN_CATEGORY + " TEXT NOT NULL); ";

    //Drop table
    public static  final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + "; ";

    public Event_Category_DAO(Context context) {
        this.db = MyDB.getInstance(context).db;
    }

    @Override
    public ArrayList<EventCategory> getAll() throws GenericDAOException {
        //aux variables;
        ArrayList<EventCategory> resEventCategory = new ArrayList<>();
        long id;
        String category;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM "+TABLE_NAME, null );
        res.moveToFirst();

        //Parse data
        while(res.isAfterLast() == false) {
            id = res.getLong(res.getColumnIndexOrThrow(COLUMN_ID));
            category = res.getString(res.getColumnIndexOrThrow(COLUMN_CATEGORY));
            resEventCategory.add(new EventCategory(id, category));
            res.moveToNext();
        }

        return resEventCategory;
    }

    @Override
    public EventCategory getById(long id) throws GenericDAOException{
        //aux variables;
        EventCategory resEventCategory;
        String category;

        //Query
        Cursor rs = db.rawQuery( "SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_ID+"="+id, null );
        rs.moveToFirst();

        //Parse data
        category = rs.getString(rs.getColumnIndexOrThrow(COLUMN_CATEGORY));
        resEventCategory = new EventCategory(id, category);

        return resEventCategory;
    }

    @Override
    public long insert(EventCategory object) throws GenericDAOException{

        ContentValues contentValues = new ContentValues();

        if(object.getId()>0)
            contentValues.put(COLUMN_ID, object.getId());
        contentValues.put(COLUMN_CATEGORY,  object.getName());

        return db.insert(TABLE_NAME, null, contentValues);
    }

    @Override
    public boolean delete(EventCategory object) throws GenericDAOException{
        int deletedCount = db.delete(TABLE_NAME,
                COLUMN_ID + " = ? ",
                new String[] { Long.toString(object.getId()) });
        return true;
    }

    @Override
    public boolean deleteById(long id){
        int deletedCount = db.delete(TABLE_NAME,
                COLUMN_ID + " = ? ",
                new String[] { Long.toString(id) });
        return true;
    }

    @Override
    public boolean update(EventCategory object) throws GenericDAOException{
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, object.getId());
        contentValues.put(COLUMN_CATEGORY,  object.getName());

        db.update(TABLE_NAME,
                contentValues,
                COLUMN_ID + " = ? ",
                new String[]{Long.toString(object.getId())});
        return true;
    }

    @Override
    public int numberOfRows(){
        return (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
    }

    @Override
    public boolean exists(EventCategory object) throws GenericDAOException {

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
        if ((tmpString = object.getName()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_CATEGORY + " = '" + tmpString + "'");
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

        StringBuilder statement = new StringBuilder("SELECT * FROM "+ TABLE_NAME +" where ");
        if ((tmpLong = object.getId()) >= 0) {
            statement.append(COLUMN_ID + "=" + tmpLong);
            fields++;
        }
        if ((tmpString = object.getName()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_CATEGORY + " LIKE '%" + tmpString + "%'");
            fields++;
        }

        if (fields > 0) {

            long id;
            String category;

            Cursor res = db.rawQuery( statement.toString(), null );
            if(res.moveToFirst())

                while(res.isAfterLast() == false) {
                    id = res.getLong(res.getColumnIndexOrThrow(COLUMN_ID));
                    category = res.getString(res.getColumnIndexOrThrow(COLUMN_CATEGORY));
                    resEventCategory.add(new EventCategory(id, category));
                    res.moveToNext();
                }
        }


        return resEventCategory;
    }
}
