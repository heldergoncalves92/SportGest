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
import studentcompany.sportgest.domains.Role;

public class Role_DAO extends GenericDAO<Role> implements IGenericDAO<Role>{
    //Database name
    private SQLiteDatabase db;

    //Table names
    public static final String TABLE_NAME         = "ROLE";

    //Table columns
    public static final String COLUMN_ID          = "ID";
    public static final String COLUMN_NAME          = "NAME";

    //Create table
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            COLUMN_NAME + " TEXT NOT NULL); ";

    //Drop table
    public static  final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + "; ";


    public Role_DAO(Context context) {
        this.db = MyDB.getInstance(context).db;
    }

    @Override
    public List<Role> getAll() throws GenericDAOException {
        ArrayList<Role> resRole = new ArrayList<>();
        int id;
        String name;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM "+TABLE_NAME, null );
        res.moveToFirst();

        //Parse data
        while(res.isAfterLast() == false) {
            id = res.getInt(res.getColumnIndexOrThrow(COLUMN_ID));
            name = res.getString(res.getColumnIndexOrThrow(COLUMN_NAME));
            resRole.add(new Role(id, name));
            res.moveToNext();
        }

        return resRole;
    }

    @Override
    public Role getById(int id) throws GenericDAOException {


        //Query
        Cursor res = db.rawQuery( "SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_ID+"="+id, null );
        res.moveToFirst();

        //Parse data
        if(res.getCount()==1)
        {String name;
            name = res.getString(res.getColumnIndexOrThrow(COLUMN_NAME));
            return new Role(id, name);}
        else
            return null;
    }

    @Override
    public long insert(Role object) throws GenericDAOException {

        if(object==null)
            return -1;

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, object.getName());
        return db.insert(TABLE_NAME, null, contentValues);
    }

    @Override
    public boolean delete(Role object) throws GenericDAOException {

        if(object==null)
            return false;

        return deleteById(object.getId());
    }

    public boolean deleteById(int id){
        return db.delete(TABLE_NAME,
                COLUMN_ID + " = ? ",
                new String[] { Integer.toString(id) }) > 0 ? true : false;
    }

    @Override
    public boolean update(Role object) throws GenericDAOException {

        if(object==null)
            return false;

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, object.getName());
        return db.update(TABLE_NAME,
                contentValues,
                COLUMN_ID + " = ? ",
                new String[]{Integer.toString(object.getId())}) >0 ? true : false ;

    }

    public int numberOfRows(){
        return (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
    }

    @Override
    public boolean exists(Role object) throws GenericDAOException {

        if(object==null)
            return false;

        int fields = 0;
        String tmpString;
        int tmpInt;

        StringBuilder statement = new StringBuilder("SELECT * FROM "+ TABLE_NAME +" where ");
        if ((tmpInt = object.getId()) >= 0) {
            statement.append(COLUMN_ID + "=" + tmpInt);
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
    public List<Role> getByCriteria(Role object) throws GenericDAOException {

        if(object==null)
            return null;

        List<Role> roles = new ArrayList<>();
        int fields = 0;
        String tmpString;
        int tmpInt;

        StringBuilder statement = new StringBuilder("SELECT * FROM "+ TABLE_NAME +" where ");
        if ((tmpInt = object.getId()) >= 0) {
            statement.append(COLUMN_ID + "=" + tmpInt);
            fields++;
        }
        if ((tmpString = object.getName()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_NAME + " LIKE '%" + tmpString + "%'");
            fields++;
        }

        if (fields > 0) {


            Cursor res = db.rawQuery( statement.toString(), null );
            if(res.moveToFirst())
                while(res.isAfterLast() == false) {
                    int id = res.getInt(res.getColumnIndexOrThrow(COLUMN_ID));
                    String name = res.getString(res.getColumnIndexOrThrow(COLUMN_NAME));
                    roles.add(new Role(id, name));
                    res.moveToNext();
                }
        }


        return roles;
    }
}
