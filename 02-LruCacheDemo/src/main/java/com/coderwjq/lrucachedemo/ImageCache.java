package com.coderwjq.lrucachedemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

/**
 * Created by coderwjq on 2017/4/20.
 */

public class ImageCache {
    private static final String TAG = "ImageCache";

    private static ImageCache sImageCache = null;
    private final LruCache<Integer, Bitmap> mBitmapLruCache;
    private Context mContext;

    private ImageCache(Context context) {
        mContext = context;

        // 在构造方法中创建一个LruCache
        long maxMemory = Runtime.getRuntime().maxMemory();
        // LruCache的默认大小一般为应用所占总内存的1/8
        mBitmapLruCache = new LruCache<Integer, Bitmap>((int) (maxMemory / 8)) {

            @Override
            protected int sizeOf(Integer key, Bitmap value) {
                // 该方法指定了entry中value对象所占用的内存空间
                return value.getByteCount();
            }

        };
    }

    public static ImageCache getInstance(Context context) {
        if (sImageCache == null) {
            synchronized (ImageCache.class) {
                if (sImageCache == null) {
                    sImageCache = new ImageCache(context);
                }
            }
        }
        return sImageCache;
    }

    public Bitmap getCachedBitmap(int source) {
        Bitmap bitmap = null;

        bitmap = mBitmapLruCache.get(source);

        if (bitmap == null) {
            // 从资源文件中加载
            Log.e(TAG, "getCachedBitmap: from drawable");
            bitmap = BitmapUtils.getScaledBitmap(mContext, source, 4);

            // 将加载后的bitmap缓存至LruCache
            mBitmapLruCache.put(source, bitmap);
        } else {
            // 从缓存中加载
            Log.e(TAG, "getCachedBitmap: from cache");
        }

        return bitmap;
    }
}
