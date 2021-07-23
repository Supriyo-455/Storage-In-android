package com.example.storageinandroid.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.storageinandroid.R;
import com.example.storageinandroid.model.CustomerModel;

import java.util.List;

public class CustomAdapter extends ArrayAdapter {

    public CustomAdapter(Context context, List<CustomerModel> customers) {
        super(context, 0, customers);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CustomerModel customerModel = (CustomerModel) getItem(position);

        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_items, parent, false);
        }

        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        TextView tv_desc = (TextView) convertView.findViewById(R.id.tv_desc);

        tv_name.setText(customerModel.getName());
        tv_desc.setText(customerModel.toString());

        return convertView;
    }
}
