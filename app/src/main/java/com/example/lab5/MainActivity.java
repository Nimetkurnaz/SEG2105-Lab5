package com.example.lab5;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView idView;
    EditText productBox;
    EditText skuBox;
    Button btnAdd, btnFind, btnDelete;
    ListView productList;
    ProductList productListAdapter;
    private MyDBHandler dbHandler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        idView = (TextView) findViewById(R.id.productIDEdit);
        productBox = (EditText) findViewById(R.id.productNameEdit);
        skuBox = (EditText) findViewById(R.id.skuEdit);
        btnAdd = findViewById(R.id.btnAdd);
        btnDelete = findViewById(R.id.btnDelete);
        btnFind = findViewById(R.id.btnFind);
        productList = findViewById(R.id.productList);

        productListAdapter = new ProductList(this, new ArrayList<>());
        productList.setAdapter(productListAdapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newProduct(v);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeProduct(v);
            }
        });

        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locateProduct(v);
            }
        });


        dbHandler = new MyDBHandler(this);

        productList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product selectedProduct = (Product) productList.getItemAtPosition(position);
                idView.setText(String.valueOf(selectedProduct.getId()));
                productBox.setText(selectedProduct.getProductName());
                skuBox.setText(String.valueOf(selectedProduct.getPrice()));
                productList.setItemChecked(position, true);
            }
        });
    }



    public void newProduct (View view) {

        int sku = Integer.parseInt(skuBox.getText().toString());

        Product product = new Product(productBox.getText().toString(), sku);

        //MyDBHandler dbHandler = new MyDBHandler(this);
        dbHandler.addProduct(product);
        productListAdapter.add(product);
        Toast.makeText(this, "Product added successfully", Toast.LENGTH_SHORT).show();

        productBox.setText("");
        skuBox.setText("");
    }


    public void locateProduct (View view) {

        //MyDBHandler dbHandler = new MyDBHandler(this);
        Product product = dbHandler.findProduct(productBox.getText().toString());

        if (product != null) {
            idView.setText(String.valueOf(product.getId()));
            skuBox.setText(String.valueOf(product.getPrice()));
        } else {
            idView.setText("No Match Found");
        }
    }


    public void removeProduct (View view) {

        int position = productList.getCheckedItemPosition();
        if (position != ListView.INVALID_POSITION) {
            Product deletedProduct = productListAdapter.getItem(position);
            productListAdapter.remove(deletedProduct);
        }
        //MyDBHandler dbHandler = new MyDBHandler(this);
        boolean result = dbHandler.deleteProduct(productBox.getText().toString());


        /**
        if (result) {
            idView.setText("");
            productBox.setText("");
            skuBox.setText("");
            productList.setItemChecked(position, false);
        }
        else
            idView.setText("No Match Found");
        {
         **/
        if (result) {
            Toast.makeText(this, "Product deleted successfully", Toast.LENGTH_SHORT).show();

            productList.setVisibility(View.GONE);

        }
        else {
            Toast.makeText(this, "No product selected for deletion", Toast.LENGTH_SHORT).show();
        }



    }

}
