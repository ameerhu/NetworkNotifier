package com.example.networknotifier.helper;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.example.networknotifier.R;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ChangeStateHelper {

    private boolean g2;
    private boolean g3;
    private boolean lte;
    private boolean wifi;
    private boolean startup;
    private boolean notifi;
    private String message = null;
    private SharedPreferences settings;
    private NotificationManager mNotificationManager;
    private SharedPreferences.OnSharedPreferenceChangeListener listener;

    public ChangeStateHelper(Context context){
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        settings = context.getSharedPreferences("AppInfo", 0);
        updateMonitorNetwork(settings);
        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                updateMonitorNetwork(settings);
            }
        };
        settings.registerOnSharedPreferenceChangeListener(listener);
    }

    public void updateMonitorNetwork(SharedPreferences settings){
        Log.e("CSH", "update setting");
        g2 = settings.getBoolean("2g", false);
        g3 = settings.getBoolean("3g", false);
        lte = settings.getBoolean("lte", false);
        wifi = settings.getBoolean("wifi", false);
        startup = settings.getBoolean("startUp",false);
        notifi = settings.getBoolean("notifi",false);
    }

    public void getNetworkClass(Context context, int networkType) {
        Log.e("Monitor network", " 2g: " + g2 + " 3g: " + g3 + " lte: " + lte + " wifi: " + wifi + " startup: "+startup+" notifi: "+notifi);
        message=null;
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_UNKNOWN: {
                message = "Network unavailable";
                break;
            }
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN: {
                if(g2)
                    message = "2G available";
                break;
            }
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP: {
                if(g3)
                    message = "3G available";
                break;
            }
            case TelephonyManager.NETWORK_TYPE_LTE: {
                if(lte)
                    message = "4G/LTE available";
                break;
            }
            default:
                message = "Data unavailable";
        }
        if(message!=null)
        writeLog(context,message);
    }

    public void writeLog(Context context, String message){
        OutputStreamWriter bout = null;
        try {
            bout = new OutputStreamWriter(context.openFileOutput("network.log",Context.MODE_APPEND));
            bout.append(getDate() + " => " +  message+"\n");
            bout.close();
            int n = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
            mNotificationManager.notify(n,notifi(context, message).build());
//            showAlert(context, message);
//            Toast.makeText(context, date + " " +  message , Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                bout.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

//    public void showAlert(Context context, String message){
//        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
//        alertDialog.setTitle("Network Status");
//        alertDialog.setMessage(message);
//        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//        alertDialog.show();
//    }

    public Notification.Builder notifi(Context context, String message){
        Log.e("CSH","Notify calling");
        Notification.Builder builder = new Notification.Builder(context)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(message)
                .setPriority(Notification.PRIORITY_DEFAULT);
        return builder;
    }

//    public NotificationCompat.Builder notifi(Context context, String message){
//        Log.e("CSH","Notify calling");
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
//                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
//                .setContentTitle(context.getString(R.string.app_name))
//                .setContentText(message)
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//        return builder;
//    }

    public String getDate(){
        String pattern = " yyyy-MM-dd HH:mm:ss a";
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat(pattern, new Locale("fr", "FR"));
        String date = simpleDateFormat.format(new Date());
        return date;
    }

}
