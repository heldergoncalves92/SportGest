package studentcompany.sportgest.Team;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Player_DAO;
import studentcompany.sportgest.daos.Team_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Player;
import studentcompany.sportgest.domains.Role;
import studentcompany.sportgest.domains.Team;

public class EditSquad_Activity extends AppCompatActivity{

    //DAOs
    private Team_DAO team_dao;
    private Player_DAO player_dao;

    Team team = null;
    int teamID = -1;

    private Spinner tv_player_select;
    private ListView tv_squad_list;

    private ArrayList<Player> originalPlayersInTheTeam = null;
    private boolean changedSquad = false;
    ArrayList<Player> listOutOfTheTeam;
    ArrayList<Player> listInTheTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_team);

        Bundle b = getIntent().getExtras();
        if(b!=null){
            teamID = (int)(b.getLong("id")+0);
        }

        tv_player_select = (Spinner) findViewById(R.id.player_select);

        tv_squad_list = (ListView) findViewById(R.id.squad_list);

        team_dao = new Team_DAO(this);
        player_dao = new Player_DAO(this);

        try {
            team = team_dao.getById(teamID);
        } catch (GenericDAOException e) {
            e.printStackTrace();
        }
        if(team != null){
            List<Player> allPlayers = null;

            try {
                allPlayers = player_dao.getAll();
            } catch (GenericDAOException e) {
                e.printStackTrace();
            }

            listOutOfTheTeam = new ArrayList<>();
            listInTheTeam = new ArrayList<>();
            originalPlayersInTheTeam = new ArrayList<>();
            final ArrayList<Player> originalPlayersOutOfTheTeam = new ArrayList<>();

            for(Player p : allPlayers){
                if(p.getTeam()!=null){
                    int teamIDOfThisPlayer = (int)(p.getTeam().getId()+0);
                    if(teamIDOfThisPlayer==teamID){
                        listInTheTeam.add(p);
                        originalPlayersInTheTeam.add(p);
                    }
                    else {
                        listOutOfTheTeam.add(p);
                        originalPlayersOutOfTheTeam.add(p);
                    }
                }
                else {
                    listOutOfTheTeam.add(p);
                    originalPlayersOutOfTheTeam.add(p);
                }
            }

            ArrayAdapter<Player> playersOutOfTheTeam = new ArrayAdapter<Player>(this,android.R.layout.simple_list_item_1, listOutOfTheTeam);
            ArrayAdapter<Player> playersInTheTeam = new ArrayAdapter<Player>(this,android.R.layout.simple_list_item_1, listInTheTeam);
            tv_player_select.setAdapter(playersOutOfTheTeam);
            tv_squad_list.setAdapter(playersInTheTeam);

            tv_player_select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Player p = (Player) tv_player_select.getSelectedItem();
                    listOutOfTheTeam.remove(p);
                    listInTheTeam.add(p);
                    changedSquad = true;
                }

                public void onNothingSelected(AdapterView<?> adapterView) {
                    return;
                }
            });

            tv_squad_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Player p = (Player) tv_squad_list.getSelectedItem();
                    listOutOfTheTeam.add(p);
                    listInTheTeam.remove(p);
                    changedSquad = true;
                }

                public void onNothingSelected(AdapterView<?> adapterView) {
                    return;
                }
            });

        }

    }

    /*
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
    }*/

    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);

        switch(item.getItemId())
        {
            //add action
            case R.id.Save:

                ArrayList<Player> actualInTheTeam = new ArrayList<>();
                ListAdapter adapterPlayersInTheTeam = tv_squad_list.getAdapter();
                int adapterLen = adapterPlayersInTheTeam.getCount();
                for(int i=0;i<adapterLen;i++){
                    Player p = (Player) adapterPlayersInTheTeam.getItem(i);
                    actualInTheTeam.add(p);
                }

                for(Player p : actualInTheTeam){
                    p.setTeam(team);
                    try {
                        player_dao.update(p);
                    } catch (GenericDAOException e) {
                        e.printStackTrace();
                    }
                }

                for(Player p : originalPlayersInTheTeam){
                    if(!actualInTheTeam.contains(p)) {
                        p.setTeam(null);
                        try {
                            player_dao.update(p);
                        } catch (GenericDAOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                Intent intent = new Intent();
                setResult(changedSquad?1:0,intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onStop(){
        super.onStop();

        setResult(0);
    }
}
