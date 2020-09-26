package com.example.beverage_booker_staff.Staff_App.Adaptors;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beverage_booker_staff.R;
import com.example.beverage_booker_staff.Staff_App.API.RetrofitClient;
import com.example.beverage_booker_staff.Staff_App.Activities.ViewCartItemsActivity;
import com.example.beverage_booker_staff.Staff_App.Models.CartItems;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewCartItems extends RecyclerView.Adapter<ViewCartItems.RecyclerViewHolder> {

    Context mContext;
    private ArrayList<CartItems> cartItems;
    private ViewCartItems.OnItemClickListener mListener;
    private int ticks;

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
        TextView textViewSizeTitle;
        TextView textViewSize;
        TextView textViewMilk;
        TextView textViewSugar;
        TextView textViewDecaf;
        TextView textViewVanilla;
        TextView textViewCaramel;
        TextView textViewChocolate;
        TextView textViewWhippedCream;
        TextView textViewFrappe;
        TextView textViewHeated;
        TextView textViewCommentTitle;
        TextView textViewComment;
        TextView mItemQuantity;
        CheckBox mDone;


        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            //mItemID = itemView.findViewById(R.id.textView_itemID);
            mItemTitle = itemView.findViewById(R.id.cartItemName);
            textViewSizeTitle = itemView.findViewById(R.id.sizeTitle);
            textViewSize = itemView.findViewById(R.id.cartItemSize);
            textViewMilk = itemView.findViewById(R.id.cartItemMilk);
            textViewSugar = itemView.findViewById(R.id.cartItemSugar);
            textViewDecaf = itemView.findViewById(R.id.cartItemDecaf);
            textViewVanilla = itemView.findViewById(R.id.cartItemVanilla);
            textViewCaramel = itemView.findViewById(R.id.cartItemCaramel);
            textViewChocolate = itemView.findViewById(R.id.cartItemChocolate);
            textViewWhippedCream = itemView.findViewById(R.id.cartItemWhippedCream);
            textViewFrappe = itemView.findViewById(R.id.cartItemFrappe);
            textViewHeated = itemView.findViewById(R.id.cartItemHeated);
            textViewCommentTitle = itemView.findViewById(R.id.commentTitle);
            textViewComment = itemView.findViewById(R.id.cartItemComment);
            mItemQuantity = itemView.findViewById(R.id.cartItemQuantity);
            mDone = itemView.findViewById(R.id.checkBox_complete);
            mDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if(mDone.isChecked()==true){
                            updateItemStatus(position, 1);
                        }
                        else if(mDone.isChecked()==false){
                            updateItemStatus(position, 0);
                        }
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

        String currentItemSize = currentItem.getItemSize();
        System.out.println(currentItemSize);
        String currentItemMilk = currentItem.getItemMilk();
        String currentItemSugar = currentItem.getItemSugar();
        String currentItemDecaf = currentItem.getItemDecaf();
        String currentItemVanilla = currentItem.getItemVanilla();
        String currentItemCaramel = currentItem.getItemCaramel();
        String currentItemChocolate = currentItem.getItemChocolate();
        String currentItemWhippedCream = currentItem.getItemWhippedCream();
        String currentItemFrappe = currentItem.getItemFrappe();
        String currentItemHeated = currentItem.getItemHeated();
        String currentItemComment = currentItem.getItemComment();

        int itemStatus = currentItem.getItemStatus();
        System.out.println("ItemStatus: " + itemStatus);

        //holder.mItemID.setText(String.valueOf(currentItem.getItemID()));
        holder.mItemTitle.setText(currentItem.getItemTitle());
        holder.textViewSize.setText(currentItem.getItemSize());
        holder.mItemQuantity.setText(String.valueOf(currentItem.getQuantity()));

        //Show only applicable menu option fields
        if (!currentItemSize.equals("-")) {
            holder.textViewSizeTitle.setVisibility(TextView.VISIBLE);
            holder.textViewSize.setVisibility(TextView.VISIBLE);
            holder.textViewSize.setText(currentItem.getItemSize());
        }

        if (!currentItemMilk.equals("-")) {
            holder.textViewMilk.setVisibility(TextView.VISIBLE);
            holder.textViewMilk.setText(currentItem.getItemMilk());
        }

        if (!currentItemSugar.equals("-")) {
            holder.textViewSugar.setVisibility(TextView.VISIBLE);
            holder.textViewSugar.setText(currentItem.getItemSugar());
        }

        if (!currentItemDecaf.equals("-")) {
            holder.textViewDecaf.setVisibility(TextView.VISIBLE);
            holder.textViewDecaf.setText(currentItem.getItemDecaf());
        }

        if (!currentItemVanilla.equals("-")) {
            holder.textViewVanilla.setVisibility(TextView.VISIBLE);
            holder.textViewVanilla.setText(currentItem.getItemVanilla());
        }

        if (!currentItemCaramel.equals("-")) {
            holder.textViewCaramel.setVisibility(TextView.VISIBLE);
            holder.textViewCaramel.setText(currentItem.getItemCaramel());
        }

        if (!currentItemChocolate.equals("-")) {
            holder.textViewChocolate.setVisibility(TextView.VISIBLE);
            holder.textViewChocolate.setText(currentItem.getItemChocolate());
        }

        if (!currentItemWhippedCream.equals("-")) {
            holder.textViewWhippedCream.setVisibility(TextView.VISIBLE);
            holder.textViewWhippedCream.setText(currentItem.getItemWhippedCream());
        }

        if (!currentItemFrappe.equals("-")) {
            holder.textViewFrappe.setVisibility(TextView.VISIBLE);
            holder.textViewFrappe.setText(currentItem.getItemFrappe());
        }

        if (!currentItemHeated.equals("-")) {
            holder.textViewHeated.setVisibility(TextView.VISIBLE);
            holder.textViewHeated.setText(currentItem.getItemHeated());
        }

        if (!currentItemComment.equals("-")) {
            holder.textViewCommentTitle.setVisibility(TextView.VISIBLE);
            holder.textViewComment.setVisibility(TextView.VISIBLE);
            holder.textViewComment.setText(currentItem.getItemComment());
        }

        if (itemStatus == 1) {
            holder.mDone.setChecked(true);
            System.out.println("True");
            ticks = ticks + 1;
        }
        else if (itemStatus == 0) {
            holder.mDone.setChecked(false);
            System.out.println("False");
            ticks = ticks - 1;
        }
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public void updateItemStatus(int position, int itemStatus){
        //update cart item status from checkbox
        String cartID = ViewCartItemsActivity.getCartID();
        CartItems itemCart = cartItems.get(position);
        int itemID = itemCart.getItemID();

        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .updateCartItemStatus(cartID, itemID, itemStatus);
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
    }
}