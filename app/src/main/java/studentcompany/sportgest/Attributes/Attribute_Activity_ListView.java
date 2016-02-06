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

import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Attribute_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Attribute;

public class Attribute_Activity_ListView extends AppCompatActivity implements Attribute_Fragment_List.OnItemSelected  {

    private Attribute_DAO attribute_dao;
    private List<Attribute> attributeList = null;
    private int currentPos = 0;
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

        if(savedInstanceState != null){
            currentPos = savedInstanceState.getInt("currentPos");
        }


        try {
            attribute_dao = new Attribute_DAO(getApplicationContext());
            updateAttributeList();

        } catch (GenericDAOException e) {
            e.printStackTrace();
        }

        //Check if it is empty
        if(attributeList == null)
            attributeList = new ArrayList<Attribute>();

        mListAttributes.setList(attributeList);


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
        outState.putInt("currentPos", currentPos);
    }

    public void removeAttribute(){
        Attribute attribute;

        mDetailsAttribute.clearDetails();
        attribute = mListAttributes.removeItem(currentPos);
        attribute.setDeleted(1);

        try {
            attribute_dao.update(attribute);
        } catch (GenericDAOException e) {
            e.printStackTrace();
        }

        currentPos = -1;
        mOptionsMenu.findItem(R.id.Delete).setVisible(false);
        mOptionsMenu.findItem(R.id.Edit).setVisible(false);
    }

    public void updateAttributeList() throws GenericDAOException {
        attributeList = attribute_dao.getByCriteria(new Attribute(-1, null, null, 0));
        if(attributeList.isEmpty()) {
            new Attribute_TestData(getApplicationContext());
            attributeList = attribute_dao.getByCriteria(new Attribute(-1, null, null, 0));
        }
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

                mDetailsAttribute.showFirstElem();
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
                                    Attribute_Activity_ListView activity = (Attribute_Activity_ListView) getActivity();
                                    activity.DialogDismiss();
                                }
                            })
                    .setPositiveButton(res.getString(R.string.positive_answer),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Attribute_Activity_ListView activity = (Attribute_Activity_ListView) getActivity();
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

        //To restore state on Layout Rotation
        if(currentPos != -1 && attributeList.size()>0) {

            MenuItem item = mOptionsMenu.findItem(R.id.Delete);
            item.setVisible(true);

            item = mOptionsMenu.findItem(R.id.Edit);
            item.setVisible(true);

            mDetailsAttribute.showAttribute(attributeList.get(currentPos));
            mListAttributes.select_Item(currentPos);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.Add:
                Intent intent = new Intent(this, Attribute_Activity_Create.class);
                startActivity(intent);
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
                startActivity(intent);
                finish();
                return true;
            default:
                return false;
        }
    }
}
