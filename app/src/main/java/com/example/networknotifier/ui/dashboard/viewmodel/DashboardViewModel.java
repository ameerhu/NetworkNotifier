package com.example.networknotifier.ui.dashboard.viewmodel;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.content.SharedPreferences;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.AndroidViewModel;

public class DashboardViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> phone;
    private MutableLiveData<Boolean> g2;
    private MutableLiveData<Boolean> g3;
    private MutableLiveData<Boolean> g4;
    private MutableLiveData<Boolean> wifi;
    private MutableLiveData<Boolean> startup;
    private MutableLiveData<Boolean> notifi;
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;
    private SharedPreferences.OnSharedPreferenceChangeListener listener;

    public DashboardViewModel(Application application) {
        super(application);
        phone = new MutableLiveData<>();
        g2 = new MutableLiveData<>();
        g3 = new MutableLiveData<>();
        g4 = new MutableLiveData<>();
        wifi = new MutableLiveData<>();
        startup = new MutableLiveData<>();
        notifi = new MutableLiveData<>();

        settings = application.getSharedPreferences("AppInfo", 0);
        editor = settings.edit();
        updateProfSetting();
        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                updateProfSetting();
                Log.e("DVM","Listener");
            }
        };
        settings.registerOnSharedPreferenceChangeListener(listener);
    }


    public void updateProfSetting(){
        phone.setValue(settings.getBoolean("phone",false));
        g2.setValue(settings.getBoolean("2g",false));
        g3.setValue(settings.getBoolean("3g",false));
        g4.setValue(settings.getBoolean("lte",false));
        wifi.setValue(settings.getBoolean("wifi",false));
        startup.setValue(settings.getBoolean("startUp", false));
        notifi.setValue(settings.getBoolean("notifi",false));
        Log.e("Dashboard Update", " 2g: " + g2.getValue() + " 3g: " + g3.getValue() + " lte: " + g4.getValue() +
                " wifi: " + wifi.getValue() + " startup: " + startup.getValue() + " notifi: " + notifi.getValue());
    }

    public LiveData<Boolean> getG2() { return g2; }
    public LiveData<Boolean> getG3() { return g3; }
    public LiveData<Boolean> getG4() { return g4; }
    public LiveData<Boolean> getWifi() { return wifi; }
    public LiveData<Boolean> getStartUp() { return startup; }
    public LiveData<Boolean> getNotifi() { return notifi; }
    public LiveData<Boolean> getPhone() { return phone; }

    public void setPhone(Boolean status){ editor.putBoolean("phone",status); editor.commit(); }
    public void setG2(Boolean status){ editor.putBoolean("2g",status); editor.commit(); }
    public void setG3(Boolean status){ editor.putBoolean("3g",status); editor.commit(); }
    public void setG4(Boolean status){ editor.putBoolean("lte",status); editor.commit(); }
    public void setWifi(Boolean status){ editor.putBoolean("wifi",status); editor.commit();}
    public void setStartUp(Boolean status){ editor.putBoolean("startUp",status); editor.commit(); }
    public void setNotifi(Boolean status){ editor.putBoolean("notifi",status); editor.commit(); }

}