package studentcompany.sportgest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import studentcompany.sportgest.daos.EVENT_CATEGORYDAO;

public class DisplayEventCategoryActivity extends AppCompatActivity {

    //DAOs
    private EVENT_CATEGORYDAO event_categorydao;

    TextView category;
    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_event_category);
        category = (TextView) findViewById(R.id.event_category_editText);

        event_categorydao = new EVENT_CATEGORYDAO(this);

        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            int Value = extras.getInt("id");

            if(Value>0){
                //means this is the view part not the add contact part.
                Cursor rs = event_categorydao.getData(Value);
                id_To_Update = Value;
                rs.moveToFirst();

                String eventCategory = rs.getString(rs.getColumnIndex(EVENT_CATEGORYDAO.EVENT_CATEGORY_COLUMN_CATEGORY));

                if (!rs.isClosed())
                {
                    rs.close();
                }
                Button b = (Button)findViewById(R.id.event_category_button);
                b.setVisibility(View.INVISIBLE);

                category.setText(eventCategory);
                category.setFocusable(false);
                category.setClickable(false);
            }
        }

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.display_event_category_toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Bundle extras = getIntent().getExtras();

        if(extras !=null)
        {
            int Value = extras.getInt("id");
            if(Value>0){
                getMenuInflater().inflate(R.menu.menu_display_event_category, menu);
            }

            else{
                getMenuInflater().inflate(R.menu.menu_list_event_category, menu);
            }
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);
        switch(item.getItemId())
        {
            case R.id.Edit_Event_Category:
                Button b = (Button)findViewById(R.id.event_category_button);
                b.setVisibility(View.VISIBLE);
                category.setEnabled(true);
                category.setFocusableInTouchMode(true);
                category.setClickable(true);

                return true;
            case R.id.Delete_Event_Category:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.delete_confirmation)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                event_categorydao.deleteEventCategory(id_To_Update);
                                Toast.makeText(getApplicationContext(), R.string.delete_sucessful, Toast.LENGTH_SHORT).show();
                                finish();
                                //Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                //startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                AlertDialog d = builder.create();
                d.setTitle(R.string.are_you_sure);
                d.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void run(View view)
    {
        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            int Value = extras.getInt("id");
            if(Value>0){
                if(event_categorydao.updateEventCategory(id_To_Update, category.getText().toString() )){
                    Toast.makeText(getApplicationContext(), R.string.updated, Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), R.string.not_updated, Toast.LENGTH_SHORT).show();
                }
            }
            else{
                if(event_categorydao.insertEventCategory(category.getText().toString())){
                    Toast.makeText(getApplicationContext(), R.string.done, Toast.LENGTH_SHORT).show();
                }

                else{
                    Toast.makeText(getApplicationContext(), R.string.not_done, Toast.LENGTH_SHORT).show();
                }
            }
            this.finish();
        }
    }
}
