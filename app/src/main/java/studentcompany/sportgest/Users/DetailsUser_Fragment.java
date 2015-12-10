package studentcompany.sportgest.Users;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.EditText;

import studentcompany.sportgest.R;
import studentcompany.sportgest.domains.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsUser_Fragment extends Fragment {

    private static final String TAG = "DETAILS_USER_FRAGMENT";
    private EditText et_username, et_name, et_email,et_role,et_team;
    private ImageView et_photo;

    public DetailsUser_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        LayoutInflater lf = getActivity().getLayoutInflater();

        View view =  lf.inflate(R.layout.fragment_user_details, container, false);
        et_username = (EditText) view.findViewById(R.id.input_details_user_username);
        et_name = (EditText) view.findViewById(R.id.input_details_user_name);
        et_email = (EditText) view.findViewById(R.id.input_details_user_email);
        et_role = (EditText) view.findViewById(R.id.input_details_user_role);
        et_team = (EditText) view.findViewById(R.id.input_details_user_team);
        et_photo = (ImageView) view.findViewById(R.id.input_details_user_photo);

        return view;
    }

    public void showUser(User user){
        et_username.setText(user.getUsername());
        et_name.setText(user.getName());
        et_email.setText(user.getEmail());

        if(user.getRole()!=null)
            et_role.setText(user.getRole().getName());
        else
            et_role.setText("");

        if(user.getTeam()!=null)
            et_role.setText(user.getTeam().getName());
        else
            et_role.setText("");

        et_photo.setImageURI(Uri.parse(user.getPhoto()));
    }

    public void clearDetails(){
        et_username.setText("");
        et_name.setText("");
        et_email.setText("");
        et_role.setText("");
        et_team.setText("");
        et_photo.setImageURI(Uri.parse("default"));
    }
}
