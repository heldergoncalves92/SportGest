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

    private ArrayList<Player> playersInTeam = null;

    Team team = null;
    int teamID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_squad);

        team_dao = new Team_DAO(this);
        player_dao = new Player_DAO(this);

        ArrayList<String> playersNotInTeamString = new ArrayList<>();
        ArrayList<String> playersInTeamString = new ArrayList<>();

        Bundle b = getIntent().getExtras();
        if(b!=null){
            teamID = (int)(b.getLong("id")+0);
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
                            playersInTeamString.add(p.getName());
                            playersInTeam.add(p);
                        }
                        else
                            playersNotInTeamString.add(p.getName());
                    }
                    else
                        playersNotInTeamString.add(p.getName());
                }

            } catch (GenericDAOException e) {
                e.printStackTrace();
            }

            ArrayAdapter<String> adapterPlayersNotInTeam = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, playersNotInTeamString);
            ArrayAdapter<String> adapterPlayersInTeam = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, playersInTeamString);

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

                for(Player p : playersInTeam) {
                    p.setTeam(team);
                    try {
                        player_dao.update(p);
                    } catch (GenericDAOException e) {
                        e.printStackTrace();
                    }
                }

                Intent intent = new Intent();
                setResult(1, intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
