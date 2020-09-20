package com.example.beverage_booker_staff.Staff_App.Adaptors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beverage_booker_staff.R;
import com.example.beverage_booker_staff.Staff_App.Models.Staff;

import java.util.ArrayList;

public class ManageStaffAdapter extends RecyclerView.Adapter<ManageStaffAdapter.RecyclerViewHolder> {

    private ArrayList<Staff> staffList;
    private OnItemClickListener itemListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnButtonClickListener(OnItemClickListener listener) {
        itemListener = listener;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView staffID;
        TextView firstName;
        TextView lastName;
        TextView staffLevel;

        Button deleteStaffMemberButton;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            staffID = itemView.findViewById(R.id.staffID);
            firstName = itemView.findViewById(R.id.staffFirstName);
            lastName = itemView.findViewById(R.id.staffLastName);
            staffLevel = itemView.findViewById(R.id.staffLevel);

            deleteStaffMemberButton = itemView.findViewById(R.id.deleteStaffMember);

            deleteStaffMemberButton.setOnClickListener(new View.OnClickListener() {
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

    public ManageStaffAdapter(ArrayList<Staff> listItems) {
        staffList = listItems;
    }

    @NonNull
    @Override
    public ManageStaffAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.staff_member, parent, false);
        RecyclerViewHolder recViewHolder = new RecyclerViewHolder(view);
        return recViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ManageStaffAdapter.RecyclerViewHolder holder, int position) {
        Staff currentStaff = staffList.get(position);

        holder.staffID.setText(String.valueOf(currentStaff.getStaffID()));
        holder.firstName.setText(currentStaff.getFirstName());
        holder.lastName.setText(currentStaff.getLastName());
        holder.staffLevel.setText(String.valueOf(currentStaff.getStaffLevel()));

    }

    @Override
    public int getItemCount() {
        return staffList.size();
    }
}
