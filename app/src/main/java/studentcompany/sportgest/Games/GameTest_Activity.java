package studentcompany.sportgest.Games;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Game_DAO;
import studentcompany.sportgest.domains.Game;
import studentcompany.sportgest.domains.Team;

public class GameTest_Activity extends AppCompatActivity {

    ImageView imageView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_test);


        textView = (TextView)findViewById(R.id.game_test_textview);

        imageView = (ImageView)findViewById(R.id.game_test_imageview);
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    textView.setText("Touch coordinates : " +
                            String.valueOf(event.getX()) + "x" + String.valueOf(event.getY()) + "\n" +
                            String.valueOf("dimensions:" + imageView.getWidth() + "x" + imageView.getHeight()));
                }
                return true;
            }
        });
        // specify an adapter (see also next example)


    }

    public void noElems(){

       /* LinearLayout l = (LinearLayout)findViewById(R.id.linear);
        l.setVisibility(View.GONE);

        TextView t= (TextView)findViewById(R.id.without_elems);
        t.setVisibility(View.VISIBLE);
        */
    }


}
