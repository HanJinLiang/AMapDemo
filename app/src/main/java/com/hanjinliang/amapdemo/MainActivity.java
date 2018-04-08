package com.hanjinliang.amapdemo;

import android.Manifest;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.MyLocationStyle;
import com.blankj.ALog;

public class MainActivity extends AppCompatActivity {
    MapView mMapView;
    AMap mAMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMap(savedInstanceState);
    }


    private void initMap(Bundle savedInstanceState) {
        mMapView=findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mAMap=mMapView.getMap();
        MyLocationStyle myLocationStyle=new MyLocationStyle();
        //定位频率
        myLocationStyle.interval(2000);
        //跟随 但是不会移动到中间
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);
        mAMap.setMyLocationStyle(myLocationStyle);

        mAMap.setMyLocationEnabled(true);
        //设置返回到当前位置
        mAMap.getUiSettings().setMyLocationButtonEnabled(true);
        mAMap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                ALog.e(location.toString());
            }
        });

    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }
}
