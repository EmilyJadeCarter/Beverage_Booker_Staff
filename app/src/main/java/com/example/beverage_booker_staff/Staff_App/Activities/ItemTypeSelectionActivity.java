package com.example.beverage_booker_staff.Staff_App.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.beverage_booker_staff.R;

public class ItemTypeSelectionActivity extends AppCompatActivity {

    public static final String ITEM_TYPE = "com.example.beverage_booker_staff.ITEM_TYPE";

    private Button foodMenuButton;
    private Button drinkMenuButton;
    private String itemType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_type_selection);

        foodMenuButton = findViewById(R.id.foodMenuButton);
        foodMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openItemFormFood();
            }
        });

        drinkMenuButton = findViewById(R.id.drinkMenuButton);
        drinkMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openItemFormDrink();
            }
        });
    }

    private void openItemFormFood() {
        Intent intent = new Intent(this, ItemFormActivity.class);
        itemType = "food";
        intent.putExtra(ITEM_TYPE, itemType);
        startActivity(intent);
    }

    public void openItemFormDrink() {
        Intent intent = new Intent(this, ItemFormActivity.class);
        itemType = "drink";
        intent.putExtra(ITEM_TYPE, itemType);
        startActivity(intent);
    }
}
