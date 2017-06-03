package com.group7.goodongroceries.items;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.group7.goodongroceries.R;

/**
 * Created by dan on 5/31/2017.
 *
 * Provides detailed information from the USDA API on a specific product selected from {@link ItemSearchActivity}
 */

public class ProductSearchActivity extends AppCompatActivity {

    private TextView mProductNameTextView;
    private TextView mProductResultsTextView;

    private ProgressBar mLoadingIndicatorPB;
    private TextView mLoadingErrorMessageTV;

    private String mProductName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_search);
        mProductNameTextView = (TextView) findViewById(R.id.tv_product_name);
        mProductResultsTextView = (TextView) findViewById(R.id.tv_product_info);
        mLoadingIndicatorPB = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        mLoadingErrorMessageTV = (TextView) findViewById(R.id.tv_loading_product_error);

        Intent intent = getIntent();
        if (null != intent && intent.hasExtra(GroceryItem.EXTRA_GROCERY_ITEM)) {
            mProductName = (String) intent.getSerializableExtra(GroceryItem.EXTRA_GROCERY_ITEM);
            mProductNameTextView.setText(mProductName);
        }

        mProductResultsTextView.setText("Results will be found here.");
    }
}
