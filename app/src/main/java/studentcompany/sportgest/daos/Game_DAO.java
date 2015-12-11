package studentcompany.sportgest.daos;
//TODO methods

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import studentcompany.sportgest.daos.db.MyDB;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Game;
import studentcompany.sportgest.domains.Team;

public class Game_DAO extends GenericDAO<Game> implements IGenericDAO<Game>{
    //Database name
    private SQLiteDatabase db;
    //Dependencies DAOs
    private Team_DAO       team_dao;

    //Table names
    public static final String TABLE_NAME                   = "GAME";

    //Table columns
    public static final String COLUMN_ID                    = "ID";
    public static final String COLUMN_HOME_TEAMID           = "HOME_TEAMID";
    public static final String COLUMN_VISITOR_TEAMID        = "VISITOR_TEAMID";
    public static final String COLUMN_DATE                  = "\"DATE\"";
    public static final String COLUMN_REPORT                = "REPORT";
    public static final String COLUMN_HOME_SCORE            = "HOME_SCORE";
    public static final String COLUMN_VISITOR_SCORE         = "VISITOR_SCORE";
    public static final String COLUMN_DURATION              = "DURATION";

    //Create table
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (\n" +
                COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, \n" +
                COLUMN_HOME_TEAMID + " INTEGER NOT NULL, \n" +
                COLUMN_VISITOR_TEAMID + " INTEGER NOT NULL, \n" +
                COLUMN_DATE + " INTEGER NOT NULL, \n" +
                COLUMN_REPORT + " TEXT, \n" +
                COLUMN_HOME_SCORE + " INTEGER, \n" +
                COLUMN_VISITOR_SCORE + " INTEGER, \n" +
                COLUMN_DURATION + " REAL NOT NULL, \n" +
                "FOREIGN KEY(" + COLUMN_HOME_TEAMID + ") REFERENCES TEAM(ID), \n" +
                "FOREIGN KEY(" + COLUMN_VISITOR_TEAMID + ") REFERENCES TEAM(ID));\n";

    //Drop table
    public static  final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + "; ";

    public Game_DAO(Context context) {
        this.db = MyDB.getInstance(context).db;
        this.team_dao = new Team_DAO(context);
    }

    @Override
    public List<Game> getAll() throws GenericDAOException {
        //aux variables
        ArrayList<Game> resGame = new ArrayList<>();
        long id;
        long home_team;
        long visitor_team;
        int date;
        String report;
        int home_score;
        int visitor_score;
        float duration;

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME, null );
        res.moveToFirst();

        //Parse data
        while(res.isAfterLast() == false) {
            id = res.getLong(res.getColumnIndex(COLUMN_ID));
            home_team = res.getLong(res.getColumnIndex(COLUMN_HOME_TEAMID));
            visitor_team = res.getLong(res.getColumnIndex(COLUMN_VISITOR_TEAMID));
            date = res.getInt(res.getColumnIndex(COLUMN_DATE));
            report = res.getString(res.getColumnIndex(COLUMN_REPORT));
            home_score = res.getInt(res.getColumnIndex(COLUMN_HOME_SCORE));
            visitor_score = res.getInt(res.getColumnIndex(COLUMN_VISITOR_SCORE));
            duration = res.getFloat(res.getColumnIndex(COLUMN_DURATION));

            //if required filed are valid add object to the list
            if(home_team >= 0 && visitor_team >= 0 && date > 0 && duration > 0 ){
                resGame.add(new Game(id, team_dao.getById(home_team), team_dao.getById(visitor_team), date, report,
                        home_score, visitor_score, duration));
            }
            res.moveToNext();
        }
          res.close();

        return resGame;
    }

