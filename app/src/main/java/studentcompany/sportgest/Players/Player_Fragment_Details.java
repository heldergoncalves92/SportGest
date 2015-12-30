package studentcompany.sportgest.Players;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Player_Position_DAO;
import studentcompany.sportgest.domains.Player;
import studentcompany.sportgest.domains.PlayerPosition;

/**
 * A simple {@link Fragment} subclass.
 */
public class Player_Fragment_Details extends Fragment {

    private static final String TAG = "DETAILS_PLAYER_FRAGMENT";
    private TextView tv_nickname, tv_name,tv_address, tv_email,tv_height, tv_weight;
    private TextView tv_birthday;
    private ListView tv_position;
    private TextView tv_nationality,tv_gender,tv_preferredFoot, tv_maritalStatus,tv_number;
    private ImageView tv_photo;

    public Player_Fragment_Details() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        LayoutInflater lf = getActivity().getLayoutInflater();

        View view =  lf.inflate(R.layout.player_fragment_details, container, false);
        tv_nickname = (TextView) view.findViewById(R.id.nickname);
        tv_name = (TextView) view.findViewById(R.id.name);
        tv_nationality = (TextView) view.findViewById(R.id.nationality);
        tv_maritalStatus = (TextView) view.findViewById(R.id.maritalstatus);
        tv_birthday = (TextView) view.findViewById(R.id.txtDate);
        tv_height = (TextView) view.findViewById(R.id.height);
        tv_weight = (TextView) view.findViewById(R.id.weight);
        tv_address = (TextView) view.findViewById(R.id.address);
        tv_gender = (TextView) view.findViewById(R.id.gender);
        tv_email = (TextView) view.findViewById(R.id.email);
        tv_preferredFoot = (TextView) view.findViewById(R.id.preferredfoot);
        tv_number = (TextView) view.findViewById(R.id.number);
        tv_photo = (ImageView) view.findViewById(R.id.photo);
        tv_position = (ListView) view.findViewById(R.id.position);

        return view;
    }

    public void showPlayer(Player player){
        if(player.getNickname()!=null)
            tv_nickname.setText(player.getNickname());
        else
            tv_nickname.setText("");

        if(player.getName()!=null)
            tv_name.setText(player.getName());
        else
            tv_name.setText("");

        if(player.getNationality()!=null)
            tv_nationality.setText(player.getNationality());
        else
            tv_nationality.setText("");

        if(player.getMarital_status()!=null)
            tv_maritalStatus.setText(player.getMarital_status());
        else
            tv_maritalStatus.setText("");

        if(player.getBirthDate()!=null)
            tv_birthday.setText(player.getBirthDate());
        else
            tv_birthday.setText("");
        tv_height.setText(String.valueOf(player.getHeight()));
        tv_weight.setText(String.valueOf(player.getWeight()));

        if(player.getAddress()!=null)
            tv_address.setText(player.getAddress());
        else
            tv_address.setText("");

        if(player.getGender()!=null)
            tv_gender.setText(player.getGender());
        else
            tv_gender.setText("");

        if(player.getEmail() != null)
            tv_email.setText(player.getEmail());
        else
            tv_email.setText("");

        if(player.getPreferredFoot()!= null)
            tv_preferredFoot.setText(player.getPreferredFoot());
        else
            tv_preferredFoot.setText("");

        tv_number.setText(String.valueOf(player.getNumber()));

        if(player.getPhoto()!=null)
            tv_photo.setImageURI(Uri.parse(player.getPhoto()));
        else
            tv_photo.setImageURI(Uri.parse(""));

        ArrayList<String> positionValue = new ArrayList<>();

        if(player.getPositions()!=null){
            for(PlayerPosition p : player.getPositions()){
                positionValue.add(p.toString());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_1, positionValue);
        tv_position.setAdapter(adapter);

        /*String position = "";
        if(player.getPosition()!=null)
            position=player.getPosition().getName();
        if(position!=null)
            tv_position.setText(position);
        else
            tv_position.setText("");*/


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
        tv_photo.setImageURI(Uri.parse("lego_face"));

        ArrayList<String> emp = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_1, emp);
        tv_position.setAdapter(adapter);

    }
}
