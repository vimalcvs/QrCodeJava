package com.nvd.qrcode;

import android.Manifest;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.nvd.qrcode.fragment.CreateFragment;
import com.nvd.qrcode.fragment.HistoryFragment;
import com.nvd.qrcode.fragment.ScanFragment;
import com.nvd.qrcode.fragment.SettingFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        AdView mAdView = findViewById(R.id.adView);

        Dexter.withContext(this).withPermissions(Manifest.permission.CAMERA, Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.CALL_PHONE).withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {/* ... */}
            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
        }).check();





        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        BottomNavigationView mBottomNavigation = findViewById(R.id.bottom_navigation);

        ScanFragment scanFragment = new ScanFragment();
        replaceFragment(scanFragment);

        mBottomNavigation.getMenu().findItem(R.id.bottom_scan).setChecked(true);
        mBottomNavigation.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.bottom_scan){
                ScanFragment scanFragment1 = new ScanFragment();
                replaceFragment(scanFragment1);

            } else if(item.getItemId() == R.id.bottom_history){
                HistoryFragment historyFragment = new HistoryFragment();
                replaceFragment(historyFragment);

            } else if(item.getItemId() == R.id.bottom_create){
                CreateFragment createFragment = new CreateFragment();
                replaceFragment(createFragment);

            }else if(item.getItemId() == R.id.bottom_settings){
                SettingFragment settingFragment = new SettingFragment();
                replaceFragment(settingFragment);
            }
            return true;
        });

    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment, null);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}