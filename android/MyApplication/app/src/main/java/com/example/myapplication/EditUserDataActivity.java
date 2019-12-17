package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

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
    //RequestQueue requestQueue;
    String baseUrl = ("http://127.0.0.1:5000/user?user_name=");
    String url;

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

        //requestQueue = Volley.newRequestQueue(this);
        /*getRepoList("MadziaWesołek85");*/


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
        user.setFirst_name(firstName.getText().toString());
        user.setLast_name(lastName.getText().toString());
        user.setEmail(emailAdress.getText().toString());
        //user.setBirthDate();
        user.setHeight(Integer.valueOf(height.getText().toString()));
        user.setWeight(Integer.valueOf(weight.getText().toString()));
        if (male.isChecked()&&!female.isChecked()) {
            user.setSex(1);
        } else if(!male.isChecked()&&female.isChecked()){
            user.setSex(2);
        }
    }

    public void hideKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
    private void setUserData(User user) {
        firstName.setText(user.getFirst_name());
        lastName.setText(user.getLast_name());
        emailAdress.setText(user.getEmail());
        //birthDate.setText(user.getBirthDate().toString());
        height.setText(user.getHeight().toString());
        weight.setText(user.getWeight().toString());
        if (user.getSex() == 1) {
            male.setChecked(false);
            female.setChecked(true);
        } else {
            male.setChecked(true);
            female.setChecked(false);
        }
    }
}
