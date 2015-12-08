package studentcompany.sportgest.Players;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;
import studentcompany.sportgest.R;
import studentcompany.sportgest.domains.Player;


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

        tv_birthday.setText(Integer.toString(player.getBirthDate()));
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

        if(player.getEmail()!=null)
            tv_email.setText(player.getEmail());
        else
            tv_email.setText("");

        if(player.getPreferredFoot()!=null)
            tv_preferredFoot.setText(player.getPreferredFoot());
        else
            tv_preferredFoot.setText("");

        tv_number.setText(String.valueOf(player.getNumber()));

        if(player.getPhoto()!=null)
            tv_photo.setImageURI(Uri.parse(player.getPhoto()));
        else
            tv_photo.setImageURI(Uri.parse(""));

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar_crud, menu);
        MenuItem editItem = menu.findItem(R.id.Edit);
        editItem.setVisible(false);
        return true;
    }
}
