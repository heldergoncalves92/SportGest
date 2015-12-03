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
import java.util.List;

import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Training_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Training;

public class TrainingListActivity extends AppCompatActivity implements ListTraining_Fragment.OnItemSelected  {

    private Training_DAO training_dao;
    private List<Training> trainingList;
    private int currentPos = -1;
    private Menu mOptionsMenu;

    private DialogFragment mDialog;
    private FragmentManager mFragmentManager;
    private ListTraining_Fragment mListTrainings = new ListTraining_Fragment();
    private DetailsTraining_Fragment mDetailsTraining = new DetailsTraining_Fragment();
    private static final String TAG = "TRAINING_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_list);


        try {
            training_dao = new Training_DAO(getApplicationContext());
            trainingList = training_dao.getAll();
            if(trainingList.isEmpty()) {
                new TrainingTestData(getApplicationContext());
                trainingList = training_dao.getAll();
            }
            mListTrainings.setTrainingList(getNamesList(trainingList));

        } catch (GenericDAOException e) {
            e.printStackTrace();
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

        return list;
    }

    public void removeTraining(){
        mDetailsTraining.clearDetails();
        mListTrainings.removeItem(currentPos);

        training_dao.deleteById(trainingList.get(currentPos).getId());
        trainingList.remove(currentPos);

        currentPos = -1;
        MenuItem item = mOptionsMenu.findItem(R.id.action_del);
        item.setVisible(false);
    }
    /************************************
     ****     Listener Functions     ****
     ************************************/

    public void itemSelected(int position) {
        Training training = trainingList.get(position);

        if(training != null){
            if(currentPos == -1) {
                MenuItem item = mOptionsMenu.findItem(R.id.action_del);
                item.setVisible(true);
            }

            currentPos = position;
            mDetailsTraining.showTraining(training);
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
        mOptionsMenu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_users_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(this, CreateTrainingActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_del:
                mDialog = AlertToDelete_DialogFragment.newInstance();
                mDialog.show(mFragmentManager, "Alert");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
