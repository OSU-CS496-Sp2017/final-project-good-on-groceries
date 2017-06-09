package com.group7.goodongroceries;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.group7.goodongroceries.data.GroceryListDBHelper;

public class MainActivity extends AppCompatActivity
        implements GroceryItemAdapter.OnItemCheckedChangeListener,
        GroceryItemAdapter.OnGroceryItemClickListener,
        LoaderManager.LoaderCallbacks<String> {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String ITEM_KEY = "groceryListItemName";
    private static final int MAIN_LOADER_ID = 0;

    //TODO temporary value for testing, replace referenced sites with Toast about empty item
    private static final String DEFAULT_ITEM_NAME= "garbonzo beans";

    private RecyclerView mGroceryItemsRV;
    private GroceryItemAdapter mGroceryItemAdapter;
    private EditText mItemEntryBoxET;

    private Toast mGroceryToast;

    private SQLiteDatabase mDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GroceryListDBHelper dbHelper = new GroceryListDBHelper(this);
        mDB = dbHelper.getWritableDatabase();

        mItemEntryBoxET = (EditText)findViewById(R.id.et_item_entry_box);
        mGroceryItemsRV = (RecyclerView)findViewById(R.id.rv_item);

        mGroceryItemAdapter = new GroceryItemAdapter(this, this, mDB);
        mGroceryItemsRV.setAdapter(mGroceryItemAdapter);
        mGroceryItemsRV.setLayoutManager(new LinearLayoutManager(this));
        mGroceryItemsRV.setHasFixedSize(true);

        getSupportLoaderManager().initLoader(MAIN_LOADER_ID, null, this);

        Button addItemButton = (Button)findViewById(R.id.btn_add_item);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemText = mItemEntryBoxET.getText().toString();

                // Show Toast if there is no text.
                if (TextUtils.isEmpty(itemText)) {
                    if(null != mGroceryToast) {
                        mGroceryToast.cancel();
                    }
                    mGroceryToast = Toast.makeText(MainActivity.this, "You must enter an item name!", Toast.LENGTH_LONG);
                    mGroceryToast.show();
                    return;
                } else if (mGroceryItemAdapter.getGroceryList().contains(new GroceryItem(itemText))) {
                    if(null != mGroceryToast) {
                        mGroceryToast.cancel();
                    }
                    mGroceryToast = Toast.makeText(MainActivity.this, itemText + " is already in the list!", Toast.LENGTH_LONG);
                    mGroceryToast.show();
                    mItemEntryBoxET.setText("");
                    return;
                }

                // Show Toast if no letters or numbers are entered
//                if (!itemText.matches("[a-zA-Z0-9 ]")) {
//                    if(null != mGroceryToast) {
//                        mGroceryToast.cancel();
//                    }
//                    mGroceryToast = Toast.makeText(MainActivity.this, "The item name is not valid!\n Letters and numbers only.", Toast.LENGTH_LONG);
//                    mGroceryToast.show();
//                    return;
//                }
                updateList(mItemEntryBoxET.getText().toString());
                mItemEntryBoxET.setText("");
            }
        });


        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                // nothing to do here.
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                ((GroceryItemAdapter.GroceryItemViewHolder)viewHolder).removeFromList();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mGroceryItemsRV);
    }

    @Override
    public void onItemClick(GroceryItem groceryItem) {

        /*  TODO check for info in database, if info found, open product intent
         *  Need to query the db, get the info (or not), if info found, construct a new
         *  product item, set linked to "true" and call the appropriate intent.
         */
        // TODO remove dummy ProductItem
        // TODO Change boolean value to see affect on main screen info button
        // true: info button goes direction to product info screen, with option to change info
        // false: info button goes to product list screen, product info screens do not have the change info button.

        if (groceryItem.isLinked()) {
            Intent intent = new Intent(this, ProductDetailActivity.class);
            intent.putExtra(GroceryItem.EXTRA_GROCERY_ITEM, groceryItem);
            startActivity(intent);
        }else {
            Intent intent = new Intent(this, ItemSearchActivity.class);
            intent.putExtra(GroceryItem.EXTRA_GROCERY_ITEM, groceryItem);
            startActivity(intent);
         }

    }

    @Override
    public void onItemCheckChange(GroceryItem item, boolean isChecked) {
        if(null != mGroceryToast) {
            mGroceryToast.cancel();
        }
        String statusMessage = isChecked ? "COMPLETED" : "MARKED INCOMPLETE";
        mGroceryToast = Toast.makeText(this, statusMessage + ": " + item.getItemName(), Toast.LENGTH_LONG);
        mGroceryToast.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDB.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGroceryItemAdapter.refresh();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (null != mGroceryToast) {
            mGroceryToast.cancel();
        }

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
                .setPositiveButton("No", null)
                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mGroceryItemAdapter.removeGroceryItems();
                    }
                })
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
                .setPositiveButton("No", null)
                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(this.getClass().getSimpleName(), "Clearing all items");
                        mGroceryItemAdapter.clearGroceryList();                    }
                })
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
                String item = "";
                if (null != args) {
                    if (args.containsKey(ITEM_KEY)) {
                        item = args.getString(ITEM_KEY);
                    }

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
            mGroceryItemsRV.scrollToPosition(0);
            GroceryItem item = new GroceryItem(data);
            mGroceryItemAdapter.addGroceryItem(item);
            mItemEntryBoxET.setText("");
        } else {
            mGroceryItemsRV.setVisibility(View.INVISIBLE);
        }
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