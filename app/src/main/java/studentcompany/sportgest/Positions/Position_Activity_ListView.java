package studentcompany.sportgest.Positions;

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

import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Position_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Position;


public class Position_Activity_ListView extends AppCompatActivity implements Position_Fragment_List.OnItemSelected {

    private Position_DAO positionDAO;
    private List<Position> positions;
    private int currentPos = -1;
    private Menu mOptionsMenu;

    private int basePositionID;
    private DialogFragment mDialog;
    private FragmentManager mFragmentManager;
    private Position_Fragment_List mListPosition = new Position_Fragment_List();
    private Position_Fragment_Details mDetailsPosition = new Position_Fragment_Details();
    private static final String TAG = "POSITION_LIST_ACTIVITY";

    private final int EDIT_TAG = 19;
    private final int CREATE_TAG = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.position_fragment_list);

        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();

            if (extras != null){
                basePositionID = extras.getInt("POSITION");

            } else
                basePositionID = 0;

        } else {
            basePositionID = savedInstanceState.getInt("basePositionID");
            currentPos = savedInstanceState.getInt("currentPos");
        }

        try {
            positionDAO = new Position_DAO(getApplicationContext());
            positions = positionDAO.getAll();
            //players = playerDao.getAll();
            if(positions.isEmpty()) {

                noElems();
                //insertUserTest(playerDao);
                //players = playerDao.getAll();
            }
            mListPosition.setList(positions);

        } catch (GenericDAOException e) {
            e.printStackTrace();
        }

        // Get a reference to the FragmentManager
        mFragmentManager = getSupportFragmentManager();

        // Start a new FragmentTransaction
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        // Add the TitleFragment to the layout
        fragmentTransaction.add(R.id.title_fragment_container , mListPosition);
        fragmentTransaction.add(R.id.detail_fragment_container, mDetailsPosition);

        fragmentTransaction.commit();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("basePositionID", basePositionID);
        outState.putInt("currentPos", currentPos);
    }

    public List<String> getNamesList(List<Position> positionList){

        ArrayList<String> list = new ArrayList<String>();

        for(Position p: positionList)
            list.add(p.getName());

        return list;
    }

    public void noElems(){

        LinearLayout l = (LinearLayout)findViewById(R.id.linear);
        l.setVisibility(View.GONE);

        TextView t= (TextView)findViewById(R.id.without_elems);
        t.setVisibility(View.VISIBLE);
    }

    public void removePosition(){
        mDetailsPosition.clearDetails();
        mListPosition.removeItem(currentPos);

        positionDAO.deleteById(positions.get(currentPos).getId());
        positions.remove(currentPos);

        currentPos = -1;
        MenuItem item = mOptionsMenu.findItem(R.id.action_del);
        item.setVisible(false);

        if(positions.isEmpty())
            noElems();
    }

    /************************************
     ****     Listener Functions     ****
     ************************************/

    public void itemSelected(int position) {
        Position positionObj = positions.get(position);

        if(positionObj != null){
            if(currentPos == -1) {
                MenuItem item = mOptionsMenu.findItem(R.id.action_del);
                item.setVisible(true);

                item = mOptionsMenu.findItem(R.id.action_edit);
                item.setVisible(true);
            }

            currentPos = position;
            mDetailsPosition.showPosition(positionObj);
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

            item = mOptionsMenu.findItem(R.id.action_edit);
            item.setVisible(true);
            mDetailsPosition.showPosition(positions.get(currentPos));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(this, Position_Activity_Create.class);
                startActivityForResult(intent, CREATE_TAG);
                return true;

            case R.id.action_del:
                mDialog = AlertToDelete_DialogFragment.newInstance();
                mDialog.show(mFragmentManager, "Alert");
                return true;

            case R.id.action_edit:
                Intent intent2 = new Intent(this, Position_Activity_Edit.class);
                intent2.putExtra("id", positions.get(currentPos).getId());
                startActivityForResult(intent2, EDIT_TAG);
                return true;

            default:
                return super.onOptionsItemSelected(item);
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
                                    Position_Activity_ListView activity = (Position_Activity_ListView) getActivity();
                                    activity.DialogDismiss();
                                }
                            })
                    .setPositiveButton(res.getString(R.string.positive_answer),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Position_Activity_ListView activity = (Position_Activity_ListView) getActivity();
                                    activity.DialogDismiss();
                                    activity.removePosition();
                                }
                            }).create();
        }
    }

    /************************************
     ****        Test Functions      ****
     ************************************/

    private void insertUserTest(Position_DAO p_dao){

        try {
            Position p1 = new Position("Ala");
            Position p2 = new Position("Fixo");
            Position p3 = new Position("Pivot");

            long id;

            id = p_dao.insert(p1);
            id = p_dao.insert(p2);
            id = p_dao.insert(p3);

        } catch (GenericDAOException e) {
            e.printStackTrace();
        }

    }

    private void testPositions(){

        Position p1 = new Position("Ala");
        Position p2 = new Position("Fixo");
        Position p3 = new Position("Pivot");

        positions = new ArrayList<Position>();
        positions.add(p1);
        positions.add(p2);
        positions.add(p3);

        mListPosition.setList(positions);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Position position = null;
        if (requestCode == EDIT_TAG) {
            if(resultCode == 1){
                try {
                    position=positionDAO.getById(positions.get(currentPos).getId());
                    positions.set(currentPos,position);
                    mListPosition.updatePosition(position, currentPos);
                    mDetailsPosition.showPosition(position);

                    Toast.makeText(getApplicationContext(), R.string.updated, Toast.LENGTH_SHORT).show();
                } catch (GenericDAOException e) {
                    e.printStackTrace();
                }
            }
            if(resultCode == 2){
                Toast.makeText(getApplicationContext(), R.string.something_wrong, Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == CREATE_TAG) {
            if(resultCode == 1){
                try {
                    /*Bundle bundle = data.getExtras();
                    long id = (long) bundle.get("id");
                    int idToSearch = (int) (id + 0);
                    player=playerDao.getById(idToSearch);
                    System.out.println(player);
                    players.add(player);
                    mDetailsPlayer.showPlayer(player);
                    */

                    positions = positionDAO.getAll();
                    mListPosition.updateList(positions);

                    Toast.makeText(getApplicationContext(), R.string.inserted, Toast.LENGTH_SHORT).show();
                } catch (GenericDAOException e) {
                    e.printStackTrace();
                }
            }
            if(resultCode == 2){
                Toast.makeText(getApplicationContext(), R.string.something_wrong, Toast.LENGTH_SHORT).show();
            }
        }
    }


}
