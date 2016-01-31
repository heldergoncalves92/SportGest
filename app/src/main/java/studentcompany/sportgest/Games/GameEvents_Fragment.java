package studentcompany.sportgest.Games;


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
import studentcompany.sportgest.domains.Event;
import studentcompany.sportgest.domains.EventCategory;
import studentcompany.sportgest.domains.Player;
import studentcompany.sportgest.domains.Team;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameEvents_Fragment extends Fragment {

    private static final String ARG_POSITION = "position";

    private static List<Event> eventt;

    private List<EventCategory> eventcategoria;
    private TextView textView;
    private int position;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    public static GameEvents_Fragment newInstance(int position, List<Event> event) {
        GameEvents_Fragment f = new GameEvents_Fragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        eventt=event;
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

        View v;


                // Inflate the layout for this fragment
                v = inflater.inflate(R.layout.game_list_events_fragment, container, false);

                mRecyclerView = (RecyclerView) v.findViewById(R.id.event_recycler_view);
                mRecyclerView.setHasFixedSize(true);

                // use a linear layout manager
                mLayoutManager = new LinearLayoutManager(getContext());
                mRecyclerView.setLayoutManager(mLayoutManager);

                // specify an adapter (see also next example)
                eventcategoria = EventsTeste();
                mAdapter = new Game_ListEvents_Adapter(eventcategoria);
                mRecyclerView.setAdapter(mAdapter);


        /*
        View title = v.findViewById(R.id.game_squad_title);
        TextView tv = (TextView)title.findViewById(R.id.player_num);

        tv.setTypeface(null, Typeface.BOLD_ITALIC);
        tv.setTextColor(Color.BLACK);

        tv = (TextView)title.findViewById(R.id.player_name);
        tv.setTypeface(null, Typeface.BOLD_ITALIC);
        tv.setTextColor(Color.BLACK);



        */
        return v;
    }

    public static List<EventCategory> EventsTeste(){

        ArrayList<EventCategory> list = new ArrayList<EventCategory>();

        EventCategory a1 = new EventCategory(1,"passe",1,true);
        EventCategory a2 = new EventCategory(3,"remate",2,true);
        EventCategory a3 = new EventCategory(3,"corte",3,false);
        EventCategory a4 = new EventCategory(4,"canto",4,false);

        list.add(a1);
        list.add(a2);
        list.add(a3);
        list.add(a4);

        return list;
    }

}
