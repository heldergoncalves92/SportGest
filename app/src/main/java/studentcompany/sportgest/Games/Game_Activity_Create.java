package studentcompany.sportgest.Games;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Game_DAO;
import studentcompany.sportgest.daos.Team_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Game;
import studentcompany.sportgest.domains.Team;

public class Game_Activity_Create extends AppCompatActivity {

    private ImageButton btnCalendar;
    private TextView txtDate, txtHour;
    private int mYear, mMonth, mDay;
    private int mHour;
    private int mMinute;
    private int selectedYear=0, selectedMonth, selectedDay;

    private EditText tv_report;
    private Spinner hometeam_Spinner, visitorTeam_Spinner;

    private Team_DAO team_dao;
    private Game_DAO game_dao;

    private ArrayList<Team> mListTeams;
    private long baseGameID=0;
    private Game game=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity_create);


        //Get Informations from the previous activity or rotation Layout
        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();

            if (extras != null){
                baseGameID = extras.getLong("GAME");

            }
        } else {
            baseGameID = savedInstanceState.getInt("baseGameID");
        }




        btnCalendar = (ImageButton) findViewById(R.id.input_gameDate);
        txtDate = (TextView) findViewById(R.id.txtDate);
        txtHour = (TextView) findViewById(R.id.txtHour);

        tv_report = (EditText) findViewById(R.id.input_gameReport);

        hometeam_Spinner = (Spinner)findViewById(R.id.input_spinner_homeTeam);
        visitorTeam_Spinner = (Spinner)findViewById(R.id.input_spinner_visitorTeam);

        game_dao = new Game_DAO(getApplicationContext());
        team_dao = new Team_DAO(getApplicationContext());

        try {
            mListTeams = team_dao.getAll();

            //If there are no teams
            if(mListTeams == null)
                mListTeams = new ArrayList<Team>();

            mListTeams.add(0, new Team (-1, getString(R.string.select_team)));
            ArrayAdapter<Team> adapter = new ArrayAdapter<Team>(this,android.R.layout.simple_list_item_1, mListTeams);
            hometeam_Spinner.setAdapter(adapter);
            visitorTeam_Spinner.setAdapter(adapter);

        } catch (GenericDAOException e) {
            e.printStackTrace();
        }

        //For Edit Case
        if(baseGameID != 0){
            try {
                game = game_dao.getById(baseGameID);

                tv_report.setText(game.getReport());


            } catch (GenericDAOException e) {
                e.printStackTrace();
            }
        }
    }


    public void calender_Date(View v) {
        if (v == btnCalendar) {

            // Process to get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            // Launch Date Picker Dialog
            DatePickerDialog dpd = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            // Display Selected date in textbox
                            //txtDate.setText(dayOfMonth + "-"
                            //        + (monthOfYear + 1) + "-" + year);
                            txtDate.setText(Integer.toString(year)+"-"+Integer.toString(monthOfYear+1)+"-"+Integer.toString(dayOfMonth));
                            selectedDay = dayOfMonth;
                            selectedMonth = monthOfYear;
                            selectedYear = year;

                        }
                    }, mYear, mMonth, mDay);
            dpd.show();
        }
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute, true);
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user

            Game_Activity_Create gac = (Game_Activity_Create)getActivity();
            gac.set_Hour(hourOfDay, minute);
        }
    }

    public void set_Hour(int hour, int minute){
        mHour = hour;
        mMinute = minute;

        txtHour.setText(hour + ":" +minute + "h");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar_crud_add, menu);
        MenuItem addItem = menu.findItem(R.id.Add);
        addItem.setVisible(true);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);

        switch(item.getItemId())
        {
            //add action
            case R.id.Add:

                Team home_team    = mListTeams.get(hometeam_Spinner.getSelectedItemPosition());
                Team visitor_team = mListTeams.get(visitorTeam_Spinner.getSelectedItemPosition());

                if(home_team.getId() < 0) {
                    Toast.makeText(getApplicationContext(), "Home team is not valid!!", Toast.LENGTH_SHORT).show();
                    return true;
                }

                if(visitor_team.getId() < 0) {
                    Toast.makeText(getApplicationContext(), "Visitor team is not valid!!", Toast.LENGTH_SHORT).show();
                    return true;
                }

                if(visitor_team.getId() == home_team.getId()) {
                    Toast.makeText(getApplicationContext(), "The teams are the same!!", Toast.LENGTH_SHORT).show();
                    return true;
                }


                if (selectedYear == 0 ) {
                    Toast.makeText(getApplicationContext(), "Select a date!!", Toast.LENGTH_SHORT).show();
                    return true;
                }

                long today = new Date().getTime();
                GregorianCalendar date_selected = new GregorianCalendar();
                date_selected.set(selectedYear,selectedMonth,selectedDay);
                long date_game = date_selected.getTimeInMillis();

                if(today > date_game){
                    Toast.makeText(getApplicationContext(), "Invalid date!!", Toast.LENGTH_SHORT).show();
                    return true;
                }

                try {

                    Game game = new Game(home_team,visitor_team, date_game, tv_report.getText().toString());
                    long id = game_dao.insert(game);

                    Toast.makeText(getApplicationContext(), "Game inserted successfully!", Toast.LENGTH_SHORT).show();

                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("ID", id);
                    setResult(1, returnIntent);
                    finish();

                } catch (GenericDAOException e) {
                    e.printStackTrace();
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void onStop(){
        super.onStop();

        Intent returnIntent = new Intent();
        setResult(0, returnIntent);
    }
}
