package com.coderwjq.lrucachedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private static final int[] sImageArray = {
            R.drawable.image_01, R.drawable.image_02,
            R.drawable.image_03, R.drawable.image_04,
            R.drawable.image_05, R.drawable.image_06,
            R.drawable.image_07};

    private ListView mLvImageList;
    private ListAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        mListAdapter = new ListAdapter();

        mLvImageList.setAdapter(mListAdapter);
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        mLvImageList = (ListView) findViewById(R.id.lv_image_list);
    }

    private static class ViewHolder {
        ImageView mIvImage;
    }

    private class ListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            Log.d(TAG, "getCount: " + sImageArray.length);
            return sImageArray.length;
        }

        @Override
        public Object getItem(int position) {
            return sImageArray[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;

            if (convertView == null) {
                convertView = View.inflate(MainActivity.this, R.layout.item_listview, null);
                viewHolder = new ViewHolder();
                viewHolder.mIvImage = (ImageView) convertView.findViewById(R.id.iv_image);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.mIvImage.setImageBitmap(ImageCache.getInstance(MainActivity.this).getCachedBitmap(sImageArray[position]));

            return convertView;
        }

    }
}
