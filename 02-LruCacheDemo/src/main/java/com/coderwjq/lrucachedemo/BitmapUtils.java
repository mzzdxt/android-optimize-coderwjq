package com.coderwjq.lrucachedemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by coderwjq on 2017/4/20.
 */

public class BitmapUtils {
    public static Bitmap getScaledBitmap(Context context, int source, int scale) {
        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inSampleSize = scale;

        return BitmapFactory.decodeResource(context.getResources(), source, options);
    }
}
