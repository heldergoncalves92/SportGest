package studentcompany.sportgest.Players;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.logging.Level;
import java.util.logging.Logger;

import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Player_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Player;
import studentcompany.sportgest.domains.Position;

public class EditPlayer_Activity extends AppCompatActivity {

    //DAOs
    private Player_DAO player_dao;

    Player player = null;
    int playerID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_player);

        EditText tv_nickname = (EditText) findViewById(R.id.nickname);
        EditText tv_name = (EditText) findViewById(R.id.name);
        Spinner tv_nationality = (Spinner) findViewById(R.id.nationality);
        Spinner tv_maritalStatus = (Spinner) findViewById(R.id.maritalstatus);
        DatePicker tv_birthday = (DatePicker) findViewById(R.id.birthday);
        EditText tv_height = (EditText) findViewById(R.id.height);
        EditText tv_weight = (EditText) findViewById(R.id.weight);
        EditText tv_address = (EditText) findViewById(R.id.address);
        Spinner tv_gender = (Spinner) findViewById(R.id.gender);
        EditText tv_email = (EditText) findViewById(R.id.email);
        Spinner tv_preferredFoot = (Spinner) findViewById(R.id.preferredfoot);
        EditText tv_number = (EditText) findViewById(R.id.number);
        ImageView tv_photo = (ImageView) findViewById(R.id.photo);
        Spinner tv_position = (Spinner) findViewById(R.id.position);

        player_dao = new Player_DAO(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar_crud, menu);
        MenuItem editItem = menu.findItem(R.id.Edit);
        MenuItem delItem = menu.findItem(R.id.Delete);
        MenuItem addItem = menu.findItem(R.id.Add);
        editItem.setVisible(true);
        delItem.setVisible(false);
        addItem.setVisible(true);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);

        switch(item.getItemId())
        {
            //add action
            case R.id.Edit:
                EditText tv_nickname = (EditText) findViewById(R.id.nickname);
                EditText tv_name = (EditText) findViewById(R.id.name);
                Spinner tv_nationality = (Spinner) findViewById(R.id.nationality);
                Spinner tv_maritalStatus = (Spinner) findViewById(R.id.maritalstatus);
                DatePicker tv_birthday = (DatePicker) findViewById(R.id.birthday);
                EditText tv_height = (EditText) findViewById(R.id.height);
                EditText tv_weight = (EditText) findViewById(R.id.weight);
                EditText tv_address = (EditText) findViewById(R.id.address);
                Spinner tv_gender = (Spinner) findViewById(R.id.gender);
                EditText tv_email = (EditText) findViewById(R.id.email);
                Spinner tv_preferredFoot = (Spinner) findViewById(R.id.preferredfoot);
                EditText tv_number = (EditText) findViewById(R.id.number);
                ImageView tv_photo = (ImageView) findViewById(R.id.photo);
                Spinner tv_position = (Spinner) findViewById(R.id.position);

                String nickname = tv_nickname.getText().toString();
                String name = tv_name.getText().toString();
                String nationality = tv_nationality.getSelectedItem()!=null ? tv_nationality.getSelectedItem().toString() : "TUGA";
                String maritalStatus = tv_maritalStatus.getSelectedItem().toString();
                int day = tv_birthday.getDayOfMonth();
                int month = tv_birthday.getMonth() + 1;
                int year = tv_birthday.getYear();
                //corrigir quando a data estiver direita
                //String birthday = year+"-"+month+"-"+day;
                int birthday = year;
                int height = Integer.parseInt(tv_height.getText().toString());
                float weight = Float.parseFloat(tv_weight.getText().toString());
                String address = tv_address.getText().toString();
                String gender = tv_gender.getSelectedItem().toString();
                String email = tv_email.getText().toString();
                String preferredFoot = tv_preferredFoot.getSelectedItem().toString();
                int number = Integer.parseInt(tv_number.getText().toString());
                //ups vai estar a imagem em bitmap ou o path para ela?
                //String photo = tv_photo.get
                String photo="";
                Position position = (Position) tv_position.getSelectedItem();

                player=new Player(nickname,name,nationality,maritalStatus,birthday,height,weight,address,gender,photo,email,preferredFoot,number,null,position);

                //insert/update database
                try {
                    if(playerID > 0){
                        player_dao.update(player);
                    } else {
                        player_dao.insert(player);
                    }
                }catch (GenericDAOException ex){
                    System.err.println(CreatePlayer_Activity.class.getName() + " [WARNING] " + ex.toString());
                    Logger.getLogger(CreatePlayer_Activity.class.getName()).log(Level.WARNING, null, ex);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}