package studentcompany.sportgest.EventCategories;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import studentcompany.sportgest.R;
import studentcompany.sportgest.domains.EventCategory;
import studentcompany.sportgest.domains.Player;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsEventCategory_Fragment extends Fragment {

    private static final String TAG = "DETAILS_EVENT_CATEGORY_FRAGMENT";
    private TextView tv_category;

    public DetailsEventCategory_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        LayoutInflater lf = getActivity().getLayoutInflater();

        View view =  lf.inflate(R.layout.fragment_eventCategory_details, container, false);
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
