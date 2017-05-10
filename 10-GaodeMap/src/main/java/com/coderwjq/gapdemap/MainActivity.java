package com.coderwjq.gapdemap;

import android.Manifest;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.MyLocationStyle;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private MapView mMapView;
    private AMap mAMap;
    private LocationSource.OnLocationChangedListener mListener;
    private AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (mListener != null && aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    mListener.onLocationChanged(aMapLocation);
                    Log.e(TAG, "getProvider: " + aMapLocation.getProvider());
                    Log.e(TAG, "getCity: " + aMapLocation.getCity());
                    Log.e(TAG, "getStreet: " + aMapLocation.getStreet());
                    Log.e(TAG, "getStreetNum: " + aMapLocation.getStreetNum());
                } else {
                    Log.e(TAG, "onLocationChanged: 定位失败，失败原因：" + aMapLocation.getErrorInfo());
                }
            }
        }
    };
    private LocationSource mLocationSource = new LocationSource() {

        private AMapLocationClientOption mClientOption;

        private AMapLocationClient mLocationClient;

        /**
         * 激活定位
         * @param onLocationChangedListener
         */
        @Override
        public void activate(OnLocationChangedListener onLocationChangedListener) {
            // 此方法为每隔固定时间会发起一次定位请求
            mListener = onLocationChangedListener;

            if (mLocationClient == null) {
                mLocationClient = new AMapLocationClient(MainActivity.this);
                mLocationClient.setLocationListener(mLocationListener);

                // 初始化定位参数
                mClientOption = new AMapLocationClientOption();
                mClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
                mClientOption.setInterval(1000);
//                mClientOption.setOnceLocation(true);
                mLocationClient.setLocationOption(mClientOption);
                mLocationClient.startLocation();
            }
        }

        /**
         * 注销定位
         */
        @Override
        public void deactivate() {
            mListener = null;

            if (mLocationClient != null) {
                mLocationClient.stopLocation();
                mLocationClient.onDestroy();
                mLocationClient = null;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMapView = (MapView) findViewById(R.id.map_view);
        mMapView.onCreate(savedInstanceState);

        initMap();
    }

    /**
     * 初始化地图控制器
     */
    private void initMap() {
        final RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.requestEach(Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(@NonNull Permission permission) throws Exception {
                        switch (permission.name) {
                            case Manifest.permission.ACCESS_COARSE_LOCATION:
                                if (permission.granted) {
                                    if (mAMap == null) {
                                        mAMap = mMapView.getMap();
                                        // 设置定位监听
                                        mAMap.setLocationSource(mLocationSource);
                                        // 设置是否显示定位按钮
                                        mAMap.getUiSettings().setMyLocationButtonEnabled(true);
                                        // 显示定位层，并且设置可以触发定位
                                        mAMap.setMyLocationEnabled(true);
                                        mAMap.moveCamera(CameraUpdateFactory.zoomTo(15));
                                        mAMap.showIndoorMap(true);

                                        MyLocationStyle myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
                                        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
                                        myLocationStyle.strokeColor(Color.TRANSPARENT);//设置定位蓝点精度圆圈的边框颜色
                                        myLocationStyle.radiusFillColor(Color.TRANSPARENT);//设置定位蓝点精度圆圈的填充颜色
                                        mAMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
                                    }
                                } else {
                                    Toast.makeText(MainActivity.this, "需要位置权限", Toast.LENGTH_SHORT).show();
                                }
                                break;
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mMapView.onSaveInstanceState(outState);
    }
}
