package studentcompany.sportgest.daos;
//TODO all

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import studentcompany.sportgest.daos.db.MyDB;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Permission;
import studentcompany.sportgest.domains.Role;

public class Role_Permission_DAO  extends GenericPairDAO<Role, Permission> implements IGenericPairDAO<Role, Permission> {
    //Database name
    private SQLiteDatabase db;

    //Dependencies DAOs
    private Role_DAO role_dao;
    private Permission_DAO permission_dao;

    //Table names
    public static final String TABLE_NAME = "ROLE_PERMISSION";

    //Table columns
    public static final String COLUMN_ROLE_ID = "ROLE_ID";
    public static final String COLUMN_PERMISSION_ID = "PERMISSION_ID";

    //Create table
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ROLE_ID + " INTEGER NOT NULL, " +
            COLUMN_PERMISSION_ID + " INTEGER NOT NULL, " +
            "PRIMARY KEY (" + COLUMN_ROLE_ID + ", " + COLUMN_PERMISSION_ID + "), " +
            "FOREIGN KEY(" + COLUMN_ROLE_ID + ") REFERENCES " + Role_DAO.TABLE_NAME + "(" + Role_DAO.COLUMN_ID + "), " +
            "FOREIGN KEY(" + COLUMN_PERMISSION_ID + ") REFERENCES " + Permission_DAO.TABLE_NAME + "(" + Permission_DAO.COLUMN_ID + "));";

    //Drop table
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + "; ";

    public Role_Permission_DAO(Context context) {
        this.db = MyDB.getInstance(context).db;
        this.role_dao = new Role_DAO(context);
        this.permission_dao = new Permission_DAO(context);
    }


    public List<Permission> getPermissionsByRoleId(long role_id) {
        List<Permission> permissions = new ArrayList<Permission>();

        try {
            List<Permission> pairs = this.getByFirstId(role_id);
            for (Permission p : pairs) {
                Permission permission = permission_dao.getById(p.getId());
                permissions.add(permission);
            }
        } catch (GenericDAOException e) {
            //e.printStackTrace();
        }


        return permissions;
    }


    @Override
    public List<Pair<Role, Permission>> getAll() throws GenericDAOException {

        //aux variables;
        ArrayList<Pair<Role, Permission>> resList = new ArrayList<>();
        long roleId;
        long permissionId;

        //Query
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        res.moveToFirst();

        //Parse data
        while(!res.isAfterLast()) {
            roleId = res.getLong(res.getColumnIndex(COLUMN_ROLE_ID));
            permissionId = res.getLong(res.getColumnIndex(COLUMN_PERMISSION_ID));

            resList.add(
                    new Pair<>(
                            role_dao.getById(roleId),
                            permission_dao.getById(permissionId)));
            res.moveToNext();
        }

        res.close();

        return resList;
    }

    @Override
    public List<Permission> getByFirstId(long id) throws GenericDAOException {
        //aux variables;
        ArrayList<Permission> resList = new ArrayList<>();
        long permissionId;

        //Query
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ROLE_ID + "=" + id, null);
        res.moveToFirst();

        //Parse data
        while(!res.isAfterLast()) {
            permissionId = res.getLong(res.getColumnIndex(COLUMN_PERMISSION_ID));
            resList.add(permission_dao.getById(permissionId));

            res.moveToNext();
        }

        res.close();

        return resList;
    }

    @Override
    public List<Role> getBySecondId(long id) throws GenericDAOException {
        //aux variables;
        ArrayList<Role> resList = new ArrayList<>();
        long roleId;

        //Query
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_PERMISSION_ID + "=" + id, null);
        res.moveToFirst();

        //Parse data
        while(!res.isAfterLast()) {
            roleId = res.getLong(res.getColumnIndex(COLUMN_ROLE_ID));
            resList.add(role_dao.getById(roleId));

            res.moveToNext();
        }

        res.close();

        return resList;
    }

    @Override
    public long insert(Pair<Role, Permission> object) throws GenericDAOException {
        if (object == null)
            return -1;

        if (object.getFirst() == null || object.getSecond() == null)
            return -1;

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ROLE_ID, object.getFirst().getId());
        contentValues.put(COLUMN_PERMISSION_ID, object.getSecond().getId());

        return db.insert(TABLE_NAME, null, contentValues);
    }


    public boolean insertPermissionsByRoleId(long id, List<Permission> list) {
        if (list == null)
            return false;

        if (list.size() == 0)
            return false;

        db.beginTransaction();
        for (Permission pe : list) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_ROLE_ID, id);
            contentValues.put(COLUMN_PERMISSION_ID, pe.getId());
            if (db.insert(TABLE_NAME, null, contentValues) < 0) {
                db.endTransaction();
                return false;
            }
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        return true;
    }

    @Override
    public boolean delete(Pair<Role, Permission> object) throws GenericDAOException {
        if (object == null)
            return false;

        if (object.getFirst() == null || object.getSecond() == null)
            return false;

        return db.delete(TABLE_NAME,
                COLUMN_ROLE_ID + " = ? , " + COLUMN_PERMISSION_ID + " = ? ",
                new String[] { Long.toString(object.getFirst().getId()), Long.toString(object.getSecond().getId()) })  > 0;

    }

    @Override
    public boolean exists(Pair<Role, Permission> object) throws GenericDAOException {
        if (object == null)
            return false;

        if (object.getFirst() == null || object.getSecond() == null)
            return false;

        StringBuilder statement = new StringBuilder("SELECT * FROM " + TABLE_NAME + " where ");
        statement.append(COLUMN_ROLE_ID).append("=").append(object.getFirst().getId());
        statement.append(" AND ").append(COLUMN_PERMISSION_ID).append("=").append(object.getSecond().getId());

        Cursor res = db.rawQuery(statement.toString(), null);
        return res.moveToFirst();
    }

    public boolean deleteAllByRoleId(long role_id) {

        if(role_id <1)
            return false;

        boolean ret = false;
        db.beginTransaction();

        ret = db.delete(TABLE_NAME,
                COLUMN_ROLE_ID + " = ?",
                new String[]{Long.toString(role_id)}) > 0;

        if (ret)
            db.setTransactionSuccessful();
        db.endTransaction();
        return ret;
    }
}
