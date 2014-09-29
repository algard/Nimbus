package planetexpress.nimbus.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.RelativeLayout;

public class CheckedRelativeLayout extends RelativeLayout implements Checkable {

    public CheckedRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    boolean isChecked;


    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void setChecked(boolean checked) {
        isChecked = checked;
        CheckBox cb;
        if ((cb = (CheckBox) findViewById(android.R.id.checkbox)) != null) {
            cb.setChecked(checked);
        }

    }

    @Override
    public void toggle() {
        setChecked(!isChecked);
    }
}