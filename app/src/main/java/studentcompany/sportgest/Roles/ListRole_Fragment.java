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

public class ListRole_Fragment extends Fragment {


    private static final String TAG = "LIST_USER_FRAGMENT";
    private List<Role> list;
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
        mAdapter = new Role_List_Adapter(list);
        mRecyclerView.setAdapter(mAdapter);

        return v;

    }


    /*@Override
    public void onActivityCreated(Bundle savedState){
        Log.i(TAG, getClass().getSimpleName() + ":entered onActivityCreated()");
        super.onActivityCreated(savedState);

        // Set the list adapter for the ListView
        if(list != null)
            setListAdapter(new ArrayAdapter<User>(getActivity(), android.R.layout.simple_list_item_1, list));
        //setListAdapter(new ArrayAdapter<User>(getActivity(), R.layout.fragment_user_list, list));

        // Set the list choice mode to allow only one selection at a time
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }*/

    public void setList(List<Role> list){
        this.list = list;
        //try{getListView().invalidateViews();}
        //catch (Exception e){
        //}
    }

    public void removeItem(int position){
        list.remove(position);
        //getListView().invalidateViews();
    }

    /************************************
     ****     Listener Functions     ****
     ************************************/

    // Container Activity must implement this interface
    public interface OnItemSelected{
        void itemSelected(int position);
    }

    /*@Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onListItemClick()");
        getListView().setItemChecked(position, true);

        mListener.itemSelected(position);
    }*/
}
