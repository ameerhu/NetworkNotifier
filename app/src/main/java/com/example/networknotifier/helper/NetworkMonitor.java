package com.example.networknotifier.helper;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
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
                }
            }, PhoneStateListener.LISTEN_DATA_CONNECTION_STATE);
        }else{
            Toast.makeText(context,"Unable to start Network Monitor",Toast.LENGTH_SHORT).show();
        }
    }

}