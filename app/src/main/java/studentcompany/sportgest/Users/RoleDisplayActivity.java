package studentcompany.sportgest.Users;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Role_DAO;
import studentcompany.sportgest.daos.Role_Permission_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Permission;
import studentcompany.sportgest.domains.Role;

public class RoleDisplayActivity extends AppCompatActivity {

    //DAOs
    private Role_DAO role_dao;
    private Role_Permission_DAO role_permission_dao;

    //Id of current role displayed
    private int roleID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set activity layout
        setContentView(R.layout.activity_display_role);
        //get layout components
        TextView roleName = (TextView) findViewById(R.id.input_permission_name);
        ListView rolePermissionsListView = (ListView) findViewById(R.id.rolePermissions);
        //initialize required DAOs
        role_dao = new Role_DAO(this);
        role_permission_dao = new Role_Permission_DAO(this);

        //variable for presented object
        Role role = null;

        //get extras with object id
        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            //get Role ID
            roleID = extras.getInt(Role_DAO.TABLE_NAME+Role_DAO.COLUMN_ID);

            System.err.println("[ROLE ID]" + roleID);

            //validation
            if(roleID > 0){
                //get the role information
                try {
                    role = role_dao.getById(roleID);
                } catch (GenericDAOException ex){
                    System.err.println(RoleDisplayActivity.class.getName() + " [WARNING] " + ex.toString());
                    Logger.getLogger(RoleDisplayActivity.class.getName()).log(Level.WARNING, null, ex);
                    role = null;
                }
                System.err.println("[INFO] " + role.toString());
                //validation
                if(role != null) {
                    //set layout variables with information
                    roleName.setText(role.getName());
                    roleName.setFocusable(false);
                    roleName.setClickable(false);


                    //Construct a new array with only the Permission Description
                    ArrayList<String> array_list = new ArrayList<>();
                    //List<Permission> permissions = role.getPermissionList();
                    List<Permission> permissions = role_permission_dao.getPermissionsByRoleId(roleID);
                    for(Permission p : permissions){
                        array_list.add(p.getDescription());
                    }
                    //ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, array_list);
                    ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, permissions);

                    //set list in layout ListView
                    rolePermissionsListView.setAdapter(arrayAdapter);

                    //in case the user wants to see details about some attribute, start a display Permission Activity
                    rolePermissionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                            // TODO Auto-generated method stub -> implement edit attribute details
                            /*int id_To_Search = arg2 + 1;

                            Bundle dataBundle = new Bundle();
                            dataBundle.putInt("id", id_To_Search);

                            Intent intent = new Intent(getApplicationContext(), DisplayEventCategoryActivity.class);

                            intent.putExtras(dataBundle);
                            startActivity(intent);*/
                        }
                    });
                }
            }
        }

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.display_role_toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar_crud, menu);
        MenuItem addItem = menu.findItem(R.id.Add);
        addItem.setVisible(false);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);
        switch(item.getItemId())
        {
            case R.id.Edit:
                //put current role ID in extras
                Bundle dataBundle = new Bundle();
                dataBundle.putInt(Role_DAO.TABLE_NAME+Role_DAO.COLUMN_ID, roleID);
                //declare intention to start CreateRoleActivity
                Intent intent = new Intent(getApplicationContext(), CreateRole_Activity.class);
                //add data
                intent.putExtras(dataBundle);
                //start activity
                startActivity(intent);

                return true;
            case R.id.Delete:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.delete_confirmation)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if(role_dao.deleteById(roleID))
                                    Toast.makeText(getApplicationContext(), R.string.delete_sucessful, Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(getApplicationContext(), R.string.delete_unsucessful, Toast.LENGTH_SHORT).show();
                                finish();
                                //Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                //startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                AlertDialog d = builder.create();
                d.setTitle(R.string.are_you_sure);
                d.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
