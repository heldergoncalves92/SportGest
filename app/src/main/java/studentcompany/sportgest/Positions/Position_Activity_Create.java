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

public class Position_Activity_Create extends AppCompatActivity{

    //DAOs
    private Position_DAO position_dao;

    Position position = null;
    long positionID = -1;

    private final int CREATE_TAG = 20;
    private final String FILENAME_POSITIONS = "";

    private EditText tv_position;
    private TextInputLayout inputLayoutPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.position_activity_create);

        position_dao = new Position_DAO(this);

        tv_position = (EditText) findViewById(R.id.position);

        tv_position.addTextChangedListener(new MyTextWatcher(tv_position));

        inputLayoutPosition = (TextInputLayout) findViewById(R.id.inputLayoutPosition);
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
                boolean okUntilNow = true;
                 tv_position = (EditText) findViewById(R.id.position);

                String positionStr = tv_position.getText().toString();

                boolean ok = false;
                if (validatePosition())
                    ok = true;

                if (!ok) {
                    Intent intent = new Intent();
                    setResult(2, intent);
                    finish();
                    return false;
                }

                position = new Position(positionStr);

                boolean corrected = false;
                //insert
                try {
                    positionID = position_dao.insert(position);
                    if(positionID>0)
                        corrected = true;
                } catch (GenericDAOException ex) {
                    System.err.println(Position_Activity_Create.class.getName() + " [WARNING] " + ex.toString());
                    Logger.getLogger(Position_Activity_Create.class.getName()).log(Level.WARNING, null, ex);
                }

                Intent intent = new Intent();
                intent.putExtra("id", positionID);
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
}
