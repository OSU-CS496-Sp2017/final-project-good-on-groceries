package com.group7.goodongroceries;

import java.io.Serializable;

/**
 * Created by dan on 6/3/2017.
 */

public class ProductItem implements Serializable {

    public static final String PRODUCT_EXTRA_ITEM = "product_extra_item";
    private String mProductName;
    private String mItemName;
    private boolean mLinked;

    public ProductItem(String productName, String itemName, boolean linked) {
        mProductName = productName;
        mItemName = itemName;
        mLinked = linked;
    }

    public String getProductName() {
        return mProductName;
    }

    public String getItemName() {
        return mItemName;
    }

    public boolean isLinked() {
        return mLinked;
    }

    public void setLinked(boolean linked) {
        mLinked = linked;
    }
}
