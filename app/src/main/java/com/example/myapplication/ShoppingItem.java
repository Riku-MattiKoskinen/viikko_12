package com.example.myapplication;
import java.io.Serializable;

public class ShoppingItem implements Serializable {
    private static final long serialVersionUID = 1L;
    String itemName;
    String itemNotes;
    long timestamp;
    boolean superImportant;


    public ShoppingItem(String itemName, String itemNotes, boolean superImportant) {
        this.itemName = itemName;
        this.itemNotes = itemNotes;
        this.timestamp = System.currentTimeMillis();
        this.superImportant = superImportant;
    }

    public String getName() {
        return itemName;
    }

    public void setName(String itemName) {
        this.itemName = itemName;
    }

    public String getNotes() {
        return itemNotes;
    }

    public long getTimeStamp() {
        return timestamp;
    }

    public boolean isSuperImportant() {
        return superImportant;
    }

    public void setSuperImportant(boolean superImportant) {
        this.superImportant = superImportant;
    }

}
