package studentcompany.sportgest.Roles;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import studentcompany.sportgest.R;
import studentcompany.sportgest.Users.RoleDisplayActivity;
import studentcompany.sportgest.daos.Permission_DAO;
import studentcompany.sportgest.daos.Role_DAO;
import studentcompany.sportgest.daos.Role_Permission_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Permission;
import studentcompany.sportgest.domains.Role;

public class Role_Activity_Create extends AppCompatActivity{

    private Role_DAO role_dao;
    private Role_Permission_DAO role_permission_dao;
    private Permission_DAO permission_dao;

    private List<Permission> permissions_list;

    private EditText roleName;
    private GridView rolePermissionsListView;
    private TextInputLayout inputLayoutName;

    //Id of current role displayed
    private long roleID = 0;
    private String roleNameOriginal="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.role_activity_create);

        //get layout components
        roleName = (EditText) findViewById(R.id.input_create_permission_name);
        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_create_permission_name);

        rolePermissionsListView = (GridView) findViewById(R.id.roleCreatePermissions);

        roleName.addTextChangedListener(new MyTextWatcher(roleName));

        rolePermissionsListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        //initialize required DAOs
        role_dao = new Role_DAO(this);
        role_permission_dao = new Role_Permission_DAO(this);
        permission_dao = new Permission_DAO(this);

        //variable for presented object
        Role role = null;

        //get extras with object id
        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            //get Role ID
            roleID = extras.getLong(Role_DAO.TABLE_NAME+Role_DAO.COLUMN_ID);

            System.err.println("[ROLE ID]" + roleID);

            //validation
            if(roleID > 0) {
                //get the role information
                try {
                    role = role_dao.getById(roleID);
                    roleNameOriginal = role.getName(); //Backup Name
                    roleName.setText(role.getName());
                } catch (GenericDAOException ex) {
                    System.err.println(RoleDisplayActivity.class.getName() + " [WARNING] " + ex.toString());
                    Logger.getLogger(RoleDisplayActivity.class.getName()).log(Level.WARNING, null, ex);
                    role = null;
                }
            }
                //set layout variables with information
                //roleName.setFocusable(false);
                //roleName.setClickable(false);


                //Construct a new array with only the Permission Description
                ArrayList<String> array_list = new ArrayList<>();
                //List<Permission> permissions = role.getPermissionList();
            List<Permission> permissions = null;
            if(roleID > 0)
                permissions = role_permission_dao.getPermissionsByRoleId(roleID);

            try {
                permissions_list = permission_dao.getAll();
            } catch (GenericDAOException e) {
                Toast.makeText(getApplicationContext(), R.string.permissions_list_failed, Toast.LENGTH_SHORT).show();
                finish();
                e.printStackTrace();
            }

            ArrayAdapter arrayAdapter;
            arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, permissions_list);

            //set list in layout ListView
            rolePermissionsListView.setAdapter(arrayAdapter);

            // Check the permissions
            if(permissions!=null) {
                for(int i = 0;i<permissions_list.size(); i++)
                    if(permissions.contains(permissions_list.get(i)))
                        rolePermissionsListView.setItemChecked(i,true);
            }



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

        //Toolbar
        //Toolbar toolbar = (Toolbar) findViewById(R.id.create_role_toolbar);
        //setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar_crud, menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.role_add_menu);



        MenuItem saveItem = menu.findItem(R.id.Save);
        saveItem.setVisible(true);

        MenuItem editItem = menu.findItem(R.id.Edit);
        editItem.setVisible(false);
        MenuItem remItem = menu.findItem(R.id.Delete);
        remItem.setVisible(false);
        MenuItem addItem = menu.findItem(R.id.Add);
        addItem.setVisible(false);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        long newId = 0;

        switch(item.getItemId())
        {
            case R.id.Save:
                if (!validateName()) {
                    return false;
                }

                //TextView roleName = (TextView) findViewById(R.id.input_create_permission_name);
                //ListView rolePermissionsListView = (ListView) findViewById(R.id.roleCreatePermissions);
                ArrayList<Permission> permissions = new ArrayList<>();
                SparseBooleanArray checkedItemPositions = rolePermissionsListView.getCheckedItemPositions();
                int len = rolePermissionsListView.getCount();
                for (int i = 0; i < len; i++)
                    if (checkedItemPositions.get(i)) {
                        permissions.add(permissions_list.get(i));// If the item is checked, then add it to the list
                    }
                Role role = new Role(roleID,roleName.getText().toString());
                boolean ret = false;
                try {
                    if(roleID>0){
                        ret = role_dao.update(role);
                        role_permission_dao.deleteAllByRoleId(role.getId());}
                    else{
                        newId = role_dao.insert(role);
                        ret = newId > 0;
                    }

                    if(ret && permissions.size()>0)
                        ret = role_permission_dao.insertPermissionsByRoleId(roleID,permissions);
                } catch (GenericDAOException e) {
                    e.printStackTrace();
                }

                if(ret){
                    Toast.makeText(getApplicationContext(), R.string.role_save_successful, Toast.LENGTH_SHORT).show();
                    Intent returnIntent = new Intent();

                    //Create
                    if(roleID == 0 ){
                        returnIntent.putExtra("ID", newId);
                        setResult(1, returnIntent);

                    } else{
                        //Update
                        setResult(2, returnIntent);
                    }
                    //returnIntent.putExtra("Age",11);

                    finish();

                } else
                    Toast.makeText(getApplicationContext(), R.string.role_save_unsuccessful, Toast.LENGTH_SHORT).show();

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override // When the back button is clicked
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }


    private boolean validateName() {
        if (roleName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_role_name));
            requestFocus(inputLayoutName);
            return false;
        } else try {
            if(!roleNameOriginal.equals(roleName.getText().toString()))
                if(role_dao.exists(new Role(roleName.getText().toString()))) {
                    inputLayoutName.setError(getString(R.string.err_role_name_exists));
                    requestFocus(inputLayoutName);
                    return false;
                }

        } catch (GenericDAOException e) {
            e.printStackTrace();
        }
            inputLayoutName.setErrorEnabled(false);
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_create_permission_name:
                    validateName();
                    break;
            }
        }
    }

    public void onStop(){
        super.onStop();

        Intent returnIntent = new Intent();
        setResult(0, returnIntent);
    }

}
