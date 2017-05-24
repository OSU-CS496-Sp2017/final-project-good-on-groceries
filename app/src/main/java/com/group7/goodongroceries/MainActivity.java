package com.group7.goodongroceries;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements GroceryItemAdapter.OnGroceryCheckedChangeListener {

    private RecyclerView mGroceryListRecyclerView;
    private EditText mGroceryEntryEditText;
    private GroceryItemAdapter mGroceryItemAdapter;

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
    public void onItemCheckChange(String item, boolean isChecked) {
        if(null != mGroceryToast) {
            mGroceryToast.cancel();
        }
        String statusMessage = isChecked ? "COMPLETED" : "MARKED INCOMPLETE";
        mGroceryToast = Toast.makeText(this, statusMessage + ": " + item, Toast.LENGTH_LONG);
        mGroceryToast.show();
    }
}
