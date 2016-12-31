package com.myteam.anglecamera;

import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by Cao on 2016/12/28.
 */

public class RulerView extends View {
    float ruler_length = 1000;
    float xcm;
    float xmm;

    public RulerView(Context context, DisplayMetrics dm) {
        super(context);

        findPixel(dm);

    }

    protected void findPixel(DisplayMetrics dm) {
        xcm = (float) (dm.xdpi / 2.54);         // 单位都是pixal
        xmm = xcm / 10;
    }
}
