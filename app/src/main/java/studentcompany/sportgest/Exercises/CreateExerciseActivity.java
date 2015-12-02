package studentcompany.sportgest.Exercises;

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

import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Attribute_DAO;
import studentcompany.sportgest.daos.Attribute_Exercise_DAO;
import studentcompany.sportgest.daos.Exercise_DAO;
import studentcompany.sportgest.daos.Pair;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Attribute;
import studentcompany.sportgest.domains.Exercise;

public class CreateExerciseActivity extends AppCompatActivity {

    //DAOs
    private Exercise_DAO exercise_dao;
    private Attribute_DAO attribute_dao;
    private Attribute_Exercise_DAO attribute_exercise_dao;

    int exerciseID = -1;
    Exercise exercise = null;
    ArrayList<Attribute> exerciseAttributesAdapter = new ArrayList<>();
    ArrayList<Attribute> selectedExerciseAttributesAdapter = new ArrayList<>();
    ArrayList<Attribute> allAttributesAdapter = new ArrayList<>();
    ArrayList<Attribute> selectedAllAttributesAdapter = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_exercise);
        TextView title = (TextView) findViewById(R.id.name);
        TextView duration = (TextView) findViewById(R.id.duration);
        TextView description = (TextView) findViewById(R.id.description);
        final ListView exerAttList = (ListView) findViewById(R.id.selectedAttributes);
        final ListView attList = (ListView) findViewById(R.id.exerciseAvailableAttributes);


        exercise_dao = new Exercise_DAO(this);
        attribute_dao = new Attribute_DAO(this);
        attribute_exercise_dao = new Attribute_Exercise_DAO(this);

        Bundle extras = getIntent().getExtras();

        if(extras !=null) {
            exerciseID = extras.getInt(Exercise_DAO.TABLE_NAME + Exercise_DAO.COLUMN_ID);
            //if the intetion is to edit, pre-load exercise information
            if (exerciseID > 0) {
                try {
                    exercise = exercise_dao.getById(exerciseID);
                } catch (GenericDAOException ex) {
                    System.err.println(CreateExerciseActivity.class.getName() + " [WARNING] " + ex.toString());
                    Logger.getLogger(CreateExerciseActivity.class.getName()).log(Level.WARNING, null, ex);
                    exercise = null;
                }

                if (exercise != null) {
                    title.setText(exercise.getTitle());
                    title.setFocusable(true);
                    title.setClickable(true);

                    duration.setText(""+exercise.getDuration());
                    title.setFocusable(true);
                    title.setClickable(true);

                    description.setText(exercise.getDescription());
                    description.setFocusable(true);
                    description.setClickable(true);

                    //get list of attributes allocated to some exerciseID
                    ArrayList<Pair<Attribute, Exercise>> exerciseAttributes;
                    try {
                        exerciseAttributes = new ArrayList<>(attribute_exercise_dao.getBySecondId(exerciseID));
                    } catch (GenericDAOException ex) {
                        System.err.println(ListExerciseActivity.class.getName() + " [WARNING] " + ex.toString());
                        Logger.getLogger(ListExerciseActivity.class.getName()).log(Level.WARNING, null, ex);
                        exerciseAttributes = null;
                    }

                    for (Pair<Attribute, Exercise> ec : exerciseAttributes) {
                        exerciseAttributesAdapter.add(ec.getFirst());
                    }
                    ArrayAdapter selectedArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, exerciseAttributesAdapter);//by default uses toString
                    exerAttList.setAdapter(selectedArrayAdapter);
                    exerAttList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    exerAttList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            exerAttList.setItemChecked(position, exerAttList.isItemChecked(position));
                        }
                    });

                    //get all registered attributes
                    ArrayList<Attribute> attributeList;
                    try {
                        attributeList = new ArrayList<>(attribute_dao.getAll());
                    } catch (GenericDAOException ex) {
                        System.err.println(ListExerciseActivity.class.getName() + " [WARNING] " + ex.toString());
                        Logger.getLogger(ListExerciseActivity.class.getName()).log(Level.WARNING, null, ex);
                        attributeList = null;
                    }

                    //select only the attributes not selected for some exercise
                    for (Attribute a : attributeList) {
                        if (!exerciseAttributesAdapter.contains(a)) {
                            allAttributesAdapter.add(a);
                        }
                    }
                    ArrayAdapter allAttrArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, allAttributesAdapter);
                    attList.setAdapter(allAttrArrayAdapter);
                    attList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    attList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            attList.setItemChecked(position, attList.isItemChecked(position));
                        }
                    });
                }
            } else { //create a new exercise from blank interface

                //get all registered attributes
                try {
                    allAttributesAdapter = new ArrayList<>(attribute_dao.getAll());
                } catch (GenericDAOException ex) {
                    System.err.println(ListExerciseActivity.class.getName() + " [WARNING] " + ex.toString());
                    Logger.getLogger(ListExerciseActivity.class.getName()).log(Level.WARNING, null, ex);
                }

                ArrayAdapter allAttrArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, allAttributesAdapter);
                attList.setAdapter(allAttrArrayAdapter);
                attList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                attList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        attList.setItemChecked(position, attList.isItemChecked(position));
                    }
                });
            }
        }


        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.create_exercise_toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar_crud, menu);
        MenuItem editItem = menu.findItem(R.id.Edit);
        MenuItem delItem = menu.findItem(R.id.Delete);
        editItem.setVisible(false);
        delItem.setVisible(false);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);

        ListView currExerciseAttributes = (ListView) findViewById(R.id.selectedAttributes);
        ListView currAttributes = (ListView) findViewById(R.id.exerciseAvailableAttributes);

        switch(item.getItemId())
        {
            //add action
            case R.id.Add:
                TextView title = (TextView) findViewById(R.id.name);
                TextView duration = (TextView) findViewById(R.id.duration);
                TextView description = (TextView) findViewById(R.id.description);

                // TODO validate inputs

                exercise = new Exercise(
                        exerciseID,
                        title.getText().toString(),
                        description.getText().toString(),
                        Integer.parseInt(duration.getText().toString()));

                //insert/update database
                try {
                    if(exerciseID > 0){
                        exercise_dao.update(exercise);
                    } else {
                        exercise_dao.insert(exercise);
                    }
                }catch (GenericDAOException ex){
                    System.err.println(CreateExerciseActivity.class.getName() + " [WARNING] " + ex.toString());
                    Logger.getLogger(CreateExerciseActivity.class.getName()).log(Level.WARNING, null, ex);
                }

                //get attributes selected
                for(int i=0 ; i< exerciseAttributesAdapter.size() ; i++){
                    //for each one insert them on the relation table
                    try {
                        attribute_exercise_dao.insert(new Pair<>(exerciseAttributesAdapter.get(i), exercise));
                    } catch (GenericDAOException ex) {
                        System.err.println(CreateExerciseActivity.class.getName() + " [WARNING] " + ex.toString());
                        Logger.getLogger(CreateExerciseActivity.class.getName()).log(Level.WARNING, null, ex);
                    }
                }
                Toast.makeText(getApplicationContext(), R.string.inserted, Toast.LENGTH_SHORT).show();
                finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void addSelectAttribute(View view){
        ListView currExerciseAttributes = (ListView) findViewById(R.id.selectedAttributes);
        ListView currAttributes = (ListView) findViewById(R.id.exerciseAvailableAttributes);

        for(int i=0; i<selectedAllAttributesAdapter.size(); i++){
            Attribute a = selectedAllAttributesAdapter.get(i);
            if(!exerciseAttributesAdapter.contains(a)){
                exerciseAttributesAdapter.add(a);
            }
            //remove from available list
            allAttributesAdapter.remove(a);
            //remove from selected list
            selectedAllAttributesAdapter.remove(a);
        }

        ArrayAdapter selectedArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, exerciseAttributesAdapter);//by default uses toString
        currExerciseAttributes.setAdapter(selectedArrayAdapter);
        ArrayAdapter allAttrArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, allAttributesAdapter);
        currAttributes.setAdapter(allAttrArrayAdapter);
    }

    public void remSelectAttribute(View view){
        ListView currExerciseAttributes = (ListView) findViewById(R.id.selectedAttributes);
        ListView currAttributes = (ListView) findViewById(R.id.exerciseAvailableAttributes);

        for(int i=0; i<selectedExerciseAttributesAdapter.size(); i++){
            Attribute a = selectedExerciseAttributesAdapter.get(i);
            if(!allAttributesAdapter.contains(a)){
                allAttributesAdapter.add(a);
            }
            //remove from available list
            exerciseAttributesAdapter.remove(a);
            //remove from selected list
            selectedExerciseAttributesAdapter.remove(a);
        }

        ArrayAdapter selectedArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, exerciseAttributesAdapter);//by default uses toString
        currExerciseAttributes.setAdapter(selectedArrayAdapter);
        ArrayAdapter allAttrArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, allAttributesAdapter);
        currAttributes.setAdapter(allAttrArrayAdapter);
    }
}
