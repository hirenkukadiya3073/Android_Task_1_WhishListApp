package com.example.wishlistapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private ArrayList<Product> products;
    private Context context;
    private OnFavoriteChangeListener favoriteChangeListener;

    public interface OnFavoriteChangeListener {
        void onFavoriteChange(Product product);
    }

    public void setOnFavoriteChangeListener(OnFavoriteChangeListener listener) {
        this.favoriteChangeListener = listener;
    }

    public ProductAdapter(ArrayList<Product> products, Context context) {
        this.products = products;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.productName.setText(product.getName());
        holder.productPrice.setText(String.format("Rs%.2f", product.getPrice()));
        holder.productDescription.setText(product.getDescription());
        holder.productImage.setImageResource(product.getImageResource());

        // Set the like button state based on whether the product is a favorite
        holder.likeButton.setImageResource(product.isFavorite() ? R.drawable.ic_heart_filled : R.drawable.ic_heart_unfilled);

        // Toggle favorite status when the like button is clicked
        holder.likeButton.setOnClickListener(v -> {
            product.setFavorite(!product.isFavorite());

            if (product.isFavorite()) {
                Toast.makeText(context, product.getName() + " added to favorites", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, product.getName() + " removed from favorites", Toast.LENGTH_SHORT).show();
            }

            if (favoriteChangeListener != null) {
                favoriteChangeListener.onFavoriteChange(product); // Notify listener of the change
            }
            notifyItemChanged(position); // Notify the adapter of the item change
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public ArrayList<Product> getFavoriteProducts() {
        ArrayList<Product> favoriteProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.isFavorite()) {
                favoriteProducts.add(product);
            }
        }
        return favoriteProducts;
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        TextView productPrice;
        TextView productDescription;
        ImageView productImage;
        ImageView likeButton;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productDescription = itemView.findViewById(R.id.productDescription);
            productImage = itemView.findViewById(R.id.productImage);
            likeButton = itemView.findViewById(R.id.likeButton);
        }
    }
}
