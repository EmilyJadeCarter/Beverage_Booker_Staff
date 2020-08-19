package com.example.beverage_booker_staff.Staff_App.Adaptors;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.beverage_booker_staff.R;
import com.example.beverage_booker_staff.Staff_App.Models.CartItems;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewCartItems extends RecyclerView.Adapter<ViewCartItems.RecyclerViewHolder> {

    private ArrayList<CartItems> cartItems;
    private ViewCartItems.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    //finish order button listener
    public void setOnItemClickListener(ViewCartItems.OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnClickListener {
        void onItemClick(int position);
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView mItemID;
        TextView mItemTitle;
        TextView mItemQuantity;
        CheckBox mDone;

        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemID = itemView.findViewById(R.id.textView_itemID);
            mItemTitle = itemView.findViewById(R.id.textView_itemTitle);
            mItemQuantity = itemView.findViewById(R.id.itemQuantityValue);
            mDone = itemView.findViewById(R.id.checkBox_complete);
            mDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }

    public ViewCartItems(ArrayList<CartItems> listItems) {
        cartItems = listItems;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_individual_item, parent, false);
        RecyclerViewHolder rvh = new RecyclerViewHolder(v);
        return rvh;
    }

    //Pass values to the views
    @Override
    public void onBindViewHolder(@NonNull ViewCartItems.RecyclerViewHolder holder, int position) {
        CartItems currentItem = cartItems.get(position);

        holder.mItemID.setText(String.valueOf(currentItem.getItemID()));
        holder.mItemTitle.setText(currentItem.getItemTitle());
        holder.mItemQuantity.setText(String.valueOf(currentItem.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

}