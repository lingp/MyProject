package com.lin.framework.Base;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.lin.framework.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends AppCompatActivity {

    // 需要申请的权限
    private String[] needPermissons = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    // 保存没有同意的权限
    private List<String> nAgreePermissonsList = new ArrayList<>();

    // 保存同意失败的权限
    private List<String> failPermissonsList = new ArrayList<>();

    private OnPermissonResult permissonResult;

    private int requestCode;

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void request(int requestCode, OnPermissonResult permissonResult) {
        if (!checkPermissonsAll()) {
            requestPermissonsAll(requestCode, permissonResult);
        }
    }

    /**
     * 请求单个权限
     * @param permissions
     * @return
     */
    protected boolean checkPermissions(String permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int check = checkSelfPermission(permissions);
            LogUtils.i("check:" + check);
            return check == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    /**
     * 判断是否需要申请权限
     * @return
     */
    protected boolean checkPermissonsAll() {
        nAgreePermissonsList.clear();
        for (int i = 0; i < needPermissons.length; i++) {
            boolean check = checkPermissions(needPermissons[i]);
            // 判断是否没权限
            if(!check) {
                nAgreePermissonsList.add(needPermissons[i]);
            }
        }
        return nAgreePermissonsList.size() > 0 ? false : true;
    }

    /**
     * 请求权限
     * @param mPermissons
     * @param requestCode
     */
    protected void requestPermissons(String[] mPermissons, int requestCode) {
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
             requestPermissions(mPermissons, requestCode);
         }
    }

    /**
     * 申请所有权限
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void requestPermissonsAll(int requestCode, OnPermissonResult permissonResult) {
        this.requestCode = requestCode;
        this.permissonResult = permissonResult;
        requestPermissions(nAgreePermissonsList.toArray(new String[nAgreePermissonsList.size()]), 1000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        nAgreePermissonsList.clear();
        if (requestCode == this.requestCode) {
            if (grantResults.length > 0) {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        // 失败的权限
                        failPermissonsList.add(needPermissons[i]);
                    }
                }

                if (permissonResult != null) {
                    if (failPermissonsList.size() == 0) {
                        permissonResult.onSuccess();
                    } else {
                        permissonResult.onFail(failPermissonsList);
                    }
                }
            }
        }

    }

    protected interface OnPermissonResult {
        void onSuccess();
        void onFail(List<String> noPermissons);
    }

    /**
     * 判断窗口权限
     * @return
     */
    protected boolean checkWindowPermissons() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(this);
        }

        return true;
    }

    /**
     * 请求窗口权限
     * @param requestCode
     */
    protected void requestWindowPermission(int requestCode) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, requestCode);
    }
}
