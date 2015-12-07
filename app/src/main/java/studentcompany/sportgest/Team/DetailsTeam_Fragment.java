package studentcompany.sportgest.Team;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import studentcompany.sportgest.R;
import studentcompany.sportgest.domains.Team;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsTeam_Fragment extends Fragment {


    private static final String TAG = "DETAILS_TEAM_FRAGMENT";
    private TextView tv_name, tv_description,tv_season;
    private ImageView tv_logo;
    private CheckBox tv_isCom;


    public DetailsTeam_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        LayoutInflater lf = getActivity().getLayoutInflater();

        View view =  lf.inflate(R.layout.fragment_players_details, container, false);
        tv_name = (TextView) view.findViewById(R.id.name);
        tv_description = (TextView) view.findViewById(R.id.description);
        tv_season = (TextView) view.findViewById(R.id.season);
        tv_logo = (ImageView) view.findViewById(R.id.logo);
        tv_isCom = (CheckBox) view.findViewById(R.id.isCom);

        return view;
    }

    public void showTeam(Team team){

        tv_name.setText(team.getName());
        tv_description.setText(team.getDescription());
        tv_season.setText(team.getSeason());
        tv_logo.setImageURI(Uri.parse(team.getLogo()));
        boolean selected = team.getIs_com()==1;
        tv_isCom.setSelected(selected);
    }

    public void clearDetails(){

        tv_name.setText("");
        tv_description.setText("");
        tv_season.setText("");
        tv_isCom.setSelected(false);
        tv_logo.setImageURI(Uri.parse("defaulf"));
    }

}
