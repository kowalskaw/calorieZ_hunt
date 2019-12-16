package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    private DataBaseHandler dataBaseHandler;
    private EditText mTextUsername;
    private EditText mTextPassword;
    private Button mLoginButton;
    private TextView mTextViewRegister;
    private LinearLayout loginLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dataBaseHandler = new DataBaseHandler();
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Logowanie");
        setContentView(R.layout.activity_login);
        initAllElementsForLayout();

    }

    private void initAllElementsForLayout() {
        mTextUsername = (EditText) findViewById(R.id.edittext_username);
        mTextPassword = (EditText) findViewById(R.id.edittext_password);
        mLoginButton = (Button) findViewById(R.id.button_login);
        mTextViewRegister = (TextView) findViewById(R.id.textview_register);
        loginLayout = (LinearLayout) findViewById(R.id.linearlayout_login);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncTask asyncTask = new AsyncTask() {
                    @Override
                    protected Object doInBackground(Object[] objects) {
                        OkHttpClient client = new OkHttpClient();

                        Request request = new Request.Builder().url("http://10.0.2.2:5000/user?user_name=MadziaWeso%C5%82ek85")
                                .build();

                        Response response = null;

                        try{
                            response = client.newCall(request).execute();
                            return response.body();

                        }catch (Exception e){
                            Log.e("Test",e.getMessage());
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        super.onPostExecute(o);


                        Toast.makeText(getApplicationContext(), o.toString(), Toast.LENGTH_LONG).show();
                        login();
                        Intent mainAppIntent = new Intent(LoginActivity.this, MainActivity.class);
                        //mainAppIntent.putExtras(userId);
                        finish();
                        startActivity(mainAppIntent);
                    }
                }.execute();
            }
        });
        mTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterForm.class);
                startActivity(registerIntent);
            }
        });
        loginLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideKeyboard();
                return false;
            }
        });
    }

    private void login() {

        if (mTextUsername.getText().toString().isEmpty() && mTextPassword.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Podaj potrzebne dane.", Toast.LENGTH_LONG).show();
        } else if (mTextUsername.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Podaj nazwę użytkownika lub adres e-mail.", Toast.LENGTH_LONG).show();
        } else if (mTextPassword.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Podaj hasło.", Toast.LENGTH_LONG).show();
        }
        else {
            User user = dataBaseHandler.findUserByUserNameOrEmailAddress(mTextUsername.getText().toString());
            if (dataBaseHandler.checkIfUserWithUserNameOrAdressExists(user.getUser_name())&&user.getPassword().equals(mTextPassword.getText().toString())) {
                Bundle userId = new Bundle();
                //User user = dataBaseHandler.findUserByUserNameOrEmailAddress(mTextUsername.getText().toString());
                userId.putInt("userId", user.getId());
                Toast.makeText(getApplicationContext(), "Zalogowano", Toast.LENGTH_LONG).show();
                Intent mainAppIntent = new Intent(LoginActivity.this, MainActivity.class);
                mainAppIntent.putExtras(userId);
                finish();
                startActivity(mainAppIntent);
            }
            else if (!dataBaseHandler.checkIfUserWithUserNameOrAdressExists(mTextUsername.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Nie znaleziono użytkonika.", Toast.LENGTH_LONG).show();
            }
            else if (!dataBaseHandler.checkIfPasswordIsCorrect(mTextUsername.toString(), mTextPassword.toString())) {
                Toast.makeText(getApplicationContext(), "Niepoprawne hasło.", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void hideKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

}
