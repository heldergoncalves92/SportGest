package studentcompany.sportgest.Evaluation;

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
import java.util.Collections;
import java.util.List;

import studentcompany.sportgest.R;
import studentcompany.sportgest.domains.Attribute;
import studentcompany.sportgest.domains.Exercise;
import studentcompany.sportgest.domains.Record;

public class ExerciseAttributes_Fragment extends Fragment {

    private static final String TAG = "EXERCISE_ATTRIBUTES_FRAGMENT";
    private TextView tv_name, tv_duration;
    private ListView lv_exerciseAttributes;

    public ExerciseAttributes_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        LayoutInflater lf = getActivity().getLayoutInflater();

        View view =  lf.inflate(R.layout.fragment_exercise_attributes_evaluation, container, false);
        tv_name = (TextView) view.findViewById(R.id.exercise_name);
        tv_duration = (TextView) view.findViewById(R.id.exercise_duration);
        lv_exerciseAttributes = (ListView) view.findViewById(R.id.selected_attributes_list);

        return view;
    }

    public List<String> getAttributesNamesList(List<Attribute> attributeList){
        ArrayList<String> list = new ArrayList<>();

        for(Attribute a: attributeList)
            list.add(a.getName());
        Collections.sort(list);
        return list;
    }

    public void showExercise(Exercise exercise, List<Attribute> exerciseAttributes, List<Record> evaluations){
        clearDetails();
        tv_name.setText(exercise.getTitle());
        tv_name.setFocusable(false);
        tv_name.setClickable(false);
        tv_duration.setText("" + exercise.getDuration());
        tv_duration.setFocusable(false);
        tv_duration.setClickable(false);

        //TODO: create table with relations from players to attributes and his evaluation -> TableLayout
        if(exerciseAttributes != null) {
            List<String> exerciseAttributesNames = getAttributesNamesList(exerciseAttributes);
            ArrayAdapter arrayAdapter = new ArrayAdapter(this.getContext(), R.layout.listview_style1, exerciseAttributes);

            //set list in layout ListView
            lv_exerciseAttributes.setAdapter(arrayAdapter);

            //in case the user wants to see details about some attribute, start a display Attribute Activity
            lv_exerciseAttributes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
            lv_exerciseAttributes.setAdapter(new ArrayAdapter(this.getContext(), R.layout.listview_style1, new ArrayList<String>()));
            System.err.println("[ERROR] Exercise attributes not found!!!");
        }
    }

    public void clearDetails(){
        tv_name.setText("");
        tv_duration.setText("");
        lv_exerciseAttributes.setAdapter(new ArrayAdapter(this.getContext(), R.layout.listview_style1, new ArrayList<String>()));
    }
}