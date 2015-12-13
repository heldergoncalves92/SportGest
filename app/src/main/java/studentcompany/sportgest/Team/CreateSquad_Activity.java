package studentcompany.sportgest.Team;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Player_DAO;
import studentcompany.sportgest.daos.Team_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Player;
import studentcompany.sportgest.domains.Team;

public class CreateSquad_Activity extends AppCompatActivity {

    //DAOs
    private Team_DAO team_dao;
    private Player_DAO player_dao;

    Team team = null;
    long teamID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_squad);

        team_dao = new Team_DAO(this);
        player_dao = new Player_DAO(this);

        ArrayList<String> playersNotInTeam = new ArrayList<>();
        ArrayList<String> playersInTeam = new ArrayList<>();

        Bundle b = getIntent().getExtras();
        if(b!=null){
            teamID = b.getInt("id");
        }

        try {
            team = team_dao.getById(teamID);
        } catch (GenericDAOException e) {
            e.printStackTrace();
        }

        if(team!=null) {

            Spinner tv_allplayersNotInTeam = (Spinner) findViewById(R.id.player_select);
            ListView tv_playersFromThisTeam = (ListView) findViewById(R.id.squad_list);

            try {
                List<Player> allPlayers = player_dao.getAll();
                for (Player p : allPlayers){
                    if(p.getTeam()!=null){
                        long idTeam = p.getTeam().getId();
                        if(idTeam==teamID){
                            playersInTeam.add(p.getName());
                        }
                        else
                            playersNotInTeam.add(p.getName());
                    }
                    else
                        playersNotInTeam.add(p.getName());
                }

            } catch (GenericDAOException e) {
                e.printStackTrace();
            }

            ArrayAdapter<String> adapterPlayersNotInTeam = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, playersNotInTeam);
            ArrayAdapter<String> adapterPlayersInTeam = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, playersInTeam);

            tv_allplayersNotInTeam.setAdapter(adapterPlayersNotInTeam);
            tv_playersFromThisTeam.setAdapter(adapterPlayersInTeam);

        }

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

                EditText tv_name = (EditText) findViewById(R.id.name);
                EditText tv_description = (EditText) findViewById(R.id.description);
                EditText tv_season = (EditText) findViewById(R.id.season);
                CheckBox tv_iscon = (CheckBox) findViewById(R.id.isCom);
                ImageView tv_logo = (ImageView) findViewById(R.id.logo);

                String name = tv_name.getText().toString();
                String description = tv_description.getText().toString();
                int season = Integer.parseInt(tv_season.getText().toString());
                int isCom = tv_iscon.isChecked()?1:0;
                //ups vai estar a imagem em bitmap ou o path para ela?
                //String logo = tv_photo.get
                String logo="";

                team=new Team(name, description, logo, season, isCom);

                //insert/update database
                try {

                    teamID = team_dao.insert(team);

                }catch (GenericDAOException ex){
                    System.err.println(CreateTeam_Activity.class.getName() + " [WARNING] " + ex.toString());
                    Logger.getLogger(CreateTeam_Activity.class.getName()).log(Level.WARNING, null, ex);
                }

                Intent intent = new Intent();
                intent.putExtra("id",teamID);
                setResult(1, intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
