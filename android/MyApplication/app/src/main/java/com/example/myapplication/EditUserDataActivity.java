package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.example.myapplication.ui.notifications.NotificationsViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class EditUserDataActivity extends AppCompatActivity {
    DataBaseHandler dataBaseHandler;
    private NotificationsViewModel notificationsViewModel;
    private TextInputEditText firstName;
    private TextInputEditText lastName;
    private TextInputEditText emailAdress;
    private TextInputEditText birthDate;
    private TextInputEditText height;
    private TextInputEditText weight;
    private LinearLayout linearLayout;
    private RadioButton male;
    private RadioButton female;
    User user;

    private Button saveUserDataButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_data);

        getSupportActionBar().setTitle("Zmiana danych");
        firstName = findViewById(R.id.userData_firstName);
        lastName = findViewById(R.id.userData_lastName);
        emailAdress = findViewById(R.id.userData_emailAdress);
        birthDate = findViewById(R.id.userData_birthDate);
        height = findViewById(R.id.userData_height);
        weight = findViewById(R.id.userData_weight);
        male = findViewById(R.id.userData_male);
        female = findViewById(R.id.userData_female);
        linearLayout = findViewById(R.id.linearLayout);

        dataBaseHandler = new DataBaseHandler();

        if (dataBaseHandler.findUser(1) != null) {
            setUserData(dataBaseHandler.findUser(1));
        }

        user = dataBaseHandler.findUser(new Integer(1));



        saveUserDataButton = (Button)findViewById(R.id.saveUserDataButton);
        saveUserDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeUser(user);
                finish();
            }
        });
        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideKeyboard();
                return false;
            }
        });
    }

    private void changeUser(User user){
        user.setUserFirsName(firstName.getText().toString());
        user.setUserLastName(lastName.getText().toString());
        user.setUserEmailAdress(emailAdress.getText().toString());
        //user.setUserBirthDate();
        user.setUserHeight(Integer.valueOf(height.getText().toString()));
        user.setUserWeight(Integer.valueOf(weight.getText().toString()));
        if (male.isChecked()&&!female.isChecked()) {
            user.setUserSex(1);
        } else if(!male.isChecked()&&female.isChecked()){
            user.setUserSex(2);
        }
    }

    public void hideKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
    private void setUserData(User user) {
        firstName.setText(user.getUserFirsName());
        lastName.setText(user.getUserLastName());
        emailAdress.setText(user.getUserEmailAdress());
        //birthDate.setText(user.getUserBirthDate().toString());
        height.setText(user.getUserHeight().toString());
        weight.setText(user.getUserWeight().toString());
        if (user.getUserSex() == 1) {
            male.setChecked(false);
            female.setChecked(true);
        } else {
            male.setChecked(true);
            female.setChecked(false);
        }
    }
}
