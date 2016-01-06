package studentcompany.sportgest.Evaluation;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import studentcompany.sportgest.Exercises.CreateExerciseActivity;
import studentcompany.sportgest.Exercises.DetailsExercise_Fragment;
import studentcompany.sportgest.Exercises.Exercises_Adapter;
import studentcompany.sportgest.Exercises.ListExercise_Fragment;
import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Attribute_DAO;
import studentcompany.sportgest.daos.Attribute_Exercise_DAO;
import studentcompany.sportgest.daos.Exercise_DAO;
import studentcompany.sportgest.daos.Pair;
import studentcompany.sportgest.daos.Player_DAO;
import studentcompany.sportgest.daos.Record_DAO;
import studentcompany.sportgest.daos.Training_DAO;
import studentcompany.sportgest.daos.Training_Exercise_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Attribute;
import studentcompany.sportgest.domains.Exercise;
import studentcompany.sportgest.domains.Player;
import studentcompany.sportgest.domains.Training;
import studentcompany.sportgest.domains.TrainingExercise;

public class ExerciseAttributesActivity extends AppCompatActivity implements ListExercise_Fragment.OnItemSelected  {

    //Required id
    private long training_id = 0;

    //DAOs
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

    //Layouts
    private LayoutInflater inflater;
    private TextInputLayout til;

    private int currentPos = -1;
    private Menu mOptionsMenu;

    private DialogFragment mDialog;
    private FragmentManager mFragmentManager;
    private ListExercise_Fragment mListExercises = new ListExercise_Fragment();
    private static final String TAG = "EVALUATE_EXERCISE_ATTRIBUTES_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eval_exercise_attributes_list);

        //DAOs
        training_dao = new Training_DAO(this);
        training_exercise_dao = new Training_Exercise_DAO(this);
        exercise_dao = new Exercise_DAO(this);
        attribute_dao = new Attribute_DAO(this);
        attribute_exercise_dao = new Attribute_Exercise_DAO(this);
        record_dao = new Record_DAO(this);
        player_dao = new Player_DAO(this);

        //Layouts
        inflater = getLayoutInflater();
        til = (TextInputLayout) findViewById(R.id.text_layout_selected_attributes);

        Training training;

        //get trainingID
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            //get training ID
            training_id = extras.getLong(Training_DAO.TABLE_NAME + Training_DAO.COLUMN_ID);

            //validation
            if (training_id > 0) {
                //get the training information
                ArrayList<TrainingExercise> te = new ArrayList<>();
                try {
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
            System.err.println(ExerciseAttributesActivity.class.getName() + " [WARNING] " + "training_id does not exist");
        }


        //get list of players (TODO: subtract list of missing players for some training -> Missing DAO)
        try {
            playerList = (ArrayList<Player>) player_dao.getAll();
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

        fragmentTransaction.commit();
    }

    public List<String> getNamesList(List<Exercise> exerciseList){
        ArrayList<String> list = new ArrayList<>();

        for(Exercise e: exerciseList)
            list.add(e.getTitle());
        Collections.sort(list);
        return list;
    }

    public List<String> getAttributesNamesList(List<Attribute> attributeList){
        ArrayList<String> list = new ArrayList<>();

        for(Attribute a: attributeList)
            list.add(a.getName());
        Collections.sort(list);
        return list;
    }

    public void removeExercise(){
        //TODO: mDetailsExercise.clearDetails();
        mListExercises.removeItem(currentPos);

        try {
            Exercise exercise = exercise_dao.getById(exerciseList.get(currentPos).getId());
            if(exercise != null) {
                //remove list of attributes
                ArrayList<Attribute> previousExerciseAttributes = (ArrayList) attribute_exercise_dao.getBySecondId(exercise.getId());
                for (Attribute a : previousExerciseAttributes) {
                    attribute_exercise_dao.delete(new Pair<>(a, exercise));
                }
                //remove exercise
                exercise_dao.deleteById(exerciseList.get(currentPos).getId());
            }
        }catch (GenericDAOException ex){
            ex.printStackTrace();
        }
        exerciseList.remove(currentPos);

        currentPos = -1;
        mOptionsMenu.findItem(R.id.Delete).setVisible(false);
        mOptionsMenu.findItem(R.id.Edit).setVisible(false);
    }
    /************************************
     ****     Listener Functions     ****
     ************************************/

    @Override
    public void itemSelected(int position) {
        Exercise exercise = exerciseList.get(position);

        if(exercise != null){
            if(currentPos == -1) {
                mOptionsMenu.findItem(R.id.Delete).setVisible(true);
                mOptionsMenu.findItem(R.id.Edit).setVisible(true);
            }

            currentPos = position;
            try {
                exerciseAttributesList = attribute_exercise_dao.getBySecondId(exercise.getId());
            } catch (GenericDAOException ex){
                ex.printStackTrace();
                exerciseAttributesList = new ArrayList<>();
            }
            //TODO: mDetailsExercise.showExercise(exercise, getAttributesNamesList(exerciseAttributesList));
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
                                    ExerciseAttributesActivity activity = (ExerciseAttributesActivity) getActivity();
                                    activity.DialogDismiss();
                                }
                            })
                    .setPositiveButton(res.getString(R.string.positive_answer),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ExerciseAttributesActivity activity = (ExerciseAttributesActivity) getActivity();
                                    activity.DialogDismiss();
                                    activity.removeExercise();
                                }
                            }).create();
        }
    }

    /************************************
     ****       Menu Functions       ****
     ************************************/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_crud, menu);
        mOptionsMenu = menu;
        menu.findItem(R.id.Edit).setVisible(false);
        menu.findItem(R.id.Delete).setVisible(false);
        menu.findItem(R.id.Save).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.Add:
                intent = new Intent(this, CreateExerciseActivity.class);
                startActivityForResult(intent, 0);
                return true;

            case R.id.Edit:
                intent = new Intent(this, CreateExerciseActivity.class);
                //put current exercise ID in extras
                Bundle dataBundle = new Bundle();
                dataBundle.putLong(Exercise_DAO.TABLE_NAME + Exercise_DAO.COLUMN_ID, exerciseList.get(currentPos).getId());
                //add data
                intent.putExtras(dataBundle);
                //start activity
                startActivityForResult(intent, 0);
                return true;

            case R.id.Delete:
                mDialog = AlertToDelete_DialogFragment.newInstance();
                mDialog.show(mFragmentManager, "Alert");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 0) {
            try {
                exerciseList = exercise_dao.getAll();
                mListExercises.setExerciseList(exerciseList);
                mListExercises.updateList();

            } catch (GenericDAOException e) {
                e.printStackTrace();
            }
            //TODO: mDetailsExercise.clearDetails();
            currentPos = -1;
            mOptionsMenu.findItem(R.id.Delete).setVisible(false);
            mOptionsMenu.findItem(R.id.Edit).setVisible(false);
        }
    }

}
