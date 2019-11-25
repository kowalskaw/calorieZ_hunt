package com.example.myapplication.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.DataBaseHandler;
import com.example.myapplication.LoginActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Product;
import com.example.myapplication.R;
import com.example.myapplication.SearchProductInDatabaseActivity;
import com.example.myapplication.User;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private DataBaseHandler dataBaseHandler;

    private TextView maxCaloriesIntakeValue;
    private TextView todaysCaloriesIntakeValue;
    private TextView howManyCaloriesLeft;
    private TextView sumOfCaloriesForBreakfast;
    private TextView sumeOfCaloriesForLunch;
    private TextView sumeOfCaloriesForDinner;
    private TextView sumeOfCaloriesForSnacks;

    private Button addTobreakfastButton;
    private Button addToLunchButton;
    private Button addToDinnerButton;
    private Button addToSnacksButton;

    private TableLayout breakfastTable;
    private TableLayout lunchTable;
    private TableLayout dinnerTable;
    private TableLayout snacksTable;

    Integer caloriesForBreakfast;
    Integer caloriesForLunch;
    Integer caloriesForDinner;
    Integer caloriesForSnacks;
    ArrayList<Product>products;
    ArrayList<Product>lunchProducts;
    ArrayList<Product>dinnerProducts;
    ArrayList<Product>snacksProducts;
    Integer id;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        dashboardViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        MainActivity mainActivity = (MainActivity)getActivity();
        id = mainActivity.getData();

        dataBaseHandler = new DataBaseHandler();
        initTables();
        initAllElementsForLayout(root);

        sumOfCaloriesForBreakfast.setText(initTable(root,breakfastTable,products).toString());
        sumeOfCaloriesForLunch.setText(initTable(root,lunchTable,lunchProducts).toString());
        sumeOfCaloriesForDinner.setText(initTable(root,dinnerTable,dinnerProducts).toString());
        sumeOfCaloriesForSnacks.setText(initTable(root,snacksTable,snacksProducts).toString());


        calculateAllCaloriesValuesForDay(dataBaseHandler.findUser(id));

        return root;
    }

    private void initAllElementsForLayout(final View root){
        maxCaloriesIntakeValue = (TextView)root.findViewById(R.id.maxCaloriesIntakeValue);
        todaysCaloriesIntakeValue = (TextView)root.findViewById(R.id.todaysCaloriesIntakeValue);
        howManyCaloriesLeft = (TextView)root.findViewById(R.id.howManyCaloriesLeft);
        sumOfCaloriesForBreakfast = (TextView)root.findViewById(R.id.sumOfCaloriesForBreakfast);
        sumeOfCaloriesForLunch = (TextView)root.findViewById(R.id.sumeOfCaloriesForLunch);
        sumeOfCaloriesForDinner = (TextView)root.findViewById(R.id.sumeOfCaloriesForDinner);
        sumeOfCaloriesForSnacks = (TextView)root.findViewById(R.id.sumeOfCaloriesForSnacks);

        breakfastTable = (TableLayout)root.findViewById(R.id.tableLayout_breakfast);
        lunchTable = (TableLayout)root.findViewById(R.id.tableLayout_lunch);
        dinnerTable = (TableLayout)root.findViewById(R.id.tableLayout_dinner);
        snacksTable = (TableLayout)root.findViewById(R.id.tableLayout_snacks);

        addTobreakfastButton = (Button)root.findViewById(R.id.addNewProduct1);
        addTobreakfastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainAppIntent = new Intent();
                mainAppIntent.setClass(getActivity(), SearchProductInDatabaseActivity.class);
                startActivity(mainAppIntent);

                //addProductToTable(root,products,caloriesForBreakfast,sumOfCaloriesForBreakfast,breakfastTable);
                //calculateAllCaloriesValuesForDay(dataBaseHandler.findUser(id));
            }
        });
        addToLunchButton = (Button)root.findViewById(R.id.addNewProductToLunch);
        addToLunchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProductToTable(root,lunchProducts,caloriesForLunch,sumeOfCaloriesForLunch,lunchTable);
                calculateAllCaloriesValuesForDay(dataBaseHandler.findUser(id));
            }
        });

        addToDinnerButton = (Button)root.findViewById(R.id.addNewProductToDinner);
        addToDinnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProductToTable(root,dinnerProducts,caloriesForDinner,sumeOfCaloriesForDinner,dinnerTable);
                calculateAllCaloriesValuesForDay(dataBaseHandler.findUser(id));
            }
        });

        addToSnacksButton = (Button)root.findViewById(R.id.addNewProductToSnacks);
        addToSnacksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProductToTable(root,snacksProducts,caloriesForSnacks,sumeOfCaloriesForSnacks,snacksTable);
                calculateAllCaloriesValuesForDay(dataBaseHandler.findUser(id));
            }
        });
    }

    private void calculateAllCaloriesValuesForDay(User user){
        maxCaloriesIntakeValue.setText(user.getUserCaloriesIntakeDaily().toString());
        Integer breakfast = (Integer.parseInt(sumOfCaloriesForBreakfast.getText().toString()))+
                (Integer.parseInt(sumeOfCaloriesForDinner.getText().toString()))+
                (Integer.parseInt(sumeOfCaloriesForLunch.getText().toString()))+
                (Integer.parseInt(sumeOfCaloriesForSnacks.getText().toString()));

        todaysCaloriesIntakeValue.setText(breakfast.toString());
        Integer left = user.getUserCaloriesIntakeDaily()-breakfast;
        howManyCaloriesLeft.setText(left.toString());
    }

    //dodawanie produktu
    //włącza się inny layaout
    //wybieramy

    private void addProductToTable(View root, ArrayList<Product>products, Integer sumOfCalories, TextView sumOfCaloriesTextView, TableLayout tableLayout){
        products.add(new Product("Masło orzechowe",90));
        sumOfCalories = Integer.valueOf(sumOfCaloriesTextView.getText().toString())+90;
        sumOfCaloriesTextView.setText(sumOfCalories.toString());
        addRowToTable(root, tableLayout,"Masło orzechowe","90");
    }

    private Integer initTable(View root, TableLayout table, ArrayList<Product>products){
        Integer sumOfCalories = new Integer(0);
        for(Product product : products){
            addRowToTable(root,table,product.getProductsName(),product.getCaloriesForProduct().toString());
            sumOfCalories += product.getCaloriesForProduct();
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

    private void initTables(){
        caloriesForBreakfast = new Integer(0);
        caloriesForLunch = new Integer(0);
        caloriesForDinner = new Integer(0);
        caloriesForSnacks = new Integer(0);

        products = new ArrayList<>();
        products.add(new Product("Jaglanka",267));
        products.add(new Product("Banan",147));

        lunchProducts = new ArrayList<>();
        dinnerProducts = new ArrayList<>();
        dinnerProducts.add(new Product("Chleb",76));
        dinnerProducts.add(new Product("Serek wiejski",267));
        dinnerProducts.add(new Product("Pomidor",6));
        dinnerProducts.add(new Product("Ogórek",6));
        snacksProducts = new ArrayList<>();
    }
}