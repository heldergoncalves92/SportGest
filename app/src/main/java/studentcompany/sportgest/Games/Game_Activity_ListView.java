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
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import studentcompany.sportgest.Exercises.Exercise_Activity_Create;
import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Game_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Exercise;
import studentcompany.sportgest.domains.Game;

public class Game_Activity_ListView extends AppCompatActivity implements Game_Fragment_list.OnItemSelected  {

    private Game_DAO game_dao;
    private List<Game> gameList;

    private int currentPos = -1;
    private long baseTeamID = 0;
    private Menu mOptionsMenu;


    private DialogFragment mDialog;
    private FragmentManager mFragmentManager;
    private Game_Fragment_list mListGames = new Game_Fragment_list();

    private static final String TAG = "EXERCISE_ACTIVITY";
    private final int CREATE = 234, EDIT = 235, DETAILS = 236;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);

        //Get Informations from the previous activity or rotation Layout
        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();

            if (extras != null){
                baseTeamID = extras.getLong("TEAM");

            } else {
                baseTeamID = 0;
            }
        } else {
            baseTeamID = savedInstanceState.getInt("baseTeamID");
        }

        //Some verifications
        if(baseTeamID <=0) {
            Toast.makeText(this, "Invalid call!!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        try {
            game_dao = new Game_DAO(getApplicationContext());

            gameList = game_dao.getAll();
            if(gameList.isEmpty()) {
                noElems();
                gameList = new ArrayList<>();
            }
            mListGames.setGameList(gameList);

        } catch (GenericDAOException e) {
            e.printStackTrace();
        }

        // Get a reference to the FragmentManager
        mFragmentManager = getSupportFragmentManager();

        // Start a new FragmentTransaction
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        // Add the TitleFragment to the layout
        fragmentTransaction.add(R.id.exercise_list_fragment_container , mListGames);
        //fragmentTransaction.add(R.id.exercise_detail_fragment_container, mDetailsExercise);

        fragmentTransaction.commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putLong("baseTeamID", baseTeamID);
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



    public void removeGame(){
        try {

            Game game = mListGames.removeItem(currentPos);
            game_dao.deleteById(game.getId());

        } catch (GenericDAOException e) {
            e.printStackTrace();
        }

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
                //mOptionsMenu.findItem(R.id.Delete).setVisible(true);
                //mOptionsMenu.findItem(R.id.Edit).setVisible(true);
                mOptionsMenu.findItem(R.id.Details).setVisible(true);
                Intent intent = new Intent(this, GameGeneralView_Activity.class);
                intent.putExtra("TEAM", baseTeamID);
                intent.putExtra("GAME", gameList.get(position).getId());

                startActivityForResult(intent, DETAILS);
            }

            currentPos = position;
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
                                    Game_Activity_ListView activity = (Game_Activity_ListView) getActivity();
                                    activity.DialogDismiss();
                                }
                            })
                    .setPositiveButton(res.getString(R.string.positive_answer),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Game_Activity_ListView activity = (Game_Activity_ListView) getActivity();
                                    activity.DialogDismiss();
                                    activity.removeGame();
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
                intent = new Intent(this, Game_Activity_Create.class);
                startActivityForResult(intent, CREATE);
                return true;

            case R.id.Edit:
                intent = new Intent(this, Game_Activity_Create.class);
                intent.putExtra("GAME", gameList.get(currentPos).getId());
                startActivityForResult(intent, EDIT);
                return true;


            case R.id.Delete:
                mDialog = AlertToDelete_DialogFragment.newInstance();
                mDialog.show(mFragmentManager, "Alert");
                return true;

            case R.id.Details:
                intent = new Intent(this, GameGeneralView_Activity.class);

                //add data
                intent.putExtra("TEAM", baseTeamID);
                intent.putExtra("GAME", gameList.get(currentPos).getId());

                startActivityForResult(intent, DETAILS);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CREATE) {
            if(resultCode == 1){
                try {
                    long id = data.getExtras().getLong("ID");
                    Game game = game_dao.getById(id);
                    mListGames.insert_Item(game, 0);

                    if(gameList.size() == 1)
                        withElems();

                } catch (GenericDAOException e) {
                    e.printStackTrace();
                }
                //mDetailsExercise.clearDetails();
                currentPos = -1;
                mOptionsMenu.findItem(R.id.Delete).setVisible(false);
                mOptionsMenu.findItem(R.id.Edit).setVisible(false);
                mOptionsMenu.findItem(R.id.Details).setVisible(false);
            }

        } else if (requestCode == DETAILS) {
            if(resultCode == 1){
                int operation = data.getExtras().getInt("OPERATION");

                //Delete a game
                if(operation == 1) removeGame();
            } else{
                mOptionsMenu.findItem(R.id.Delete).setVisible(true);
            }
        }
    }
}
