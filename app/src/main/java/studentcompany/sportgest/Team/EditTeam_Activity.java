package studentcompany.sportgest.Team;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.logging.Level;
import java.util.logging.Logger;

import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Player_DAO;
import studentcompany.sportgest.daos.Team_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Player;
import studentcompany.sportgest.domains.Team;

public class EditTeam_Activity extends AppCompatActivity {

    //DAOs
    private Team_DAO team_dao;

    Team team = null;
    int teamID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_team);

        Bundle b = getIntent().getExtras();
        if(b!=null){
            teamID = b.getInt("id");
        }

        EditText tv_name = (EditText) findViewById(R.id.name);
        EditText tv_description = (EditText) findViewById(R.id.description);
        EditText tv_season = (EditText) findViewById(R.id.season);
        CheckBox tv_iscon = (CheckBox) findViewById(R.id.isCom);
        ImageView tv_logo = (ImageView) findViewById(R.id.logo);

        Team teamFromDB=null;
        team_dao = new Team_DAO(this);

        try {
            teamFromDB = team_dao.getById(teamID);
        } catch (GenericDAOException e) {
            e.printStackTrace();
        }
        if (teamFromDB!=null){
            if(teamFromDB.getName()!=null)
                tv_name.setText(teamFromDB.getName());
            else
                tv_name.setText("");
            if(teamFromDB.getDescription()!=null)
                tv_description.setText(teamFromDB.getDescription());
            else
                tv_description.setText("");
            if(teamFromDB.getSeason()!=-1)
                tv_season.setText(Integer.toString(teamFromDB.getSeason()));
            else
                tv_season.setText("");
            if(teamFromDB.getIs_com()!=-1)
                tv_iscon.setSelected(teamFromDB.getIs_com() == 1);
            else
                tv_iscon.setSelected(false);
            //TODO: por a imagem a aparecer
            //if(teamFromDB.getLogo()!=null)

        }

    }

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
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);

        switch(item.getItemId())
        {
            //add action
            case R.id.Edit:

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

                team=new Team(teamID, name, description, logo, season, isCom);

                //insert/update database
                try {
                    if(teamID > 0){
                        team_dao.update(team);
                    } else {
                        team_dao.insert(team);
                    }
                }catch (GenericDAOException ex){
                    System.err.println(CreateTeam_Activity.class.getName() + " [WARNING] " + ex.toString());
                    Logger.getLogger(CreateTeam_Activity.class.getName()).log(Level.WARNING, null, ex);
                }
                Intent intent = new Intent();
                setResult(1,intent);
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
