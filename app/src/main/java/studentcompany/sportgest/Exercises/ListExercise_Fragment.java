/*
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

public class ListExercise_Fragment extends Fragment {

    private static final String TAG = "LIST_EXERCISE_FRAGMENT";
    private List<Exercise> list;
    private TextView textview;
    private int position;


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        mAdapter = new Exercises_Adapter(list);
        mRecyclerView.setAdapter(mAdapter);

        View title = v.findViewById(R.id.exercise_item);
        TextView tv = (TextView)title.findViewById(R.id.text_view);
        tv.setTypeface(null, Typeface.BOLD_ITALIC);
        tv.setTextColor(Color.BLACK);

        //textView = (TextView)v.findViewById(R.id.text_view);
        //textView.setText("CARD "+position);
        return v;
    }
}


*/

package studentcompany.sportgest.Exercises;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import studentcompany.sportgest.R;

public class ListExercise_Fragment extends ListFragment {

    private static final String TAG = "LIST_EXERCISE_FRAGMENT";
    private List<String> list;
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
    public void onActivityCreated(Bundle savedState){
        Log.i(TAG, getClass().getSimpleName() + ":entered onActivityCreated()");
        super.onActivityCreated(savedState);

        // Set the list adapter for the ListView
        if(list != null)
            setListAdapter(new ArrayAdapter<>(getActivity(), R.layout.fragment_exercise_list, list));

        // Set the list choice mode to allow only one selection at a time
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    public void setExerciseList(List<String> list){
        this.list = list;
    }

    public void removeItem(int position){
        list.remove(position);
        getListView().invalidateViews();
    }

    public void updateList(){
        // Set the list adapter for the ListView
        if(list != null)
            setListAdapter(new ArrayAdapter<>(getActivity(), R.layout.fragment_exercise_list, list));
    }

    // Container Activity must implement this interface
    public interface OnItemSelected{
        void itemSelected(int position);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onListItemClick()");
        getListView().setItemChecked(position, true);

        mListener.itemSelected(position);
    }
}
