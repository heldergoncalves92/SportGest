package studentcompany.sportgest.Users;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import studentcompany.sportgest.R;
import studentcompany.sportgest.domains.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsUser_Fragment extends Fragment {

    private static final String TAG = "DETAILS_USER_FRAGMENT";
    private TextView tv_id, tv_name, tv_email;

    public DetailsUser_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        LayoutInflater lf = getActivity().getLayoutInflater();

        View view =  lf.inflate(R.layout.fragment_user_details, container, false);
        tv_id = (TextView) view.findViewById(R.id.user_id);
        tv_name = (TextView) view.findViewById(R.id.user_name);
        tv_email = (TextView) view.findViewById(R.id.user_email);
        return view;
    }

    public void showUser(User user){
        tv_id.setText(Integer.toString(user.getId()));
        tv_name.setText(user.getName());
        tv_email.setText(user.getEmail());
    }

    public void clearDetails(){
        tv_id.setText("");
        tv_name.setText("");
        tv_email.setText("");
    }
}
