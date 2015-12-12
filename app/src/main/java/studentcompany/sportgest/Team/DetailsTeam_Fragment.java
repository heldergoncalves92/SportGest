package studentcompany.sportgest.Team;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import studentcompany.sportgest.R;
import studentcompany.sportgest.domains.Team;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsTeam_Fragment extends Fragment {


    private static final String TAG = "DETAILS_TEAM_FRAGMENT";
    private TextView tv_name;

    public DetailsTeam_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LayoutInflater lf = getActivity().getLayoutInflater();

        View view =  lf.inflate(R.layout.fragment_team_details, container, false);
        tv_name = (TextView) view.findViewById(R.id.name);

        return view;
    }

    public void showTeam(Team player){
        tv_name.setText(player.getName());
    }

    public void clearDetails(){
        tv_name.setText("");
    }

}
