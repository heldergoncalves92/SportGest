package studentcompany.sportgest.Evaluation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import studentcompany.sportgest.R;

public class PlayerAttributesQualitativeEvaluation_Fragment extends Fragment {

    private static final String TAG = "PLAYER_ATTRIBUTES_QUALITATIVE_EVALUATION_FRAGMENT";
    private View view;
    private Spinner spinner;

    public PlayerAttributesQualitativeEvaluation_Fragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        LayoutInflater lf = getActivity().getLayoutInflater();

        view =  lf.inflate(R.layout.fragment_player_attributes_evaluation_qualitative, container, false);
        spinner = (Spinner) view.findViewById(R.id.spinner);
        return view;
    }

    public void showEvaluations(List<String> evaluationList){
        clearDetails();

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, evaluationList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void clearDetails(){
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, new ArrayList<String>());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public String getValue(){
        return spinner.getSelectedItem().toString();
    }
}