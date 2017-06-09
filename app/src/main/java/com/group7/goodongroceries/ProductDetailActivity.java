package com.group7.goodongroceries;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
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

import com.group7.goodongroceries.Models.NutritionResult.Food;
import com.group7.goodongroceries.Models.NutritionResult.Nutrient;
import com.group7.goodongroceries.Models.NutritionResult.USDANutritionResult;
import com.group7.goodongroceries.Models.SearchResult.SearchResultItem;
import com.group7.goodongroceries.Models.USDAObjectMapper;
import com.group7.goodongroceries.Utils.NetworkUtils;
import com.group7.goodongroceries.Utils.USDAUtils;
import com.group7.goodongroceries.data.GroceryListContract;
import com.group7.goodongroceries.data.GroceryListDBHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dan on 5/31/2017.
 *
 * Provides detailed information from the USDA API on a specific product selected from {@link ItemSearchActivity}
 */

public class ProductDetailActivity extends AppCompatActivity {

    private TextView mProductNameTextView;
    private ImageView mCopyrightImageView;
    private TextView mItemNameTextView;
    private TextView mProductResultsTextView;
    private CardView mChangeProductCardView;
    private ImageView mChangeProductImageView;
    private TextView mChangeProductInfo;

    private ProgressBar mLoadingIndicatorPB;
    private TextView mLoadingErrorMessageTV;

    private GroceryItem mGroceryItem;
    //private SearchResultItem mSearchResultItem;
    private String mNdbNo;
    private String mProductName;

    private Toast mToast;

    private SQLiteDatabase mDB;

    //TODO testing changing the menu icon
    private Menu mActionBarMenu;

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
        mLoadingIndicatorPB = (ProgressBar) findViewById(R.id.pb_product_indicator);
        mLoadingErrorMessageTV = (TextView) findViewById(R.id.tv_loading_product_error);
        GroceryListDBHelper dbHelper = new GroceryListDBHelper(this);
        mDB = dbHelper.getWritableDatabase();

        Intent intent = getIntent();
        if (null != intent
                && intent.hasExtra(SearchResultItem.PRODUCT_EXTRA_ITEM)
                && intent.hasExtra(GroceryItem.EXTRA_GROCERY_ITEM)) {
            mGroceryItem = (GroceryItem) intent.getSerializableExtra(GroceryItem.EXTRA_GROCERY_ITEM);
            SearchResultItem searchResultItem = (SearchResultItem) intent.getSerializableExtra(SearchResultItem.PRODUCT_EXTRA_ITEM);
            mNdbNo = searchResultItem.getNdbno();
            mProductName = searchResultItem.getName();
            mItemNameTextView.setText(mGroceryItem.getItemName());
        } else if(null != intent
                && !intent.hasExtra(SearchResultItem.PRODUCT_EXTRA_ITEM)
                && intent.hasExtra(GroceryItem.EXTRA_GROCERY_ITEM)) {
            //No search item passed in.
            mGroceryItem = (GroceryItem) intent.getSerializableExtra(GroceryItem.EXTRA_GROCERY_ITEM);
            mNdbNo = mGroceryItem.getLinkedProductId();
            mItemNameTextView.setText(mGroceryItem.getItemName());

        }


        /*TODO fill out this class with necessary elements for product results.
         * It would be good to check the db for the product before making a USDA API call.
         */
        if (mGroceryItem != null) {
            mChangeProductCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getBaseContext(), ItemSearchActivity.class);

