package studentcompany.sportgest.Evaluation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import studentcompany.sportgest.R;

public class PlayerAttributesQuantitativeEvaluation_Fragment extends Fragment {

    private static final String TAG = "PLAYER_ATTRIBUTES_QUANTITATIVE_EVALUATION_FRAGMENT";
    private View view;
    private SeekBar seekBar;

    public PlayerAttributesQuantitativeEvaluation_Fragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        LayoutInflater lf = getActivity().getLayoutInflater();

        view =  lf.inflate(R.layout.fragment_player_attributes_evaluation_quantitative, container, false);
        seekBar = (SeekBar) view.findViewById(R.id.seekbar);
        return view;
    }

    public void showEvaluation(int value){
        clearDetails();

        if(value > seekBar.getMax()) {
            seekBar.setProgress(seekBar.getMax());
        } else if(value < 0){
            seekBar.setProgress(0);
        } else {
            seekBar.setProgress(value);
        }
    }

    public void clearDetails(){
        seekBar.setProgress(seekBar.getMax()/2);
    }

    public int getValue(){
        return seekBar.getProgress();
    }
}
