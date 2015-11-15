package studentcompany.sportgest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import studentcompany.sportgest.daos.EVENT_CATEGORYDAO;

public class ListEventCategoryActivity extends AppCompatActivity {
    //Interface
    private ListView listView;

    //DAOs
    private EVENT_CATEGORYDAO event_categorydao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_event_category);

        event_categorydao = new EVENT_CATEGORYDAO(this);
        ArrayList array_list = event_categorydao.getAllEventCategories();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, array_list);

        listView = (ListView)findViewById(R.id.event_category_ListView);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
                // TODO Auto-generated method stub
                int id_To_Search = arg2 + 1;

                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", id_To_Search);

                Intent intent = new Intent(getApplicationContext(),DisplayEventCategoryActivity.class);

                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.list_event_category_toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onResume() {//update list
        super.onResume();  // Always call the superclass method first

        ArrayList array_list = event_categorydao.getAllEventCategories();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, array_list);
        listView = (ListView)findViewById(R.id.event_category_ListView);
        listView.setAdapter(arrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_event_category, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);

        switch(item.getItemId())
        {
            case R.id.Add_Event_Category:Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", 0);

                Intent intent = new Intent(getApplicationContext(),DisplayEventCategoryActivity.class);
                intent.putExtras(dataBundle);

                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
