package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterForm extends AppCompatActivity {
    LinearLayout mainLayout;
    RadioGroup radioGroup;
    Button registerButton;

    TextInputEditText firstName;
    TextInputEditText lastname;
    TextInputEditText email;
    TextInputEditText passwordFirst;
    TextInputEditText passwordSecond;
    TextInputEditText birthDate;
    TextInputEditText wigth;
    TextInputEditText height;
    RadioButton male;
    RadioButton female;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Rejestracja");
        setContentView(R.layout.activity_register_form);

        layoutConfiguration();

        registerButton = (Button) findViewById(R.id.button_registration);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //tutaj przesyłamy do bazy nowego użytkownika
                AsyncTask asyncTask = new AsyncTask() {
                    @Override
                    protected Object doInBackground(Object[] objects) {
                        try {
                            //create new user
                            User user = getNewUserFromRegisterFormFields();

                            Gson gson = new Gson();
                            String json = gson.toJson(user);

                            //establish connection to database
                            OkHttpClient client = new OkHttpClient();
                            RequestBody formBody = new FormBody.Builder().add("newUser",json).build();
                            Request request = new Request.Builder()
                                    .url("http://10.0.2.2:5000/user?user_name="+ user.getUser_name())
                                    .post(formBody)
                                    .build();

                            Response response = client.newCall(request).execute();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }.execute();

                finish();

            }
        });
    }

    private void layoutConfiguration(){
        //main layout
        mainLayout = (LinearLayout) findViewById(R.id.layout_registration);
        mainLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideKeyboard();
                return false;
            }
        });
        //radio group
        radioGroup = (RadioGroup) findViewById(R.id.radiogroup_registration);
        radioGroup.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideKeyboard();
                return false;
            }
        });
        //register form fields
        firstName = (TextInputEditText)findViewById(R.id.textInputEditText_firstNameRegister);
        lastname = (TextInputEditText)findViewById(R.id.textInputEditText_lastNameRegister);
        email = (TextInputEditText)findViewById(R.id.textInputEditText_emailRegister);
        passwordFirst = (TextInputEditText)findViewById(R.id.textInputEditText_passwordFirstRegister);
        passwordSecond = (TextInputEditText)findViewById(R.id.textInputEditText_passwordSecondRegister);
        wigth = (TextInputEditText)findViewById(R.id.textInputEditText_wightRegister);
        height = (TextInputEditText)findViewById(R.id.textInputEditText_heightRegister);
        birthDate = (TextInputEditText)findViewById(R.id.textInputEditText_birthDateRegister);
        male = (RadioButton)findViewById(R.id.radiobutton_maleRegister);
        female = (RadioButton)findViewById(R.id.radiobutton_femaleRegister);
    }

    private User getNewUserFromRegisterFormFields() throws ParseException {
        User user = new User();

        user.setId(6);
        user.setFirst_name(firstName.getText().toString());
        user.setLast_name(lastname.getText().toString());
        user.setEmail(email.getText().toString());
        user.setPassword(passwordFirst.getText().toString());
        user.setBirthDate(new SimpleDateFormat("dd/MM/yyyy").parse(birthDate.getText().toString()));
        user.setWeight(Integer.valueOf(wigth.getText().toString()));
        user.setHeight(Integer.valueOf(height.getText().toString()));
        if(male.isChecked()){
            //Double BMRforMale = new Double(9.99*user.getWeight()+6.25*user.getHeight()-4.92*user.getBirthDate().getYear()-Integer.valueOf(new SimpleDateFormat("yyyy-MM-dd 'at' HH-mm-ss").format(new Date(System.currentTimeMillis())))+5);
            user.setCalories_intake_daily(2200);
            user.setSex(1);
        }
        else if(female.isChecked()){
            //Double BMRforFemale = new Double(9.99*user.getWeight()+6.25*user.getHeight()-4.92*user.getBirthDate().getYear()-Integer.valueOf(new SimpleDateFormat("yyyy-MM-dd 'at' HH-mm-ss").format(new Date(System.currentTimeMillis())))-161);
            user.setCalories_intake_daily(1800);
            user.setSex(0);
        }
        //add wieghtGoals, useName
        user.setUser_name("user");

        return user;
    }

    public void hideKeyboard(){
        if(getCurrentFocus()!=null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}
