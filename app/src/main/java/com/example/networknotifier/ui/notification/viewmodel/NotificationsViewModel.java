package com.example.networknotifier.ui.notification.viewmodel;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.io.BufferedReader;
import android.app.Application;
import android.content.Context;
import java.io.InputStreamReader;
import androidx.lifecycle.AndroidViewModel;
import com.example.networknotifier.helper.adapter.LogModel;

public class NotificationsViewModel extends AndroidViewModel {

    private LogModel logModel;
    private List<LogModel> listLogModel;
    private BufferedReader fileReader = null;
    private Context context;

    public NotificationsViewModel(Application application){
        super(application);
        context = application.getApplicationContext();
        listLogModel = new ArrayList<LogModel>();

        String data[];
        try {
            fileReader = new BufferedReader(new InputStreamReader(context.openFileInput("network.log")));
            String read;
            while((read = fileReader.readLine())!=null){
                data = read.split("=>");
                logModel = new LogModel(data[2],data[0],data[1]);
                listLogModel.add(logModel);
            }
            fileReader.close();

        } catch (IOException e) {
//            mText1.setValue("There is old log exist.");
//            e.printStackTrace();
        }
    }

    public List<LogModel> getList(){
        return listLogModel;
    }

    public boolean removeFile(){
        boolean del = context.deleteFile("network.log");
        if(del){
            listLogModel.clear();
        }
        return del;
    }
}