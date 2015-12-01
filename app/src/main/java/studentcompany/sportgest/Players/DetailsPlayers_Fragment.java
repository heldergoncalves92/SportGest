package studentcompany.sportgest.Players;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import studentcompany.sportgest.R;
import studentcompany.sportgest.domains.Player;
import studentcompany.sportgest.domains.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsPlayers_Fragment extends Fragment {

    private static final String TAG = "DETAILS_PLAYER_FRAGMENT";
    private TextView tv_nickname, tv_name, tv_nationality, tv_married, tv_single;
    private TextView tv_address, tv_male, tv_female, tv_email, tv_left, tv_right;
    private DatePicker tv_birthday;
    private TextView tv_height, tv_weight, tv_number;


    public DetailsPlayers_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        LayoutInflater lf = getActivity().getLayoutInflater();

        View view =  lf.inflate(R.layout.fragment_players_details, container, false);
        tv_nickname = (TextView) view.findViewById(R.id.nickname);
        tv_name = (TextView) view.findViewById(R.id.name);
        tv_nationality = (TextView) view.findViewById(R.id.nationality);
        tv_married = (TextView) view.findViewById(R.id.married);
        tv_single = (TextView) view.findViewById(R.id.single);
        tv_birthday = (DatePicker) view.findViewById(R.id.birthday);
        tv_height = (TextView) view.findViewById(R.id.height);
        tv_weight = (TextView) view.findViewById(R.id.weight);
        tv_address = (TextView) view.findViewById(R.id.address);
        tv_male = (TextView) view.findViewById(R.id.male);
        tv_female = (TextView) view.findViewById(R.id.female);
        tv_email = (TextView) view.findViewById(R.id.email);
        tv_left = (TextView) view.findViewById(R.id.left);
        tv_right = (TextView) view.findViewById(R.id.right);
        tv_number = (TextView) view.findViewById(R.id.male);

        return view;
    }

    public void showPlayer(Player player){
        tv_nickname.setText(player.getNickname());
        tv_name.setText(player.getName());
        tv_nationality.setText(player.getNationality());
        tv_married.setSelected(false);
        tv_single.setSelected(false);
        if(player.getMarital_status().equals("single"))
            tv_single.setSelected(true);
        else
            tv_married.setSelected(true);
        tv_birthday.updateDate(player.getBirthDate(),0,0);
        tv_height.setText(player.getHeight());
        tv_weight.setText(String.valueOf(player.getWeight()));
        tv_address.setText(player.getAddress());
        tv_male.setSelected(false);
        tv_female.setSelected(false);
        if(player.getGender().equals("male"))
            tv_male.setSelected(true);
        else
            tv_female.setSelected(true);
        tv_email.setText(player.getEmail());
        tv_left.setSelected(false);
        tv_right.setSelected(false);
        if(player.getPreferredFoot().equals("right"))
            tv_right.setSelected(true);
        else
            tv_left.setSelected(true);
        tv_number.setText(player.getNumber());
    }

    public void clearDetails(){
        tv_nickname.setText("");
        tv_name.setText("");
        tv_nationality.setText("");
        tv_married.setSelected(false);
        tv_single.setSelected(false);
        tv_birthday.updateDate(0,0,0);
        tv_height.setText("");
        tv_weight.setText("");
        tv_address.setText("");
        tv_male.setSelected(false);
        tv_female.setSelected(false);
        tv_email.setText("");
        tv_left.setSelected(false);
        tv_right.setSelected(false);
        tv_number.setText("");
    }
}
