package studentcompany.sportgest.Exercises;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import studentcompany.sportgest.EventCategories.DisplayEventCategoryActivity;
import studentcompany.sportgest.EventCategories.ListEventCategoryActivity;
import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Attribute_Exercise_DAO;
import studentcompany.sportgest.daos.Exercise_DAO;
import studentcompany.sportgest.daos.Pair;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Attribute;
import studentcompany.sportgest.domains.Exercise;

public class DisplayExerciseActivity extends AppCompatActivity {

    //DAOs
    private Exercise_DAO exercise_dao;
    private Attribute_Exercise_DAO attribute_exercise_dao;

    //Id of current exercise displayed
    private int exerciseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set activity layout
        setContentView(R.layout.activity_display_exercise);
        //get layout components
        TextView exerciseName = (TextView) findViewById(R.id.name);
        TextView exerciseDuration = (TextView) findViewById(R.id.display_duration);
        TextView exerciseDescription = (TextView) findViewById(R.id.description);
        ListView exerciseAttributesListView = (ListView) findViewById(R.id.exerciseAttributes);
        //initialize required DAOs
        exercise_dao = new Exercise_DAO(this);
        attribute_exercise_dao = new Attribute_Exercise_DAO(this);

        //variable for presented object
        Exercise exercise = null;

        //get extras with object id
        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            //get Exercise ID
            exerciseID = extras.getInt(Exercise_DAO.TABLE_NAME+Exercise_DAO.COLUMN_ID);

            System.err.println("[EXERCISE ID]" + exerciseID);

            //validation
            if(exerciseID > 0){
                //get the exercise information
                try {
                    exercise = exercise_dao.getById(exerciseID);
                } catch (GenericDAOException ex){
                    System.err.println(DisplayEventCategoryActivity.class.getName() + " [WARNING] " + ex.toString());
                    Logger.getLogger(DisplayEventCategoryActivity.class.getName()).log(Level.WARNING, null, ex);
                    exercise = null;
                }
                System.err.println("[INFO] " + exercise.toString());
                if(exerciseDuration == null){
                    System.err.println("[INFO] is null!!!!!!!");
                } else {
                    System.err.println("[INFO] is not null :)" + String.valueOf(exercise.getDuration()));
                }
                //validation
                if(exercise != null) {
                    //set layout variables with information
                    exerciseName.setText(exercise.getTitle());
                    exerciseName.setFocusable(false);
                    exerciseName.setClickable(false);

                    exerciseDuration.setText(String.valueOf(exercise.getDuration()));
                    exerciseDuration.setFocusable(false);
                    exerciseDuration.setClickable(false);

                    exerciseDescription.setText(exercise.getDescription());
                    exerciseDescription.setFocusable(false);
                    exerciseDescription.setClickable(false);

                    //get list of attributes allocated to some exerciseID
                    ArrayList<Pair<Attribute, Exercise>> exerciseAttributes;
                    try {
                        exerciseAttributes = new ArrayList<>(attribute_exercise_dao.getBySecondId(exerciseID));
                    } catch (GenericDAOException ex){
                        System.err.println(ListEventCategoryActivity.class.getName() + " [WARNING] " + ex.toString());
                        Logger.getLogger(ListEventCategoryActivity.class.getName()).log(Level.WARNING, null, ex);
                        exerciseAttributes = null;
                    }
                    //Construct a new array with only the attribute names
                    ArrayList<String> array_list = new ArrayList<>();
                    for(Pair<Attribute, Exercise> ec : exerciseAttributes){
                        array_list.add(ec.getFirst().getName());
                    }
                    ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, array_list);

                    //set list in layout ListView
                    exerciseAttributesListView.setAdapter(arrayAdapter);

                    //in case the user wants to see details about some attribute, start a display Attribute Activity
                    exerciseAttributesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                            // TODO Auto-generated method stub -> implement edit attribute details
                            /*int id_To_Search = arg2 + 1;

                            Bundle dataBundle = new Bundle();
                            dataBundle.putInt("id", id_To_Search);

                            Intent intent = new Intent(getApplicationContext(), DisplayEventCategoryActivity.class);

                            intent.putExtras(dataBundle);
                            startActivity(intent);*/
                        }
                    });
                }
            }
        }

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.display_exercise_toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar_crud, menu);
        MenuItem addItem = menu.findItem(R.id.Add);
        addItem.setVisible(false);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);
        switch(item.getItemId())
        {
            case R.id.Edit:
                //put current exercise ID in extras
                Bundle dataBundle = new Bundle();
                dataBundle.putInt(Exercise_DAO.TABLE_NAME+Exercise_DAO.COLUMN_ID, exerciseID);
                //declare intention to start CreateExerciseActivity
                Intent intent = new Intent(getApplicationContext(), CreateExerciseActivity.class);
                //add data
                intent.putExtras(dataBundle);
                //start activity
                startActivity(intent);

                return true;
            case R.id.Delete:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.delete_confirmation)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                exercise_dao.deleteById(exerciseID);
                                Toast.makeText(getApplicationContext(), R.string.delete_sucessful, Toast.LENGTH_SHORT).show();
                                finish();
                                //Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                //startActivity(intent);
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
