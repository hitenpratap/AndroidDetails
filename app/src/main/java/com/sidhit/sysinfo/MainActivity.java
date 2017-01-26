package com.sidhit.sysinfo;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sidhit.sysinfo.src.HelperMethod;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (HelperMethod.askForPermission(Manifest.permission.READ_SMS, 35, getApplicationContext(), this))
            HelperMethod.getListOfSMSReceived(getContentResolver(), this);
    }
}
