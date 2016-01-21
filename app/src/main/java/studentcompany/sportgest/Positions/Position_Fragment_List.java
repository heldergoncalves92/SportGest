package studentcompany.sportgest.Positions;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import studentcompany.sportgest.R;
import studentcompany.sportgest.domains.Position;


public class Position_Fragment_List extends Fragment {


    private static final String TAG = "LIST_POSITION_FRAGMENT";
    private List<Position> list;
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
        mAdapter = new Position_List_Adapter(list, mListener);
        mRecyclerView.setAdapter(mAdapter);

        return v;

    }

    public void setList(List<Position> list){
        this.list = list;
    }

    public void updateList(List<Position> list){
        this.list = list;

        mAdapter = new Position_List_Adapter(list, mListener);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void updatePosition(Position positionObj, int position){
        this.list.set(position, positionObj);
        mAdapter.notifyItemChanged(position);
    }



    public void removeItem(int position){
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
