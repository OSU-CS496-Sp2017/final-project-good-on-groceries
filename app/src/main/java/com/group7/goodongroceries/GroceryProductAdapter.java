package com.group7.goodongroceries;

/**
 * Created by dan on 5/17/2017.
 */

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by dan on 4/18/2017.
 */

public class GroceryProductAdapter extends RecyclerView.Adapter<GroceryProductAdapter.GroceryProductViewHolder> {
    private ArrayList<String> mGroceryProductList;
    private OnItemClickListener mProductClickListener;

    /**
     * For use in places where we need to listen to both a checkbox and the item itself.
     * @param checkedChangedListener
     * @param clickListener
     */

    /**
     * For use where we only need to listen to clicking on an item.
     * @param clickListener
     */
    public GroceryProductAdapter(OnItemClickListener clickListener) {
        mGroceryProductList = new ArrayList<String>();
        mProductClickListener = clickListener;
    }

    /**
     * Used when the View is created
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public GroceryProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.grocery_product_item, parent, false);
        return new GroceryProductViewHolder(view);
    }

    /**
     * Used when the view is updated.
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(GroceryProductViewHolder holder, int position) {
        final String groceryProduct = mGroceryProductList.get(adapterPositionToArrayIndex(position));
        holder.mProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProductClickListener.onItemClick(groceryProduct);
            }
        });
        holder.bind(groceryProduct);
    }

    @Override
    public int getItemCount() {
        return mGroceryProductList.size();
    }

    public void updateSearchResults(ArrayList<String> searchResultList) {
        mGroceryProductList = searchResultList;
        notifyDataSetChanged();
    }

    private int adapterPositionToArrayIndex(int position) {
        return mGroceryProductList.size() - position -1;
    }

    public interface OnItemClickListener {
        //TODO replace "String item" with utils class item object.
        void onItemClick(String item);
    }

    class GroceryProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CardView mProductCV;
        private TextView mProductTextView;
        private ImageView mProductImage;

        public GroceryProductViewHolder(final View itemView) {
            super(itemView);
            mProductCV = (CardView)itemView.findViewById(R.id.cv_product);
            mProductTextView = (TextView) itemView.findViewById(R.id.tv_product_text);
            mProductImage = (ImageView) itemView.findViewById(R.id.iv_product_launcher);
            mProductImage.setOnClickListener(this);
        }

        public void bind(String item) {
            mProductTextView.setText(item);
        }

        @Override
        public void onClick(View v) {
            String item = mGroceryProductList.get(adapterPositionToArrayIndex(getAdapterPosition()));
            mProductClickListener.onItemClick(item);
        }

    }
}
