package studentcompany.sportgest.Players;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
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
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
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
import studentcompany.sportgest.daos.Team_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Player;
import studentcompany.sportgest.domains.PlayerPosition;
import studentcompany.sportgest.domains.Position;
import studentcompany.sportgest.domains.Team;

public class Player_Activity_Edit extends AppCompatActivity implements View.OnClickListener, NumberPicker.OnValueChangeListener {

    //DAOs
    private Player_DAO player_dao;
    private Position_DAO position_dao;
    private Player_Position_DAO playerPosition_dao;
    private Team_DAO team_dao;

    private int EDIT_POSITION = 20;

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

    private Spinner spinnerTeam;
    private ArrayAdapter<Team> dataAdaptert;
    private List<Team> listTeams;

    ImageView viewImage;
    Button bt;
    Bitmap bitmap = null;

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

        bt = (Button)findViewById(R.id.btnSelectPhoto);
        viewImage=(ImageView)findViewById(R.id.viewImage);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

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

        Player playerFromDB=null;
        player_dao = new Player_DAO(this);
        position_dao = new Position_DAO(this);
        playerPosition_dao = new Player_Position_DAO(this);

        try {
             playerFromDB = player_dao.getById(playerID);
             player = playerFromDB;
        } catch (GenericDAOException e) {
            e.printStackTrace();
        }


        spinnerTeam = (Spinner) findViewById(R.id.input_create_player_team_spinner);

