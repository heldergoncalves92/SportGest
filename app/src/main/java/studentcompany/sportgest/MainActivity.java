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

import studentcompany.sportgest.Attributes.Attribute_Activity_ListView;
import studentcompany.sportgest.EventCategories.EventCategory_Activity_ListView;
import studentcompany.sportgest.Exercises.Exercise_Activity_ListView;
import studentcompany.sportgest.Games.Game_Activity_SquadCall;
import studentcompany.sportgest.Games.Game_Activity_GeneralView;

import studentcompany.sportgest.Games.Game_Activity_GameMode;
import studentcompany.sportgest.Games.Game_Activity_ListView;
import studentcompany.sportgest.Players.Player_Activity_ListView;
import studentcompany.sportgest.Positions.Position_Activity_ListView;
import studentcompany.sportgest.Roles.Role_Activity_ListView;
import studentcompany.sportgest.Team.Team_Activity_ListView;
import studentcompany.sportgest.Trainings.Training_Activity_ListView;


import studentcompany.sportgest.Users.RolesListActivity;
import studentcompany.sportgest.Users.User_Activity_ListView;
import studentcompany.sportgest.daos.Game_DAO;
import studentcompany.sportgest.daos.Player_DAO;
import studentcompany.sportgest.daos.Team_DAO;
import studentcompany.sportgest.daos.Training_DAO;
import studentcompany.sportgest.daos.User_DAO;
import studentcompany.sportgest.daos.db.TestData;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Game;
import studentcompany.sportgest.domains.Team;

public class MainActivity extends AppCompatActivity {
    //Interface

    private long baseTeamID, baseUserID;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Player_DAO player_dao = new Player_DAO(this.getApplicationContext());
        if(player_dao.numberOfRows()==0) {
            TestData td = new TestData(this.getApplicationContext());
        }
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //Get Informations from the previous activity or rotation Layout
        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();

            if (extras != null){
                baseTeamID = extras.getLong("TEAM");
                baseUserID = extras.getLong("USER");
            }
        } else {
            baseTeamID = savedInstanceState.getInt("baseTeamID");
            baseUserID = savedInstanceState.getInt("baseUserID");
        }

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        //Evaluation Button
        Button exerEval  = (Button)findViewById(R.id.exercise_attributes_evaluation);
        exerEval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sEvaluation="Evaluation";
                Intent intent = new Intent(getApplicationContext(), Training_Activity_ListView.class);

                //put current team ID and training ID in extras
                Bundle dataBundle = new Bundle();
                dataBundle.putLong(Team_DAO.TABLE_NAME + Team_DAO.COLUMN_ID, 1);
                dataBundle.putLong(Training_DAO.TABLE_NAME + Training_DAO.COLUMN_ID, 1);
                dataBundle.putString("sEvaluation",sEvaluation);
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
    public void onSaveInstanceState(Bundle outState) {

        outState.putLong("baseTeamID", baseTeamID);
        outState.putLong("baseUserID", baseUserID);
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
                return true;
        }
    }


    public void goTo_Players(View v){
        Intent intent = new Intent(this, Player_Activity_ListView.class);
        startActivity(intent);
    }

    public void goTo_Attribute(View v){
        Intent intent = new Intent(this, Attribute_Activity_ListView.class);
        startActivity(intent);
    }

    public void goTo_Exercise(View v){
        Intent intent = new Intent(this, Exercise_Activity_ListView.class);
        startActivity(intent);
    }

    public void goTo_Training(View v){
        Intent intent = new Intent(this, Training_Activity_ListView.class);
        startActivity(intent);
    }

    public void goTo_Games(View v){
        Intent intent = new Intent(this, Game_Activity_ListView.class);
        intent.putExtra("TEAM", baseTeamID);
        startActivity(intent);
    }

    public void goTo_Teams(View v){
        Intent intent = new Intent(this, Team_Activity_ListView.class);
        startActivity(intent);
    }

    public void generalSettings(View v){

        Intent intent = new Intent(this, Main_Activity_Settings.class);
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
                                if (mDrawerLayout.isDrawerOpen(GravityCompat.START))
                                    mDrawerLayout.closeDrawer(GravityCompat.START);
                                intent = new Intent(getApplicationContext(), RolesListActivity.class);
                                startActivity(intent);
                                return true;
                            case R.id.drawer_Users:
                                if (mDrawerLayout.isDrawerOpen(GravityCompat.START))
                                    mDrawerLayout.closeDrawer(GravityCompat.START);
                                intent = new Intent(getApplicationContext(), User_Activity_ListView.class);
                                startActivity(intent);
                                return true;
                            case R.id.drawer_Players:
                                if (mDrawerLayout.isDrawerOpen(GravityCompat.START))
                                    mDrawerLayout.closeDrawer(GravityCompat.START);
                                intent = new Intent(getApplicationContext(), Player_Activity_ListView.class);
                                startActivity(intent);
                                return true;
                            case R.id.drawer_Exercise:
                                if (mDrawerLayout.isDrawerOpen(GravityCompat.START))
                                    mDrawerLayout.closeDrawer(GravityCompat.START);

                                intent = new Intent(getApplicationContext(), Exercise_Activity_ListView.class);
                                startActivity(intent);
                                return true;
                        }

                        //menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

}
