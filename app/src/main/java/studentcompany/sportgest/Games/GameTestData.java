package studentcompany.sportgest.Games;

import android.content.Context;

import java.util.logging.Level;
import java.util.logging.Logger;

import studentcompany.sportgest.daos.Game_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Game;
import studentcompany.sportgest.domains.Team;

public class GameTestData {

    Game_DAO game_dao;
    //Game_DAO attribute_dao;
    //Attribute_Exercise_DAO attribute_exercise_dao;

    public GameTestData(Context context) {
        game_dao = new Game_DAO(context);
        //attribute_dao = new Attribute_DAO(context);
        //attribute_exercise_dao = new Attribute_Exercise_DAO(context);
        try {
            game_dao.insert(new Game( new Team(1),new Team(2), 0, "A - B", 3, 1, 40f));
            game_dao.insert(new Game( new Team(1),new Team(3), 0, "A - C", 3, 1, 40f));
            game_dao.insert(new Game( new Team(3),new Team(2), 0, "C - B", 3, 1, 40f));
            game_dao.insert(new Game( new Team(4),new Team(2), 0, "D - B", 3, 1, 40f));
            game_dao.insert(new Game( new Team(4),new Team(1), 0, "D - A", 3, 1, 40f));
            }catch (GenericDAOException ex){
            System.err.println(GameTestData.class.getName() + " [WARNING] " + ex.toString());
            Logger.getLogger(GameTestData.class.getName()).log(Level.WARNING, null, ex);
            ex.printStackTrace();
        }
    }
}
