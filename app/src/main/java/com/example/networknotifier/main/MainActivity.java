package com.example.networknotifier.main;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;

import com.example.networknotifier.R;
import com.example.networknotifier.helper.NetworkMonitor;
import com.example.networknotifier.helper.receiver.NetworkReceiver;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


public class MainActivity extends AppCompatActivity {

    private NetworkReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

//        NetworkMonitor nm = new NetworkMonitor(this.getApplicationContext());

        if(!isServiceRunning()){
            Log.e("MA", "starting service");
            startService(new Intent(this, NetworkMonitor.class));
        }else {
            Log.e("MA", "already running service");
        }

        registeringNetworkBroadcasting();

    }

    public boolean isServiceRunning(){
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        if(manager.getRunningServices(Integer.MAX_VALUE)!=null)
            for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
            {
                if (NetworkMonitor.class.getName().equals(service.service.getClassName()))
                {
                    Log.e("MA", "already running service ");
                    return true;
                }
            }
        return false;
    }

    public void registeringNetworkBroadcasting(){
        receiver = new NetworkReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver,filter);
//        unregisterReceiver(receiver);
        Log.e("MA", " Network Broadcast. ");
    }

}
