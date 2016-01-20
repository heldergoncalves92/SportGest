package studentcompany.sportgest.Games;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import studentcompany.sportgest.Games.GameMode_Event_List_Adapter;
import studentcompany.sportgest.R;
import studentcompany.sportgest.domains.Event;
import studentcompany.sportgest.domains.EventCategory;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameMode_Event_Fragment_List extends Fragment {


    private static final String TAG = "GAMEMODE_EVENT_FRAGMENT";
    private List<EventCategory> list;
    private int tag = 0;
    OnItemSelected mListener;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


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
        View v = inflater.inflate(R.layout.role_fragment_list, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.role_recyclerView);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new GridLayoutManager(getContext(),3);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new GameMode_Event_List_Adapter(list, mListener, tag);
        mRecyclerView.setAdapter(mAdapter);

        return v;

    }

    public void setList(List<EventCategory> list){
        this.list = list;
    }

    public void setTag(int tag){
        this.tag = tag;
    }

    public void updateList(List<EventCategory> list){
        this.list = list;

        mAdapter = new GameMode_Event_List_Adapter(list, mListener, tag);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void updatePosition(EventCategory event, int position){
        this.list.set(position, event);
        mAdapter.notifyItemChanged(position);
    }

    public void unselect_Item(int position){

        GameMode_Event_List_Adapter.ViewHolder v = (GameMode_Event_List_Adapter.ViewHolder) mRecyclerView.findViewHolderForAdapterPosition(position);
        v.focus_loss();
        ((GameMode_Event_List_Adapter) mAdapter).unselectItem();
    }
    public void unselect_Item(){
        int pos = has_Selection();
        unselect_Item(pos);
    }
    public int has_Selection(){
        return ((GameMode_Event_List_Adapter) mAdapter).getCurrentPos();
    }

    public void removeItem(int position){
        mAdapter.notifyItemRemoved(position);
    }

    /************************************
     ****     Listener Functions     ****
     ************************************/

    // Container Activity must implement this interface
    public interface OnItemSelected{
        void itemSelected(int position, int tag);
    }

    public EventCategory getCurrentItem(){
        if(mAdapter == null)
            return null;
        else
            return ((GameMode_Event_List_Adapter) mAdapter).getCurrentItem();
    }

}
