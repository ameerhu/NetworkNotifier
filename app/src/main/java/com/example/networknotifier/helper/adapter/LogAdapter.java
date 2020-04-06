package com.example.networknotifier.helper.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.networknotifier.R;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.ViewHolder> {

    List<LogModel> listLogModel;
    public LogAdapter(List<LogModel> logModel){
        this.listLogModel = logModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View listItem = inflater.inflate(R.layout.log_layout,parent,false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final LogModel logModelData = listLogModel.get(position);
        holder.message.setText(listLogModel.get(position).getMessage());
        holder.ddate.setText(listLogModel.get(position).getDdate());
        holder.ttime.setText(listLogModel.get(position).getTtime());
    }

    @Override
    public int getItemCount() {
        return listLogModel.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView message;
        public TextView ddate;
        public TextView ttime;

        public ViewHolder(View itemView) {
            super(itemView);
            this.message = (TextView) itemView.findViewById(R.id.log_message);
            this.ddate = (TextView) itemView.findViewById(R.id.ddate);
            this.ttime = (TextView) itemView.findViewById(R.id.ttime);
        }
    }
}
