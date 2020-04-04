package com.example.networknotifier.ui.notification.viewmodel;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class NotificationsViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;
    private BufferedReader bin = null;
    private Context context;

    public NotificationsViewModel(Application application){
        super(application);
        context = application.getApplicationContext();
        mText = new MutableLiveData<>();
        String data = "";
        try {
            bin = new BufferedReader(new InputStreamReader(context.openFileInput("network.log")));
            String read;
            while((read = bin.readLine())!=null){
                data += "\n"+read;
                System.err.println("data::::::::::::::"+read);
            }
            bin.close();
            mText.setValue(data);
        } catch (IOException e) {
            mText.setValue("There is old log exist.");
//            e.printStackTrace();
        }
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void removeFile(){
        boolean del = context.deleteFile("network.log");
        if(del){
            mText.setValue("");
        }else {
            Toast.makeText(context,"Unable to clear log",Toast.LENGTH_SHORT).show();
        }
    }
}