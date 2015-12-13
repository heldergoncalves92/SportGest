package studentcompany.sportgest.Team;


import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import studentcompany.sportgest.R;
import studentcompany.sportgest.domains.Team;

public class DetailsSquad_Fragment extends AppCompatActivity {


    private static final String TAG = "DETAILS_TEAM_FRAGMENT";
    private TextView tv_name, tv_description,tv_season;
    private ImageView tv_logo;
    private CheckBox tv_isCom;

    int teamID = -1;

    public DetailsSquad_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LayoutInflater lf = getActivity().getLayoutInflater();

        View view =  lf.inflate(R.layout.fragment_team_details, container, false);
        tv_name = (TextView) view.findViewById(R.id.name);
        tv_description = (TextView) view.findViewById(R.id.description);
        tv_season = (TextView) view.findViewById(R.id.season);
        tv_logo = (ImageView) view.findViewById(R.id.logo);
        tv_isCom = (CheckBox) view.findViewById(R.id.isCom);

        Bundle b = getIntent().getExtras();
        if(b!=null){
            teamID = b.getInt("id");
        }

        return view;
    }

    public void showTeam(Team team){

        if(team.getName()!=null)
            tv_name.setText(team.getName());
        else
            tv_name.setText("");

        if(team.getDescription()!=null)
            tv_description.setText(team.getDescription());
        else
            tv_description.setText("");

        tv_season.setText(Integer.toString(team.getSeason()));

        if(team.getLogo()!=null)
            tv_logo.setImageURI(Uri.parse(team.getLogo()));
        else
            tv_logo.setImageURI(Uri.parse(""));

        boolean selected = team.getIs_com()==1;
        tv_isCom.setSelected(selected);
    }

    public void clearDetails(){

        tv_name.setText("");
        tv_description.setText("");
        tv_season.setText("");
        tv_isCom.setSelected(false);
        tv_logo.setImageURI(Uri.parse("lego_face"));
    }

}
