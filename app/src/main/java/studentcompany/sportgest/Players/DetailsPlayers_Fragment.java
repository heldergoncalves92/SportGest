package studentcompany.sportgest.Players;


import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Date;

import studentcompany.sportgest.R;
import studentcompany.sportgest.domains.Player;
import studentcompany.sportgest.domains.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsPlayers_Fragment extends Fragment {

    private static final String TAG = "DETAILS_PLAYER_FRAGMENT";
    private TextView tv_nickname, tv_name,tv_address, tv_email,tv_height, tv_weight;
    private TextView tv_birthday, tv_position;
    private TextView tv_nationality,tv_gender,tv_preferredFoot, tv_maritalStatus,tv_number;
    private ImageView tv_photo;


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
        tv_maritalStatus = (TextView) view.findViewById(R.id.maritalstatus);
        tv_birthday = (TextView) view.findViewById(R.id.birthday);
        tv_height = (TextView) view.findViewById(R.id.height);
        tv_weight = (TextView) view.findViewById(R.id.weight);
        tv_address = (TextView) view.findViewById(R.id.address);
        tv_gender = (TextView) view.findViewById(R.id.gender);
        tv_email = (TextView) view.findViewById(R.id.email);
        tv_preferredFoot = (TextView) view.findViewById(R.id.preferredfoot);
        tv_number = (TextView) view.findViewById(R.id.number);
        tv_photo = (ImageView) view.findViewById(R.id.photo);
        tv_position = (TextView) view.findViewById(R.id.position);

        return view;
    }

    public void showPlayer(Player player){

        tv_nickname.setText(player.getNickname());
        tv_name.setText(player.getName());
        tv_nationality.setText(player.getNationality());
        tv_maritalStatus.setText(player.getMarital_status()!=null?player.getMarital_status():"Solteiro");
        tv_birthday.setText(Integer.toString(player.getBirthDate()));
        tv_height.setText(String.valueOf(player.getHeight()));
        tv_weight.setText(String.valueOf(player.getWeight()));
        tv_address.setText(player.getAddress());
        tv_gender.setText(player.getGender());
        tv_email.setText(player.getEmail());
        tv_preferredFoot.setText(player.getPreferredFoot());
        tv_number.setText(String.valueOf(player.getNumber()));
        tv_photo.setImageURI(Uri.parse(player.getPhoto()));
        String position = "";
        if(player.getPosition()!=null)
            position=player.getPosition().getName();
        if(position!=null)
            tv_position.setText(position);
        else
            tv_position.setText("");
    }

    public void clearDetails(){

        tv_nickname.setText("");
        tv_name.setText("");
        tv_nationality.setText("");
        tv_gender.setText("");
        tv_birthday.setText("");
        tv_height.setText("");
        tv_weight.setText("");
        tv_address.setText("");
        tv_gender.setText("");
        tv_email.setText("");
        tv_preferredFoot.setText("");
        tv_number.setText("");
        tv_photo.setImageURI(Uri.parse("defaulf"));
        tv_position.setText("");
    }
}
