package studentcompany.sportgest.Trainings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Exercise_DAO;
import studentcompany.sportgest.daos.Training_DAO;
import studentcompany.sportgest.daos.Training_Exercise_DAO;
import studentcompany.sportgest.domains.Exercise;
import studentcompany.sportgest.domains.Training;

public class CreateTrainingActivity extends AppCompatActivity {

    private static final String TAG = "CREATE_TRAINING_ACTIVITY";
    private TextView tv_name, tv_duration, tv_description;
    private ListView lv_availableExercises, lv_trainingExercise;

    //DAOs
    private Training_DAO training_dao;
    private Exercise_DAO exercise_dao;
    private Training_Exercise_DAO training_exercise_dao;

    //Id of current exercise displayed
    private Exercise exercise;
    private ArrayList<Training> availableExercises;
    private ArrayList<Training> trainingExercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_training);
    }
}