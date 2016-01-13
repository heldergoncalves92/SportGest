package studentcompany.sportgest.Evaluation;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toolbar;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Pair;
import studentcompany.sportgest.domains.Attribute;
import studentcompany.sportgest.domains.Exercise;
import studentcompany.sportgest.domains.Player;
import studentcompany.sportgest.domains.Record;
import studentcompany.sportgest.domains.TrainingExercise;

public class ExerciseAttributes_Fragment extends Fragment {

    private static final String TAG = "EXERCISE_ATTRIBUTES_FRAGMENT";
    private View view;
    private TextView tv_name, tv_duration, tv_repetitions;
    private TableLayout globalTable, table;
    private HorizontalScrollView scrollableTable;

    public ExerciseAttributes_Fragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        LayoutInflater lf = getActivity().getLayoutInflater();

        view =  lf.inflate(R.layout.fragment_exercise_attributes_evaluation, container, false);
        tv_name = (TextView) view.findViewById(R.id.exercise_name);
        tv_duration = (TextView) view.findViewById(R.id.exercise_duration);
        tv_repetitions = (TextView) view.findViewById(R.id.exercise_repetitions);

        globalTable = new TableLayout(getActivity());
        globalTable.setStretchAllColumns(false);
        globalTable.setShrinkAllColumns(false);

        table = new TableLayout(getActivity());

        scrollableTable = new HorizontalScrollView(getActivity());

        return view;
    }

    public List<String> getAttributesNamesList(List<Attribute> attributeList){
        ArrayList<String> list = new ArrayList<>();

        for(Attribute a: attributeList)
            list.add(a.getName());
        Collections.sort(list);
        return list;
    }

    public void showExercise(Exercise exercise, TrainingExercise trainingExercise, List<Attribute> exerciseAttributes, List<Player> playerList, List<Record> evaluations){
        clearDetails();
        tv_name.setText(exercise.getTitle());
        tv_name.setFocusable(false);
        tv_name.setClickable(false);
        tv_duration.setText("" + exercise.getDuration());
        tv_duration.setFocusable(false);
        tv_duration.setClickable(false);
        tv_repetitions.setText("" + trainingExercise.getRepetitions());
        tv_repetitions.setFocusable(false);
        tv_repetitions.setClickable(false);

        //TODO: create globalTable with relations from players to attributes and his evaluation -> TableLayout
        FragmentActivity fa = getActivity();

        //TextViews
        TextView tv;
        TableRow tr;

        //Data structures
        int rowNumber = 1;
        int colNumber = 1;
        HashMap<Long, Integer> playerRow = new HashMap<>();
        HashMap<Long, Integer> attributeCol = new HashMap<>();
        HashMap<Pair<Integer, Integer>, TextView> evaluationTable = new HashMap<>();
        ArrayList<TableRow> tableRows = new ArrayList<>();

        //fill rows (player names)
        tv = new TextView(fa);
        tv.setText("");
        tv.setPadding(5, 5, 5, 5);
        tv.setMinimumWidth(10);
        tv.setBackgroundColor(Color.WHITE);
        evaluationTable.put(new Pair<>(0, 0), tv);
        tr = new TableRow(fa);
        tr.setPadding(0, 2, 0, 2); //Border between rows
        tr.setBackgroundColor(Color.WHITE);
        tr.setGravity(Gravity.CENTER_VERTICAL);
        tableRows.add(tr);
        for(Player p: playerList){
            playerRow.put(p.getId(), rowNumber);
            tv = new TextView(fa);
            tv.setText(p.getName());
            tv.setMinimumWidth(10);
            tv.setMaxWidth(250);
            tv.setBackgroundColor(Color.WHITE);
            tv.setPadding(5, 5, 5, 5);
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
            evaluationTable.put(new Pair<>(rowNumber, 0), tv);
            tr = new TableRow(fa);
            tr.setPadding(0, 2, 0, 2); //Border between rows
            if(rowNumber%2 == 0){
                tr.setBackgroundColor(Color.GRAY);
            } else {
                tr.setBackgroundColor(Color.DKGRAY);
            }
            tr.setGravity(Gravity.CENTER_VERTICAL);
            tableRows.add(tr);
            rowNumber++;
        }

        //fill columns (attribute names)
        for(Attribute a: exerciseAttributes){
            attributeCol.put(a.getId(), colNumber);
            tv = new TextView(fa);
            tv.setText(a.getName());
            tv.setMinimumWidth(10);
            tv.setMaxWidth(200);
            tv.setBackgroundColor(Color.WHITE);
            tv.setPadding(5, 5, 5, 5);
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
            evaluationTable.put(new Pair<>(0, colNumber), tv);
            colNumber++;
        }

        //fill content
        int auxRow, auxCol;
        for(Record r: evaluations){
            //TODO: validation
            auxRow = playerRow.get(r.getPlayer().getId());
            auxCol = attributeCol.get(r.getAttribute().getId());
            //TODO: print da nota consoante o tipo
            tv = new TextView(fa);
            tv.setMinimumWidth(10);
            tv.setMaxWidth(200);
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
            tv.setPadding(5, 5, 5, 5);
            tv.setText("" + r.getValue());
            //TODO: verify key does not exists
            evaluationTable.put(new Pair<>(auxRow, auxCol), tv);
        }

        //build Table View
        TableRow auxTR;
        for(auxRow=0; auxRow < rowNumber; auxRow++){
            auxTR = tableRows.get(auxRow);
            for(auxCol=0; auxCol<colNumber; auxCol++){
                if(evaluationTable.containsKey(new Pair<>(auxRow, auxCol))){
                    auxTR.addView(evaluationTable.get(new Pair<>(auxRow, auxCol)));
                } else {
                    tv = new TextView(fa);
                    tv.setText("N/A");
                    tv.setMinimumWidth(10);
                    tv.setMaxWidth(200);
                    tv.setGravity(Gravity.CENTER_HORIZONTAL);
                    tv.setPadding(5, 5, 5, 5);
                    auxTR.addView(tv);
                }
            }
            tableRows.set(auxRow, auxTR);
        }

        for(auxRow=0; auxRow < rowNumber; auxRow++) {
            globalTable.addView(tableRows.get(auxRow));
        }

        HorizontalScrollView til = (HorizontalScrollView) view.findViewById(R.id.text_layout_exercise_attributes);
        til.removeAllViews();
        til.addView(globalTable);
    }

    public void clearDetails(){
        tv_name.setText("");
        tv_duration.setText("");
        tv_repetitions.setText("");
        globalTable.removeAllViews();
    }
}