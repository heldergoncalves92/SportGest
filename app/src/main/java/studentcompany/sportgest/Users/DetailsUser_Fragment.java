package studentcompany.sportgest.Users;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import studentcompany.sportgest.R;
import studentcompany.sportgest.domains.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsUser_Fragment extends Fragment {

    private static final String TAG = "DETAILS_USER_FRAGMENT";
    private TextView tv_username, tv_name, tv_email;
    private ImageView tv_photo;

    public DetailsUser_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        LayoutInflater lf = getActivity().getLayoutInflater();

        View view =  lf.inflate(R.layout.fragment_user_details, container, false);
        tv_username = (TextView) view.findViewById(R.id.username);
        tv_name = (TextView) view.findViewById(R.id.name);
        tv_email = (TextView) view.findViewById(R.id.email);
        tv_photo = (ImageView) view.findViewById(R.id.photo);

        return view;
    }

    public void showUser(User user){

        if(user.getUsername()!=null)
            tv_username.setText(user.getUsername());
        else
            tv_username.setText("");

        if(user.getName()!=null)
            tv_name.setText(user.getName());
        else
            tv_name.setText("");

        if(user.getEmail()!=null)
            tv_email.setText(user.getEmail());
        else
            tv_email.setText("");

        if (user.getPhoto()!=null)
            tv_photo.setImageURI(Uri.parse(user.getPhoto()));
        else
            tv_photo.setImageURI(Uri.parse(""));
    }

    public void clearDetails(){
        tv_username.setText("");
        tv_name.setText("");
        tv_email.setText("");
        tv_photo.setImageURI(Uri.parse("defaulf"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar_crud, menu);
        MenuItem editItem = menu.findItem(R.id.Edit);
        editItem.setVisible(false);
        return true;
    }
}
