package studentcompany.sportgest.Roles;

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
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import studentcompany.sportgest.R;
import studentcompany.sportgest.Users.CreateRole_Activity;
import studentcompany.sportgest.daos.Role_DAO;
import studentcompany.sportgest.daos.Role_Permission_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Permission;
import studentcompany.sportgest.domains.Role;

public class Role_Activity_ListView extends AppCompatActivity implements Role_Fragment_List.OnItemSelected{


    private Role_DAO roleDao;
    private Role_Permission_DAO role_permission_dao;

    private List<Role> roles;
    private int currentPos = 0;
    private Menu mOptionsMenu;

    private DialogFragment mDialog;
    private FragmentManager mFragmentManager;
    private Role_Fragment_List mListRoles = new Role_Fragment_List();
    private Role_Fragment_Details mDetailsRole = new Role_Fragment_Details();
    private static final String TAG = "ROLE_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.role_activity_list_view);

        if(savedInstanceState != null)
            currentPos = savedInstanceState.getInt("currentPos");


        try {
            roleDao = new Role_DAO(getApplicationContext());
            role_permission_dao = new Role_Permission_DAO(getApplicationContext());
            roles = roleDao.getAll();

            if (roles.isEmpty()) {
                noElems();
                roles = new ArrayList<Role>();
            }else{

                //Get the permission list of each Role
                List<Permission> lp;

                for(Role r: roles){
                    lp = role_permission_dao.getPermissionsByRoleId(r.getId());
                    r.setPermissionList(lp);
                }
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
        fragmentTransaction.add(R.id.detail_fragment_container, mDetailsRole);

        fragmentTransaction.commit();
    }

    private void noElems(){

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

    public void removeRole(){
        mDetailsRole.clearDetails();

        roleDao.deleteById(roles.get(currentPos).getId());
        mListRoles.removeItem(currentPos);

        currentPos = -1;
        MenuItem item = mOptionsMenu.findItem(R.id.action_del);
        item.setVisible(false);

        if(roles.isEmpty())
            noElems();
    }

    /************************************
     ****     Listener Functions     ****
     ************************************/

    @Override
    public void itemSelected(int position, int tag) {
        Role role = roles.get(position);

        if(role != null){
            if(currentPos == -1) {
                MenuItem item = mOptionsMenu.findItem(R.id.action_del);
                item.setVisible(true);

                item = mOptionsMenu.findItem(R.id.action_edit);
                item.setVisible(true);

                mDetailsRole.showFirstElem();
            }

            currentPos = position;
            mDetailsRole.showRole(role);
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
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //To restore state on Layout Rotation
        if(currentPos != -1 && roles.size()>0) {

            MenuItem item = mOptionsMenu.findItem(R.id.action_del);
            item.setVisible(true);

            item = mOptionsMenu.findItem(R.id.action_edit);
            item.setVisible(true);

            mDetailsRole.showRole(roles.get(currentPos));
            mListRoles.select_Item(currentPos);

        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Intent intent;

        switch (item.getItemId()) {
            case R.id.action_add:
                intent = new Intent(this, Role_Activity_Create.class);
                startActivityForResult(intent,112);
                return true;

            case R.id.action_edit:
                //put current role ID in extras
                Bundle dataBundle = new Bundle();
                dataBundle.putLong(Role_DAO.TABLE_NAME+Role_DAO.COLUMN_ID, roles.get(currentPos).getId());

                //declare intention to start CreateRoleActivity
                intent = new Intent(getApplicationContext(), Role_Activity_Create.class);
                intent.putExtras(dataBundle);

                //start activity
                startActivityForResult(intent, 112);
                return true;

            case R.id.action_del:
                mDialog = AlertToDelete_DialogFragment.newInstance();
                mDialog.show(mFragmentManager, "Alert");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("currentPos", currentPos);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //*************************
        //***    Result Code    ***
        //*************************
        //***   0 - Nothing     ***
        //***   1 - Create      ***
        //***   2 - Edit        ***
        //*************************


        if (requestCode == 112) {

            if(resultCode == 1) {
                try {
                    roles = roleDao.getAll();
                    mListRoles.updateList(roles);

                    if(roles.size() == 1)
                        withElems();

                    //Get the permission list of each Role
                    List<Permission> lp;

                    for(Role r: roles){
                        lp = role_permission_dao.getPermissionsByRoleId(r.getId());
                        r.setPermissionList(lp);
                    }

                } catch (GenericDAOException e) {
                    e.printStackTrace();
                }

            } else if(resultCode == 2) {
                try {
                    Role newRole = roleDao.getById(roles.get(currentPos).getId());
                    newRole.setPermissionList(role_permission_dao.getPermissionsByRoleId(newRole.getId()));

                    //Update ViewDetails
                    mDetailsRole.showRole(newRole);
                    roles.set(currentPos, newRole);

                    //Update RecyclerView
                    mListRoles.updatePosition(newRole, currentPos);

                } catch (GenericDAOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
