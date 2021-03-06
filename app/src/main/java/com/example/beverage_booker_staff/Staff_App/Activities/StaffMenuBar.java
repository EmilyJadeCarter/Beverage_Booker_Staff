package com.example.beverage_booker_staff.Staff_App.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.beverage_booker_staff.R;
import com.example.beverage_booker_staff.Staff_App.Models.Staff;
import com.example.beverage_booker_staff.Staff_App.storage.SharedPrefManager;


public class StaffMenuBar extends Fragment {

    private ImageButton homeButton;
    private ImageButton signOutButton;
    private Staff activeStaff;


    public StaffMenuBar() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_menubarstaff, container, false);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        homeButton = view.findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(StaffMenuBar.super.getActivity(), MainMenuActivity.class);
                startActivity(intent);
            }
        });

        signOutButton = view.findViewById(R.id.signOutButton);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                SharedPrefManager.getInstance(StaffMenuBar.super.getActivity()).clear();
                Intent intent = new Intent(StaffMenuBar.super.getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if(!SharedPrefManager.getInstance(StaffMenuBar.super.getActivity()).isLoggedIn()){
            Intent intent = new Intent(super.getContext(), MainActivity.class);
            startActivity(intent);
        }
    }
}