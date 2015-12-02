package studentcompany.sportgest.Users;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import studentcompany.sportgest.Players.PlayersList_Activity;
import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.User_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.User;

public class UserListActivity extends AppCompatActivity implements ListUser_Fragment.OnItemSelected {


    private User_DAO userDao;
    private List<User> users;
    private int currentPos = -1;
    private Menu mOptionsMenu;

    private DialogFragment mDialog;
    private FragmentManager mFragmentManager;
    private ListUser_Fragment mListUsers = new ListUser_Fragment();
    private DetailsUser_Fragment mDetailsUser = new DetailsUser_Fragment();
    private static final String TAG = "USERS_ACTIVITY";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);


        //this.testUsers();
        try {
            userDao = new User_DAO(getApplicationContext());
            users = userDao.getAll();
            if(users.isEmpty()) {
                insertUserTest(userDao);
                users = userDao.getAll();
            }
            mListUsers.setUserList(getNamesList(users));

        } catch (GenericDAOException e) {
            e.printStackTrace();
        }


        // Get a reference to the FragmentManager
        mFragmentManager = getSupportFragmentManager();

        // Start a new FragmentTransaction
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        // Add the TitleFragment to the layout
        fragmentTransaction.add(R.id.title_fragment_container , mListUsers);
        fragmentTransaction.add(R.id.detail_fragment_container, mDetailsUser);

        fragmentTransaction.commit();
    }

    public List<String> getNamesList(List<User> usersList){

        ArrayList<String> list = new ArrayList<String>();

        for(User u: usersList)
            list.add(u.getName());

        return list;
    }

    public void removeUser(){
        mDetailsUser.clearDetails();
        mListUsers.removeItem(currentPos);

        userDao.deleteById(users.get(currentPos).getId());
        users.remove(currentPos);

        currentPos = -1;
        MenuItem item = mOptionsMenu.findItem(R.id.action_del);
        item.setVisible(false);
    }
    /************************************
     ****     Listener Functions     ****
     ************************************/

    public void itemSelected(int position) {
        User user = users.get(position);

        if(user != null){
            if(currentPos == -1) {
                MenuItem item = mOptionsMenu.findItem(R.id.action_del);
                item.setVisible(true);
            }

            currentPos = position;
            mDetailsUser.showUser(user);
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
                                    UserListActivity activity = (UserListActivity) getActivity();
                                    activity.DialogDismiss();
                                }
                            })
                    .setPositiveButton(res.getString(R.string.positive_answer),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    UserListActivity activity = (UserListActivity) getActivity();
                                    activity.DialogDismiss();
                                    activity.removeUser();
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
                Intent intent = new Intent(this, CreateUser_Activity.class);
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

    /************************************
     ****        Test Functions      ****
     ************************************/

    private void insertUserTest(User_DAO u_dao){

        try {
            User u1 = new User("user0","password","photo0","António Joaquim","user0@email.com",null);
            User u2 = new User("user1","password","photo1","João Dias","user1@email.com",null);
            User u3 = new User("user2","password","photo2","Maria Andrade","user2@email.com",null);
            User u4 = new User("user3","password","photo3","José Alves","user3@email.com",null);

            u_dao.insert(u1);
            u_dao.insert(u2);
            u_dao.insert(u3);
            u_dao.insert(u4);

        } catch (GenericDAOException e) {
            e.printStackTrace();
        }
    }

    private void testUsers(){

        User u1 = new User(0,"user0","password","photo0","António Joaquim","user0@email.com",null);
        User u2 = new User(1,"user1","password","photo1","João Dias","user1@email.com",null);
        User u3 = new User(2,"user2","password","photo2","Maria Andrade","user2@email.com",null);
        User u4 = new User(3,"user3","password","photo3","José Alves","user3@email.com",null);

        users = new ArrayList<User>();
        users.add(u1);
        users.add(u2);
        users.add(u3);
        users.add(u4);

        mListUsers.setUserList(getNamesList(users));
    }

}
