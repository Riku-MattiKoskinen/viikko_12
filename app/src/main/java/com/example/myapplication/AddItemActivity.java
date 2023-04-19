package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;


public class AddItemActivity extends AppCompatActivity {
    private EditText itemNameEditText, itemNotesEditText;
    private Switch superImportantSwitch;
    private Button addButton;
    private boolean isEditMode = false;
    private int itemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        itemNameEditText = findViewById(R.id.itemName);
        itemNotesEditText = findViewById(R.id.itemNotes);
        addButton = findViewById(R.id.button);
        superImportantSwitch = findViewById(R.id.superImportantSwitch);

        Intent intent = getIntent();
        if (intent.hasExtra("isEditMode")) {
            isEditMode = intent.getBooleanExtra("isEditMode", false);
            itemPosition = intent.getIntExtra("itemPosition", -1);

            if (isEditMode && itemPosition != -1) {
                ShoppingItem currentItem = ItemStorage.getInstance().getItems().get(itemPosition);
                itemNameEditText.setText(currentItem.itemName);
                itemNotesEditText.setText(currentItem.itemNotes);
                addButton.setText("Update Item");
                superImportantSwitch.setChecked(currentItem.isSuperImportant());
            }

        }

        addButton.setOnClickListener(v -> {
            String itemName = itemNameEditText.getText().toString();
            String itemNotes = itemNotesEditText.getText().toString();
            boolean superImportant = superImportantSwitch.isChecked();

            if (isEditMode) {
                ShoppingItem updatedItem = new ShoppingItem(itemName, itemNotes, superImportant);
                ItemStorage.getInstance().getItems().set(itemPosition, updatedItem);
            } else {
                ShoppingItem newItem = new ShoppingItem(itemName, itemNotes, superImportant);
                ItemStorage.getInstance().addItem(newItem);
            }

            ItemStorage.getInstance().saveItems(AddItemActivity.this);
            finish();
        });

    }
}