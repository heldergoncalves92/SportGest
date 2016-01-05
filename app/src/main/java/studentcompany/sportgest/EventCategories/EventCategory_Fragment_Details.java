package studentcompany.sportgest.EventCategories;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import studentcompany.sportgest.R;
import studentcompany.sportgest.domains.EventCategory;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventCategory_Fragment_Details extends Fragment {

    private static final String TAG = "DETAILS_EVENT_CATEGORY_FRAGMENT";
    private TextView tv_category;

    public EventCategory_Fragment_Details() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        LayoutInflater lf = getActivity().getLayoutInflater();

        View view =  lf.inflate(R.layout.event_category_fragment_details, container, false);
        tv_category = (TextView) view.findViewById(R.id.category);

        return view;
    }

    public void showCategory(EventCategory category){
        if(category.getName()!=null)
            tv_category.setText(category.getName());
        else
            tv_category.setText("");
    }

    public void clearDetails(){

        tv_category.setText("");
    }
}
