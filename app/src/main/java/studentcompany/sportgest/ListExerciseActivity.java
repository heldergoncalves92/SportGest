package studentcompany.sportgest;

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

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import studentcompany.sportgest.daos.Attribute_DAO;
import studentcompany.sportgest.daos.Attribute_Exercise_DAO;
import studentcompany.sportgest.daos.Exercise_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Exercise;

public class ListExerciseActivity extends AppCompatActivity {

    //DAOs
    private Exercise_DAO exercise_dao;
    private Attribute_DAO attribute_dao;
    private Attribute_Exercise_DAO attribute_exercise_dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_exercise);

        exercise_dao = new Exercise_DAO(this);
        ArrayList<Exercise> exerciseList;
        try {
            exerciseList = exercise_dao.getAll();
        } catch (GenericDAOException ex) {
            //System.err.println(ListEventCategoryActivity.class.getName() + " [WARNING] " + ex.toString());
            Logger.getLogger(ListEventCategoryActivity.class.getName()).log(Level.WARNING, null, ex);
            exerciseList = null;
        }
        ArrayList<String> array_list = new ArrayList<>();
        for (Exercise ec : exerciseList) {
            array_list.add(ec.getTitle());
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array_list);

        ListView listView = (ListView) findViewById(R.id.exercise_ListView);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                int id_To_Search = position + 1;

                Bundle dataBundle = new Bundle();
                dataBundle.putInt(Exercise_DAO.TABLE_NAME+Exercise_DAO.COLUMN_ID, id_To_Search);

                Intent intent = new Intent(getApplicationContext(), DisplayExerciseActivity.class);

                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.list_exercise_toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onResume() {//update list
        super.onResume();  // Always call the superclass method first

        ArrayList<Exercise> exerciseList;
        try {
            exerciseList = exercise_dao.getAll();
        } catch (GenericDAOException ex) {
            //System.err.println(ListEventCategoryActivity.class.getName() + " [WARNING] " + ex.toString());
            Logger.getLogger(ListEventCategoryActivity.class.getName()).log(Level.WARNING, null, ex);
            exerciseList = null;
        }
        ArrayList<String> array_list = new ArrayList<>();
        for (Exercise ec : exerciseList) {
            array_list.add(ec.getTitle());
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array_list);

        ListView listView = (ListView) findViewById(R.id.exercise_ListView);
        listView.setAdapter(arrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_crud, menu);
        MenuItem editItem = menu.findItem(R.id.Edit);
        editItem.setVisible(false);
        MenuItem remItem = menu.findItem(R.id.Delete);
        remItem.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.Add:
                Bundle dataBundle = new Bundle();
                dataBundle.putInt(Exercise_DAO.TABLE_NAME+Exercise_DAO.COLUMN_ID, 0);

                Intent intent = new Intent(getApplicationContext(), CreateExerciseActivity.class);
                intent.putExtras(dataBundle);

                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}