package studentcompany.sportgest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import studentcompany.sportgest.EventCategories.ListEventCategoryActivity;
import studentcompany.sportgest.Exercises.ListExerciseActivity;
import studentcompany.sportgest.Players.PlayersList_Activity;
import studentcompany.sportgest.Team.TeamList_Activity;
import studentcompany.sportgest.Users.UserListActivity;

public class MainActivity extends AppCompatActivity {
    //Interface
    private Button eventCategoiesButton, exerciseButton;

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

        //EventCategoryButton
        exerciseButton = (Button)findViewById(R.id.exercise_button);
        exerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListExerciseActivity.class);

                startActivity(intent);
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

    public void goTo_Users(View v){
        Intent intent = new Intent(this, UserListActivity.class);
        startActivity(intent);
    }

    public void goTo_Players(View v){
        Intent intent = new Intent(this, PlayersList_Activity.class);
        intent.putExtra("TEAM",1);
        startActivity(intent);
    }

    public void goTo_Teams(View v){
        Intent intent = new Intent(this, TeamList_Activity.class);
        startActivity(intent);
    }

    public void goTo_Login(View v){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
