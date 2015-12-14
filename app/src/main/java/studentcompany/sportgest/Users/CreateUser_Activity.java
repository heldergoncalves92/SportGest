package studentcompany.sportgest.Users;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Pair;
import studentcompany.sportgest.daos.Role_DAO;
import studentcompany.sportgest.daos.Team_DAO;
import studentcompany.sportgest.daos.User_DAO;
import studentcompany.sportgest.daos.User_Team_DAO;
import studentcompany.sportgest.daos.db.MyDB;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Role;
import studentcompany.sportgest.domains.Team;
import studentcompany.sportgest.domains.User;

public class CreateUser_Activity extends AppCompatActivity {

    private User user;
    private long userID;
    private Team_DAO team_dao;
    private User_Team_DAO user_team_dao;
    private User_DAO user_dao;
    private Role_DAO role_dao;
    private ArrayList<Team> listTeams;
    private List<Role> listRoles;
    private ArrayAdapter<Team> dataAdaptert;
    private ArrayAdapter<Role> dataAdapterr;

    private EditText username,password,password2,email,name,photo;
    private Spinner spinnerRole,spinnerTeam;

    private TextInputLayout inputLayoutName,inputLayoutUsername,inputLayoutPassword,inputLayoutPassword2,inputLayoutEmail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);


        team_dao = new Team_DAO(this);
        role_dao = new Role_DAO(this);
        user_dao = new User_DAO(this);
        user_team_dao = new User_Team_DAO(this);


        //get extras with object id
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            //get User ID
            userID = extras.getLong("ID");
        }

        username =   (EditText) findViewById(R.id.input_create_user_username);
        password =   (EditText) findViewById(R.id.input_create_user_password);
        password2 =  (EditText) findViewById(R.id.input_create_user_password2);
        email =      (EditText) findViewById(R.id.input_create_user_email);
        name =       (EditText) findViewById(R.id.input_create_user_name);

        spinnerRole = (Spinner) findViewById(R.id.input_create_user_role_spinner);
        spinnerTeam = (Spinner) findViewById(R.id.input_create_user_team_spinner);


        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_create_user_name);
        inputLayoutUsername = (TextInputLayout) findViewById(R.id.input_layout_create_user_username);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_create_user_email);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_create_user_password);
        inputLayoutPassword2 = (TextInputLayout) findViewById(R.id.input_layout_create_user_password2);



        if(userID > 0)
        try {
            listTeams = team_dao.getAll();
            listTeams.add(0,new Team(-1,getResources().getString(R.string.no_team)));
            listRoles = role_dao.getAll();
            listRoles.add(0,new Role(-1,getResources().getString(R.string.no_role)));
        } catch (GenericDAOException e) {
            e.printStackTrace();
        }


        dataAdaptert = new ArrayAdapter<Team>(this,android.R.layout.simple_list_item_1, listTeams);
        dataAdapterr = new ArrayAdapter<Role>(this,android.R.layout.simple_list_item_1, listRoles);

        spinnerRole.setAdapter(dataAdapterr);
        spinnerTeam.setAdapter(dataAdaptert);


        try {
            user = user_dao.getById(userID);

            List<Team> t = user_team_dao.getByFirstId(user.getId());
            if(t!=null)
                if(t.size()>0)
                    user.setTeam(t.get(0)); // Get the Team

            username.setText(user.getUsername());
            name.setText(user.getName());
            email.setText(user.getEmail());
            password.setText(user.getPassword());
            password2.setText(user.getPassword());
            int index = 0;
            if(user.getRole()!=null) {
                spinnerRole.setSelection(dataAdapterr.getPosition(user.getRole()));
                /*index = 0;
                for (Role r : listRoles) {
                    if (r.getId() == user.getRole().getId())
                        {spinnerRole.setSelection(index);
                         break;}
                    index++;
                }*/
            }
            index = 0;
            if(user.getTeam()!=null) {
                spinnerTeam.setSelection(dataAdaptert.getPosition(user.getTeam()));

                /*index = 0;
                for (Team t : listTeams) {
                    if (t.getId() == user.getTeam().getId())
                    {spinnerTeam.setSelection(index);
                        break;}
                    index++;
                }*/
            }
        } catch (GenericDAOException e) {
            Toast.makeText(getApplicationContext(), R.string.error_occured, Toast.LENGTH_SHORT).show();
            finish();
        }


        username.addTextChangedListener(new MyTextWatcher(username));
        name.addTextChangedListener(new MyTextWatcher(name));
        email.addTextChangedListener(new MyTextWatcher(email));
        password.addTextChangedListener(new MyTextWatcher(password));
        password2.addTextChangedListener(new MyTextWatcher(password2));


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

                String username,password,password2,photo="",name,email;
                username = ((EditText) findViewById(R.id.input_create_user_username)).getText().toString();
                password = ((EditText) findViewById(R.id.input_create_user_password)).getText().toString();
                password2 = ((EditText) findViewById(R.id.input_create_user_password2)).getText().toString();
                //photo = ((EditText) findViewById(R.id.input_create_user_photo)).getText().toString();
                name = ((EditText) findViewById(R.id.input_create_user_name)).getText().toString();
                email = ((EditText) findViewById(R.id.input_create_user_email)).getText().toString();

                Spinner spr = ((Spinner) findViewById(R.id.input_create_user_role_spinner));
                Role role = (Role) spr.getSelectedItem();

                Spinner spt = ((Spinner) findViewById(R.id.input_create_user_team_spinner));
                Team team = (Team)spt.getSelectedItem();

                User usernew = new User(username,password,photo,name,email,role.getId() < 0 ? null:role,team.getId() < 0 ? null : team);

                try {
                    boolean ok = false;
                    if(validateName())
                        if(validateUsername())
                            if(validateEmail())
                                if(validatePassword())
                                    if(validatePassword2())
                                        ok = true;

                    if(!ok)
                        return false;

                    MyDB.getInstance(this).db.beginTransaction();

                    if(userID > 0){
                        usernew.setId(userID);

                        // Team
                        if(team.equals(user.getTeam())==false && usernew.getTeam()!=null) {

                            Pair<User, Team> pair = new Pair<>(usernew, usernew.getTeam());
                            user_team_dao.delete(pair); // Update the User <=> Team
                            for(Pair<User,Team> p : user_team_dao.getAll())
                                user_team_dao.delete(p);

                            if (team.getId() > 0) {
                                pair.setSecond(team);
                                user_team_dao.insert(pair);
                            }
                        }
                        else{
                            Pair<User, Team> pair = new Pair<>(usernew, usernew.getTeam());
                            user_team_dao.insert(pair);
                        }

                        user_dao.update(usernew); // Update the User

                    }else {
                        long new_user_id = user_dao.insert(usernew); // Inser the User
                        usernew.setId(new_user_id);

                        // Team
                        if(team!=null)
                            if(team.getId() > 0) {
                                Pair<User, Team> pair = new Pair<>(usernew, team);
                                user_team_dao.insert(pair); // Insert the User <=> Team
                        }

                    }





                    MyDB.getInstance(this).db.setTransactionSuccessful();
                    MyDB.getInstance(this).db.endTransaction();

                    Toast.makeText(getApplicationContext(), R.string.user_save_successful, Toast.LENGTH_SHORT).show();
                    setResult(112);
                    finish();
                    return true;
                } catch (GenericDAOException e) {
                    MyDB.getInstance(this).db.endTransaction();
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


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_create_user_username:
                    validateUsername();
                    break;
                case R.id.input_create_user_name:
                    validateName();
                    break;
                case R.id.input_create_user_email:
                    validateEmail();
                    break;
                case R.id.input_create_user_password:
                case R.id.input_create_user_password2:
                    validatePassword();
                    validatePassword2();
                    break;
            }
        }
    }


    private boolean validateName() {
        String name1 = name.getText().toString();
        if(user!=null)
            {if(name1.compareTo(user.getName())==0)
                ;}
        else if (name1.trim().isEmpty()) {
                inputLayoutName.setError(getString(R.string.err_user_name_empty));
                requestFocus(inputLayoutName);
                return false;
            }
        inputLayoutName.setErrorEnabled(false);
        return true;
    }


    private boolean validateUsername() {
        String nametest = username.getText().toString().trim();
        if(user!=null)
        {
            if(nametest.compareTo(user.getUsername())==0)
                ;
        }
        else if (nametest.isEmpty()) {
                inputLayoutUsername.setError(getString(R.string.err_username_empty));
                requestFocus(inputLayoutUsername);
                return false;
            } else try {
                User u = new User(nametest);
                if(user_dao.exists(u)){
                    inputLayoutUsername.setError(getString(R.string.err_username_exists));
                    requestFocus(inputLayoutUsername);
                    return false;
                }

            } catch (GenericDAOException e) {
                e.printStackTrace();
            }
        inputLayoutUsername.setErrorEnabled(false);
        return true;
    }


    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    private boolean validateEmail() {
        String emailt = email.getText().toString().trim();
        if (emailt.isEmpty() || !isValidEmailAddress(emailt)) {
            inputLayoutEmail.setError(getString(R.string.error_invalid_email));
            requestFocus(inputLayoutEmail);
            return false;
        }
        inputLayoutEmail.setErrorEnabled(false);
        return true;
    }

    private boolean validatePassword() {
        String pw = password.getText().toString().trim();
        if (pw.isEmpty() || pw.length() < 6) {
            inputLayoutPassword.setError(getString(R.string.error_invalid_password));
            //requestFocus(inputLayoutPassword);
            return false;
        }
        inputLayoutPassword.setErrorEnabled(false);
        return true;
    }

    private boolean validatePassword2() {
        String pw1 = password.getText().toString();
        String pw2 = password2.getText().toString();
        if (pw1.compareTo(pw2) != 0) {
            inputLayoutPassword2.setError(getString(R.string.error_mismatch_passwords));
            //requestFocus(inputLayoutPassword2);
            return false;
        }
        inputLayoutPassword2.setErrorEnabled(false);
        return true;
    }








    public void onStop(){
        super.onStop();

        Intent returnIntent = new Intent();
        setResult(0, returnIntent);
    }

}
