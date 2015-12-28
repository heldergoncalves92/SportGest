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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Player_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Player;
import studentcompany.sportgest.domains.Team;

public class Player_Activity_ListView extends AppCompatActivity implements Player_Fragment_List.OnItemSelected {

    private Player_DAO playerDao;
    private List<Player> players;
    private int currentPos = -1;
    private Menu mOptionsMenu;

    private int baseTeamID;
    private DialogFragment mDialog;
    private FragmentManager mFragmentManager;
    private Player_Fragment_List mListPlayer = new Player_Fragment_List();
    private Player_Fragment_Details mDetailsPlayer = new Player_Fragment_Details();
    private static final String TAG = "PLAYERS_LIST_ACTIVITY";

    private final int EDIT_TAG = 19;
    private final int CREATE_TAG = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_activity_list_view);

        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();

            if (extras != null){
                baseTeamID = extras.getInt("TEAM");

            } else
                baseTeamID = 0;

        } else {
            baseTeamID = savedInstanceState.getInt("baseTeamID");
            currentPos = savedInstanceState.getInt("currentPos");
        }

        try {
            playerDao = new Player_DAO(getApplicationContext());
            players = playerDao.getByCriteria(new Player(new Team(baseTeamID)));
            //players = playerDao.getAll();
            if(players.isEmpty()) {

                noElems();
                //insertUserTest(playerDao);
                //players = playerDao.getAll();
            }
            mListPlayer.setList(players);

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


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("baseTeamID", baseTeamID);
        outState.putInt("currentPos", currentPos);
    }

    public List<String> getNamesList(List<Player> playerList){

        ArrayList<String> list = new ArrayList<String>();

        for(Player p: playerList)
            list.add(p.getName());

        return list;
    }

    public void noElems(){

        LinearLayout l = (LinearLayout)findViewById(R.id.linear);
        l.setVisibility(View.GONE);

        TextView t= (TextView)findViewById(R.id.without_elems);
        t.setVisibility(View.VISIBLE);
    }

    public void removePlayer(){
        mDetailsPlayer.clearDetails();
        mListPlayer.removeItem(currentPos);

        playerDao.deleteById(players.get(currentPos).getId());
        players.remove(currentPos);

        currentPos = -1;
        MenuItem item = mOptionsMenu.findItem(R.id.action_del);
        item.setVisible(false);

        if(players.isEmpty())
            noElems();
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

                item = mOptionsMenu.findItem(R.id.action_edit);
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

        //To restore state on Layout Rotation
        if(currentPos != -1) {
            MenuItem item = mOptionsMenu.findItem(R.id.action_del);
            item.setVisible(true);

            item = mOptionsMenu.findItem(R.id.action_edit);
            item.setVisible(true);
            mDetailsPlayer.showPlayer(players.get(currentPos));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(this, Player_Activity_Create.class);
                startActivityForResult(intent, CREATE_TAG);
                return true;

            case R.id.action_del:
                mDialog = AlertToDelete_DialogFragment.newInstance();
                mDialog.show(mFragmentManager, "Alert");
                return true;

            case R.id.action_edit:
                Intent intent2 = new Intent(this, Player_Activity_Edit.class);
                intent2.putExtra("id", players.get(currentPos).getId());
                startActivityForResult(intent2, EDIT_TAG);
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
                                    Player_Activity_ListView activity = (Player_Activity_ListView) getActivity();
                                    activity.DialogDismiss();
                                }
                            })
                    .setPositiveButton(res.getString(R.string.positive_answer),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Player_Activity_ListView activity = (Player_Activity_ListView) getActivity();
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
            Player p1 = new Player("Jocka", "João Alberto", "Portuguesa", "Solteiro", "1222-1-23", 176 ,70.4f , "Travessa do Morro", "Masculino", "default.jpg", "player1@email.com", "Direito", 2, new Team(1), null);
            Player p2 = new Player("Fabinho", "Fábio Gomes", "Portuguesa", "Solteiro", "1222-1-23", 170 ,83 , "Travessa do Morro", "Masculino", "default.jpg", "player1@email.com", "Direito", 4, new Team(1), null);
            Player p3 = new Player("Jorge D.", "Jorge Duarte", "Portuguesa", "Solteiro", "1231-2-3", 180 ,73.6f , "Travessa do Morro", "Masculino", "default.jpg", "player1@email.com", "Esquerdo", 3, new Team(1), null);
            Player p4 = new Player("Nel", "Manuel Arouca", "Portuguesa", "Solteiro", "1231-2-3", 194 ,69.69f , "Travessa do Morro", "Masculino", "default.jpg", "player1@email.com", "Direito", 1, new Team(2), null);

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

        Player p1 = new Player(1,"Jocka", "João Alberto", "Portuguesa", "Solteiro", "1231-2-3", 176 ,70.4f , "Travessa do Morro", "Masculino", "default.jpg", "player1@email.com", "Direito", 2, new Team(1), null);
        Player p2 = new Player(2,"Fabinho", "Fábio Gomes", "Portuguesa", "Solteiro", "1231-2-3", 170 ,83 , "Travessa do Morro", "Masculino", "default.jpg", "player1@email.com", "Direito", 4, new Team(1), null);
        Player p3 = new Player(3,"Jorge D.", "Jorge Duarte", "Portuguesa", "Solteiro", "1231-2-3", 180 ,73.6f , "Travessa do Morro", "Masculino", "default.jpg", "player1@email.com", "Esquerdo", 3, new Team(1), null);
        Player p4 = new Player(4,"Nel", "Manuel Arouca", "Portuguesa", "Solteiro", "1231-2-3", 194 ,69.69f , "Travessa do Morro", "Masculino", "default.jpg", "player1@email.com", "Direito", 1, new Team(2), null);

        players = new ArrayList<Player>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);

        mListPlayer.setList(players);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Player player = null;
        if (requestCode == EDIT_TAG) {
            if(resultCode == 1){
                try {
                    player=playerDao.getById(players.get(currentPos).getId());
                    players.set(currentPos,player);
                    mListPlayer.updatePosition(player, currentPos);
                    mDetailsPlayer.showPlayer(player);

                    Toast.makeText(getApplicationContext(), R.string.updated, Toast.LENGTH_SHORT).show();
                } catch (GenericDAOException e) {
                    e.printStackTrace();
                }
            }
            if(resultCode == 2){
                Toast.makeText(getApplicationContext(), R.string.something_wrong, Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == CREATE_TAG) {
            if(resultCode == 1){
                try {
                    /*Bundle bundle = data.getExtras();
                    long id = (long) bundle.get("id");
                    int idToSearch = (int) (id + 0);
                    player=playerDao.getById(idToSearch);
                    System.out.println(player);
                    players.add(player);
                    mDetailsPlayer.showPlayer(player);
                    */

                    players = playerDao.getByCriteria(new Player(new Team(baseTeamID)));
                    mListPlayer.updateList(players);

                    Toast.makeText(getApplicationContext(), R.string.inserted, Toast.LENGTH_SHORT).show();
                } catch (GenericDAOException e) {
                    e.printStackTrace();
                }
            }
            if(resultCode == 2){
                Toast.makeText(getApplicationContext(), R.string.something_wrong, Toast.LENGTH_SHORT).show();
            }
        }
    }


}
