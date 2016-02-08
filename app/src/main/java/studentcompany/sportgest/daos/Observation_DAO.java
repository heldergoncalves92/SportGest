package studentcompany.sportgest.daos;
//TODO methods

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import studentcompany.sportgest.daos.db.MyDB;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Observation;


public class Observation_DAO extends GenericDAO<Observation> implements IGenericDAO<Observation> {
    //Database name
    private SQLiteDatabase db;

    //Dependencies DAOs
    private Obs_Category_DAO    obsCategory_dao;
    private Game_DAO            game_dao;
    private Player_DAO          player_dao;
    private User_DAO            user_dao;

    //Table names
    public static final String TABLE_NAME            = "OBSERVATION";

    //Table columns
    public static final String COLUMN_ID             = "ID";
    public static final String COLUMN_TITLE          = "TITLE";
    public static final String COLUMN_DESCRIPTION    = "DESCRIPTION";
    public static final String COLUMN_DATE           = "DATE";
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
            "FOREIGN KEY(" + COLUMN_PLAYER_ID + ") REFERENCES " + Player_DAO.TABLE_NAME + "(" + Player_DAO.COLUMN_ID + "), \n" +
            "FOREIGN KEY(" + COLUMN_USER_ID + ") REFERENCES " + User_DAO.TABLE_NAME + "(" + User_DAO.COLUMN_ID + "), \n" +
            "FOREIGN KEY(" + COLUMN_GAME_ID + ") REFERENCES " + Game_DAO.TABLE_NAME + "(" + Game_DAO.COLUMN_ID + "));\n";

    //Drop table
    public static  final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + "; ";

    public Observation_DAO(Context context) {
        this.db = MyDB.getInstance(context).db;
        this.obsCategory_dao = new Obs_Category_DAO(context);
        this.game_dao = new Game_DAO(context);
        this.player_dao = new Player_DAO(context);
        this.user_dao =new User_DAO(context);
    }

    @Override
    public ArrayList<Observation> getAll() throws GenericDAOException {

        //aux variables;
        ArrayList<Observation> resObservation = new ArrayList<>();
        long id;
        String title;
        String description;
        long date;
        long obsCatId;
        long playerId;
        long gameId;
        long userId;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME, null );
        res.moveToFirst();

        //Parse data
        while(res.isAfterLast() == false) {
            id = res.getLong(res.getColumnIndex(COLUMN_ID));
            title = res.getString(res.getColumnIndex(COLUMN_TITLE));
            description = res.getString(res.getColumnIndex(COLUMN_DESCRIPTION));
            date = res.getLong(res.getColumnIndex(COLUMN_DATE));
            obsCatId = res.getLong(res.getColumnIndex(COLUMN_OBS_CATEGORYID));
            playerId = res.getLong(res.getColumnIndex(COLUMN_PLAYER_ID));
            gameId = res.getLong(res.getColumnIndex(COLUMN_GAME_ID));
            userId = res.getLong(res.getColumnIndex(COLUMN_USER_ID));

            resObservation.add(new Observation(id,title,description,
                    date,obsCategory_dao.getById(obsCatId),
                    player_dao.getById(playerId),
                    user_dao.getById(userId),
                    game_dao.getById(gameId)));
            res.moveToNext();
        }

