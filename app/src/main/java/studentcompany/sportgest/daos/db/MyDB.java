package studentcompany.sportgest.daos.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import studentcompany.sportgest.daos.Attribute_DAO;
import studentcompany.sportgest.daos.Attribute_Exercise_DAO;
import studentcompany.sportgest.daos.Event_Category_DAO;
import studentcompany.sportgest.daos.Event_DAO;
import studentcompany.sportgest.daos.Exercise_DAO;
import studentcompany.sportgest.daos.Game_DAO;
import studentcompany.sportgest.daos.Missing_DAO;
import studentcompany.sportgest.daos.Obs_Category_DAO;
import studentcompany.sportgest.daos.Observation_DAO;
import studentcompany.sportgest.daos.Permission_DAO;
import studentcompany.sportgest.daos.Player_DAO;
import studentcompany.sportgest.daos.Player_Position_DAO;
import studentcompany.sportgest.daos.Position_DAO;
import studentcompany.sportgest.daos.Record_DAO;
import studentcompany.sportgest.daos.Role_DAO;
import studentcompany.sportgest.daos.Role_Permission_DAO;
import studentcompany.sportgest.daos.Squad_Call_DAO;
import studentcompany.sportgest.daos.Team_DAO;
import studentcompany.sportgest.daos.Training_DAO;
import studentcompany.sportgest.daos.Training_Exercise_DAO;
import studentcompany.sportgest.daos.User_DAO;
import studentcompany.sportgest.daos.User_Team_DAO;

/**
 * Created by MrFabio on 18/11/2015.
 */
public class MyDB extends SQLiteOpenHelper {

    public SQLiteDatabase db;
    public static final String DATABASE_NAME = "SportGest.db";
    public static final int DATABASE_VERSION = 1;
    public static MyDB _Instance;
    public Context contextx;

    public MyDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        db = this.getWritableDatabase();
    }


    public static MyDB getInstance (Context context) {
        if (_Instance == null) {
            _Instance = new MyDB(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        return _Instance;
    }

    void setInstance(MyDB value) {
        _Instance = value;
        value.Init();
    }

    void Init() {

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Drop tables
        //db.execSQL("DROP DATABASE "+DATABASE_NAME);

        //Create tables
        db.execSQL(Permission_DAO.CREATE_TABLE);
        db.execSQL(Role_DAO.CREATE_TABLE);
        db.execSQL(User_DAO.CREATE_TABLE);
        db.execSQL(Team_DAO.CREATE_TABLE);
        db.execSQL(Position_DAO.CREATE_TABLE);
        db.execSQL(Player_DAO.CREATE_TABLE);
        db.execSQL(Observation_DAO.CREATE_TABLE);
        db.execSQL(Obs_Category_DAO.CREATE_TABLE);
        db.execSQL(Game_DAO.CREATE_TABLE);
        db.execSQL(Squad_Call_DAO.CREATE_TABLE);
        db.execSQL(Event_Category_DAO.CREATE_TABLE);
        db.execSQL(Event_DAO.CREATE_TABLE);
        db.execSQL(Training_DAO.CREATE_TABLE);
        db.execSQL(Exercise_DAO.CREATE_TABLE);
        db.execSQL(Attribute_DAO.CREATE_TABLE);
        db.execSQL(Record_DAO.CREATE_TABLE);
        db.execSQL(Missing_DAO.CREATE_TABLE);
        // PAIRS
        db.execSQL(User_Team_DAO.CREATE_TABLE);
        db.execSQL(Role_Permission_DAO.CREATE_TABLE);
        db.execSQL(Attribute_Exercise_DAO.CREATE_TABLE);
        db.execSQL(Training_Exercise_DAO.CREATE_TABLE);
        db.execSQL(Player_Position_DAO.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No upgrade available
        onCreate(db);
    }



}
