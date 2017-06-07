package com.group7.goodongroceries;

/**
 * Created by dan on 5/17/2017.
 */

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


import com.group7.goodongroceries.data.GroceryListContract;
import com.group7.goodongroceries.data.GroceryListDBHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dan on 4/18/2017.
 */

public class GroceryItemAdapter extends RecyclerView.Adapter<GroceryItemAdapter.GroceryItemViewHolder> {

    private List<GroceryItem> mGroceryList;
    private OnGroceryItemClickListener mGroceryItemClickListener;
    private OnItemCheckedChangeListener mCheckedChangedListener;
    private SQLiteDatabase mDB;

    /**
     * For use in places where we need to listen to both a checkbox and the item itself.
     * @param checkedChangeListener
     * @param clickListener
     */
    public GroceryItemAdapter(OnGroceryItemClickListener clickListener,
                              OnItemCheckedChangeListener checkedChangeListener,
                              SQLiteDatabase db) {
        mDB = db;
        mGroceryList = getGroceryListFromDb();
        mGroceryItemClickListener = clickListener;
        mCheckedChangedListener = checkedChangeListener;
    }

    public interface OnGroceryItemClickListener {
        void onItemClick(GroceryItem groceryItem);
    }

    public interface OnItemCheckedChangeListener {
        void onItemCheckChange(GroceryItem item, boolean isChecked);
    }

    private List<GroceryItem> getGroceryListFromDb() {
        Cursor cursor = mDB.query(
                GroceryListContract.ListItems.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        ArrayList<GroceryItem> groceryList = new ArrayList<>();
        while (cursor.moveToNext()) {
            GroceryItem item = new GroceryItem(
                    cursor.getString(cursor.getColumnIndex(GroceryListContract.ListItems.COLUMN_ENTRY)),
                    cursor.getInt(cursor.getColumnIndex(GroceryListContract.ListItems.COLUMN_CHECKED)) != 0
            );
            groceryList.add(item);
        }
        cursor.close();
        return groceryList;
    }

    /**
     * Removes all checked items from the grocery list.
     *
     */
    public void removeGroceryItems(){

        //We need to have a double list here so we don't modify a list as we are iterating over it
        ArrayList<GroceryItem> itemsToRemove = new ArrayList<>();
        for(GroceryItem item: mGroceryList) {
            if(item.isChecked()) {
                itemsToRemove.add(item);
                String whereClause = GroceryListContract.ListItems.COLUMN_ENTRY + " = ?";
                String[] sqlSelectionArgs = {item.getItemName() };
                mDB.delete(GroceryListContract.ListItems.TABLE_NAME, whereClause, sqlSelectionArgs);
                Log.d(this.getClass().getSimpleName(), "removing item [ " + item.getItemName() + " ]");

            }
        }

        for (GroceryItem item : itemsToRemove) {
            if(mGroceryList.contains(item)) {
                mGroceryList.remove(item);
            }
        }

        notifyDataSetChanged();
    }

    public boolean itemSelected () {
        boolean isSelected = false;
        if (0 == mGroceryList.size()) {
            return isSelected;
        }
        for (GroceryItem item : mGroceryList) {
            if (item.isChecked()) {
                isSelected = true;
                break;
            }
        }
        return isSelected;
    }

    public boolean addGroceryItem(GroceryItem item) {
        //Don't add the same item twice
        if(mGroceryList.contains(item)) {
            return false;
        }

        mGroceryList.add(item);
        ContentValues values = new ContentValues();
        values.put(GroceryListContract.ListItems.COLUMN_ENTRY, item.getItemName());
        values.put(GroceryListContract.ListItems.COLUMN_CHECKED, item.isChecked() ? 1 : 0);
        mDB.insert(GroceryListContract.ListItems.TABLE_NAME, null, values);
        notifyItemInserted(0);
        notifyDataSetChanged();
        return true;
    }

    public void clearGroceryList() {
        mGroceryList.clear();
        mDB.delete(GroceryListContract.ListItems.TABLE_NAME, null, null);
        notifyDataSetChanged();
    }

    private int adapterPositionToArrayIndex(int position) {
        return mGroceryList.size() - position -1;
    }

    @Override
    public int getItemCount() {
        if (mGroceryList != null) {
            return mGroceryList.size();
        } else {
            return 0;
        }
    }

    @Override
    public GroceryItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.grocery_item, parent, false);
        return new GroceryItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GroceryItemViewHolder holder, int position) {
        final int pos = adapterPositionToArrayIndex(position);
        holder.mCheckbox.setChecked(mGroceryList.get(pos).isChecked());
        holder.mCheckbox.setTag(mGroceryList.get(pos));
        holder.mCheckbox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final CheckBox cb = (CheckBox) v;
                GroceryItem item = (GroceryItem) cb.getTag();
                item.setChecked(cb.isChecked());
                mGroceryList.get(pos).setChecked(cb.isChecked());

                //Update the DB when an item is checked off or not.
                String sqlSelection = GroceryListContract.ListItems.COLUMN_ENTRY +  " = ? ";
                String[] sqlSelectionArgs = { item.getItemName() };
                ContentValues values = new ContentValues();
                values.put(GroceryListContract.ListItems.COLUMN_CHECKED, item.isChecked() ? 1 : 0);
                mDB.update(GroceryListContract.ListItems.TABLE_NAME, values, sqlSelection, sqlSelectionArgs);

                mCheckedChangedListener.onItemCheckChange(item, cb.isChecked());
            }
        });

        holder.mInfoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroceryItem groceryItem = mGroceryList.get(pos);
                mGroceryItemClickListener.onItemClick(groceryItem);
            }
        });

        holder.bind(mGroceryList.get(pos));
    }

    class GroceryItemViewHolder extends RecyclerView.ViewHolder {
        private TextView mItemTextView;
        private CheckBox mCheckbox;
        private ImageView mInfoImage;
        private CardView mItemCV;

        public GroceryItemViewHolder(View itemView) {
            super(itemView);
            mItemCV = (CardView) itemView.findViewById(R.id.cv_item);
            mItemTextView = (TextView) itemView.findViewById(R.id.tv_item_name);
            mCheckbox = (CheckBox) itemView.findViewById(R.id.item_checkbox);
            mCheckbox.setChecked(false);
            mInfoImage = (ImageView)itemView.findViewById(R.id.iv_item_launcher);
        }

        public void removeFromList() {
            int position = getAdapterPosition();
            mGroceryList.remove(adapterPositionToArrayIndex(position));
            notifyDataSetChanged();
        }

        public void bind(GroceryItem groceryItem) {
            String itemName = groceryItem.getItemName();
            mItemTextView.setText(itemName);
        }
    }
}