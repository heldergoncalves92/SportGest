package studentcompany.sportgest.Exercises;

import android.graphics.Color;
import android.graphics.Typeface;
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
import android.widget.TextView;

import java.util.List;

import studentcompany.sportgest.R;
import studentcompany.sportgest.domains.Exercise;

public class Exercise_Fragment_List extends Fragment {

    private static final String TAG = "LIST_EXERCISE_FRAGMENT";
    private List<Exercise> list;
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

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new Exercises_Adapter(list, mListener, tag);
        mRecyclerView.setAdapter(mAdapter);

        return v;
    }

    public void setExerciseList(List<Exercise> list){
        this.list = list;
    }

    public void setTag(int tag){
        this.tag = tag;
    }

    public void updateList(){
        // Set the list adapter for the ListView
        if(list != null) {
            mAdapter = new Exercises_Adapter(list, mListener, tag);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    public void updateList(List<Exercise> list){
        this.list = list;

        mAdapter = new Exercises_Adapter(list, mListener, tag);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void updatePosition(Exercise exercise, int position){
        this.list.set(position, exercise);
        mAdapter.notifyItemChanged(position);
    }

    public void insert_Item(Exercise exercise){
        this.list.add(exercise);
        mAdapter.notifyItemInserted(list.size() - 1);
    }

    public void selectFirstItem(){

        Exercises_Adapter.ViewHolder v = (Exercises_Adapter.ViewHolder) mRecyclerView.findViewHolderForAdapterPosition(0);
        v.focus_gain();
    }

    public void unselect_Item(int position){

        Exercises_Adapter.ViewHolder v = (Exercises_Adapter.ViewHolder) mRecyclerView.findViewHolderForAdapterPosition(position);
        v.focus_loss();
    }

    public void select_Item(int position){

        Exercises_Adapter.ViewHolder v = (Exercises_Adapter.ViewHolder) mRecyclerView.findViewHolderForAdapterPosition(position);
        v.focus_gain();
    }

    public Exercise removeItem(int position){
        Exercise p = list.remove(position);
        mAdapter.notifyItemRemoved(position);

        return p;
    }

    public int has_Selection(){
        return ((Exercises_Adapter) mAdapter).getCurrentPos();
    }


    /************************************
     ****     Listener Functions     ****
     ************************************/

    // Container Activity must implement this interface
    public interface OnItemSelected{
        void itemSelected(int position, int tag);
    }
}