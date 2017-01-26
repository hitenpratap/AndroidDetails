package com.sidhit.sysinfo.src;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.sidhit.sysinfo.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hitenpratap on 26/01/17.
 */

public class HelperMethod {

    public static void getListOfSMSReceived(ContentResolver contentResolver, Activity activity) {
        List<SMSData> smsDataList = new ArrayList<>();
        Uri uri = Uri.parse("content://sms/inbox");
        Cursor c = contentResolver.query(uri, null, null, null, null);
        activity.startManagingCursor(c);

        // Read the sms data and store it in the list
        if (c.moveToFirst()) {
            for (int i = 0; i < c.getCount(); i++) {
                SMSData sms = new SMSData();
                sms.setBody(c.getString(c.getColumnIndexOrThrow("body")).toString());
                sms.setNumber(c.getString(c.getColumnIndexOrThrow("address")).toString());
                smsDataList.add(sms);

                c.moveToNext();
            }
        }
        c.close();
        for (int i = 0; i < 5; i++) {
            Log.d("Body", smsDataList.get(0).getBody());
            Log.d("Number", smsDataList.get(0).getNumber());
            System.out.println("**************************************");
        }
    }

    public static Boolean askForPermission(String permission, Integer requestCode, Context context, Activity activity) {
        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
            }
        } else {
            Toast.makeText(activity, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
        }
        return Boolean.TRUE;
    }

}
