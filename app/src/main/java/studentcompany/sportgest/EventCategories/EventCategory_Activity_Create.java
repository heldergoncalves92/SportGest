package studentcompany.sportgest.EventCategories;

import android.content.Intent;
import android.graphics.Color;
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

import com.github.danielnilsson9.colorpickerview.dialog.ColorPickerDialogFragment;

import java.util.logging.Level;
import java.util.logging.Logger;


import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Event_Category_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.EventCategory;

public class EventCategory_Activity_Create extends AppCompatActivity {

    //DAOs
    private Event_Category_DAO ec_dao;

    EventCategory ec = null;
    long ecID = -1;

    int color = Color.parseColor("#ba68c8");

    private EditText tv_category;
    private TextInputLayout inputLayoutCategory;
    private Button changecolor;
    private CheckBox checkBox_hastimestamp;

    private static final int CREATE_TAG = 20;


    //private ExamplePreferenceFragment mPreferenceFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_category_activity_create);

        ec_dao = new Event_Category_DAO(this);

        tv_category = (EditText) findViewById(R.id.category);

        tv_category.addTextChangedListener(new MyTextWatcher(tv_category));

        inputLayoutCategory = (TextInputLayout) findViewById(R.id.inputLayoutCategory);

        checkBox_hastimestamp = (CheckBox) findViewById(R.id.checkbox_event_create_hasTime);

        changecolor = (Button) findViewById(R.id.btnChangeColorEventCatCreate);
        changecolor.setBackgroundColor(color);
        changecolor.setTextColor(color);

        changecolor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent2 = new Intent(getApplicationContext(), Color_Picker_Activity.class);
                intent2.putExtra("initialColor", color);
                startActivityForResult(intent2, CREATE_TAG);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_category, menu);
        MenuItem addItem = menu.findItem(R.id.Event_Category_Add);
        addItem.setVisible(true);

        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);

        switch(item.getItemId())
        {
            //add action
            case R.id.Event_Category_Add:
                boolean okUntilNow = true;
                tv_category = (EditText) findViewById(R.id.category);

                String categoryStr = tv_category.getText().toString();

                boolean ok = false;
                if (validateCategory())
                    ok = true;

                if (!ok) {
                    //Intent intent = new Intent();
                    //setResult(1, intent);
                    //finish();
                    return false;
                }

                boolean hast = checkBox_hastimestamp.isChecked();

                ec = new EventCategory(categoryStr,color,hast);

                boolean corrected = false;
                //insert
                try {
                    ecID = ec_dao.insert(ec);
                    if(ecID >0)
                        corrected = true;
                } catch (GenericDAOException ex) {
                    System.err.println(EventCategory_Activity_Create.class.getName() + " [WARNING] " + ex.toString());
                    Logger.getLogger(EventCategory_Activity_Create.class.getName()).log(Level.WARNING, null, ex);
                }

                Intent intent = new Intent();
                intent.putExtra("id", ecID);
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
                case R.id.inputLayoutCategory:
                    validateCategory();
                    break;
            }
        }
    }

    private boolean validateCategory() {
        String catname = tv_category.getText().toString().trim();
        if (catname.isEmpty() || catname.length() < 3) {
            inputLayoutCategory.setError(getString(R.string.err_event_category_short));
            //requestFocus(inputLayoutPassword);
            return false;
        }
        inputLayoutCategory.setErrorEnabled(false);
        return true;
    }








    private static String colorToHexString(int color) {
        return String.format("#%06X", 0xFFFFFFFF & color);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CREATE_TAG) {
                try {

                    String hex = colorToHexString(resultCode);
                    color = Color.parseColor(hex);
                    changecolor.setBackgroundColor(color);
                    changecolor.setTextColor(color);

                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }
}
