package com.example.myapplication.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.DataBaseHandler;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.User;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        final ProgressBar progressBar = root.findViewById(R.id.specificDayProgressBar);
        final TextView progressBarValue = root.findViewById(R.id.specificDayProgressValue);

        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
                progressBar.setProgress(1000);//zapytanie do bazy ile kalorii zostało sporzytych tego dnia
                progressBar.setScaleY(5f);
                progressBarValue.setText("1000 kalorii");
            }
        });
        return root;
    }
}