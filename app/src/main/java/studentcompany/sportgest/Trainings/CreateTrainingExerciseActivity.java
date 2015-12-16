package studentcompany.sportgest.Trainings;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import studentcompany.sportgest.Exercises.DetailsExercise_Fragment;
import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Attribute_Exercise_DAO;
import studentcompany.sportgest.daos.Exercise_DAO;
import studentcompany.sportgest.daos.Team_DAO;
import studentcompany.sportgest.daos.Training_DAO;
import studentcompany.sportgest.daos.Training_Exercise_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Attribute;
import studentcompany.sportgest.domains.Exercise;
import studentcompany.sportgest.domains.Training;
import studentcompany.sportgest.domains.TrainingExercise;

public class CreateTrainingExerciseActivity extends AppCompatActivity {
    private DialogFragment mDialog;
    private FragmentManager mFragmentManager;
    private DetailsExercise_Fragment mDetailsExercise = new DetailsExercise_Fragment();

    private static final String TAG = "CREATE_TRAINING_ACTIVITY";
    private TextView tv_repetitions, tv_duration;
    private ListView lv_availableExercises, lv_trainingExercise;
    private ImageButton b_repMore, b_repLess;
    private Button b_next, b_return;

    //DAOs
    private Team_DAO team_dao;
    private Training_DAO training_dao;
    private Exercise_DAO exercise_dao;
    private Attribute_Exercise_DAO attribute_exercise_dao;
    private Training_Exercise_DAO training_exercise_dao;

    //Id of current training displayed
    //private Exercise exercise;
    private Training training;
    private TrainingExercise trainingExercise;
    private List<Exercise> exerciseList;
    private List<Attribute> exerciseAttributesList;
    private ArrayList<Exercise> availableExercises;
    private ArrayList<Exercise> trainingExercises;

