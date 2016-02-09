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

import java.util.logging.Level;
import java.util.logging.Logger;

import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Event_Category_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.EventCategory;

public class EventCategory_Activity_Edit extends AppCompatActivity {

    //DAOs
    private Event_Category_DAO ec_dao;

    int ecID = -1;

    private EditText tv_category;
    private TextInputLayout inputLayoutCategory;
    private Button changecolor;
    private CheckBox checkBox_hastimestamp;

    private EventCategory ec = null;

    int color = 0;


    private static final int EDIT_TAG = 20;
    private static final int EDIT_COLOR_TAG = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_category_activity_edit);

        Bundle b = getIntent().getExtras();
        if(b!=null){
            ecID = (int) (b.getLong("id")+0);
        }

        tv_category = (EditText) findViewById(R.id.category);

        ec = null;
        ec_dao = new Event_Category_DAO(this);

        try {
            ec = ec_dao.getById(ecID);
        } catch (GenericDAOException e) {
            e.printStackTrace();
        }
        if (ec!=null) {
            if (ec.getName() != null)
                tv_category.setText(ec.getName());
            else
                tv_category.setText("");
        }else {
            Intent intent = new Intent();
            setResult(2, intent);
            finish();
        }

        tv_category.addTextChangedListener(new MyTextWatcher(tv_category));

        inputLayoutCategory = (TextInputLayout) findViewById(R.id.inputLayoutCategory);

        checkBox_hastimestamp = (CheckBox) findViewById(R.id.checkbox_event_edit_hasTime);
        checkBox_hastimestamp.setChecked(ec.hasTimestamp());

        changecolor = (Button) findViewById(R.id.btnChangeColorEventCatEdit);
        color = ec.getColor();
        changecolor.setBackgroundColor(color);
        changecolor.setTextColor(color);


        changecolor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent2 = new Intent(getApplicationContext(), Color_Picker_Activity.class);
                intent2.putExtra("initialColor", ec.getColor());
                startActivityForResult(intent2, EDIT_COLOR_TAG);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_category, menu);
        MenuItem editItem = menu.findItem(R.id.Event_Category_Save);
        editItem.setVisible(true);
        editItem = menu.findItem(R.id.Event_Category_Add);
        editItem.setVisible(false);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);

        switch(item.getItemId())
        {
            //add action
            case R.id.Event_Category_Save:
                boolean okUntilNow = true, hast = false;
                tv_category = (EditText) findViewById(R.id.category);
                String categoryStr = tv_category.getText().toString();
                hast = checkBox_hastimestamp.isChecked();

                boolean ok = false;
                if (validateCategory())
                    ok=true;

                if (!ok) {
                    //Intent intent = new Intent();
                    //setResult(2, intent);
                    //finish();
                    return false;
                }

                ec =new EventCategory(ecID,categoryStr,color,hast);
                boolean corrected = false;
                //insert/update database
                try {
                    if(ecID > 0){
                        ec_dao.update(ec);
                        corrected = true;
                    } else {
                        ec_dao.insert(ec);
                        corrected = true;
                    }
                }catch (GenericDAOException ex){
                    System.err.println(EventCategory_Activity_Edit.class.getName() + " [WARNING] " + ex.toString());
                    Logger.getLogger(EventCategory_Activity_Edit.class.getName()).log(Level.WARNING, null, ex);
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
                case R.id.inputLayoutCategory:
                    validateCategory();
                    break;
            }
        }
    }

    private boolean validateCategory() {
        String pw = tv_category.getText().toString().trim();
        if (pw.isEmpty() || pw.length() < 3) {
            inputLayoutCategory.setError(getString(R.string.err_category_short));
            //requestFocus(inputLayoutPassword);
            return false;
        }
        inputLayoutCategory.setErrorEnabled(false);
        return true;
    }

    @Override
    public void onStop(){
        super.onStop();

        setResult(0);
    }


    private static String colorToHexString(int color) {
        return String.format("#%06X", 0xFFFFFFFF & color);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EDIT_COLOR_TAG) {
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