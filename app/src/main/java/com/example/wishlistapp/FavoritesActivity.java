package com.example.wishlistapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {
    private RecyclerView favoritesListView;
    private ProductAdapter adapter;
    private ArrayList<Product> favoriteProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        favoritesListView = findViewById(R.id.favoritesListView);
        favoritesListView.setLayoutManager(new LinearLayoutManager(this));

        favoriteProducts = getIntent().getParcelableArrayListExtra("favorites");
        if (favoriteProducts != null) {
            adapter = new ProductAdapter(favoriteProducts, this);
            favoritesListView.setAdapter(adapter);

            // Set listener for changes in favorite status
            adapter.setOnFavoriteChangeListener(product -> {
                if (!product.isFavorite()) {
                    // If the product is "unliked", remove it from the list
                    favoriteProducts.remove(product);
                    adapter.notifyDataSetChanged(); // Notify the adapter about the change
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        // Send back the updated favorites list when user leaves the favorites screen
        Intent resultIntent = new Intent();
        resultIntent.putParcelableArrayListExtra("updatedFavorites", favoriteProducts);
        setResult(RESULT_OK, resultIntent);
        super.onBackPressed();
    }
}
