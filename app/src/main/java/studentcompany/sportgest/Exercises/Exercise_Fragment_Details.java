package studentcompany.sportgest.Exercises;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Exercise;

public class Exercise_Fragment_Details extends Fragment {

    private static final String TAG = "DETAILS_EXERCISE_FRAGMENT";
    private TextView tv_name, tv_duration;
    private EditText et_description;
    private ImageView iv_image;
    private ListView lv_exerciseAttributes;

    public Exercise_Fragment_Details() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        LayoutInflater lf = getActivity().getLayoutInflater();

        View view =  lf.inflate(R.layout.fragment_exercise_details, container, false);
        tv_name = (TextView) view.findViewById(R.id.exercise_name);
        et_description = (EditText) view.findViewById(R.id.exercise_description);
        tv_duration = (TextView) view.findViewById(R.id.exercise_duration);
        lv_exerciseAttributes = (ListView) view.findViewById(R.id.selected_attributes_list);
        iv_image = (ImageView) view.findViewById(R.id.exercise_image);

        return view;
    }

    public void showExercise(Exercise exercise, List<String> exerciseAttributes){
        //clearDetails();
        tv_name.setText(exercise.getTitle());
        tv_name.setFocusable(false);
        tv_name.setClickable(false);
        et_description.setText(exercise.getDescription());
        et_description.setFocusable(false);
        et_description.setClickable(false);
        tv_duration.setText("" + exercise.getDuration());
        tv_duration.setFocusable(false);
        tv_duration.setClickable(false);
        iv_image.setImageResource(R.drawable.inf);

        if(exerciseAttributes != null) {
            ArrayAdapter arrayAdapter = new ArrayAdapter(this.getContext(), R.layout.listview_style1, exerciseAttributes);

            //set list in layout ListView
            lv_exerciseAttributes.setAdapter(arrayAdapter);

            //in case the user wants to see details about some attribute, start a display Attribute Activity
            lv_exerciseAttributes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                }
            });
        } else {
            lv_exerciseAttributes.setAdapter(new ArrayAdapter(this.getContext(), R.layout.listview_style1, new ArrayList<String>()));
            System.err.println("[ERROR] Exercise attributes not found!!!");
        }
    }

    public void clearDetails(){

        /*View v = getView().findViewById(R.id.frame_details);
        v.setVisibility(View.GONE);

        v = getView().findViewById(R.id.no_Selection);
        v.setVisibility(View.VISIBLE);*/

        tv_name.setText("");
        et_description.setText("");
        tv_duration.setText("");
        lv_exerciseAttributes.setAdapter(new ArrayAdapter(this.getContext(), R.layout.listview_style1, new ArrayList<String>()));
        iv_image.setImageResource(R.drawable.inf);
    }

    public void showFirstElem(){
        View v = getView().findViewById(R.id.frame_details);
        v.setVisibility(View.VISIBLE);

        v = getView().findViewById(R.id.no_Selection);
        v.setVisibility(View.GONE);
    }
}