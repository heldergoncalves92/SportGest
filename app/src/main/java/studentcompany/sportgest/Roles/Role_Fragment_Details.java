package studentcompany.sportgest.Roles;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import studentcompany.sportgest.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Role_Fragment_Details extends Fragment {


    public Role_Fragment_Details() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        LayoutInflater lf = getActivity().getLayoutInflater();

        View view =  lf.inflate(R.layout.fragment_user_details, container, false);


        return view;
    }

   /* public void showUser(User user){
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
        et_photo.setImageBitmap(getImageBitmap(this.getContext(),user.getPhoto()));
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
*/
}
