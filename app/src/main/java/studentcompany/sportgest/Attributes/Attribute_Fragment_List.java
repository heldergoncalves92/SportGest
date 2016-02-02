package studentcompany.sportgest.Attributes;

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
import studentcompany.sportgest.domains.Attribute;

public class Attribute_Fragment_List extends Fragment {

    private static final String TAG = "LIST_ATTRIBUTE_FRAGMENT";
    private List<Attribute> list;
    private int tag = 0;

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
        View v = inflater.inflate(R.layout.fragment_exercise_list, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.exercise_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new Attributes_Adapter(list, mListener, tag);
        mRecyclerView.setAdapter(mAdapter);

        return v;
    }
    public void setList(List<Attribute> list){
        this.list = list;
    }

    public void setTag(int tag){
        this.tag = tag;
    }

    public void updateList(List<Attribute> list){
        this.list = list;

        mAdapter = new Attributes_Adapter(list, mListener, tag);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void updatePosition(Attribute attribute, int position){
        this.list.set(position, attribute);
        mAdapter.notifyItemChanged(position);
    }

    public void insert_Item(Attribute attribute){
        this.list.add(attribute);
        mAdapter.notifyItemInserted(list.size() - 1);
    }

    public void selectFirstItem(){

        Attributes_Adapter.ViewHolder v = (Attributes_Adapter.ViewHolder) mRecyclerView.findViewHolderForAdapterPosition(0);
        v.focus_gain();
    }

    public void unselect_Item(int position){

        Attributes_Adapter.ViewHolder v = (Attributes_Adapter.ViewHolder) mRecyclerView.findViewHolderForAdapterPosition(position);
        v.focus_loss();
    }

    public void select_Item(int position){

        Attributes_Adapter.ViewHolder v = (Attributes_Adapter.ViewHolder) mRecyclerView.findViewHolderForAdapterPosition(position);
        v.focus_gain();
    }


    public Attribute removeItem(int position){
        Attribute a = list.remove(position);
        mAdapter.notifyItemRemoved(position);

        return a;
    }

    public int has_Selection(){
        return ((Attributes_Adapter) mAdapter).getCurrentPos();
    }

    /************************************
     ****     Listener Functions     ****
     ************************************/

    // Container Activity must implement this interface
    public interface OnItemSelected{
        void itemSelected(int position, int tag);
    }
}
