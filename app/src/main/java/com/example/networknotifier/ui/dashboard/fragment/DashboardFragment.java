package com.example.networknotifier.ui.dashboard.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.networknotifier.R;
import com.example.networknotifier.ui.dashboard.viewmodel.DashboardViewModel;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private SharedPreferences settings;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
//        final TextView textView = root.findViewById(R.id.text_dashboard);
        final CheckBox phone = root.findViewById(R.id.phone);
        final CheckBox g2 = root.findViewById(R.id.g2);
        final CheckBox g3 = root.findViewById(R.id.g3);
        final CheckBox lte = root.findViewById(R.id.g4);
        final CheckBox wifi = root.findViewById(R.id.wifi);
        final Switch startUp = root.findViewById(R.id.startup);
        final Switch notifi = root.findViewById(R.id.noti);

//        settings = inflater.getContext().getSharedPreferences("AppInfo", 0);
//        final SharedPreferences.Editor editor = settings.edit();

//        g2.setChecked(settings.getBoolean("2g",false));
//        g3.setChecked(settings.getBoolean("3g",false));
//        lte.setChecked(settings.getBoolean("lte",false));
//        wifi.setChecked(settings.getBoolean("wifi",false));
//        startUp.setChecked(settings.getBoolean("startUp",false));
//        notifi.setChecked(settings.getBoolean("notifi",false));

/*        g2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    editor.putBoolean("2g",true);
                else
                    editor.putBoolean("2g",false);
                editor.commit();
            }
        });

        g3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    editor.putBoolean("3g",true);
                else
                    editor.putBoolean("3g",false);
                editor.commit();
            }
        });

        lte.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    editor.putBoolean("lte",true);
                else
                    editor.putBoolean("lte",false);
                editor.commit();
            }
        });

        wifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    editor.putBoolean("wifi",true);
                else
                    editor.putBoolean("wifi",false);
                editor.commit();
            }
        });

        startUp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    editor.putBoolean("startUp",true);
                else
                    editor.putBoolean("startUp",false);
                editor.commit();
            }
        });

        notifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    editor.putBoolean("notifi",true);
                else
                    editor.putBoolean("notifi",false);
                editor.commit();
            }
        });*/

        phone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                dashboardViewModel.setPhone(b);
            }
        });

        g2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                dashboardViewModel.setG2(b);
            }
        });

        g3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                dashboardViewModel.setG3(b);
            }
        });

        lte.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                dashboardViewModel.setG4(b);
            }
        });

        wifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                dashboardViewModel.setWifi(b);
            }
        });

        startUp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                dashboardViewModel.setStartUp(b);
            }
        });

        notifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                dashboardViewModel.setNotifi(b);
            }
        });

        dashboardViewModel.getPhone().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                phone.setChecked(aBoolean);
            }
        });

        dashboardViewModel.getG2().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                g2.setChecked(aBoolean);
            }
        });

        dashboardViewModel.getG3().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                g3.setChecked(aBoolean);
            }
        });

        dashboardViewModel.getG4().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                lte.setChecked(aBoolean);
            }
        });

        dashboardViewModel.getWifi().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                wifi.setChecked(aBoolean);
            }
        });

        dashboardViewModel.getStartUp().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                startUp.setChecked(aBoolean);
            }
        });

        dashboardViewModel.getNotifi().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                notifi.setChecked(aBoolean);
            }
        });

        return root;
    }
}