package studentcompany.sportgest.Team;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import studentcompany.sportgest.R;
import studentcompany.sportgest.domains.Team;


public class Team_Fragment_List extends Fragment {

    private static final String TAG = "LIST_TEAM_FRAGMENT";
    private List<Team> list;
    OnItemSelected mListener;
    private int tag = 0;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    public Team_Fragment_List() {
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
        View v = inflater.inflate(R.layout.role_fragment_list, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.role_recyclerView);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new Team_List_Adapter(list, mListener, tag);
        mRecyclerView.setAdapter(mAdapter);

        return v;
    }

    public void setTag(int tag){
        this.tag = tag;
    }

    public void setList(List<Team> list){
        this.list = list;
    }

    public void insert_Item(Team team){
        this.list.add(team);
        mAdapter.notifyItemInserted(this.list.size() - 1);
    }

    public void updateList(List<Team> list){
        this.list = list;
        mAdapter = new Team_List_Adapter(list, mListener, tag);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void updatePosition(Team team, int position){
        this.list.set(position, team);
        mAdapter.notifyItemChanged(position);

    }


    public Team removeItem(int position){
        Team t = list.remove(position);
        mAdapter.notifyItemRemoved(position);

        return t;
    }

    public void selectFirstItem(){

        Team_List_Adapter.ViewHolder v = (Team_List_Adapter.ViewHolder) mRecyclerView.findViewHolderForAdapterPosition(0);
        v.focus_gain();
    }

    public void unselect_Item(int position){

        Team_List_Adapter.ViewHolder v = (Team_List_Adapter.ViewHolder) mRecyclerView.findViewHolderForAdapterPosition(position);
        v.focus_loss();
    }

    public void select_Item(int position){

        Team_List_Adapter.ViewHolder v = (Team_List_Adapter.ViewHolder) mRecyclerView.findViewHolderForAdapterPosition(position);
        v.focus_gain();
    }


    public int has_Selection(){
        return ((Team_List_Adapter) mAdapter).getCurrentPos();
    }


    /************************************
     ****     Listener Functions     ****
     ************************************/

    // Container Activity must implement this interface
    public interface OnItemSelected{
        void itemSelected(int position, int tag);
    }

}
