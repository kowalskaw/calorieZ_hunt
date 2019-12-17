package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SearchProductInDatabaseActivity extends AppCompatActivity {

    List<Product> listData;
    List<Product>filteredOutput;
    SearchView searchView_productInDatabase;
    ListView listView;
    ProductsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Znajdz produkt");
        setContentView(R.layout.activity_search_product_in_database);

        listView = (ListView)findViewById(R.id.listView_productToChose);
        getDataFromDatabase("");


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //przejście do innego widoku
                Product product = filteredOutput.get(i);
                Intent intent = new Intent(SearchProductInDatabaseActivity.this, ProductAmountsConfiguration.class);
                intent.putExtra("productToConfigure",product);
                Toast.makeText(getApplicationContext(), "Wybrano "+filteredOutput.get(i).getProductsName(), Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });



    }

    private void setData(){
        listData = new ArrayList<>();
        listData.add(new Product("Kapusta", 45));
        listData.add(new Product("Szynka", 70));
        listData.add(new Product("Pomidor", 6));
        listData.add(new Product("Ogórek", 5));
        listData.add(new Product("Masło orzechowe", 90));
        listData.add(new Product("Chleb", 67));
        listData.add(new Product("Makaron", 245));
        listData.add(new Product("Ser kozi", 76));
    }

    //po wyszukaniu w bazie tworzymy arrayList i przekazujemy w argumencie do stworzenia listy produktów
    private void getDataFromDatabase(String query){
        setData();
        filteredOutput = new ArrayList<>();

        if(searchView_productInDatabase!=null) {
            for(Product product : listData){
                if(product.getProductsName().toLowerCase().startsWith(query.toLowerCase())){
                    filteredOutput.add(product);
                }
            }
        }
        else{
            filteredOutput = listData;
        }
        adapter = new ProductsAdapter(this,R.layout.itemrow,filteredOutput);
        listView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem searchItem = menu.findItem(R.id.search);

        searchView_productInDatabase = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView_productInDatabase.setQueryHint("Wpisz nazwę szukanego produktu");
        searchView_productInDatabase.setIconifiedByDefault(true);
        searchView_productInDatabase.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                getDataFromDatabase(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                getDataFromDatabase(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}