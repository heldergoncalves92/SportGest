package studentcompany.sportgest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import studentcompany.sportgest.daos.Attribute_DAO;
import studentcompany.sportgest.daos.Attribute_Exercise_DAO;
import studentcompany.sportgest.daos.Exercise_DAO;

public class CreateExerciseActivity extends AppCompatActivity {

    //DAOs
    private Exercise_DAO exercise_dao;
    private Attribute_DAO attribute_dao;
    private Attribute_Exercise_DAO attribute_exercise_dao;

    TextView category;
    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_exercise);
        //category = (TextView) findViewById(R.id.event_category_editText);

        exercise_dao = new Exercise_DAO(this);
        attribute_dao = new Attribute_DAO(this);
        attribute_exercise_dao = new Attribute_Exercise_DAO(this);

        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            int Value = extras.getInt("id");

            if(Value>0){
                //means this is the view part not the add contact part.
                /*
                EventCategory res;
                try {
                    res = event_category_dao.getById(Value);
                } catch (GenericDAOException ex){
                    //System.err.println(DisplayEventCategoryActivity.class.getName() + " [WARNING] " + ex.toString());
                    Logger.getLogger(DisplayEventCategoryActivity.class.getName()).log(Level.WARNING, null, ex);
                    res = null;
                }
                id_To_Update = Value;

                Button b = (Button)findViewById(R.id.event_category_button);
                b.setVisibility(View.INVISIBLE);

                if(res != null) {
                    category.setText(res.getName());
                    category.setFocusable(false);
                    category.setClickable(false);
                }
                */
            }
        }

        //Toolbar
        //Toolbar toolbar = (Toolbar) findViewById(R.id.display_event_category_toolbar);
        //setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Bundle extras = getIntent().getExtras();

        if(extras !=null)
        {
            int Value = extras.getInt("id");
            /*
            if(Value>0){
                getMenuInflater().inflate(R.menu.menu_display_event_category, menu);
            }

            else{
                getMenuInflater().inflate(R.menu.menu_list_event_category, menu);
            }
            */
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);
        /*
        switch(item.getItemId())
        {
            case R.id.Edit_Event_Category:
                Button b = (Button)findViewById(R.id.event_category_button);
                b.setVisibility(View.VISIBLE);
                category.setEnabled(true);
                category.setFocusableInTouchMode(true);
                category.setClickable(true);

                return true;
            case R.id.Delete_Event_Category:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.delete_confirmation)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                event_category_dao.deleteById(id_To_Update);
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

        }*/
        return super.onOptionsItemSelected(item);
    }

    public void run(View view)
    {
        Boolean res = false;
        Bundle extras = getIntent().getExtras();

        if(extras !=null)
        {
            /*
            int Value = extras.getInt("id");
            if(Value>0){
                try {
                    res = event_category_dao.update(new EventCategory(id_To_Update, category.getText().toString()));
                } catch (GenericDAOException ex){
                    //System.err.println(DisplayEventCategoryActivity.class.getName() + " [WARNING] " + ex.toString());
                    Logger.getLogger(DisplayEventCategoryActivity.class.getName()).log(Level.WARNING, null, ex);
                }
                if(res){
                    Toast.makeText(getApplicationContext(), R.string.updated, Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), R.string.not_updated, Toast.LENGTH_SHORT).show();
                }
            }
            else{
                try {
                    res = event_category_dao.insert(new EventCategory(-1, category.getText().toString())) > 0;
                } catch (GenericDAOException ex){
                    //System.err.println(DisplayEventCategoryActivity.class.getName() + " [WARNING] " + ex.toString());
                    Logger.getLogger(DisplayEventCategoryActivity.class.getName()).log(Level.WARNING, null, ex);
                }
                if(res){
                    Toast.makeText(getApplicationContext(), R.string.done, Toast.LENGTH_SHORT).show();
                }

                else{
                    Toast.makeText(getApplicationContext(), R.string.not_done, Toast.LENGTH_SHORT).show();
                }
            }
            this.finish();
            */
        }

    }
}
