package com.example.beverage_booker_staff.Staff_App.Adaptors;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beverage_booker_staff.R;
import com.example.beverage_booker_staff.Staff_App.Models.CartItems;

import java.util.ArrayList;

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

        TextView mItemMilk;
        TextView mItemSugar;
        TextView mItemDecaf;
        TextView mItemVanilla;
        TextView mItemCaramel;
        TextView mItemChocolate;
        TextView mItemWhippedCream;
        TextView mItemFrappe;
        TextView mItemHeated;
        TextView mItemComment;

        CheckBox mDone;

        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemTitle = itemView.findViewById(R.id.cartItemName);
            mItemQuantity = itemView.findViewById(R.id.cartItemQuantity);
            mItemMilk = itemView.findViewById(R.id.cartItemMilk);
            mItemSugar = itemView.findViewById(R.id.cartItemSugar);
            mItemDecaf = itemView.findViewById(R.id.cartItemDecaf);
            mItemVanilla = itemView.findViewById(R.id.cartItemVanilla);
            mItemCaramel = itemView.findViewById(R.id.cartItemCaramel);
            mItemChocolate = itemView.findViewById(R.id.cartItemChocolate);
            mItemWhippedCream = itemView.findViewById(R.id.cartItemWhippedCream);
            mItemFrappe = itemView.findViewById(R.id.cartItemFrappe);
            mItemHeated = itemView.findViewById(R.id.cartItemHeated);
            mItemComment = itemView.findViewById(R.id.cartItemComment);

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

        //holder.mItemID.setText(String.valueOf(currentItem.getItemID()));
        holder.mItemTitle.setText(currentItem.getItemTitle());
        holder.mItemQuantity.setText(String.valueOf(currentItem.getQuantity()));
        holder.mItemMilk.setText(currentItem.getItemMilk());
        holder.mItemSugar.setText(currentItem.getItemSugar());
        holder.mItemDecaf.setText(currentItem.getItemDecaf());
        holder.mItemVanilla.setText(currentItem.getItemVanilla());
        holder.mItemCaramel.setText(currentItem.getItemCaramel());
        holder.mItemChocolate.setText(currentItem.getItemChocolate());
        holder.mItemWhippedCream.setText(currentItem.getItemWhippedCream());
        holder.mItemFrappe.setText(currentItem.getItemFrappe());
        holder.mItemHeated.setText(currentItem.getItemHeated());
        holder.mItemComment.setText(currentItem.getItemComment());
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

}