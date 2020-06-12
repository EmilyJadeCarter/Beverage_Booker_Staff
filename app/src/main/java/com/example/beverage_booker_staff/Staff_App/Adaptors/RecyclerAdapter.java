package com.example.beverage_booker_staff.Staff_App.Adaptors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.beverage_booker_staff.R;
import com.example.beverage_booker_staff.Staff_App.Models.OrderItems;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    private ArrayList<OrderItems> orderItems;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public interface OnClickListener {
        void onItemClick(int position);
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView mOrderID;
        TextView mOrderStatus;

        Button mStartOrder;

        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            mOrderID = itemView.findViewById(R.id.orderID);
            mOrderStatus = itemView.findViewById(R.id.orderStatus);

            mStartOrder.setOnClickListener(new View.OnClickListener() {
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

    public RecyclerAdapter(ArrayList<OrderItems> listItems) {
        orderItems = listItems;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        RecyclerViewHolder rvh = new RecyclerViewHolder(v);
        return rvh;
    }

    //Pass values to the views
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        OrderItems currentItem = orderItems.get(position);

        holder.mOrderID.setText(String.valueOf(currentItem.getOrderID()));
        holder.mOrderStatus.setText(currentItem.getStatus());

    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    //start order button listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}

