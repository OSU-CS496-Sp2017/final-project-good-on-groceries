package com.group7.goodongroceries;

/**
 * Created by dan on 5/17/2017.
 */

import android.provider.ContactsContract;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.group7.goodongroceries.items.GroceryItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dan on 4/18/2017.
 */

public class GroceryItemAdapter extends RecyclerView.Adapter<GroceryItemAdapter.GroceryItemViewHolder> {

    private ArrayList<GroceryItem> mGroceryList;
    private OnGroceryItemClickListener mGroceryItemClickListener;
    private OnItemCheckedChangeListener mCheckedChangedListener;

    /**
     * For use in places where we need to listen to both a checkbox and the item itself.
     * @param checkedChangeListener
     * @param clickListener
     */
    public GroceryItemAdapter(OnGroceryItemClickListener clickListener, OnItemCheckedChangeListener checkedChangeListener) {
        mGroceryList = new ArrayList<GroceryItem>();
        mGroceryItemClickListener = clickListener;
        mCheckedChangedListener = checkedChangeListener;
    }

    public interface OnGroceryItemClickListener {
        void onItemClick(GroceryItem groceryItem);
    }

    public interface OnItemCheckedChangeListener {
        void onItemCheckChange(String item, boolean isChecked);
    }

//    public void updateGroceryList(String item) {
////        public void updateGroceryList(ArrayList<GroceryItem> groceryItems) {
//        mGroceryList.add(new GroceryItem(item));
//        notifyDataSetChanged();
//    }

    /**
     * Removes and item from the grocery list.
     *
     * @return boolean
     */
    public boolean removeGroceryItems(){
        boolean removed = false;
        if (0 == mGroceryList.size()) {
            return removed;
        }

        for (int i = 0; i < mGroceryList.size(); i++) {
            GroceryItem item = mGroceryList.get(i);
            if (item.isChecked()) {
                Log.d(this.getClass().getSimpleName(), "removing item [ " + item.getItemName() + " ]");
                mGroceryList.remove(item);
                notifyDataSetChanged();
                removed = true;
            }
        }
        return removed;
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

    public void addGroceryItem(String item) {
        mGroceryList.add(new GroceryItem(item));
        notifyItemInserted(0);
        notifyDataSetChanged();
    }

    public void clearGroceryList() {
        //TODO need to figure out how to clear the checkboxes along with the items
        mGroceryList.clear();
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
                mCheckedChangedListener.onItemCheckChange(item.getItemName(), cb.isChecked());
            }
        });

        holder.mInfoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(this.getClass().getSimpleName(), "info icon works!");
                GroceryItem groceryItem = mGroceryList.get(adapterPositionToArrayIndex(pos));
                mGroceryItemClickListener.onItemClick(groceryItem);
            }
        });

        mGroceryList.get(pos).setPosition(pos);
        holder.bind(mGroceryList.get(pos));
    }

    class GroceryItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mItemTextView;
        private CheckBox mCheckbox;
        private ImageView mInfoImage;
        private CardView mItemCV;

        public GroceryItemViewHolder(View itemView) {
            super(itemView);
            mItemCV = (CardView) itemView.findViewById(R.id.cv_item);
            mItemTextView = (TextView) itemView.findViewById(R.id.tv_item_name);
//            itemView.setOnClickListener(this);
            mCheckbox = (CheckBox) itemView.findViewById(R.id.item_checkbox);
            mCheckbox.setChecked(false);

            mInfoImage = (ImageView)itemView.findViewById(R.id.iv_item_launcher);
//            mInfoImage.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.i(this.getClass().getSimpleName(), "info icon works!");
//                    GroceryItem groceryItem = mGroceryList.get(adapterPositionToArrayIndex(getAdapterPosition()));
//                    mGroceryItemClickListener.onItemClick(groceryItem);
//                }
//            });
//            mInfoImage.setOnClickListener(this);

//            mCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    int position = adapterPositionToArrayIndex(getAdapterPosition());
//                    GroceryItem item = mGroceryList.get(position);
////                    mCheckbox.setTag(position, mCheckbox);
////                    GroceryItem item = mGroceryList.get(getAdapterPosition());
//                    mCheckedChangedListener.onItemCheckChange(item.itemName, isChecked);
//                }
//            });
        }

//        public void removeFromList() {
//            int position = getAdapterPosition();
//            mGroceryList.remove(adapterPositionToArrayIndex(position));
//            notifyItemRemoved(adapterPositionToArrayIndex(position));
//        }

        public void bind(GroceryItem groceryItem) {
            String itemName = groceryItem.getItemName();
            mItemTextView.setText(itemName);
        }

        @Override
        public void onClick(View v) {
            GroceryItem groceryItem = mGroceryList.get(adapterPositionToArrayIndex(getAdapterPosition()));
            mGroceryItemClickListener.onItemClick(groceryItem);
        }
    }
}