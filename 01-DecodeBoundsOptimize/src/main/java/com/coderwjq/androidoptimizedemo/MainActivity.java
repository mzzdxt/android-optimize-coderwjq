package com.coderwjq.androidoptimizedemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private Button mBtnLoadOriginalImage;
    private Button mBtnLoadCompressedImage;
    private ImageView mIvImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnLoadOriginalImage = (Button) findViewById(R.id.btn_load_original_image);
        mBtnLoadCompressedImage = (Button) findViewById(R.id.btn_load_compressed_image);
        mIvImage = (ImageView) findViewById(R.id.iv_image);

        mBtnLoadOriginalImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadOriginalImage();
            }
        });

        mBtnLoadCompressedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadCompressedImage(300, 300);
            }
        });
    }

    /**
     * 加载压缩后的图片
     */
    private void loadCompressedImage(int targetWidth, int targetHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();

        // 加载图片时不会将整张图片加载到内存，只会读取图片的大小信息并存放在options中
        options.inJustDecodeBounds = true;

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test, options);

        // 获取图片的原始尺寸大小
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;

        // 用原图尺寸除以目标尺寸得到缩放比例，将最大值作为压缩比例
        // e.g. 原图 200 * 100 目标比例 100 * 25
        // widthScale = 200 / 100 = 2
        // heightScale = 100 / 80 = 4
        // 最大值代表图片被压缩的更厉害
        int widthScale = outWidth / targetWidth;
        int heightScale = outHeight / targetHeight;

        int maxScale = Math.max(widthScale, heightScale);

        Log.d(TAG, "loadCompressedImage: " + maxScale);

        /**
         * 源码注释：
         * If set to a value > 1, requests the decoder to subsample the original
         * image, returning a smaller image to save memory. The sample size is
         * the number of pixels in either dimension that correspond to a single
         * pixel in the decoded bitmap. For example, inSampleSize == 4 returns
         * an image that is 1/4 the width/height of the original, and 1/16 the
         * number of pixels. Any value <= 1 is treated the same as 1. Note: the
         * decoder uses a final value based on powers of 2, any other value will
         * be rounded down to the nearest power of 2.
         */
        options.inSampleSize = maxScale;

        // 边界压缩后，还需要将图片加载到内存，生成bitmap
        options.inJustDecodeBounds = false;

        // 进行色彩压缩
//        options.inPreferredConfig = Bitmap.Config.RGB_565;

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test, options);

        mIvImage.setImageBitmap(bitmap);

    }

    /**
     * 加载原始图片
     */
    private void loadOriginalImage() {
        mIvImage.setImageResource(R.drawable.test);
    }
}
