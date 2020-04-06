package com.example.networknotifier.helper.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.example.networknotifier.helper.ChangeStateHelper;

public class NetworkReceiver extends BroadcastReceiver {

    private static boolean wifiConnected = false;
    private ChangeStateHelper cState;
    private String wifiName=null;

    @Override
    public void onReceive(final Context context, Intent intent) {
        cState = new ChangeStateHelper(context);
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            for (NetworkInfo networkInfo : connMgr.getAllNetworkInfo()) {
                if (ConnectivityManager.TYPE_WIFI == networkInfo.getType()) {
                    if (networkInfo != null) {
                        if (networkInfo.isConnected()) {
                            if(wifiConnected!=networkInfo.isConnected()) {
                                wifiConnected = networkInfo.isConnected();
                                wifiName = networkInfo.getExtraInfo();
                                cState.writeLog(context, "Wifi Connected " + wifiName);
                            }
                        }else{
                            if(wifiConnected!=networkInfo.isConnected()) {
                                wifiConnected = networkInfo.isConnected();
                                cState.writeLog(context, " Wifi Disconnected ");
                            }
                        }
                    }
                }
            }
        }

        if (intent.getAction() == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
            boolean onOff = intent.getBooleanExtra("state", false);
            if (onOff)
                cState.writeLog(context, "Airplane On");
            else
                cState.writeLog(context, "Airplane Off");
            return;
        }

    }
}


/*      Log.e("NR type", networkInfo.getType() + " info: " + networkInfo.getTypeName() +
            " subType: " + networkInfo.getSubtype() + " subTypeName: " + networkInfo.getSubtypeName() +
            " Extra: " + networkInfo.getExtraInfo() + " status: " + networkInfo.getState().toString() +
            " Detail Status: " + networkInfo.getDetailedState().toString() + " con: " + wifiConnected);*/