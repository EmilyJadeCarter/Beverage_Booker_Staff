package com.example.beverage_booker_staff.Staff_App.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.beverage_booker_staff.R;

public class ItemFormActivity extends AppCompatActivity {

    private Button foodMenuButton;

    EditText title;
    EditText shortdesc;
    EditText price;

    CheckBox milkStatus;
    CheckBox sugarStatus;
    CheckBox decafStatus;
    CheckBox extrasStatus;
    CheckBox frappeStatus;
    CheckBox heatedStatus;

    Button addButton;

    String itemTitle;

    //TODO make the titles for each thing in the item form a proper colour and
    // make them larger. All works up to here - title will have to be checked against
    // the db to make sure its not already existent. Shortdesc all the way down to
    // heated status needs to be linked up and a new request in the api will have to be
    // created for the creation of a new item. itemType needs to do something not sure what
    // but maybe there is a way to hide things so then there is only some stuff shown,
    // e.g. if itemType == food then disable all the options to do with beverages
    // (this could be bad might cause problems with spacing between things - research needed) or
    // make a new page for it since that wouldn't be too time consuming either.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_form);

        Intent intent = getIntent();
        String itemType = intent.getStringExtra(ItemTypeSelectionActivity.ITEM_TYPE);

        title = findViewById(R.id.editTextTitle);

        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkTitle();
            }
        });
    }

    private void checkTitle() {
        itemTitle = title.getText().toString().trim();

        // make sure its not empty
        if (itemTitle.isEmpty()) {
            title.setError("ID is required");
            title.requestFocus();
            return;
        }
    }
}
