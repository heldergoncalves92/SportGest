package studentcompany.sportgest.Team;


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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
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
public class Team_Fragment_Details extends Fragment {


    private static final String TAG = "DETAILS_TEAM_FRAGMENT";
    private TextView tv_name, tv_description,tv_season;
    private ImageView et_photo;
    private CheckBox tv_isCom;
    private ListView tv_squad;
    private TextView text_squad;

    private Player_DAO player_dao;

    public Team_Fragment_Details() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LayoutInflater lf = getActivity().getLayoutInflater();

        View view =  lf.inflate(R.layout.team_fragment_details, container, false);
        tv_name = (TextView) view.findViewById(R.id.name);
        tv_description = (TextView) view.findViewById(R.id.description);
        tv_season = (TextView) view.findViewById(R.id.season);
        et_photo = (ImageView) view.findViewById(R.id.input_details_user_photo);
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

        String pho = team.getLogo();
        if(pho == null)
        {
            Drawable myDrawable;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                myDrawable = getResources().getDrawable(R.drawable.team_default, getContext().getTheme());
            } else {
                myDrawable = getResources().getDrawable(R.drawable.team_default);
            }
            et_photo.setImageDrawable(myDrawable);
        }
        else
            et_photo.setImageBitmap(getImageBitmap(this.getContext(), pho));

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

        View v = getView().findViewById(R.id.frame_details);
        v.setVisibility(View.GONE);

        v = getView().findViewById(R.id.no_Selection);
        v.setVisibility(View.VISIBLE);

        tv_name.setText("");
        tv_description.setText("");
        tv_season.setText("");
        tv_isCom.setChecked(false);
        tv_squad.setAdapter(null);
        text_squad.setText("");
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
