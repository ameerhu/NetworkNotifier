package com.example.networknotifier.ui.notification.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.networknotifier.R;
import com.example.networknotifier.helper.adapter.LogAdapter;
import com.example.networknotifier.ui.notification.viewmodel.NotificationsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    FloatingActionButton btnClear;

    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        notificationsViewModel = ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        btnClear = root.findViewById(R.id.btn_clear);
        recyclerView = root.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(inflater.getContext());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new LogAdapter(notificationsViewModel.getList());
        recyclerView.setAdapter(mAdapter);

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(notificationsViewModel.removeFile()) {
                    mAdapter.notifyDataSetChanged();
                    Toast.makeText(inflater.getContext(), "log deleted.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }
}