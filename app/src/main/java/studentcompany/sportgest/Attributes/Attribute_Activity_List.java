package studentcompany.sportgest.Attributes;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.jar.Attributes;

import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Attribute_DAO;
import studentcompany.sportgest.daos.Attribute_Exercise_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Attribute;
import studentcompany.sportgest.domains.Exercise;

public class Attribute_Activity_List extends AppCompatActivity implements Attribute_Fragment_List.OnItemSelected  {
    //DAOs
    private Attribute_DAO attribute_dao;

    private List<Attribute> attributeList;
    private int currentPos = -1;
    private Menu mOptionsMenu;

    private DialogFragment mDialog;
    private FragmentManager mFragmentManager;
    private Attribute_Fragment_List mListAttributes = new Attribute_Fragment_List();
    private Attribute_Fragment_Details mDetailsAttribute = new Attribute_Fragment_Details();
    private static final String TAG = "ATTRIBUTE_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attribute_list);


        try {
            attribute_dao = new Attribute_DAO(getApplicationContext());
            updateAttributeList();
            mListAttributes.setAttributeList(attributeList);
        } catch (GenericDAOException e) {
            e.printStackTrace();
        }


        // Get a reference to the FragmentManager
        mFragmentManager = getSupportFragmentManager();

        // Start a new FragmentTransaction
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        // Add the TitleFragment to the layout
        fragmentTransaction.add(R.id.attribute_list_fragment_container , mListAttributes);
        fragmentTransaction.add(R.id.attribute_detail_fragment_container, mDetailsAttribute);

        fragmentTransaction.commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
    }

    public List<String> getNamesList(List<Attribute> attrList){
        ArrayList<String> list = new ArrayList<>();

        for(Attribute a: attrList)
            list.add(a.getName());

        Collections.sort(list);

        return list;
    }

    public void removeAttribute(){
        Attribute attribute = attributeList.get(currentPos);
        attribute.setDeleted(1);

        try {
            attribute_dao.update(attribute);
        } catch (GenericDAOException e) {
            e.printStackTrace();
        }

        mDetailsAttribute.clearDetails();
        mListAttributes.removeItem(currentPos);
        currentPos = -1;
        mOptionsMenu.findItem(R.id.Delete).setVisible(false);
        mOptionsMenu.findItem(R.id.Edit).setVisible(false);
    }

    public void updateAttributeList() throws GenericDAOException {
        if(attribute_dao.numberOfRows() == 0) {
            new Attribute_TestData(getApplicationContext());
        }
        attributeList = attribute_dao.getByCriteria(new Attribute(-1, null, null, 0));
    }
    /************************************
     ****     Listener Functions     ****
     ************************************/

    public void itemSelected(int position, int tag) {
        Attribute attribute = attributeList.get(position);

        if(attribute != null){
            if(currentPos == -1) {
                mOptionsMenu.findItem(R.id.Delete).setVisible(true);
                mOptionsMenu.findItem(R.id.Edit).setVisible(true);
            }

            currentPos = position;
            mDetailsAttribute.showAttribute(attribute);
        }
    }

    /************************************
     ****      Dialog Functions      ****
     ************************************/

    public void DialogDismiss(){
        mDialog.dismiss();
    }

    public static class AlertToDelete_DialogFragment extends DialogFragment {

        public static AlertToDelete_DialogFragment newInstance(){
            return new AlertToDelete_DialogFragment();
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            Resources res = getResources();

            return new AlertDialog.Builder(getActivity())
                    .setMessage(res.getString(R.string.are_you_sure))
                    .setCancelable(false)
                    .setNegativeButton(res.getString(R.string.negative_answer),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Attribute_Activity_List activity = (Attribute_Activity_List) getActivity();
                                    activity.DialogDismiss();
                                }
                            })
                    .setPositiveButton(res.getString(R.string.positive_answer),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Attribute_Activity_List activity = (Attribute_Activity_List) getActivity();
                                    activity.DialogDismiss();
                                    activity.removeAttribute();
                                }
                            }).create();
        }
    }

    /************************************
     ****       Menu Functions       ****
     ************************************/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_crud, menu);
        mOptionsMenu = menu;
        menu.findItem(R.id.Edit).setVisible(false);
        menu.findItem(R.id.Delete).setVisible(false);
        menu.findItem(R.id.Save).setVisible(false);
        menu.findItem(R.id.Forward).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.Add:
                Intent intent = new Intent(this, Attribute_Activity_Create.class);
                startActivityForResult(intent,0);
                finish();
                return true;
            case R.id.Delete:
                mDialog = AlertToDelete_DialogFragment.newInstance();
                mDialog.show(mFragmentManager, "Alert");
                return true;
            case R.id.Edit:
                intent = new Intent(this, Attribute_Activity_Create.class);
                Bundle dataBundle = new Bundle();
                dataBundle.putLong(Attribute_DAO.TABLE_NAME + Attribute_DAO.COLUMN_ID, attributeList.get(currentPos).getId());
                intent.putExtras(dataBundle);
                startActivityForResult(intent,0);
                finish();
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 0) {
            try {
                attributeList = attribute_dao.getByCriteria(new Attribute(-1, null, null, 0));
                mListAttributes.setAttributeList(attributeList);
                mListAttributes.updateList(attributeList);

            } catch (GenericDAOException e) {
                e.printStackTrace();
            }
            mDetailsAttribute.clearDetails();
            currentPos = -1;
            mOptionsMenu.findItem(R.id.Delete).setVisible(false);
            mOptionsMenu.findItem(R.id.Edit).setVisible(false);
        }
    }
}
