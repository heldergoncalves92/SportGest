package studentcompany.sportgest.Users;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Role_DAO;
import studentcompany.sportgest.daos.Team_DAO;
import studentcompany.sportgest.daos.User_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Role;
import studentcompany.sportgest.domains.Team;
import studentcompany.sportgest.domains.User;

public class CreateUser_Activity extends AppCompatActivity {

    private User user;
    private long userID;
    private Team_DAO team_dao;
    private User_DAO user_dao;
    private Role_DAO role_dao;
    private ArrayList<Team> listTeams;
    private List<Role> listRoles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);


        team_dao = new Team_DAO(this);
        role_dao = new Role_DAO(this);
        user_dao = new User_DAO(this);


        //get extras with object id
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            //get User ID
            userID = extras.getLong("ID");
        }

        Spinner spinnert = (Spinner) findViewById(R.id.input_create_user_team_spinner);
        Spinner spinnerr = (Spinner) findViewById(R.id.input_create_user_role_spinner);
        try {
            listTeams = team_dao.getAll();
            listRoles = role_dao.getAll();
        } catch (GenericDAOException e) {
            e.printStackTrace();
        }


        ArrayAdapter<Team> dataAdaptert = new ArrayAdapter<Team>(this,android.R.layout.simple_list_item_1, listTeams);
        ArrayAdapter<Role> dataAdapterr = new ArrayAdapter<Role>(this,android.R.layout.simple_list_item_1, listRoles);

        spinnert.setAdapter(dataAdaptert);
        spinnerr.setAdapter(dataAdapterr);

    }





    /************************************
     ****       Menu Functions       ****
     ************************************/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_users_view, menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MenuItem item;

        item = menu.findItem(R.id.action_edit);
        item.setVisible(false);
        item = menu.findItem(R.id.action_del);
        item.setVisible(false);
        item = menu.findItem(R.id.action_add);
        item.setVisible(false);
        item = menu.findItem(R.id.action_save);
        item.setVisible(true);
        item = menu.findItem(R.id.action_settings);
        item.setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_save:

                String username,password,photo="",name,email;
                username = ((EditText) findViewById(R.id.input_create_user_username)).getText().toString();
                password = ((EditText) findViewById(R.id.input_create_user_password)).getText().toString();
                //photo = ((EditText) findViewById(R.id.input_create_user_photo)).getText().toString();
                name = ((EditText) findViewById(R.id.input_create_user_name)).getText().toString();
                email = ((EditText) findViewById(R.id.input_create_user_email)).getText().toString();

                Spinner spr = ((Spinner) findViewById(R.id.input_create_user_role_spinner));
                Role role = (Role) null;
                        role = (Role) spr.getSelectedItem();

                Spinner spt = ((Spinner) findViewById(R.id.input_create_user_team_spinner));
                Team team = (Team)spt.getSelectedItem();

                user = new User(username,password,photo,name,email,role,team);

                try {
                    user_dao.insert(user);
                    Toast.makeText(getApplicationContext(), R.string.user_save_successful, Toast.LENGTH_SHORT).show();
                    setResult(112);
                    finish();
                    return true;
                } catch (GenericDAOException e) {
                    Toast.makeText(getApplicationContext(), R.string.user_save_unsuccessful, Toast.LENGTH_SHORT).show();
                }

                return false;

            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }





}
