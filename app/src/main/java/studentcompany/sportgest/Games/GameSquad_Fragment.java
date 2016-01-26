package studentcompany.sportgest.Games;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import studentcompany.sportgest.R;
import studentcompany.sportgest.domains.Attribute;
import studentcompany.sportgest.domains.Player;
import studentcompany.sportgest.domains.Team;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameSquad_Fragment extends Fragment {

    private static final String ARG_POSITION = "position";

    private List<Player> players;
    private TextView textView;
    private int position;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    public static GameSquad_Fragment newInstance(int position) {
        GameSquad_Fragment f = new GameSquad_Fragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(ARG_POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.game_squad_fragment, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.squad_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        players = playersToTest();
        mAdapter = new Game_SquadPlayer_Adapter(players);
        mRecyclerView.setAdapter(mAdapter);


        mRecyclerView = (RecyclerView) v.findViewById(R.id.advr_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        players = playersToTestadvr();
        mAdapter = new Game_SquadPlayer_Adapter(players);
        mRecyclerView.setAdapter(mAdapter);


        /*
        View title = v.findViewById(R.id.game_squad_title);
        TextView tv = (TextView)title.findViewById(R.id.player_num);

        tv.setTypeface(null, Typeface.BOLD_ITALIC);
        tv.setTextColor(Color.BLACK);

        tv = (TextView)title.findViewById(R.id.player_name);
        tv.setTypeface(null, Typeface.BOLD_ITALIC);
        tv.setTextColor(Color.BLACK);


        //textView = (TextView)v.findViewById(R.id.text_view);
        //textView.setText("CARD "+position);
        */
        return v;
    }


    public static List<Player> playersToTest(){

        ArrayList<Player> list = new ArrayList<Player>();

        Player p1 = new Player(1,"Jocka", "João Alberto", "Portuguesa", "Solteiro", "2012-12-12", 176 ,70.4f , "Travessa do Morro", "Masculino", "default.jpg", "player1@email.com", "Direito", 2, new Team(1), null);
        Player p2 = new Player(2,"Fabinho", "Fábio Gomes", "Portuguesa", "Solteiro", "2012-12-12", 170 ,83 , "Travessa do Morro", "Masculino", "default.jpg", "player1@email.com", "Direito", 4, new Team(1), null);
        Player p3 = new Player(3,"Jorge D.", "Jorge Duarte", "Portuguesa", "Solteiro", "2012-12-12", 180 ,73.6f , "Travessa do Morro", "Masculino", "default.jpg", "player1@email.com", "Esquerdo", 3, new Team(1), null);
        Player p4 = new Player(4,"Nel", "Manuel Arouca", "Portuguesa", "Solteiro", "2012-12-12", 194 ,69.69f , "Travessa do Morro", "Masculino", "default.jpg", "player1@email.com", "Direito", 1, new Team(2), null);

        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p4);

        return list;
    }

    public static List<Player> playersToTestadvr(){

        ArrayList<Player> list = new ArrayList<Player>();

        Player p1 = new Player(1,"Hulk", "João Alberto", "Portuguesa", "Solteiro", "2012-12-12", 176 ,70.4f , "Travessa do Morro", "Masculino", "default.jpg", "player1@email.com", "Direito", 9, new Team(1), null);
        Player p2 = new Player(2,"Rafa", "Fábio Gomes", "Portuguesa", "Solteiro", "2012-12-12", 170 ,83 , "Travessa do Morro", "Masculino", "default.jpg", "player1@email.com", "Direito", 10, new Team(1), null);
        Player p3 = new Player(3,"Rocha", "Jorge Duarte", "Portuguesa", "Solteiro", "2012-12-12", 180 ,73.6f , "Travessa do Morro", "Masculino", "default.jpg", "player1@email.com", "Esquerdo", 7, new Team(1), null);
        Player p4 = new Player(4,"Roberto", "Manuel Arouca", "Portuguesa", "Solteiro", "2012-12-12", 194 ,69.69f , "Travessa do Morro", "Masculino", "default.jpg", "player1@email.com", "Direito", 1, new Team(2), null);

        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p4);

        return list;
    }

    public static List<Attribute> atributos(){

        ArrayList<Attribute> list = new ArrayList<Attribute>();

        Attribute a1 = new Attribute(1,"senas","passe",1);
        Attribute a2 = new Attribute(2,"senas","remate",1);
        Attribute a3 = new Attribute(3,"senas","corte",1);
        Attribute a4 = new Attribute(4,"senas","desmarcaçao",1);

        list.add(a1);
        list.add(a2);
        list.add(a3);
        list.add(a4);

        return list;
    }

}
