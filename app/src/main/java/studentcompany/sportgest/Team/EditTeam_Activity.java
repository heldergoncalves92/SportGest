package studentcompany.sportgest.Team;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.logging.Level;
import java.util.logging.Logger;

import studentcompany.sportgest.EventCategories.EventCategory_List_Adapter;
import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Team_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Team;

public class EditTeam_Activity extends AppCompatActivity {

    //DAOs
    private Team_DAO team_dao;

    Team team = null;
    int teamID = -1;

    private EditText tv_name,tv_description,tv_season;
    private TextInputLayout inputLayoutName,inputLayoutDescription,inputLayoutSeason;
    private ImageButton tv_squad;
    private CheckBox tv_isCon;
    private int EDIT_SQUAD = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_team);

        Bundle b = getIntent().getExtras();
        if(b!=null){
            teamID = (int)(b.getLong("id")+0);
        }

         tv_name = (EditText) findViewById(R.id.name);
         tv_description = (EditText) findViewById(R.id.description);
         tv_season = (EditText) findViewById(R.id.season);
        tv_isCon = (CheckBox) findViewById(R.id.isCom);

        //CheckBox tv_iscon = (CheckBox) findViewById(R.id.isCom);
        ImageView tv_logo = (ImageView) findViewById(R.id.logo);

        Team teamFromDB=null;
        team_dao = new Team_DAO(this);

        try {
            teamFromDB = team_dao.getById(teamID);
        } catch (GenericDAOException e) {
            e.printStackTrace();
        }
        if (teamFromDB!=null){
            if(teamFromDB.getName()!=null)
                tv_name.setText(teamFromDB.getName());
            else
                tv_name.setText("");
            if(teamFromDB.getDescription()!=null)
                tv_description.setText(teamFromDB.getDescription());
            else
                tv_description.setText("");
            if(teamFromDB.getSeason()!=-1)
                tv_season.setText(Integer.toString(teamFromDB.getSeason()));
            else
                tv_season.setText("");
            if(teamFromDB.getIs_com()!=-1){
                boolean val = false;
                if(teamFromDB.getIs_com()==1)
                    val = true;
                tv_isCon.setChecked(val);
            }
            else
                tv_isCon.setChecked(false);
            //TODO: por a imagem a aparecer
            //if(teamFromDB.getLogo()!=null)

        }

        inputLayoutName = (TextInputLayout) findViewById(R.id.inputLayoutName);
        inputLayoutDescription = (TextInputLayout) findViewById(R.id.inputLayoutDescription);
        inputLayoutSeason = (TextInputLayout) findViewById(R.id.inputLayoutSeason);

        tv_name.addTextChangedListener(new MyTextWatcher(tv_name));
        tv_description.addTextChangedListener(new MyTextWatcher(tv_description));
        tv_season.addTextChangedListener(new MyTextWatcher(tv_season));

        tv_squad = (ImageButton)findViewById(R.id.squad);
        tv_squad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), EditSquad_Activity.class);
                intent.putExtra("id", teamID);
                startActivityForResult(intent, EDIT_SQUAD);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar_crud_edit, menu);
        MenuItem editItem = menu.findItem(R.id.Edit);
        editItem.setVisible(true);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);

        switch(item.getItemId())
        {
            //add action
            case R.id.Edit:

                 tv_name = (EditText) findViewById(R.id.name);
                 tv_description = (EditText) findViewById(R.id.description);
                 tv_season = (EditText) findViewById(R.id.season);
                //CheckBox tv_iscon = (CheckBox) findViewById(R.id.isCom);
                ImageView tv_logo = (ImageView) findViewById(R.id.logo);

                String name = tv_name.getText().toString();
                String description = tv_description.getText().toString();
                int season = Integer.parseInt(tv_season.getText().toString());
                int isCom = tv_isCon.isChecked()?1:0;
                //ups vai estar a imagem em bitmap ou o path para ela?
                //String logo = tv_photo.get
                String logo="";

                boolean ok = false;
                if (validateName())
                    if (validateDescription())
                        if (validateSeason())
                            ok = true;

                if (!ok) {
                    //Intent intent = new Intent();
                    //setResult(2, intent);
                    //finish();
                    return false;
                }

                team=new Team(teamID, name, description, logo, season, isCom);
                boolean corrected = false;
                //insert/update database
                try {
                    if(teamID > 0){
                        team_dao.update(team);
                        corrected = true;
                    } else {
                        team_dao.insert(team);
                        corrected = true;
                    }
                }catch (GenericDAOException ex){
                    System.err.println(CreateTeam_Activity.class.getName() + " [WARNING] " + ex.toString());
                    Logger.getLogger(CreateTeam_Activity.class.getName()).log(Level.WARNING, null, ex);
                }
                Intent intent = new Intent();
                setResult(corrected?1:2, intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.inputLayoutName:
                    validateName();
                    break;
                case R.id.inputLayoutDescription:
                    validateDescription();
                    break;
                case R.id.inputLayoutSeason:
                    validateSeason();
                    break;
            }
        }
    }

    private boolean validateName() {
        if(inputLayoutName==null)
            inputLayoutName = (TextInputLayout) findViewById(R.id.inputLayoutName);
        String pw = tv_name.getText().toString().trim();
        if (pw.isEmpty() || pw.length() < 5) {
            inputLayoutName.setError(getString(R.string.err_name_short));
            return false;
        }
        inputLayoutName.setErrorEnabled(false);
        return true;
    }

    private boolean validateDescription() {
        if(inputLayoutDescription==null)
            inputLayoutDescription = (TextInputLayout) findViewById(R.id.inputLayoutDescription);
        String pw = tv_description.getText().toString().trim();
        if (pw.isEmpty() || pw.length() < 5) {
            inputLayoutDescription.setError(getString(R.string.err_description_short));
            return false;
        }
        inputLayoutDescription.setErrorEnabled(false);
        return true;
    }

    private boolean validateSeason() {
        if(inputLayoutSeason==null)
            inputLayoutSeason = (TextInputLayout) findViewById(R.id.inputLayoutSeason);
        String pw = tv_season.getText().toString().trim();
        if(!pw.matches("\\d+(\\.\\d+)?")){
            inputLayoutSeason.setError(getString(R.string.err_number));
            return false;
        }
        if (pw.isEmpty() || (pw.length() > 1 && pw.length()<4)) {
            int nb = -1;
            if(isNumericInt(pw)){
                nb = Integer.parseInt(pw);
            }else{
                inputLayoutSeason.setError(getString(R.string.err_number));
                return false;
            }
            if(!(nb<1000 && nb>2050)){
                inputLayoutSeason.setError(getString(R.string.err_season));
                return false;
            }
        }
        inputLayoutSeason.setErrorEnabled(false);
        return true;
    }

    @Override
    public void onStop(){
        super.onStop();

        setResult(0);
    }

    private void selectTeam() {
        Intent intent = new Intent(this, EditSquad_Activity.class);
        intent.putExtra("id", teamID);
        startActivityForResult(intent, EDIT_SQUAD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EDIT_SQUAD) {
            if(resultCode == 1){
                Toast.makeText(getApplicationContext(), R.string.updated, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static boolean isNumericInt(String str)
    {
        try
        {
            int d = Integer.parseInt(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }
}
