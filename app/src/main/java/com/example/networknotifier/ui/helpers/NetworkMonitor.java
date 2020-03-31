package com.example.networknotifier.ui.helpers;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class NetworkMonitor extends Service {

    private TelephonyManager mTelephonyManager;
    private Context context;
    private ChangeStateHelper cState;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        this.context = getApplicationContext();
        cState = new ChangeStateHelper(this.context);

        if(this.context!=null) {
            mTelephonyManager = (TelephonyManager)
                    this.context.getSystemService(Context.TELEPHONY_SERVICE);
            Log.e("NM", "listener created.");
        }else{
            Toast.makeText(context,"Unable to start Network Monitor",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStart(Intent intent, final int startId) {
        super.onStart(intent, startId);

        Log.e("NM", "listening ");
        if(this.mTelephonyManager!=null) {
            mTelephonyManager.listen(new PhoneStateListener() {

                @Override
                public void onDataConnectionStateChanged(int state, int networkType) {
                    super.onDataConnectionStateChanged(state, networkType);
                    Log.e("NW ", "network: " + networkType + " " + " state: " + state);
                    if(state==TelephonyManager.DATA_DISCONNECTED && networkType==TelephonyManager.NETWORK_TYPE_UNKNOWN)
                        cState.writeLog(context, "Network unavailable");
                    else if(state==TelephonyManager.DATA_CONNECTED)
                        cState.getNetworkClass(context,networkType);
//                    else if(state==TelephonyManager.DATA_DISCONNECTED) {
//                        cState.writeLog(context, "Data unavailable");
//                    }
                }

//                @Override
//                public void onDataConnectionStateChanged(int state) {
//                    super.onDataConnectionStateChanged(state);
//                    Log.e("NW ", "network: " + " state: " + state);
//                    if(state==TelephonyManager.DATA_DISCONNECTED)
//                        cState.writeLog(context,"Mobile Data Off");
//                }

            }, PhoneStateListener.LISTEN_DATA_CONNECTION_STATE);

        }else{
            Toast.makeText(context,"Unable to start Network Monitor",Toast.LENGTH_SHORT).show();
        }
    }

}

/*public class NetworkMonitor extends PhoneStateListener {

    TelephonyManager mTelephonyManager;

    public NetworkMonitor(Context context) {
        mTelephonyManager = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        mTelephonyManager.listen(this,LISTEN_USER_MOBILE_DATA_STATE);
        Log.d("NM","listener created.");
    }

    @Override
    public void onDataConnectionStateChanged(int state, int networkType) {
        super.onDataConnectionStateChanged(state, networkType);
//                        getNetworkClass(context, networkType);
        Log.e("network ",networkType + " " + " state: " + state);
    }

}*/

/*
@Override
                public void onUserMobileDataStateChanged(boolean enabled) {
                    super.onUserMobileDataStateChanged(enabled);
                    Log.e("NW","DataStateChanged");
                    if(enabled)
                        Log.e(  "NW", "Network Available");
                    else
                        Log.e(  "NW", "Network Unavailable");
                }
 */