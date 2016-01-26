package studentcompany.sportgest.Attributes;

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
import studentcompany.sportgest.domains.Attribute;

public class ListAttribute_Fragment extends Fragment {

    private static final String TAG = "LIST_ATTRIBUTE_FRAGMENT";
    private List<Attribute> list;

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
        mAdapter = new Attributes_Adapter(list, getContext(), mListener);
        mRecyclerView.setAdapter(mAdapter);

        View title = v.findViewById(R.id.exercise_item);
        TextView tv = (TextView)title.findViewById(R.id.text_view);
        tv.setTypeface(null, Typeface.BOLD_ITALIC);
        tv.setTextColor(Color.BLACK);

        //textView = (TextView)v.findViewById(R.id.text_view);
        //textView.setText("CARD "+position);
        return v;
    }

    public void setAttributeList(List<Attribute> list){
        this.list = list;
    }

    public void removeItem(int position){
        list.remove(position);
        mAdapter = new Attributes_Adapter(list, getContext(), mListener);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void updateList(){
        // Set the list adapter for the ListView
        if(list != null) {
            mAdapter = new Attributes_Adapter(list, getContext(), mListener);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    /************************************
     ****     Listener Functions     ****
     ************************************/

    // Container Activity must implement this interface
    public interface OnItemSelected{
        void itemSelected(int position);
    }
}
