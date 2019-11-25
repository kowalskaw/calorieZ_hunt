package com.example.myapplication.ui.dashboard;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public DashboardViewModel() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDateTime today = LocalDateTime.now();

        mText = new MutableLiveData<>();
        mText.setValue(dateTimeFormatter.format(today));
    }

    public LiveData<String> getText() {
        return mText;
    }
}