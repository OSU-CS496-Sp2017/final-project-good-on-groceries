package com.group7.goodongroceries;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.group7.goodongroceries.items.GroceryItem;
import com.group7.goodongroceries.items.ItemSearchActivity;
import com.group7.goodongroceries.items.ProductSearchActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity
        implements GroceryItemAdapter.OnItemCheckedChangeListener,
        GroceryItemAdapter.OnGroceryItemClickListener,
        LoaderManager.LoaderCallbacks<String> {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String ITEM_KEY = "groceryListItemName";
    private static final int MAIN_LOADER_ID = 0;

    //TODO temporary value for testing
    private static final String DEFAULT_ITEM_NAME= "garbonzo beans";

    private RecyclerView mGroceryItemsRV;
    private GroceryItemAdapter mGroceryItemAdapter;
    private EditText mItemEntryBoxET;

    private ArrayList<GroceryItem> mGroceryList;

    private Toast mGroceryToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGroceryList = null;

        mItemEntryBoxET = (EditText)findViewById(R.id.et_item_entry_box);
        mGroceryItemsRV = (RecyclerView)findViewById(R.id.rv_item);

        mGroceryItemAdapter = new GroceryItemAdapter(this, this);
        mGroceryItemsRV.setAdapter(mGroceryItemAdapter);
        mGroceryItemsRV.setLayoutManager(new LinearLayoutManager(this));
        mGroceryItemsRV.setHasFixedSize(true);

        getSupportLoaderManager().initLoader(MAIN_LOADER_ID, null, this);

        Button addItemButton = (Button)findViewById(R.id.btn_add_item);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemText = mItemEntryBoxET.getText().toString();
                if (!TextUtils.isEmpty(itemText)) {

                }
                updateList(mItemEntryBoxET.getText().toString());
                mItemEntryBoxET.setText("");
            }
        });
    }

    @Override
    public void onItemClick(GroceryItem groceryItem) {
        Intent intent = new Intent(this, ItemSearchActivity.class);
        intent.putExtra(GroceryItem.EXTRA_GROCERY_ITEM, groceryItem);
        startActivity(intent);
    }

    @Override
    public void onItemCheckChange(String item, boolean isChecked) {
        if(null != mGroceryToast) {
            mGroceryToast.cancel();
        }
        String statusMessage = isChecked ? "COMPLETED" : "MARKED INCOMPLETE";
        mGroceryToast = Toast.makeText(this, statusMessage + ": " + item, Toast.LENGTH_LONG);
        mGroceryToast.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch (item.getItemId()) {
            case R.id.action_delete:
                if (!mGroceryItemAdapter.itemSelected()) {
                    if(null != mGroceryToast) {
                        mGroceryToast.cancel();
                    }
                    mGroceryToast = Toast.makeText(MainActivity.this,"No items selected!",Toast.LENGTH_LONG);
                    mGroceryToast.show();
                    return true;
                }
                builder.setTitle("Delete Selected Items")
                .setMessage("Are you sure?")
                .setIcon(R.drawable.ic_action_delete_single)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mGroceryItemAdapter.removeGroceryItems();
                    }
                })
                .setNegativeButton("No", null)
                .show();

                return true;
            case R.id.action_clear:
                if (0 == mGroceryItemAdapter.getItemCount()) {
                    if(null != mGroceryToast) {
                        mGroceryToast.cancel();
                    }
                    mGroceryToast = Toast.makeText(MainActivity.this,"No items to remove!",Toast.LENGTH_LONG);
                    mGroceryToast.show();
                    return true;
                }
                builder.setTitle("Delete All Items")
                .setMessage("Are you sure?")
                .setIcon(R.drawable.ic_action_clear)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d(this.getClass().getSimpleName(), "Clearing all items");
                    mGroceryItemAdapter.clearGroceryList();                    }
                })
                .setNegativeButton("No", null)
                .show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public Loader<String> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<String>(this) {

            String mGroceryListString;

            @Override
            protected void onStartLoading () {
                if (null != args) {
                    if (null != mGroceryListString) {
                        Log.d(TAG, "AsyncTaskLoader delivering cached results.");
                        deliverResult(mGroceryListString);
                    } else {
                        forceLoad();

                    }
                }
            }

            @Override
            public String loadInBackground() {
                // TODO update list with new item here.
                String item = "";
                if (null != args) {
                    if (args.containsKey(ITEM_KEY)) {
                        item = args.getString(ITEM_KEY);
                    }
//                    Log.d(TAG, "AsyncTaskLoader making item list: ");
//                    item = item + ",milk,bread,cookies,licorice,crackers,soup";

                    return item;
                } else {
                    return null;
                }
            }

            @Override
            public void deliverResult (String data) {
                mGroceryListString = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        Log.d(TAG, "AsyncTaskLoader's onLoadFinished called");
        if (null != data) {
            mGroceryItemsRV.setVisibility(View.VISIBLE);
//            mGroceryList = constructArrayList(data);
            mGroceryItemsRV.scrollToPosition(0);
            mGroceryItemAdapter.addGroceryItem(data);
            mItemEntryBoxET.setText("");
        } else {
            mGroceryItemsRV.setVisibility(View.INVISIBLE);
        }
    }

    private ArrayList<GroceryItem> constructArrayList (String listString) {
        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(listString.split(",")));
        ArrayList<GroceryItem> forecastItems = new ArrayList<>();
        for (String item : arrayList) {
            GroceryItem forecastItem = new GroceryItem(item);
//            forecastItem.itemName = item;
            forecastItems.add(forecastItem);
        }
        return forecastItems;
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        // Nothing to do.
    }

    public void updateList(String itemName) {
        if (null == itemName || itemName.isEmpty()) {
            itemName = DEFAULT_ITEM_NAME;
        }

        Log.d(TAG, "got item: " + itemName);

        Bundle bundleArgs = new Bundle();
        bundleArgs.putString(ITEM_KEY, itemName);
        getSupportLoaderManager().restartLoader(MAIN_LOADER_ID, bundleArgs, this);
    }
}