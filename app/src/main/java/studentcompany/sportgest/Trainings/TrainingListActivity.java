package studentcompany.sportgest.Trainings;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import studentcompany.sportgest.Evaluation.ExerciseAttributesActivity;
import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Pair;
import studentcompany.sportgest.daos.Team_DAO;
import studentcompany.sportgest.daos.Training_DAO;
import studentcompany.sportgest.daos.Training_Exercise_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Exercise;
import studentcompany.sportgest.domains.Training;
import studentcompany.sportgest.domains.TrainingExercise;

public class TrainingListActivity extends AppCompatActivity implements ListTraining_Fragment.OnItemSelected  {

    private Training_DAO training_dao;
    private Training_Exercise_DAO training_exercise_dao;
    private List<Training> trainingList;
    private List<TrainingExercise> trainingExercisesList;
    private int currentPos = -1;
    private Menu mOptionsMenu;

    private DialogFragment mDialog;
    private FragmentManager mFragmentManager;
    private ListTraining_Fragment mListTrainings = new ListTraining_Fragment();
    private DetailsTraining_Fragment mDetailsTraining = new DetailsTraining_Fragment();
    private static final String TAG = "TRAINING_ACTIVITY";

    private long trainingID;
    private long teamID;
    private String sEvaluation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_list);

        try {
            training_dao = new Training_DAO(getApplicationContext());
            training_exercise_dao = new Training_Exercise_DAO(getApplicationContext());

            trainingList = training_dao.getAll();
            if(trainingList.isEmpty()) {
                new TrainingTestData(getApplicationContext());
                trainingList = training_dao.getAll();
            }
            mListTrainings.setTrainingList(trainingList);

        } catch (GenericDAOException e) {
            e.printStackTrace();
        }

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            //get Exercise ID
            trainingID = extras.getLong(Training_DAO.TABLE_NAME + Training_DAO.COLUMN_ID);
            sEvaluation = extras.getString("sEvaluation");
            teamID = extras.getLong(Team_DAO.TABLE_NAME + Team_DAO.COLUMN_ID, 1);

        }

        // Get a reference to the FragmentManager
        mFragmentManager = getSupportFragmentManager();

        // Start a new FragmentTransaction
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        // Add the TitleFragment to the layout
        fragmentTransaction.add(R.id.training_list_fragment_container , mListTrainings);
        fragmentTransaction.add(R.id.training_detail_fragment_container, mDetailsTraining);

        fragmentTransaction.commit();
    }

    public List<String> getNamesList(List<Training> trainingList){
        ArrayList<String> list = new ArrayList<>();

        for(Training t: trainingList)
            list.add(t.getTitle());
        Collections.sort(list);
        return list;
    }

    public List<String> getExercisesNamesList(List<TrainingExercise> exerciseList){
        ArrayList<String> list = new ArrayList<>();
        Exercise e;

        for(TrainingExercise te: exerciseList) {
            if (te != null) {
                e = te.getExercise();
                if(e != null) {
                    list.add(e.getTitle() + " x" + te.getRepetitions());
                }
            }
            e=null;
        }
        Collections.sort(list);
        return list;
    }

    public void removeTraining(){
        mDetailsTraining.clearDetails();
        mListTrainings.removeItem(currentPos);

        try {
            Training training = training_dao.getById(trainingList.get(currentPos).getId());
            if(training != null) {
                //search by training id
                TrainingExercise trainingExercise = new TrainingExercise(-1, training, null, -1);
                //remove list of exercises
                ArrayList<TrainingExercise> previousTrainingExercises = (ArrayList) training_exercise_dao.getByCriteria(trainingExercise);
                for (TrainingExercise te : previousTrainingExercises) {
                    training_exercise_dao.delete(te);
                }
                //remove training
                training_dao.deleteById(trainingList.get(currentPos).getId());
            }
        }catch (GenericDAOException ex){
            ex.printStackTrace();
        }
        trainingList.remove(currentPos);

        currentPos = -1;
        mOptionsMenu.findItem(R.id.Delete).setVisible(false);
        mOptionsMenu.findItem(R.id.Edit).setVisible(false);
    }
    /************************************
     ****     Listener Functions     ****
     ************************************/

    public void itemSelected(int position) {
        Training training = trainingList.get(position);

        if(sEvaluation==null) {
            if (training != null) {
                if (currentPos == -1) {
                    mOptionsMenu.findItem(R.id.Delete).setVisible(true);
                    mOptionsMenu.findItem(R.id.Edit).setVisible(true);
                }

                currentPos = position;
                try {
                    TrainingExercise trainingExercise = new TrainingExercise(-1, training, null, -1);
                    trainingExercisesList = training_exercise_dao.getByCriteria(trainingExercise);
                } catch (GenericDAOException ex) {
                    ex.printStackTrace();
                    trainingExercisesList = new ArrayList<>();
                }
                mDetailsTraining.showTraining(training, getExercisesNamesList(trainingExercisesList));
            }
        }
        else{
            if (training != null) {
                if (currentPos == -1) {
                    mOptionsMenu.findItem(R.id.Forward).setVisible(true);
                }

                currentPos = position;
                try {
                    TrainingExercise trainingExercise = new TrainingExercise(-1, training, null, -1);
                    trainingExercisesList = training_exercise_dao.getByCriteria(trainingExercise);
                } catch (GenericDAOException ex) {
                    ex.printStackTrace();
                    trainingExercisesList = new ArrayList<>();
                }
                mDetailsTraining.showTraining(training, getExercisesNamesList(trainingExercisesList));
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
                                    TrainingListActivity activity = (TrainingListActivity) getActivity();
                                    activity.DialogDismiss();
                                }
                            })
                    .setPositiveButton(res.getString(R.string.positive_answer),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    TrainingListActivity activity = (TrainingListActivity) getActivity();
                                    activity.DialogDismiss();
                                    activity.removeTraining();
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
        if(sEvaluation==null) {
            menu.findItem(R.id.Edit).setVisible(false);
            menu.findItem(R.id.Delete).setVisible(false);
            menu.findItem(R.id.Save).setVisible(false);
            menu.findItem(R.id.Forward).setVisible(false);
        }
        else{
            menu.findItem(R.id.Edit).setVisible(false);
            menu.findItem(R.id.Delete).setVisible(false);
            menu.findItem(R.id.Save).setVisible(false);
            menu.findItem(R.id.Add).setVisible(false);
            menu.findItem(R.id.Forward).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.Add:
                intent = new Intent(this, CreateTrainingActivity.class);
                startActivityForResult(intent, 0);
                return true;

            case R.id.Edit:
                intent = new Intent(this, CreateTrainingActivity.class);
                //put current training ID in extras
                Bundle dataBundle = new Bundle();
                dataBundle.putLong(Training_DAO.TABLE_NAME + Training_DAO.COLUMN_ID, trainingList.get(currentPos).getId());
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

            case R.id.Forward:
                intent = new Intent(getApplicationContext(), ExerciseAttributesActivity.class);

                //put current team ID and training ID in extras
                dataBundle = new Bundle();
                dataBundle.putLong(Team_DAO.TABLE_NAME + Team_DAO.COLUMN_ID, teamID);
                dataBundle.putLong(Training_DAO.TABLE_NAME + Training_DAO.COLUMN_ID, trainingList.get(currentPos).getId());
                //add data
                intent.putExtras(dataBundle);
                //start activity
                startActivityForResult(intent, 0);
                return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 0) {
            try {
                trainingList = training_dao.getAll();
                mListTrainings.setTrainingList(trainingList);
                mListTrainings.updateList();

            } catch (GenericDAOException e) {
                e.printStackTrace();
            }
            mDetailsTraining.clearDetails();
            currentPos = -1;
            mOptionsMenu.findItem(R.id.Delete).setVisible(false);
            mOptionsMenu.findItem(R.id.Edit).setVisible(false);
        }
    }
}