        return resObservation;
    }

    @Override
    public Observation getById(long id) throws GenericDAOException {

        //aux variables;
        Observation resObservation;
        String title;
        String description;
        long date;
        long obsCatId;
        long playerId;
        long gameId;
        long userId;



        //Query
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + "=" + id, null );
        res.moveToFirst();

        //Parse data
        title = res.getString(res.getColumnIndex(COLUMN_TITLE));
        description = res.getString(res.getColumnIndex(COLUMN_DESCRIPTION));
        date = res.getLong(res.getColumnIndex(COLUMN_DATE));
        obsCatId = res.getLong(res.getColumnIndex(COLUMN_OBS_CATEGORYID));
        playerId = res.getLong(res.getColumnIndex(COLUMN_PLAYER_ID));
        gameId = res.getLong(res.getColumnIndex(COLUMN_GAME_ID));
        userId = res.getLong(res.getColumnIndex(COLUMN_USER_ID));

        resObservation = (new Observation(id,title,description,date,obsCategory_dao.getById(obsCatId),player_dao.getById(playerId),
                user_dao.getById(userId),game_dao.getById(gameId)));

        return resObservation;
    }

    @Override
    public long insert(Observation object) throws GenericDAOException {

        if(object==null)
            return -1;

        if(object.getPlayer()==null || object.getObservationcategory()==null || object.getGame()==null || object.getUser()==null)
            return -1;

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, object.getTitle());
        contentValues.put(COLUMN_DESCRIPTION, object.getDescription());
        contentValues.put(COLUMN_DATE, object.getDate());
        contentValues.put(COLUMN_OBS_CATEGORYID, object.getObservationcategory().getId());
        contentValues.put(COLUMN_PLAYER_ID, object.getPlayer().getId());
        contentValues.put(COLUMN_GAME_ID, object.getGame().getId());
        contentValues.put(COLUMN_USER_ID, object.getUser().getId());

        return db.insert(TABLE_NAME, null, contentValues);
    }

    @Override
    public boolean delete(Observation object) throws GenericDAOException {
        if(object==null)
            return false;

        return deleteById(object.getId());
    }

    public boolean deleteById(long id) {
        return db.delete(TABLE_NAME,
                COLUMN_ID + " = ? ",
                new String[]{Long.toString(id)}) > 0;
    }

    @Override
    public boolean update(Observation object) throws GenericDAOException {

        if(object==null)
            return false;

        if(object.getPlayer()==null || object.getObservationcategory()==null || object.getGame()==null || object.getUser()==null)
            return false;

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, object.getTitle());
        contentValues.put(COLUMN_DESCRIPTION, object.getDescription());
        contentValues.put(COLUMN_DATE, object.getDate());
        contentValues.put(COLUMN_OBS_CATEGORYID, object.getObservationcategory().getId());
        contentValues.put(COLUMN_PLAYER_ID, object.getPlayer().getId());
        contentValues.put(COLUMN_GAME_ID, object.getGame().getId());
        contentValues.put(COLUMN_USER_ID, object.getUser().getId());

        db.update(TABLE_NAME,
                contentValues,
                COLUMN_ID + " = ? ",
                new String[] { Long.toString(object.getId()) } );
        return true;
    }
    @Override
    public int numberOfRows(){
        return (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
    }

    @Override
    public boolean exists(Observation object) throws GenericDAOException {

        if(object==null)
            return false;

        int fields = 0;
        String tmpString;
        int tmpInt;
        long tmpLong;

        StringBuilder statement = new StringBuilder("SELECT * FROM "+ TABLE_NAME +" WHERE ");
        if ((tmpLong = object.getId()) > 0) {
            statement.append(COLUMN_ID + "=" + tmpLong);
            fields++;
        }
        if ((tmpString = object.getTitle()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_TITLE + " = '" + tmpString + "'");
            fields++;
        }
        if ((tmpString = object.getDescription()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_DESCRIPTION + " = '" + tmpString + "'");
            fields++;
        }
        if ((tmpLong = object.getDate()) > 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_DATE + " = " + tmpLong );
            fields++;
        }
        if(object.getObservationcategory()!=null) {
            if ((tmpLong = object.getObservationcategory().getId()) > 0) {
                statement.append(((fields != 0) ? " AND " : "") + COLUMN_OBS_CATEGORYID + " = " + tmpLong);
                fields++;
            }
        }
        if(object.getPlayer()!=null) {
            if ((tmpLong = object.getPlayer().getId()) > 0) {
                statement.append(((fields != 0) ? " AND " : "") + COLUMN_PLAYER_ID + " = " + tmpLong);
                fields++;
            }
        }
        if(object.getGame()!=null) {
            if ((tmpLong = object.getGame().getId()) > 0) {
                statement.append(((fields != 0) ? " AND " : "") + COLUMN_GAME_ID + " = " + tmpLong);
                fields++;
            }
        }
        if(object.getUser()!=null) {
            if ((tmpLong = object.getUser().getId()) > 0) {
                statement.append(((fields != 0) ? " AND " : "") + COLUMN_USER_ID + " = " + tmpLong);
                fields++;
            }
        }

        if (fields > 0) {
            Cursor res = db.rawQuery(statement.toString(), null);
            return res.moveToFirst();
        }
        else
            return false;
    }

    @Override
    public List<Observation> getByCriteria(Observation object) throws GenericDAOException {

        if(object==null)
            return null;

        List<Observation> resObservation = new ArrayList<>();
        int fields = 0;
        String tmpString;
        int tmpInt;
        long tmpLong;

        StringBuilder statement = new StringBuilder("SELECT * FROM "+ TABLE_NAME +" where ");
        if ((tmpLong = object.getId()) > 0) {
            statement.append(COLUMN_ID + "=" + tmpLong);
            fields++;
        }
        if ((tmpString = object.getTitle()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_TITLE + " LIKE '%" + tmpString + "%'");
            fields++;
        }
        if ((tmpString = object.getDescription()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_DESCRIPTION + " LIKE '%" + tmpString + "%'");
            fields++;
        }
        if ((tmpLong = object.getDate()) > 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_DATE + " = " + tmpLong );
            fields++;
        }
            if(object.getObservationcategory()!=null) {
            if ((tmpLong = object.getObservationcategory().getId()) > 0) {
                statement.append(((fields != 0) ? " AND " : "") + COLUMN_OBS_CATEGORYID + " = " + tmpLong);
                fields++;
            }
        }
        if(object.getPlayer()!=null) {
            if ((tmpLong = object.getPlayer().getId()) > 0) {
                statement.append(((fields != 0) ? " AND " : "") + COLUMN_PLAYER_ID + " = " + tmpLong);
                fields++;
            }
        }
        if(object.getObservationcategory()!=null) {
            if ((tmpLong = object.getGame().getId()) > 0) {
                statement.append(((fields != 0) ? " AND " : "") + COLUMN_GAME_ID + " = " + tmpLong);
                fields++;
            }
        }
        if(object.getUser()!=null) {
            if ((tmpLong = object.getUser().getId()) > 0) {
                statement.append(((fields != 0) ? " AND " : "") + COLUMN_USER_ID + " = " + tmpLong);
                fields++;
            }
        }

        if (fields > 0) {

            long id;
            String title;
            String description;
            long date;
            long obsCatId;
            long playerId;
            long gameId;
            long userId;

            Cursor res = db.rawQuery( statement.toString(), null );
            if(res.moveToFirst())

                while(res.isAfterLast() == false) {
                    id = res.getLong(res.getColumnIndex(COLUMN_ID));
                    title = res.getString(res.getColumnIndex(COLUMN_TITLE));
                    description = res.getString(res.getColumnIndex(COLUMN_DESCRIPTION));
                    date = res.getLong(res.getColumnIndex(COLUMN_DATE));
                    obsCatId = res.getLong(res.getColumnIndex(COLUMN_OBS_CATEGORYID));
                    playerId = res.getLong(res.getColumnIndex(COLUMN_PLAYER_ID));
                    gameId = res.getLong(res.getColumnIndex(COLUMN_GAME_ID));
                    userId = res.getLong(res.getColumnIndex(COLUMN_USER_ID));

                    resObservation.add(new Observation(id,title,description,
                            date,obsCategory_dao.getById(obsCatId),
                            player_dao.getById(playerId),
                            user_dao.getById(userId),
                            game_dao.getById(gameId)));
                    res.moveToNext();
                }
        }


        return resObservation;
    }

}
