package studentcompany.sportgest.Team;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import studentcompany.sportgest.domains.PlayerPosition;
import studentcompany.sportgest.domains.Position;
import studentcompany.sportgest.domains.Role;
import studentcompany.sportgest.domains.Team;

public class Squad_Activity_Edit extends AppCompatActivity{

    //DAOs
    private Team_DAO team_dao;
    private Player_DAO player_dao;

    Team team = null;
    int teamID = -1;

    private Spinner tv_player_select;
    private ListView tv_squad_list;

    private boolean changedSquad = false;
    ArrayList<Player> listOutOfTheTeam;
    ArrayList<Player> listInTheTeam;
    ArrayList<Player> allPlayers;
    ArrayList<Player> originalPlayersInTheTeam;

    Button btnRemoveSelected;
    Button btnAddSelected;

    int selectedPlayerPosition=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_squad);

        Bundle b = getIntent().getExtras();
        if(b!=null){
            teamID = b.getInt("id");
        }

        tv_player_select = (Spinner) findViewById(R.id.team_edit_squad_player_select);
        tv_squad_list = (ListView) findViewById(R.id.team_edit_squad_list);
        btnRemoveSelected = (Button) findViewById(R.id.btnRemoveSelected);
        btnAddSelected = (Button) findViewById(R.id.btnAddSelected);


        team_dao = new Team_DAO(this);
        player_dao = new Player_DAO(this);

        try {
            team = team_dao.getById(teamID);
        } catch (GenericDAOException e) {
            e.printStackTrace();
        }
        if(team != null){
            List<Player> allPlayersTmp = null;

            try {
                allPlayersTmp = player_dao.getAll();
            } catch (GenericDAOException e) {
                e.printStackTrace();
            }

            listOutOfTheTeam = new ArrayList<>();
            listInTheTeam = new ArrayList<>();
            allPlayers = new ArrayList<>();
            originalPlayersInTheTeam = new ArrayList<>();

            for(Player p : allPlayersTmp){
                if(p.getTeam()!=null){
                    int teamIDOfThisPlayer = (int)(p.getTeam().getId()+0);
                    if(teamIDOfThisPlayer==teamID){
                        listInTheTeam.add(p);
                        originalPlayersInTheTeam.add(p);
                    }
                    else {
                        listOutOfTheTeam.add(p);
                    }
                }
                else {
                    listOutOfTheTeam.add(p);
                }
            }

            ArrayAdapter<Player> playersOutOfTheTeam = new ArrayAdapter<Player>(this,android.R.layout.simple_list_item_1, listOutOfTheTeam);
            ArrayAdapter<Player> playersInTheTeam = new ArrayAdapter<Player>(this,R.layout.player_listview_for_positions, listInTheTeam);

            tv_player_select.setAdapter(playersOutOfTheTeam);
            tv_squad_list.setAdapter(playersInTheTeam);

            btnRemoveSelected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeSelected();
                }
            });

            btnAddSelected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addSelected();
                }
            });

            tv_squad_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                        selectedPlayerPosition=position;
                }
            });

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar_crud, menu);
        MenuItem editItem = menu.findItem(R.id.Edit);
        MenuItem delItem = menu.findItem(R.id.Delete);
        MenuItem addItem = menu.findItem(R.id.Add);
        MenuItem saveItem = menu.findItem(R.id.Save);
        editItem.setVisible(true);
        delItem.setVisible(false);
        addItem.setVisible(false);
        saveItem.setVisible(false);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);

        switch(item.getItemId())
        {
            //add action
            case R.id.Edit:

                for(Player p : originalPlayersInTheTeam){
                    p.setTeam(null);
                    try {
                        player_dao.update(p);
                    } catch (GenericDAOException e) {
                        e.printStackTrace();
                    }
                }

                for(Player p : listInTheTeam){
                    p.setTeam(team);
                    try {
                        player_dao.update(p);
                    } catch (GenericDAOException e) {
                        e.printStackTrace();
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

    public void removeSelected(){
        if (selectedPlayerPosition>=0 && selectedPlayerPosition < listInTheTeam.size()) {
            Player change = listInTheTeam.remove(selectedPlayerPosition);
            listOutOfTheTeam.add(change);

            ArrayAdapter<Player> adapterOutOfTheTeam = new ArrayAdapter<>(getApplicationContext(),
                    R.layout.player_listview_for_positions, listOutOfTheTeam);
            ArrayAdapter<Player> adapterInTheTeam = new ArrayAdapter<>(getApplicationContext(),
                    R.layout.player_listview_for_positions, listInTheTeam);

            tv_squad_list.setAdapter(adapterInTheTeam);
            tv_player_select.setAdapter(adapterOutOfTheTeam);
        }
    }

    public void addSelected(){
        if (tv_player_select.getSelectedItemPosition()>=0) {
            Player change = (Player)tv_player_select.getSelectedItem();
            listInTheTeam.add(change);
            listOutOfTheTeam.remove(change);

            ArrayAdapter<Player> adapterOutOfTheTeam = new ArrayAdapter<>(getApplicationContext(),
                    R.layout.player_listview_for_positions, listOutOfTheTeam);
            ArrayAdapter<Player> adapterInTheTeam = new ArrayAdapter<>(getApplicationContext(),
                    R.layout.player_listview_for_positions, listInTheTeam);

            tv_squad_list.setAdapter(adapterInTheTeam);
            tv_player_select.setAdapter(adapterOutOfTheTeam);
        }
    }
}
