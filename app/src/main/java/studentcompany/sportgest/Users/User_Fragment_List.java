package studentcompany.sportgest.Users;


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
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

import studentcompany.sportgest.R;
import studentcompany.sportgest.domains.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class User_Fragment_List extends Fragment {


    private static final String TAG = "LIST_USER_FRAGMENT";
    private List<User> list;
    private int tag=0;
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
        mAdapter = new User_List_Adapter(list, mListener, tag);
        mRecyclerView.setAdapter(mAdapter);

        return v;
    }

    public void setList(List<User> list){
        this.list = list;
    }

    public void setTag(int tag){
        this.tag = tag;
    }


    public void updateList(User user){
        this.list.add(user);
        mAdapter.notifyItemInserted(this.list.size() - 1);

        //mAdapter = new User_List_Adapter(list, mListener);
        //mRecyclerView.setAdapter(mAdapter);
    }

    public void updatePosition(User user, int position){
        this.list.set(position, user);
        mAdapter.notifyItemChanged(position);
    }

    public void selectFirstItem(){

        User_List_Adapter.ViewHolder v = (User_List_Adapter.ViewHolder) mRecyclerView.findViewHolderForAdapterPosition(0);
        v.focus_gain();
    }

    public void unselect_Item(int position){

        User_List_Adapter.ViewHolder v = (User_List_Adapter.ViewHolder) mRecyclerView.findViewHolderForAdapterPosition(position);
        v.focus_loss();
    }

    public void select_Item(int position){

        User_List_Adapter.ViewHolder v = (User_List_Adapter.ViewHolder) mRecyclerView.findViewHolderForAdapterPosition(position);
        v.focus_gain();
    }


    public User removeItem(int position){
        User u = list.remove(position);
        mAdapter.notifyItemRemoved(position);

        return u;
    }

    public int has_Selection(){
        return ((User_List_Adapter) mAdapter).getCurrentPos();
    }

    /************************************
     ****     Listener Functions     ****
     ************************************/

    // Container Activity must implement this interface
    public interface OnItemSelected{
        void itemSelected(int position, int tag);
    }


}
