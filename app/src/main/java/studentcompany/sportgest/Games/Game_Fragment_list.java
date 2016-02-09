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

import java.util.List;

import studentcompany.sportgest.R;
import studentcompany.sportgest.domains.Game;

public class Game_Fragment_list extends Fragment {

    private static final String TAG = "LIST_EXERCISE_FRAGMENT";
    private List<Game> list;
    private TextView textview;
    private int position;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    OnItemSelected mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Active Listener to this activity
        try {
            mListener = (OnItemSelected)getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement OnArticleSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_games_list_fragment, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.games_recycler_view);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new Game_List_Adapter(list, mListener, getContext());
        mRecyclerView.setAdapter(mAdapter);

        return v;
    }

    public void setGameList(List<Game> list){
        this.list = list;
    }

    public Game removeItem(int position){
        Game g = list.remove(position);
        mAdapter.notifyItemRemoved(position);

        return g;
    }

    public void updateList(){
        // Set the list adapter for the ListView
        if(list != null) {
            mAdapter = new Game_List_Adapter(list, mListener,getContext());
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    public void updateList(List<Game> list){
        this.list = list;
        mAdapter = new Game_List_Adapter(list, mListener,getContext());
        mRecyclerView.setAdapter(mAdapter);
    }

    public void updatePosition(Game game, int position){
        this.list.set(position, game);
        mAdapter.notifyItemChanged(position);
    }

    public void insert_Item(Game game, int position){
        this.list.add(position, game);
        mAdapter.notifyItemInserted(position);
    }

    // Container Activity must implement this interface
    public interface OnItemSelected{
        void itemSelected(int position);
    }
}