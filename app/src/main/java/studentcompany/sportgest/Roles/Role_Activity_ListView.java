package studentcompany.sportgest.Roles;

import android.app.Activity;
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
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Role_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Role;

public class Role_Activity_ListView extends AppCompatActivity implements ListRole_Fragment.OnItemSelected{


    private Role_DAO roleDao;
    private List<Role> roles;
    private int currentPos = -1;
    private long user_id;
    private Menu mOptionsMenu;

    private DialogFragment mDialog;
    private FragmentManager mFragmentManager;
    private ListRole_Fragment mListRoles = new ListRole_Fragment();
    //private DetailsRole_Fragment mDetailsRole = new DetailsRole_Fragment();
    private static final String TAG = "ROLE_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.role_activity_list_view);

        if(savedInstanceState != null)
            currentPos = savedInstanceState.getInt("currentPos");

        //this.testUsers();
        try {
            roleDao = new Role_DAO(getApplicationContext());
            roles = roleDao.getAll();

            if (roles.isEmpty()) {
                noElems();
                //insertUserTest(userDao);
                //users = userDao.getAll();
            }
            mListRoles.setList(roles);

        } catch (GenericDAOException e) {
            e.printStackTrace();
        }


        // Get a reference to the FragmentManager
        mFragmentManager = getSupportFragmentManager();

        // Start a new FragmentTransaction
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        // Add the TitleFragment to the layout
        fragmentTransaction.add(R.id.title_fragment_container , mListRoles);
        //fragmentTransaction.add(R.id.detail_fragment_container, mDetailsRole);

        fragmentTransaction.commit();


        if(roles.size()>0) {
            currentPos=0;
            //mDetailsUser.startActivity();
            //mDetailsUser.showUser(users.get(currentPos));
        }
    }

    public void noElems(){

        LinearLayoutCompat l = (LinearLayoutCompat)findViewById(R.id.linear);
        l.setVisibility(View.GONE);

        AppCompatTextView t= (AppCompatTextView)findViewById(R.id.without_elems);
        t.setVisibility(View.VISIBLE);
    }

    public void removeRole(){
        //mDetailsUser.clearDetails();

        roleDao.deleteById(roles.get(currentPos).getId());
        mListRoles.removeItem(currentPos);
        //users.remove(currentPos);

        currentPos = -1;
        MenuItem item = mOptionsMenu.findItem(R.id.action_del);
        item.setVisible(false);

        if(roles.isEmpty())
            noElems();
    }

    /************************************
     ****     Listener Functions     ****
     ************************************/

    public void itemSelected(int position) {
        Role user = roles.get(position);

        if(user != null){
            if(currentPos == -1) {
                MenuItem item = mOptionsMenu.findItem(R.id.action_del);
                item.setVisible(true);
            }

            currentPos = position;
            //mDetailsUser.showUser(user);
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
                                    Role_Activity_ListView activity = (Role_Activity_ListView) getActivity();
                                    activity.DialogDismiss();
                                }
                            })
                    .setPositiveButton(res.getString(R.string.positive_answer),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Role_Activity_ListView activity = (Role_Activity_ListView) getActivity();
                                    activity.DialogDismiss();
                                    activity.removeRole();
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MenuItem item;

        if(roles.size()==0)
        {
            item = mOptionsMenu.findItem(R.id.action_add);
            item.setVisible(true);
            item = mOptionsMenu.findItem(R.id.action_edit);
            item.setVisible(false);
            item = mOptionsMenu.findItem(R.id.action_del);
            item.setVisible(false);
            item = mOptionsMenu.findItem(R.id.action_settings);
            item.setVisible(false);
        }else {
            item = mOptionsMenu.findItem(R.id.action_edit);
            item.setVisible(true);

            item = mOptionsMenu.findItem(R.id.action_del);
            item.setVisible(true);

            item = mOptionsMenu.findItem(R.id.action_add);
            item.setVisible(true);

            item = mOptionsMenu.findItem(R.id.action_settings);
            item.setVisible(false);
        }

        //To restore state on Layout Rotation
        /*if(currentPos != -1) {
            mDetailsUser.showUser(users.get(currentPos));
        }*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
       switch (item.getItemId()) {
            /*case R.id.action_add:
                Intent intent = new Intent(this, CreateUser_Activity.class);
                startActivityForResult(intent,112);
                return true;

            case R.id.action_edit:
                intent = new Intent(this, CreateUser_Activity.class);
                user_id = users.get(currentPos).getId();
                intent.putExtra("ID",user_id);
                startActivityForResult(intent,1112);
                return true;

            case R.id.action_del:
                mDialog = AlertToDelete_DialogFragment.newInstance();
                mDialog.show(mFragmentManager, "Alert");
                return true;


            case android.R.id.home:
                finish();
                return true;*/

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