                    intent.putExtra(GroceryItem.EXTRA_GROCERY_ITEM, new GroceryItem(mGroceryItem.getItemName()));
                    startActivity(intent);
                }
            });
            mChangeProductInfo.setVisibility(View.VISIBLE);
            mChangeProductImageView.setVisibility(View.VISIBLE);
        }
        mProductResultsTextView.setText("Results will be found here.");
        new NutritionSearchTask().execute(mNdbNo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.product_detail, menu);
        //TODO check to see if the product is linked here, set the link icon in the menu accordingly:
        // menu.findItem(R.id.action_link).setIcon(R.drawable.ic_ulinked);
        // menu.findItem(R.id.action_link).setIcon(R.drawable.ic_linked);
        mActionBarMenu = menu;

        if (null != mActionBarMenu) {
            if (mGroceryItem.isLinked()){
                mActionBarMenu.findItem(R.id.action_link).setIcon(R.drawable.ic_linked);
            } else {
                mActionBarMenu.findItem(R.id.action_link).setIcon(R.drawable.ic_unlinked);
            }

        }

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
                if (mGroceryItem.isLinked()) {
                    //TODO remove link from product to item from the db
                    mGroceryItem.setLinkedProductId(null);
                    String sqlSelection = GroceryListContract.ListItems.COLUMN_ENTRY +  " = ? ";
                    String[] sqlSelectionArgs = { mGroceryItem.getItemName() };
                    ContentValues values = new ContentValues();
                    values.putNull(GroceryListContract.ListItems.COLUMN_FOOD_ID);
                    mDB.update(GroceryListContract.ListItems.TABLE_NAME, values, sqlSelection, sqlSelectionArgs);

                    //disable the "Change Info" button so that it can't be clicked after unlinking
//                    mChangeProductCardView.setVisibility(View.INVISIBLE);
                    mChangeProductCardView.setVisibility(View.GONE);
                    mChangeProductCardView.setOnClickListener(null);
                    mToast = Toast.makeText(this, mProductName + " is no longer linked to item " + mGroceryItem.getItemName(), Toast.LENGTH_LONG);
                    mActionBarMenu.findItem(R.id.action_link).setIcon(R.drawable.ic_unlinked);
                } else {
                    mGroceryItem.setLinkedProductId(mNdbNo);
                    String sqlSelection = GroceryListContract.ListItems.COLUMN_ENTRY +  " = ? ";
                    String[] sqlSelectionArgs = { mGroceryItem.getItemName() };
                    ContentValues values = new ContentValues();
                    values.put(GroceryListContract.ListItems.COLUMN_FOOD_ID, mNdbNo);
                    mDB.update(GroceryListContract.ListItems.TABLE_NAME, values, sqlSelection, sqlSelectionArgs);
                    mToast = Toast.makeText(this, mProductName + " is now linked to item " + mGroceryItem.getItemName(), Toast.LENGTH_LONG);
                    mChangeProductCardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getBaseContext(), ItemSearchActivity.class);

                            intent.putExtra(GroceryItem.EXTRA_GROCERY_ITEM, new GroceryItem(mGroceryItem.getItemName()));
                            startActivity(intent);
                        }
                    });
                    mChangeProductCardView.setVisibility(View.VISIBLE);
                    if (null != mActionBarMenu) {
                        mActionBarMenu.findItem(R.id.action_link).setIcon(R.drawable.ic_linked);
                    }
                    mChangeProductInfo.setVisibility(View.VISIBLE);
                    mChangeProductImageView.setVisibility(View.VISIBLE);
                }
                mToast.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void displayResults(USDANutritionResult result) {
        //TODO: display
        Food food = null;
        for (Food listItem : result.getReport().getFoods()) {
            if (listItem.getNdbno().equals(mNdbNo)) {
                food = listItem;
                break;
            }
        }
        List<Nutrient> nutrients = food.getNutrients();
        String details = new String("");
        for(Nutrient nutrient : nutrients ) {
            details += nutrient.toString() + "\n";
        }

        mProductResultsTextView.setText(details);
        mProductName = food.getName();
        mProductNameTextView.setText(mProductName);

    }

    public class NutritionSearchTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicatorPB.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            String ndbNo = params[0];
            String url = USDAUtils.buildNutrientQueryURL(ndbNo);

            try {
                String result = NetworkUtils.doHTTPGet(url);
                return result;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
            if(s != null) {
                try {
                    USDANutritionResult result = USDAObjectMapper.NutritionResultConverter(s);
                    displayResults(result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                //its busted
                mLoadingErrorMessageTV.setVisibility(View.VISIBLE);
            }
        }
    }
}
