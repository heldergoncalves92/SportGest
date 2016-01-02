package studentcompany.sportgest.Players;

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
import android.widget.NumberPicker;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Player_DAO;
import studentcompany.sportgest.daos.Player_Position_DAO;
import studentcompany.sportgest.daos.Position_DAO;
import studentcompany.sportgest.daos.Team_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Player;
import studentcompany.sportgest.domains.PlayerPosition;
import studentcompany.sportgest.domains.Position;
import studentcompany.sportgest.domains.Role;
import studentcompany.sportgest.domains.Team;

public class EditPosition_Activity extends AppCompatActivity{

    //DAOs
    private Team_DAO team_dao;
    private Player_DAO player_dao;
    private Player_Position_DAO player_position_dao;
    private Position_DAO position_dao;

    Player player = null;
    int playerID = -1;

    private Spinner tv_position_select;
    private ListView tv_positions_list;
    private NumberPicker tv_numberPicker;

    private boolean changedSquad = false;
    //ArrayList<PlayerPosition> listOutOfThePlayer;
    ArrayList<PlayerPosition> listInThePlayer;
    ArrayList<Position> allPositions;
    ArrayList<Position> availPositions;
    ArrayList<PlayerPosition> originalPositionsInThePlayer;

    Button btnRemoveSelected;
    Button btnAddSelected;

    int selectedPlayerPosition=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.position_chose);

        Bundle b = getIntent().getExtras();
        if(b!=null){
            playerID = b.getInt("id");
        }

        tv_position_select = (Spinner) findViewById(R.id.player_edit_position_player_select);
        tv_positions_list = (ListView) findViewById(R.id.player_edit_position_list);
        tv_numberPicker = (NumberPicker) findViewById(R.id.numberPicker);
        tv_numberPicker.setMaxValue(10);
        tv_numberPicker.setMinValue(1);
        btnRemoveSelected = (Button) findViewById(R.id.btnRemoveSelected);
        btnAddSelected = (Button) findViewById(R.id.btnAddSelected);

        team_dao = new Team_DAO(this);
        player_dao = new Player_DAO(this);
        player_position_dao = new Player_Position_DAO(this);
        position_dao = new Position_DAO(this);

        try {
            player = player_dao.getById(playerID);
        } catch (GenericDAOException e) {
            e.printStackTrace();
        }
        if(player != null){
            List<PlayerPosition> allPlayerPositionsTmp = null;
            List<Position> allPositionsTmp = null;

            PlayerPosition playerPositionToSearch = new PlayerPosition(player,null,-1);
            try {
                allPlayerPositionsTmp = player_position_dao.getByCriteria(playerPositionToSearch);
                allPositionsTmp = position_dao.getAll();
            } catch (GenericDAOException e) {
                e.printStackTrace();
            }

            //listOutOfThePlayer = new ArrayList<>();
            listInThePlayer = new ArrayList<>();
            allPositions = new ArrayList<>();
            originalPositionsInThePlayer = new ArrayList<>();
            availPositions = new ArrayList<>();

            for(Position p : allPositionsTmp){
                allPositions.add(p);
                availPositions.add(p);
            }

            for(PlayerPosition p : allPlayerPositionsTmp) {
                originalPositionsInThePlayer.add(p);
                listInThePlayer.add(p);
                availPositions.remove(p.getPosition());
            }

            ArrayAdapter<Position> positionsOutOfThePlayer = new ArrayAdapter<Position>(this,android.R.layout.simple_list_item_1, availPositions);
            ArrayAdapter<PlayerPosition> positionsInThePlayer = new ArrayAdapter<PlayerPosition>(this,R.layout.player_listview_for_positions, listInThePlayer);

            tv_position_select.setAdapter(positionsOutOfThePlayer);
            tv_positions_list.setAdapter(positionsInThePlayer);

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

            tv_positions_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                    selectedPlayerPosition = position;
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

                for(PlayerPosition p : originalPositionsInThePlayer){
                    try {
                        player_position_dao.delete(p);
                    } catch (GenericDAOException e) {
                        e.printStackTrace();
                    }
                }

                for(PlayerPosition p : listInThePlayer){
                    try {
                        player_position_dao.insert(p);
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
        if (selectedPlayerPosition>=0 && selectedPlayerPosition < listInThePlayer.size()) {
            PlayerPosition change = listInThePlayer.remove(selectedPlayerPosition);
            availPositions.add(change.getPosition());

            ArrayAdapter<Position> adapterOutOfThePlayer = new ArrayAdapter<>(getApplicationContext(),
                    R.layout.player_listview_for_positions, availPositions);
            ArrayAdapter<PlayerPosition> adapterInThePlayer = new ArrayAdapter<>(getApplicationContext(),
                    R.layout.player_listview_for_positions, listInThePlayer);

            tv_positions_list.setAdapter(adapterInThePlayer);
            tv_position_select.setAdapter(adapterOutOfThePlayer);
        }
    }

    public void addSelected(){
        if (tv_position_select.getSelectedItemPosition()>=0) {
            Position change = (Position)tv_position_select.getSelectedItem();
            availPositions.remove(change);
            PlayerPosition ppToAdd = new PlayerPosition(player,change,tv_numberPicker.getValue());
            listInThePlayer.add(ppToAdd);

            ArrayAdapter<Position> adapterOutOfThePlayer = new ArrayAdapter<>(getApplicationContext(),
                    R.layout.player_listview_for_positions, availPositions);
            ArrayAdapter<PlayerPosition> adapterInThePlayer = new ArrayAdapter<>(getApplicationContext(),
                    R.layout.player_listview_for_positions, listInThePlayer);

            tv_positions_list.setAdapter(adapterInThePlayer);
            tv_position_select.setAdapter(adapterOutOfThePlayer);
        }
    }
}
