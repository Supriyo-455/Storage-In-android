package com.example.storageinandroid.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.storageinandroid.R;
import com.example.storageinandroid.database.DatabaseHelper;
import com.example.storageinandroid.databinding.ActivityMainBinding;
import com.example.storageinandroid.model.CustomerModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mainBinding;
    private Button btn_add, btn_viewAll, btn_hide;
    private EditText et_age, et_name;
    private Switch sw_active;
    private ListView list_view;
    private CustomAdapter customAdapter;
    private DatabaseHelper databaseHelper;
    private boolean isVisible = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        btn_add = mainBinding.btnAdd;
        btn_viewAll = mainBinding.btnViewAll;
        btn_hide = mainBinding.btnHide;
        et_age = mainBinding.etAge;
        et_name = mainBinding.etName;
        sw_active = mainBinding.swActive;
        list_view = mainBinding.listView;

        databaseHelper = new DatabaseHelper(MainActivity.this);

        if (isVisible) showCustomer();

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerModel customerModel;
                try {
                    customerModel = new CustomerModel(-1,
                            et_name.getText().toString(),
                            Integer.parseInt(et_age.getText().toString()),
                            sw_active.isActivated());

                    Toast.makeText(MainActivity.this, customerModel.toString(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Error Creating customer!", Toast.LENGTH_SHORT).show();
                    customerModel = new CustomerModel(-1, "error", 0, false);
                }
                boolean success = databaseHelper.addOne(customerModel);
                Toast.makeText(MainActivity.this, "Success " + success, Toast.LENGTH_SHORT).show();
                if (isVisible) showCustomer();
            }
        });

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CustomerModel clickedCustomer = (CustomerModel) parent.getItemAtPosition(position);
                databaseHelper.deleteOne(clickedCustomer);
                showCustomer();
                Toast.makeText(MainActivity.this, "Item deleted: " + clickedCustomer.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        btn_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isVisible = true;
                if (isVisible) showCustomer();
            }
        });

        btn_hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isVisible = false;
                list_view.setVisibility(View.GONE);
            }
        });
    }

    private void showCustomer() {
        list_view.setVisibility(View.VISIBLE);
        DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
        List<CustomerModel> list = databaseHelper.getAll();

        customAdapter = new CustomAdapter(MainActivity.this, list);
        list_view.setAdapter(customAdapter);
    }
}