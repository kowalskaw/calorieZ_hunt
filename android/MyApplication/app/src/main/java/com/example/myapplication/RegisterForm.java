package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

public class RegisterForm extends AppCompatActivity {
    LinearLayout mainLayout;
    RadioGroup radioGroup;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Rejestracja");
        setContentView(R.layout.activity_register_form);
        mainLayout = (LinearLayout) findViewById(R.id.layout_registration);
        mainLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideKeyboard();
                return false;
            }
        });
        radioGroup = (RadioGroup) findViewById(R.id.radiogroup_registration);
        radioGroup.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideKeyboard();
                return false;
            }
        });
        registerButton = (Button) findViewById(R.id.button_registration);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //tutaj przesyłamy do bazy nowego użytkownika
                Intent loginIntent = new Intent(RegisterForm.this,LoginActivity.class);
                finish();
                startActivity(loginIntent);
            }
        });
    }

    public void hideKeyboard(){
        if(getCurrentFocus()!=null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}
