package studentcompany.sportgest.Trainings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.DatePicker;
import android.widget.Toast;

import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Training_DAO;
import studentcompany.sportgest.domains.Training;

public class CreateTrainingActivity extends AppCompatActivity {

    private static final String TAG = "CREATE_TRAINING_ACTIVITY";
    private TextView tv_name, tv_duration;
    private EditText et_description;
    private DatePicker dpResult;
    private Button next_button;

    private Training training;
    private int year;
    private int month;
    private int day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_training);


        //Initialiaze Views and variables

        tv_name=(TextView) findViewById(R.id.training_name);
        tv_duration=(TextView) findViewById(R.id.training_duration);
        et_description = (EditText) findViewById(R.id.training_description);
        dpResult=(DatePicker) findViewById(R.id.training_date);

        training= new Training(-1,null,null,-1,-1,null);
        year=month=day=0;


        tv_name.setText("");
        tv_name.setFocusable(true);
        tv_name.setClickable(true);
        et_description.setText("");
        et_description.setFocusable(true);
        et_description.setClickable(true);
        tv_duration.setText("");
        tv_duration.setFocusable(true);
        tv_duration.setClickable(true);
        dpResult.setFocusable(true);
        dpResult.setClickable(true);

        addListenerOnButton();

    }

    public void addListenerOnButton() {

        next_button = (Button) findViewById(R.id.next_button);

        next_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (tv_name.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), R.string.error_field_required, Toast.LENGTH_SHORT).show();
                } else {
                        //acabar

                    training.setTitle(tv_name.getText().toString());
                }

            }

        });

    }
}