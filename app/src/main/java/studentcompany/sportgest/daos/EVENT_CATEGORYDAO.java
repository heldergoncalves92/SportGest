package studentcompany.sportgest.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class EVENT_CATEGORYDAO extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "SportGest.db";
	public static final String EVENT_CATEGORY_TABLE_NAME = "EVENT_CATEGORY";
	public static final String EVENT_CATEGORY_COLUMN_ID = "ID";
	public static final String EVENT_CATEGORY_COLUMN_CATEGORY = "CATEGORY";

	public EVENT_CATEGORYDAO(Context context)
	{
		super(context, DATABASE_NAME , null, 1);
	}

	public EVENT_CATEGORYDAO(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	public EVENT_CATEGORYDAO(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(
				"CREATE TABLE EVENT_CATEGORY (ID INTEGER NOT NULL PRIMARY KEY, CATEGORY text NOT NULL)"
		);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS EVENT_CATEGORY");
		onCreate(db);
	}

	public boolean insertEventCategory  (String category)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(EVENT_CATEGORY_COLUMN_CATEGORY, category);
		db.insert(EVENT_CATEGORY_TABLE_NAME, null, contentValues);
		return true;
	}

	public Cursor getData(int id){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res =  db.rawQuery( "select * from "+EVENT_CATEGORY_TABLE_NAME+" where ID="+id+"", null );
		return res;
	}

	public int numberOfRows(){
		SQLiteDatabase db = this.getReadableDatabase();
		int numRows = (int) DatabaseUtils.queryNumEntries(db, EVENT_CATEGORY_TABLE_NAME);
		return numRows;
	}

	public boolean updateEventCategory (Integer id, String category)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(EVENT_CATEGORY_COLUMN_CATEGORY, category);
		db.update(EVENT_CATEGORY_TABLE_NAME, contentValues, "ID = ? ", new String[] { Integer.toString(id) } );
		return true;
	}

	public Integer deleteEventCategory (Integer id)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		return db.delete(EVENT_CATEGORY_TABLE_NAME,
				"ID = ? ",
				new String[] { Integer.toString(id) });
	}

	public ArrayList<String> getAllEventCategories()
	{
		ArrayList<String> array_list = new ArrayList<String>();

		//hp = new HashMap();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res =  db.rawQuery( "select * from " + EVENT_CATEGORY_TABLE_NAME, null );
		res.moveToFirst();

		while(res.isAfterLast() == false){
			array_list.add(res.getString(res.getColumnIndex(EVENT_CATEGORY_COLUMN_CATEGORY)));
			res.moveToNext();
		}
		return array_list;
	}
}
