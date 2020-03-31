package com.example.networknotifier.ui.helpers;

import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.networknotifier.R;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;

public class ChangeStateHelper {

    private String message;
    private NotificationManager mNotificationManager;

    ChangeStateHelper(Context context){
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void getNetworkClass(Context context, int networkType) {

//        int networkType = Objects.requireNonNull(mTelephonyManager).getNetworkType();
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
                message = "3G available";
                break;
            }
            case TelephonyManager.NETWORK_TYPE_LTE: {
                message = "4G/LTE available";
                break;
            }
            default:
                message = "Data unavailable";
        }
        writeLog(context,message);
    }

    public void writeLog(Context context, String message){
        OutputStreamWriter bout = null;
        try {
            bout = new OutputStreamWriter(context.openFileOutput("network.log",Context.MODE_APPEND));
            message = getDate() + " => " +  message+"\n";
            bout.append(message);
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

    public void showAlert(Context context, String message){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Network Status");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public NotificationCompat.Builder notifi(Context context, String message){
        Log.e("CSH","Notify calling");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        return builder;
    }

    public String getDate(){
        String pattern = " yyyy-MM-dd HH:mm:ss a";
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat(pattern, new Locale("fr", "FR"));
        String date = simpleDateFormat.format(new Date());
        return date;
    }

}
