package com.hw.pq.mvp.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.hw.pq.R;
import com.hw.pq.base.SimpleActivity;
import com.hw.pq.util.T;
import com.socks.library.KLog;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;

public class PQIndexActivity extends SimpleActivity {
    @BindView(R.id.tv_loc)
    TextView tvLoc;
    @BindView(R.id.tv_acce)
    TextView tvAcce;

    //定位都要通过LocationManager这个类实现
    private LocationManager locationManager;
    private MyLocationListener myLocationListener;
    private int providerStatus;

    public SensorManager sensorManager;

    @Override
    protected int getLayout() {
        return R.layout.activity_pq_index;
    }

    @Override
    protected void initEventAndData() {
        initLocation();
//        initSensor();
    }

    @SuppressWarnings("static-access")
    public void initLocation() {
        requestNeedPermission();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        //1.获取位置的管理者
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        //2.获取定位方式
        //2.1获取所有的定位方式，true:表示返回所有可用定位方式
        List<String> providers = locationManager.getProviders(true);
        for (String string : providers) {
            System.out.println(string);
        }
        //2.2获取最佳的定位方式
        Criteria criteria = new Criteria();
        criteria.setAltitudeRequired(true);//设置是否可以定位海拔,如果设置定位海拔，返回一定是gps
        //criteria : 设置定位属性
        //enabledOnly : true如果定位可用就返回
        String bestProvider = locationManager.getBestProvider(criteria, false);
        System.out.println("最佳的定位方式:" + bestProvider);
        //3.定位
        myLocationListener = new MyLocationListener();
        //provider : 定位的方式
        //minTime : 定位的最小时间间隔
        //minDistance : 定位最小的间隔距离
        //LocationListener : 定位监听
        locationManager.requestLocationUpdates("network", 0, 0, myLocationListener);
    }

    private class MyLocationListener implements LocationListener {
        private String latLongString;

        //当定位位置改变的调用的方法
        //Location : 当前的位置
        @Override
        public void onLocationChanged(Location location) {
            float accuracy = location.getAccuracy();//获取精确位置
            double altitude = location.getAltitude();//获取海拔
            final double latitude = location.getLatitude();//获取纬度，平行
            final double longitude = location.getLongitude();//获取经度，垂直
            tvLoc.setText("longitude:" + longitude + "  latitude:" + latitude + "  精确位置" + accuracy + "  海拔" + altitude);
            KLog.e("打印经纬度：", "longitude:" + longitude + "  latitude:" + latitude + "精确位置" + accuracy + "海拔" + altitude);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<Address> addsList = null;
                    Geocoder geocoder = new Geocoder(PQIndexActivity.this);
                    try {
                        addsList = geocoder.getFromLocation(latitude, longitude, 10);//得到的位置可能有多个当前只取其中一个
                        KLog.e("打印拿到的城市", addsList.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (addsList != null && addsList.size() > 0) {
                        for (int i = 0; i < addsList.size(); i++) {
                            final Address ads = addsList.get(i);
                            latLongString = ads.getLocality();//拿到城市
//                            latLongString = ads.getAddressLine(0);//拿到地址
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    KLog.e("打印拿到的城市的地址", latLongString + ads.getAddressLine(0) + ads.getAddressLine(1) + ads.getAddressLine(4));
//                                    tvLoc.setText(latLongString + ads.getAddressLine(0) + ads.getAddressLine(1));
                                    T.shortShow("当前所在的城市为" + latLongString + ads.getAddressLine(0) + ads.getAddressLine(4) + ads.getAddressLine(1));
                                }
                            });
                        }
                    }
                }
            }).start();
        }

        //当定位状态发生改变的时候调用的方式
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // TODO Auto-generated method stub
            providerStatus = status;
            switch (status) {
                //GPS状态为服务区外时 0
                case LocationProvider.OUT_OF_SERVICE:
                    KLog.i("当前GPS状态为服务区外状态");
                    break;
                //GPS状态为暂停服务时 1
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    KLog.i("当前GPS状态为暂停服务状态");
                    break;
                //GPS状态为可见时 2
                case LocationProvider.AVAILABLE:
                    KLog.i("当前GPS状态为可见状态");
                    break;
            }
        }

        //当定位可用的时候调用的方法
        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub
        }

        //当定位不可用的时候调用的方法
        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(myLocationListener);//关闭gps,但是高版本中规定打开和关闭gps必须由用户自己主观的去实现，代码已经不允许进行操作
    }


    public void initSensor() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                tvAcce.setText("x:" + sensorEvent.values[0] +
                        "  y:" + sensorEvent.values[1] +
                        "  z:" + sensorEvent.values[2]);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        }, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), sensorManager.SENSOR_DELAY_UI);
    }

    /**
     * 需要android6.0以上处理的权限
     */
    private String[] PERMISSIONS = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS};

    /**
     * 判断是否需要进行权限的请求
     */
    private void requestNeedPermission() {
        boolean temp = false;
        for (String permission : PERMISSIONS) {
            if (!hasPermissionGranted(permission)) {
                temp = true;
                break;
            }
        }
        if (temp) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, 0);
        }
    }

    /**
     * 判断该权限是否已经授权
     *
     * @param permission
     * @return
     */
    private boolean hasPermissionGranted(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }
}
