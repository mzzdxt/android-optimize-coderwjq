package com.coderwjq.applist;

import android.graphics.drawable.Drawable;

/**
 * @Created by coderwjq on 2017/5/22 9:36.
 * @Desc
 */

public class AppInfoBean {

    private Drawable appIcon;
    private String appName;
    private String appVersionName;

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppVersionName() {
        return appVersionName;
    }

    public void setAppVersionName(String appVersionName) {
        this.appVersionName = appVersionName;
    }

    @Override
    public String toString() {
        return "AppInfoBean{" +
                "appIcon=" + appIcon +
                ", appName='" + appName + '\'' +
                ", appVersionName='" + appVersionName + '\'' +
                '}';
    }
}
