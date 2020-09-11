package com.example.beverage_booker_staff.Staff_App.Adaptors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.beverage_booker_staff.Staff_App.Models.MenuItem;

import com.example.beverage_booker_staff.R;

import java.util.ArrayList;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.RecyclerViewHolder> {

    private ArrayList<MenuItem> menuItems;
    private OnItemClickListener itemListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnButtonClickListener(OnItemClickListener listener) {
        itemListener = listener;
    }



    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView itemID;
        TextView itemName;

        EditText itemStock;

        Button updateInventoryItem;


        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            itemID = itemView.findViewById(R.id.inventoryItemID);
            itemName = itemView.findViewById(R.id.inventoryItemName);
            itemStock = itemView.findViewById(R.id.inventoryItemStock);

            updateInventoryItem = itemView.findViewById(R.id.updateInventoryItem);

            updateInventoryItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            itemListener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }

    public InventoryAdapter(ArrayList<MenuItem> listItems) {
        menuItems = listItems;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inventory_item, parent, false);
        RecyclerViewHolder recViewHolder = new RecyclerViewHolder(view);
        return recViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        MenuItem currentItem = menuItems.get(position);

        holder.itemID.setText(String.valueOf(currentItem.getId()));
        holder.itemName.setText(currentItem.getName());
        int itemStock = currentItem.getItemStock();
        System.out.println("Stock :" + itemStock);
        holder.itemStock.setText(String.valueOf(currentItem.getItemStock()));


    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }
}
