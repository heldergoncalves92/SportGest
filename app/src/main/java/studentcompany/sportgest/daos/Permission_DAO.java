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
import studentcompany.sportgest.domains.Permission;

public class Permission_DAO extends GenericDAO<Permission> implements IGenericDAO<Permission>{
    //Database name
    private SQLiteDatabase db;

    //Table names
    public static final String TABLE_NAME         = "PERMISSION";

    //Table columns
    public static final String COLUMN_ID          = "ID";
    public static final String COLUMN_DESCRIPTION = "DESCRIPTION";

    //Create table
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            COLUMN_DESCRIPTION + " TEXT NOT NULL"
            +"); ";

    //Drop table
    public static  final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + "; ";


    public Permission_DAO(Context context) {
        this.db = MyDB.getInstance(context).db;
    }

    @Override
    public List<Permission> getAll() throws GenericDAOException {
        ArrayList<Permission> resPermission = new ArrayList<>();
        long id;
        String desc;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM "+TABLE_NAME, null );
        res.moveToFirst();

        //Parse data
        while(!res.isAfterLast()) {
            id = res.getLong(res.getColumnIndexOrThrow(COLUMN_ID));
            desc = res.getString(res.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
            resPermission.add(new Permission(id, desc));
            res.moveToNext();
        }

        res.close();

        return resPermission;
    }

    @Override
    public Permission getById(long id) throws GenericDAOException {


        //Query
        Cursor res = db.rawQuery( "SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_ID+"="+id, null );
        res.moveToFirst();

        int count = res.getCount();
        //Parse data
        if(count==1)
            {String desc;
             desc = res.getString(res.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
                res.close();
             return new Permission(id, desc);}
        else{
             res.close();
             return null;}
    }

    @Override
    public long insert(Permission object) throws GenericDAOException {

        if(object==null)
            return -1;

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DESCRIPTION, object.getDescription());
        return db.insert(TABLE_NAME, null, contentValues);
    }

    @Override
    public boolean delete(Permission object) throws GenericDAOException {

        if(object==null)
            return false;

        return deleteById(object.getId());
    }

    public boolean deleteById(long id){
        return db.delete(TABLE_NAME,
                COLUMN_ID + " = ? ",
                new String[] { Long.toString(id) }) > 0;
    }

    @Override
    public boolean update(Permission object) throws GenericDAOException {

        if(object==null)
            return false;

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DESCRIPTION, object.getDescription());
        return db.update(TABLE_NAME,
                contentValues,
                COLUMN_ID + " = ? ",
                new String[]{Long.toString(object.getId())}) >0;

    }

    public int numberOfRows(){
        return (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
    }

    @Override
    public boolean exists(Permission object) throws GenericDAOException {

        if(object==null)
            return false;

        int fields = 0;
        String tmpString;
        long tmpLong;

        StringBuilder statement = new StringBuilder("SELECT * FROM "+ TABLE_NAME +" where ");
        if ((tmpLong = object.getId()) >= 0) {
            statement.append(COLUMN_ID).append("=").append(tmpLong);
            fields++;
        }
        if ((tmpString = object.getDescription()) != null) {
            statement.append(((fields != 0) ? " AND " : "")).append(COLUMN_DESCRIPTION).append(" = '").append(tmpString).append("'");
            fields++;
        }

        if (fields > 0) {
            Cursor res = db.rawQuery(statement.toString(), null);
            boolean re = res.moveToFirst();
            res.close();
            return re;
        }
        else
            return false;
    }

    @Override
    public List<Permission> getByCriteria(Permission object) throws GenericDAOException {

        if(object==null)
            return null;

        List<Permission> permissions = new ArrayList<>();
        int fields = 0;
        String tmpString;
        long tmpLong;

        StringBuilder statement = new StringBuilder("SELECT * FROM "+ TABLE_NAME +" where ");
        if ((tmpLong = object.getId()) >= 0) {
            statement.append(COLUMN_ID).append("=").append(tmpLong);
            fields++;
        }
        if ((tmpString = object.getDescription()) != null) {
            statement.append(((fields != 0) ? " AND " : "")).append(COLUMN_DESCRIPTION).append(" LIKE '%").append(tmpString).append("%'");
            fields++;
        }

        if (fields > 0) {

            Cursor res = db.rawQuery( statement.toString(), null );
            if(res.moveToFirst())
                while(!res.isAfterLast()) {
                    long id = res.getLong(res.getColumnIndexOrThrow(COLUMN_ID));
                    String desc = res.getString(res.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
                    permissions.add(new Permission(id, desc));
                    res.moveToNext();
                }
            res.close();
        }


        return permissions;
    }
}
