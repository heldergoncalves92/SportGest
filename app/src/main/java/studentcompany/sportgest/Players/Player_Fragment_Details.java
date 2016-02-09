package studentcompany.sportgest.Players;


import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Player_Position_DAO;
import studentcompany.sportgest.daos.Team_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Player;
import studentcompany.sportgest.domains.PlayerPosition;
import studentcompany.sportgest.domains.Team;

/**
 * A simple {@link Fragment} subclass.
 */
public class Player_Fragment_Details extends Fragment {

    private static final String TAG = "DETAILS_PLAYER_FRAGMENT";
    private TextView tv_nickname, tv_name,tv_address, tv_email,tv_height, tv_weight;
    private TextView tv_birthday;
    private ListView tv_position;
    private TextView tv_nationality,tv_gender,tv_preferredFoot, tv_maritalStatus,tv_number;
    private EditText tv_team;
    private ImageView et_photo;

    private Team_DAO team_dao;

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
        et_photo = (ImageView) view.findViewById(R.id.input_details_user_photo);
        tv_position = (ListView) view.findViewById(R.id.position);
        tv_team = (EditText) view.findViewById(R.id.team);

        tv_position.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        team_dao = new Team_DAO(getActivity());

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

        String pho = player.getPhoto();
        if(pho == null)
        {
            Drawable myDrawable;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                myDrawable = getResources().getDrawable(R.drawable.lego_face, getContext().getTheme());
            } else {
                myDrawable = getResources().getDrawable(R.drawable.lego_face);
            }
            et_photo.setImageDrawable(myDrawable);
        }
        else
            et_photo.setImageBitmap(getImageBitmap(this.getContext(), pho));

        ArrayList<String> positionValue = new ArrayList<>();

        Player_Position_DAO pp_dao = new Player_Position_DAO(getContext());
        PlayerPosition ppToSearch = new PlayerPosition(player,null,-1);
        List<PlayerPosition> listPositions = null;
        try {
            listPositions = pp_dao.getByCriteria(ppToSearch);
        } catch (GenericDAOException e){}
        if(listPositions!=null) {
            for (PlayerPosition p : listPositions) {
                positionValue.add(p.toString());
            }

            tv_position.setEnabled(true);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(),
                    R.layout.player_listview_for_positions, positionValue);
            tv_position.setAdapter(adapter);
        }

        try {

            if(player.getTeam() != null){
                Team t = team_dao.getById(player.getTeam().getId());
                tv_team.setText(t.getName());
            }
        } catch (GenericDAOException e) {
            e.printStackTrace();
        }

        /*String position = "";
        if(player.getPosition()!=null)
            position=player.getPosition().getName();
        if(position!=null)
            tv_position.setText(position);
        else
            tv_position.setText("");*/


    }

    public void clearDetails(){

        View v = getView().findViewById(R.id.frame_details);
        v.setVisibility(View.GONE);

        v = getView().findViewById(R.id.no_Selection);
        v.setVisibility(View.VISIBLE);

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
        //tv_photo.setImageURI(Uri.parse("lego_face"));

        ArrayList<String> emp = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_1, emp);
        tv_position.setAdapter(adapter);

    }

    public void showFirstElem(){
        View v = getView().findViewById(R.id.frame_details);
        v.setVisibility(View.VISIBLE);

        v = getView().findViewById(R.id.no_Selection);
        v.setVisibility(View.GONE);
    }

    public Bitmap getImageBitmap(Context context,String name){
        //name=name+"."+extension;
        try{
            ContextWrapper cw = new ContextWrapper(this.getContext());
            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
            // Create imageDir
            File mypath=new File(directory,name);
            FileInputStream fis = new FileInputStream(mypath);
            Bitmap b = BitmapFactory.decodeStream(fis);
            fis.close();
            return b;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
