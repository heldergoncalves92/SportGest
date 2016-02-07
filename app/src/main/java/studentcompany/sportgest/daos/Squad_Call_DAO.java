package studentcompany.sportgest.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import studentcompany.sportgest.daos.db.MyDB;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Game;
import studentcompany.sportgest.domains.Player;
import studentcompany.sportgest.domains.Team;

public  class Squad_Call_DAO extends GenericPairDAO<Player,Game> implements IGenericPairDAO<Player,Game> {
    //Database name
    private SQLiteDatabase db;

    //Dependencies DAOs
    private Player_DAO player_dao;
    private Game_DAO game_dao;

    //Table names
    public static final String TABLE_NAME         = "SQUAD_CALL";

    //Table columns
    public static final String COLUMN_PLAYER_ID = "PLAYER_ID";
    public static final String COLUMN_GAME_ID = "GAME_ID";

    //Create table
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_PLAYER_ID + " INTEGER NOT NULL, " +
            COLUMN_GAME_ID + " INTEGER NOT NULL, " +
            "PRIMARY KEY (" + COLUMN_GAME_ID + ", " + COLUMN_PLAYER_ID + "), " +
            "FOREIGN KEY(" + COLUMN_GAME_ID + ") REFERENCES " + Game_DAO.TABLE_NAME + "(" + Game_DAO.COLUMN_ID + "), " +
            "FOREIGN KEY(" + COLUMN_PLAYER_ID + ") REFERENCES " + Player_DAO.TABLE_NAME + "(" + Player_DAO.COLUMN_ID + "));";

    //Drop table
    public static  final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + "; ";

    public Squad_Call_DAO(Context context) {
        this.db = MyDB.getInstance(context).db;
        this.player_dao = new Player_DAO(context);
        this.game_dao = new Game_DAO(context);
    }

    @Override
    public List<Pair<Player, Game>> getAll() throws GenericDAOException {
        //aux variables;
        ArrayList<Pair<Player, Game>> resList = new ArrayList<>();
        long gameId;
        long playerId;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME, null );
        res.moveToFirst();

        //Parse data
        while(res.isAfterLast() == false) {
            playerId = res.getLong(res.getColumnIndex(COLUMN_PLAYER_ID));
            gameId = res.getLong(res.getColumnIndex(COLUMN_GAME_ID));
            resList.add(
                    new Pair<>(
                            player_dao.getById(playerId),
                            game_dao.getById(gameId)));
            res.moveToNext();
        }
        return resList;
    }

    @Override
    public List<Game> getByFirstId(long id) throws GenericDAOException {
        //aux variables;
        ArrayList<Game> resList = new ArrayList<>();
        long gameId;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_PLAYER_ID + "=" + id, null );
        res.moveToFirst();

        //Parse data
        while(res.isAfterLast() == false) {
            gameId = res.getLong(res.getColumnIndex(COLUMN_GAME_ID));
            resList.add(game_dao.getById(gameId));
            res.moveToNext();
        }

        return resList;
    }

    @Override
    public List<Player> getBySecondId(long id) throws GenericDAOException {
        //aux variables;
        ArrayList<Player> resList = new ArrayList<>();
        long playerId;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_GAME_ID + "=" + id, null );
        res.moveToFirst();

        //Parse data
        while(res.isAfterLast() == false) {
            playerId = res.getLong(res.getColumnIndex(COLUMN_PLAYER_ID));
            resList.add(player_dao.getById(playerId));
            res.moveToNext();
        }

        return resList;

    }

    @Override
    public long insert(Pair<Player, Game> object) throws GenericDAOException {
        if (object == null)
            return -1;

        if (object.getFirst() == null || object.getSecond() == null || object.getFirst().getId() <= 0 || object.getSecond().getId() <= 0)
            return -1;
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PLAYER_ID, object.getFirst().getId());
        contentValues.put(COLUMN_GAME_ID, object.getSecond().getId());

        return db.insert(TABLE_NAME, null, contentValues);
    }

    @Override
    public boolean delete(Pair<Player, Game> object) throws GenericDAOException {
        if (object == null)
            return false;

        if (object.getFirst() == null || object.getSecond() == null)
            return false;

        return db.delete(TABLE_NAME,
                COLUMN_PLAYER_ID + " = ? AND " + COLUMN_GAME_ID + " = ? ",
                new String[] { Long.toString(object.getFirst().getId()), Long.toString(object.getSecond().getId()) })  > 0;

    }

    @Override
    public boolean exists(Pair<Player, Game> object) throws GenericDAOException {
        if (object == null)
            return false;

        if (object.getFirst() == null || object.getSecond() == null)
            return false;

        StringBuilder statement = new StringBuilder("SELECT * FROM " + TABLE_NAME + " where ");
        statement.append(COLUMN_PLAYER_ID).append("=").append(object.getFirst().getId());
        statement.append(" AND ").append(COLUMN_GAME_ID).append("=").append(object.getSecond().getId());

        Cursor res = db.rawQuery(statement.toString(), null);
        return res.moveToFirst();
    }

    public List<Player> getPlayersBy_GameID(long gameId) throws GenericDAOException{

        int number;
        long id,team;
        String nickname, name, photo;
        ArrayList<Player> players = null;

     /*   String sql = "SELECT * FROM " + Player_DAO.TABLE_NAME + " AS PL, " + Squad_Call_DAO.TABLE_NAME
                + " AS SQ WHERE SQ." + Squad_Call_DAO.COLUMN_GAME_ID + "=" + gameId
                + " AND SQ." + Squad_Call_DAO.COLUMN_PLAYER_ID + "=PL." + Player_DAO.COLUMN_ID;
*/
        String sql = "SELECT PL.* FROM " + Player_DAO.TABLE_NAME + " AS PL INNER JOIN " + Squad_Call_DAO.TABLE_NAME
                + " ON " + Squad_Call_DAO.COLUMN_GAME_ID + "=" + gameId;



        //Query
        Cursor res = db.rawQuery(sql, null);
        res.moveToFirst();

        if(res.isAfterLast() == false){
            players = new ArrayList<Player>();

            //Parse data
            while(res.isAfterLast() == false) {
                id = res.getLong(res.getColumnIndex(Player_DAO.COLUMN_ID));
                nickname = res.getString(res.getColumnIndex(Player_DAO.COLUMN_NICKNAME));
                name = res.getString(res.getColumnIndex(Player_DAO.COLUMN_NAME));
                photo= res.getString(res.getColumnIndex(Player_DAO.COLUMN_PHOTO));
                number= res.getInt(res.getColumnIndex(Player_DAO.COLUMN_NUMBER));
                team=res.getLong(res.getColumnIndex(Player_DAO.COLUMN_TEAM_ID));


                players.add(new Player(id,nickname,name,"","","",0,0,"","",photo,"","",number, new Team(team),null));
                res.moveToNext();
            }
        }

        return players;
    }
}
