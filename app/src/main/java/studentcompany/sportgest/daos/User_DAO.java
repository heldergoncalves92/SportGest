package studentcompany.sportgest.daos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//USER_TEAM
//USER
//ROLE
//ROLE_PERMISSION
//PERMISSION
public class User_DAO extends SQLiteOpenHelper {

    //Database name
    public final String DATABASE_NAME;

    //Table names
    public static final String EVENT_CATEGORY_TABLE_NAME = "EVENT_CATEGORY";

    //Table columns
    public static final String EVENT_CATEGORY_COLUMN_ID = "ID";
    public static final String EVENT_CATEGORY_COLUMN_CATEGORY = "CATEGORY";

    public User_DAO(Context context, String db_name, int version) {
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
