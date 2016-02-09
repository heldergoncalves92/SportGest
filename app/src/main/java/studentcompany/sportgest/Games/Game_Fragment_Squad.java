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
    private RecyclerView.Adapter mAdapter_Home, mAdapter_Visitor;
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
        mAdapter_Home = new Game_SquadPlayer_Adapter(home_squad);
        mRecyclerView_home.setAdapter(mAdapter_Home);


        mRecyclerView_visitor = (RecyclerView) v.findViewById(R.id.advr_recycler_view);
        mRecyclerView_visitor.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView_visitor.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter_Visitor = new Game_SquadPlayer_Adapter(visitor_squad);
        mRecyclerView_visitor.setAdapter(mAdapter_Visitor);

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
        this.home_squad = list;
        mAdapter_Home = new Game_SquadPlayer_Adapter(home_squad);
        mRecyclerView_home.setAdapter(mAdapter_Home);
    }

    public void update_VisitorSquad(List<Player> list){
        set_VisitorSquad(list);
        // specify an adapter (see also next example)
        this.visitor_squad = list;
        mAdapter_Visitor = new Game_SquadPlayer_Adapter(visitor_squad);
        mRecyclerView_visitor.setAdapter(mAdapter_Visitor);
    }
}
