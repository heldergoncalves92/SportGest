package studentcompany.sportgest.Games;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import studentcompany.sportgest.R;
import studentcompany.sportgest.domains.Event;

/**
 * A simple {@link Fragment} subclass.
 */

public class Game_Fragment_GameMode_History extends Fragment {


    private static final String TAG = "HISTORY_GAME_MODE_LIST_FRAGMENT";
    private List<Event> list;
    private int tag = 0;
    OnItemSelected mListener;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private View deleteButton = null;


    public Game_Fragment_GameMode_History() {
        // Required empty public constructor
    }

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
        View v = inflater.inflate(R.layout.game_fragment_game_mode_history, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.history_recyclerView);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new Game_GameMode_History_List_Adapter(list, mListener, tag);
        mRecyclerView.setAdapter(mAdapter);

        deleteButton = v.findViewById(R.id.delete_action);

        return v;

    }

    public void setList(List<Event> list){
        this.list = list;
    }

    public void setTag(int tag){
        this.tag = tag;
    }

    public void updateList(List<Event> list){
        this.list = list;

        mAdapter = new Game_GameMode_History_List_Adapter(list, mListener, tag);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void updatePosition(Event player, int position){
        this.list.set(position, player);
        mAdapter.notifyItemChanged(position);
    }

    public void insert_Item(Event event){
        this.list.add(0, event);
        mAdapter.notifyItemInserted(0);
    }

    public void selectFirstItem(){

        Game_GameMode_History_List_Adapter.ViewHolder v = (Game_GameMode_History_List_Adapter.ViewHolder) mRecyclerView.findViewHolderForAdapterPosition(0);
        v.focus_gain();
    }

    public void unselect_Item(int position){

        Game_GameMode_History_List_Adapter.ViewHolder v = (Game_GameMode_History_List_Adapter.ViewHolder) mRecyclerView.findViewHolderForAdapterPosition(position);
        v.focus_loss();
    }


    public Event removeItem(int position){
        Event p = list.remove(position);
        mAdapter.notifyItemRemoved(position);

        return p;
    }

    public int has_Selection(){
        return ((Game_GameMode_History_List_Adapter) mAdapter).getCurrentPos();
    }

    public void hideButtons(){
        deleteButton.setVisibility(View.GONE);
    }

    public void showButtons(){
        deleteButton.setVisibility(View.VISIBLE);
    }

    /************************************
     ****     Listener Functions     ****
     ************************************/

    // Container Activity must implement this interface
    public interface OnItemSelected{
        void itemSelected(int position, int tag);
        void itemDesselected(int position, int tag);
    }

    public Event getCurrentItem(){
        if(mAdapter == null)
            return null;
        else
            return ((Game_GameMode_History_List_Adapter) mAdapter).getCurrentItem();
    }

}
