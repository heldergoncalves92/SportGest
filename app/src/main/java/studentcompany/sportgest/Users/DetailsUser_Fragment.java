package studentcompany.sportgest.Users;


import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.EditText;

import java.io.File;
import java.io.FileInputStream;

import studentcompany.sportgest.R;
import studentcompany.sportgest.domains.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsUser_Fragment extends Fragment {

    private static final String TAG = "DETAILS_USER_FRAGMENT";
    private EditText et_username, et_name, et_email,et_role,et_team;
    private ImageView et_photo;

    public DetailsUser_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        LayoutInflater lf = getActivity().getLayoutInflater();

        View view =  lf.inflate(R.layout.fragment_user_details, container, false);
        et_username = (EditText) view.findViewById(R.id.input_details_user_username);
        et_name = (EditText) view.findViewById(R.id.input_details_user_name);
        et_email = (EditText) view.findViewById(R.id.input_details_user_email);
        et_role = (EditText) view.findViewById(R.id.input_details_user_role);
        et_team = (EditText) view.findViewById(R.id.input_details_user_team);
        et_photo = (ImageView) view.findViewById(R.id.input_details_user_photo);

        return view;
    }

    public void showUser(User user){
        et_username.setText(user.getUsername());
        et_name.setText(user.getName());
        et_email.setText(user.getEmail());

        if(user.getRole()!=null)
            et_role.setText(user.getRole().getName());
        else
            et_role.setText("");

        if(user.getTeam()!=null)
            et_team.setText(user.getTeam().getName());
        else
            et_team.setText("");

        //et_photo.setImageURI(Uri.parse(user.getPhoto()));
        String pho = user.getPhoto();
        if(pho == null)
            {
                Drawable myDrawable;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    myDrawable = getResources().getDrawable(R.drawable.lego_face, getContext().getTheme());
                } else {
                    myDrawable = getResources().getDrawable(R.drawable.lego_face);
                }
             et_photo.setImageDrawable(myDrawable);
            }
        else
            et_photo.setImageBitmap(getImageBitmap(this.getContext(),pho));
    }

    public void clearDetails(){
        et_username.setText("");
        et_name.setText("");
        et_email.setText("");
        et_role.setText("");
        et_team.setText("");
        //et_photo.setImageURI(Uri.parse("default"));
    }

    public Bitmap getImageBitmap(Context context,String name){
        //name=name+"."+extension;
        try{
            ContextWrapper cw = new ContextWrapper(this.getContext());
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
