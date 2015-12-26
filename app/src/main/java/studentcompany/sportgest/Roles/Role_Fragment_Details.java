package studentcompany.sportgest.Roles;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import studentcompany.sportgest.R;
import studentcompany.sportgest.domains.Permission;
import studentcompany.sportgest.domains.Role;

/**
 * A simple {@link Fragment} subclass.
 */
public class Role_Fragment_Details extends Fragment {

    //Id of current role displayed
    private long roleID;
    private EditText roleName;
    private ListView rolePermissionsListView;

    public Role_Fragment_Details() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        LayoutInflater lf = getActivity().getLayoutInflater();

        View view =  lf.inflate(R.layout.role_fragment_details, container, false);
        //get layout components
        roleName = (EditText) view.findViewById(R.id.input_permission_name);
        roleName.setSelected(false);

        rolePermissionsListView = (ListView) view.findViewById(R.id.rolePermissions);

        return view;
    }

    public void showRole(Role role){
        roleName.setText(role.getName());

        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), R.layout.role_item_permission, role.getPermissionList());

        //set list in layout ListView
        rolePermissionsListView.setAdapter(arrayAdapter);
    }

    public void clearDetails(){
        roleName.setText("");
    }


}
