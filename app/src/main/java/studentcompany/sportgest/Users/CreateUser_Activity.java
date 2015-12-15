package studentcompany.sportgest.Users;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.text.TextWatcher;
import android.text.Editable;
import android.text.TextUtils;
import android.widget.TextView;

import java.util.logging.Level;
import java.util.logging.Logger;

import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.User_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Role;
import studentcompany.sportgest.domains.User;

public class CreateUser_Activity extends AppCompatActivity {

    //DAOs
    private User_DAO user_dao;

    User user = null;
    private long userID = -1;
    private EditText tv_username,tv_name,tv_email,tv_password1,tv_password2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        user_dao = new User_DAO(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar_crud, menu);
        MenuItem editItem = menu.findItem(R.id.Edit);
        MenuItem delItem = menu.findItem(R.id.Delete);
        MenuItem addItem = menu.findItem(R.id.Add);
        editItem.setVisible(false);
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
            case R.id.Add:

                EditText focusView = tv_username;
                tv_username = (EditText) findViewById(R.id.username);
                tv_name = (EditText) findViewById(R.id.name);
                tv_email = (EditText) findViewById(R.id.email);
                tv_password1 = (EditText) findViewById(R.id.password1);
                tv_password2 = (EditText) findViewById(R.id.password2);
                ImageView tv_photo = (ImageView) findViewById(R.id.photo);

                String username = tv_username.getText().toString();
                String name = tv_name.getText().toString();
                String email = tv_email.getText().toString();
                String password1 = tv_password1.getText().toString();
                String password2 = tv_password1.getText().toString();

                boolean conti = validate(username,name,email,password1,password2);

                //ups vai estar a imagem em bitmap ou o path para ela?
                //String photo = tv_photo.get
                String photo="";
                //corrigir isto
                Role role = new Role(null,null);

                if(conti){
                    user=new User(username,password1,photo,name,email,null);

                    //insert/update database
                    try {

                        userID = user_dao.insert(user);

                    }catch (GenericDAOException ex){
                        System.err.println(CreateUser_Activity.class.getName() + " [WARNING] " + ex.toString());
                        Logger.getLogger(CreateUser_Activity.class.getName()).log(Level.WARNING, null, ex);
                    }



                    Intent intent = new Intent();
                    intent.putExtra("id",userID);
                    setResult(1, intent);
                    finish();
                    return true;
                } else{
                    focusView.requestFocus();
                    return super.onOptionsItemSelected(item);
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean validate(String username,String name,String email,String password1,String password2) {
        boolean conti=false;
        TextView focusView = tv_username;

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

        if(password1.equals(password2)){
            conti=true;
        } else {
            focusView = tv_password1;
            tv_password1.setError(getString(R.string.err_password_match));
            tv_password2.setError(getString(R.string.err_password_match));
        }

        return conti;
    }
}
