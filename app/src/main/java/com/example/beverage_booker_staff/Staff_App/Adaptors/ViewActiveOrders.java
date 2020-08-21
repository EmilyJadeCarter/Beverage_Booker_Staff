package com.example.beverage_booker_staff.Staff_App.Adaptors;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.beverage_booker_staff.R;
import com.example.beverage_booker_staff.Staff_App.Models.OrderItems;
import com.example.beverage_booker_staff.Staff_App.storage.SharedPrefManager;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewActiveOrders extends RecyclerView.Adapter<ViewActiveOrders.RecyclerViewHolder> {
    private ArrayList<OrderItems> orderItems;
    private OnItemClickListener mListener;
    private int activeStaff;
    private int red = Color.parseColor("#33FF0000");
    private int green = Color.parseColor("#3300FF00");
    private int yellow = Color.parseColor("#33FFFF00");

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    //start order button listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnClickListener {
        void onItemClick(int position);
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView mOrderID;
        TextView mOrderStatus;
        Button mStartOrder;
        RelativeLayout mRelativeLayout;

        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            mOrderID = itemView.findViewById(R.id.orderID);
            mOrderStatus = itemView.findViewById(R.id.orderStatus);
            mStartOrder = itemView.findViewById(R.id.buttonStartOrder);
            mRelativeLayout = itemView.findViewById(R.id.relativeLayout);

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

    public ViewActiveOrders(Context context, ArrayList<OrderItems> listItems) {
        activeStaff = SharedPrefManager.getInstance(context).getStaff().getStaffID();
        orderItems = listItems;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_active_orders_item, parent, false);
        RecyclerViewHolder rvh = new RecyclerViewHolder(v);
        return rvh;
    }

    //Pass values to the views
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        OrderItems currentItem = orderItems.get(position);

        holder.mOrderID.setText(String.valueOf(currentItem.getOrderID()));
        holder.mOrderStatus.setText(currentItem.getStatus());

        if (currentItem.getAssignedStaff() != 0 && currentItem.getAssignedStaff() != 1 && currentItem.getAssignedStaff() != activeStaff) {
            holder.mStartOrder.setEnabled(false);
            holder.mStartOrder.setText("In Progress");
            holder.mRelativeLayout.setBackgroundColor(red);
        } else if (currentItem.getAssignedStaff() == 1 || currentItem.getAssignedStaff() == activeStaff) {
            holder.mStartOrder.setText("Continue Order");
            holder.mRelativeLayout.setBackgroundColor(yellow);
            holder.mStartOrder.setEnabled(true);
        } else {
            holder.mStartOrder.setText("Start Order");
            holder.mRelativeLayout.setBackgroundColor(green);
            holder.mStartOrder.setEnabled(true);
        }

    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }
}

