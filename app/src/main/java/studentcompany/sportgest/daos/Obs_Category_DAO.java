package studentcompany.sportgest.daos;
//TODO methods

import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import studentcompany.sportgest.daos.db.MyDB;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.ObsCategory;


public class Obs_Category_DAO {
    //Database name
    private SQLiteDatabase db;

    //Dependencies DAOs
    private Observation_DAO  observation_dao;


    //Table names
    public static final String TABLE_NAME           = "OBS_CATEGORY";

    //Table columns
    public static final String COLUMN_ID            = "ID";
    public static final String COLUMN_CATEGORY      = "CATEGORY";

    //Create table
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, \n" +
            COLUMN_CATEGORY + " TEXT NOT NULL);\n";

    //Drop table
    public static  final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + "; ";

    public Obs_Category_DAO(Context context) {
        this.db = MyDB.getInstance(context).db;
        this.observation_dao = new Observation_DAO(context);

    }

   // @Override
    public ArrayList<ObsCategory> getAll() throws GenericDAOException {
        //aux variables;
        ArrayList<ObsCategory> resObsCategory = new ArrayList<>();
        int id;
        String category;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM "+TABLE_NAME, null );
        res.moveToFirst();

        //Parse data
        while(res.isAfterLast() == false) {
            id = res.getInt(res.getColumnIndexOrThrow(COLUMN_ID));
            category = res.getString(res.getColumnIndexOrThrow(COLUMN_CATEGORY));
            resObsCategory.add(new ObsCategory(id, category));
            res.moveToNext();
        }

        return resObsCategory;
    }

  //  @Override
    public ObsCategory getById(int id) throws GenericDAOException{
        //aux variables;
        ObsCategory resObsCategory;
        String category;

        //Query
        Cursor rs = db.rawQuery( "SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_ID+"="+id, null );
        rs.moveToFirst();

        //Parse data
        category = rs.getString(rs.getColumnIndexOrThrow(COLUMN_CATEGORY));
        resObsCategory = new ObsCategory(id, category);

        return resObsCategory;
    }

   //@Override
    public long insert(ObsCategory object) throws GenericDAOException{

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CATEGORY, object.getCategory());

        return db.insert(TABLE_NAME, null, contentValues);
    }

  //  @Override
    public boolean delete(ObsCategory object) throws GenericDAOException{
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

    //@Override
    public boolean update(ObsCategory object) throws GenericDAOException{
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CATEGORY, object.getCategory());
        db.update(TABLE_NAME,
                contentValues,
                COLUMN_ID + " = ? ",
                new String[] { Integer.toString(object.getId()) } );
        return true;
    }

    public int numberOfRows(){
        return (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
    }

   // @Override
    public boolean exists(ObsCategory object) throws GenericDAOException{
        //TODO implement exists
        return false;
    }

   // @Override
    public List<ObsCategory> getByCriteria(ObsCategory object) throws GenericDAOException {
        //TODO implement getByCriteria
        return null;
    }

}
