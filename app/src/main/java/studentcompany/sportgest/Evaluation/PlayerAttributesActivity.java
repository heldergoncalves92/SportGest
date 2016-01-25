package studentcompany.sportgest.Evaluation;

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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import studentcompany.sportgest.R;
import studentcompany.sportgest.Trainings.TrainingTestData;
import studentcompany.sportgest.daos.Attribute_DAO;
import studentcompany.sportgest.daos.Attribute_Exercise_DAO;
import studentcompany.sportgest.daos.Exercise_DAO;
import studentcompany.sportgest.daos.Pair;
import studentcompany.sportgest.daos.Player_DAO;
import studentcompany.sportgest.daos.Record_DAO;
import studentcompany.sportgest.daos.Team_DAO;
import studentcompany.sportgest.daos.Training_DAO;
import studentcompany.sportgest.daos.Training_Exercise_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Attribute;
import studentcompany.sportgest.domains.Exercise;
import studentcompany.sportgest.domains.Player;
import studentcompany.sportgest.domains.PlayerPosition;
import studentcompany.sportgest.domains.Position;
import studentcompany.sportgest.domains.Record;
import studentcompany.sportgest.domains.Team;
import studentcompany.sportgest.domains.Training;
import studentcompany.sportgest.domains.TrainingExercise;

public class PlayerAttributesActivity extends AppCompatActivity implements studentcompany.sportgest.Players.Player_Fragment_List.OnItemSelected {

    //Required id
    private long team_id = 0;
    private long training_id = 0;
    private long exercise_id = 0;

    //DAOs
    private Training_DAO training_dao;
    private Training_Exercise_DAO training_exercise_dao;
    private Exercise_DAO exercise_dao;
    private Attribute_DAO attribute_dao;
    private Attribute_Exercise_DAO attribute_exercise_dao;
    private Record_DAO record_dao;
    private Player_DAO player_dao;

    //Lists
    private Exercise exercise;
    private Player player;
    private List<Player> playerList = new ArrayList<>();
    private List<Attribute> exerciseAttributesList = new ArrayList<>();
    private List<Record> evaluations = new ArrayList<>();

    private int currentPos = 0;
    private Menu mOptionsMenu;

    private int baseTeamID;
    private DialogFragment mDialog;
    private FragmentManager mFragmentManager;
    private studentcompany.sportgest.Players.Player_Fragment_List mListPlayer = new studentcompany.sportgest.Players.Player_Fragment_List();
    private studentcompany.sportgest.Evaluation.PlayerAttributes_Fragment mPlayerAttributes = new studentcompany.sportgest.Evaluation.PlayerAttributes_Fragment();
    private static final String TAG = "PLAYERS_EVALUATION_ACTIVITY";

    private final int EDIT_TAG = 19;
    private final int CREATE_TAG = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eval_player_attributes);

        //DAOs
        training_dao = new Training_DAO(this);
        training_exercise_dao = new Training_Exercise_DAO(this);
        exercise_dao = new Exercise_DAO(this);
        attribute_dao = new Attribute_DAO(this);
        attribute_exercise_dao = new Attribute_Exercise_DAO(this);
        record_dao = new Record_DAO(this);
        player_dao = new Player_DAO(this);

        //TEST ONLY -> REMOVE
        try {
            if(training_dao.getAll().size() == 0) {
                new TrainingTestData(getApplicationContext());
            }
        } catch (GenericDAOException e) {
            e.printStackTrace();
        }

        //get trainingID
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            //get IDs
            team_id = extras.getLong(Team_DAO.TABLE_NAME + Team_DAO.COLUMN_ID);
            training_id = extras.getLong(Training_DAO.TABLE_NAME + Training_DAO.COLUMN_ID);
            exercise_id = extras.getLong(Exercise_DAO.TABLE_NAME + Exercise_DAO.COLUMN_ID);

            //validation
            if (training_id > 0 && team_id > 0 && exercise_id > 0) {
                //get the exercise information
                try {
                    exercise = exercise_dao.getById(exercise_id);
                    playerList = player_dao.getByCriteria(new Player(new Team(team_id)));
                    exerciseAttributesList = attribute_exercise_dao.getBySecondId(exercise_id);
                    evaluations = record_dao.getByCriteria(new Record(-1, -1, -1, -1,
                            new Training(training_id),
                            new Exercise(exercise.getId()),
                            null, null, null));
                } catch (GenericDAOException ex) {
                    System.err.println(ExerciseAttributesActivity.class.getName() + " [WARNING] " + ex.toString());
                    Logger.getLogger(ExerciseAttributesActivity.class.getName()).log(Level.WARNING, null, ex);
                }
            } else {
                System.err.println(ExerciseAttributesActivity.class.getName() + " [WARNING] " + "training_id or team_id or exercise_id invalid");
            }
        } else {
            System.err.println(ExerciseAttributesActivity.class.getName() + " [WARNING] " + "NO EXTRAS!!!");
            team_id = 0;
            training_id = 0;
            exercise_id = 0;
        }
