package studentcompany.sportgest.daos;
//TODO methods

import android.database.sqlite.SQLiteDatabase;

public class Obs_Category_DAO {
    //Database name
    private SQLiteDatabase db;

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

}
