package com.group7.goodongroceries;

import java.io.Serializable;

/**
 * Created by dan on 6/1/2017.
 */

public class GroceryItem implements Serializable {

    public static final String EXTRA_GROCERY_ITEM = "GroceryItem";

    private String mItemName;
    private boolean mIsChecked;
    private int mPosition;

    public void setItemName(String itemName) {
        mItemName = itemName;
    }

    public String getItemName() {
        return mItemName;
    }

    public GroceryItem(String itemName) {
        mItemName = itemName;
        mIsChecked = false;
    }

    public boolean isChecked() {
        return mIsChecked;
    }

    public void setChecked(boolean isChecked) {
        mIsChecked = isChecked;
    }

    public int getPosition() {
        return mPosition;
    }

    public void setPosition (int position) {
        mPosition = position;
    }
}
