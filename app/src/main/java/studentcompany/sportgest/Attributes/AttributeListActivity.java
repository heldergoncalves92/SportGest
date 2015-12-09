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
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import studentcompany.sportgest.R;
import studentcompany.sportgest.daos.Attribute_DAO;
import studentcompany.sportgest.daos.exceptions.GenericDAOException;
import studentcompany.sportgest.domains.Attribute;

public class AttributeListActivity extends AppCompatActivity implements ListAttribute_Fragment.OnItemSelected  {

    private Attribute_DAO attribute_dao;
    private List<Attribute> attributeList;
    private int currentPos = -1;
    private Menu mOptionsMenu;

    private DialogFragment mDialog;
    private FragmentManager mFragmentManager;
    private ListAttribute_Fragment mListAttributes = new ListAttribute_Fragment();
    private DetailsAttribute_Fragment mDetailsAttribute = new DetailsAttribute_Fragment();
    private static final String TAG = "ATTRIBUTE_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attribute_list);


        try {
            attribute_dao = new Attribute_DAO(getApplicationContext());
            updateAttributeList();
            mListAttributes.setAttributeList(getNamesList(attributeList));
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

    public List<String> getNamesList(List<Attribute> attrList){
        ArrayList<String> list = new ArrayList<>();

        for(Attribute a: attrList)
            list.add(a.getName());

        Collections.sort(list);

        return list;
    }

    public void removeAttribute(){
        mDetailsAttribute.clearDetails();
        mListAttributes.removeItem(currentPos);

        attribute_dao.deleteById(attributeList.get(currentPos).getId());
        attributeList.remove(currentPos);

        currentPos = -1;
        MenuItem item = mOptionsMenu.findItem(R.id.action_del);
        item.setVisible(false);
    }

    public void updateAttributeList() throws GenericDAOException {
        attributeList = attribute_dao.getAll();
        if(attributeList.isEmpty()) {
            new AttributeTestData(getApplicationContext());
            attributeList = attribute_dao.getAll();
        }
    }
    /************************************
     ****     Listener Functions     ****
     ************************************/

    public void itemSelected(int position) {
        Attribute attribute = attributeList.get(position);

        if(attribute != null){
            if(currentPos == -1) {
                MenuItem item = mOptionsMenu.findItem(R.id.action_del);
                item.setVisible(true);
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
                                    AttributeListActivity activity = (AttributeListActivity) getActivity();
                                    activity.DialogDismiss();
                                }
                            })
                    .setPositiveButton(res.getString(R.string.positive_answer),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    AttributeListActivity activity = (AttributeListActivity) getActivity();
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
        mOptionsMenu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_attributes_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(this, CreateAttributeActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.action_del:
                mDialog = AlertToDelete_DialogFragment.newInstance();
                mDialog.show(mFragmentManager, "Alert");
                return true;
            default:
                return false;
        }
    }
}
