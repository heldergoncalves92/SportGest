package studentcompany.sportgest.Games;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Event_DAO;
import studentcompany.sportgest.daos.Game_DAO;
import studentcompany.sportgest.daos.Player_DAO;
import studentcompany.sportgest.daos.Squad_Call_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Event;
import studentcompany.sportgest.domains.Game;
import studentcompany.sportgest.domains.Player;
import studentcompany.sportgest.domains.PlayerPosition;
import studentcompany.sportgest.domains.Position;
import studentcompany.sportgest.domains.Team;

public class GameGeneralView_Activity extends AppCompatActivity{


    //DAOs
    private Event_DAO eventDAO;
    private Game_DAO game_dao;
    private Squad_Call_DAO squad_call_dao;

    private List<Player> home_players, visitor_players;
    private List<Event> eventsList;


    private long baseTeamID = 0;
    private long baseGameID = 0;
    private int current_tab = 0;

    //Id of current game displayed
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_general_view);


        //Get Informations from the previous activity or rotation Layout
        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();

            if (extras != null){
                baseTeamID = extras.getLong("TEAM");
                baseGameID = extras.getLong("GAME");

            } else {
                baseTeamID = 0;
                baseGameID = 0;
            }
        } else {
            baseTeamID = savedInstanceState.getInt("baseTeamID");
            current_tab = savedInstanceState.getInt("current_tab");
        }

        //Some verifications
        if(baseTeamID <=0 || baseGameID <= 0) {
            Toast.makeText(this, "Invalid call!!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }


        // Initialize the ViewPager and set an adapter
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        // Bind the tabs to the ViewPager
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(pager);

        pager.setCurrentItem(0);

        tabs.setOnTabReselectedListener(new PagerSlidingTabStrip.OnTabReselectedListener() {
            @Override
            public void onTabReselected(int position) {
                Toast.makeText(GameGeneralView_Activity.this, "Tab reselected: " + position, Toast.LENGTH_SHORT).show();
            }
        });


        //initialize required DAOs
        game_dao = new Game_DAO(getApplicationContext());
        squad_call_dao = new Squad_Call_DAO(getApplicationContext());
        eventDAO = new Event_DAO(getApplicationContext());

        //get all events and Players
        try {
            game = game_dao.getById(baseGameID);
            eventsList = eventDAO.getByGameID(baseGameID);
            visitor_players = squad_call_dao.getPlayersBy_GameID(baseGameID);

            if(visitor_players == null) visitor_players = new ArrayList<Player>();
            home_players = new ArrayList<Player>();

            for (Player p: visitor_players)
                if (p.getTeam().getId() == baseTeamID)
                    home_players.add(p);

            //Remove repeted players
            for (Player p: home_players)
                visitor_players.remove(p);
            
        } catch (GenericDAOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putLong("baseTeamID", baseTeamID);
        outState.putLong("baseGameID", baseGameID);
        outState.putInt("current_tab", current_tab);
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = {"Overview", "Statistics", "Squad"};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            if(position==0)
                return GameEvents_Fragment.newInstance(position, game, eventsList);
            else if(position==1)
                return GameStatistics_Fragment.newInstance(position);
            else
                return Game_Fragment_Squad.newInstance(position, home_players, visitor_players);
        }
    }


    /************************************
     ****       Menu Functions       ****
     ************************************/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Menu mOptionsMenu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_game_view, menu);

        //To restore state on Layout Rotation
        /*if(currentPos != -1 && players.size()>0) {

            MenuItem item = mOptionsMenu.findItem(R.id.action_del);
            item.setVisible(true);

            item = mOptionsMenu.findItem(R.id.action_edit);
            item.setVisible(true);

            mDetailsPlayer.showPlayer(players.get(currentPos));
            mListPlayer.select_Item(currentPos);
        }*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_home_CallSquad:
                intent = new Intent(this, Game_Activity_SquadCall.class);
                intent.putExtra("TEAM", baseTeamID);
                intent.putExtra("GAME", baseGameID);
                startActivity(intent);
                return true;

            case R.id.action_del:
                intent = new Intent();
                setResult(1, intent);
                intent.putExtra("OPERATION", 1); //To delete

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onStop(){
        super.onStop();

        Intent returnIntent = new Intent();
        setResult(0, returnIntent);
    }

    /************************************
     ****        Test Functions      ****
     ************************************/

    private void insertUserTest(Player_DAO p_dao){

        try {
            Player p1 = new Player("Jocka", "João Alberto", "Portugal", "Single", "1222-1-23", 176 ,70.4f , "Travessa do Morro", "Male", "default.jpg", "player1@email.com", "Direito", 2, new Team(1), null);
            Player p2 = new Player("Fabinho", "Fábio Gomes", "Portugal", "Married", "1222-1-23", 170 ,83 , "Travessa do Morro", "Male", "default.jpg", "player1@email.com", "Direito", 4, new Team(1), null);
            Player p3 = new Player("Jorge D.", "Jorge Duarte", "Spain", "Single", "1231-2-3", 180 ,73.6f , "Travessa do Morro", "Male", "default.jpg", "player1@email.com", "Esquerdo", 3, new Team(1), null);
            Player p4 = new Player("Nel", "Manuel Arouca", "Portugal", "Married", "1231-2-3", 194 ,69.69f , "Travessa do Morro", "Male", "default.jpg", "player1@email.com", "Direito", 1, new Team(2), null);

            Position po1 = new Position("Ala");
            PlayerPosition pp1 = new PlayerPosition(1,p1,po1,5);

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
