package studentcompany.sportgest.Players;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Player_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Player;
import studentcompany.sportgest.domains.Position;

public class CreatePlayer_Activity extends AppCompatActivity implements View.OnClickListener {

    //DAOs
    private Player_DAO player_dao;

    Player player = null;
    long playerID = -1;

    private final int CREATE_TAG = 20;
    private final String FILENAME_COUNTRIES = "";
    private final String FILENAME_GENDERS = "";
    private final String FILENAME_MARITALSTATUS = "";

    private ImageButton btnCalendar;
    private TextView txtDate;
    private int mYear, mMonth, mDay;

    private EditText tv_nickname,tv_name,tv_height,tv_weight,tv_address,tv_email,tv_number;
    private EditText focusView=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_player);

        player_dao = new Player_DAO(this);

        btnCalendar = (ImageButton) findViewById(R.id.birthday);
        txtDate = (TextView) findViewById(R.id.txtDate);

        btnCalendar.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar_crud, menu);
        MenuItem editItem = menu.findItem(R.id.Edit);
        MenuItem delItem = menu.findItem(R.id.Delete);
        MenuItem addItem = menu.findItem(R.id.Add);
        editItem.setVisible(false);
        delItem.setVisible(false);
        addItem.setVisible(true);

        tv_nickname = (EditText) findViewById(R.id.nickname);
        tv_name = (EditText) findViewById(R.id.name);
        Spinner tv_nationality = (Spinner) findViewById(R.id.nationality);
        Spinner tv_maritalStatus = (Spinner) findViewById(R.id.maritalstatus);
        tv_height = (EditText) findViewById(R.id.height);
        tv_weight = (EditText) findViewById(R.id.weight);
        tv_address = (EditText) findViewById(R.id.address);
        Spinner tv_gender = (Spinner) findViewById(R.id.gender);
        tv_email = (EditText) findViewById(R.id.email);
        Spinner tv_preferredFoot = (Spinner) findViewById(R.id.preferredfoot);
        tv_number = (EditText) findViewById(R.id.number);
        ImageView tv_photo = (ImageView) findViewById(R.id.photo);
        Spinner tv_position = (Spinner) findViewById(R.id.position);


        ArrayList<String> gendersList = new ArrayList<>();
        gendersList.add("Male");
        gendersList.add("Female");

        Resources res = getResources();
        String[] countries_array = res.getStringArray(R.array.countries_array);
        String[] marital_array = res.getStringArray(R.array.marital_status);

        ArrayList<String> preferredList = new ArrayList<>();
        preferredList.add("Right");
        preferredList.add("Left");

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, gendersList);
        tv_gender.setAdapter(adapter1);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, countries_array);
        tv_nationality.setAdapter(adapter2);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, marital_array);
        tv_maritalStatus.setAdapter(adapter3);
        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, preferredList);
        tv_preferredFoot.setAdapter(adapter4);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);

        switch(item.getItemId())
        {
            //add action
            case R.id.Add:
                 tv_nickname = (EditText) findViewById(R.id.nickname);
                 tv_name = (EditText) findViewById(R.id.name);
                Spinner tv_nationality = (Spinner) findViewById(R.id.nationality);
                Spinner tv_maritalStatus = (Spinner) findViewById(R.id.maritalstatus);
                //TODO: por a data a vir do botao
                 tv_height = (EditText) findViewById(R.id.height);
                 tv_weight = (EditText) findViewById(R.id.weight);
                 tv_address = (EditText) findViewById(R.id.address);
                Spinner tv_gender = (Spinner) findViewById(R.id.gender);
                 tv_email = (EditText) findViewById(R.id.email);
                Spinner tv_preferredFoot = (Spinner) findViewById(R.id.preferredfoot);
                 tv_number = (EditText) findViewById(R.id.number);
                ImageView tv_photo = (ImageView) findViewById(R.id.photo);
                Spinner tv_position = (Spinner) findViewById(R.id.position);

                String nickname = tv_nickname.getText().toString();
                String name = tv_name.getText().toString();
                String nationality = tv_nationality.getSelectedItem()!=null ? tv_nationality.getSelectedItem().toString() : "TUGA";
                String maritalStatus = "";
                if(tv_maritalStatus.getSelectedItem()!=null)
                    maritalStatus=tv_maritalStatus.getSelectedItem().toString();
                //TODO corrigir data
                //corrigir quando a data estiver direita
                //String birthday = mYear+"-"+mMonth+"-"+mDay;
                int birthday = 1;
                int height = Integer.parseInt(tv_height.getText().toString());
                float weight = Float.parseFloat(tv_weight.getText().toString());
                String address = tv_address.getText().toString();
                String gender = "";
                if(tv_gender.getSelectedItem()!=null)
                    gender = tv_gender.getSelectedItem().toString();
                String email = tv_email.getText().toString();
                String preferredFoot = "";
                if(tv_preferredFoot.getSelectedItem()!=null)
                    preferredFoot = tv_preferredFoot.getSelectedItem().toString();
                int number = Integer.parseInt(tv_number.getText().toString());
                //ups vai estar a imagem em bitmap ou o path para ela?
                //String photo = tv_photo.get
                String photo="";
                Position position = null;
                if(tv_position.getSelectedItem()!=null)
                    position = (Position) tv_position.getSelectedItem();

                boolean conti = validate(nickname,name,height,weight,address,email,number);
                if(conti){

                    player=new Player(nickname,name,nationality,maritalStatus,birthday,height,weight,address,gender,photo,email,preferredFoot,number,null,position);

                    //insert/update database
                    try {
                        playerID=player_dao.insert(player);
                    }catch (GenericDAOException ex){
                        System.err.println(CreatePlayer_Activity.class.getName() + " [WARNING] " + ex.toString());
                        Logger.getLogger(CreatePlayer_Activity.class.getName()).log(Level.WARNING, null, ex);
                    }

                    Intent intent = new Intent();
                    intent.putExtra("id",playerID);
                    setResult(1, intent);
                    finish();
                    return true;
                } else {
                    focusView.requestFocus();
                    return super.onOptionsItemSelected(item);
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean validate(String nickname,String name,int height,float weight,String address,String email,int number){
        boolean conti=false;
        focusView = tv_nickname;

        if(nickname.length()<5) {
            focusView = tv_nickname;
            tv_nickname.setError(getString(R.string.err_username_short));
        }
        else
            conti=true;

        if(name.length()<5) {
            focusView = tv_name;
            tv_nickname.setError(getString(R.string.err_name_short));
        }
        else
            conti=true;

        if(address.length()<5) {
            focusView = tv_name;
            tv_nickname.setError(getString(R.string.err_address_short));
        }
        else
            conti=true;

        if(number>0 && number<100) {
            focusView = tv_number;
            tv_number.setError(getString(R.string.err_number));
        }
        else
            conti=true;

        if(email.contains("@")) {
            String[] emailTesting = email.split("@");
            if(emailTesting[1].contains(".")) {
                String[] emailTestingIntern = emailTesting[1].split(".");
                if(emailTestingIntern[0].length()>1 && emailTestingIntern[1].length()>1)
                    conti = true;
                else {
                    focusView = tv_email;
                    tv_email.setError(getString(R.string.err_valid_email));
                }
            }
            else {
                focusView = tv_email;
                tv_email.setError(getString(R.string.err_valid_email));
            }
        } else {
            focusView = tv_email;
            tv_email.setError(getString(R.string.err_valid_email));
        }

        return conti;
    }












    @Override
    public void onClick(View v) {
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
                            txtDate.setText(Integer.toString(year));

                        }
                    }, mYear, mMonth, mDay);
            dpd.show();
        }
    }
}
