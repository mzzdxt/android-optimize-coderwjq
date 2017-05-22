package com.coderwjq.applist;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private RecyclerView mRvAppList;
    private ArrayList<AppInfoBean> mDatas;
    private AppListAdapter mAppListAdapter;
    private KProgressHUD mKProgressHUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRvAppList = (RecyclerView) findViewById(R.id.rv_app_list);
        mRvAppList.setLayoutManager(new LinearLayoutManager(this));
        mDatas = new ArrayList<>();
        mAppListAdapter = new AppListAdapter(mDatas);
        mRvAppList.setAdapter(mAppListAdapter);

        initProgress();

        getAppInfoList().subscribe(new Consumer<AppInfoBean>() {
            @Override
            public void accept(@NonNull AppInfoBean appInfoBean) throws Exception {
                Log.d(TAG, "accept: " + appInfoBean.toString());
                mKProgressHUD.setLabel(String.format("已读取到%02d个应用", mDatas.size()));
                mDatas.add(appInfoBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {

            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                Log.d(TAG, "run() called");
                if (mKProgressHUD.isShowing()) {
                    mKProgressHUD.dismiss();
                }
                mAppListAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initProgress() {
        mKProgressHUD = KProgressHUD.create(MainActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("正在读取应用程序列表...")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
    }

    private Observable getAppInfoList() {
        return Observable.create(new ObservableOnSubscribe<PackageInfo>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<PackageInfo> e) throws Exception {
                List<PackageInfo> installedPackages = getPackageManager().getInstalledPackages(0);
                for (PackageInfo installedPackage : installedPackages) {
                    e.onNext(installedPackage);
                    SystemClock.sleep(10);
                }
                e.onComplete();
            }
        }).filter(new Predicate<PackageInfo>() {
            @Override
            public boolean test(@NonNull PackageInfo packageInfo) throws Exception {
                return (packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0;
            }
        }).map(new Function<PackageInfo, AppInfoBean>() {
            @Override
            public AppInfoBean apply(@NonNull PackageInfo packageInfo) throws Exception {
                AppInfoBean appInfoBean = new AppInfoBean();
                appInfoBean.setAppIcon(packageInfo.applicationInfo.loadIcon(getPackageManager()));
                appInfoBean.setAppName(packageInfo.applicationInfo.loadLabel(getPackageManager()).toString());
                appInfoBean.setAppVersionName(packageInfo.versionName);
                return appInfoBean;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
