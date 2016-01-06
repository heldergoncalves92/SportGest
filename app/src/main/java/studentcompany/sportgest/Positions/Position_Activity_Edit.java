package studentcompany.sportgest.Positions;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Position_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Position;

public class Position_Activity_Edit extends AppCompatActivity {

    //DAOs
    private Position_DAO position_dao;

    Position position = null;
    int positionID = -1;

    private EditText tv_position;
    private TextInputLayout inputLayoutPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.position_activity_edit);

        Bundle b = getIntent().getExtras();
        if(b!=null){
            positionID = (int) (b.getLong("id")+0);
        }

         tv_position = (EditText) findViewById(R.id.position);

        Position positionFromDB = null;
        position_dao = new Position_DAO(this);

        try {
             positionFromDB = position_dao.getById(positionID);
        } catch (GenericDAOException e) {
            e.printStackTrace();
        }
        if (positionFromDB!=null) {
            if (positionFromDB.getName() != null)
                tv_position.setText(positionFromDB.getName());
            else
                tv_position.setText("");
        }

        tv_position.addTextChangedListener(new MyTextWatcher(tv_position));

        inputLayoutPosition = (TextInputLayout) findViewById(R.id.inputLayoutPosition);

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
                boolean okUntilNow = true;
                tv_position = (EditText) findViewById(R.id.position);
                String positionStr = tv_position.getText().toString();

                boolean ok = false;
                if (validatePosition())
                    ok=true;

                if (!ok) {
                    //Intent intent = new Intent();
                    //setResult(2, intent);
                    //finish();
                    return false;
                }

                position=new Position(positionID,positionStr);
                boolean corrected = false;
                //insert/update database
                try {
                    if(positionID > 0){
                        position_dao.update(position);
                        corrected = true;
                    } else {
                        position_dao.insert(position);
                        corrected = true;
                    }
                }catch (GenericDAOException ex){
                    System.err.println(Position_Activity_Edit.class.getName() + " [WARNING] " + ex.toString());
                    Logger.getLogger(Position_Activity_Edit.class.getName()).log(Level.WARNING, null, ex);
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
                case R.id.inputLayoutPosition:
                    validatePosition();
                    break;
            }
        }
    }

    private boolean validatePosition() {
        String pw = tv_position.getText().toString().trim();
        if (pw.isEmpty() || pw.length() < 3) {
            inputLayoutPosition.setError(getString(R.string.err_position_short));
            //requestFocus(inputLayoutPassword);
            return false;
        }
        inputLayoutPosition.setErrorEnabled(false);
        return true;
    }

    @Override
    public void onStop(){
        super.onStop();

        setResult(0);
    }
}