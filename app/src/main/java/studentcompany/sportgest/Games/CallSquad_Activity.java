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
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import studentcompany.sportgest.Players.CreatePlayer_Activity;
import studentcompany.sportgest.Players.DetailsPlayers_Fragment;
import studentcompany.sportgest.Players.ListPlayers_Fragment;
import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Game_DAO;
import studentcompany.sportgest.daos.Pair;
import studentcompany.sportgest.daos.Player_DAO;
import studentcompany.sportgest.daos.Squad_Call_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Game;
import studentcompany.sportgest.domains.Player;
import studentcompany.sportgest.domains.Team;

public class CallSquad_Activity extends AppCompatActivity {


    private Player_DAO playerDao;
    private List<Player> players;
    private int currentPos = -1;
    private Menu mOptionsMenu;

    private int baseTeamID;
    private DialogFragment mDialog;
    private FragmentManager mFragmentManager;
    private ListPlayers_Fragment mListPlayer = new ListPlayers_Fragment();
    private DetailsPlayers_Fragment mDetailsPlayer = new DetailsPlayers_Fragment();


    private  ArrayAdapter<String> adapter;
    private  ArrayAdapter<String> adapter1;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_squal_call);

        ListView lvc = (ListView) findViewById(R.id.lvc);

        ListView lvnc = (ListView) findViewById(R.id.lvnc);



        //for tests (insert player)
        try {
            playerDao = new Player_DAO(getApplicationContext());
            players = playerDao.getByCriteria(new Player(new Team(1)));
            if(players.isEmpty()) {

                //noElems();
                insertUserTest(playerDao);
                players = playerDao.getAll();
            }


            ArrayList<String> firstArrayList = new ArrayList<String>(getNamesList(players));
            adapter = new ArrayAdapter<String>( this, android.R.layout.simple_list_item_multiple_choice, firstArrayList);
            lvnc.setAdapter(adapter);

        } catch (GenericDAOException e) {
            e.printStackTrace();
        }

        ArrayList<String> secondArrayList = new ArrayList<String>();
        adapter1 = new ArrayAdapter<String>( this, android.R.layout.simple_list_item_multiple_choice, secondArrayList);
        lvc.setAdapter(adapter1);


        // Get a reference to the FragmentManager
        mFragmentManager = getSupportFragmentManager();

        // Start a new FragmentTransaction
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        // Add the TitleFragment to the layout
        //fragmentTransaction.add(R.id.title_fragment_container , mListPlayer);
        fragmentTransaction.add(R.id.detail_fragment_container, mDetailsPlayer);

        fragmentTransaction.commit();

        lvnc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //display player


                //To change body of implemented methods use File | Settings | File Templates.
                String s = adapter.getItem(i);
                showPlayerSelected(s);

                //ListView check2 = (ListView) findViewById(R.id.lvc);
                //check2.setFocusable(false);

                /*
                adapter1.add(s);
                adapter.remove(s);
                adapter.notifyDataSetChanged();
                adapter1.notifyDataSetChanged();
                */
            }
        });

        lvc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView2, View view, int a, long b) {
                String s = adapter1.getItem(a);
                showPlayerSelected(s);
                //To change body of implemented methods use File | Settings | File Templates.
                /*
                String x = adapter1.getItem(a);
                adapter.add(x);
                adapter1.remove(x);
                adapter.notifyDataSetChanged();
                adapter1.notifyDataSetChanged();
                */
                //ListView check = (ListView) findViewById(R.id.lvnc);
                //check.setFocusable(false);

            }
        });

    }

    public void SelectPlayer(View view) {

        ListView check = (ListView) findViewById(R.id.lvnc);
        ListView check2 = (ListView) findViewById(R.id.lvc);

        SparseBooleanArray checkedPositions = check.getCheckedItemPositions();
        SparseBooleanArray checkedPositions2 = check2.getCheckedItemPositions();


        int conta=check.getCount();

        for (int i = 0; i <conta; i++) {
            if (checkedPositions.get(i)) {
                String s = adapter.getItem(i);
                adapter1.add(s);
                adapter.remove(s);
                adapter.notifyDataSetChanged();
                adapter1.notifyDataSetChanged();

            }
        }

        int conta2=check2.getCount();
        for (int i = 0; i <conta2; i++) {
            if (checkedPositions2.get(i)) {
                String x = adapter1.getItem(i);
                adapter.add(x);
                adapter1.remove(x);
                adapter.notifyDataSetChanged();
                adapter1.notifyDataSetChanged();

            }
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar_crud, menu);
        MenuItem editItem = menu.findItem(R.id.Edit);
        MenuItem delItem = menu.findItem(R.id.Delete);
        editItem.setVisible(false);
        delItem.setVisible(false);
        return true;
    }

    /************************************
     ****       Functions       ****
     ************************************/
    public List<String> getNamesList(List<Player> playerList){

        ArrayList<String> list = new ArrayList<String>();

        for(Player p: playerList)
            list.add(p.getName());

        return list;
    }


    public void showPlayerSelected(String position) {
        Player player = players.get(1);

        for(Player p: players)
            if(p.getName()==position)
            {
                player=p;
            }


        mDetailsPlayer.showPlayer(player);
    }

    public void save_player_call(ArrayAdapter<String> adap, Squad_Call_DAO sq_da, Game game, Pair<Player,Game> CS) throws GenericDAOException {

        for(int i=0; i<adap.getCount(); i++){
            for(Player p: players)
                if(p.getName()==adap.getItem(i))
                {
                    //adicionar p a lista de convocados;
                    CS.setFirst(p);
                    CS.setSecond(game);
                    sq_da.insert(CS);
                }
        }
    }


    /************************************
     ****        Test Functions      ****
     ************************************/

    private void insertUserTest(Player_DAO p_dao){

        try {
            Player p1 = new Player("Jocka", "1João Alberto", "Portuguesa", "Solteiro", 123123, 176 ,70.4f , "Travessa do Morro", "Masculino", "default.jpg", "player1@email.com", "Direito", 2, new Team(1), null);
            Player p2 = new Player("Fabinho", "2Fábio Gomes", "Portuguesa", "Solteiro", 123123, 170 ,83 , "Travessa do Morro", "Masculino", "default.jpg", "player1@email.com", "Direito", 4, new Team(1), null);
            Player p3 = new Player("Jorge D.", "3Jorge Duarte", "Portuguesa", "Solteiro", 123123, 180 ,73.6f , "Travessa do Morro", "Masculino", "default.jpg", "player1@email.com", "Esquerdo", 3, new Team(1), null);
            Player p4 = new Player("A", "4Manuel ", "Portuguesa2", "Solteiro2", 123123, 194 ,69.69f , "Travessa do Morro", "Masculino", "default.jpg", "player1@email.com", "Direito", 6, new Team(1), null);
            Player p5 = new Player("A", "5Manuel ", "Portuguesa2", "Solteiro2", 123123, 194 ,69.69f , "Travessa do Morro", "Masculino", "default.jpg", "player1@email.com", "Direito", 5, new Team(1), null);
            Player p6 = new Player("B", "6Fábio", "Portuguesa", "Solteiro", 123123, 170 ,83 , "Travessa do Morro", "Masculino", "default.jpg", "player1@email.com", "Direito", 1, new Team(1), null);
            long id;

            id = p_dao.insert(p1);
            id = p_dao.insert(p2);
            id = p_dao.insert(p3);
            id = p_dao.insert(p4);
            id = p_dao.insert(p5);
            id = p_dao.insert(p6);

        } catch (GenericDAOException e) {
            e.printStackTrace();
        }

    }
}
