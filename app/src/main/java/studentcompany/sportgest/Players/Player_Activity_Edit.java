package studentcompany.sportgest.Players;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Player_DAO;
import studentcompany.sportgest.daos.Player_Position_DAO;
import studentcompany.sportgest.daos.Position_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Player;
import studentcompany.sportgest.domains.PlayerPosition;
import studentcompany.sportgest.domains.Position;

public class Player_Activity_Edit extends AppCompatActivity implements View.OnClickListener, NumberPicker.OnValueChangeListener {

    //DAOs
    private Player_DAO player_dao;
    private Position_DAO position_dao;
    private Player_Position_DAO playerPosition_dao;

    Player player = null;
    int playerID = -1;

    private EditText tv_nickname,tv_name,tv_height,tv_weight,tv_address,tv_email,tv_number;
    private TextInputLayout inputLayoutNickname,inputLayoutName,inputLayoutHeight,inputLayoutWeight,inputLayoutAddress,inputLayoutEmail,inputLayoutNumber;

    private Button tv_positionButton;
    private Button tv_btnRemove;
    private ListView tv_positionsList;
    private TextView text_positions;

    private TextView focusView;
    private final String FILENAME_COUNTRIES = "";

    private ImageButton btnCalendar;
    private TextView txtDate;
    private int mYear, mMonth, mDay;
    private int selectedYear, selectedMonth, selectedDay;

    private ArrayList<PlayerPosition> playerPositionsActuals;
    ArrayList<String> positionsAvail;
    ArrayList<String> positionsAll;
    ArrayList<String> playerPositionsActualsString;
    NumberPicker np;
    Spinner selectedPositions;

