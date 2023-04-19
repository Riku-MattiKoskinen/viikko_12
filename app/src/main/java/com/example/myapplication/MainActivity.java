package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private com.example.myapplication.ShoppingListAdapter adapter;
    private List<com.example.myapplication.ShoppingItem> shoppingItems;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shoppingItems = new ArrayList<>(); // Initialize the shopping items list

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new com.example.myapplication.ShoppingListAdapter(this, shoppingItems, position -> {
            com.example.myapplication.ItemStorage.getInstance().getItems().remove(position);
            com.example.myapplication.ItemStorage.getInstance().saveItems(MainActivity.this);
            refreshItems();
        });

        recyclerView.setAdapter(adapter);
    }

    public void onAddItemClick(View view) {
        Intent intent = new Intent(MainActivity.this, com.example.myapplication.AddItemActivity.class);
        startActivityForResult(intent, 1);
    }

    public void onSortByDateClick(View view) {
        List<com.example.myapplication.ShoppingItem> items = com.example.myapplication.ItemStorage.getInstance().getItems();
        Collections.sort(items, (item1, item2) -> Long.compare(item1.getTimeStamp(), item2.getTimeStamp()));
        adapter.updateItems(items);
        adapter.notifyDataSetChanged();
    }

    public void onSortByNameClick(View view) {
        List<com.example.myapplication.ShoppingItem> filteredItems = new ArrayList<>(shoppingItems);
        Collections.sort(filteredItems, (item1, item2) -> item1.getName().compareToIgnoreCase(item2.getName()));
        adapter.updateItems(filteredItems);
        adapter.notifyDataSetChanged();
    }

    private void refreshItems() {
        com.example.myapplication.ItemStorage.getInstance().loadItems(this);
        List<com.example.myapplication.ShoppingItem> items = com.example.myapplication.ItemStorage.getInstance().getItems();
        adapter.updateItems(items);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
        refreshItems();
    }



}