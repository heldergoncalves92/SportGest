package studentcompany.sportgest.Trainings;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import studentcompany.sportgest.R;
import studentcompany.sportgest.domains.Training;

public class DetailsTraining_Fragment extends Fragment {

    private static final String TAG = "DETAILS_TRAINING_FRAGMENT";
    private TextView tv_name, tv_date, tv_description, tv_duration, tv_team;
    //private ImageView iv_image;//???

    public DetailsTraining_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        LayoutInflater lf = getActivity().getLayoutInflater();

        View view =  lf.inflate(R.layout.fragment_training_details, container, false);
        tv_name = (TextView) view.findViewById(R.id.training_name);
        tv_date = (TextView) view.findViewById(R.id.training_date);
        tv_description = (TextView) view.findViewById(R.id.training_description);
        tv_duration = (TextView) view.findViewById(R.id.training_total_duration);
        tv_team = (TextView) view.findViewById(R.id.training_team);

        return view;
    }

    public void showTraining(Training training){
        tv_name.setText(training.getTitle());
        tv_date.setText(""+training.getDate());
        tv_description.setText(training.getDescription());
        tv_duration.setText(""+training.getTotalDuration());
        tv_team.setText(training.getTeam().getName());
    }

    public void clearDetails(){
        tv_name.setText("");
        tv_date.setText("");
        tv_description.setText("");
        tv_duration.setText("");
        tv_team.setText("");
    }
}