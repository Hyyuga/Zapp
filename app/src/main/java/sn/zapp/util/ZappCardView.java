package sn.zapp.util;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

import sn.zapp.R;

/**
 * Created by Steppo on 16.01.2016.
 */
public class ZappCardView extends CardView {

    public ZappCardView(Context context, AttributeSet attrs) {
        this(context, attrs, R.style.ZappCardView);
    }

    public ZappCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
