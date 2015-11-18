package studentcompany.sportgest.daos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB_Connection extends SQLiteOpenHelper
{
    public SQLiteDatabase MyDatabase;
    static DB_Connection _Instance;
    private static Context context;

    private DB_Connection(Context context)
    {
        super(context, "SportGest.db", null, 1);
        this.context = context;
    }

    public static DB_Connection getInstance ()
    {
        if (_Instance == null)
            _Instance = new DB_Connection(DB_Connection.context);
        return _Instance;
    }

    void setInstance(DB_Connection value)
    {
        _Instance = value;
        value.Init();
    }

    void Init()
    {

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.MyDatabase = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
