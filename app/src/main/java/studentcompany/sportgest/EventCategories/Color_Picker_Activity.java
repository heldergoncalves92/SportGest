package studentcompany.sportgest.EventCategories;

import com.github.danielnilsson9.colorpickerview.view.ColorPanelView;
import com.github.danielnilsson9.colorpickerview.view.ColorPickerView;
import com.github.danielnilsson9.colorpickerview.view.ColorPickerView.OnColorChangedListener;
import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import studentcompany.sportgest.R;

public class Color_Picker_Activity extends Activity implements OnColorChangedListener, View.OnClickListener {

    private ColorPickerView			mColorPickerView;
    private ColorPanelView			mOldColorPanelView;
    private ColorPanelView			mNewColorPanelView;

    private Button					mOkButton;
    private Button					mCancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.RGB_888);

        setContentView(R.layout.activity_color_picker);

        init();

    }

    private void init() {

        int initialColor;

        Bundle b = getIntent().getExtras();
        if(b!=null){
            initialColor = b.getInt("initialColor",0xFF000000);
        }
        else
            initialColor = 0xFF000000;

        mColorPickerView = (ColorPickerView) findViewById(R.id.colorpickerview__color_picker_view);
        //mOldColorPanelView = (ColorPanelView) findViewById(R.id.colorpickerview__color_panel_old);
        //mNewColorPanelView = (ColorPanelView) findViewById(R.id.colorpickerview__color_panel_new);

        mOkButton = (Button) findViewById(R.id.okButton);
        mCancelButton = (Button) findViewById(R.id.cancelButton);


        /*((LinearLayout) mOldColorPanelView.getParent()).setPadding(
                mColorPickerView.getPaddingLeft(), 0,
                mColorPickerView.getPaddingRight(), 0);
*/

        mColorPickerView.setOnColorChangedListener(this);
        mColorPickerView.setColor(initialColor, true);
        mColorPickerView.setAlpha(1.0f);
/*
        mOldColorPanelView.setColor(initialColor);
        mOldColorPanelView.setAlpha(1.0f);
*/

        mOkButton.setOnClickListener(this);
        mCancelButton.setOnClickListener(this);

    }

    @Override
    public void onColorChanged(int newColor) {
        /*mNewColorPanelView.setColor(newColor);
        mNewColorPanelView.setAlpha(1.0f);*/
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent();
        switch(v.getId()) {
            case R.id.okButton:
                setResult(mColorPickerView.getColor(), intent);
                finish();
                break;
            case R.id.cancelButton:
                setResult(0, intent);
                finish();
                break;
        }

    }


}