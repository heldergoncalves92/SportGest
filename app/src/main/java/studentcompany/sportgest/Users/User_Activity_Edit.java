package studentcompany.sportgest.Users;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.logging.Level;
import java.util.logging.Logger;

import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.User_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Role;
import studentcompany.sportgest.domains.User;

public class User_Activity_Edit extends AppCompatActivity {

    //DAOs
    private User_DAO user_dao;

    User user = null;
    int userID = -1;
    private EditText tv_username,tv_name,tv_email,tv_password0,tv_password1,tv_password2;
    private ImageView tv_photo;
    private TextView focusView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_edit);

        Bundle b = getIntent().getExtras();
        if(b!=null){
            userID = b.getInt("id");
        }

        tv_username = (EditText) findViewById(R.id.username);
        tv_name = (EditText) findViewById(R.id.name);
        tv_email = (EditText) findViewById(R.id.email);
        tv_password1 = (EditText) findViewById(R.id.password1);
        tv_password2 = (EditText) findViewById(R.id.password2);
        tv_photo = (ImageView) findViewById(R.id.photo);

        User userFromDB=null;
        user_dao = new User_DAO(this);

        try {
            userFromDB = user_dao.getById(userID);
        } catch (GenericDAOException e) {
            e.printStackTrace();
        }
        if (userFromDB!=null){
            if(userFromDB.getUsername()!=null)
                tv_username.setText(userFromDB.getUsername());

            if(userFromDB.getName()!=null)
                tv_name.setText(userFromDB.getName());

            if(userFromDB.getName()!=null)
                tv_email.setText(userFromDB.getEmail());

            //para nunca vir a password
            userFromDB.setPassword("*********");

            //TODO: por a foto
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar_crud, menu);
        MenuItem editItem = menu.findItem(R.id.Edit);
        MenuItem delItem = menu.findItem(R.id.Delete);
        MenuItem addItem = menu.findItem(R.id.Add);
        editItem.setVisible(true);
        delItem.setVisible(false);
        addItem.setVisible(true);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);

        switch(item.getItemId())
        {
            //add action
            case R.id.Edit:

                boolean conti=false;

                tv_username = (EditText) findViewById(R.id.username);
                tv_name = (EditText) findViewById(R.id.name);
                tv_email = (EditText) findViewById(R.id.email);
                tv_password0 = (EditText) findViewById(R.id.password0);
                tv_password1 = (EditText) findViewById(R.id.password1);
                tv_password2 = (EditText) findViewById(R.id.password2);
                tv_photo = (ImageView) findViewById(R.id.photo);

                String username = tv_username.getText().toString();
                String name = tv_name.getText().toString();
                String password = "";
                String email = tv_email.getText().toString();
                String password0 = tv_password0.getText().toString();
                String password1 = tv_password1.getText().toString();
                String password2 = tv_password1.getText().toString();
                User userFromDB=null;
                try {
                    userFromDB = user_dao.getById(userID);
                } catch (GenericDAOException e) {
                    e.printStackTrace();
                }
                String passwordBef="";
                if(userFromDB!=null)
                    passwordBef=userFromDB.getPassword();
                conti = validate(username,name,email,passwordBef,password0,password1,password2);
                if(conti) {
                    if (password0.equals(userFromDB.getPassword())) {

                        if (password1.equals(password2)) {
                            password = password1;
                        } else {

                        }
                        //ups vai estar a imagem em bitmap ou o path para ela?
                        //String photo = tv_photo.get
                        String photo = null;
                        //corrigir isto
                        Role role = new Role(null, null);

                        user = new User(username, password, photo, name, email, null);

                        //insert/update database
                        try {
                            if (userID > 0) {
                                user_dao.update(user);
                            } else {
                                user_dao.insert(user);
                            }
                        } catch (GenericDAOException ex) {
                            System.err.println(User_Activity_Create.class.getName() + " [WARNING] " + ex.toString());
                            Logger.getLogger(User_Activity_Create.class.getName()).log(Level.WARNING, null, ex);
                        }
                        Intent intent = new Intent();
                        setResult(1, intent);
                        finish();
                    } else {
                        Intent intent = new Intent();
                        setResult(0, intent);
                        finish();
                    }
                    return true;
                }
               else{
                    focusView.requestFocus();
                    return super.onOptionsItemSelected(item);
                }

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onStop(){
        super.onStop();

        setResult(0);
    }

    private boolean validate(String username,String name,String email,String befPassword,String password0,String password1,String password2) {
        boolean conti=false;
        focusView = tv_username;

        if(username.length()<5) {
            focusView = tv_username;
            tv_username.setError(getString(R.string.err_username_short));
        }
        else
            conti=true;

        if(name.length()<3) {
            focusView = tv_name;
            tv_name.setError(getString(R.string.err_name_short));
        }
        else
            conti=true;

        if(email.contains("@")) {
            String[] emailTesting = email.split("@");
            if(emailTesting[1].contains(".")) {
                String[] emailTestingIntern = emailTesting[1].split(".");
                if(emailTestingIntern[0].length()>1 && emailTestingIntern[1].length()>1)
                    conti = true;
                else {
                    focusView = tv_email;
                    tv_email.setError(getString(R.string.err_valid_email));
                }
            }
            else {
                focusView = tv_email;
                tv_email.setError(getString(R.string.err_valid_email));
            }
        } else {
            focusView = tv_email;
            tv_email.setError(getString(R.string.err_valid_email));
        }

        if(password1.equals(password2) && password0.equals(befPassword)){
            conti=true;
        } else {
            focusView = tv_password0;
            tv_password0.setError(getString(R.string.err_password_match));
            tv_password1.setError(getString(R.string.err_password_match));
            tv_password2.setError(getString(R.string.err_password_match));
        }

        return conti;
    }
}
