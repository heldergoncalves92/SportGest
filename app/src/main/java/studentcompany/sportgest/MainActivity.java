package studentcompany.sportgest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    //Interface
    private Button eventCategoiesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //EventCategoryButton
        eventCategoiesButton = (Button)findViewById(R.id.event_category_button);
        eventCategoiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*int id_To_Search = arg2 + 1;
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", id_To_Search);
                */
                Intent intent = new Intent(getApplicationContext(), ListEventCategoryActivity.class);

                //intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });


        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
