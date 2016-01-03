package studentcompany.sportgest.Team;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Player_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Player;
import studentcompany.sportgest.domains.Team;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsTeam_Fragment extends Fragment {


    private static final String TAG = "DETAILS_TEAM_FRAGMENT";
    private TextView tv_name, tv_description,tv_season;
    private ImageView tv_logo;
    private CheckBox tv_isCom;
    private ListView tv_squad;
    private TextView text_squad;

    private Player_DAO player_dao;

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
        tv_description = (TextView) view.findViewById(R.id.description);
        tv_season = (TextView) view.findViewById(R.id.season);
        tv_logo = (ImageView) view.findViewById(R.id.logo);
        tv_isCom = (CheckBox) view.findViewById(R.id.isCom);
        tv_squad = (ListView) view.findViewById(R.id.squad);
        text_squad = (TextView) view.findViewById(R.id.text_squad);
        tv_squad.setAdapter(null);

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
        tv_isCom.setChecked(selected);

        player_dao = new Player_DAO(getContext());
        Player playerToSearch = new Player(team);
        List<Player> teamSquad = null;
        try {
            teamSquad = player_dao.getByCriteria(playerToSearch);
        } catch (GenericDAOException e) {
            e.printStackTrace();
        }
        if(teamSquad.size()>0){
            text_squad.setText("Squad");
            ArrayList<String> squadNames = new ArrayList<>();
            for(Player p : teamSquad)
                squadNames.add(p.getName());

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                    android.R.layout.simple_list_item_1, squadNames);
            tv_squad.setAdapter(adapter);
        }
        else {
            text_squad.setText("");
            tv_squad.setAdapter(null);
        }
    }

    public void clearDetails(){

        tv_name.setText("");
        tv_description.setText("");
        tv_season.setText("");
        tv_isCom.setChecked(false);
        tv_logo.setImageURI(Uri.parse("lego_face"));
        tv_squad.setAdapter(null);
        text_squad.setText("");
    }
}
