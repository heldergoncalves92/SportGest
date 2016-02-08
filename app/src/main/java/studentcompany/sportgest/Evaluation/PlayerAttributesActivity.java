package studentcompany.sportgest.Evaluation;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import studentcompany.sportgest.R;

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

public class PlayerAttributesActivity extends AppCompatActivity implements studentcompany.sportgest.Players.Player_Fragment_List.OnItemSelected {

    //Required id
    private long user_id = 0;
    private long team_id = 0;
    private long training_id = 0;
    private long exercise_id = 0;

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
    private User user;
    private Exercise exercise;
    private Training training;
    private Player player;
    private List<Player> playerList = new ArrayList<>();
    private List<Attribute> exerciseAttributesList = new ArrayList<>();
    private List<Record> evaluations = new ArrayList<>();

    private int currentPos = 0;
    private Menu mOptionsMenu;

    private DialogFragment mDialog;
    private FragmentManager mFragmentManager;
    private studentcompany.sportgest.Players.Player_Fragment_List mListPlayer = new studentcompany.sportgest.Players.Player_Fragment_List();
    private studentcompany.sportgest.Evaluation.PlayerAttributes_Fragment mPlayerAttributes = new studentcompany.sportgest.Evaluation.PlayerAttributes_Fragment();
    private static final String TAG = "EVALUATE_PLAYER_ATTRIBUTES_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eval_player_attributes);

        //DAOs
        user_dao = new User_DAO(this);
        training_dao = new Training_DAO(this);
        training_exercise_dao = new Training_Exercise_DAO(this);
        exercise_dao = new Exercise_DAO(this);
        attribute_dao = new Attribute_DAO(this);
        attribute_exercise_dao = new Attribute_Exercise_DAO(this);
        record_dao = new Record_DAO(this);
        player_dao = new Player_DAO(this);


        //get trainingID
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            //get IDs
            user_id = extras.getLong(User_DAO.TABLE_NAME + User_DAO.COLUMN_ID);
            team_id = extras.getLong(Team_DAO.TABLE_NAME + Team_DAO.COLUMN_ID);
            training_id = extras.getLong(Training_DAO.TABLE_NAME + Training_DAO.COLUMN_ID);
            exercise_id = extras.getLong(Exercise_DAO.TABLE_NAME + Exercise_DAO.COLUMN_ID);

            //validation
            if (user_id > 0 && training_id > 0 && team_id > 0 && exercise_id > 0) {
                //get the exercise information
                try {
                    user = user_dao.getById(user_id);
                    exercise = exercise_dao.getById(exercise_id);
                    training = training_dao.getById(training_id);
                    playerList = player_dao.getByCriteria(new Player(new Team(team_id)));
                    exerciseAttributesList = attribute_exercise_dao.getBySecondId(exercise_id);
                    evaluations = record_dao.getByCriteria(new Record(-1, -1, -1, -1,
                            training,
                            exercise,
                            null, null, user));
                } catch (GenericDAOException ex) {
                    System.err.println(ExerciseAttributesActivity.class.getName() + " [WARNING] " + ex.toString());
                    Logger.getLogger(ExerciseAttributesActivity.class.getName()).log(Level.WARNING, null, ex);
                }
            } else {
                System.err.println(ExerciseAttributesActivity.class.getName() + " [WARNING] " + "training_id or team_id or exercise_id invalid");
            }
        } else {
            System.err.println(ExerciseAttributesActivity.class.getName() + " [WARNING] " + "NO EXTRAS!!!");
            user_id = 0;
            team_id = 0;
            training_id = 0;
            exercise_id = 0;
        }

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
        outState.putInt("baseTeamID", (int)team_id);
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

            mPlayerAttributes.showEvaluations(exerciseAttributesList, evaluations, player);
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
                //Get inserted values
                HashMap<Long, Integer> quantitativeHashMap = mPlayerAttributes.getQuantitativeHashMap();
                HashMap<Long, Integer> qualitativeHashMap = mPlayerAttributes.getQualitativeHashMap();
                HashMap<Long, Float> ratioPartialHashMap = mPlayerAttributes.getRatioPartialHashMap();
                HashMap<Long, Float> ratioTotalHashMap = mPlayerAttributes.getRatioTotalHashMap();
                // validate inputs
                Attribute missingEvaluation;
                for(Attribute a:exerciseAttributesList){
                    missingEvaluation = null;
                    switch (a.getType()){
                        case Attribute.QUANTITATIVE:
                            if(!quantitativeHashMap.containsKey(a.getId())){
                                missingEvaluation = a;
                            }
                            break;
                        case Attribute.QUALITATIVE:
                            if(!qualitativeHashMap.containsKey(a.getId())){
                                missingEvaluation = a;
                            }
                            break;
                        case Attribute.RATIO:
                            if(!ratioPartialHashMap.containsKey(a.getId()) || !ratioTotalHashMap.containsKey(a.getId())){
                                missingEvaluation = a;
                            }
                            break;
                    }

                    //if some attribute was not evaluated -> break and dialog
                    if(missingEvaluation != null){
                        Toast.makeText(getApplicationContext(),"Failed: "+ missingEvaluation.getName() + " not evaluated", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }

                //only reach this space if it was validated
                for(Attribute att:exerciseAttributesList) {
                    Record newRecord = new Record(-1,1111/*TODO set time*/,0/*modified bellow*/,1,training,exercise,att,player,user);

                    switch (att.getType()) {
                        case Attribute.QUANTITATIVE:
                            newRecord.setValue(quantitativeHashMap.get(att.getId()));
                            break;
                        case Attribute.QUALITATIVE:
                            newRecord.setValue(qualitativeHashMap.get(att.getId()));
                            break;
                        case Attribute.RATIO:
                            newRecord.setValue(ratioPartialHashMap.get(att.getId())/ratioTotalHashMap.get(att.getId()));
                            break;
                    }
                    try {
                        record_dao.insert(newRecord);
                    } catch (GenericDAOException ex) {
                        System.err.println(ExerciseAttributesActivity.class.getName() + " [WARNING] " + ex.toString());
                        Logger.getLogger(ExerciseAttributesActivity.class.getName()).log(Level.WARNING, null, ex);
                    }
                }

                finish();

                return true;

            case R.id.Delete:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.delete_confirmation)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //TODO: delete
                                try {
                                    List<Record> playerEvaluations = record_dao.getByCriteria(new Record(-1,-1,-1,-1,training,exercise,null,player,user));
                                    for(Record r: playerEvaluations){
                                        record_dao.deleteById(r.getId());
                                    }
                                } catch (GenericDAOException e) {
                                    e.printStackTrace();
                                }
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
