package com.example.beverage_booker_staff.Staff_App.Adaptors;

import android.text.Editable;
import android.text.TextWatcher;
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
    public void onBindViewHolder(@NonNull final RecyclerViewHolder holder, final int position) {
        final MenuItem currentItem = menuItems.get(position);

        holder.itemID.setText(String.valueOf(currentItem.getId()));
        holder.itemName.setText(currentItem.getName());
        holder.itemStock.setText(String.valueOf(currentItem.getItemStock()));
        holder.itemStock.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                String newStock = holder.itemStock.getText().toString();
                System.out.println("New Stock Entered: " + newStock);

                menuItems.get(position).setItemStock(newStock);
                String checkStock = menuItems.get(position).getItemStock();
                System.out.println("Check Stock String: " + checkStock);
            }
        });


    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }
}
