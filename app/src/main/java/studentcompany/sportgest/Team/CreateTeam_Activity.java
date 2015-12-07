package studentcompany.sportgest.Team;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Player_DAO;
import studentcompany.sportgest.daos.Team_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Player;
import studentcompany.sportgest.domains.Position;
import studentcompany.sportgest.domains.Team;

public class CreateTeam_Activity extends AppCompatActivity {

    //DAOs
    private Team_DAO team_dao;

    Team team = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_player);

        EditText tv_name = (EditText) findViewById(R.id.name);
        EditText tv_description = (EditText) findViewById(R.id.description);
        EditText tv_season = (EditText) findViewById(R.id.season);
        CheckBox tv_iscon = (CheckBox) findViewById(R.id.isCom);
        ImageView tv_logo = (ImageView) findViewById(R.id.logo);

        team_dao = new Team_DAO(this);

        String name = tv_name.getText().toString();
        String description = tv_description.getText().toString();
        int season = Integer.parseInt(tv_season.getText().toString());
        int isCom = tv_iscon.isChecked()?1:0;
        //ups vai estar a imagem em bitmap ou o path para ela?
        //String logo = tv_photo.get
        String logo="";

        team=new Team(name, description, logo, season, isCom);
        try {
            team_dao.insert(team);
        } catch (GenericDAOException e) {
            e.printStackTrace();
        }
    }
}
