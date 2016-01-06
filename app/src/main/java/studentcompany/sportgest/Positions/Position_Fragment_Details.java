package studentcompany.sportgest.Positions;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import studentcompany.sportgest.R;
import studentcompany.sportgest.domains.Position;


public class Position_Fragment_Details extends Fragment {

    private static final String TAG = "DETAILS_POSITION_FRAGMENT";
    private EditText tv_position;

    public Position_Fragment_Details() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        LayoutInflater lf = getActivity().getLayoutInflater();

        View view =  lf.inflate(R.layout.position_fragment_details, container, false);
        tv_position = (EditText) view.findViewById(R.id.position);

        return view;
    }

    public void showPosition(Position position){
        if(position.getName()!=null)
            tv_position.setText(position.getName());
        else
            tv_position.setText("");
    }

    public void clearDetails(){

        tv_position.setText("");
    }
}
