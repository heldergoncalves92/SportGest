package studentcompany.sportgest.Players;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import studentcompany.sportgest.R;
import studentcompany.sportgest.domains.Player;

/**
 * A simple {@link Fragment} subclass.
 */
public class Player_Fragment_List extends Fragment {


    private static final String TAG = "LIST_PLAYER_FRAGMENT";
    private List<Player> list;
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
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new Player_List_Adapter(list, mListener, tag);
        mRecyclerView.setAdapter(mAdapter);

        return v;

    }

    public void setList(List<Player> list){
        this.list = list;
    }

    public void setTag(int tag){
        this.tag = tag;
    }

    public void updateList(List<Player> list){
        this.list = list;

        mAdapter = new Player_List_Adapter(list, mListener, tag);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void updatePosition(Player player, int position){
        this.list.set(position, player);
        mAdapter.notifyItemChanged(position);
    }

    public void insert_Item(Player player){
        this.list.add(player);
        mAdapter.notifyItemInserted(list.size() - 1);
    }

    public void selectFirstItem(){

        Player_List_Adapter.ViewHolder v = (Player_List_Adapter.ViewHolder) mRecyclerView.findViewHolderForAdapterPosition(0);
        v.focus_gain();
    }

    public void unselect_Item(int position){

        Player_List_Adapter.ViewHolder v = (Player_List_Adapter.ViewHolder) mRecyclerView.findViewHolderForAdapterPosition(position);
        v.focus_loss();
    }


    public Player removeItem(int position){
        Player p = list.remove(position);
        mAdapter.notifyItemRemoved(position);

        return p;
    }

    public int has_Selection(){
        return ((Player_List_Adapter) mAdapter).getCurrentPos();
    }

    /************************************
     ****     Listener Functions     ****
     ************************************/

    // Container Activity must implement this interface
    public interface OnItemSelected{
        void itemSelected(int position, int tag);
    }

}
