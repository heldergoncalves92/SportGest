package studentcompany.sportgest.daos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//TRAINING
//TRAINING_EXERCISE
//EXERCISE
//ATTRIBUTE_EXERCISE
//ATTRIBUTE
//RECORD
public class Training_DAO extends SQLiteOpenHelper {

    public Training_DAO(Context context, String db_name, int version) {
        super(context, db_name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
