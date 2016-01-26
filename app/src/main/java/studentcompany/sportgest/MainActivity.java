package studentcompany.sportgest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import studentcompany.sportgest.Attributes.AttributeListActivity;
import studentcompany.sportgest.Evaluation.ExerciseAttributesActivity;
import studentcompany.sportgest.EventCategories.EventCategory_Activity_ListView;
import studentcompany.sportgest.EventCategories.EventCategory_List_Adapter;
import studentcompany.sportgest.Exercises.ExerciseListActivity;
import studentcompany.sportgest.Games.CallSquad_Activity;
import studentcompany.sportgest.Games.GameGeneralView_Activity;
import studentcompany.sportgest.Games.GameTest_Activity;
import studentcompany.sportgest.Games.Game_Activity_GameMode;
import studentcompany.sportgest.Games.GamesListActivity;
//import studentcompany.sportgest.Games.GamesList_Activity;
import studentcompany.sportgest.Players.Player_Activity_ListView;
import studentcompany.sportgest.Positions.Position_Activity_ListView;
import studentcompany.sportgest.Roles.Role_Activity_ListView;
import studentcompany.sportgest.Team.Team_Activity_ListView;
import studentcompany.sportgest.Trainings.TrainingListActivity;
import studentcompany.sportgest.Users.RolesListActivity;
import studentcompany.sportgest.Users.User_Activity_ListView;
import studentcompany.sportgest.daos.Game_DAO;
import studentcompany.sportgest.daos.Team_DAO;
import studentcompany.sportgest.daos.Training_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Game;
import studentcompany.sportgest.domains.Team;

public class MainActivity extends AppCompatActivity {
    //Interface
    private Button eventCategoiesButton, exerciseButton, rolesButton, gamebutton, gametestbutton;
    private MenuItem menuItem;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }


        //EventCategoryButton
        eventCategoiesButton = (Button)findViewById(R.id.event_category_button);
        eventCategoiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), EventCategory_Activity_ListView.class);

                startActivity(intent);
            }
        });

        //gameButton
        gamebutton = (Button)findViewById(R.id.game_squadCall_button);
        gamebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CallSquad_Activity.class);

                startActivity(intent);
            }
        });

        //gameTestButton
        gametestbutton = (Button)findViewById(R.id.game_test_button);
        gametestbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GameTest_Activity.class);

                startActivity(intent);
            }
        });


        //RoleButton
        rolesButton = (Button)findViewById(R.id.roles_button);
        rolesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Role_Activity_ListView.class);
                //Intent intent = new Intent(getApplicationContext(), RolesListActivity.class);

                startActivity(intent);
            }
        });

        //Evaluation Button
        Button exerEval  = (Button)findViewById(R.id.exercise_attributes_evaluation);
        exerEval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ExerciseAttributesActivity.class);

                //put current team ID and training ID in extras
                Bundle dataBundle = new Bundle();
                dataBundle.putLong(Team_DAO.TABLE_NAME + Team_DAO.COLUMN_ID, 1);
                dataBundle.putLong(Training_DAO.TABLE_NAME + Training_DAO.COLUMN_ID, 1);
                //add data
                intent.putExtras(dataBundle);
                //start activity
                startActivity(intent);
            }
        });
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            //case R.id.nav_home:
            case android.R.id.home:
                if(mDrawerLayout.isDrawerOpen(GravityCompat.START))
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                else
                    mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
                return true;
            default:
                //mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        //return super.onOptionsItemSelected(item);
    }

    public void goTo_Positions(View v){
        Intent intent = new Intent(this, Position_Activity_ListView.class);
        startActivity(intent);
    }

    public void goTo_Users(View v){
        Intent intent = new Intent(this, User_Activity_ListView.class);
        startActivity(intent);
    }

    public void goTo_Players(View v){
        Intent intent = new Intent(this, Player_Activity_ListView.class);
        intent.putExtra("TEAM",1);
        startActivity(intent);
    }

    public void goTo_Attribute(View v){
        Intent intent = new Intent(this, AttributeListActivity.class);
        startActivity(intent);
    }

    public void goTo_Exercise(View v){
        Intent intent = new Intent(this, ExerciseListActivity.class);
        startActivity(intent);
    }

    public void goTo_Training(View v){
        Intent intent = new Intent(this, TrainingListActivity.class);
        startActivity(intent);
    }

    public void goTo_Teams(View v){
        Intent intent = new Intent(this, Team_Activity_ListView.class);
        startActivity(intent);
    }

    public void goTo_Login(View v){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void goTo_Roles(View v){
        Intent intent = new Intent(this, Role_Activity_ListView.class);
        startActivity(intent);
    }

    public void goTo_Games_teste(View v){
        //insertGamesTest();
        Intent intent = new Intent(this, GamesListActivity.class);
        startActivity(intent);
    }
    public void goTo_Games(View v){
        //insertGamesTest();
        Intent intent = new Intent(this, GamesListActivity.class);
        startActivity(intent);
    }

    public void goTo_GameTest(View v){
        Intent intent = new Intent(this, GameTest_Activity.class);
        startActivity(intent);
    }

    public void goTo_Game_GeneralView(View v){
        Intent intent = new Intent(this, GameGeneralView_Activity.class);
        startActivity(intent);
    }

    public void goTo_GameModeView(View v){
        Intent intent = new Intent(this, Game_Activity_GameMode.class);
        intent.putExtra("GAME",(long)1);
        startActivity(intent);
    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        Intent intent;
                        switch (menuItem.getItemId()) {
                            case R.id.drawer_roles:
                                if(mDrawerLayout.isDrawerOpen(GravityCompat.START))
                                    mDrawerLayout.closeDrawer(GravityCompat.START);
                                intent = new Intent(getApplicationContext(), RolesListActivity.class);
                                startActivity(intent);
                                return true;
                            case R.id.drawer_Users:
                                if(mDrawerLayout.isDrawerOpen(GravityCompat.START))
                                    mDrawerLayout.closeDrawer(GravityCompat.START);
                                intent = new Intent(getApplicationContext(), User_Activity_ListView.class);
                                startActivity(intent);
                                return true;
                            case R.id.drawer_Players:
                                if(mDrawerLayout.isDrawerOpen(GravityCompat.START))
                                    mDrawerLayout.closeDrawer(GravityCompat.START);
                                intent = new Intent(getApplicationContext(), Player_Activity_ListView.class);
                                startActivity(intent);
                                return true;
                            case R.id.drawer_Exercise:
                                if(mDrawerLayout.isDrawerOpen(GravityCompat.START))
                                    mDrawerLayout.closeDrawer(GravityCompat.START);
                                intent = new Intent(getApplicationContext(), ExerciseListActivity.class);
                                startActivity(intent);
                                return true;
                        }

                        //menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }


    private void insertGamesTest(){

        try {
            Game_DAO gameDao = new Game_DAO(getApplicationContext());
            if(gameDao.getAll() == null) {

                gameDao.insert(new Game(new Team(1), new Team(2), 0, "O jogo foi muito competitivo!!", 3, 1, 40f));
                gameDao.insert(new Game(new Team(3), new Team(4), 0, "O jogo foi muito competitivo!!", 1, 1, 50f));
                gameDao.insert(new Game(new Team(1), new Team(3), 0, "O jogo foi muito competitivo!!", 2, 3, 40f));
                gameDao.insert(new Game(new Team(2), new Team(4), 0, "O jogo foi muito competitivo!!", 3, 2, 40f));
            }
        } catch (GenericDAOException e) {
            e.printStackTrace();
        }
    }
}
