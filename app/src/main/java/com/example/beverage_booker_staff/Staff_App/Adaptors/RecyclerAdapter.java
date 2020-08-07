package com.example.beverage_booker_staff.Staff_App.Adaptors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beverage_booker_staff.R;
import com.example.beverage_booker_staff.Staff_App.Models.MenuItem;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    private ArrayList<MenuItem> menuItems;
    private OnItemClickListener itemListener;

    DecimalFormat currency = new DecimalFormat("###0.00");

    public interface OnItemClickListener {
        void onItemClick(int selection, int position);
    }

    //modify or delete
    public void setOnButtonClickListener(OnItemClickListener listener) {
        itemListener = listener;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView itemID;
        TextView itemName;
        TextView shortDesc;
        TextView price;
        Button modifyMenuItem;
        Button deleteMenuItem;

        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            itemID = itemView.findViewById(R.id.itemID);
            itemName = itemView.findViewById(R.id.itemName);
            shortDesc = itemView.findViewById(R.id.itemDesc);
            price = itemView.findViewById(R.id.itemPrice);
            modifyMenuItem = itemView.findViewById(R.id.modifyMenuItem);
            deleteMenuItem = itemView.findViewById(R.id.deleteMenuItem);

            modifyMenuItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            itemListener.onItemClick(1, position);
                        }
                    }
                }
            });

            deleteMenuItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            itemListener.onItemClick(2, position);
                        }
                    }
                }
            });
        }
    }

    public RecyclerAdapter(ArrayList<MenuItem> listItems) {
        menuItems = listItems;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_menu_items, parent, false);
        RecyclerViewHolder recViewHolder = new RecyclerViewHolder(view);
        return recViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        MenuItem currentItem = menuItems.get(position);

        holder.itemID.setText(String.valueOf(currentItem.getId()));
        holder.itemName.setText(currentItem.getName());
        holder.shortDesc.setText(currentItem.getDescription());
        holder.price.setText("$" + currency.format(currentItem.getPrice()));
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }
}
