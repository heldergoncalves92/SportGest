package studentcompany.sportgest.Trainings;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Attribute_Exercise_DAO;
import studentcompany.sportgest.daos.Exercise_DAO;
import studentcompany.sportgest.daos.Team_DAO;
import studentcompany.sportgest.daos.Training_DAO;
import studentcompany.sportgest.daos.Training_Exercise_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Training;
import studentcompany.sportgest.domains.TrainingExercise;

public class CreateTrainingActivity extends AppCompatActivity {

    private TextInputLayout inputLayoutName;

    private static final String TAG = "CREATE_TRAINING_ACTIVITY";
    private TextView tv_name, tv_description;
    private EditText et_date, et_time;
    private Button next_button;

    // /DAOs
    private Team_DAO team_dao;
    private Training_DAO training_dao;
    private Exercise_DAO exercise_dao;
    private Attribute_Exercise_DAO attribute_exercise_dao;
    private Training_Exercise_DAO training_exercise_dao;

    //ID of current training displayed
    private Training training;

    private long trainingID;
    private String trainingName;
    private String trainingDescription;
    private long trainingDateInMillis;

    private Calendar cal;
    public static final java.text.DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd");
    public static final java.text.DateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_training);

        //initialize required DAOs
        team_dao = new Team_DAO(this);
        training_dao = new Training_DAO(this);
        exercise_dao = new Exercise_DAO(this);
        attribute_exercise_dao = new Attribute_Exercise_DAO(this);
        training_exercise_dao = new Training_Exercise_DAO(this);

        if(training_dao==null || exercise_dao==null || attribute_exercise_dao==null || training_exercise_dao==null){
            System.err.println(CreateTrainingActivity.class.getName() + " [ERROR] DAOs not created");
            return;
        }

        //initialize layouts
        inputLayoutName = (TextInputLayout) findViewById(R.id.layout_training_name);

        //initialize views
        tv_name = (TextView) findViewById(R.id.training_name);
        et_date= (EditText) findViewById(R.id.pick_date);
        et_time = (EditText) findViewById(R.id.pick_time);
        tv_description = (TextView) findViewById(R.id.training_description);
        next_button = (Button) findViewById(R.id.next_button);

        //initialize variables
        training = null;
        cal=Calendar.getInstance();

        tv_name.setText("");
        tv_name.setFocusable(true);
        tv_name.setClickable(true);
        tv_description.setText("");
        tv_description.setFocusable(true);
        tv_description.setClickable(true);
        et_date.setText(DATE_FORMAT.format(cal.getTime()));
        et_date.setFocusable(false);
        et_date.setClickable(true);
        et_time.setText(TIME_FORMAT.format(cal.getTime()));
        et_time.setFocusable(false);
        et_time.setClickable(true);
        et_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
        et_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(tv_name.getText().toString().isEmpty()){
                    inputLayoutName.setError("Field Name Required");
                } else {
                    goto_NextActivity(v);
                }
            }
        });

        //if update -> get object id
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            //get Exercise ID
            trainingID = extras.getLong(Training_DAO.TABLE_NAME + Training_DAO.COLUMN_ID);

            //validation
            if(trainingID > 0){
                //get the exercise information
                try {
                    training = training_dao.getById(trainingID);
                } catch (GenericDAOException ex){
                    System.err.println(CreateTrainingActivity.class.getName() + " [WARNING] " + ex.toString());
                    Logger.getLogger(CreateTrainingActivity.class.getName()).log(Level.WARNING, null, ex);
                }

                //validation
                if(training != null) {
                    //set layout variables with information
                    tv_name.setText(training.getTitle());
                    tv_description.setText(training.getDescription());
                    Date training_date = new Date(training.getDate());
                    cal.setTime(training_date);
                    et_date.setText(DATE_FORMAT.format(cal.getTime()));
                    et_time.setText(TIME_FORMAT.format(cal.getTime()));
                }//if(training != null)

            }//if(trainingID > 0)

        }//if(extras != null)
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar_crud, menu);
        //if its for updating an entry -> Save & Delete
        //for inserting a new entry -> Save
        if(training == null) {
            menu.findItem(R.id.Delete).setVisible(false);
        }
        menu.findItem(R.id.Add).setVisible(false);
        menu.findItem(R.id.Edit).setVisible(false);
        menu.findItem(R.id.Save).setVisible(false);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        boolean result = false;

        switch(item.getItemId())
        {
            //add action
            case R.id.Save:
                trainingName = tv_name.getText().toString();
                trainingDescription = tv_description.getText().toString();
                trainingDateInMillis = cal.getTimeInMillis();

                // validate inputs
                if(training == null || trainingName.isEmpty()){
                    return false;
                }

                //insert/update database
                if(training != null && training.getId() > 0) {
                    training.setTitle(trainingName);
                    training.setDescription(trainingDescription);
                    training.setDate(trainingDateInMillis);
                    try {
                        result = training_dao.update(training);
                    }catch (GenericDAOException ex){
                        System.err.println(CreateTrainingActivity.class.getName() + " [WARNING] " + ex.toString());
                        Logger.getLogger(CreateTrainingActivity.class.getName()).log(Level.WARNING, null, ex);
                    }

                    if(result){
                        Toast.makeText(getApplicationContext(), R.string.updated, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.not_updated, Toast.LENGTH_SHORT).show();
                    }
                }

                finish();

                return true;

            case R.id.Delete:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.delete_confirmation)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                try {
                                    ArrayList<TrainingExercise> auxTE =(ArrayList<TrainingExercise>) training_exercise_dao.getByCriteria(new TrainingExercise(-1, training, null, -1));
                                    for(TrainingExercise te : auxTE){
                                        training_exercise_dao.delete(te);
                                    }
                                    training_dao.delete(training);
                                } catch (GenericDAOException ex){
                                    ex.printStackTrace();
                                }
                                Toast.makeText(getApplicationContext(), R.string.delete_sucessful, Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                AlertDialog d = builder.create();
                d.setTitle(R.string.are_you_sure);
                d.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            EditText et_date = (EditText) getActivity().findViewById(R.id.pick_date);
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.DAY_OF_MONTH, day);
            et_date.setText(CreateTrainingActivity.DATE_FORMAT.format(cal.getTime()));
        }
    }

    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            EditText et_time = (EditText) getActivity().findViewById(R.id.pick_time);
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
            cal.set(Calendar.MINUTE, minute);
            et_time.setText(CreateTrainingActivity.TIME_FORMAT.format(cal.getTime()));
        }
    }

    public void goto_NextActivity(View view){
        trainingName = tv_name.getText().toString();
        trainingDescription = tv_description.getText().toString();
        trainingDateInMillis = cal.getTimeInMillis();

        Intent intent;
        intent = new Intent(this, CreateTrainingExerciseActivity.class);
        //put current training info in extras
        Bundle dataBundle = new Bundle();
        dataBundle.putLong(Training_DAO.TABLE_NAME + Training_DAO.COLUMN_ID, trainingID);
        dataBundle.putString(Training_DAO.TABLE_NAME + Training_DAO.COLUMN_TITLE, trainingName);
        dataBundle.putString(Training_DAO.TABLE_NAME + Training_DAO.COLUMN_DESCRIPTION, trainingDescription);
        dataBundle.putLong(Training_DAO.TABLE_NAME + Training_DAO.COLUMN_DATE, trainingDateInMillis);

        //add data
        intent.putExtras(dataBundle);
        //start activity
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            finish();
        }
    }
}