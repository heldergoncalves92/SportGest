package studentcompany.sportgest.Games;

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

import studentcompany.sportgest.Exercises.CreateExerciseActivity;
import studentcompany.sportgest.Exercises.DetailsExercise_Fragment;
import studentcompany.sportgest.Exercises.ExerciseTestData;
import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Attribute_Exercise_DAO;
import studentcompany.sportgest.daos.Exercise_DAO;
import studentcompany.sportgest.daos.Game_DAO;
import studentcompany.sportgest.daos.GenericDAO;
import studentcompany.sportgest.daos.Pair;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Attribute;
import studentcompany.sportgest.domains.Exercise;
import studentcompany.sportgest.domains.Game;

public class GamesListActivity extends AppCompatActivity implements Game_Fragment_list.OnItemSelected  {

    private Game_DAO game_dao;
    //private Attribute_Exercise_DAO attribute_exercise_dao;
    private List<Game> gameList;
    //private List<Attribute> exerciseAttributesList;
    private int currentPos = -1;
    private Menu mOptionsMenu;

    private DialogFragment mDialog;
    private FragmentManager mFragmentManager;
    private Game_Fragment_list mListExercises = new Game_Fragment_list();
    //private DetailsExercise_Fragment mDetailsExercise = new DetailsExercise_Fragment();
    private static final String TAG = "EXERCISE_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);

        try {
            game_dao = new Game_DAO(getApplicationContext());
            //attribute_exercise_dao = new Attribute_Exercise_DAO(getApplicationContext());

            gameList = game_dao.getAll();
            if(gameList.isEmpty()) {
                new GameTestData(getApplicationContext());
                gameList = game_dao.getAll();
            }
            mListExercises.setGameList(gameList);

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
        //fragmentTransaction.add(R.id.exercise_detail_fragment_container, mDetailsExercise);

        fragmentTransaction.commit();
    }

    public List<String> getNamesList(List<Exercise> exerciseList){
        ArrayList<String> list = new ArrayList<>();

        for(Exercise e: exerciseList)
            list.add(e.getTitle());
        Collections.sort(list);
        return list;
    }


    public void removeExercise(){
        //mDetailsExercise.clearDetails();
        mListExercises.removeItem(currentPos);

        try {
            Game game = game_dao.getById(gameList.get(currentPos).getId());
            if(game != null) {
                //remove agme
                game_dao.deleteById(gameList.get(currentPos).getId());
            }
        }catch (GenericDAOException ex){
            ex.printStackTrace();
        }
        gameList.remove(currentPos);

        currentPos = -1;
        mOptionsMenu.findItem(R.id.Delete).setVisible(false);
        mOptionsMenu.findItem(R.id.Edit).setVisible(false);
    }
    /************************************
     ****     Listener Functions     ****
     ************************************/

    @Override
    public void itemSelected(int position) {
        Game game = gameList.get(position);

        if(game != null){
            if(currentPos == -1) {
                mOptionsMenu.findItem(R.id.Delete).setVisible(true);
                mOptionsMenu.findItem(R.id.Edit).setVisible(true);
                mOptionsMenu.findItem(R.id.Details).setVisible(true);
            }

            currentPos = position;
            /*
            try {
                exerciseAttributesList = attribute_exercise_dao.getBySecondId(exercise.getId());
            } catch (GenericDAOException ex){
                ex.printStackTrace();
                exerciseAttributesList = new ArrayList<>();
            }
            */
            //mDetailsExercise.showExercise(exercise, getAttributesNamesList(exerciseAttributesList));
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
                                    GamesListActivity activity = (GamesListActivity) getActivity();
                                    activity.DialogDismiss();
                                }
                            })
                    .setPositiveButton(res.getString(R.string.positive_answer),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    GamesListActivity activity = (GamesListActivity) getActivity();
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
        getMenuInflater().inflate(R.menu.menu_toolbar_crud2, menu);
        mOptionsMenu = menu;
        menu.findItem(R.id.Edit).setVisible(false);
        menu.findItem(R.id.Delete).setVisible(false);
        menu.findItem(R.id.Details).setVisible(false);
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
                dataBundle.putLong(Game_DAO.TABLE_NAME + Game_DAO.COLUMN_ID, gameList.get(currentPos).getId());
                //add data
                intent.putExtras(dataBundle);
                //start activity
                startActivityForResult(intent, 0);
                return true;


            case R.id.Delete:
                mDialog = AlertToDelete_DialogFragment.newInstance();
                mDialog.show(mFragmentManager, "Alert");
                return true;

            case R.id.Details:
                intent = new Intent(this, GameGeneralView_Activity.class);
                startActivityForResult(intent, 0);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 0) {
            try {
                gameList = game_dao.getAll();
                mListExercises.setGameList(gameList);
                mListExercises.updateList();

            } catch (GenericDAOException e) {
                e.printStackTrace();
            }
            //mDetailsExercise.clearDetails();
            currentPos = -1;
            mOptionsMenu.findItem(R.id.Delete).setVisible(false);
            mOptionsMenu.findItem(R.id.Edit).setVisible(false);
            mOptionsMenu.findItem(R.id.Details).setVisible(false);
        }
    }
}
