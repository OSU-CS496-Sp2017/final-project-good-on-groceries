package com.group7.goodongroceries;

/**
 * Created by dan on 5/17/2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;


import java.util.ArrayList;

/**
 * Created by dan on 4/18/2017.
 */

public class GroceryItemAdapter extends RecyclerView.Adapter<GroceryItemAdapter.GroceryItemViewHolder> {
    private ArrayList<String> mGroceryList;

    private OnGroceryCheckedChangeListener mCheckedChangedListener;
    public GroceryItemAdapter(OnGroceryCheckedChangeListener checkedChangedListener) {
        mGroceryList = new ArrayList<String>();
        mCheckedChangedListener = checkedChangedListener;
    }

    /**
     * Used when the View is created
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public GroceryItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.grocery_list_item, parent, false);
        return new GroceryItemViewHolder(view);
    }

    /**
     * Used when the view is updated.
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(GroceryItemViewHolder holder, int position) {
        String groceryItem = mGroceryList.get(adapterPositionToArrayIndex(position));
        holder.bind(groceryItem);
    }

    @Override
    public int getItemCount() {
        return mGroceryList.size();
    }

    public void addGroceryItem(String item) {
        mGroceryList.add(item);
        notifyItemInserted(0);
    }

    private int adapterPositionToArrayIndex(int position) {
        return mGroceryList.size() - position -1;
    }

    public interface OnGroceryCheckedChangeListener {
        void onItemCheckChange(String item, boolean isChecked);
    }

    class GroceryItemViewHolder extends RecyclerView.ViewHolder {
        private TextView mItemTextView;

        public GroceryItemViewHolder(final View itemView) {
            super(itemView);
            mItemTextView = (TextView)itemView.findViewById(R.id.tv_item_text);

            CheckBox checkBox = (CheckBox)itemView.findViewById(R.id.item_checkbox);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    String item = mGroceryList.get(adapterPositionToArrayIndex(getAdapterPosition()));
                    mCheckedChangedListener.onItemCheckChange(item, isChecked);
                }
            });
        }

        public void removeFromList() {
            int position = getAdapterPosition();
            mGroceryList.remove(adapterPositionToArrayIndex(position));
            notifyItemRemoved(position);
        }

        public void bind(String item) {
            mItemTextView.setText(item);
        }

    }
}
