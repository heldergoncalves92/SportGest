package studentcompany.sportgest.Team;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Team_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Team;

public class Team_Activity_ListView extends AppCompatActivity implements Team_Fragment_List.OnItemSelected{

    private Team_DAO teamDao;
    private List<Team> teams;
    private int currentPos = 0;
    private Menu mOptionsMenu;

    private DialogFragment mDialog;
    private FragmentManager mFragmentManager;
    private Team_Fragment_List mListTeams = new Team_Fragment_List();
    private Team_Fragment_Details mDetailsTeam = new Team_Fragment_Details();
    private static final String TAG = "TEAM_ACTIVITY";

    private final int EDIT_TAG = 19;
    private final int CREATE_TAG = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_activity_list);

        if(savedInstanceState != null)
            currentPos = savedInstanceState.getInt("currentPos");

        try {
            teamDao = new Team_DAO(getApplicationContext());
            teams = teamDao.getAll();
            if(teams.isEmpty()) {

                noElems();
                teams = new ArrayList<Team>();
                //insertTest(teamDao);
                //teams = teamDao.getAll();
            }
            mListTeams.setList(teams);

        } catch (GenericDAOException e) {
            e.printStackTrace();
        }


        // Get a reference to the FragmentManager
        mFragmentManager = getSupportFragmentManager();

        // Start a new FragmentTransaction
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        // Add the TitleFragment to the layout
        fragmentTransaction.add(R.id.title_fragment_container , mListTeams);
        fragmentTransaction.add(R.id.detail_fragment_container, mDetailsTeam);

        fragmentTransaction.commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("currentPos", currentPos);
    }

    public List<String> getNamesList(List<Team> teamsList){

        ArrayList<String> list = new ArrayList<String>();

        for(Team u: teamsList)
            list.add(u.getName());

        return list;
    }

    public void noElems(){

        LinearLayoutCompat l = (LinearLayoutCompat)findViewById(R.id.linear);
        l.setVisibility(View.GONE);

        AppCompatTextView t= (AppCompatTextView)findViewById(R.id.without_elems);
        t.setVisibility(View.VISIBLE);
    }

    private void withElems(){

        LinearLayoutCompat l = (LinearLayoutCompat)findViewById(R.id.linear);
        l.setVisibility(View.VISIBLE);

        AppCompatTextView t= (AppCompatTextView)findViewById(R.id.without_elems);
        t.setVisibility(View.GONE);
    }

    public void removeTeam(){
        mDetailsTeam.clearDetails();

        teamDao.deleteById(teams.get(currentPos).getId());
        mListTeams.removeItem(currentPos);
        currentPos = -1;


        MenuItem item = mOptionsMenu.findItem(R.id.action_del);
        item.setVisible(false);
        item = mOptionsMenu.findItem(R.id.action_edit);
        item.setVisible(false);

        if(teams.isEmpty())
            noElems();
    }
    /************************************
     ****     Listener Functions     ****
     ************************************/

    public void itemSelected(int position, int tag) {
        Team team = teams.get(position);

        if(team != null){
            if(currentPos == -1) {
                MenuItem item = mOptionsMenu.findItem(R.id.action_del);
                item.setVisible(true);

                item = mOptionsMenu.findItem(R.id.action_edit);
                item.setVisible(true);

                mDetailsTeam.showFirstElem();
            }

            currentPos = position;
            mDetailsTeam.showTeam(team);
        }
    }

    /************************************
     ****      Dialog Functions      ****
     ************************************/

    public void DialogDismiss(){
        mDialog.dismiss();
    }

    public static class AlertToDelete_DialogFragment extends DialogFragment {

        public static AlertToDelete_DialogFragment newInstance(){
            return new AlertToDelete_DialogFragment();
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            Resources res = getResources();

            return new AlertDialog.Builder(getActivity())
                    .setMessage(res.getString(R.string.are_you_sure))
                    .setCancelable(false)
                    .setNegativeButton(res.getString(R.string.negative_answer),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Team_Activity_ListView activity = (Team_Activity_ListView) getActivity();
                                    activity.DialogDismiss();
                                }
                            })
                    .setPositiveButton(res.getString(R.string.positive_answer),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Team_Activity_ListView activity = (Team_Activity_ListView) getActivity();
                                    activity.DialogDismiss();
                                    activity.removeTeam();
                                }
                            }).create();
        }
    }

    /************************************
     ****       Menu Functions       ****
     ************************************/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mOptionsMenu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_teams_view, menu);

        //To restore state on Layout Rotation
        if(currentPos != -1 && teams.size()>0) {
            MenuItem item = mOptionsMenu.findItem(R.id.action_del);
            item.setVisible(true);

            item = mOptionsMenu.findItem(R.id.action_edit);
            item.setVisible(true);

            mDetailsTeam.showTeam(teams.get(currentPos));
            mListTeams.select_Item(currentPos);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(this, Team_Activity_Create.class);
                startActivityForResult(intent, CREATE_TAG);
                return true;

            case R.id.action_del:
                mDialog = AlertToDelete_DialogFragment.newInstance();
                mDialog.show(mFragmentManager, "Alert");
                return true;

            case R.id.action_edit:
                Intent intent2 = new Intent(this, Team_Activity_Edit.class);
                intent2.putExtra("id",teams.get(currentPos).getId());
                startActivityForResult(intent2, EDIT_TAG);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /************************************
     ****        Test Functions      ****
     ************************************/

    private void insertTest(Team_DAO t_dao){

        try {
            Team u1 = new Team("Santa Maria","Uma equipa fantástica!!","default.jpg",2015,0);
            Team u2 = new Team("Braga","Uma equipa fantástica!!","default.jpg",2015,0);
            Team u3 = new Team("Gualtar","Uma equipa fantástica!!","default.jpg",2015,0);
            Team u4 = new Team("Fafe","Uma equipa fantástica!!","default.jpg",2015,0);

            t_dao.insert(u1);
            t_dao.insert(u2);
            t_dao.insert(u3);
            t_dao.insert(u4);

        } catch (GenericDAOException e) {
            e.printStackTrace();
        }
    }

    private void testUsers(){

        Team u1 = new Team("Santa Maria","Uma equipa fantástica!!","default.jpg",2015,0);
        Team u2 = new Team("Braga","Uma equipa fantástica!!","default.jpg",2015,0);
        Team u3 = new Team("Gualtar","Uma equipa fantástica!!","default.jpg",2015,0);
        Team u4 = new Team("Fafe","Uma equipa fantástica!!","default.jpg",2015,0);


        teams = new ArrayList<Team>();
        teams.add(u1);
        teams.add(u2);
        teams.add(u3);
        teams.add(u4);

        mListTeams.setList(teams);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Team team = null;
        if (requestCode == EDIT_TAG) {
            if(resultCode == 1){
                try {
                    team=teamDao.getById(teams.get(currentPos).getId());

                    mListTeams.updatePosition(team, currentPos);
                    mDetailsTeam.showTeam(team);

                    Toast.makeText(getApplicationContext(), R.string.updated, Toast.LENGTH_SHORT).show();
                } catch (GenericDAOException e) {
                    e.printStackTrace();
                }
            }
            if(resultCode == 2){
                Toast.makeText(getApplicationContext(), R.string.something_wrong, Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == CREATE_TAG) {
            if(resultCode == 1){
                try {
                    Bundle bundle = data.getExtras();
                    long id = (long) bundle.get("id");
                    int idToSearch = (int) (id + 0);

                    team=teamDao.getById(idToSearch);

                    mListTeams.insert_Item(team);
                    mDetailsTeam.showTeam(team);

                    if(teams.size() == 1)
                        withElems();

                    Toast.makeText(getApplicationContext(), R.string.inserted, Toast.LENGTH_SHORT).show();
                } catch (GenericDAOException e) {
                    e.printStackTrace();
                }
            }
            if(resultCode == 2){
                Toast.makeText(getApplicationContext(), R.string.something_wrong, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
