package com.example.beverage_booker_staff.Staff_App.Adaptors;


import android.content.Context;
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

    Context mContext;
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
                    /*if(mDone.isChecked()==true){
                        updateItemStatus(mItemID, 1);}
                    else if(mDone.isChecked()==false){
                        updateItemStatus(mItemID, 0);
                    }*/
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
        this.mContext=mContext;
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

        int itemStatus = currentItem.getItemStatus();
        System.out.println("ItemStatus: " + itemStatus);

        holder.mItemID.setText(String.valueOf(currentItem.getItemID()));
        holder.mItemTitle.setText(currentItem.getItemTitle());
        holder.mItemQuantity.setText(String.valueOf(currentItem.getQuantity()));

        if (itemStatus == 1) {
            holder.mDone.setChecked(true);
        } 

    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    /*public void updateItemStatus(TextView itemID, int i){
        String cartID = ViewCartItemsActivity.getCartID();
        //update cart item status from checkbox
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .updateCartItemStatus(cartID, itemID.toString(), String.valueOf(i));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 402) {
                    Toast.makeText(mContext, "An error occurred when updating databases", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }*/
}