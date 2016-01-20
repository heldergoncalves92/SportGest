package studentcompany.sportgest.Evaluation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import studentcompany.sportgest.R;

public class PlayerAttributesRatioEvaluation_Fragment extends Fragment {

    private static final String TAG = "PLAYER_ATTRIBUTES_RATIO_EVALUATION_FRAGMENT";
    private View view;
    private EditText partial, total;

    public PlayerAttributesRatioEvaluation_Fragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        LayoutInflater lf = getActivity().getLayoutInflater();

        view =  lf.inflate(R.layout.fragment_player_attributes_evaluation_ratio, container, false);
        partial = (EditText) view.findViewById(R.id.partial_value);
        total = (EditText) view.findViewById(R.id.total_value);
        return view;
    }

    public void showEvaluation(float value){
        clearDetails();

        partial.setText(String.format("%.0f", value));
        total.setText("100");
    }

    public void clearDetails(){
        partial.setText("");
        total.setText("");
    }

    public float getValue(){
        float partialValue=0, totalValue=0;
        boolean error = false;

        try {
            partialValue = Float.parseFloat(partial.getText().toString());
        } catch (Exception ex){
            partial.setError("Partial value required");
            error = true;
        }

        try {
            totalValue = Float.parseFloat(total.getText().toString());
        } catch (Exception ex){
            total.setError("Total value required");
            error = true;
        }

        if (error){
            return -1;
        } else if(partialValue > totalValue){
            partial.setError("Partial value higher than total");
            return -1;
        }

        return partialValue/totalValue;
    }
}
