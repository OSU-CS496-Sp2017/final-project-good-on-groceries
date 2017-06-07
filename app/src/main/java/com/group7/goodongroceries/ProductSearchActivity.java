package com.group7.goodongroceries;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by dan on 5/31/2017.
 *
 * Provides detailed information from the USDA API on a specific product selected from {@link ItemSearchActivity}
 */

public class ProductSearchActivity extends AppCompatActivity {

    private TextView mProductNameTextView;
    private ImageView mCopyrightImageView;
    private TextView mItemNameTextView;
    private TextView mProductResultsTextView;
    private CardView mChangeProductCardView;
    private ImageView mChangeProductImageView;
    private TextView mChangeProductInfo;

    private ProgressBar mLoadingIndicatorPB;
    private TextView mLoadingErrorMessageTV;

    private ProductItem mProductItem;

    private Toast mToast;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_search);
        mProductNameTextView = (TextView) findViewById(R.id.tv_product_name);
        mCopyrightImageView = (ImageView) findViewById(R.id.iv_copyright);
        mItemNameTextView = (TextView) findViewById(R.id.tv_item_name);
        mProductResultsTextView = (TextView) findViewById(R.id.tv_product_info);
        mChangeProductCardView = (CardView) findViewById(R.id.cv_change_info);
        mChangeProductImageView = (ImageView) findViewById(R.id.iv_change_info);
        mChangeProductInfo = (TextView)findViewById(R.id.tv_change_info);
        mLoadingIndicatorPB = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        mLoadingErrorMessageTV = (TextView) findViewById(R.id.tv_loading_product_error);

        Intent intent = getIntent();
        if (null != intent && intent.hasExtra(ProductItem.PRODUCT_EXTRA_ITEM)) {
            Bundle bundle = intent.getBundleExtra(ProductItem.PRODUCT_EXTRA_ITEM);
            mProductItem = (ProductItem) bundle.getSerializable(ProductItem.PRODUCT_EXTRA_ITEM);
            mProductNameTextView.setText(mProductItem.getProductName());
            mItemNameTextView.setText(mProductItem.getItemName());
        }
        /*TODO fill out this class with necessary elements for product results.
         * It would be good to check the db for the product before making a USDA API call.
         */
        if (mProductItem.isLinked()) {
            mChangeProductCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getBaseContext(), ItemSearchActivity.class);

                    intent.putExtra(GroceryItem.EXTRA_GROCERY_ITEM, new GroceryItem(mProductItem.getItemName()));
                    startActivity(intent);
                }
            });
            mChangeProductInfo.setVisibility(View.VISIBLE);
            mChangeProductImageView.setVisibility(View.VISIBLE);
        }
        mProductResultsTextView.setText("Results will be found here.");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.product_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //TODO Add actually linking product name and information to the item.
        switch (item.getItemId()) {
            case R.id.action_link:
                if (null != mToast) {
                    mToast.cancel();
                }
                //TODO might want to use db to determine links, e.g. have foreign keys for products and items in the db
                if (mProductItem.isLinked()) {
                    mToast = Toast.makeText(this, mProductItem.getProductName() + " is already linked to item " + mProductItem.getItemName(), Toast.LENGTH_LONG);
                } else {
                    mProductItem.setLinked(true);
                    mToast = Toast.makeText(this, mProductItem.getProductName() + " is now linked to item " + mProductItem.getItemName(), Toast.LENGTH_LONG);

                    mChangeProductCardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getBaseContext(), ItemSearchActivity.class);

                            intent.putExtra(GroceryItem.EXTRA_GROCERY_ITEM, new GroceryItem(mProductItem.getItemName()));
                            startActivity(intent);
                        }
                    });
                    mChangeProductInfo.setVisibility(View.VISIBLE);
                    mChangeProductImageView.setVisibility(View.VISIBLE);
                }
                mToast.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
