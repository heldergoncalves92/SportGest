package studentcompany.sportgest.Users;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Player_DAO;
import studentcompany.sportgest.daos.User_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Player;
import studentcompany.sportgest.domains.Position;
import studentcompany.sportgest.domains.Role;
import studentcompany.sportgest.domains.User;

public class CreateUser_Activity extends AppCompatActivity {

    //DAOs
    private User_DAO user_dao;

    User user = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        EditText tv_username = (EditText) findViewById(R.id.username);
        EditText tv_name = (EditText) findViewById(R.id.name);
        EditText tv_email = (EditText) findViewById(R.id.email);
        EditText tv_password1 = (EditText) findViewById(R.id.password1);
        EditText tv_password2 = (EditText) findViewById(R.id.password2);
        ImageView tv_photo = (ImageView) findViewById(R.id.photo);

        user_dao = new User_DAO(this);

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

        try {
            user_dao.insert(user);
        } catch (GenericDAOException e) {
            e.printStackTrace();
        }
    }
}