    @Override
    public Game getById(long id) throws GenericDAOException {
        //aux variables;
        Game resGame;
        long home_team;
        long visitor_team;
        int date;
        String report;
        int home_score;
        int visitor_score;
        float duration;

        if(id < 0){
            return null;
        }

        //Query
        Cursor res = db.rawQuery( "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + "=" + id, null );
        res.moveToFirst();

        //Parse data
        if(res.getCount()==1){
            id = res.getInt(res.getColumnIndex(COLUMN_ID));
            home_team = res.getLong(res.getColumnIndex(COLUMN_HOME_TEAMID));
            visitor_team = res.getLong(res.getColumnIndex(COLUMN_VISITOR_TEAMID));
            date = res.getInt(res.getColumnIndex(COLUMN_DATE));
            report = res.getString(res.getColumnIndex(COLUMN_REPORT));
            home_score = res.getInt(res.getColumnIndex(COLUMN_HOME_SCORE));
            visitor_score = res.getInt(res.getColumnIndex(COLUMN_VISITOR_SCORE));
            duration = res.getFloat(res.getColumnIndex(COLUMN_DURATION));

            if(home_team <0 || visitor_team < 0) {
                resGame = new Game(id, team_dao.getById(home_team), team_dao.getById(visitor_team), date, report,
                        home_score, visitor_score, duration);
                res.close();
                return resGame;
            }
            else{
                    resGame = new Game(id,team_dao.getById(home_team),team_dao.getById(visitor_team), date, report,
                            home_score,visitor_score, duration);
                    res.close();
                    return resGame;
                }
        }
        else
        {
            res.close();
            return null;
        }
    }

    @Override
    public long insert(Game object) throws GenericDAOException {

        //validate fields not null
        if(object==null ||
                object.getDate() <= 0 || object.getDuration() <= 0 ||
                object.getHome_team() == null || object.getVisitor_team() == null ||
                object.getHome_team().getId() < 0 || object.getVisitor_team().getId() < 0)
            return -1;

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_HOME_TEAMID,  object.getHome_team().getId());
        contentValues.put(COLUMN_VISITOR_TEAMID,  object.getVisitor_team().getId());
        contentValues.put(COLUMN_DATE, object.getDate());
        contentValues.put(COLUMN_REPORT, object.getReport());
        contentValues.put(COLUMN_DATE, object.getDate());
        contentValues.put(COLUMN_REPORT, object.getReport());
        contentValues.put(COLUMN_HOME_SCORE, object.getHome_score());
        contentValues.put(COLUMN_VISITOR_SCORE, object.getVisitor_score());
        contentValues.put(COLUMN_DURATION, object.getDuration());

        return db.insert(TABLE_NAME, null, contentValues);
    }

    @Override
    public boolean delete(Game object) throws GenericDAOException {
        if(object==null)
            return false;

        return deleteById(object.getId());
    }

    @Override
    public boolean deleteById(long id){
        return db.delete(TABLE_NAME,
                COLUMN_ID + " = ? ",
                new String[] { Long.toString(id) }) > 0;
    }
    @Override
    public boolean update(Game object) throws GenericDAOException {
        //validate fields not null
        if(object==null ||
                object.getDate() <= 0 || object.getDuration() <= 0 ||
                object.getHome_team() == null || object.getVisitor_team() == null ||
                object.getHome_team().getId() < 0 || object.getVisitor_team().getId() < 0)
            return false;

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_HOME_TEAMID,  object.getHome_team().getId());
        contentValues.put(COLUMN_VISITOR_TEAMID,  object.getVisitor_team().getId());
        contentValues.put(COLUMN_DATE, object.getDate());
        contentValues.put(COLUMN_REPORT, object.getReport());
        contentValues.put(COLUMN_DATE, object.getDate());
        contentValues.put(COLUMN_REPORT, object.getReport());
        contentValues.put(COLUMN_HOME_TEAMID, object.getHome_score());
        contentValues.put(COLUMN_VISITOR_TEAMID, object.getVisitor_score());
        contentValues.put(COLUMN_DURATION, object.getDuration());

