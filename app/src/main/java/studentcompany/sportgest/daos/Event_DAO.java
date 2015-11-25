package studentcompany.sportgest.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import studentcompany.sportgest.daos.db.MyDB;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Event;
import studentcompany.sportgest.domains.EventCategory;
import studentcompany.sportgest.domains.Game;
import studentcompany.sportgest.domains.Player;

public class Event_DAO extends GenericDAO<Event> implements IGenericDAO<Event> {

    //Database name
    private SQLiteDatabase      db;

    //Dependencies DAOs
    private Event_Category_DAO  event_category_dao;
    private Game_DAO            game_dao;
    private Player_DAO          player_dao;

    //Table names
    public static final String TABLE_NAME                  = "EVENT";

    //Table columns
    public static final String COLUMN_ID                   = "ID";
    public static final String COLUMN_DESCRIPTION          = "DESCRIPTION";
    public static final String COLUMN_DATE                 = "\"DATE\"";
    public static final String COLUMN_POSX                 = "POSX";
    public static final String COLUMN_POSY                 = "POSY";
    public static final String COLUMN_EVENT_CATEGORYID     = "EVENT_CATEGORYID";
    public static final String COLUMN_GAMEID               = "GAMEID";
    public static final String COLUMN_PLAYER_ID            = "PLAYER_ID";

    //Create table
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_DESCRIPTION + " TEXT, " +
            COLUMN_DATE + " INTEGER NOT NULL, " +
            COLUMN_POSX + " REAL NOT NULL, " +
            COLUMN_POSY + " REAL NOT NULL, " +
            COLUMN_EVENT_CATEGORYID + " INTEGER NOT NULL, " +
            COLUMN_GAMEID + " INTEGER NOT NULL, " +
            COLUMN_PLAYER_ID + " INTEGER NOT NULL, " +
            "FOREIGN KEY(" + COLUMN_EVENT_CATEGORYID + ") REFERENCES " + Event_DAO.TABLE_NAME + "(" + Event_DAO.COLUMN_ID + "), " +
            "FOREIGN KEY(" + COLUMN_GAMEID + ") REFERENCES " + Game_DAO.TABLE_NAME + "(" + Game_DAO.COLUMN_ID + "), " +
            "FOREIGN KEY(" + COLUMN_PLAYER_ID + ") REFERENCES PLAYER(ID)); ";

    //Drop table
    public static  final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + "; ";

    public Event_DAO(Context context) {
        this.db = MyDB.getInstance(context).db;
        this.event_category_dao = new Event_Category_DAO(context);
        this.game_dao = new Game_DAO(context);
        this.player_dao = new Player_DAO(context);
    }

    @Override
    public ArrayList<Event> getAll() throws GenericDAOException {

        //aux variables;
        ArrayList<Event> resEvent = new ArrayList<>();
        int id;
        String description;
        int date;
        float posx;
        float posy;
        int eventCategoryId;
        int gameId;
        int playerId;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME, null );
        res.moveToFirst();

        //Parse data
        while(res.isAfterLast() == false) {
            id = res.getInt(res.getColumnIndex(COLUMN_ID));
            description = res.getString(res.getColumnIndex(COLUMN_DESCRIPTION));
            date = res.getInt(res.getColumnIndex(COLUMN_DATE));
            posx = res.getFloat(res.getColumnIndex(COLUMN_POSX));
            posy = res.getFloat(res.getColumnIndex(COLUMN_POSY));
            eventCategoryId = res.getInt(res.getColumnIndex(COLUMN_EVENT_CATEGORYID));
            gameId = res.getInt(res.getColumnIndex(COLUMN_GAMEID));
            playerId = res.getInt(res.getColumnIndex(COLUMN_PLAYER_ID));
            resEvent.add(new Event(id, description, date, posx, posy,
                    event_category_dao.getById(eventCategoryId),
                    game_dao.getById(gameId),
                    player_dao.getById(playerId)));
            res.moveToNext();
        }

