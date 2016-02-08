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
import studentcompany.sportgest.domains.EventCategory;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameStatistics_Fragment extends Fragment {

    private static final String ARG_POSITION = "position";


    private TextView textView;
    private int position;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    public static GameStatistics_Fragment newInstance(int position) {
        GameStatistics_Fragment f = new GameStatistics_Fragment();
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

        View v;

        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.game_statistics_fragment, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.sta_recycler_view);
        mRecyclerView.setHasFixedSize(true);


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


}
