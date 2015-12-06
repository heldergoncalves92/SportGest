package studentcompany.sportgest.Users;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

import studentcompany.sportgest.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListUser_Fragment extends ListFragment {


    private static final String TAG = "LIST_USER_FRAGMENT";
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
            setListAdapter(new ArrayAdapter<String>(getActivity(), R.layout.fragment_user_list, list));


        // Set the list choice mode to allow only one selection at a time
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

    }

    public void setList(List<String> list){
        this.list = list;
    }

    public void removeItem(int position){
        list.remove(position);
        getListView().invalidateViews();
    }

    /************************************
     ****     Listener Functions     ****
     ************************************/

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
