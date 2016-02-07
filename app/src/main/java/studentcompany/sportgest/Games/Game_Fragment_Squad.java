package studentcompany.sportgest.Games;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import studentcompany.sportgest.R;
import studentcompany.sportgest.domains.Attribute;
import studentcompany.sportgest.domains.Player;
import studentcompany.sportgest.domains.Team;

/**
 * A simple {@link Fragment} subclass.
 */
public class Game_Fragment_Squad extends Fragment {

    private static final String ARG_POSITION = "position";

    private List<Player> home_squad;
    private List<Player> visitor_squad;
    private int position;

    private RecyclerView mRecyclerView_home, mRecyclerView_visitor;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    public static Game_Fragment_Squad newInstance(int position, List<Player> playershome, List<Player> playersvisitor) {
        Game_Fragment_Squad f = new Game_Fragment_Squad();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);

        f.set_HomeSquad(playershome);
        f.set_VisitorSquad(playersvisitor);

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

        mRecyclerView_home = (RecyclerView) v.findViewById(R.id.squad_recycler_view);
        mRecyclerView_home.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView_home.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new Game_SquadPlayer_Adapter(home_squad);
        mRecyclerView_home.setAdapter(mAdapter);


        mRecyclerView_visitor = (RecyclerView) v.findViewById(R.id.advr_recycler_view);
        mRecyclerView_visitor.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView_visitor.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new Game_SquadPlayer_Adapter(visitor_squad);
        mRecyclerView_visitor.setAdapter(mAdapter);

        return v;
    }

    private void set_HomeSquad(List<Player> list){
        this.home_squad = list;
    }

    private void set_VisitorSquad(List<Player> list){
        this.visitor_squad = list;
    }


    public void update_HomeSquad(List<Player> list){
        set_HomeSquad(list);
        // specify an adapter (see also next example)
        mAdapter = new Game_SquadPlayer_Adapter(home_squad);
        mRecyclerView_home.setAdapter(mAdapter);
    }

    public void update_VisitorSquad(List<Player> list){
        set_VisitorSquad(list);
        // specify an adapter (see also next example)
        mAdapter = new Game_SquadPlayer_Adapter(home_squad);
        mRecyclerView_visitor.setAdapter(mAdapter);
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
