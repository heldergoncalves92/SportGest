package studentcompany.sportgest.Attributes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import studentcompany.sportgest.R;
import studentcompany.sportgest.domains.Attribute;

public class DetailsAttribute_Fragment extends Fragment {

    private static final String TAG = "DETAILS_ATTRIBUTE_FRAGMENT";
    private TextView tv_name, tv_type;

    public DetailsAttribute_Fragment() {
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

        return view;
    }

    public void showAttribute(Attribute attribute){
        tv_name.setText(attribute.getName());
        tv_type.setText(attribute.getType());
    }

    public void clearDetails(){
        tv_name.setText("");
        tv_type.setText("");
    }
}