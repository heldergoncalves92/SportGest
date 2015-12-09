package studentcompany.sportgest.Games;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import studentcompany.sportgest.Players.CreatePlayer_Activity;
import studentcompany.sportgest.Players.DetailsPlayers_Fragment;
import studentcompany.sportgest.Players.ListPlayers_Fragment;
import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Player_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Player;

/**
 * Created by Marcelo on 05-12-2015.
 */
public class CallSquad_Activity extends AppCompatActivity{

    private Player_DAO playerDao;
    private List<Player> players;
    private int currentPos = -1;
    private Menu mOptionsMenu;

    Button button;
    ListView listView;
    ArrayAdapter<String> adapter;

    private DialogFragment mDialog;
    private FragmentManager mFragmentManager;
    private ListPlayers_Fragment mListPlayer = new ListPlayers_Fragment();
    private DetailsPlayers_Fragment mDetailsPlayer = new DetailsPlayers_Fragment();
    private static final String TAG = "CREATE_CALLSQUAD_ACTIVITY";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_squal_call);


        //this.testPlayers();
        try {
            playerDao = new Player_DAO(getApplicationContext());
            players = playerDao.getAll();
            if(players.isEmpty()) {
                insertUserTest(playerDao);
                players = playerDao.getAll();
            }

            findViewsById();
            //select.........
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, getNamesList(players));
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            listView.setAdapter(adapter);

            //mListPlayer.setPlayerList(getNamesList(players));

        } catch (GenericDAOException e) {
            e.printStackTrace();
        }


        //button.setOnClickListener((View.OnClickListener) this);


        // Get a reference to the FragmentManager
        mFragmentManager = getSupportFragmentManager();

        // Start a new FragmentTransaction
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        // Add the TitleFragment to the layout
        //fragmentTransaction.add(R.id.title_fragment_container , mListPlayer);
        fragmentTransaction.add(R.id.detail_fragment_container, mDetailsPlayer);

        fragmentTransaction.commit();
    }
    private void findViewsById() {
        listView = (ListView) findViewById(R.id.listplayer);
        //button = (Button) findViewById(R.id.testbutton);
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

    public void itemSelect(int position) {
        Player player = players.get(position);

        if(player != null){
            if(currentPos == -1) {
                MenuItem item = mOptionsMenu.findItem(R.id.action_del);
                item.setVisible(true);
            }

            currentPos = position;
            mDetailsPlayer.showPlayer(player);
        }
    }

    /************************************
     ****       Menu Functions       ****
     ************************************/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mOptionsMenu = menu;
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
}
