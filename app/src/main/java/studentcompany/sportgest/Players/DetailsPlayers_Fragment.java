package studentcompany.sportgest.Players;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import studentcompany.sportgest.R;
import studentcompany.sportgest.domains.Player;
import studentcompany.sportgest.domains.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsPlayers_Fragment extends Fragment {

    private static final String TAG = "DETAILS_PLAYER_FRAGMENT";
    private TextView tv_id, tv_name, tv_email;


    public DetailsPlayers_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        LayoutInflater lf = getActivity().getLayoutInflater();

        View view =  lf.inflate(R.layout.fragment_players_details, container, false);
        tv_id = (TextView) view.findViewById(R.id.player_id);
        tv_name = (TextView) view.findViewById(R.id.player_name);
        tv_email = (TextView) view.findViewById(R.id.player_email);
        return view;
    }

    public void showPlayer(Player player){
        tv_id.setText(Integer.toString(player.getId()));
        tv_name.setText(player.getName());
        tv_email.setText(player.getEmail());
    }

    public void clearDetails(){
        tv_id.setText("");
        tv_name.setText("");
        tv_email.setText("");
    }
}
