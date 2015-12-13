package studentcompany.sportgest.Users;

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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.User_DAO;
import studentcompany.sportgest.daos.User_Team_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Team;
import studentcompany.sportgest.domains.User;

public class UserListActivity extends AppCompatActivity implements ListUser_Fragment.OnItemSelected {


    private User_DAO userDao;
    private User_Team_DAO user_team_dao;
    private List<User> users;
    private int currentPos = -1;
    private long user_id;
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

        if(savedInstanceState != null)
            currentPos = savedInstanceState.getInt("currentPos");

        //this.testUsers();
        try {
            userDao = new User_DAO(getApplicationContext());
            user_team_dao = new User_Team_DAO(getApplicationContext());
            users = userDao.getAll();
            for(User u : users)
            {List<Team> t = user_team_dao.getByFirstId(u.getId());
             if(t!=null)
                 if(t.size()>0)
                     u.setTeam(t.get(0)); // Get the Team
            }
            if(users.isEmpty()) {
                //noElems();
                insertUserTest(userDao);
                users = userDao.getAll();
            }
            //mListUsers.setList(getNamesList(users));
            mListUsers.setList(users);

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
        if(users.size()>0) {
            currentPos=0;
            //mDetailsUser.startActivity();
            //mDetailsUser.showUser(users.get(currentPos));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("currentPos", currentPos);
    }

    public List<String> getNamesList(List<User> usersList){

        ArrayList<String> list = new ArrayList<String>();

        for(User u: usersList)
            list.add(u.getName());

        return list;
    }

    public void noElems(){

        LinearLayout l = (LinearLayout)findViewById(R.id.linear);
        l.setVisibility(View.GONE);

        TextView t= (TextView)findViewById(R.id.without_elems);
        t.setVisibility(View.VISIBLE);
    }

    public void removeUser(){
        mDetailsUser.clearDetails();

        userDao.deleteById(users.get(currentPos).getId());
        mListUsers.removeItem(currentPos);
        //users.remove(currentPos);

        currentPos = -1;
        MenuItem item = mOptionsMenu.findItem(R.id.action_del);
        item.setVisible(false);

        if(users.isEmpty())
            noElems();
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MenuItem item;

        if(users.size()==0)
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
        if(currentPos != -1) {
            mDetailsUser.showUser(users.get(currentPos));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_add:
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

        mListUsers.setList(users);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 112) {
            try {
                users = userDao.getAll();
                //ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, users);


                for(User u : users)
                {List<Team> t = user_team_dao.getByFirstId(u.getId());
                    if(t!=null)
                        if(t.size()>0)
                            u.setTeam(t.get(0)); // Get the Team
                }
                mListUsers.setList(users);
                //((BaseAdapter) mListUsers.getListAdapter()).notifyDataSetChanged();
                //mListUsers.setListAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, users));
            } catch (GenericDAOException e) {
                e.printStackTrace();
            }
        }else if (requestCode == 1112) {
            try {
                User user1 = userDao.getById(user_id);
                //ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, users);

                List<Team> t = user_team_dao.getByFirstId(user1.getId());
                    if(t!=null)
                        if(t.size()>0)
                            user1.setTeam(t.get(0)); // Get the Team

                mDetailsUser.showUser(user1);
                //((BaseAdapter) mListUsers.getListAdapter()).notifyDataSetChanged();
                //mListUsers.setListAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, users));
            } catch (GenericDAOException e) {
                e.printStackTrace();
            }
        }

    }


}
