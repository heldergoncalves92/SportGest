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
import studentcompany.sportgest.domains.User;

public class User_DAO extends GenericDAO<User> implements IGenericDAO<User>{
    //Database name
    private SQLiteDatabase db;

    //Table names
    public static final String TABLE_NAME         = "APPUSER";

    //Table columns
    public static final String COLUMN_ID          = "ID";
    public static final String COLUMN_NAME        = "NAME";
    public static final String COLUMN_PASSWORD    = "PASSWORD";
    public static final String COLUMN_PHOTO       = "PHOTO";
    public static final String COLUMN_EMAIL       = "EMAIL";
    public static final String COLUMN_ROLE_ID     = "ROLE_ID"; // ROLE IS MANDATORY

    private Role_DAO role_dao;

    //Create table
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            COLUMN_NAME + " TEXT NOT NULL," +
            COLUMN_PASSWORD + " TEXT NOT NULL," +
            COLUMN_PHOTO + " TEXT NOT NULL," +
            COLUMN_EMAIL + " TEXT NOT NULL," +
            COLUMN_ROLE_ID + " INTEGER," + // NOT NULL," +
            "FOREIGN KEY(" + COLUMN_ROLE_ID + ") REFERENCES " + Role_DAO.TABLE_NAME + "(" + Role_DAO.COLUMN_ID + ")"+
            "); ";

    //Drop table
    public static  final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + "; ";


    public User_DAO(Context context) {
        this.db = MyDB.getInstance(context).db;
        role_dao = new Role_DAO(context);
    }

    @Override
    public List<User> getAll() throws GenericDAOException {
        ArrayList<User> resUser = new ArrayList<>();
        int id,roleId;
        String name,password,photo,email;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM "+TABLE_NAME, null );
        res.moveToFirst();

        //Parse data
        while(!res.isAfterLast()) {
            id = res.getInt(res.getColumnIndexOrThrow(COLUMN_ID));
            name = res.getString(res.getColumnIndexOrThrow(COLUMN_NAME));
            password = res.getString(res.getColumnIndexOrThrow(COLUMN_PASSWORD));
            photo = res.getString(res.getColumnIndexOrThrow(COLUMN_PHOTO));
            email = res.getString(res.getColumnIndexOrThrow(COLUMN_EMAIL));
            roleId = res.getInt(res.getColumnIndexOrThrow(COLUMN_ROLE_ID));
            Role role = role_dao.getById(roleId); // Get the Role
            resUser.add(new User(id, name,password,photo,name,email,role));
            res.moveToNext();
        }
        res.close(); // Close the cursor
        return resUser;
    }

    @Override
    public User getById(int id) throws GenericDAOException {


        //Query
        Cursor res = db.rawQuery( "SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_ID+"="+id, null );
        res.moveToFirst();

        //Parse data
        if(res.getCount()==1)
           {int roleId;
            String name,password,photo,email;

            name = res.getString(res.getColumnIndexOrThrow(COLUMN_NAME));
            password = res.getString(res.getColumnIndexOrThrow(COLUMN_PASSWORD));
            photo = res.getString(res.getColumnIndexOrThrow(COLUMN_PHOTO));
            email = res.getString(res.getColumnIndexOrThrow(COLUMN_EMAIL));
            roleId = res.getInt(res.getColumnIndexOrThrow(COLUMN_ROLE_ID));
            Role role = role_dao.getById(roleId); // Get the Role
            res.close(); // Close the cursor
            return new User(id, name,password,photo,name,email,role);}
        else
            {res.close(); // Close the cursor
             return null;}
    }

    @Override
    public long insert(User object) throws GenericDAOException {

        if(object==null)
            return -1;

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, object.getName());
        contentValues.put(COLUMN_PASSWORD, object.getPassword());
        contentValues.put(COLUMN_PHOTO, object.getPhoto());
        contentValues.put(COLUMN_EMAIL, object.getEmail());
        contentValues.put(COLUMN_ROLE_ID, object.getRole() != null ? object.getRole().getId() : null);
        return db.insert(TABLE_NAME, null, contentValues);
    }

    @Override
    public boolean delete(User object) throws GenericDAOException {

        if(object==null)
            return false;

        return deleteById(object.getId());
    }

    public boolean deleteById(int id){
        return db.delete(TABLE_NAME,
                COLUMN_ID + " = ? ",
                new String[]{Integer.toString(id)}) > 0;
    }

