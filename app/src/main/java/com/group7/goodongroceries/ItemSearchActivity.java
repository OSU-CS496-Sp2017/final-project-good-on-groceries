package com.group7.goodongroceries;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.group7.goodongroceries.Models.SearchResult.SearchResultItem;
import com.group7.goodongroceries.Models.SearchResult.USDASearchResult;
import com.group7.goodongroceries.Models.USDAObjectMapper;
import com.group7.goodongroceries.Utils.NetworkUtils;
import com.group7.goodongroceries.Utils.USDAUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dan on 5/31/2017.
 *
 * Provides the Activity for items in the grocery list.
 * The contents of the Activity are a list result of a USDA API food item query.
 * example:
 * - "Milk" is an item in the grocery list.
 * - User clicks on "Milk", the new ItemSearchActivity queries the USDA database and returns
 * a list of milk products that the user can click on for more details (nutrition, ingredients, etc.)
 * which is displayed in {@link ProductDetailActivity}
 */

public class ItemSearchActivity extends AppCompatActivity
        implements GroceryProductAdapter.OnItemClickListener,
        LoaderManager.LoaderCallbacks<String> {

    private static final int ITEM_SEARCH_LOADER_ID = 1;
    private static final String ITEM_SEARCH_KEY = "grocery_item_search";

    private TextView mItemNameTV;
    private RecyclerView mSearchResultsRV;
    private GroceryProductAdapter mGroceryProductAdapter;
    private ProgressBar mLoadingIndicatorPB;
    private TextView mLoadingErrorMessageTV;

    private GroceryItem mItem;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_search);
        mSearchResultsRV = (RecyclerView)findViewById(R.id.rv_search_results);
        mSearchResultsRV.setLayoutManager(new LinearLayoutManager(this));
        mSearchResultsRV.setHasFixedSize(true);
        mItemNameTV = (TextView)findViewById(R.id.tv_item_name);

        mLoadingIndicatorPB = (ProgressBar)findViewById(R.id.pb_loading_indicator);
        mLoadingErrorMessageTV = (TextView)findViewById(R.id.tv_loading_search_error);
        mGroceryProductAdapter = new GroceryProductAdapter(this);
        mSearchResultsRV.setAdapter(mGroceryProductAdapter);

        Intent intent = getIntent();
        if (null != intent && intent.hasExtra(GroceryItem.EXTRA_GROCERY_ITEM)) {
            mItem = (GroceryItem) intent.getSerializableExtra(GroceryItem.EXTRA_GROCERY_ITEM);
            mItemNameTV.setText(mItem.getItemName());
        }

        // Removing for now ProductItem product = new ProductItem(null, mItem.getItemName(), false);
        Bundle argsBundle = new Bundle();
        argsBundle.putSerializable(ITEM_SEARCH_KEY, mItem);
        getSupportLoaderManager().initLoader(ITEM_SEARCH_LOADER_ID, argsBundle, this);

    }

    @Override
    public void onItemClick(SearchResultItem item) {
        Intent intent = new Intent(this, ProductDetailActivity.class);
        intent.putExtra(SearchResultItem.PRODUCT_EXTRA_ITEM, item);
        intent.putExtra(GroceryItem.EXTRA_GROCERY_ITEM, mItem);
        startActivity(intent);
    }

    @Override
    public Loader<String> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<String>(this) {

            String mSearchResults;

            @Override
            protected void onStartLoading() {
                if (null != args) {
                    if (null != mSearchResults) {
                        deliverResult(mSearchResults);
                    } else {
                        mLoadingIndicatorPB.setVisibility(View.VISIBLE);
                        forceLoad();
                    }
                }
            }

            @Override
            public String loadInBackground() {
                if (null != args && args.containsKey(ITEM_SEARCH_KEY)) {
                    GroceryItem item = (GroceryItem)args.getSerializable(ITEM_SEARCH_KEY);
                    //TODO put search query here.
                    //TODO should return JSON result
                    String searchUrl = USDAUtils.buildSearchQueryURL(item.getItemName());
                    try {
                        mSearchResults = NetworkUtils.doHTTPGet(searchUrl);
                        return mSearchResults;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        };
    }

    /*
    private ArrayList<USDASearchResult> createTempListData(USDASearchResult item) {
        ArrayList<USDASearchResult> list = new ArrayList<>();
        for (int i = 1; i < 21; i++) {
            list.add(new USDASearchResult(" product " + i, item.getItemName(), false));
        }
        return list;
    }
    */


    @Override
    public void onLoadFinished(Loader<String> loader, String data) {

        mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
        if (null != data) {
            mLoadingErrorMessageTV.setVisibility(View.INVISIBLE);
            mSearchResultsRV.setVisibility(View.VISIBLE);
            //TODO parse data into list here. The list needs to have ProductItems: ArrayList<ProductItem>
            try {
                USDASearchResult result =  USDAObjectMapper.SearchResultConverter(data);
                result.getList().getItem();
                mGroceryProductAdapter.updateSearchResults(result.getList().getItem());
            } catch (IOException e) {
                mSearchResultsRV.setVisibility(View.INVISIBLE);
                mLoadingErrorMessageTV.setVisibility(View.VISIBLE);
                e.printStackTrace();
            }
        } else {
            mSearchResultsRV.setVisibility(View.INVISIBLE);
            mLoadingErrorMessageTV.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