/*
        try {
            playerDao = new Player_DAO(getApplicationContext());
            players = playerDao.getAll();//playerDao.getByCriteria(new Player(new Team(baseTeamID)));
            //players = playerDao.getAll();
            if(players.isEmpty()) {

                //noElems();
                insertUserTest(playerDao);
                //players = playerDao.getAll();
            }
            mListPlayer.setList(players);

        } catch (GenericDAOException e) {
            e.printStackTrace();
        }
*/
        mListPlayer.setList(playerList);

        // Get a reference to the FragmentManager
        mFragmentManager = getSupportFragmentManager();

        // Start a new FragmentTransaction
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        // Add the TitleFragment to the layout
        fragmentTransaction.add(R.id.title_fragment_container , mListPlayer);
        fragmentTransaction.add(R.id.detail_fragment_container, mPlayerAttributes);

        fragmentTransaction.commit();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("baseTeamID", baseTeamID);
        outState.putInt("currentPos", currentPos);
    }

    public void noElems(){

        LinearLayout l = (LinearLayout)findViewById(R.id.linear);
        l.setVisibility(View.GONE);

        TextView t= (TextView)findViewById(R.id.without_elems);
        t.setVisibility(View.VISIBLE);
    }

    /************************************
     ****     Listener Functions     ****
     ************************************/

    @Override
    public void itemSelected(int position, int tag) {
        player = playerList.get(position);
        List<TrainingExercise> trainingExerciseList;
        TrainingExercise trainingExercise = null;

        if(player != null){
            if(currentPos == -1) {
                mOptionsMenu.findItem(R.id.Delete).setVisible(true);
                mOptionsMenu.findItem(R.id.Edit).setVisible(true);
            }

            currentPos = position;

            mPlayerAttributes.showEvaluations(exerciseAttributesList);
            //showExercise(exercise, trainingExercise, exerciseAttributesList, playerList, evaluations);
        }
    }


    /************************************
     ****       Menu Functions       ****
     ************************************/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mOptionsMenu = menu;
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar_crud, menu);

        //if its for updating an entry -> Save & Delete
        if(!evaluations.isEmpty()) {
            menu.findItem(R.id.Add).setVisible(false);
            menu.findItem(R.id.Edit).setVisible(false);
        } else {//for inserting a new entry -> Save
            menu.findItem(R.id.Add).setVisible(false);
            menu.findItem(R.id.Edit).setVisible(false);
            menu.findItem(R.id.Delete).setVisible(false);
        }

//        //To restore state on Layout Rotation
//        if(currentPos != -1) {
//            if(playerList.size()>0) {
//                mPlayerAttributes.showEvaluations(exerciseAttributesList);
//            }
//        }

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        boolean result = false;


        switch(item.getItemId())
        {
            //add action
            case R.id.Save:
                // TODO: validate inputs

                finish();

                return true;

            case R.id.Delete:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.delete_confirmation)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //TODO: delete
                                Toast.makeText(getApplicationContext(), R.string.delete_sucessful, Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                AlertDialog d = builder.create();
                d.setTitle(R.string.are_you_sure);
                d.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
