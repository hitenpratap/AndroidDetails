package com.sidhit.sysinfo.src;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
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

    public static void getListOfCallLog(ContentResolver contentResolver, Activity activity) {
        List<CallLogData> callLogDataList = new ArrayList<>();
        Uri uri = Uri.parse("content://call_log/calls");
        Cursor c = contentResolver.query(uri, null, null, null, null);
        activity.startManagingCursor(c);

        // Read the sms data and store it in the list
        if (c.moveToFirst()) {
            for (int i = 0; i < c.getCount(); i++) {
                CallLogData callLogData = new CallLogData();
                callLogData.setDateTime(new Date(Long.valueOf(c.getString(c.getColumnIndexOrThrow(CallLog.Calls.DATE)).toString())));
                callLogData.setNumber(c.getString(c.getColumnIndexOrThrow(CallLog.Calls.NUMBER)).toString());
                callLogData.setDuration(c.getString(c.getColumnIndexOrThrow(CallLog.Calls.DURATION)).toString());
                int dircode = Integer.parseInt(c.getString(c.getColumnIndexOrThrow(CallLog.Calls.TYPE)).toString());
                switch (dircode) {
                    case CallLog.Calls.OUTGOING_TYPE:
                        callLogData.setCallType(Enums.CallType.OUTGOING);
                        break;
                    case CallLog.Calls.INCOMING_TYPE:
                        callLogData.setCallType(Enums.CallType.INCOMING);
                        break;
                    case CallLog.Calls.MISSED_TYPE:
                        callLogData.setCallType(Enums.CallType.MISSED);
                        break;
                }
                callLogDataList.add(callLogData);
                c.moveToNext();
            }
        }
        c.close();
        for (int i = 0; i < 5; i++) {
            Log.d("Dated", callLogDataList.get(0).getDateTime().toString());
            Log.d("Number", callLogDataList.get(0).getNumber());
            Log.d("Duration", callLogDataList.get(0).getDuration());
            Log.d("Type", callLogDataList.get(0).getCallType().name());
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
