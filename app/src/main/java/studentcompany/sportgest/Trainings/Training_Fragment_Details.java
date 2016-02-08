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
import java.util.Calendar;
import java.util.List;

import studentcompany.sportgest.R;
import studentcompany.sportgest.domains.Training;

public class Training_Fragment_Details extends Fragment {

    private static final String TAG = "DETAILS_TRAINING_FRAGMENT";
    private TextView tv_name, tv_date, tv_description, tv_duration;//, tv_team;
    private ListView lv_trainingExercises;

    public Training_Fragment_Details() {
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

        tv_name.setText(training.getTitle());
        tv_name.setFocusable(false);
        tv_name.setClickable(false);
        tv_date.setText("" + getDate(training.getDate()));
        tv_date.setFocusable(false);
        tv_date.setClickable(false);
        tv_description.setText(training.getDescription());
        tv_description.setFocusable(false);
        tv_description.setClickable(false);
        tv_duration.setText("" + training.getTotalDuration());
        tv_duration.setFocusable(false);
        tv_duration.setClickable(false);


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

        View v = getView().findViewById(R.id.frame_details);
        v.setVisibility(View.GONE);

        v = getView().findViewById(R.id.no_Selection);
        v.setVisibility(View.VISIBLE);

        tv_name.setText("");
        tv_date.setText("");
        tv_description.setText("");
        tv_duration.setText("");
        //tv_team.setText("");
        lv_trainingExercises.setAdapter(new ArrayAdapter(this.getContext(), R.layout.listview_style1, new ArrayList<String>()));
    }

    private String getDate(long time){
        String res="";

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);

        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        mMonth++;


        if(mDay<10){
            res="0"+mDay;
        }
        else{
            res=""+mDay;
        }
        if (mMonth<10){
            res=res+"/0"+mMonth;
        }
        else{
            res=res+"/"+mMonth;
        }

        res=res+"/"+mYear;
        return res;
    }

    public void showFirstElem() {
        View v = getView().findViewById(R.id.frame_details);
        v.setVisibility(View.VISIBLE);

        v = getView().findViewById(R.id.no_Selection);
        v.setVisibility(View.GONE);
    }
}