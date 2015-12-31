package studentcompany.sportgest.Exercises;

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

import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Attribute_Exercise_DAO;
import studentcompany.sportgest.daos.Exercise_DAO;
import studentcompany.sportgest.daos.GenericDAO;
import studentcompany.sportgest.daos.Pair;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Attribute;
import studentcompany.sportgest.domains.Exercise;

public class ExerciseListActivity extends AppCompatActivity implements ListExercise_Fragment.OnItemSelected  {

    private Exercise_DAO exercise_dao;
    private Attribute_Exercise_DAO attribute_exercise_dao;
    private List<Exercise> exerciseList;
    private List<Attribute> exerciseAttributesList;
    private int currentPos = -1;
    private Menu mOptionsMenu;

    private DialogFragment mDialog;
    private FragmentManager mFragmentManager;
    private ListExercise_Fragment mListExercises = new ListExercise_Fragment();
    private DetailsExercise_Fragment mDetailsExercise = new DetailsExercise_Fragment();
    private static final String TAG = "EXERCISE_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);

        try {
            exercise_dao = new Exercise_DAO(getApplicationContext());
            attribute_exercise_dao = new Attribute_Exercise_DAO(getApplicationContext());

            exerciseList = exercise_dao.getAll();
            if(exerciseList.isEmpty()) {
                new ExerciseTestData(getApplicationContext());
                exerciseList = exercise_dao.getAll();
            }
            mListExercises.setExerciseList(exerciseList);

        } catch (GenericDAOException e) {
            e.printStackTrace();
        }

        /*//if there are at lest one element, display the first one
        if(exerciseList.size() > 0){
            try {
                exerciseAttributesList = attribute_exercise_dao.getBySecondId(exerciseList.get(0).getId());
            } catch (GenericDAOException ex){
                ex.printStackTrace();
                exerciseAttributesList = new ArrayList<>();
            }
            System.out.println(exerciseList.get(0).toString());
            mDetailsExercise.showExercise(exerciseList.get(0), getAttributesNamesList(exerciseAttributesList));
        }*/

        // Get a reference to the FragmentManager
        mFragmentManager = getSupportFragmentManager();

        // Start a new FragmentTransaction
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        // Add the TitleFragment to the layout
        fragmentTransaction.add(R.id.exercise_list_fragment_container , mListExercises);
        fragmentTransaction.add(R.id.exercise_detail_fragment_container, mDetailsExercise);

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
        mDetailsExercise.clearDetails();
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
            mDetailsExercise.showExercise(exercise, getAttributesNamesList(exerciseAttributesList));
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
                                    ExerciseListActivity activity = (ExerciseListActivity) getActivity();
                                    activity.DialogDismiss();
                                }
                            })
                    .setPositiveButton(res.getString(R.string.positive_answer),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ExerciseListActivity activity = (ExerciseListActivity) getActivity();
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
            mDetailsExercise.clearDetails();
            currentPos = -1;
            mOptionsMenu.findItem(R.id.Delete).setVisible(false);
            mOptionsMenu.findItem(R.id.Edit).setVisible(false);
        }
    }
}
