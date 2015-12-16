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
import studentcompany.sportgest.EventCategories.ListEventCategoryActivity;
import studentcompany.sportgest.Games.CallSquad_Activity;
import studentcompany.sportgest.Games.GameGeneralView_Activity;
import studentcompany.sportgest.Games.GamesList_Activity;
import studentcompany.sportgest.Exercises.ExerciseListActivity;
import studentcompany.sportgest.Players.PlayersList_Activity;
import studentcompany.sportgest.Trainings.CreateTrainingActivity;
import studentcompany.sportgest.Trainings.TrainingListActivity;
import studentcompany.sportgest.Team.TeamList_Activity;
import studentcompany.sportgest.Users.RolesListActivity;
import studentcompany.sportgest.Users.UserListActivity;
import studentcompany.sportgest.daos.Game_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Game;
import studentcompany.sportgest.domains.Team;

public class MainActivity extends AppCompatActivity {
    //Interface
    private Button eventCategoiesButton, exerciseButton, rolesButton, gamebutton;
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
                /*int id_To_Search = arg2 + 1;
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", id_To_Search);
                */
                Intent intent = new Intent(getApplicationContext(), ListEventCategoryActivity.class);

                //intent.putExtras(dataBundle);
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


        //RoleButton
        rolesButton = (Button)findViewById(R.id.roles_button);
        rolesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RolesListActivity.class);

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

    public void goTo_Users(View v){
        Intent intent = new Intent(this, UserListActivity.class);
        startActivity(intent);
    }

    public void goTo_Players(View v){
        Intent intent = new Intent(this, PlayersList_Activity.class);
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
        Intent intent = new Intent(this, CreateTrainingActivity.class);
        startActivity(intent);
    }

    public void goTo_Teams(View v){
        Intent intent = new Intent(this, TeamList_Activity.class);
        startActivity(intent);
    }

    public void goTo_Login(View v){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void goTo_Roles(View v){
        Intent intent = new Intent(this, RolesListActivity.class);
        startActivity(intent);
    }

    public void goTo_Games(View v){
        //insertGamesTest();
        Intent intent = new Intent(this, GamesList_Activity.class);
        startActivity(intent);
    }

    public void goTo_Game_GeneralView(View v){
        Intent intent = new Intent(this, GameGeneralView_Activity.class);
        startActivity(intent);
    }



    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.drawer_roles:
                                Intent intent = new Intent(getApplicationContext(), RolesListActivity.class);
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
