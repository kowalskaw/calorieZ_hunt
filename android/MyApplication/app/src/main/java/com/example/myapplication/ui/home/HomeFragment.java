package com.example.myapplication.ui.home;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.DataBaseHandler;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Product;
import com.example.myapplication.R;
import com.example.myapplication.User;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private ProgressBar progressBar;
    private TextView progressBarValue;

    private TextView sumOfCaloriesForBreakfast;
    private TextView sumeOfCaloriesForLunch;
    private TextView sumeOfCaloriesForDinner;
    private TextView sumeOfCaloriesForSnacks;

    private TableLayout breakfastTable;
    private TableLayout lunchTable;
    private TableLayout dinnerTable;
    private TableLayout snacksTable;

    private CalendarView calendarView;

    Integer caloriesForBreakfast;
    Integer caloriesForLunch;
    Integer caloriesForDinner;
    Integer caloriesForSnacks;
    Integer sumOfProgresCaloriesForDay;
    ArrayList<Product> products;
    ArrayList<Product>lunchProducts;
    ArrayList<Product>dinnerProducts;
    ArrayList<Product>snacksProducts;
    Integer id=0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);

        progressBar = root.findViewById(R.id.specificDayProgressBar);
        progressBarValue = root.findViewById(R.id.specificDayProgressValue);

        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
                //progressBar.setProgress(1000);//zapytanie do bazy ile kalorii zostało sporzytych tego dnia
                progressBar.setScaleY(5f);
                //progressBarValue.setText("1000 kalorii");
            }
        });

        initAllElementsForLayout(root);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                Toast.makeText(root.getContext(), "Zaznaczona data: "+day+"."+month+"."+year, Toast.LENGTH_LONG).show();
                clearAllTableLayouts();
                initAllElementsForLayout(root);
            }
        });
        return root;
    }

    private void initAllElementsForLayout(final View root){
        progressBar = root.findViewById(R.id.specificDayProgressBar);
        progressBarValue = root.findViewById(R.id.specificDayProgressValue);
        calendarView = (CalendarView) root.findViewById(R.id.calendarView);

        breakfastTable = (TableLayout)root.findViewById(R.id.tableLayout_breakfast);
        lunchTable = (TableLayout)root.findViewById(R.id.tableLayout_lunch);
        dinnerTable = (TableLayout)root.findViewById(R.id.tableLayout_dinner);
        snacksTable = (TableLayout)root.findViewById(R.id.tableLayout_snacks);

        sumOfCaloriesForBreakfast = (TextView)root.findViewById(R.id.sumOfCaloriesForBreakfast);
        sumeOfCaloriesForLunch = (TextView)root.findViewById((R.id.sumeOfCaloriesForLunch));
        sumeOfCaloriesForDinner = (TextView)root.findViewById(R.id.sumeOfCaloriesForDinner);
        sumeOfCaloriesForSnacks = (TextView)root.findViewById(R.id.sumeOfCaloriesForSnacks);

        addProductsToArraysFromDataBase();

        sumOfCaloriesForBreakfast.setText(initTable(root,breakfastTable,products).toString());
        sumeOfCaloriesForLunch.setText(initTable(root,lunchTable,lunchProducts).toString());
        sumeOfCaloriesForDinner.setText(initTable(root,dinnerTable,dinnerProducts).toString());
        sumeOfCaloriesForSnacks.setText(initTable(root,snacksTable,snacksProducts).toString());


        progressBar.setProgress(sumOfProgresCaloriesForDay);
        progressBarValue.setText(sumOfProgresCaloriesForDay.toString()+" kalorii");
    }

    //pobieranie danych z bazy o danym dniu
    private void addProductsToArraysFromDataBase(){
        //proces łączenia do bazy
        caloriesForBreakfast = new Integer(0);
        caloriesForLunch = new Integer(0);
        caloriesForDinner = new Integer(0);
        caloriesForSnacks = new Integer(0);
        sumOfProgresCaloriesForDay = new Integer(0);


        if(id==0) {
            products = new ArrayList<>();
            products.add(new Product("Chleb", 76));
            products.add(new Product("Serek wielski", 267));
            products.add(new Product("Pomidor", 6));
            products.add(new Product("Ogórek", 6));
            products.add(new Product("Ser kozi", 87));

            lunchProducts = new ArrayList<>();
            lunchProducts.add(new Product("Chleb", 76));
            lunchProducts.add(new Product("Serek wiejski", 267));
            lunchProducts.add(new Product("Pomidor", 6));

            dinnerProducts = new ArrayList<>();
            dinnerProducts.add(new Product("Chleb", 76));
            dinnerProducts.add(new Product("Serek wiejski", 267));
            dinnerProducts.add(new Product("Pomidor", 6));
            dinnerProducts.add(new Product("Ogórek", 6));

            snacksProducts = new ArrayList<>();
            snacksProducts.add(new Product("Jaglanka", 267));
            snacksProducts.add(new Product("Banan", 147));
            id=1;
        }
        else{
            products = new ArrayList<>();
            products.add(new Product("Jaglanka", 267));
            products.add(new Product("Banan", 147));

            lunchProducts = new ArrayList<>();
            lunchProducts.add(new Product("Chleb", 76));
            lunchProducts.add(new Product("Serek wiejski", 267));
            lunchProducts.add(new Product("Pomidor", 6));

            dinnerProducts = new ArrayList<>();
            dinnerProducts.add(new Product("Makaron ciemny", 76));
            dinnerProducts.add(new Product("Pesto zielone", 267));
            dinnerProducts.add(new Product("Pomidorki koktajlowe", 36));

            snacksProducts = new ArrayList<>();
            snacksProducts.add(new Product("Serek Skyr", 120));
            snacksProducts.add(new Product("Banan", 147));
            id=0;
        }
    }

    private Integer initTable(View root, TableLayout table, ArrayList<Product>products){
        Integer sumOfCalories = new Integer(0);
        for(Product product : products){
            addRowToTable(root,table,product.getProductsName(),product.getCaloriesForProduct().toString());
            sumOfProgresCaloriesForDay += product.getCaloriesForProduct();
            sumOfCalories +=product.getCaloriesForProduct();
        }
        return sumOfCalories;
    }

    private void addRowToTable(View root, TableLayout table, String name, String calories){
        TableRow tableRow = new TableRow(root.getContext());

        TextView productName = new TextView(root.getContext());
        productName.setGravity(Gravity.CENTER_VERTICAL);
        productName.setPadding(27,0,0,0);
        productName.setText(name);

        TextView caloriesForproduct = new TextView(root.getContext());
        caloriesForproduct.setGravity(Gravity.CENTER_VERTICAL|Gravity.END);
        caloriesForproduct.setText(calories);

        tableRow.addView(productName);
        tableRow.addView(caloriesForproduct);

        table.addView(tableRow);
    }

    private void clearAllTableLayouts(){
        breakfastTable.removeViews(1,products.size());
        lunchTable.removeViews(1,lunchProducts.size());
        dinnerTable.removeViews(1,dinnerProducts.size());
        snacksTable.removeViews(1,snacksProducts.size());
    }

}