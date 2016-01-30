package studentcompany.sportgest.Roles;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import studentcompany.sportgest.R;
import studentcompany.sportgest.domains.Role;

public class Role_Fragment_List extends Fragment {


    private static final String TAG = "LIST_USER_FRAGMENT";
    private List<Role> list;
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
        mAdapter = new Role_List_Adapter(list, mListener, tag);
        mRecyclerView.setAdapter(mAdapter);

        return v;

    }

    public void setTag(int tag){
        this.tag = tag;
    }

    public void setList(List<Role> list){
        this.list = list;
    }

    public void updateList(List<Role> list){
        this.list = list;

        mAdapter = new Role_List_Adapter(list, mListener, tag);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void updatePosition(Role role, int position){
        this.list.set(position, role);
        mAdapter.notifyItemChanged(position);
    }

    public void selectFirstItem(){

        Role_List_Adapter.ViewHolder v = (Role_List_Adapter.ViewHolder) mRecyclerView.findViewHolderForAdapterPosition(0);
        v.focus_gain();
    }

    public void unselect_Item(int position){

        Role_List_Adapter.ViewHolder v = (Role_List_Adapter.ViewHolder) mRecyclerView.findViewHolderForAdapterPosition(position);
        v.focus_loss();
    }

    public void select_Item(int position){

        Role_List_Adapter.ViewHolder v = (Role_List_Adapter.ViewHolder) mRecyclerView.findViewHolderForAdapterPosition(position);
        v.focus_gain();
    }

    public Role removeItem(int position){
        Role p = list.remove(position);
        mAdapter.notifyItemRemoved(position);

        return p;
    }

    public int has_Selection(){
        return ((Role_List_Adapter) mAdapter).getCurrentPos();
    }

    /************************************
     ****     Listener Functions     ****
     ************************************/

    // Container Activity must implement this interface
    public interface OnItemSelected{
        void itemSelected(int position, int tag);
    }

}
