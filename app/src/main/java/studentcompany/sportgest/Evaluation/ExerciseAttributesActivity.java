package studentcompany.sportgest.Evaluation;
/**
 * Required input info: user_id, team_id and training_id
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import studentcompany.sportgest.Exercises.Exercise_Fragment_List;
import studentcompany.sportgest.R;
import studentcompany.sportgest.Trainings.Training_TestData;
import studentcompany.sportgest.daos.Attribute_DAO;
import studentcompany.sportgest.daos.Attribute_Exercise_DAO;
import studentcompany.sportgest.daos.Exercise_DAO;
import studentcompany.sportgest.daos.Player_DAO;
import studentcompany.sportgest.daos.Record_DAO;
import studentcompany.sportgest.daos.Team_DAO;
import studentcompany.sportgest.daos.Training_DAO;
import studentcompany.sportgest.daos.Training_Exercise_DAO;
import studentcompany.sportgest.daos.User_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Attribute;
import studentcompany.sportgest.domains.Exercise;
import studentcompany.sportgest.domains.Player;
import studentcompany.sportgest.domains.Record;
import studentcompany.sportgest.domains.Team;
import studentcompany.sportgest.domains.Training;
import studentcompany.sportgest.domains.TrainingExercise;
import studentcompany.sportgest.domains.User;

public class ExerciseAttributesActivity extends AppCompatActivity implements Exercise_Fragment_List.OnItemSelected  {

    //Required id
    private long user_id = 0;
    private long team_id = 0;
    private long training_id = 0;

    //DAOs
    private User_DAO user_dao;
    private Training_DAO training_dao;
    private Training_Exercise_DAO training_exercise_dao;
    private Exercise_DAO exercise_dao;
    private Attribute_DAO attribute_dao;
    private Attribute_Exercise_DAO attribute_exercise_dao;
    private Record_DAO record_dao;
    private Player_DAO player_dao;

    //Lists
    private List<Exercise> exerciseList = new ArrayList<>();
    private List<Player> playerList = new ArrayList<>();
    private List<Attribute> exerciseAttributesList = new ArrayList<>();
    private List<Record> evaluations;

    //Objects
    private User user;

    private int currentPos = -1;
    private Menu mOptionsMenu;

    private FragmentManager mFragmentManager;
    private Exercise_Fragment_List mListExercises = new Exercise_Fragment_List();
    private ExerciseAttributes_Fragment mExerciseAttributes = new ExerciseAttributes_Fragment();
    private static final String TAG = "EVALUATE_EXERCISE_ATTRIBUTES_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eval_exercise_attributes_list);

        //DAOs
        user_dao = new User_DAO(this);
        training_dao = new Training_DAO(this);
        training_exercise_dao = new Training_Exercise_DAO(this);
        exercise_dao = new Exercise_DAO(this);
        attribute_dao = new Attribute_DAO(this);
        attribute_exercise_dao = new Attribute_Exercise_DAO(this);
        record_dao = new Record_DAO(this);
        player_dao = new Player_DAO(this);

        //TEST ONLY -> REMOVE
        if(training_dao.numberOfRows() == 0) {
            new Training_TestData(getApplicationContext());
        }

        //get trainingID
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            //get IDs
            user_id = extras.getLong(User_DAO.TABLE_NAME + User_DAO.COLUMN_ID);
            team_id = extras.getLong(Team_DAO.TABLE_NAME + Team_DAO.COLUMN_ID);
            team_id = extras.getLong(Team_DAO.TABLE_NAME + Team_DAO.COLUMN_ID);
            training_id = extras.getLong(Training_DAO.TABLE_NAME + Training_DAO.COLUMN_ID);

            //validation
            if (user_id > 0 && training_id > 0 && team_id > 0) {
                //get the training information
                ArrayList<TrainingExercise> te = new ArrayList<>();
                try {
                    user = user_dao.getById(user_id);
                    te = (ArrayList<TrainingExercise>) training_exercise_dao.getByCriteria(new TrainingExercise(-1, training_dao.getById(training_id), null, -1));
                } catch (GenericDAOException ex) {
                    System.err.println(ExerciseAttributesActivity.class.getName() + " [WARNING] " + ex.toString());
                    Logger.getLogger(ExerciseAttributesActivity.class.getName()).log(Level.WARNING, null, ex);
                }
                //validation
                if(!te.isEmpty()){
                    //get list of exercises
                    for(TrainingExercise trainingExercise : te){
                        if(trainingExercise != null && trainingExercise.getExercise() != null) {
                            exerciseList.add(trainingExercise.getExercise());
                        }
                    }
                } else {
                    System.err.println(ExerciseAttributesActivity.class.getName() + " [WARNING] " + "TrainingExercise list empty");
                }
            } else {
                System.err.println(ExerciseAttributesActivity.class.getName() + " [WARNING] " + "training_id invalid");
            }
        } else {
            System.err.println(ExerciseAttributesActivity.class.getName() + " [WARNING] " + "training_id or team_id does not exist");
        }


        //get list of players (TODO: subtract list of missing players for some training -> Missing DAO)
        try {
            playerList = player_dao.getByCriteria(new Player(new Team(team_id)));
        } catch (GenericDAOException ex) {
            System.err.println(ExerciseAttributesActivity.class.getName() + " [WARNING] " + ex.toString());
            Logger.getLogger(ExerciseAttributesActivity.class.getName()).log(Level.WARNING, null, ex);
        }
        if(playerList.isEmpty()){
            System.err.println(ExerciseAttributesActivity.class.getName() + " [WARNING] " + "playerList empty");
        }

        //fill exercise list
        mListExercises.setExerciseList(exerciseList);

        // Get a reference to the FragmentManager
        mFragmentManager = getSupportFragmentManager();

        // Start a new FragmentTransaction
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        // Add the TitleFragment to the layout
        fragmentTransaction.add(R.id.exercise_list_fragment_container , mListExercises);
        fragmentTransaction.add(R.id.exercise_attributes_players_evaluation_fragment_container, mExerciseAttributes);

        fragmentTransaction.commit();
    }

    /************************************
     ****     Listener Functions     ****
     ************************************/

    @Override
    public void itemSelected(int position, int tag) {
        Exercise exercise = exerciseList.get(position);
        List<TrainingExercise> trainingExerciseList;
        TrainingExercise trainingExercise = null;

        if(exercise != null){
            if(currentPos == -1) {
                mOptionsMenu.findItem(R.id.Forward).setVisible(true);
            }

            currentPos = position;

            try {
                exerciseAttributesList = attribute_exercise_dao.getBySecondId(exercise.getId());
                trainingExerciseList = training_exercise_dao.getByCriteria(new TrainingExercise(-1, training_dao.getById(training_id), exercise, -1));
                if(!trainingExerciseList.isEmpty()){
                    trainingExercise = trainingExerciseList.get(0);
                }
                //get previous/current evaluations
                evaluations = record_dao.getByCriteria(new Record(-1, -1, -1, -1,
                        new Training(training_id),
                        new Exercise(exercise.getId()),
                        null, null, user));
            } catch (GenericDAOException ex){
                ex.printStackTrace();
                exerciseAttributesList = new ArrayList<>();
                evaluations = new ArrayList<>();
            }
            mExerciseAttributes.showExercise(exercise, trainingExercise, exerciseAttributesList, playerList, evaluations);
        }
    }

    /************************************
     ****      Dialog Functions      ****
     ************************************/


    /************************************
     ****       Menu Functions       ****
     ************************************/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_crud, menu);
        mOptionsMenu = menu;
        menu.findItem(R.id.Add).setVisible(false);
        menu.findItem(R.id.Edit).setVisible(false);
        menu.findItem(R.id.Delete).setVisible(false);
        menu.findItem(R.id.Save).setVisible(false);
        menu.findItem(R.id.Forward).setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        Bundle dataBundle;
        // Handle item selection
        switch (item.getItemId()) {
            /*
            case R.id.Add:
                intent = new Intent(this, Exercise_Activity_Create.class);
                startActivityForResult(intent, 0);
                return true;
            */
            case R.id.Forward:
                intent = new Intent(getApplicationContext(), PlayerAttributesActivity.class);

                //put current team ID and training ID in extras
                dataBundle = new Bundle();
                dataBundle.putLong(User_DAO.TABLE_NAME + User_DAO.COLUMN_ID, user_id);
                dataBundle.putLong(Team_DAO.TABLE_NAME + Team_DAO.COLUMN_ID, team_id);
                dataBundle.putLong(Training_DAO.TABLE_NAME + Training_DAO.COLUMN_ID, training_id);
                dataBundle.putLong(Exercise_DAO.TABLE_NAME + Exercise_DAO.COLUMN_ID, exerciseList.get(currentPos).getId());
                //add data
                intent.putExtras(dataBundle);
                //start activity
                startActivityForResult(intent, 0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 0) {
            mExerciseAttributes.clearDetails();
            currentPos = -1;
            mOptionsMenu.findItem(R.id.Forward).setVisible(false);
        }
    }

}