package studentcompany.sportgest.daos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//EVENT
//EVENT_CATEGORY
//GAME
//OBSERVATION
//OBS_CATEGORY
public class Event_DAO extends SQLiteOpenHelper {

    //Database name
    public final String DATABASE_NAME;

    //Table names
    public static final String EVENT_TABLE_NAME             = "EVENT";
    public static final String EVENT_CATEGORY_TABLE_NAME    = "EVENT_CATEGORY";
    public static final String GAME_TABLE_NAME              = "GAME";
    public static final String OBSERVATION_TABLE_NAME       = "OBSERVATION";
    public static final String OBS_CATEGORY_TABLE_NAME      = "OBS_CATEGORY";

    //Table columns
    public static final String EVENT_COLUMN_ID                  = "ID";
    public static final String EVENT_COLUMN_DESCRIPTION         = "DESCRIPTION";
    public static final String EVENT_COLUMN_DATE                = "\"DATE\"";
    public static final String EVENT_COLUMN_POSX                = "POSX";
    public static final String EVENT_COLUMN_POSY                = "POSY";
    public static final String EVENT_COLUMN_EVENT_CATEGORYID    = "EVENT_CATEGORYID";
    public static final String EVENT_COLUMN_GAMEID              = "GAMEID";
    public static final String EVENT_COLUMN_PLAYER_ID           = "PLAYER_ID";

    public static final String EVENT_CATEGORY_COLUMN_ID         = "ID";
    public static final String EVENT_CATEGORY_COLUMN_CATEGORY   = "CATEGORY";

    public static final String GAME_COLUMN_ID             = "ID";
    public static final String GAME_COLUMN_HOME_TEAMID    = "HOME_TEAMID";
    public static final String GAME_COLUMN_VISITOR_TEAMID = "VISITOR_TEAMID";
    public static final String GAME_COLUMN_DATE           = "\"DATE\"";
    public static final String GAME_COLUMN_REPORT         = "REPORT";
    public static final String GAME_COLUMN_HOME_SCORE     = "HOME_SCORE";
    public static final String GAME_COLUMN_VISITOR_SCORE  = "VISITOR_SCORE";
    public static final String GAME_COLUMN_DURATION       = "DURATION";

    public static final String OBSERVATION_COLUMN_ID             = "ID";
    public static final String OBSERVATION_COLUMN_TITLE          = "TITLE";
    public static final String OBSERVATION_COLUMN_DESCRIPTION    = "DESCRIPTION";
    public static final String OBSERVATION_COLUMN_DATE           = "\"DATE\"";
    public static final String OBSERVATION_COLUMN_OBS_CATEGORYID = "OBS_CATEGORYID";
    public static final String OBSERVATION_COLUMN_PLAYER_ID      = "PLAYER_ID";
    public static final String OBSERVATION_COLUMN_USER_ID        = "USER_ID";
    public static final String OBSERVATION_COLUMN_GAME_ID        = "GAME_ID";

    public static final String OBS_CATEGORY_COLUMN_ID         = "ID";
    public static final String OBS_CATEGORY_COLUMN_CATEGORY   = "CATEGORY";

    public Event_DAO(Context context, String db_name, int version) {
        super(context, db_name, null, version);
        this.DATABASE_NAME = db_name;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
