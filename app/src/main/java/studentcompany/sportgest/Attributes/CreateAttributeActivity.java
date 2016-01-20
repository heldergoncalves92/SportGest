package studentcompany.sportgest.Attributes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Attribute_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Attribute;
import studentcompany.sportgest.domains.Role;

public class CreateAttributeActivity extends AppCompatActivity {

    private Attribute_DAO dao;

    private TextInputLayout inputLayoutName;
    private EditText inputName;
    private Spinner spinner;

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
    }

    private void submitForm() {
        if (!validateName()) {
            return;
        }

        try {
            dao.insert(new Attribute(-1, spinner.getSelectedItem().toString(), inputName.getText().toString(), 0));//TODO define convention for field "deleted"
            Toast.makeText(getApplicationContext(), "Attribute added!", Toast.LENGTH_LONG).show();
            goToAttributeListActivity();
        } catch (GenericDAOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Could not add the attribute. Try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateName() {
        if (inputName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError("Enter the name of the attribute");
            requestFocus(inputName);
            return false;
        }  else {
            try {
                if (dao.exists(new Attribute(-1, null, inputName.getText().toString(), -1))) {
                    inputLayoutName.setError("There is already an attribute with this name");
                    requestFocus(inputName);
                    return false;
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
        Intent intent = new Intent(this, AttributeListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_crud, menu);
        menu.findItem(R.id.Add).setVisible(false);
        menu.findItem(R.id.Edit).setVisible(false);
        menu.findItem(R.id.Delete).setVisible(false);
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