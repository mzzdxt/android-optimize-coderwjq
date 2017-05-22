package com.coderwjq.applist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @Created by coderwjq on 2017/5/22 9:38.
 * @Desc
 */

public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.AppInfoViewHolder> {
    private ArrayList<AppInfoBean> mDatas;

    public AppListAdapter(ArrayList<AppInfoBean> datas) {
        mDatas = datas;
    }

    @Override
    public AppListAdapter.AppInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_app_info, parent, false);
        return new AppInfoViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(AppInfoViewHolder holder, int position) {
        AppInfoBean appInfoBean = mDatas.get(position);

        if (appInfoBean == null) {
            return;
        }

        if (appInfoBean.getAppIcon() != null) {
            holder.mIvAppIcon.setImageDrawable(appInfoBean.getAppIcon());
        }

        holder.mTvAppName.setText(appInfoBean.getAppName());
        holder.mTvAppVersionName.setText(appInfoBean.getAppVersionName());
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public class AppInfoViewHolder extends RecyclerView.ViewHolder {
        private ImageView mIvAppIcon;
        private TextView mTvAppName;
        private TextView mTvAppVersionName;

        public AppInfoViewHolder(View itemView) {
            super(itemView);

            mIvAppIcon = (ImageView) itemView.findViewById(R.id.iv_app_icon);
            mTvAppName = (TextView) itemView.findViewById(R.id.tv_app_name);
            mTvAppVersionName = (TextView) itemView.findViewById(R.id.tv_app_version_name);
        }
    }
}
