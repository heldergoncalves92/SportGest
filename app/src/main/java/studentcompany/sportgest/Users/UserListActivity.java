package studentcompany.sportgest.Users;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.User_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.User;

public class UserListActivity extends AppCompatActivity implements ListUser_Fragment.OnItemSelected {

    private User_DAO userDao;
    private List<User> users;


    private FragmentManager mFragmentManager;
    private ListUser_Fragment mListUsers = new ListUser_Fragment();
    private DetailsUser_Fragment mDetailsUser = new DetailsUser_Fragment();
    private static final String TAG = "USERS_ACTIVITY";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        this.testUsers();
        /*try {
            userDao = new User_DAO(getApplicationContext());
            insertUserTest(userDao);
            users = userDao.getAll();
            mListUsers.setUserList(getNamesList(users));

        } catch (GenericDAOException e) {
            e.printStackTrace();
        }*/


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


    public void itemSelected(int position) {
        User user = users.get(position);

        if(user != null){
            mDetailsUser.showUser(user);
        }
    }

    private void insertUserTest(User_DAO u_dao){

        try {
            User u1 = new User(0,"user0","password","photo0","António Joaquim","user0@email.com",null);
            User u2 = new User(1,"user1","password","photo1","João Dias","user1@email.com",null);
            User u3 = new User(2,"user2","password","photo2","Maria Andrade","user2@email.com",null);
            User u4 = new User(3,"user3","password","photo3","José Alves","user3@email.com",null);

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
