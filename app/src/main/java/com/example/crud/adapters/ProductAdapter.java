package com.example.crud.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.crud.R;
import com.example.crud.data.ProductsData;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    Context context;
    private ArrayList<ProductsData> products;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView cardProduct;
        private ImageView img;
        private TextView txtName, txtPrice;
        public ViewHolder(View view) {
            super(view);
            cardProduct = view.findViewById(R.id.card_product);
            img = view.findViewById(R.id.img_product);
            txtName = view.findViewById(R.id.txt_name);
            txtPrice = view.findViewById(R.id.txt_price);
        }

        public MaterialCardView getCardProduct() {
            return cardProduct;
        }

        public ImageView getImg() {
            return img;
        }

        public TextView getTxtName() {
            return txtName;
        }

        public TextView getTxtPrice() {
            return txtPrice;
        }
    }

    public ProductAdapter(ArrayList<ProductsData> products, Context context) {
        this.context = context;
        this.products = products;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_product, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        ProductsData product = products.get(position);
        viewHolder.getTxtName().setText(product.getName());
        viewHolder.getTxtPrice().setText("Rs. " + product.getPrice());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
