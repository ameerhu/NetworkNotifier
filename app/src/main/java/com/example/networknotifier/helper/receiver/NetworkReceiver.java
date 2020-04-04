package com.example.networknotifier.helper.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.networknotifier.helper.ChangeStateHelper;


public class NetworkReceiver extends BroadcastReceiver {

    private static boolean wifiConnected = false;
    private static boolean mobileConnected = false;
    private static NetworkInfo wifiNetInfo = null;
    private static NetworkInfo mobiNetInfo = null;
    private String message = "";
    private ChangeStateHelper cState;

    @Override
    public void onReceive(final Context context, Intent intent) {
        cState = new ChangeStateHelper(context);
        ConnectivityManager connMgr =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (intent.getAction() == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
            boolean onOff = intent.getBooleanExtra("state", false);
            if (onOff)
                cState.writeLog(context, "Network unavailable");
//                Log.e("Network  ","unavailable");
            else
                Log.e("Network  ", "available");
            return;
        } else if (intent.getAction() == ConnectivityManager.CONNECTIVITY_ACTION) {
            for (NetworkInfo networkInfo : connMgr.getAllNetworkInfo()) {
                if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    mobiNetInfo = networkInfo;
                    mobileConnected = networkInfo.isConnected();
//                    Log.e("connect type", mobiNetInfo.getType() + " info: " + mobiNetInfo.getSubtypeName() + " " + mobiNetInfo.getState().toString());
                }
                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    wifiConnected = networkInfo.isConnected();
                    wifiNetInfo = networkInfo;
                }

            }
        }

//            Log.e("connect type", mobiNetInfo.getType() + " info: " + mobiNetInfo.getSubtypeName() + " " + mobiNetInfo.getState().toString());
//            Log.e("connect type", wifiNetInfo.getType() + " info: " + wifiNetInfo.getTypeName() + " sub: " +
//                    wifiNetInfo.getSubtype() + " " + wifiNetInfo.getState().toString());

        if (wifiNetInfo != null && wifiNetInfo.isConnected() && wifiNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
//                System.err.println("WiFi is connected.");

        }
        if (mobiNetInfo != null && mobiNetInfo.isConnected() && mobiNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            System.err.println("Mobile network is connected. " + mobiNetInfo.getType());
            cState.getNetworkClass(context, mobiNetInfo.getSubtype());
        }
//            else if(mobiNetInfo.getType()==ConnectivityManager.TYPE_MOBILE){
//                System.err.println("Mobile network .");
////                writeLog(context,"Data unavailable");
//            }
//            else if(wifiNetInfo.getType() == ConnectivityManager.TYPE_WIFI){
//                System.err.println("WiFi is disconnected.");
//                writeLog(context,"Wifi unavailable");
//            }

    }
}