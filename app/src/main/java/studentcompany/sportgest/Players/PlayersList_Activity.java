package studentcompany.sportgest.Players;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import studentcompany.sportgest.R;
import studentcompany.sportgest.Users.CreateUser_Activity;
import studentcompany.sportgest.daos.Player_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Player;
import studentcompany.sportgest.domains.Team;

public class PlayersList_Activity extends AppCompatActivity implements ListPlayers_Fragment.OnItemSelected {

    private Player_DAO playerDao;
    private List<Player> players;
    private int currentPos = -1;
    private Menu mOptionsMenu;


    private int baseTeamID;
    private DialogFragment mDialog;
    private FragmentManager mFragmentManager;
    private ListPlayers_Fragment mListPlayer = new ListPlayers_Fragment();
    private DetailsPlayers_Fragment mDetailsPlayer = new DetailsPlayers_Fragment();
    private static final String TAG = "PLAYERS_LIST_ACTIVITY";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players_list);

        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();

            if (extras != null){
                baseTeamID = extras.getInt("TEAM");

            } else
                baseTeamID = 0;
        }

        try {
            playerDao = new Player_DAO(getApplicationContext());
            players = playerDao.getByCriteria(new Player(new Team(baseTeamID)));
            if(players.isEmpty()) {

                LinearLayout l = (LinearLayout)findViewById(R.id.linear);
                l.setVisibility(View.GONE);

                TextView t= (TextView)findViewById(R.id.without_elems);
                t.setVisibility(View.VISIBLE);
                //insertUserTest(playerDao);
                //players = playerDao.getByCriteria(new Player(new Team(baseTeamID)));
                //players = playerDao.getAll();
            }
            mListPlayer.setList(getNamesList(players));

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

    public void removePlayer(){
        mDetailsPlayer.clearDetails();
        mListPlayer.removeItem(currentPos);

        playerDao.deleteById(players.get(currentPos).getId());
        players.remove(currentPos);

        currentPos = -1;
        MenuItem item = mOptionsMenu.findItem(R.id.action_del);
        item.setVisible(false);
    }

    /************************************
     ****     Listener Functions     ****
     ************************************/


    public void itemSelected(int position) {
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

            case R.id.action_del:
                mDialog = AlertToDelete_DialogFragment.newInstance();
                mDialog.show(mFragmentManager,"Alert");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /************************************
     ****      Dialog Functions      ****
     ************************************/

    public void DialogDismiss(){
        mDialog.dismiss();
    }

    public static class AlertToDelete_DialogFragment extends DialogFragment {

        public static AlertToDelete_DialogFragment newInstance(){
            return new AlertToDelete_DialogFragment();
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            Resources res = getResources();

            return new AlertDialog.Builder(getActivity())
                    .setMessage(res.getString(R.string.are_you_sure))
                    .setCancelable(false)
                    .setNegativeButton(res.getString(R.string.negative_answer),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    PlayersList_Activity activity = (PlayersList_Activity) getActivity();
                                    activity.DialogDismiss();
                                }
                            })
                    .setPositiveButton(res.getString(R.string.positive_answer),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    PlayersList_Activity activity = (PlayersList_Activity) getActivity();
                                    activity.DialogDismiss();
                                    activity.removePlayer();
                                }
                            }).create();
        }
    }

    /************************************
     ****        Test Functions      ****
     ************************************/

    private void insertUserTest(Player_DAO p_dao){

        try {
            Player p1 = new Player("Jocka", "João Alberto", "Portuguesa", "Solteiro", 123123, 176 ,70.4f , "Travessa do Morro", "Masculino", "default.jpg", "player1@email.com", "Direito", 2, new Team(1), null);
            Player p2 = new Player("Fabinho", "Fábio Gomes", "Portuguesa", "Solteiro", 123123, 170 ,83 , "Travessa do Morro", "Masculino", "default.jpg", "player1@email.com", "Direito", 4, new Team(1), null);
            Player p3 = new Player("Jorge D.", "Jorge Duarte", "Portuguesa", "Solteiro", 123123, 180 ,73.6f , "Travessa do Morro", "Masculino", "default.jpg", "player1@email.com", "Esquerdo", 3, new Team(1), null);
            Player p4 = new Player("Nel", "Manuel Arouca", "Portuguesa", "Solteiro", 123123, 194 ,69.69f , "Travessa do Morro", "Masculino", "default.jpg", "player1@email.com", "Direito", 1, new Team(2), null);

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

        Player p1 = new Player(1,"Jocka", "João Alberto", "Portuguesa", "Solteiro", 123123, 176 ,70.4f , "Travessa do Morro", "Masculino", "default.jpg", "player1@email.com", "Direito", 2, new Team(1), null);
        Player p2 = new Player(2,"Fabinho", "Fábio Gomes", "Portuguesa", "Solteiro", 123123, 170 ,83 , "Travessa do Morro", "Masculino", "default.jpg", "player1@email.com", "Direito", 4, new Team(1), null);
        Player p3 = new Player(3,"Jorge D.", "Jorge Duarte", "Portuguesa", "Solteiro", 123123, 180 ,73.6f , "Travessa do Morro", "Masculino", "default.jpg", "player1@email.com", "Esquerdo", 3, new Team(1), null);
        Player p4 = new Player(4,"Nel", "Manuel Arouca", "Portuguesa", "Solteiro", 123123, 194 ,69.69f , "Travessa do Morro", "Masculino", "default.jpg", "player1@email.com", "Direito", 1, new Team(2), null);

        players = new ArrayList<Player>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);

        mListPlayer.setList(getNamesList(players));
    }
}
