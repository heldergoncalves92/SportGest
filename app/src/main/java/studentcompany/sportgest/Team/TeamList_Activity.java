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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import studentcompany.sportgest.Players.EditPlayer_Activity;
import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Team_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Team;
import studentcompany.sportgest.domains.User;

public class TeamList_Activity extends AppCompatActivity implements ListTeam_Fragment.OnItemSelected{

    private Team_DAO teamDao;
    private List<Team> teams;
    private int currentPos = -1;
    private Menu mOptionsMenu;

    private DialogFragment mDialog;
    private FragmentManager mFragmentManager;
    private ListTeam_Fragment mListTeams = new ListTeam_Fragment();
    private DetailsTeam_Fragment mDetailsTeam = new DetailsTeam_Fragment();
    private static final String TAG = "TEAM_ACTIVITY";

    private final int EDIT_TAG = 19;
    private final int CREATE_TAG = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_list);

        if(savedInstanceState != null)
            currentPos = savedInstanceState.getInt("currentPos");

        //this.testUsers();
        try {
            teamDao = new Team_DAO(getApplicationContext());
            teams = teamDao.getAll();
            if(teams.isEmpty()) {

                noElems();
                //insertTest(teamDao);
                //teams = teamDao.getAll();
            }
            mListTeams.setList(getNamesList(teams));

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

        LinearLayout l = (LinearLayout)findViewById(R.id.linear);
        l.setVisibility(View.GONE);

        TextView t= (TextView)findViewById(R.id.without_elems);
        t.setVisibility(View.VISIBLE);
    }

    public void removeTeam(){
        mDetailsTeam.clearDetails();
        mListTeams.removeItem(currentPos);

        teamDao.deleteById(teams.get(currentPos).getId());
        teams.remove(currentPos);

        currentPos = -1;
        MenuItem item = mOptionsMenu.findItem(R.id.action_del);
        item.setVisible(false);

        if(teams.isEmpty())
            noElems();
    }
    /************************************
     ****     Listener Functions     ****
     ************************************/

    public void itemSelected(int position) {
        Team team = teams.get(position);

        if(team != null){
            if(currentPos == -1) {
                MenuItem item = mOptionsMenu.findItem(R.id.action_del);
                item.setVisible(true);

                item = mOptionsMenu.findItem(R.id.action_edit);
                item.setVisible(true);

                currentPos = position;
                mDetailsTeam.showTeam(team);
            }
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
                                    TeamList_Activity activity = (TeamList_Activity) getActivity();
                                    activity.DialogDismiss();
                                }
                            })
                    .setPositiveButton(res.getString(R.string.positive_answer),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    TeamList_Activity activity = (TeamList_Activity) getActivity();
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
        inflater.inflate(R.menu.menu_users_view, menu);

        //To restore state on Layout Rotation
        if(currentPos != -1) {
            MenuItem item = mOptionsMenu.findItem(R.id.action_del);
            item.setVisible(true);
            mDetailsTeam.showTeam(teams.get(currentPos));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(this, CreateTeam_Activity.class);
                startActivityForResult(intent, CREATE_TAG);
                return true;

            case R.id.action_del:
                mDialog = AlertToDelete_DialogFragment.newInstance();
                mDialog.show(mFragmentManager, "Alert");
                return true;

            case R.id.action_edit:
                Intent intent2 = new Intent(this, EditTeam_Activity.class);
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

        mListTeams.setList(getNamesList(teams));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Team team = null;
        if (requestCode == EDIT_TAG) {
            if(resultCode == 1){
                try {
                    team=teamDao.getById(teams.get(currentPos).getId());
                    teams.set(currentPos,team);
                    mDetailsTeam.showTeam(team);
                } catch (GenericDAOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (requestCode == CREATE_TAG) {
            if(resultCode == 1){
                try {
                    Bundle bundle = data.getExtras();
                    long id = (long) bundle.get("id");
                    int idToSearch = (int) (id + 0);
                    //System.out.println("ID TO SEARCH "+idToSearch);
                    team=teamDao.getById(idToSearch);
                    teams.add(team);
                    mDetailsTeam.showTeam(team);
                    //mDetailsPlayer.
                } catch (GenericDAOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
