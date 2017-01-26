package com.sidhit.sysinfo;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.sidhit.sysinfo.src.HelperMethod;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            if (HelperMethod.askForPermission(Manifest.permission.READ_SMS, 35, getApplicationContext(), this))
                HelperMethod.getListOfSMSReceived(getContentResolver(), this);
            if (HelperMethod.askForPermission(Manifest.permission.READ_CALL_LOG, 31, getApplicationContext(), this))
                HelperMethod.getListOfCallLog(getContentResolver(), this);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("Exception", e.getMessage());
        }
    }
}
