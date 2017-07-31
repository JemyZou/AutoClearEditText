package com.tts.autoclearedittext.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.tts.autoclearedittext.R;

/**
 * Author: Jemy
 **/
public class AutoClearEditText extends EditText implements TextWatcher, View.OnFocusChangeListener {
    private Drawable cDrawable;
    private boolean focus;

    public AutoClearEditText(Context context) {
        this(context, null);
    }

    public AutoClearEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public AutoClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AutoClearEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        //get drawables
        cDrawable = getCompoundDrawables()[2];
        if (cDrawable == null) {
            cDrawable = getResources().getDrawable(R.drawable.ic_clear);
        }
        cDrawable.setBounds(0,0,cDrawable.getIntrinsicWidth(),cDrawable.getIntrinsicHeight());
        setDeleteIconVisible(false);
        addTextChangedListener(this);
        setOnFocusChangeListener(this);
    }

    private void setDeleteIconVisible(boolean show) {
        Drawable right = show ? cDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1],
                right, getCompoundDrawables()[3]);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        if (focus) {
            setDeleteIconVisible(text.length() > 0);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (MotionEvent.ACTION_UP == event.getAction()) {
            if (getCompoundDrawables()[2] != null) {
                boolean click = event.getX() > (getWidth() - getTotalPaddingRight()) && event.getX() < (getWidth() - getPaddingRight());
                if (click) {
                    setText("");
                }
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        this.focus = b;
        setDeleteIconVisible(focus && getText().toString().length() > 0);
    }
}
