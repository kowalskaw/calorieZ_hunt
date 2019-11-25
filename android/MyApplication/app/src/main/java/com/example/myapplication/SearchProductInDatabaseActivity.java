package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SearchProductInDatabaseActivity extends AppCompatActivity {

    List<Product> listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product_in_database);

        listData = new ArrayList<>();
        listData.add(new Product("Kapusta",45));
        listData.add(new Product("Szynka",70));
        listData.add(new Product("Pomidor",6));
        listData.add(new Product("Ogórek",5));
        listData.add(new Product("Masło orzechowe",90));
        listData.add(new Product("Chleb",67));
        listData.add(new Product("Makaron",245));
        listData.add(new Product("Ser kozi",76));

        ListView listView = (ListView)findViewById(R.id.listView_productToChose);

        ProductsAdapter adapter = new ProductsAdapter(this,R.layout.itemrow,listData);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "Wybrano "+listData.get(i).getProductsName(), Toast.LENGTH_LONG).show();
            }
        });

    }
}
