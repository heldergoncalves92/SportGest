package studentcompany.sportgest.Users;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

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
    long userID = -1;

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

                EditText tv_username = (EditText) findViewById(R.id.username);
                EditText tv_name = (EditText) findViewById(R.id.name);
                EditText tv_email = (EditText) findViewById(R.id.email);
                EditText tv_password1 = (EditText) findViewById(R.id.password1);
                EditText tv_password2 = (EditText) findViewById(R.id.password2);
                ImageView tv_photo = (ImageView) findViewById(R.id.photo);

                String username = tv_username.getText().toString();
                String name = tv_name.getText().toString();
                String password = "";
                String password1 = tv_password1.getText().toString();
                String password2 = tv_password1.getText().toString();
                if(password1.equals(password2)){
                    password=password1;
                } else {
                    try {
                        throw new Exception("passwords don't match");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                String email = tv_email.getText().toString();
                //ups vai estar a imagem em bitmap ou o path para ela?
                //String photo = tv_photo.get
                String photo="";
                //corrigir isto
                Role role = new Role(null,null);

                user=new User(username,password,photo,name,email,null);

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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
