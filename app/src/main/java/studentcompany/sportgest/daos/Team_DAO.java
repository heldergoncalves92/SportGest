package studentcompany.sportgest.daos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//TEAM
//PLAYER
//PLAYER_POSITION
//POSITION
//SQUAD_CALL
//MISSING
public class Team_DAO extends SQLiteOpenHelper {

    public Team_DAO(Context context, String db_name, int version) {
        super(context, db_name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}