    @Override
    public boolean update(User object) throws GenericDAOException {

        if(object==null)
            return false;

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, object.getName());
        contentValues.put(COLUMN_PASSWORD, object.getPassword());
        contentValues.put(COLUMN_PHOTO, object.getPhoto());
        contentValues.put(COLUMN_EMAIL, object.getEmail());
        contentValues.put(COLUMN_ROLE_ID, object.getRole().getId());
        return db.update(TABLE_NAME,
                contentValues,
                COLUMN_ID + " = ? ",
                new String[]{Integer.toString(object.getId())}) > 0;

    }

    public int numberOfRows(){
        return (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
    }

    @Override
    public boolean exists(User object) throws GenericDAOException {

        if(object==null)
            return false;

        int fields = 0;
        String tmpString;
        int tmpInt;

        StringBuilder statement = new StringBuilder("SELECT * FROM "+ TABLE_NAME +" where ");
        if ((tmpInt = object.getId()) >= 0) {
            statement.append(COLUMN_ID).append("=").append(tmpInt);
            fields++;
        }
        if ((tmpString = object.getName()) != null) {
            statement.append((fields != 0) ? " AND " : "").append(COLUMN_NAME).append(" = '").append(tmpString).append("'");
            fields++;
        }
        if ((tmpString = object.getPassword()) != null) {
            statement.append((fields != 0) ? " AND " : "").append(COLUMN_PASSWORD).append(" = '").append(tmpString).append("'");
            fields++;
        }
        if ((tmpString = object.getPhoto()) != null) {
            statement.append((fields != 0) ? " AND " : "").append(COLUMN_PHOTO).append(" = '").append(tmpString).append("'");
            fields++;
        }
        if ((tmpString = object.getEmail()) != null) {
            statement.append((fields != 0) ? " AND " : "").append(COLUMN_EMAIL).append(" = '").append(tmpString).append("'");
            fields++;
        }
        if ((tmpInt = object.getRole().getId()) >= 0) {
            statement.append((fields != 0) ? " AND " : "").append(COLUMN_ROLE_ID).append(" = ").append(tmpInt);
            fields++;
        }

        if (fields > 0) {
            Cursor res = db.rawQuery(statement.toString(), null);
            boolean re = res.moveToFirst();
            res.close(); // Close the Cursor
            return re;
        }
        else
            return false;
    }

    @Override
    public List<User> getByCriteria(User object) throws GenericDAOException {

        if(object==null)
            return null;

        List<User> roles = new ArrayList<>();
        int fields = 0;
        String tmpString;
        int tmpInt;
        int id,roleId;
        String name,password,photo,email;
        Role role;

        StringBuilder statement = new StringBuilder("SELECT * FROM "+ TABLE_NAME +" where ");
        if ((tmpInt = object.getId()) >= 0) {
            statement.append(COLUMN_ID + "=" + tmpInt);
            fields++;
        }
        if ((tmpString = object.getName()) != null) {
            statement.append((fields != 0) ? " AND " : "").append(COLUMN_NAME).append(" LIKE '%").append(tmpString).append("%'");
            fields++;
        }
        if ((tmpString = object.getPassword()) != null) {
            statement.append((fields != 0) ? " AND " : "").append(COLUMN_PASSWORD).append(" LIKE '%").append(tmpString).append("%'");
            fields++;
        }
        if ((tmpString = object.getPhoto()) != null) {
            statement.append((fields != 0) ? " AND " : "").append(COLUMN_PHOTO).append(" LIKE '%").append(tmpString).append("%'");
            fields++;
        }
        if ((tmpString = object.getEmail()) != null) {
            statement.append((fields != 0) ? " AND " : "").append(COLUMN_EMAIL).append(" LIKE '%").append(tmpString).append("%'");
            fields++;
        }
        if ((tmpInt = object.getRole().getId()) >= 0) {
            statement.append((fields != 0) ? " AND " : "").append(COLUMN_ROLE_ID).append(" = ").append(tmpInt);
            fields++;
        }

        if (fields > 0) {

            Cursor res = db.rawQuery( statement.toString(), null );
            if(res.moveToFirst())
                while(!res.isAfterLast()) {
                    id = res.getInt(res.getColumnIndexOrThrow(COLUMN_ID));
                    name = res.getString(res.getColumnIndexOrThrow(COLUMN_NAME));
                    password = res.getString(res.getColumnIndexOrThrow(COLUMN_PASSWORD));
                    photo = res.getString(res.getColumnIndexOrThrow(COLUMN_PHOTO));
                    email = res.getString(res.getColumnIndexOrThrow(COLUMN_EMAIL));
                    roleId = res.getInt(res.getColumnIndexOrThrow(COLUMN_ROLE_ID));
                    role = role_dao.getById(roleId); // Get the Role
                    roles.add(new User(id, name,password,photo,name,email,role));
                    res.moveToNext();
                }
            res.close(); // Close the Cursor
        }

        return roles;
    }
}
