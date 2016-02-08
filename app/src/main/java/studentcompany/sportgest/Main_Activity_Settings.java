package studentcompany.sportgest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import studentcompany.sportgest.EventCategories.EventCategory_Activity_ListView;
import studentcompany.sportgest.Positions.Position_Activity_ListView;
import studentcompany.sportgest.Roles.Role_Activity_ListView;
import studentcompany.sportgest.Team.Team_Activity_ListView;
import studentcompany.sportgest.Users.User_Activity_ListView;

public class Main_Activity_Settings extends Activity {

    private Button eventCategoiesButton, rolesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_settings);

        //RoleButton
        rolesButton = (Button)findViewById(R.id.roles_button);
        rolesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Role_Activity_ListView.class);
                startActivity(intent);
            }
        });

        //EventCategoryButton
        eventCategoiesButton = (Button)findViewById(R.id.event_category_button);
        eventCategoiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), EventCategory_Activity_ListView.class);
                startActivity(intent);
            }
        });
    }


    public void goTo_Positions(View v){
        Intent intent = new Intent(this, Position_Activity_ListView.class);
        startActivity(intent);
    }

    public void goTo_Users(View v){
        Intent intent = new Intent(this, User_Activity_ListView.class);
        startActivity(intent);
    }

    public void goTo_Teams(View v){
        Intent intent = new Intent(this, Team_Activity_ListView.class);
        startActivity(intent);
    }
}
