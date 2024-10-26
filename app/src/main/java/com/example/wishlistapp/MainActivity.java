package com.example.wishlistapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int FAVORITES_REQUEST_CODE = 1; // Request code for identifying result
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private Button viewFavoritesButton;
    private ArrayList<Product> productList; // Maintain the main product list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the RecyclerView and Button
        recyclerView = findViewById(R.id.recyclerView);
        viewFavoritesButton = findViewById(R.id.viewFavoritesButton);

        // Set up the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create a sample product list
        productList = new ArrayList<>();
        productList.add(new Product("Nike Shoes", "Black size 7", 1099, R.drawable.img, false));
        productList.add(new Product("Nike Shoes ", "Black size 8", 1099, R.drawable.img_1, false));
        productList.add(new Product("T - Shirt ", "Mens full slive", 1599, R.drawable.img_2, false));

        productAdapter = new ProductAdapter(productList, this);
        recyclerView.setAdapter(productAdapter);

        // Set onClick listener for the View Favorites button
        viewFavoritesButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
            ArrayList<Product> favoriteProducts = productAdapter.getFavoriteProducts();
            intent.putParcelableArrayListExtra("favorites", favoriteProducts);
            startActivityForResult(intent, FAVORITES_REQUEST_CODE); // Use startActivityForResult to get result back
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check if result is coming from FavoritesActivity
        if (requestCode == FAVORITES_REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList<Product> updatedFavorites = data.getParcelableArrayListExtra("updatedFavorites");

            if (updatedFavorites != null) {
                // Synchronize the favorites state in the main product list
                for (Product product : productList) {
                    // Check if this product is still a favorite
                    boolean isStillFavorite = false;
                    for (Product favorite : updatedFavorites) {
                        if (product.getName().equals(favorite.getName())) {
                            isStillFavorite = true;
                            break;
                        }
                    }
                    product.setFavorite(isStillFavorite); // Update the favorite status
                }

                // Refresh the main list to reflect the changes
                productAdapter.notifyDataSetChanged();
            }
        }
    }
}
