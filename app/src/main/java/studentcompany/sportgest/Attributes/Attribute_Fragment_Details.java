package studentcompany.sportgest.Attributes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import studentcompany.sportgest.R;
import studentcompany.sportgest.domains.Attribute;

public class Attribute_Fragment_Details extends Fragment {

    private static final String TAG = "DETAILS_ATTRIBUTE_FRAGMENT";
    private TextView tv_name, tv_type;

    public Attribute_Fragment_Details() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        LayoutInflater lf = getActivity().getLayoutInflater();

        View view =  lf.inflate(R.layout.fragment_attribute_details, container, false);
        tv_name = (TextView) view.findViewById(R.id.attribute_name);
        tv_type = (TextView) view.findViewById(R.id.attribute_category);

        tv_name.setFocusable(false);
        tv_name.setClickable(false);
        tv_type.setFocusable(false);
        tv_type.setClickable(false);

        return view;
    }

    public void showAttribute(Attribute attribute){
        tv_name.setText(attribute.getName());
        tv_type.setText(attribute.getType());
    }

    public void clearDetails(){
        View v = getView().findViewById(R.id.frame_details);
        v.setVisibility(View.GONE);

        v = getView().findViewById(R.id.no_Selection);
        v.setVisibility(View.VISIBLE);

        tv_name.setText("");
        tv_type.setText("");
    }

    public void showFirstElem(){
        View v = getView().findViewById(R.id.frame_details);
        v.setVisibility(View.VISIBLE);

        v = getView().findViewById(R.id.no_Selection);
        v.setVisibility(View.GONE);
    }
}