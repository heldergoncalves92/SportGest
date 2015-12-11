package studentcompany.sportgest.Games;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import studentcompany.sportgest.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameTest_Fragment extends Fragment {

    private static final String ARG_POSITION = "position";

    TextView textView;
    private int position;


    public static GameTest_Fragment newInstance(int position) {
        GameTest_Fragment f = new GameTest_Fragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(ARG_POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_game_test, container, false);

        textView = (TextView)v.findViewById(R.id.text_view);
        textView.setText("CARD "+position);
        return v;
    }

}
