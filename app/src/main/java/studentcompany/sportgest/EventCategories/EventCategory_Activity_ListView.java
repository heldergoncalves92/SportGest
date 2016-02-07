package studentcompany.sportgest.EventCategories;

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
import studentcompany.sportgest.daos.Event_Category_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.EventCategory;


public class EventCategory_Activity_ListView extends AppCompatActivity implements EventCategory_Fragment_List.OnItemSelected {

    private Event_Category_DAO ecDAO;
    private List<EventCategory> ecs;
    private int currentPos = 0;
    private Menu mOptionsMenu;

    private int basePositionID;
    private DialogFragment mDialog;
    private FragmentManager mFragmentManager;
    private EventCategory_Fragment_List mListEventCategory = new EventCategory_Fragment_List();
    private EventCategory_Fragment_Details mDetailsEventCategory = new EventCategory_Fragment_Details();
    private static final String TAG = "EVENTCATEGORY_LIST_ACTIVITY";

    private final int EDIT_TAG = 19;
    private final int CREATE_TAG = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_category_fragment_list);

        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();

            if (extras != null){
                basePositionID = extras.getInt("EVENTCATEGORY");

            } else
                basePositionID = 0;

        } else {
            basePositionID = savedInstanceState.getInt("basePositionID");
            currentPos = savedInstanceState.getInt("currentPos");
        }

        try {
            ecDAO = new Event_Category_DAO(getApplicationContext());
            ecs = ecDAO.getAll();
            if(ecs.isEmpty()) {
                noElems();
            }
            mListEventCategory.setList(ecs);

        } catch (GenericDAOException e) {
            e.printStackTrace();
        }

        // Get a reference to the FragmentManager
        mFragmentManager = getSupportFragmentManager();

        // Start a new FragmentTransaction
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        // Add the TitleFragment to the layout
        fragmentTransaction.add(R.id.title_fragment_container , mListEventCategory);
        fragmentTransaction.add(R.id.detail_fragment_container, mDetailsEventCategory);

        fragmentTransaction.commit();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("basePositionID", basePositionID);
        outState.putInt("currentPos", currentPos);
    }

    public List<String> getNamesList(List<EventCategory> ecList){

        ArrayList<String> list = new ArrayList<String>();

        for(EventCategory p: ecList)
            list.add(p.getName());

        return list;
    }

    public void noElems(){

        LinearLayout l = (LinearLayout)findViewById(R.id.linear);
        l.setVisibility(View.GONE);

        TextView t= (TextView)findViewById(R.id.without_elems);
        t.setVisibility(View.VISIBLE);
    }

    public void showElems(){

        LinearLayout l = (LinearLayout)findViewById(R.id.linear);
        l.setVisibility(View.VISIBLE);

        TextView t= (TextView)findViewById(R.id.without_elems);
        t.setVisibility(View.GONE);
    }

    public void removePosition(){
        mDetailsEventCategory.clearDetails();
        mListEventCategory.removeItem(currentPos);

        ecDAO.deleteById(ecs.get(currentPos).getId());
        ecs.remove(currentPos);

        currentPos = -1;
        MenuItem item = mOptionsMenu.findItem(R.id.Event_Category_Delete);
        item.setVisible(false);

        if(ecs.isEmpty())
            noElems();
    }

    /************************************
     ****     Listener Functions     ****
     ************************************/

    public void itemSelected(int position) {
        EventCategory positionObj = ecs.get(position);

        if(positionObj != null){
            if(currentPos == -1) {
                MenuItem item = mOptionsMenu.findItem(R.id.Event_Category_Delete);
                item.setVisible(true);

                item = mOptionsMenu.findItem(R.id.Event_Category_Edit);
                item.setVisible(true);
            }

            currentPos = position;
            mDetailsEventCategory.showEventCategory(positionObj);
        }
    }

    /************************************
     ****       Menu Functions       ****
     ************************************/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mOptionsMenu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_event_category, menu);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //To restore state on Layout Rotation
        if(currentPos != -1) {
            MenuItem item = mOptionsMenu.findItem(R.id.Event_Category_Delete);
            item.setVisible(true);

            item = mOptionsMenu.findItem(R.id.Event_Category_Edit);
            item.setVisible(true);
            if(ecs.size()>0)
                mDetailsEventCategory.showEventCategory(ecs.get(currentPos));
        }



        MenuItem item;

        if(ecs.size()==0)
        {
            item = mOptionsMenu.findItem(R.id.Event_Category_Add);
            item.setVisible(true);
            item = mOptionsMenu.findItem(R.id.Event_Category_Edit);
            item.setVisible(false);
            item = mOptionsMenu.findItem(R.id.Event_Category_Delete);
            item.setVisible(false);
        }else {
            item = mOptionsMenu.findItem(R.id.Event_Category_Edit);
            item.setVisible(true);

            item = mOptionsMenu.findItem(R.id.Event_Category_Delete);
            item.setVisible(true);

            item = mOptionsMenu.findItem(R.id.Event_Category_Add);
            item.setVisible(true);

        }



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.Event_Category_Add:
                Intent intent = new Intent(this, EventCategory_Activity_Create.class);
                startActivityForResult(intent, CREATE_TAG);
                return true;

            case R.id.Event_Category_Delete:
                mDialog = AlertToDelete_DialogFragment.newInstance();
                mDialog.show(mFragmentManager, "Alert");
                return true;

            case R.id.Event_Category_Edit:
                Intent intent2 = new Intent(this, EventCategory_Activity_Edit.class);
                intent2.putExtra("id", ecs.get(currentPos).getId());
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
                                    EventCategory_Activity_ListView activity = (EventCategory_Activity_ListView) getActivity();
                                    activity.DialogDismiss();
                                }
                            })
                    .setPositiveButton(res.getString(R.string.positive_answer),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    EventCategory_Activity_ListView activity = (EventCategory_Activity_ListView) getActivity();
                                    activity.DialogDismiss();
                                    activity.removePosition();
                                }
                            }).create();
        }
    }

    /************************************
     ****        Test Functions      ****
     ************************************/


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        EventCategory eventCategort = null;
        if (requestCode == EDIT_TAG) {
            if(resultCode == 1){
                try {
                    eventCategort=ecDAO.getById(ecs.get(currentPos).getId());
                    ecs.set(currentPos,eventCategort);
                    mListEventCategory.updateEventCategory(eventCategort, currentPos);
                    mDetailsEventCategory.showEventCategory(eventCategort);

                    showElems();

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

                    ecs = ecDAO.getAll();
                    mListEventCategory.updateList(ecs);

                    showElems();

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
