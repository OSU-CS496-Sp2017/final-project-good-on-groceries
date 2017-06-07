package com.group7.goodongroceries;

import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.group7.goodongroceries.Utils.NetworkUtils;
import com.group7.goodongroceries.Utils.USDAUtils;

import java.io.IOException;


public class MainActivity extends AppCompatActivity implements GroceryItemAdapter.OnGroceryCheckedChangeListener {

    private RecyclerView mGroceryListRecyclerView;
    private EditText mGroceryEntryEditText;
    private GroceryItemAdapter mGroceryItemAdapter;

    // Classes needed for drawer
    private ListView mDrawerList;
    private ArrayAdapter<String> mDrawerAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;
    private Toast mGroceryToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGroceryEntryEditText = (EditText) findViewById(R.id.et_item_entry_box);
        mGroceryListRecyclerView = (RecyclerView)findViewById(R.id.rv_item_list);
        mGroceryListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mGroceryListRecyclerView.setHasFixedSize(true);

        mGroceryListRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mGroceryItemAdapter = new GroceryItemAdapter(this);
        mGroceryListRecyclerView.setAdapter(mGroceryItemAdapter);

        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        addDrawerItems();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mActivityTitle = getTitle().toString();
        setupDrawer();

        Button addTodoButton = (Button) findViewById(R.id.btn_add_todo);
        addTodoButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String todoText = mGroceryEntryEditText.getText().toString();
                if (!TextUtils.isEmpty(todoText)) {
                    mGroceryListRecyclerView.scrollToPosition(0);
                    mGroceryItemAdapter.addGroceryItem(todoText);
                    mGroceryEntryEditText.setText("");
                }
            }
        });

        Button testApiButton = (Button) findViewById(R.id.btn_test_api);
        testApiButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String todoText = mGroceryEntryEditText.getText().toString();
                if (!TextUtils.isEmpty(todoText)) {
                    String result = USDAUtils.buildSearchQueryURL(todoText);
                    Log.d("MainActivity", result);
                    try {
                        String val = NetworkUtils.doHTTPGet(result);
                        Log.d("MainActivity", val);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mGroceryListRecyclerView.scrollToPosition(0);
                    mGroceryItemAdapter.addGroceryItem(result);
                    mGroceryEntryEditText.setText("");
                }
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
        itemTouchHelper.attachToRecyclerView(mGroceryListRecyclerView);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void addDrawerItems() {
        String[] osArray = { getString(R.string.user_pref), "Test USDA API" };
        mDrawerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mDrawerAdapter);
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Options");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
}
