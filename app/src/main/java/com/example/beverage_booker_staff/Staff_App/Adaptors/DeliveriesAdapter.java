package com.example.beverage_booker_staff.Staff_App.Adaptors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beverage_booker_staff.R;
import com.example.beverage_booker_staff.Staff_App.Models.Deliveries;

import java.util.ArrayList;

public class DeliveriesAdapter extends RecyclerView.Adapter<DeliveriesAdapter.RecyclerViewHolder> {

    private ArrayList<Deliveries> deliveries;
    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(int position);
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        //TextView mUserID;
        TextView mFirstName;
        TextView mPhone;
        TextView mStreetUnit;
        TextView mStreetName;


        Button mDelivered;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            mFirstName = itemView.findViewById(R.id.textViewFirstName);
            mPhone = itemView.findViewById(R.id.textViewPhone);
            mStreetUnit = itemView.findViewById(R.id.streetUnit);
            mStreetName = itemView.findViewById(R.id.streetName);
            mDelivered = itemView.findViewById(R.id.buttonDelivered);

            mDelivered.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) mListener.onItemClickListener(position);
                    }
                }
            });

        }
    }

    public DeliveriesAdapter(ArrayList<Deliveries> listItems) {
        deliveries = listItems;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.deliveries_item, parent, false);
        RecyclerViewHolder rvh = new RecyclerViewHolder(v);
        return rvh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        Deliveries currentDelivery = deliveries.get(position);

        //holder.mUserID.setText(String.valueOf(currentDelivery.getUserID()));
        holder.mFirstName.setText(currentDelivery.getFirstName());
        holder.mPhone.setText(currentDelivery.getPhone());
        holder.mStreetUnit.setText(String.valueOf(currentDelivery.getStreetUnit()));
        holder.mStreetName.setText(currentDelivery.getStreetName());
    }

    @Override
    public int getItemCount() {
        return deliveries.size();
    }
}
