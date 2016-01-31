package studentcompany.sportgest.EventCategories;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import studentcompany.sportgest.R;
import studentcompany.sportgest.domains.EventCategory;


public class EventCategory_Fragment_Details extends Fragment {

    private static final String TAG = "DETAILS_EVENTCATEGORY_FRAGMENT";
    private EditText tv_category;
    private Button changecolor;
    private CheckBox checkBox_hastimestamp;

    public EventCategory_Fragment_Details() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        LayoutInflater lf = getActivity().getLayoutInflater();

        View view =  lf.inflate(R.layout.event_category_fragment_details, container, false);
        tv_category = (EditText) view.findViewById(R.id.category);

        checkBox_hastimestamp = (CheckBox) view.findViewById(R.id.checkbox_event_details_hasTime);

        changecolor = (Button) view.findViewById(R.id.btnChangeColorEventCatDetails);

        return view;
    }

    public void showEventCategory(EventCategory category){
        if(category.getName()!=null)
            tv_category.setText(category.getName());
        else
            tv_category.setText("");

        changecolor.setBackgroundColor(category.getColor());
        changecolor.setTextColor(category.getColor());

        checkBox_hastimestamp.setChecked(category.hasTimestamp());
    }

    public void clearDetails(){

        tv_category.setText("");
    }
}