        return resEvent;
    }

    @Override
    public Event getById(int id) throws GenericDAOException {

        //aux variables;
        Event resEvent;
        String description;
        int date;
        float posx;
        float posy;
        int eventCategoryId;
        int gameId;
        int playerId;
        EventCategory eventCategory;
        Game game;
        Player player;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + "=" + id, null );
        res.moveToFirst();

        //Parse data
        description = res.getString(res.getColumnIndex(COLUMN_DESCRIPTION));
        date = res.getInt(res.getColumnIndex(COLUMN_DATE));
        posx = res.getFloat(res.getColumnIndex(COLUMN_POSX));
        posy = res.getFloat(res.getColumnIndex(COLUMN_POSY));
        eventCategoryId = res.getInt(res.getColumnIndex(COLUMN_EVENT_CATEGORYID));
        gameId = res.getInt(res.getColumnIndex(COLUMN_GAMEID));
        playerId = res.getInt(res.getColumnIndex(COLUMN_PLAYER_ID));

        resEvent = new Event(id, description, date, posx, posy,
                event_category_dao.getById(eventCategoryId),
                game_dao.getById(gameId),
                player_dao.getById(playerId));

        return resEvent;
    }

    @Override
    public long insert(Event object) throws GenericDAOException {

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DESCRIPTION, object.getDescription());
        contentValues.put(COLUMN_DATE, object.getDate());
        contentValues.put(COLUMN_POSX, object.getPosx());
        contentValues.put(COLUMN_POSY, object.getPosy());
        contentValues.put(COLUMN_EVENT_CATEGORYID, object.getEventCategory().getId());
        contentValues.put(COLUMN_GAMEID, object.getGame().getId());
        contentValues.put(COLUMN_PLAYER_ID, object.getPlayer().getId());

        return db.insert(TABLE_NAME, null, contentValues);
    }

    @Override
    public boolean delete(Event object) throws GenericDAOException {
        int deletedCount = db.delete(TABLE_NAME,
                COLUMN_ID + " = ? ",
                new String[] { Integer.toString(object.getId()) });
        return true;
    }

    public boolean deleteById(int id) {

        int deletedCount = db.delete(TABLE_NAME,
                COLUMN_ID + " = ? ",
                new String[] { Integer.toString(id) });
        return true;
    }

    @Override
    public boolean update(Event object) throws GenericDAOException {

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DESCRIPTION, object.getDescription());
        contentValues.put(COLUMN_DATE, object.getDate());
        contentValues.put(COLUMN_POSX, object.getPosx());
        contentValues.put(COLUMN_POSY, object.getPosy());
        contentValues.put(COLUMN_EVENT_CATEGORYID, object.getEventCategory().getId());
        contentValues.put(COLUMN_GAMEID, object.getGame().getId());
        contentValues.put(COLUMN_PLAYER_ID, object.getPlayer().getId());

        db.update(TABLE_NAME,
                contentValues,
                COLUMN_ID + " = ? ",
                new String[] { Integer.toString(object.getId()) } );
        return true;
    }

    public int numberOfRows(){
        return (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
    }

    @Override
    public boolean exists(Event object) throws GenericDAOException {

        if(object==null)
            return false;

        int fields = 0;
        String tmpString;
        int tmpInt;
        float tmpFloat;

        StringBuilder statement = new StringBuilder("SELECT * FROM "+ TABLE_NAME +" where ");
        if ((tmpInt = object.getId()) >= 0) {
            statement.append(COLUMN_ID + "=" + tmpInt);
            fields++;
        }
        if ((tmpString = object.getDescription()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_DESCRIPTION + " = '" + tmpString + "'");
            fields++;
        }
        if ((tmpInt = object.getDate()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_DATE + " = " + tmpInt );
            fields++;
        }
        if ((tmpFloat = object.getPosx()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_POSX + " = " + tmpFloat );
            fields++;
        }
        if ((tmpFloat = object.getPosy()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_POSY + " = " + tmpFloat );
            fields++;
        }
        if ((tmpInt = object.getEventCategory().getId()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_EVENT_CATEGORYID + " = " + tmpInt );
            fields++;
        }
        if ((tmpInt = object.getGame().getId()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_GAMEID + " = " + tmpInt );
            fields++;
        }
        if ((tmpInt = object.getPlayer().getId()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_PLAYER_ID + " = " + tmpInt );
            fields++;
        }

        if (fields > 0) {
            Cursor res = db.rawQuery(statement.toString(), null);
            return res.moveToFirst();
        }
        else
            return false;
    }

    @Override
    public List<Event> getByCriteria(Event object) throws GenericDAOException {

        if(object==null)
            return null;

        List<Event> resEvent = new ArrayList<>();
        int fields = 0;
        String tmpString;
        int tmpInt;
        float tmpFloat;

        StringBuilder statement = new StringBuilder("SELECT * FROM "+ TABLE_NAME +" where ");
        if ((tmpInt = object.getId()) >= 0) {
            statement.append(COLUMN_ID + "=" + tmpInt);
            fields++;
        }
        if ((tmpString = object.getDescription()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_DESCRIPTION + " LIKE '%" + tmpString + "%'");
            fields++;
        }
        if ((tmpInt = object.getDate()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_DATE + " = " + tmpInt );
            fields++;
        }
        if ((tmpFloat = object.getPosx()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_POSX + " = " + tmpFloat );
            fields++;
        }
        if ((tmpFloat = object.getPosy()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_POSY + " = " + tmpFloat );
            fields++;
        }
        if ((tmpInt = object.getEventCategory().getId()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_EVENT_CATEGORYID + " = " + tmpInt );
            fields++;
        }
        if ((tmpInt = object.getGame().getId()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_GAMEID + " = " + tmpInt );
            fields++;
        }
        if ((tmpInt = object.getPlayer().getId()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_PLAYER_ID + " = " + tmpInt );
            fields++;
        }

        if (fields > 0) {

            int id;
            String description;
            int date;
            float posx;
            float posy;
            int eventCategoryId;
            int gameId;
            int playerId;

            Cursor res = db.rawQuery( statement.toString(), null );
            if(res.moveToFirst())

                while(res.isAfterLast() == false) {
                    id = res.getInt(res.getColumnIndex(COLUMN_ID));
                    description = res.getString(res.getColumnIndex(COLUMN_DESCRIPTION));
                    date = res.getInt(res.getColumnIndex(COLUMN_DATE));
                    posx = res.getFloat(res.getColumnIndex(COLUMN_POSX));
                    posy = res.getFloat(res.getColumnIndex(COLUMN_POSY));
                    eventCategoryId = res.getInt(res.getColumnIndex(COLUMN_EVENT_CATEGORYID));
                    gameId = res.getInt(res.getColumnIndex(COLUMN_GAMEID));
                    playerId = res.getInt(res.getColumnIndex(COLUMN_PLAYER_ID));
                    resEvent.add(new Event(id, description, date, posx, posy,
                            event_category_dao.getById(eventCategoryId),
                            game_dao.getById(gameId),
                            player_dao.getById(playerId)));
                    res.moveToNext();
                }
        }


        return resEvent;
    }
}
