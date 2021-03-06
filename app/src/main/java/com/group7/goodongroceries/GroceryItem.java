package com.group7.goodongroceries;

import java.io.Serializable;

/**
 * Created by dan on 6/1/2017.
 */

public class GroceryItem implements Serializable {

    public static final String EXTRA_GROCERY_ITEM = "GroceryItem";

    private String mItemName;
    private boolean mIsChecked;
    private String mLinkedProductId;

    public String getItemName() {
        return mItemName;
    }

    public GroceryItem(String itemName) {
        mItemName = itemName;
        mIsChecked = false;
    }

    public GroceryItem(String itemName, boolean isChecked) {
        mItemName = itemName;
        mIsChecked = isChecked;
    }

    public GroceryItem(String itemName, boolean isChecked, String ndbNo) {
        mItemName = itemName;
        mIsChecked = isChecked;
        mLinkedProductId = ndbNo;
    }

    public boolean isChecked() {
        return mIsChecked;
    }

    public void setChecked(boolean isChecked) {
        mIsChecked = isChecked;
    }

    public void setLinkedProductId(String productId) {
        mLinkedProductId = productId;
    }

    public String getLinkedProductId() {
        return mLinkedProductId;
    }

    public boolean isLinked() {
        return mLinkedProductId != null;
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }

        if(!(o instanceof GroceryItem)) {
            return false;
        }

        GroceryItem groceryItem = (GroceryItem) o;

        //Check if the item name is the same when checking for equality
        //When adding/removing from lists the name needs to be unique.
        return this.getItemName().toLowerCase().equals(groceryItem.getItemName().toLowerCase());
    }
}