        return db.update(TABLE_NAME,
                contentValues,
                COLUMN_ID + " = ? ",
                new String[]{Long.toString(object.getId())}) >0;
    }

    @Override
    public int numberOfRows(){
        return (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
    }

    @Override
    public boolean exists(Game object) throws GenericDAOException {

        if(object==null)
            return false;

        int fields = 0;
        String tmpString;
        int tmpInt;
        long tmpLong;
        float tmpFloat;

        StringBuilder statement = new StringBuilder("SELECT * FROM "+ TABLE_NAME +" where ");
        if ((tmpLong = object.getId()) >= 0) {
            statement.append(COLUMN_ID + "=" + tmpLong);
            fields++;
        }
        if ((tmpLong = object.getHome_team().getId()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_HOME_TEAMID + " = " + tmpLong );
            fields++;
        }
        if ((tmpLong = object.getVisitor_team().getId()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_VISITOR_TEAMID + " = " + tmpLong );
            fields++;
        }
        if ((tmpInt = object.getDate()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_DATE + " = " + tmpInt );
            fields++;
        }
        if ((tmpString = object.getReport()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_REPORT + " = '" + tmpString + "'");
            fields++;
        }
        if ((tmpInt = object.getHome_score()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_HOME_SCORE + " = " + tmpInt );
            fields++;
        }
        if ((tmpInt = object.getVisitor_score()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_VISITOR_SCORE + " = " + tmpInt );
            fields++;
        }if ((tmpFloat = object.getDuration()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_DURATION + " = " + tmpFloat );
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
    public List<Game> getByCriteria(Game object) throws GenericDAOException {

        if(object==null)
            return null;

        List<Game> resGame = new ArrayList<>();
        int fields = 0;
        String tmpString;
        int tmpInt;
        long tmpLong;
        float tmpFloat;

        StringBuilder statement = new StringBuilder("SELECT * FROM "+ TABLE_NAME +" where ");
        if ((tmpLong = object.getId()) >= 0) {
            statement.append(COLUMN_ID + "=" + tmpLong);
            fields++;
        }
        if ((tmpLong = object.getHome_team().getId()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_HOME_TEAMID + " = " + tmpLong );
            fields++;
        }
        if ((tmpLong = object.getVisitor_team().getId()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_VISITOR_TEAMID + " = " + tmpLong );
            fields++;
        }
        if ((tmpInt = object.getDate()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_DATE + " = " + tmpInt );
            fields++;
        }
        if ((tmpString = object.getReport()) != null) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_REPORT + " LIKE '%" + tmpString + "%'");
            fields++;
        }
        if ((tmpInt = object.getHome_score()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_HOME_SCORE + " = " + tmpInt );
            fields++;
        }
        if ((tmpInt = object.getVisitor_score()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_VISITOR_SCORE + " = " + tmpInt );
            fields++;
        }if ((tmpFloat = object.getDuration()) >= 0) {
            statement.append(((fields != 0) ? " AND " : "") + COLUMN_DURATION + " = " + tmpFloat );
            fields++;
        }

        if (fields > 0) {

            long id;
            long home_teamid;
            long visitor_teamid;
            int date;
            String report;
            int home_score;
            int visitor_score;
            float duration;

            Cursor res = db.rawQuery( statement.toString(), null );
            if(res.moveToFirst())

                while(res.isAfterLast() == false) {
                    id = res.getInt(res.getColumnIndex(COLUMN_ID));
                    home_teamid = res.getLong(res.getColumnIndex(COLUMN_HOME_TEAMID));
                    visitor_teamid = res.getLong(res.getColumnIndex(COLUMN_VISITOR_TEAMID));
                    date = res.getInt(res.getColumnIndex(COLUMN_DATE));
                    report = res.getString(res.getColumnIndex(COLUMN_REPORT));
                    home_score = res.getInt(res.getColumnIndex(COLUMN_HOME_SCORE));
                    visitor_score = res.getInt(res.getColumnIndex(COLUMN_VISITOR_SCORE));
                    duration = res.getFloat(res.getColumnIndex(COLUMN_DURATION));
                    resGame.add(new Game(id,team_dao.getById(home_teamid),team_dao.getById(visitor_teamid), date, report,
                            home_score,visitor_score, duration));
                    res.moveToNext();
                }
        }


        return resGame;
    }

    void insertionTest(){

        HashMap<String, Long> res = new HashMap<>();

        try {
            /* VALID ENTRIES */
            team_dao.insert(new Team(-1, "SuperTeam1", "SuperTeamDescription1", "SuperLogoURL", 2015, 1));
            team_dao.insert(new Team(-1, "SuperTeam2", "SuperTeamDescription2", "SuperLogoURL", 2015, 1));
            team_dao.insert(new Team(-1, "SuperTeam3", "SuperTeamDescription3", "SuperLogoURL", 2015, 1));
            team_dao.insert(new Team(-1, "SuperTeam4", "SuperTeamDescription4", "SuperLogoURL", 2015, 1));
            res.put("Valid 1vs2", insert(new Game(-1, team_dao.getById(1), team_dao.getById(2), 100000, "Report1", 1, 2, 40)));
            //note: it is possible to have a game between the same team (friendly?)
            res.put("Valid 1vs1", insert(new Game(-1, team_dao.getById(1), team_dao.getById(1), 100000, "Report2", 1, 2, 40)));
            res.put("Valid 3vs1", insert(new Game(-1, team_dao.getById(3), team_dao.getById(1), 100000, "Report3", 1, 2, 40)));
            res.put("Valid 2vs4", insert(new Game(-1, team_dao.getById(2), team_dao.getById(4), 100000, "Report3", 1, 2, 40)));

            /* INVALID ENTRIES */
            //team object null
            res.put("Invalid team object", insert(new Game(-1, null, null, 100000, "Report4", 1, 2, 40)));
            //date invalid
            res.put("Invalid date", insert(new Game(-1, null, null, 0, "Report5", 1, 2, 40)));
            //duration invalid
            res.put("Invalid duration", insert(new Game(-1, null, null, 100000, "Report5", 1, 2, 0)));

        } catch (GenericDAOException ex){
            ex.printStackTrace();
        }

        //print results
        System.out.println("insertionTest");
        for(Map.Entry<String, Long> o: res.entrySet()){
            System.out.println("->K:" + o.getKey() + " Value:" + o.getValue());
        }
    }

    void getTest(){
        try {
            if(numberOfRows() == 0){
                insertionTest();
            }
        /* GET ALL */
            ArrayList<Game> res = (ArrayList) getAll();

            //print results
            System.out.println("insertionTest");
            for(int i = 0; i<res.size(); i++){
                System.out.println("->K:" + i + " Value:" + res.get(i).toString());
            }

        } catch (GenericDAOException ex){
            ex.printStackTrace();
        }
    }

    void getByCriteriaTest(){
        HashMap<String, Game> g = new HashMap<>();
        HashMap<String, ArrayList<Game>> res = new HashMap<>();
        try {
            if(numberOfRows() == 0){
                insertionTest();
            }
            /* VALID ENTRIES */
            g.put("by team1",
            new Game(-1, team_dao.getById(1), null, -1, "", -1, -1, -1));
            g.put("by team2",
            new Game(-1, null, team_dao.getById(2), -1, "", -1, -1, -1));
            g.put("by date",
            new Game(-1,null, null, 1000, "", -1, -1, -1));
            g.put("by report",
            new Game(-1,null, null, -1, "Report1", -1, -1, -1));
            g.put("by score home team",
            new Game(-1,null, null, -1, "", 1, -1, -1));
            g.put("by score home team",
            new Game(-1,null, null, -1, "", -1, 2, -1));
            g.put("by duration",
            new Game(-1,null, null, -1, "", -1, -1, 40));

            /* INVALID ENTRIES */
            g.put("by unknown team",
            new Game(-1,new Team(10, "", "", "", -1, -1), null, -1, "", -1, -1, -1));
            g.put("by non existent date",
            new Game(-1,null, null, 99999999, "", -1, -1, -1));
            g.put("by non existent report",
            new Game(-1,null, null, -1, "Report99", -1, -1, -1));
            g.put("by non existent score home team",
            new Game(-1,null, null, -1, "", 9, -1, -1));
            g.put("by non existent score home team",
            new Game(-1,null, null, -1, "", -1, 9, -1));
            g.put("by non existent duration",
            new Game(-1,null, null, -1, "", -1, -1, 99));

            for(Map.Entry<String, Game> o: g.entrySet()){
                res.put(o.getKey(), (ArrayList)getByCriteria(o.getValue()));
            }

            //print results
            System.out.println("insertionTest");
            for(int i = 0; i<res.size(); i++){
                System.out.println("->K:" + i + " Results:" + res.get(i).size());
            }

        } catch (GenericDAOException ex){
            ex.printStackTrace();
        }
    }

    void updateTest(){

    }
}
