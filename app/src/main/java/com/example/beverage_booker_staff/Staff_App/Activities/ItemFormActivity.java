package com.example.beverage_booker_staff.Staff_App.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.beverage_booker_staff.R;
import com.example.beverage_booker_staff.Staff_App.API.RetrofitClient;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemFormActivity extends AppCompatActivity {

    // all the xml components
    EditText title;
    EditText shortDesc;
    EditText price;
    EditText time;
    TextView optionTitle;
    CheckBox milkStatus;
    CheckBox sugarStatus;
    CheckBox decafStatus;
    CheckBox extrasStatus;
    CheckBox frappeStatus;
    CheckBox heatedStatus;
    Button addButton;

    String itemTitle;
    String itemShortDesc;
    String itemPriceString;
    double itemPriceDouble;
    String itemTimeString;
    int itemTimeInt;

    String itemType;

    //Checkbox ints for db
    int milkOption;
    int sugarOption;
    int decafOption;
    int extrasOption;
    int frappeOption;
    int heatedOption;

    //TODO itemType needs to do something not sure what
    // but maybe there is a way to hide things so then there is only some stuff shown,
    // e.g. if itemType == food then disable all the options to do with beverages
    // (this could be bad might cause problems with spacing between things - research needed) or
    // make a new page for it since that wouldn't be too time consuming either.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_form);

        Intent intent = getIntent();
        itemType = intent.getStringExtra(ItemTypeSelectionActivity.ITEM_TYPE);

        title = findViewById(R.id.editTextTitle);
        shortDesc = findViewById(R.id.editTextShortDesc);
        price = findViewById(R.id.editTextPrice);
        time = findViewById(R.id.editTextTime);

        milkStatus = findViewById(R.id.milkOption);
        sugarStatus = findViewById(R.id.sugarOption);
        decafStatus = findViewById(R.id.decafOption);
        extrasStatus = findViewById(R.id.extrasOption);
        frappeStatus = findViewById(R.id.frappeOption);
        heatedStatus = findViewById(R.id.heatingOption);

        if(itemType.equals("food")) {
            optionTitle = findViewById(R.id.optionTitle);
            optionTitle.setVisibility(View.GONE);
            milkStatus.setVisibility(View.GONE);
            sugarStatus.setVisibility(View.GONE);
            decafStatus.setVisibility(View.GONE);
            extrasStatus.setVisibility(View.GONE);
            frappeStatus.setVisibility(View.GONE);
        } else if(itemType.equals("drink")) {
            heatedStatus.setVisibility(View.GONE);
        }


        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEditTextVariables();
                checkboxAssignment();
                addItemToList();
            }
        });
    }

    private void checkEditTextVariables() {
        //assign all the EditTexts too strings doubles and ints assigned when string value is checked
        itemTitle = title.getText().toString().trim();
        itemShortDesc = shortDesc.getText().toString().trim();
        itemPriceString = price.getText().toString();
        itemTimeString = time.getText().toString();

        if (itemTitle.isEmpty()) {
            title.setError("A title is required");
            title.requestFocus();
            return;
        }

        if (itemPriceString.isEmpty()) {
            price.setError("A price is required");
            price.requestFocus();
            return;
        } else {
            itemPriceDouble = Double.parseDouble(price.getText().toString());
        }

        if (itemTimeString.isEmpty()) {
            time.setError("A time is required");
            time.requestFocus();
            return;
        } else {
            itemTimeInt = Integer.parseInt(time.getText().toString().trim());
        }
    }

    private void checkboxAssignment() {
        // assigns an int for option
        if(milkStatus.isChecked()) {
            milkOption = 1;
        } else {
            milkOption = 0;
        }

        if(sugarStatus.isChecked()) {
            sugarOption = 1;
        } else {
            sugarOption = 0;
        }

        if(decafStatus.isChecked()) {
            decafOption = 1;
        } else {
            decafOption = 0;
        }

        if(extrasStatus.isChecked()) {
            extrasOption = 1;
        } else {
            extrasOption = 0;
        }

        if(frappeStatus.isChecked()) {
            frappeOption = 1;
        } else {
            frappeOption = 0;
        }

        if(heatedStatus.isChecked()) {
            heatedOption = 1;
        } else {
            heatedOption = 0;
        }
    }

    private void addItemToList() {
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .addMenuItem(itemTitle, itemShortDesc, itemPriceDouble, milkOption, sugarOption, decafOption,
                        extrasOption, frappeOption, heatedOption, itemType, itemTimeInt);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 201) {
                    Toast.makeText(ItemFormActivity.this, "Item added to list", Toast.LENGTH_LONG).show();
                } else if (response.code() == 402) {
                    Toast.makeText(ItemFormActivity.this, "Item failed to be added to list", Toast.LENGTH_LONG).show();
                } else if (response.code() == 403){
                    Toast.makeText(ItemFormActivity.this, "Item title already exists", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ItemFormActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return;
    }
}
