package studentcompany.sportgest.Team;


import android.content.Intent;
import android.net.Uri;
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

public class DetailsSquad_Fragment extends AppCompatActivity {


    private static final String TAG = "DETAILS_SQUAD_FRAGMENT";
    private ListView tv_squad_list;

    private int teamID = -1;
    private Team_DAO team_dao;
    private Player_DAO player_dao;

    public DetailsSquad_Fragment() {
        // Required empty public constructor
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_squad);

        tv_squad_list = (ListView) findViewById(R.id.squad_list);

        Bundle b = getIntent().getExtras();
        if(b!=null){
            teamID = b.getInt("id");
        }

        team_dao = new Team_DAO(this);
        player_dao = new Player_DAO(this);

        ArrayList<String> playersInTeam = new ArrayList<>();
        //ArrayList<String> playersOutTeam = new ArrayList<>();

        try {
            List<Player> allPlayers = player_dao.getAll();

            if(allPlayers!=null){

                for(Player p : allPlayers){
                    if(p.getTeam()!=null) {
                        if (p.getTeam().getId() == teamID)
                            playersInTeam.add(p.getName());
                        /*else
                            playersOutTeam.add(p.getName());
                    }
                    else
                        playersInTeam.add(p.getName());*/
                    }

                }
            }
        } catch (GenericDAOException e) {
            e.printStackTrace();
        }

        if(!playersInTeam.isEmpty()){
            ArrayAdapter<String> adapterWithPlayers = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, playersInTeam);
            tv_squad_list.setAdapter(adapterWithPlayers);
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
        addItem.setVisible(false);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);

        switch(item.getItemId())
        {
            //add action
            case R.id.Add:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
