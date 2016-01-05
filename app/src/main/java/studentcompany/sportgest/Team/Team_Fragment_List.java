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
        mAdapter = new Team_List_Adapter(list, mListener);
        mRecyclerView.setAdapter(mAdapter);

        return v;
    }

    public void setList(List<Team> list){
        this.list = list;
    }

    public void insert_Item(Team team){
        this.list.add(team);
        mAdapter.notifyItemInserted(this.list.size() - 1);

        //mAdapter = new User_List_Adapter(list, mListener);
        //mRecyclerView.setAdapter(mAdapter);
    }

    public void updatePosition(Team team, int position){
        this.list.set(position, team);
        mAdapter.notifyItemChanged(position);
    }


    public void removeItem(int position){
        this.list.remove(position);
        mAdapter.notifyItemRemoved(position);
    }


    /************************************
     ****     Listener Functions     ****
     ************************************/

    // Container Activity must implement this interface
    public interface OnItemSelected{
        void itemSelected(int position);
    }

}
