package com.sidhit.sysinfo.src;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.sidhit.sysinfo.src.model.CallLogData;
import com.sidhit.sysinfo.src.model.DeviceData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by hitenpratap on 26/01/17.
 */

public class NetworkUtil {

    static void sendDeviceData(Context context, DeviceData deviceData) {
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            JSONObject jsonParams = new JSONObject();
            jsonParams.put("simSerialNumber", deviceData.getSimSerialNumber());
            jsonParams.put("deviceName", deviceData.getDeviceName());
            jsonParams.put("primaryPhoneNumber", deviceData.getPrimaryPhoneNumber());
            jsonParams.put("secondaryPhoneNumber", deviceData.getSecondaryPhoneNumber());
            jsonParams.put("IMEINumber", deviceData.getIMEINumber());
            StringEntity entity = new StringEntity(jsonParams.toString());
            client.post(context, "http://10.0.2.2:8075/device/create", entity, "application/json", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void sendCallLogData(Context context, List<CallLogData> callLogDataList) {
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            JSONArray jsonArray = new JSONArray();
            for (CallLogData callLogData : callLogDataList) {
                JSONObject jsonParams = new JSONObject();
                jsonParams.put("callLogId", callLogData.getId());
                jsonParams.put("number", callLogData.getNumber());
                jsonParams.put("dateTime", callLogData.getDateTime());
                jsonParams.put("duration", callLogData.getDuration());
                jsonParams.put("callType", callLogData.getCallType().name());
                jsonArray.put(jsonParams);
            }
            StringEntity entity = new StringEntity(jsonArray.toString());
            client.post(context, "http://10.0.2.2:8075/callLog/create", entity, "application/json", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
