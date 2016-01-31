package studentcompany.sportgest.Attributes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Attribute_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Attribute;

public class Attribute_Activity_Create extends AppCompatActivity {

    private Attribute_DAO dao;

    private TextInputLayout inputLayoutName;
    private EditText inputName;
    private Spinner spinner;

    private enum Mode {
        CREATE, UPDATE;
    }
    private Mode mode;

    private Attribute attribute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_attribute);

        dao = new Attribute_DAO(this);

        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_attribute_name);
        inputName = (EditText) findViewById(R.id.input_attribute_name);
        inputName.addTextChangedListener(new MyTextWatcher(inputName));

        spinner = (Spinner) findViewById(R.id.spinner);
        List<String> list = new ArrayList<String>();
        list.add(Attribute.QUALITATIVE);
        list.add(Attribute.QUANTITATIVE);
        list.add(Attribute.RATIO);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            mode = Mode.UPDATE;
            setTitle(R.string.app_updateAttribute);
            long attributeID = extras.getLong(Attribute_DAO.TABLE_NAME + Attribute_DAO.COLUMN_ID);
            try {
                attribute = dao.getById(attributeID);
                inputName.setText(attribute.getName());
                spinner.setSelection(list.indexOf(attribute.getType()));
            } catch (GenericDAOException e) {
                e.printStackTrace();
            }
        }
        else {
            mode = Mode.CREATE;
            setTitle(R.string.app_newAttribute);
        }
    }

    private void submitForm() {
        if (!validateName()) {
            return;
        }

        try {
            String name = inputName.getText().toString(),
                    type = spinner.getSelectedItem().toString();
            if (mode == Mode.CREATE){
                dao.insert(new Attribute(-1, type, name, 0));
            }
            else {
                attribute.setName(name);
                attribute.setType(type);
                dao.update(attribute);
            }
            Toast.makeText(getApplicationContext(), "Attribute " + (mode == Mode.CREATE ? "added" : "updated") + "!", Toast.LENGTH_LONG).show();
            goToAttributeListActivity();
        } catch (GenericDAOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Could not " + (mode == Mode.CREATE ? "add" : "update") + " the attribute. Try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateName() {
        String name = inputName.getText().toString().trim();
        if (name.isEmpty()) {
            inputLayoutName.setError("Enter the name of the attribute");
            requestFocus(inputName);
            return false;
        }  else {
            try {
                if (dao.exists(new Attribute(-1, null, inputName.getText().toString(), -1))) {
                    if(attribute == null || !attribute.getName().equals(name)) {
                        inputLayoutName.setError("There is already an attribute with this name");
                        requestFocus(inputName);
                        return false;
                    }
                }
            } catch (GenericDAOException e) {
                e.printStackTrace();
            }
        }

        inputLayoutName.setErrorEnabled(false);
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
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
            validateName();
        }
    }

    private void goToAttributeListActivity(){
        Intent intent = new Intent(this, Attribute_Activity_List.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_crud, menu);
        menu.findItem(R.id.Add).setVisible(false);
        menu.findItem(R.id.Edit).setVisible(false);
        menu.findItem(R.id.Delete).setVisible(false);
        menu.findItem(R.id.Forward).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        submitForm();
        return true;
    }

    @Override
    public void onBackPressed() {
        goToAttributeListActivity();
    }
}