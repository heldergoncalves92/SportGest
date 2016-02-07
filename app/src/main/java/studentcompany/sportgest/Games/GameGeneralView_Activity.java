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

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Event_DAO;
import studentcompany.sportgest.daos.Game_DAO;
import studentcompany.sportgest.daos.Player_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Event;
import studentcompany.sportgest.domains.Game;
import studentcompany.sportgest.domains.Player;
import studentcompany.sportgest.domains.PlayerPosition;
import studentcompany.sportgest.domains.Position;
import studentcompany.sportgest.domains.Team;

public class GameGeneralView_Activity extends AppCompatActivity{


    private Event_DAO eventDAO;
    private List<Event> listevents;
    private List<Event> listev;


    //DAOs
    private Game_DAO game_dao;
    private Player_DAO playerDao;

    private List<Player> players;
    private List<Player> playershome;
    private List<Player> playersvisitor;



    private long baseTeamID = 0;
    private long baseGameID = 0;

    //Id of current game displayed
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_general_view);

        //initialize required DAOs
        game_dao = new Game_DAO(this);

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


        //get all events
        try {
            eventDAO = new Event_DAO(getApplicationContext());
            listevents = eventDAO.getAll();
            //players = playerDao.getAll();
            if(listevents.isEmpty()) {

                //noElems();
                //players = playerDao.getAll();
            }
            //mListEventCategory.setList(listevents);

        } catch (GenericDAOException e) {
            e.printStackTrace();
        }


        // get object id games
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            //get Game ID
            long gameID = extras.getLong(Game_DAO.TABLE_NAME + Game_DAO.COLUMN_ID);

            //validation
            if(gameID > 0){
                //get the exercise information
                try {
                    game = game_dao.getById(gameID);
                } catch (GenericDAOException ex){
                    System.err.println(GameGeneralView_Activity.class.getName() + " [WARNING] " + ex.toString());
                    Logger.getLogger(GameGeneralView_Activity.class.getName()).log(Level.WARNING, null, ex);
                }

                //validation
                if(game != null) {
                    Team teamhome =game.getHome_team();
                    Team teamhome2 =game.getVisitor_team();
                    //for(int i=0; i<players.size();i++)
                    try {
                        playerDao = new Player_DAO(getApplicationContext());
                        playershome = playerDao.getByCriteria(new Player(teamhome));
                        //playershome = playerDao.getAll();
                        if(playershome.isEmpty()) {

                            //noElems();
                            insertUserTest(playerDao);
                            //players = playerDao.getAll();
                        }
                        //mListPlayer.setList(players);

                    } catch (GenericDAOException e) {
                        e.printStackTrace();
                    }
                    try {
                        playerDao = new Player_DAO(getApplicationContext());
                        playersvisitor = playerDao.getByCriteria(new Player(teamhome2));

                        if(playersvisitor.isEmpty()) {

                            //noElems();
                            insertUserTest(playerDao);
                            //players = playerDao.getAll();
                        }
                        //mListPlayer.setList(players);

                    } catch (GenericDAOException e) {
                        e.printStackTrace();
                    }
                    for(int i=0; i<listevents.size();i++)
                    {
                        Event ev = listevents.get(i);
                        if(ev.getGame()==game)
                            listev.add(ev);
                    }

                    //set layout variables with information


                }//if(exercise != null)

            }//if(exerciseID > 0)

        }//if(extras != null)
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
                return GameEvents_Fragment.newInstance(position, listev);
            else if(position==1)
                return GameStatistics_Fragment.newInstance(position);
            else
                return Game_Fragment_Squad.newInstance(position, playershome, playersvisitor);
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
        switch (item.getItemId()) {
            case R.id.action_home_CallSquad:
                Intent intent = new Intent(this, Game_Activity_SquadCall.class);
                intent.putExtra("TEAM", baseTeamID);
                intent.putExtra("GAME", baseGameID);
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
