package com.hanjinliang.amapdemo;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;

import java.util.ArrayList;

public class StartActivity extends AppCompatActivity {

    String[] permissions=new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE
            ,Manifest.permission.ACCESS_WIFI_STATE,Manifest.permission.ACCESS_NETWORK_STATE
            ,Manifest.permission.READ_PHONE_STATE,Manifest.permission.ACCESS_COARSE_LOCATION};
    private static final int REQUEST_PERMISSION=0X082;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(isPermissionGranted()){//所有权限都有
            go2Main();
        }else{//没有权限
            requestPermissions();
        }
    }

    /**
     * 申请权限
     */
    private void requestPermissions() {
        ArrayList<String> noGrantedPermissions = new ArrayList<String>();
        for (String permission : permissions) {
            int result = PermissionChecker.checkSelfPermission(getApplicationContext(), permission);
            if (result != PermissionChecker.PERMISSION_GRANTED) {//没有授权
                noGrantedPermissions.add(permission);
            }
        }
        String[] noGrantedPermissionsArray = noGrantedPermissions.toArray(new String[noGrantedPermissions.size()]);
        ActivityCompat.requestPermissions(this, noGrantedPermissionsArray, REQUEST_PERMISSION);
    }


    /**
     * 检查是不是所有权限都有
     * @return
     */
    private boolean isPermissionGranted() {
        for(String permission:permissions){
            int result= ContextCompat.checkSelfPermission(getApplicationContext(), permission);
            if(result!=PermissionChecker.PERMISSION_GRANTED){//没有授权
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_PERMISSION:
                if(isPermissionGranted()){//所有权限都有
                    go2Main();
                }else{
                    showRequestDialog();
                }
                break;
        }
    }

    private void showRequestDialog() {
        new AlertDialog.Builder(this)
                .setMessage("权限一定要的，老铁")
                .setPositiveButton("明白", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package:" + getPackageName())); // 根据包名打开对应的设置界面
                                startActivity(intent);
                    }
                })
                .setNegativeButton("就不", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }


    private void go2Main(){
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

}
