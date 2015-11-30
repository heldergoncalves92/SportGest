package studentcompany.sportgest.Players;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import studentcompany.sportgest.R;
import studentcompany.sportgest.Users.CreateUser_Activity;
import studentcompany.sportgest.daos.Player_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Player;

public class PlayersList_Activity extends AppCompatActivity implements ListPlayers_Fragment.OnItemSelected {

    private Player_DAO playerDao;
    private List<Player> players;


    private FragmentManager mFragmentManager;
    private ListPlayers_Fragment mListPlayer = new ListPlayers_Fragment();
    private DetailsPlayers_Fragment mDetailsPlayer = new DetailsPlayers_Fragment();
    private static final String TAG = "PLAYERS_LIST_ACTIVITY";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players_list);

        //this.testPlayers();
        try {
            playerDao = new Player_DAO(getApplicationContext());
            //insertUserTest(playerDao);
            players = playerDao.getAll();
            mListPlayer.setPlayerList(getNamesList(players));

        } catch (GenericDAOException e) {
            e.printStackTrace();
        }


        // Get a reference to the FragmentManager
        mFragmentManager = getSupportFragmentManager();

        // Start a new FragmentTransaction
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        // Add the TitleFragment to the layout
        fragmentTransaction.add(R.id.title_fragment_container , mListPlayer);
        fragmentTransaction.add(R.id.detail_fragment_container, mDetailsPlayer);

        fragmentTransaction.commit();
    }

    public List<String> getNamesList(List<Player> playerList){

        ArrayList<String> list = new ArrayList<String>();

        for(Player p: playerList)
            list.add(p.getName());

        return list;
    }

    /************************************
     ****     Listener Functions     ****
     ************************************/


    public void itemSelected(int position) {
        Player player = players.get(position);

        if(player != null){
            mDetailsPlayer.showPlayer(player);
        }
    }

    /************************************
     ****       Menu Functions       ****
     ************************************/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_users_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(this, CreatePlayer_Activity.class);
                startActivity(intent);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /************************************
     ****        Test Functions      ****
     ************************************/

    private void insertUserTest(Player_DAO p_dao){

        try {
            Player p1 = new Player("Jocka", "João Alberto", "Portuguesa", "Solteiro", 123123, 176 ,70.4f , "Travessa do Morro", "Masculino", "default.jpg", "player1@email.com", "Direito", 2, null, null);
            Player p2 = new Player("Fabinho", "Fábio Gomes", "Portuguesa", "Solteiro", 123123, 170 ,83 , "Travessa do Morro", "Masculino", "default.jpg", "player1@email.com", "Direito", 4, null, null);
            Player p3 = new Player("Jorge D.", "Jorge Duarte", "Portuguesa", "Solteiro", 123123, 180 ,73.6f , "Travessa do Morro", "Masculino", "default.jpg", "player1@email.com", "Esquerdo", 3, null, null);
            Player p4 = new Player("Nel", "Manuel Arouca", "Portuguesa", "Solteiro", 123123, 194 ,69.69f , "Travessa do Morro", "Masculino", "default.jpg", "player1@email.com", "Direito", 1, null, null);

            long id;

            id = p_dao.insert(p1);
            id = p_dao.insert(p2);
            id = p_dao.insert(p3);
            id = p_dao.insert(p4);

        } catch (GenericDAOException e) {
            e.printStackTrace();
        }

    }

    private void testPlayers(){

        Player p1 = new Player(1,"Jocka", "João Alberto", "Portuguesa", "Solteiro", 123123, 176 ,70.4f , "Travessa do Morro", "Masculino", "default.jpg", "player1@email.com", "Direito", 2, null, null);
        Player p2 = new Player(2,"Fabinho", "Fábio Gomes", "Portuguesa", "Solteiro", 123123, 170 ,83 , "Travessa do Morro", "Masculino", "default.jpg", "player1@email.com", "Direito", 4, null, null);
        Player p3 = new Player(3,"Jorge D.", "Jorge Duarte", "Portuguesa", "Solteiro", 123123, 180 ,73.6f , "Travessa do Morro", "Masculino", "default.jpg", "player1@email.com", "Esquerdo", 3, null, null);
        Player p4 = new Player(4,"Nel", "Manuel Arouca", "Portuguesa", "Solteiro", 123123, 194 ,69.69f , "Travessa do Morro", "Masculino", "default.jpg", "player1@email.com", "Direito", 1, null, null);

        players = new ArrayList<Player>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);

        mListPlayer.setPlayerList(getNamesList(players));
    }
}
