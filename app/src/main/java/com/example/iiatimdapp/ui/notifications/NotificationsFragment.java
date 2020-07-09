package com.example.iiatimdapp.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.iiatimdapp.APIManager;
import com.example.iiatimdapp.AppDatabase;
import com.example.iiatimdapp.HomeActivity;
import com.example.iiatimdapp.MainActivity;
import com.example.iiatimdapp.R;
import com.example.iiatimdapp.Room.DeleteTokenTask;
import com.example.iiatimdapp.Room.Token;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        Button uitloggen = root.findViewById(R.id.btnLoguit);

        uitloggen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                new Thread(new DeleteTokenTask(AppDatabase.getInstance(v.getContext()))).start();
                APIManager.getInstance(v.getContext()).deleteAccessToken();

                Intent myIntent = new Intent(getActivity(), MainActivity.class);
                getActivity().startActivity(myIntent);
            }
        });

        return root;
    }
}
