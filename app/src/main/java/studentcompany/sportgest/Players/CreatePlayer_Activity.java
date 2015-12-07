package studentcompany.sportgest.Players;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import studentcompany.sportgest.Exercises.ListExerciseActivity;
import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Attribute_DAO;
import studentcompany.sportgest.daos.Attribute_Exercise_DAO;
import studentcompany.sportgest.daos.Exercise_DAO;
import studentcompany.sportgest.daos.Pair;
import studentcompany.sportgest.daos.Player_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Attribute;
import studentcompany.sportgest.domains.Exercise;
import studentcompany.sportgest.domains.Player;
import studentcompany.sportgest.domains.Position;

public class CreatePlayer_Activity extends AppCompatActivity {

    //DAOs
    private Player_DAO player_dao;

    Player player = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_player);

        EditText tv_nickname = (EditText) findViewById(R.id.nickname);
        EditText tv_name = (EditText) findViewById(R.id.name);
        Spinner tv_nationality = (Spinner) findViewById(R.id.nationality);
        Spinner tv_maritalStatus = (Spinner) findViewById(R.id.maritalstatus);
        DatePicker tv_birthday = (DatePicker) findViewById(R.id.birthday);
        EditText tv_height = (EditText) findViewById(R.id.height);
        EditText tv_weight = (EditText) findViewById(R.id.weight);
        EditText tv_address = (EditText) findViewById(R.id.address);
        Spinner tv_gender = (Spinner) findViewById(R.id.gender);
        EditText tv_email = (EditText) findViewById(R.id.email);
        Spinner tv_preferredFoot = (Spinner) findViewById(R.id.preferredfoot);
        EditText tv_number = (EditText) findViewById(R.id.number);
        ImageView tv_photo = (ImageView) findViewById(R.id.photo);
        Spinner tv_position = (Spinner) findViewById(R.id.position);

        player_dao = new Player_DAO(this);

        String nickname = tv_nickname.getText().toString();
        String name = tv_name.getText().toString();
        String nationality = tv_nationality.getSelectedItem().toString();
        String maritalStatus = tv_maritalStatus.getSelectedItem().toString();
        int day = tv_birthday.getDayOfMonth();
        int month = tv_birthday.getMonth() + 1;
        int year = tv_birthday.getYear();
        //corrigir quando a data estiver direita
        //String birthday = year+"-"+month+"-"+day;
        int birthday = year;
        int height = Integer.parseInt(tv_height.getText().toString());
        float weight = Float.parseFloat(tv_weight.getText().toString());
        String address = tv_address.getText().toString();
        String gender = tv_gender.getSelectedItem().toString();
        String email = tv_email.getText().toString();
        String preferredFoot = tv_preferredFoot.getSelectedItem().toString();
        int number = Integer.parseInt(tv_number.getText().toString());
        //ups vai estar a imagem em bitmap ou o path para ela?
        //String photo = tv_photo.get
        String photo="";
        Position position = (Position) tv_position.getSelectedItem();

        player=new Player(nickname,name,nationality,maritalStatus,birthday,height,weight,address,gender,photo,email,preferredFoot,number,null,position);
        try {
            player_dao.insert(player);
        } catch (GenericDAOException e) {
            e.printStackTrace();
        }
    }
}