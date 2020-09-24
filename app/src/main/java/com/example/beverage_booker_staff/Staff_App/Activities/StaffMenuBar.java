package com.example.beverage_booker_staff.Staff_App.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.beverage_booker_staff.R;
import com.example.beverage_booker_staff.Staff_App.storage.SharedPrefManager;


public class StaffMenuBar extends Fragment {

    private LinearLayout homeButton;
    private LinearLayout accountButton;
    private LinearLayout cartButton;
    private LinearLayout orderButton;
    private LinearLayout helpButton;
    private LinearLayout signOutButton;


    public StaffMenuBar(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_staffMenuBar, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){

         homeButton = view.findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent intent = new Intent(StaffMenuBar.super.getActivity(), PrimaryMenu.class);
                startActivity(intent);
            }
        });

        accountButton = view.findViewById(R.id.accountButton);
        accountButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent intent = new Intent(StaffMenuBar.super.getActivity(), AccountActivity.class);
                startActivity(intent);
            }
        });

        cartButton = view.findViewById(R.id.cartButton);
        cartButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent intent = new Intent(StaffMenuBar.super.getActivity(), CartActivity.class);
                startActivity(intent);
            }
        });

        orderButton = view.findViewById(R.id.orderButton);
        orderButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent intent = new Intent(StaffMenuBar.super.getActivity(), OrderConfirmationActivity.class);
                startActivity(intent);
            }
        });

        helpButton = view.findViewById(R.id.helpButton);
        helpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent intent = new Intent(StaffMenuBar.super.getActivity(), HelpActivity.class);
                startActivity(intent);
            }
        });

        signOutButton = view.findViewById(R.id.signOutButton);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                SharedPrefManager.getInstance(StaffMenuBar.super.getActivity()).clear();
                startActivity(new Intent(StaffMenuBar.super.getActivity(), MainActivity.class));
            }
        });
    }

}