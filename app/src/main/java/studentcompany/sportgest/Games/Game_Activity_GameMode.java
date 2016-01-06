package studentcompany.sportgest.Games;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.Date;
import java.util.List;
import java.util.concurrent.SynchronousQueue;

import studentcompany.sportgest.Players.Player_Fragment_List;
import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Game_DAO;
import studentcompany.sportgest.daos.Pair;
import studentcompany.sportgest.daos.Player_DAO;
import studentcompany.sportgest.daos.Squad_Call_DAO;
import studentcompany.sportgest.daos.Team_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Game;
import studentcompany.sportgest.domains.Player;
import studentcompany.sportgest.domains.Team;

public class Game_Activity_GameMode extends AppCompatActivity {

    private List<Player> inGame, onBench;
    private Player_DAO playerDao;
    private Squad_Call_DAO squadCallDao;

    private long baseGameID;

    private FragmentManager mFragmentManager;
    private Player_Fragment_List mList_inGame = new Player_Fragment_List();
    private Player_Fragment_List mList_onBench = new Player_Fragment_List();
    private static final String TAG = "GAME_GAME_MODE_ACTIVITY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity_game_mode);


        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();

            if (extras != null){
                baseGameID = extras.getInt("GAME");

            } else
                baseGameID = -1;

        } else {
            baseGameID = savedInstanceState.getInt("baseGameID");
        }

        squadCallDao = new Squad_Call_DAO(getApplicationContext());

        try {

            List<Pair<Player,Game>> list = squadCallDao.getAll();
            onBench = squadCallDao.getPlayersBy_GameID(baseGameID);


            if(onBench == null){
                //insertTest();
            }


        } catch (GenericDAOException e) {
            e.printStackTrace();
        }


        // Get a reference to the FragmentManager
       /* mFragmentManager = getSupportFragmentManager();

        // Start a new FragmentTransaction
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        // Add the TitleFragment to the layout
        fragmentTransaction.add(R.id.title_fragment_container , mList_inGame);
        fragmentTransaction.add(R.id.title_fragment_container , mList_onBench);

        fragmentTransaction.commit();

        */
    }



    private void insertTest(){
        Team_DAO team_dao = new Team_DAO(getApplicationContext());
        Player_DAO player_dao = new Player_DAO(getApplicationContext());
        Game_DAO game_dao = new Game_DAO(getApplicationContext());
        Squad_Call_DAO squad_call_dao = new Squad_Call_DAO(getApplicationContext());
        long id;
        Player p;

        try {
            Team team = new Team("Santa Maria","Uma equipa fantástica!!","default.jpg",2015,0);
            long teamID = team_dao.insert(team);
            team.setId(teamID);

            Game game = new Game(team,team, new Date().getTime(), "", -1 , -1, 40.0f);
            long gameID = game_dao.insert(game);
            game.setId(gameID);


            p = new Player("Jocka", "João Alberto", "Portuguesa", "Solteiro", "1222-1-23", 176 ,70.4f , "Travessa do Morro", "Masculino", "default.jpg", "player1@email.com", "Direito", 2, team, null);
            id = player_dao.insert(p);
            p.setId(id);
            id = squad_call_dao.insert(new Pair<>(p , game));

            p = new Player("Fabinho", "Fábio Gomes", "Portuguesa", "Solteiro", "1222-1-23", 170 ,83 , "Travessa do Morro", "Masculino", "default.jpg", "player1@email.com", "Direito", 4, team, null);
            id = player_dao.insert(p);
            p.setId(id);
            id = squad_call_dao.insert(new Pair<>(p , game));

            p = new Player("Jorge D.", "Jorge Duarte", "Portuguesa", "Solteiro", "1231-2-3", 180 ,73.6f , "Travessa do Morro", "Masculino", "default.jpg", "player1@email.com", "Esquerdo", 3, team, null);
            id = player_dao.insert(p);
            p.setId(id);
            id = squad_call_dao.insert(new Pair<>(p , game));

            p = new Player("Nel", "Manuel Arouca", "Portuguesa", "Solteiro", "1231-2-3", 194 ,69.69f , "Travessa do Morro", "Masculino", "default.jpg", "player1@email.com", "Direito", 1, team, null);
            id = player_dao.insert(p);
            p.setId(id);
            id = squad_call_dao.insert(new Pair<>(p , game));


        } catch (GenericDAOException e) {
            e.printStackTrace();
        }

    }

}