    private long trainingID;
    private String trainingName;
    private String trainingDescription;
    private int trainingDuration;
    private long trainingDateInMilis;
    private int trainingExerciseRepetitions;
    private Menu mOptionsMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_training_exercises);

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

        //initialize views
        tv_repetitions = (TextView) findViewById(R.id.exercise_repetitions);
        b_repLess = (ImageButton) findViewById(R.id.selected_exercise_repetitions_less);
        b_repMore = (ImageButton) findViewById(R.id.selected_exercise_repetitions_more);
        tv_duration = (TextView) findViewById(R.id.training_total_duration);
        lv_availableExercises = (ListView) findViewById(R.id.training_available_exercises_list);
        lv_trainingExercise = (ListView) findViewById(R.id.training_selected_exercises_list);
        b_next = (Button) findViewById(R.id.next_button);
        b_return = (Button) findViewById(R.id.return_button);

        //initialize variables
        training = null;
        exerciseList = new ArrayList<>();
        exerciseAttributesList = new ArrayList<>();
        availableExercises = new ArrayList<>();
        trainingExercises = new ArrayList<>();

        tv_repetitions.setText("");
        tv_repetitions.setFocusable(true);
        tv_repetitions.setClickable(true);
        tv_duration.setText("");
        tv_duration.setFocusable(true);
        tv_duration.setClickable(true);
        b_repMore.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentRep = tv_repetitions.getText().toString();
                int repetitions = 0;
                if (!currentRep.isEmpty()) {
                    repetitions = Integer.parseInt(currentRep);
                    if (repetitions < 0) repetitions = 0;
                }
                tv_repetitions.setText(repetitions + 1);
            }
        });
        b_repLess.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v) {
                String currentRep = tv_repetitions.getText().toString();
                int repetitions = 0;
                if(!currentRep.isEmpty()){
                    repetitions = Integer.parseInt(currentRep);
                    if(repetitions < 0) repetitions = 0;
                }
                if(repetitions > 0) {
                    repetitions -=1;
                }
                tv_repetitions.setText(repetitions);
            }
        });
        lv_availableExercises.setFocusable(false);
        lv_trainingExercise.setFocusable(false);
        lv_availableExercises.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Add the attribute to the selected ones
                long id_To_Search = availableExercises.get(position).getId();
                try {
                    Exercise ex = exercise_dao.getById(id_To_Search);
                    if (ex != null) {
                        availableExercises.remove(ex);
                        trainingExercises.add(ex);

                        Collections.sort(trainingExercises, new Comparator<Exercise>() {
                            @Override
                            public int compare(Exercise lhs, Exercise rhs) {
                                return lhs.getTitle().compareTo(rhs.getTitle());
                            }
                        });

                        //update ListViews
                        lv_availableExercises.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, exerciseNamesFromList(availableExercises)));
                        lv_trainingExercise.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, exerciseNamesFromList(trainingExercises)));
                    }
                } catch (GenericDAOException ex) {
                    System.err.println(CreateTrainingActivity.class.getName() + " [WARNING] " + ex.toString());
                    Logger.getLogger(CreateTrainingActivity.class.getName()).log(Level.WARNING, null, ex);
                }
            }//onItemClick

        });//setOnItemClickListener
        lv_trainingExercise.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long id_To_Search = trainingExercises.get(position).getId();
                try {
                    Exercise ex = exercise_dao.getById(id_To_Search);
                    if(ex != null){
                        trainingExercises.remove(ex);
                        availableExercises.add(ex);

                        Collections.sort(availableExercises, new Comparator<Exercise>() {
                            @Override
                            public int compare(Exercise lhs, Exercise rhs) {
                                return lhs.getTitle().compareTo(rhs.getTitle());
                            }
                        });

                        //update ListViews
                        lv_availableExercises.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, exerciseNamesFromList(availableExercises)));
                        lv_trainingExercise.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, exerciseNamesFromList(trainingExercises)));
                    }
                } catch (GenericDAOException ex){
                    System.err.println(CreateTrainingActivity.class.getName() + " [WARNING] " + ex.toString());
                    Logger.getLogger(CreateTrainingActivity.class.getName()).log(Level.WARNING, null, ex);
                }
            }//onItemClick

        });//setOnItemClickListener
        b_next.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO new intent for training insertion summary
                /*
                if (currentView == R.layout.activity_create_training) {
                    setContentView(R.layout.activity_create_training_exercises);
                    currentView = R.layout.activity_create_training_exercises;
                    mOptionsMenu.findItem(R.id.Save).setVisible(true);
                }*/
            }
        });
        b_next.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                //TODO return to the previous activity
            }
        });

        //get a list of available exercises
        try {
            availableExercises = exercise_dao.getAll();
        } catch (GenericDAOException ex){
            System.err.println(CreateTrainingActivity.class.getName() + " [WARNING] " + ex.toString());
            Logger.getLogger(CreateTrainingActivity.class.getName()).log(Level.WARNING, null, ex);
        }

        //if update -> get object id
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            //get Exercise ID
            trainingID = extras.getLong(Training_DAO.TABLE_NAME + Training_DAO.COLUMN_ID);
            trainingName = extras.getString(Training_DAO.TABLE_NAME + Training_DAO.COLUMN_TITLE);
            trainingDescription = extras.getString(Training_DAO.TABLE_NAME + Training_DAO.COLUMN_DESCRIPTION);
            trainingDateInMilis = extras.getLong(Training_DAO.TABLE_NAME + Training_DAO.COLUMN_DATE);

            //validation
            if(trainingID > 0){
                //get the training information
                try {
                    training = training_dao.getById(trainingID);
                } catch (GenericDAOException ex){
                    System.err.println(CreateTrainingActivity.class.getName() + " [WARNING] " + ex.toString());
                    Logger.getLogger(CreateTrainingActivity.class.getName()).log(Level.WARNING, null, ex);
                }

                //validation
                if(training != null) {

                    //get list of exercises allocated to some trainingID
                    try {
                        ArrayList<TrainingExercise> auxTE = (ArrayList<TrainingExercise>) training_exercise_dao.getByCriteria(new TrainingExercise(-1, training, null, -1));
                        for(TrainingExercise te : auxTE){
                            if(te.getExercise()!=null) {
                                trainingExercises.add(te.getExercise());
                            }
                        }
                    } catch (GenericDAOException ex){
                        System.err.println(CreateTrainingActivity.class.getName() + " [WARNING] " + ex.toString());
                        Logger.getLogger(CreateTrainingActivity.class.getName()).log(Level.WARNING, null, ex);
                    }

                    //subtract from available already selected attributes
                    for(Exercise e: trainingExercises){
                        availableExercises.remove(e);
                    }

                    //set list in layout ListView
                    lv_trainingExercise.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, exerciseNamesFromList(trainingExercises)));

                }//if(training != null)

            }//if(trainingID > 0)

        }//if(extras != null)

        //set available attributes ListView
        lv_availableExercises.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, exerciseNamesFromList(availableExercises)));

        // Get a reference to the FragmentManager
        mFragmentManager = getSupportFragmentManager();

        // Start a new FragmentTransaction
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        // Add the TitleFragment to the layout
        fragmentTransaction.add(R.id.exercise_detail_fragment_container, mDetailsExercise);

        fragmentTransaction.commit();
    }

    private List<String> exerciseNamesFromList(List<Exercise> listAttr){
        ArrayList<String> res = new ArrayList<>();
        for(Exercise e : listAttr){
            res.add(e.getTitle());
        }
        Collections.sort(res);
        return res;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar_crud, menu);
        mOptionsMenu = menu;
        //if its for updating an entry -> Save & Delete
        //for inserting a new entry -> Save
        if(training == null) {
            menu.findItem(R.id.Delete).setVisible(false);
        }
        menu.findItem(R.id.Add).setVisible(false);
        menu.findItem(R.id.Edit).setVisible(false);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        boolean result = false;

        switch(item.getItemId())
        {
            //add action
            case R.id.Save:
                // validate inputs
                if(trainingName.isEmpty() || trainingDateInMilis <= 0){
                    return false;
                }
                trainingDuration = Integer.parseInt(tv_duration.getText().toString());
                trainingExerciseRepetitions = Integer.parseInt(tv_repetitions.getText().toString());

                //insert/update database
                if(training != null && training.getId() > 0) {
                    training.setTitle(trainingName);
                    training.setDescription(trainingDescription);
                    training.setTotalDuration(trainingDuration);
                    training.setDate(trainingDateInMilis);
                    //training.setTeam();
                    try {
                        result = training_dao.update(training);
                        if(result){
                            ArrayList<TrainingExercise> previousTrainingExercises = (ArrayList<TrainingExercise>) training_exercise_dao.getByCriteria(new TrainingExercise(-1, training, null, -1));
                            for(TrainingExercise te: previousTrainingExercises){
                                training_exercise_dao.delete(te);
                            }
                            for(Exercise e:trainingExercises){
                                training_exercise_dao.insert(new TrainingExercise(-1, training, e, trainingExerciseRepetitions));
                            }
                        }
                    }catch (GenericDAOException ex){
                        System.err.println(CreateTrainingActivity.class.getName() + " [WARNING] " + ex.toString());
                        Logger.getLogger(CreateTrainingActivity.class.getName()).log(Level.WARNING, null, ex);
                    }

                    if(result){
                        Toast.makeText(getApplicationContext(), R.string.updated, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.not_updated, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        training = new Training(
                                -1,
                                trainingName,
                                trainingDescription,
                                trainingDateInMilis,
                                trainingDuration,
                                team_dao.getById(1)); //TODO what team is selected???
                        training.setId(training_dao.insert(training));
                        result = training.getId() > 0;
                        if(result){
                            for(Exercise e:trainingExercises){
                                training_exercise_dao.insert(new TrainingExercise(-1, training, e, Integer.parseInt(tv_repetitions.getText().toString())));
                            }
                        }
                    }catch (GenericDAOException ex){
                        System.err.println(CreateTrainingActivity.class.getName() + " [WARNING] " + ex.toString());
                        Logger.getLogger(CreateTrainingActivity.class.getName()).log(Level.WARNING, null, ex);
                    }

                    if(result){
                        Toast.makeText(getApplicationContext(), R.string.inserted, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.not_inserted, Toast.LENGTH_SHORT).show();
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
}
