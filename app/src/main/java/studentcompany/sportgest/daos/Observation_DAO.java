package studentcompany.sportgest.daos;
//TODO methods

import android.database.sqlite.SQLiteDatabase;

public class Observation_DAO {
    //Database name
    private SQLiteDatabase db;

    //Table names
    public static final String TABLE_NAME            = "OBSERVATION";

    //Table columns
    public static final String COLUMN_ID             = "ID";
    public static final String COLUMN_TITLE          = "TITLE";
    public static final String COLUMN_DESCRIPTION    = "DESCRIPTION";
    public static final String COLUMN_DATE           = "\"DATE\"";
    public static final String COLUMN_OBS_CATEGORYID = "OBS_CATEGORYID";
    public static final String COLUMN_PLAYER_ID      = "PLAYER_ID";
    public static final String COLUMN_USER_ID        = "USER_ID";
    public static final String COLUMN_GAME_ID        = "GAME_ID";

    //Create table
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, \n" +
            COLUMN_TITLE + " TEXT NOT NULL, \n" +
            COLUMN_DESCRIPTION + " TEXT, \n" +
            COLUMN_DATE + " INTEGER NOT NULL, \n" +
            COLUMN_OBS_CATEGORYID + " INTEGER NOT NULL, \n" +
            COLUMN_PLAYER_ID + " INTEGER NOT NULL, \n" +
            COLUMN_USER_ID + " INTEGER NOT NULL, \n" +
            COLUMN_GAME_ID + " INTEGER NOT NULL, \n" +
            "FOREIGN KEY(" + COLUMN_OBS_CATEGORYID + ") REFERENCES " + Obs_Category_DAO.TABLE_NAME + "(" + Obs_Category_DAO.COLUMN_ID + "), \n" +
            "FOREIGN KEY(" + COLUMN_PLAYER_ID + ") REFERENCES PLAYER(ID), \n" +
            "FOREIGN KEY(" + COLUMN_USER_ID + ") REFERENCES \"USER\"(ID), \n" +
            "FOREIGN KEY(" + COLUMN_GAME_ID + ") REFERENCES " + Game_DAO.TABLE_NAME + "(" + Game_DAO.COLUMN_ID + "));\n";

    //Drop table
    public static  final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + "; ";
}
