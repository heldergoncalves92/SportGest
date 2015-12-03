package studentcompany.sportgest.Exercises;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import studentcompany.sportgest.R;
import studentcompany.sportgest.domains.Exercise;

public class DetailsExercise_Fragment extends Fragment {

    private static final String TAG = "DETAILS_EXERCISE_FRAGMENT";
    private TextView tv_name, tv_description, tv_duration;
    private ImageView iv_image;

    public DetailsExercise_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        LayoutInflater lf = getActivity().getLayoutInflater();

        View view =  lf.inflate(R.layout.fragment_exercise_details, container, false);
        tv_name = (TextView) view.findViewById(R.id.exercise_name);
        tv_description = (TextView) view.findViewById(R.id.exercise_description);
        tv_duration = (TextView) view.findViewById(R.id.exercise_duration);

        return view;
    }

    public void showExercise(Exercise exercise){
        tv_name.setText(exercise.getTitle());
        tv_description.setText(exercise.getDescription());
        tv_duration.setText(""+exercise.getDuration());
    }

    public void clearDetails(){
        tv_name.setText("");
        tv_description.setText("");
        tv_duration.setText("");
    }
}