        try {
            team_dao = new Team_DAO(getApplicationContext());
            listTeams = team_dao.getAll();
            listTeams.add(0,new Team(-1,getResources().getString(R.string.no_team)));
            dataAdaptert = new ArrayAdapter<Team>(this,android.R.layout.simple_list_item_1, listTeams);
            spinnerTeam.setAdapter(dataAdaptert);

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

            String pho = player.getPhoto();
            if(pho == null)
            {
                viewImage.setImageDrawable(getDefaultBitmap());
            }
            else
                viewImage.setImageBitmap(getImageBitmap(this,pho));
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

        tv_positionButton = (Button)findViewById(R.id.btnPositionOpen);
        tv_positionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), EditPosition_Activity.class);
                intent.putExtra("id", playerID);
                startActivityForResult(intent, EDIT_POSITION);
            }
        });
    }

    private Drawable getDefaultBitmap() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return getResources().getDrawable(R.drawable.lego_face, this.getTheme());
        } else {
            return getResources().getDrawable(R.drawable.lego_face);
        }
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
                //Spinner tv_position = (Spinner) findViewById(R.id.position);

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

                Team selected_team = (Team)spinnerTeam.getSelectedItem();

                if(selected_team == null || selected_team.getId() <= 0) {
                    Toast.makeText(getApplicationContext(), "Selected team is not valid!!", Toast.LENGTH_SHORT).show();
                    return true;
                }

                if (!ok) {
                    //Intent intent = new Intent();
                    //setResult(2, intent);
                    //finish();
                    return false;
                }

                photo = bitmap == null ? null: nickname.concat(".jpg");

                player=new Player(playerID,nickname,name,nationality,maritalStatus,birthday,height,weight,address,gender,photo,email,preferredFoot,number,selected_team,null);
                boolean corrected = false;
                //insert/update database
                try {
                    if(playerID > 0){
                        player_dao.update(player);
                        corrected = true;
                    } else {
                        player_dao.insert(player);
                        corrected = true;
                    }
                }catch (GenericDAOException ex){
                    System.err.println(studentcompany.sportgest.Players.Player_Activity_Create.class.getName() + " [WARNING] " + ex.toString());
                    Logger.getLogger(studentcompany.sportgest.Players.Player_Activity_Create.class.getName()).log(Level.WARNING, null, ex);
                }

                if(bitmap!=null)
                    try {
                        saveToInternalSorage(bitmap, player.getPhoto());
                    } catch (IOException e) {
                        e.printStackTrace();
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
        if(inputLayoutNickname==null)
            inputLayoutNickname = (TextInputLayout) findViewById(R.id.inputLayoutNickname);
        String pw = tv_nickname.getText().toString().trim();
        if (pw.isEmpty() || pw.length() < 5) {
            inputLayoutNickname.setError(getString(R.string.err_nickname_short));
            return false;
        }
        inputLayoutNickname.setErrorEnabled(false);
        return true;
    }

    private boolean validateName() {
        if(inputLayoutName==null)
            inputLayoutName = (TextInputLayout) findViewById(R.id.inputLayoutName);
        String pw = tv_name.getText().toString().trim();
        if (pw.isEmpty() || pw.length() < 6) {
            inputLayoutName.setError(getString(R.string.err_name_short));
            return false;
        }
        inputLayoutName.setErrorEnabled(false);
        return true;
    }

    private boolean validateHeight() {
        if(inputLayoutHeight==null)
            inputLayoutHeight = (TextInputLayout) findViewById(R.id.inputLayoutHeight);
        String pw = tv_height.getText().toString().trim();
        if(!pw.matches("\\d+(\\.\\d+)?")){
            inputLayoutNumber.setError(getString(R.string.err_number));
            return false;
        }
        if (pw.isEmpty() || (pw.length() > 1 && pw.length()<4)) {
            int hg = -1;
            if (isNumericInt(pw)) {
                hg = Integer.parseInt(pw);
            } else {
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
        if(inputLayoutWeight==null)
            inputLayoutWeight = (TextInputLayout) findViewById(R.id.inputLayoutWeight);
        String pw = tv_weight.getText().toString().trim();
        if(!pw.matches("\\d+(\\.\\d+)?")){
            inputLayoutNumber.setError(getString(R.string.err_number));
            return false;
        }
        if (pw.isEmpty() || (pw.length() > 1 && pw.length()<5)) {
            float wg = -1f;
            if(isNumericFloat(pw)){
                wg = Float.parseFloat(pw);
            } else {
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
        if(inputLayoutAddress==null)
            inputLayoutAddress = (TextInputLayout) findViewById(R.id.inputLayoutAddress);
        String pw = tv_address.getText().toString().trim();
        if (pw.isEmpty() || pw.length() < 5) {
            inputLayoutAddress.setError(getString(R.string.err_address_short));
            return false;
        }
        inputLayoutAddress.setErrorEnabled(false);
        return true;
    }

    private boolean validateEmail() {
        if(inputLayoutEmail==null)
            inputLayoutEmail = (TextInputLayout) findViewById(R.id.inputLayoutEmail);
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
        if(inputLayoutNumber==null)
            inputLayoutNumber = (TextInputLayout) findViewById(R.id.inputLayoutNumber);
        String pw = tv_number.getText().toString().trim();
        if(!pw.matches("\\d+(\\.\\d+)?")){
            inputLayoutNumber.setError(getString(R.string.err_number));
            return false;
        }

        if (pw.isEmpty() || (pw.length() > 1 && pw.length()<4)) {
            int nb = -1;
            if(isNumericInt(pw)){
                nb = Integer.parseInt(pw);
            } else {
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

    public static boolean isNumericFloat(String str)
    {
        try
        {
            float d = Float.parseFloat(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                try {

                    File f = new File(Environment.getExternalStorageDirectory().toString(),"temp.jpg");
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);

                    if(bitmap==null)
                        Toast.makeText(getApplicationContext(), R.string.error_occured, Toast.LENGTH_SHORT).show();
                    else
                        viewImage.setImageBitmap(bitmap);
                    f.delete();
                    /*
                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), R.string.error_occured, Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == 2) {
                try {
                    Uri selectedImage = data.getData();
                    String[] filePath = {MediaStore.Images.Media.DATA};
                    Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePath[0]);
                    String picturePath = c.getString(columnIndex);
                    c.close();
                    bitmap = BitmapFactory.decodeFile(picturePath);
                    if (bitmap == null)
                        Toast.makeText(getApplicationContext(), R.string.error_occured, Toast.LENGTH_SHORT).show();
                    else {
                        bitmap = resize(bitmap,200,200);
                        viewImage.setImageBitmap(bitmap);
                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), R.string.error_occured, Toast.LENGTH_SHORT).show();
                }
            } else  if (requestCode == EDIT_POSITION) {
                if(resultCode == 1){
                    Toast.makeText(getApplicationContext(), R.string.updated, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    private String saveToInternalSorage(Bitmap bitmapImage,String name) throws IOException {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File mypath=new File(directory,name);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fos.close();
        }
        return directory.getAbsolutePath();
    }




    private void selectImage() {

        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Set to default" };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add a Photo to the Player");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                try {
                    if (options[item].equals("Take Photo")) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                        startActivityForResult(intent, 1);
                    } else if (options[item].equals("Choose from Gallery")) {
                        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 2);

                    } else if (options[item].equals("Set to default")) {
                        bitmap = null;
                        viewImage.setImageDrawable(getDefaultBitmap());
                        dialog.dismiss();
                    }
                } catch (Exception e) {

                }
            }
        });
        builder.show();
    }





    private Bitmap resize(Bitmap image, int maxWidth, int maxHeight) {
        if (maxHeight > 0 && maxWidth > 0) {
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > 1) {
                finalWidth = (int) ((float)maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float)maxWidth / ratioBitmap);
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            return image;
        } else {
            return image;
        }
    }






    public Bitmap getImageBitmap(Context context,String name){
        //name=name+"."+extension;
        try{
            ContextWrapper cw = new ContextWrapper(this);
            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
            // Create imageDir
            File mypath=new File(directory,name);
            FileInputStream fis = new FileInputStream(mypath);
            Bitmap b = BitmapFactory.decodeStream(fis);
            fis.close();
            return b;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

}