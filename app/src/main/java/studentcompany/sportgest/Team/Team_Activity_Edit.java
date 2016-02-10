package studentcompany.sportgest.Team;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Team_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Team;

public class Team_Activity_Edit extends AppCompatActivity {

    //DAOs
    private Team_DAO team_dao;

    Team team = null;
    int teamID = -1;

    private EditText tv_name,tv_description,tv_season;
    private TextInputLayout inputLayoutName,inputLayoutDescription,inputLayoutSeason;
    private ImageButton tv_squad;
    private CheckBox tv_isCon;
    private int EDIT_SQUAD = 20;

    ImageView viewImage;
    Button bt;
    Bitmap bitmap = null;

    boolean editImage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_activity_edit);

        Bundle b = getIntent().getExtras();
        if(b!=null){
            teamID = (int)(b.getLong("id")+0);
        }

        bt=(Button)findViewById(R.id.btnSelectPhoto);
        viewImage=(ImageView)findViewById(R.id.viewImage);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

         tv_name = (EditText) findViewById(R.id.name);
         tv_description = (EditText) findViewById(R.id.description);
         tv_season = (EditText) findViewById(R.id.season);
        tv_isCon = (CheckBox) findViewById(R.id.isCom);

        //CheckBox tv_iscon = (CheckBox) findViewById(R.id.isCom);
        ImageView tv_logo = (ImageView) findViewById(R.id.viewImage);

        Team teamFromDB=null;
        team_dao = new Team_DAO(this);

        try {
            teamFromDB = team_dao.getById(teamID);
        } catch (GenericDAOException e) {
            e.printStackTrace();
        }
        if (teamFromDB!=null){
            if(teamFromDB.getName()!=null)
                tv_name.setText(teamFromDB.getName());
            else
                tv_name.setText("");
            if(teamFromDB.getDescription()!=null)
                tv_description.setText(teamFromDB.getDescription());
            else
                tv_description.setText("");
            if(teamFromDB.getSeason()!=-1)
                tv_season.setText(Integer.toString(teamFromDB.getSeason()));
            else
                tv_season.setText("");
            if(teamFromDB.getIs_com()!=-1){
                boolean val = false;
                if(teamFromDB.getIs_com()==1)
                    val = true;
                tv_isCon.setChecked(val);
            }
            else
                tv_isCon.setChecked(false);
            //TODO: por a imagem a aparecer
            //if(teamFromDB.getLogo()!=null)

        }

        inputLayoutName = (TextInputLayout) findViewById(R.id.inputLayoutName);
        inputLayoutDescription = (TextInputLayout) findViewById(R.id.inputLayoutDescription);
        inputLayoutSeason = (TextInputLayout) findViewById(R.id.inputLayoutSeason);

        tv_name.addTextChangedListener(new MyTextWatcher(tv_name));
        tv_description.addTextChangedListener(new MyTextWatcher(tv_description));
        tv_season.addTextChangedListener(new MyTextWatcher(tv_season));

        tv_squad = (ImageButton)findViewById(R.id.squad);
        tv_squad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Squad_Activity_Edit.class);
                intent.putExtra("id", teamID);
                startActivityForResult(intent, EDIT_SQUAD);
            }
        });

        String pho = teamFromDB.getLogo();

        team = teamFromDB;

        if(pho == null)
        {
            viewImage.setImageDrawable(getDefaultBitmap());
        }
        else
            viewImage.setImageBitmap(getImageBitmap(this,pho));

    }

    private Drawable getDefaultBitmap() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return getResources().getDrawable(R.drawable.team_default, this.getTheme());
        } else {
            return getResources().getDrawable(R.drawable.team_default);
        }
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

                 tv_name = (EditText) findViewById(R.id.name);
                 tv_description = (EditText) findViewById(R.id.description);
                 tv_season = (EditText) findViewById(R.id.season);
                //CheckBox tv_iscon = (CheckBox) findViewById(R.id.isCom);
                //ImageView tv_logo = (ImageView) findViewById(R.id.logo);

                String name = tv_name.getText().toString();
                String description = tv_description.getText().toString();
                int season = Integer.parseInt(tv_season.getText().toString());
                int isCom = tv_isCon.isChecked()?1:0;
                //ups vai estar a imagem em bitmap ou o path para ela?
                //String logo = tv_photo.get
                String photo="";

                photo = bitmap == null ? null: name.concat(".png");

                boolean ok = false;
                if (validateName())
                    if (validateDescription())
                        if (validateSeason())
                            ok = true;

                if (!ok) {
                    //Intent intent = new Intent();
                    //setResult(2, intent);
                    //finish();
                    return false;
                }
                if(editImage)
                    team=new Team(teamID, name, description, photo, season, isCom);
                else{
                    String origImage = team.getLogo();
                    team=new Team(teamID, name, description, origImage, season, isCom);
                }
                boolean corrected = false;
                //insert/update database
                try {
                    if(teamID > 0){
                        team_dao.update(team);
                        if(bitmap!=null && team.getLogo()!=null)
                            try {
                                saveToInternalSorage(bitmap, team.getLogo());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        corrected = true;
                    } else {
                        team_dao.insert(team);
                        if(bitmap!=null)
                            try {
                                saveToInternalSorage(bitmap, team.getLogo());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        corrected = true;
                    }
                }catch (GenericDAOException ex){
                    System.err.println(Team_Activity_Create.class.getName() + " [WARNING] " + ex.toString());
                    Logger.getLogger(Team_Activity_Create.class.getName()).log(Level.WARNING, null, ex);
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
                case R.id.inputLayoutName:
                    validateName();
                    break;
                case R.id.inputLayoutDescription:
                    validateDescription();
                    break;
                case R.id.inputLayoutSeason:
                    validateSeason();
                    break;
            }
        }
    }

    private boolean validateName() {
        if(inputLayoutName==null)
            inputLayoutName = (TextInputLayout) findViewById(R.id.inputLayoutName);
        String pw = tv_name.getText().toString().trim();
        if (pw.isEmpty() || pw.length() < 5) {
            inputLayoutName.setError(getString(R.string.err_name_short));
            return false;
        }
        inputLayoutName.setErrorEnabled(false);
        return true;
    }

    private boolean validateDescription() {
        if(inputLayoutDescription==null)
            inputLayoutDescription = (TextInputLayout) findViewById(R.id.inputLayoutDescription);
        String pw = tv_description.getText().toString().trim();
        if (pw.isEmpty() || pw.length() < 5) {
            inputLayoutDescription.setError(getString(R.string.err_description_short));
            return false;
        }
        inputLayoutDescription.setErrorEnabled(false);
        return true;
    }

    private boolean validateSeason() {
        if(inputLayoutSeason==null)
            inputLayoutSeason = (TextInputLayout) findViewById(R.id.inputLayoutSeason);
        String pw = tv_season.getText().toString().trim();
        if(!pw.matches("\\d+(\\.\\d+)?")){
            inputLayoutSeason.setError(getString(R.string.err_number));
            return false;
        }
        if (pw.isEmpty() || (pw.length() > 1 && pw.length()<4)) {
            int nb = -1;
            if(isNumericInt(pw)){
                nb = Integer.parseInt(pw);
            }else{
                inputLayoutSeason.setError(getString(R.string.err_number));
                return false;
            }
            if(!(nb<1000 && nb>2050)){
                inputLayoutSeason.setError(getString(R.string.err_season));
                return false;
            }
        }
        inputLayoutSeason.setErrorEnabled(false);
        return true;
    }

    @Override
    public void onStop(){
        super.onStop();

        setResult(0);
    }

    private void selectTeam() {
        Intent intent = new Intent(this, Squad_Activity_Edit.class);
        intent.putExtra("id", teamID);
        startActivityForResult(intent, EDIT_SQUAD);
    }

    public static boolean isNumericInt(String str)
    {
        try
        {
            int d = Integer.parseInt(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                try {

                    File f = new File(Environment.getExternalStorageDirectory().toString(),"temp.png");
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);

                    if(bitmap==null)
                        Toast.makeText(getApplicationContext(), R.string.error_occurred, Toast.LENGTH_SHORT).show();
                    else
                        viewImage.setImageBitmap(bitmap);
                    f.delete();
                    editImage = true;
                    /*
                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), R.string.error_occurred, Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == 2) {
                try {
                    Uri selectedImage = data.getData();
                    String[] filePath = {MediaStore.Images.Media.DATA};
                    Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePath[0]);
                    String picturePath = c.getString(columnIndex);
                    c.close();
                    bitmap = BitmapFactory.decodeFile(picturePath);
                    if (bitmap == null)
                        Toast.makeText(getApplicationContext(), R.string.error_occurred, Toast.LENGTH_SHORT).show();
                    else {
                        bitmap = resize(bitmap,200,200);
                        viewImage.setImageBitmap(bitmap);
                        editImage = true;
                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), R.string.error_occurred, Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == EDIT_SQUAD) {
                if(resultCode == 1){
                    Toast.makeText(getApplicationContext(), R.string.updated, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    private String saveToInternalSorage(Bitmap bitmapImage,String name) throws IOException {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File mypath=new File(directory,name);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fos.close();
        }
        return directory.getAbsolutePath();
    }




    private void selectImage() {

        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Set to default" };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add a Photo to the User");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                try {
                    if (options[item].equals("Take Photo")) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.png");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                        startActivityForResult(intent, 1);
                    } else if (options[item].equals("Choose from Gallery")) {
                        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 2);

                    } else if (options[item].equals("Set to default")) {
                        bitmap = null;
                        viewImage.setImageDrawable(getDefaultBitmap());
                        dialog.dismiss();
                    }
                } catch (Exception e) {

                }
            }
        });
        builder.show();
    }





    private Bitmap resize(Bitmap image, int maxWidth, int maxHeight) {
        if (maxHeight > 0 && maxWidth > 0) {
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > 1) {
                finalWidth = (int) ((float)maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float)maxWidth / ratioBitmap);
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            return image;
        } else {
            return image;
        }
    }

    public Bitmap getImageBitmap(Context context,String name){
        //name=name+"."+extension;
        try{
            ContextWrapper cw = new ContextWrapper(this);
            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
            // Create imageDir
            File mypath=new File(directory,name);
            FileInputStream fis = new FileInputStream(mypath);
            Bitmap b = BitmapFactory.decodeStream(fis);
            fis.close();
            return b;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
