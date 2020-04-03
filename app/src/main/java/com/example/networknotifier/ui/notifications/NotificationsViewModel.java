package com.example.networknotifier.ui.notifications;

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

    private MutableLiveData<String> mText1;
    private MutableLiveData<String> mText2;
    private BufferedReader bin = null;
    private Context context;

    public NotificationsViewModel(Application application){
        super(application);
        context = application.getApplicationContext();
        mText1 = new MutableLiveData<>();
        mText2 = new MutableLiveData<>();
        mText1.setValue("08/04/2020");
        mText2.setValue("Available Network : 4G");
        String data = "";
        try {
            bin = new BufferedReader(new InputStreamReader(context.openFileInput("network.log")));
            String read;
            while((read = bin.readLine())!=null){
                data += "\n"+read;
                System.err.println("data::::::::::::::"+read);
            }
            bin.close();
            //mText1.setValue(data);
        } catch (IOException e) {
            mText1.setValue("There is old log exist.");
//            e.printStackTrace();
        }
    }

    public LiveData<String> getText1() {
        return mText1;
    }
    public LiveData<String> getText2() {
        return mText2;
    }

    public void removeFile(){
        boolean del = context.deleteFile("network.log");
        if(del){
            mText1.setValue("");
        }else {
            Toast.makeText(context,"Unable to clear log",Toast.LENGTH_SHORT).show();
        }
    }
}