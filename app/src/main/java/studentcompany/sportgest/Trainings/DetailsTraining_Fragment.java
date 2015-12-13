package studentcompany.sportgest.Trainings;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import studentcompany.sportgest.R;
import studentcompany.sportgest.domains.Training;

public class DetailsTraining_Fragment extends Fragment {

    private static final String TAG = "DETAILS_TRAINING_FRAGMENT";
    private TextView tv_name, tv_date, tv_description, tv_duration;//, tv_team;
    private ListView lv_trainingExercises;

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
        tv_duration = (TextView) view.findViewById(R.id.training_duration);
        //tv_team = (TextView) view.findViewById(R.id.training_team);
        lv_trainingExercises = (ListView) view.findViewById(R.id.selected_exercises_list);

        return view;
    }

    public void showTraining(Training training, List<String> trainingExercises){
        clearDetails();
        tv_name.setText(training.getTitle());
        tv_name.setFocusable(false);
        tv_name.setClickable(false);
        tv_date.setText("" + training.getDate());
        tv_date.setFocusable(false);
        tv_date.setClickable(false);
        tv_description.setText(training.getDescription());
        tv_description.setFocusable(false);
        tv_description.setClickable(false);
        tv_duration.setText("" + training.getTotalDuration());
        tv_duration.setFocusable(false);
        tv_duration.setClickable(false);
//        tv_team.setText(training.getTeam().getName());
//        tv_team.setFocusable(false);
//        tv_team.setClickable(false);

        if(lv_trainingExercises != null) {
            ArrayAdapter arrayAdapter = new ArrayAdapter(this.getContext(), R.layout.listview_style1, trainingExercises);

            //set list in layout ListView
            lv_trainingExercises.setAdapter(arrayAdapter);

            //in case the user wants to see details about some attribute, start a display Attribute Activity
            lv_trainingExercises.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // TODO Auto-generated method stub -> implement edit attribute details
                    /*int id_To_Search = position + 1;
                    Bundle dataBundle = new Bundle();
                    dataBundle.putInt("id", id_To_Search);
                    Intent intent = new Intent(getApplicationContext(), DisplayEventCategoryActivity.class);
                    intent.putExtras(dataBundle);
                    startActivity(intent);*/
                }
            });
        } else {
            lv_trainingExercises.setAdapter(new ArrayAdapter(this.getContext(), R.layout.listview_style1, new ArrayList<String>()));
            System.err.println("[ERROR] Training exercises not found!!!");
        }
    }

    public void clearDetails(){
        tv_name.setText("");
        tv_date.setText("");
        tv_description.setText("");
        tv_duration.setText("");
        //tv_team.setText("");
        lv_trainingExercises.setAdapter(new ArrayAdapter(this.getContext(), R.layout.listview_style1, new ArrayList<String>()));
    }
}