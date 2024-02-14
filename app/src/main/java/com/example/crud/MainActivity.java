package com.example.crud;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.crud.adapters.ProductAdapter;
import com.example.crud.data.AppData;
import com.example.crud.data.ProductsData;
import com.example.crud.databinding.ActivityMainBinding;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<ProductsData> products;
    ProductAdapter adapter;
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        products = new ArrayList<>();
        adapter = new ProductAdapter(products, this);
        binding.rvProducts.setAdapter(adapter);
        binding.rvProducts.setLayoutManager(new GridLayoutManager(this, 2));

        binding.btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddProduct.class);
            startActivity(intent);
        });

        loadProducts();
    }

    private void loadProducts() {
        StringRequest request = new StringRequest(Request.Method.GET, AppData.IP + "get_products.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("r_products", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray productsArray = jsonObject.getJSONArray("products");
                    for(int i = 0; i < productsArray.length(); i++) {
                        JSONObject productObject = productsArray.getJSONObject(i);
                        products.add(new Gson().fromJson(productObject.toString(), ProductsData.class));
                    }

                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("e_products", error.toString());
            }
        });

        Volley.newRequestQueue(this).add(request);
    }
}