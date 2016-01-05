package studentcompany.sportgest.EventCategories;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.logging.Level;
import java.util.logging.Logger;

import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Event_Category_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.EventCategory;

public class EventCategory_Activity_Edit extends AppCompatActivity {

    //DAOs
    private Event_Category_DAO event_category_dao;

    EventCategory eventCategory = null;
    int eventID = -1;

    private EditText tv_category;
    private TextInputLayout inputLayoutCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_category_activity_edit);

        Bundle b = getIntent().getExtras();
        if(b!=null){
            eventID = (int) (b.getLong("id")+0);
        }

         tv_category = (EditText) findViewById(R.id.category);

        EventCategory eventFromDB=null;
        event_category_dao = new Event_Category_DAO(this);

        try {
            eventFromDB = event_category_dao.getById(eventID);
        } catch (GenericDAOException e) {
            e.printStackTrace();
        }
        if (eventFromDB!=null) {
            if (eventFromDB.getName() != null)
                tv_category.setText(eventFromDB.getName());
            else
                tv_category.setText("");
        }

        tv_category.addTextChangedListener(new MyTextWatcher(tv_category));
        inputLayoutCategory = (TextInputLayout) findViewById(R.id.inputLayoutCategory);
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

                tv_category = (EditText) findViewById(R.id.category);

                String category = tv_category.getText().toString();

                boolean ok = false;
                if (validateCategory())
                    ok = true;

                if (!ok)
                    return false;

                eventCategory=new EventCategory(0,category);

                try {
                    if(eventID > 0){
                        event_category_dao.update(eventCategory);
                    } else {
                        event_category_dao.insert(eventCategory);
                    }
                }catch (GenericDAOException ex){
                    System.err.println(EventCategory_Activity_Edit.class.getName() + " [WARNING] " + ex.toString());
                    Logger.getLogger(EventCategory_Activity_Edit.class.getName()).log(Level.WARNING, null, ex);
                }

                Intent intent = new Intent();
                setResult(1,intent);
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
        if (pw.isEmpty() || pw.length() < 5) {
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
}