    String selectedPosition = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_activity_edit);

        Bundle b = getIntent().getExtras();
        if(b!=null){
            playerID = (int) (b.getLong("id")+0);
        }

        btnCalendar = (ImageButton) findViewById(R.id.birthday);
        txtDate = (TextView) findViewById(R.id.txtDate);

        btnCalendar.setOnClickListener(this);

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
        text_positions = (TextView) findViewById(R.id.text_positions);
        tv_positionsList = (ListView) findViewById(R.id.positionsList);
        tv_btnRemove = (Button) findViewById(R.id.btnRemoveSelectedPosition);

        tv_positionsList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        tv_btnRemove.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                removeSelected();
            }
        });

        tv_positionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                    public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                                                        selectedPosition = (String) adapter.getSelectedItem();
                                                    }
                                                });

        Player playerFromDB=null;
        player_dao = new Player_DAO(this);
        position_dao = new Position_DAO(this);
        playerPosition_dao = new Player_Position_DAO(this);

        playerPositionsActuals = new ArrayList<>();

        try {
             playerFromDB = player_dao.getById(playerID);
             player = playerFromDB;
        } catch (GenericDAOException e) {
            e.printStackTrace();
        }
        if (playerFromDB!=null){
            if(playerFromDB.getNickname()!=null)
                tv_nickname.setText(playerFromDB.getNickname());
            else
                tv_nickname.setText("");

            if(playerFromDB.getName()!=null)
                tv_name.setText(playerFromDB.getName());
            else
                tv_name.setText(playerFromDB.getName());

            ArrayList<String> gendersList = new ArrayList<>();
            gendersList.add("Male");
            gendersList.add("Female");

            Resources res = getResources();
            String[] countries_array = res.getStringArray(R.array.countries_array);
            String[] marital_array = res.getStringArray(R.array.marital_status);

            ArrayList<String> preferredList = new ArrayList<>();
            preferredList.add("Right");
            preferredList.add("Left");

            positionsAll = new ArrayList<>();

            try {
                List<Position> positions = position_dao.getAll();
                for(Position p : positions)
                    positionsAll.add(p.getName());
            } catch (GenericDAOException e) {
                e.printStackTrace();
            }

            ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, gendersList);
            tv_gender.setAdapter(adapter1);
            ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, countries_array);
            tv_nationality.setAdapter(adapter2);
            ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, marital_array);
            tv_maritalStatus.setAdapter(adapter3);
            ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, preferredList);
            tv_preferredFoot.setAdapter(adapter4);

            int pos=-1;
            int counter=0;
            if (playerFromDB.getNationality()!=null)
                for(String s : countries_array){
                    if(playerFromDB.getNationality().equals(s))
                        pos=counter;
                    else
                        counter++;
                }

            if(pos!=-1)
                tv_nationality.setSelection(pos);
            else
                tv_nationality.setSelection(0);
                        if(playerFromDB.getHeight()!=-1)
                tv_height.setText(Integer.toString(playerFromDB.getHeight()));
            else
                tv_height.setText(Integer.toString(0));

            if(playerFromDB.getWeight()!=-1)
                tv_weight.setText(Float.toString(playerFromDB.getWeight()));
            else
                tv_weight.setText(Float.toString(0f));

            if(playerFromDB.getAddress()!=null)
                tv_address.setText(playerFromDB.getAddress());
            else
                tv_address.setText("");

            pos=-1;
            counter=0;
            if (playerFromDB.getMarital_status()!=null)
                for(String s : marital_array){
                    if(playerFromDB.getMarital_status().equals(s))
                        pos=counter;
                    else
                        counter++;
                }

            if(pos!=-1)
                tv_maritalStatus.setSelection(pos);
            else
                tv_maritalStatus.setSelection(0);

            pos=-1;
            counter=0;
            if (playerFromDB.getGender()!=null)
                for(String s : gendersList){
                    if(playerFromDB.getGender().equals(s))
                        pos=counter;
                    else
                        counter++;
                }

            if(pos!=-1)
                tv_gender.setSelection(pos);
            else
                tv_gender.setSelection(0);

            if(playerFromDB.getEmail()!=null)
                tv_email.setText(playerFromDB.getEmail());
            else
                tv_email.setText("");

            pos=-1;
            counter=0;
            if (playerFromDB.getPreferredFoot()!=null)
                for(String s : preferredList){
                    if(playerFromDB.getPreferredFoot().equals(s))
                        pos=counter;
                    else
                        counter++;
                }

            if(playerFromDB.getBirthDate()!=null) {
                String dateBeforeSplit = playerFromDB.getBirthDate();
                txtDate.setText(dateBeforeSplit);

                String[] dateParts = dateBeforeSplit.split("-");
                mYear = Integer.parseInt(dateParts[0]);
                mMonth = Integer.parseInt(dateParts[1]);
                mDay = Integer.parseInt(dateParts[2]);
                selectedYear = Integer.parseInt(dateParts[0]);
                selectedMonth = Integer.parseInt(dateParts[1]);
                selectedDay = Integer.parseInt(dateParts[2]);
            }

            if(pos!=-1)
                tv_preferredFoot.setSelection(pos);
            else
                tv_preferredFoot.setSelection(0);

            if(playerFromDB.getNumber()!=-1)
                tv_number.setText(Integer.toString(playerFromDB.getNumber()));
            else
                tv_number.setText("");


            PlayerPosition ppToSearchAllPositionsFromThisPlayer = new PlayerPosition(player,null,-1);
            List<PlayerPosition> ppFoundFromThisPlayer = null;
            try {
                ppFoundFromThisPlayer = playerPosition_dao.getByCriteria(ppToSearchAllPositionsFromThisPlayer);
            } catch (GenericDAOException e) {
                e.printStackTrace();
            }

            for(PlayerPosition pp : ppFoundFromThisPlayer){
                positionsAll.remove(pp.getPosition().toString());
            }

            ArrayList<String> forAdapter = new ArrayList<>();
            playerPositionsActualsString = new ArrayList<>();

            positionsAvail = new ArrayList<>();
            for(String s : positionsAll)
                positionsAvail.add(s);

            for(PlayerPosition pp : ppFoundFromThisPlayer){
                playerPositionsActuals.add(pp);
                if(pp.getPosition()!=null){
                    if(positionsAvail.contains(pp.getPosition().getName())){
                        positionsAvail.remove(pp.getPosition());
                    }
                }
                forAdapter.add(pp.toString());
            }

            if(forAdapter.size()>0) {
                tv_positionsList.setEnabled(true);
                text_positions.setEnabled(true);
                text_positions.setText("Positions");

                ArrayAdapter<String> adapterP = new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1, forAdapter);
                tv_positionsList.setAdapter(adapterP);
            } else {
                tv_positionsList.setEnabled(false);
                text_positions.setEnabled(false);
                text_positions.setText("");
            }
        }

        tv_nickname.addTextChangedListener(new MyTextWatcher(tv_nickname));
        tv_name.addTextChangedListener(new MyTextWatcher(tv_name));
        tv_height.addTextChangedListener(new MyTextWatcher(tv_height));
        tv_weight.addTextChangedListener(new MyTextWatcher(tv_weight));
        tv_address.addTextChangedListener(new MyTextWatcher(tv_address));
        tv_email.addTextChangedListener(new MyTextWatcher(tv_email));
        tv_number.addTextChangedListener(new MyTextWatcher(tv_number));

        inputLayoutNickname = (TextInputLayout) findViewById(R.id.inputLayoutNickname);
        inputLayoutName = (TextInputLayout) findViewById(R.id.inputLayoutName);
        inputLayoutHeight = (TextInputLayout) findViewById(R.id.inputLayoutHeight);
        inputLayoutWeight = (TextInputLayout) findViewById(R.id.inputLayoutWeight);
        inputLayoutAddress = (TextInputLayout) findViewById(R.id.inputLayoutAddress);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.inputLayoutEmail);
        inputLayoutNumber = (TextInputLayout) findViewById(R.id.inputLayoutNumber);

        Button btnPositions = (Button) findViewById(R.id.btnPositionOpen);// on click of button display the dialog
        btnPositions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
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
            case R.id.Edit:
                boolean okUntilNow = true;
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

                String nickname = tv_nickname.getText().toString();
                String name = tv_name.getText().toString();
                String nationality = tv_nationality.getSelectedItem()!=null ? tv_nationality.getSelectedItem().toString() : "TUGA";
                String maritalStatus = "";
                if(tv_maritalStatus.getSelectedItem()!=null)
                    maritalStatus = tv_maritalStatus.getSelectedItem().toString();
                String birthday = selectedYear+"-"+selectedMonth+"-"+selectedDay;
                int height = -1;
                try {
                    height = Integer.parseInt(tv_height.getText().toString());
                } catch (NumberFormatException ex){
                    okUntilNow = false;
                }
                float weight = -1;
                try {
                    weight = Float.parseFloat(tv_weight.getText().toString());
                } catch (NumberFormatException ex){
                    okUntilNow = false;
                }
                String address = tv_address.getText().toString();
                String gender = "";
                if(tv_gender.getSelectedItem()!=null)
                    gender = tv_gender.getSelectedItem().toString();
                String email = tv_email.getText().toString();
                String preferredFoot = "";
                if(tv_preferredFoot.getSelectedItem()!=null)
                    preferredFoot = tv_preferredFoot.getSelectedItem().toString();
                int number = -1;
                try {
                    number = Integer.parseInt(tv_number.getText().toString());
                } catch (NumberFormatException ex){
                    okUntilNow = false;
                }
                //ups vai estar a imagem em bitmap ou o path para ela?
                //String photo = tv_photo.get
                String photo="";
                //String positionStr = (String) tv_position.getSelectedItem();

                boolean ok = false;
                if (validateName())
                    if (validateNickname())
                        if (validateEmail())
                            if (validateHeight())
                                if (validateWeight())
                                    if (validateAddress())
                                        if (validateNumber())
                                            ok = true;

                /*
                if(!okUntilNow){
                    Intent intent = new Intent();
                    setResult(2, intent);
                    finish();
                    return false;
                }*/

                if (!ok) {
                    //Intent intent = new Intent();
                    //setResult(2, intent);
                    //finish();
                    return false;
                }

                player=new Player(playerID,nickname,name,nationality,maritalStatus,birthday,height,weight,address,gender,photo,email,preferredFoot,number,null,playerPositionsActuals);
                boolean corrected = false;
                //insert/update database
                try {
                    if(playerID > 0){
                        player_dao.update(player);
                        for(PlayerPosition pp : playerPositionsActuals){
                            try {
                                long x = playerPosition_dao.insert(pp);
                                System.out.println(x);
                            } catch (GenericDAOException e) {
                                e.printStackTrace();
                            }
                        }
                        corrected = true;
                    } else {
                        player_dao.insert(player);
                        for(PlayerPosition pp : playerPositionsActuals) {
                            try {
                                playerPosition_dao.insert(pp);
                            } catch (GenericDAOException e) {
                                e.printStackTrace();
                            }
                        }
                        corrected = true;
                    }
                }catch (GenericDAOException ex){
                    System.err.println(studentcompany.sportgest.Players.Player_Activity_Create.class.getName() + " [WARNING] " + ex.toString());
                    Logger.getLogger(studentcompany.sportgest.Players.Player_Activity_Create.class.getName()).log(Level.WARNING, null, ex);
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
                case R.id.inputLayoutNickname:
                    validateNickname();
                    break;
                case R.id.inputLayoutName:
                    validateName();
                    break;
                case R.id.inputLayoutHeight:
                    validateHeight();
                    break;
                case R.id.inputLayoutWeight:
                    validateWeight();
                    break;
                case R.id.inputLayoutAddress:
                    validateAddress();
                    break;
                case R.id.inputLayoutEmail:
                    validateEmail();
                    break;
                case R.id.inputLayoutNumber:
                    validateNumber();
                    break;
            }
        }
    }

    private boolean validateNickname() {
        String pw = tv_nickname.getText().toString().trim();
        if (pw.isEmpty() || pw.length() < 5) {
            inputLayoutNickname.setError(getString(R.string.err_nickname_short));
            return false;
        }
        inputLayoutNickname.setErrorEnabled(false);
        return true;
    }

    private boolean validateName() {
        String pw = tv_name.getText().toString().trim();
        if (pw.isEmpty() || pw.length() < 6) {
            inputLayoutName.setError(getString(R.string.err_name_short));
            return false;
        }
        inputLayoutName.setErrorEnabled(false);
        return true;
    }

    private boolean validateHeight() {
        String pw = tv_height.getText().toString().trim();
        if(!pw.matches("\\d+(\\.\\d+)?")){
            inputLayoutNumber.setError(getString(R.string.err_number));
            return false;
        }
        if (pw.isEmpty() || (pw.length() > 1 && pw.length()<4)) {
            int hg = -1;
            try {
                hg = Integer.parseInt(pw);
            } catch (NumberFormatException e){
                inputLayoutHeight.setError(getString(R.string.err_height_invalid));
                return false;
            }
            if(!(hg<200 && hg>0)){
                inputLayoutHeight.setError(getString(R.string.err_height_invalid));
                //requestFocus(inputLayoutPassword);
                return false;
            }
        }
        inputLayoutHeight.setErrorEnabled(false);
        return true;
    }

    private boolean validateWeight() {
        String pw = tv_weight.getText().toString().trim();
        if(!pw.matches("\\d+(\\.\\d+)?")){
            inputLayoutNumber.setError(getString(R.string.err_number));
            return false;
        }
        if (pw.isEmpty() || (pw.length() > 1 && pw.length()<5)) {
            float wg = -1f;
            try{
                wg = Float.parseFloat(pw);
            } catch (NumberFormatException e){
                inputLayoutWeight.setError(getString(R.string.err_weight_invalid));
                return false;
            }
            if(!(wg>0)){
                inputLayoutWeight.setError(getString(R.string.err_weight_invalid));
                return false;
            }
        }
        inputLayoutWeight.setErrorEnabled(false);
        return true;
    }

    private boolean validateAddress() {
        String pw = tv_address.getText().toString().trim();
        if (pw.isEmpty() || pw.length() < 5) {
            inputLayoutAddress.setError(getString(R.string.err_address_short));
            return false;
        }
        inputLayoutAddress.setErrorEnabled(false);
        return true;
    }

    private boolean validateEmail() {

        String pw = tv_email.getText().toString().trim();
        if (pw.isEmpty() || pw.length() < 5) {
            Pattern VALID_EMAIL_ADDRESS_REGEX =
                    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(pw);
            if(!matcher.find()){
                inputLayoutEmail.setError(getString(R.string.error_invalid_email));
                return false;
            }
        }
        inputLayoutEmail.setErrorEnabled(false);
        return true;
    }

    private boolean validateNumber() {
        String pw = tv_number.getText().toString().trim();
        if(!pw.matches("\\d+(\\.\\d+)?")){
            inputLayoutNumber.setError(getString(R.string.err_number));
            return false;
        }

        if (pw.isEmpty() || (pw.length() > 1 && pw.length()<4)) {
            int nb = -1;
            try{
                nb = Integer.parseInt(pw);
            } catch (NumberFormatException e){
                inputLayoutNumber.setError(getString(R.string.err_number));
                return false;
            }
            if(!(nb<100 && nb>0)){
                inputLayoutNumber.setError(getString(R.string.err_number));
                return false;
            }
        }
        inputLayoutNumber.setErrorEnabled(false);
        return true;
    }

    @Override
    public void onStop(){
        super.onStop();

        setResult(0);
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
                            txtDate.setText(Integer.toString(year)+"-"+Integer.toString(monthOfYear+1)+"-"+Integer.toString(dayOfMonth));
                            selectedDay = dayOfMonth;
                            selectedMonth = monthOfYear+1;
                            selectedYear = year;

                        }
                    }, mYear, mMonth, mDay);
            dpd.show();
        }
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

        System.out.println("changed");

    }

    public void show()
    {
        final Dialog d = new Dialog(Player_Activity_Edit.this);
        d.setTitle("Position Chooser");
        d.setContentView(R.layout.position_chose);
        Button bSet= (Button) d.findViewById(R.id.buttonSet);
        Button bCancel = (Button) d.findViewById(R.id.buttonCancel);
        np = (NumberPicker) d.findViewById(R.id.valueToPositions);
        selectedPositions = (Spinner) d.findViewById(R.id.spinnerPositionsToAdd);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, positionsAvail);
        selectedPositions.setAdapter(adapter);

        np.setMaxValue(10); // max value 100
        np.setMinValue(0);   // min value 0
        np.setWrapSelectorWheel(false);
        //np.setOnValueChangedListener(this);

        bSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String positionToAdd = (String) selectedPositions.getSelectedItem();
                int valueToPosition = np.getValue();
                List<Position> positionFromDB = null;
                try {
                    positionFromDB = position_dao.getByCriteria(new Position(positionToAdd));
                } catch (GenericDAOException e) {
                    e.printStackTrace();
                }
                if (positionFromDB.size() > 0) {
                    PlayerPosition pp = new PlayerPosition(player, positionFromDB.get(0), valueToPosition);
                    playerPositionsActuals.add(pp);
                    playerPositionsActualsString.add(0,pp.toString());
                    positionsAvail.remove(positionToAdd);

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                            R.layout.player_listview_for_positions, playerPositionsActualsString);
                    tv_positionsList.setEnabled(true);
                    text_positions.setEnabled(true);
                    text_positions.setText("Positions");
                    tv_positionsList.setAdapter(adapter);

                }

                d.dismiss();
            }
        });

        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();

    }

    public void removeSelected(){
        if (selectedPosition != null) {
            String[] compositeOfPosition = selectedPosition.split(" - ");
            positionsAvail.add(compositeOfPosition[0]);
            List<Position> position = null;
            try {
                position = position_dao.getByCriteria(new Position(compositeOfPosition[0]));
            } catch (GenericDAOException e) {
                e.printStackTrace();
            }
            if (position.size() > 0) {
                PlayerPosition ppToRemove = new PlayerPosition(player, position.get(0), Integer.parseInt(compositeOfPosition[1]));
                try {
                    playerPosition_dao.delete(ppToRemove);
                } catch (GenericDAOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}