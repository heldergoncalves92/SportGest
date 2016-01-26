package studentcompany.sportgest.Evaluation;

import android.os.Build;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import studentcompany.sportgest.R;
import studentcompany.sportgest.domains.Attribute;

public class PlayerAttributes_Fragment extends Fragment {

    private static final String TAG = "EVALUATE_PLAYER_ATTRIBUTES_FRAGMENT";
    private View view;
    //Layout
    private LinearLayout ll;

    private List<Attribute> attributes;

    //LayoutElems
    private HashMap<Long, Integer> quantitativeHashMap = new HashMap<>();
    private HashMap<Long, Integer> qualitativeHashMap = new HashMap<>();
    private HashMap<Long, Float> ratioPartialHashMap = new HashMap<>();
    private HashMap<Long, Float> ratioTotalHashMap = new HashMap<>();

    //DEFINES
    private static final int SEEKBAR_MAX = 20;
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);
    private static final String TOTAL = "Total";        //TODO: put in String.xml
    private static final String PARTIAL = "Partial";    //TODO: put in String.xml

    public PlayerAttributes_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        LayoutInflater lf = getActivity().getLayoutInflater();

        view =  lf.inflate(R.layout.fragment_player_attributes_evaluation, container, false);

        ll = (LinearLayout) view.findViewById(R.id.attribute_item_fragment_container);

        return view;
    }

    public void showEvaluations(List<Attribute> attributeList){
        clearDetails();
        attributes = attributeList;

        FragmentActivity fa = getActivity();

        //TODO: initialize variables with current evaluations (if applicable)
        for(Attribute a:attributeList){
            final long attribute_id = a.getId();
            //Horizontal Layout
            LinearLayout linearLayout = new LinearLayout(getActivity());
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

            //Attribute Name
            TextView tv_name = new TextView(fa);
            tv_name.setText(a.getName());
            tv_name.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
            linearLayout.addView(tv_name);

            switch (a.getType()){
                case Attribute.QUANTITATIVE:
                    final int v_id;
                    //Value indication
                    TextView tv_progress = new TextView(fa);
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1)
                        v_id = generateViewId();
                    else
                        v_id = View.generateViewId();
                    tv_progress.setId(v_id);
                    tv_progress.setText(SEEKBAR_MAX / 2 + "/" + SEEKBAR_MAX);
                    tv_progress.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.5f));

                    //SeekBar
                    SeekBar sb = new SeekBar(fa);
                    sb.setMax(SEEKBAR_MAX);
                    sb.setProgress(SEEKBAR_MAX / 2);
                    quantitativeHashMap.put(attribute_id, SEEKBAR_MAX / 2);
                    sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            TextView display = (TextView) view.findViewById(v_id);
                            display.setText(progress + "/" + SEEKBAR_MAX);
                            quantitativeHashMap.put(attribute_id, progress);
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {
                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                        }
                    });
                    sb.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 2f));

                    //Add to the layout
                    linearLayout.addView(sb);
                    linearLayout.addView(tv_progress);
                    break;
                case Attribute.QUALITATIVE:
                    Spinner spinner = new Spinner(fa);
                    ArrayList<String> qualitativeEval = new ArrayList<>();

                    List<Attribute.QUALITATIVE_TYPE> typeList = Arrays.asList(Attribute.QUALITATIVE_TYPE.values());
                    for(Attribute.QUALITATIVE_TYPE q:typeList){
                        qualitativeEval.add(q.name());
                    }
                    ArrayAdapter<CharSequence> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, qualitativeEval);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                    spinner.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 2.5f));
                    qualitativeHashMap.put(attribute_id, typeList.size()/2);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                            qualitativeHashMap.put(attribute_id, position);
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parentView) {}
                    });

                    //Add to the layout
                    linearLayout.addView(spinner);
                    break;
                case Attribute.RATIO:
                    //sub-LinearLayout
                    LinearLayout linearLayoutRatio = new LinearLayout(getActivity());
                    linearLayoutRatio.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 2.5f));
                    linearLayoutRatio.setOrientation(LinearLayout.HORIZONTAL);

                    TextInputLayout til_partial = new TextInputLayout(fa);
                    til_partial.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
                    EditText et_partial = new EditText(fa);
                    et_partial.setText("");
                    et_partial.setEms(5);
                    et_partial.setHint(PARTIAL);
                    et_partial.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    et_partial.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
                    et_partial.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {}

                        @Override
                        public void afterTextChanged(Editable s) {
                            ratioPartialHashMap.put(attribute_id, Float.parseFloat(s.toString()));
                        }
                    });
                    til_partial.addView(et_partial);

                    TextView tv_divisor = new TextView(fa);
                    tv_divisor.setText("/");
                    tv_divisor.setGravity(Gravity.BOTTOM);
                    tv_divisor.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));

                    TextInputLayout til_total = new TextInputLayout(fa);
                    til_total.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
                    EditText et_total = new EditText(fa);
                    et_total.setText("");
                    et_total.setEms(5);
                    et_total.setHint(TOTAL);
                    et_total.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    et_total.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
                    et_total.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {}

                        @Override
                        public void afterTextChanged(Editable s) {
                            ratioTotalHashMap.put(attribute_id, Float.parseFloat(s.toString()));
                        }
                    });
                    til_total.addView(et_total);

                    //Add to the layout
                    linearLayoutRatio.addView(til_partial);
                    linearLayoutRatio.addView(tv_divisor);
                    linearLayoutRatio.addView(til_total);
                    linearLayout.addView(linearLayoutRatio);
                    break;
                default:
                    TextView tv_na = new TextView(fa);
                    tv_na.setText("Sorry, not supported.");
                    tv_na.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 2.5f));
                    linearLayout.addView(tv_na);
            }

            ll.addView(linearLayout);
        }
    }

    public void clearDetails(){
        ll.removeAllViews();
    }

    /**
     * Generate a value suitable for use in @link #setId(int).
     * This value will not collide with ID values generated at build time by aapt for R.id.
     *
     * @return a generated ID value
     */
    public static int generateViewId() {
        for (;;) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }

    public HashMap<Long, Integer> getQuantitativeHashMap() {
        return quantitativeHashMap;
    }

    public HashMap<Long, Integer> getQualitativeHashMap() {
        return qualitativeHashMap;
    }

    public HashMap<Long, Float> getRatioPartialHashMap() {
        return ratioPartialHashMap;
    }

    public HashMap<Long, Float> getRatioTotalHashMap() {
        return ratioTotalHashMap;
    }
}