package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class ProductsAdapter extends ArrayAdapter<Product> {

    Context context;
    int layoutResourceId;
    List<Product> listOfproducts = null;


    public ProductsAdapter(Context context, int resource, List<Product> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutResourceId = resource;
        this.listOfproducts = objects;
    }

    static class DataHolder{
        TextView productName;
        TextView productCalorie;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DataHolder holder = null;

        if(convertView==null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();

            convertView = inflater.inflate(layoutResourceId,parent,false);

            holder = new DataHolder();
            holder.productName = (TextView)convertView.findViewById(R.id.textView_productName);
            holder.productCalorie = (TextView)convertView.findViewById(R.id.textView_calories);

            convertView.setTag(holder);
        }else{
            holder = (DataHolder)convertView.getTag();
        }
        Product product = listOfproducts.get(position);
        holder.productName.setText(product.getProductsName().toString());
        holder.productCalorie.setText(product.getCaloriesForProduct().toString());

        return convertView;
    }
}
