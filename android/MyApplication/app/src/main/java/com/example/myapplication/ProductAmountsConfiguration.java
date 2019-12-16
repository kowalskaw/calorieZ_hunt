package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class ProductAmountsConfiguration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_amounts_configuration);
        final LinearLayout layout = (LinearLayout)findViewById(R.id.layout_productConfiguration);
        final TextInputEditText productName = (TextInputEditText)findViewById(R.id.text_viev_productNameConfig);
        final TextInputEditText caloriesInPortion = (TextInputEditText)findViewById(R.id.text_viev_claoriesInPortionConfig);
        final TextInputEditText portionSize = (TextInputEditText)findViewById(R.id.text_viev_portionSizeConfig);
        final ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar);
        final EditText productsPerPortion = (EditText)findViewById(R.id.textView_caloriesPerPortionConfig);
        Intent i = getIntent();
        Product product = (Product)i.getSerializableExtra("productToConfigure");
        productName.setText(product.getProductsName());
        caloriesInPortion.setText(product.getCaloriesForProduct().toString());


        progressBar.setMax(product.caloriesForProduct);
        //int calForPortion = Integer.getInteger(portionSize.getText().toString())*product.getCaloriesForProduct()/100;
        progressBar.setProgress(100);

        getSupportActionBar().hide();

        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideKeyboard();
                return false;
            }
        });
    }

    public void hideKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}
