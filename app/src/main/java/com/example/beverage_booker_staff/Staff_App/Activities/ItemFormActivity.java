package com.example.beverage_booker_staff.Staff_App.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.beverage_booker_staff.R;
import com.example.beverage_booker_staff.Staff_App.API.RetrofitClient;

import es.dmoral.toasty.Toasty;
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

    int itemID;
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

    boolean fieldEnteredChecker;

    boolean modifyButtonClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_form);

        Intent intent = getIntent();
        itemType = intent.getStringExtra(ItemTypeSelectionActivity.ITEM_TYPE);

        fieldEnteredChecker = false;
        modifyButtonClicked = false;

        // add/update button
        addButton = findViewById(R.id.addButton);

        // text fields
        title = findViewById(R.id.editTextTitle);
        shortDesc = findViewById(R.id.editTextShortDesc);
        price = findViewById(R.id.editTextPrice);
        time = findViewById(R.id.editTextTime);

        // checkboxes
        milkStatus = findViewById(R.id.milkOption);
        sugarStatus = findViewById(R.id.sugarOption);
        decafStatus = findViewById(R.id.decafOption);
        extrasStatus = findViewById(R.id.extrasOption);
        frappeStatus = findViewById(R.id.frappeOption);
        heatedStatus = findViewById(R.id.heatingOption);

        // if modify was clicked - this sets all the next page to correspond with options
        if (intent.getIntExtra(BrowseMenuActivity.ITEM_ID, 0) != 0) {
            modifyButtonClicked = true;
            addButton.setText("Modify");
            itemID = intent.getIntExtra(BrowseMenuActivity.ITEM_ID, 0);
            title.setText(intent.getStringExtra(BrowseMenuActivity.ITEM_TITLE));
            shortDesc.setText(intent.getStringExtra(BrowseMenuActivity.ITEM_DESC));
            price.setText(String.valueOf(intent.getDoubleExtra(BrowseMenuActivity.ITEM_PRICE, 0)));
            time.setText(String.valueOf(intent.getIntExtra(BrowseMenuActivity.ITEM_TIME, 0)));

            if (intent.getIntExtra(BrowseMenuActivity.ITEM_MILK, 0) == 1) {
                milkStatus.setChecked(true);
            }
            if (intent.getIntExtra(BrowseMenuActivity.ITEM_SUGAR, 0) == 1) {
                sugarStatus.setChecked(true);
            }
            if (intent.getIntExtra(BrowseMenuActivity.ITEM_DECAF, 0) == 1) {
                decafStatus.setChecked(true);
            }
            if (intent.getIntExtra(BrowseMenuActivity.ITEM_EXTRAS, 0) == 1) {
                extrasStatus.setChecked(true);
            }
            if (intent.getIntExtra(BrowseMenuActivity.ITEM_FRAPPE, 0) == 1) {
                frappeStatus.setChecked(true);
            }
            if (intent.getIntExtra(BrowseMenuActivity.ITEM_HEATED, 0) == 1) {
                heatedStatus.setChecked(true);
            }
            itemType = intent.getStringExtra(BrowseMenuActivity.ITEM_TYPE);
        }

        if (itemType.equals("food")) {
            optionTitle = findViewById(R.id.optionTitle);
            optionTitle.setVisibility(View.GONE);
            milkStatus.setVisibility(View.GONE);
            sugarStatus.setVisibility(View.GONE);
            decafStatus.setVisibility(View.GONE);
            extrasStatus.setVisibility(View.GONE);
            frappeStatus.setVisibility(View.GONE);
        } else if (itemType.equals("drink")) {
            heatedStatus.setVisibility(View.GONE);
        }

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEditTextVariables();
                checkboxAssignment();
                if (fieldEnteredChecker == true && modifyButtonClicked == false) {
                    addItemToList();
                } else if (fieldEnteredChecker == true && modifyButtonClicked == true) {
                    modifyItemToList();
                }
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
        fieldEnteredChecker = true;
    }

    private void checkboxAssignment() {
        // assigns an int for option
        if (milkStatus.isChecked()) {
            milkOption = 1;
        } else {
            milkOption = 0;
        }

        if (sugarStatus.isChecked()) {
            sugarOption = 1;
        } else {
            sugarOption = 0;
        }

        if (decafStatus.isChecked()) {
            decafOption = 1;
        } else {
            decafOption = 0;
        }

        if (extrasStatus.isChecked()) {
            extrasOption = 1;
        } else {
            extrasOption = 0;
        }

        if (frappeStatus.isChecked()) {
            frappeOption = 1;
        } else {
            frappeOption = 0;
        }

        if (heatedStatus.isChecked()) {
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
                    Toasty.Config.getInstance()
                            .setTextSize(40)
                            .apply();
                    Toast toast = Toasty.success(ItemFormActivity.this, "Item added to list", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
                    toast.show();
                    viewMenu();
                } else if (response.code() == 402) {
                    Toasty.Config.getInstance()
                            .setTextSize(40)
                            .apply();
                    Toast toast = Toasty.error(ItemFormActivity.this, "Item failed to be added to list", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
                    toast.show();
                } else if (response.code() == 403) {
                    Toasty.Config.getInstance()
                            .setTextSize(40)
                            .apply();
                    Toast toast = Toasty.error(ItemFormActivity.this, "Item title already exists", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toasty.Config.getInstance()
                        .setTextSize(40)
                        .apply();
                Toast toast = Toasty.error(ItemFormActivity.this, "Error while adding item to list", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
                toast.show();
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
        return;
    }

    private void modifyItemToList() {
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .modifyMenuItem(itemID, itemTitle, itemShortDesc, itemPriceDouble, milkOption, sugarOption, decafOption,
                        extrasOption, frappeOption, heatedOption, itemType, itemTimeInt);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                System.out.println(response.code());
                if (response.code() == 201) {
                    Toasty.Config.getInstance()
                            .setTextSize(40)
                            .apply();
                    Toast toast = Toasty.success(ItemFormActivity.this, "Item was modified", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
                    toast.show();
                    viewMenu();
                } else if (response.code() == 402) {
                    Toasty.Config.getInstance()
                            .setTextSize(40)
                            .apply();
                    Toast toast = Toasty.error(ItemFormActivity.this, "Item failed to modify", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
                    toast.show();
                } else if (response.code() == 403) {
                    Toasty.Config.getInstance()
                            .setTextSize(40)
                            .apply();
                    Toast toast = Toasty.error(ItemFormActivity.this, "Item title already exists", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toasty.Config.getInstance()
                        .setTextSize(40)
                        .apply();
                Toast toast = Toasty.error(ItemFormActivity.this, "Error while modifying item in list", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
                toast.show();
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
        return;
    }

    private void viewMenu() {
        Intent intent = new Intent(this, BrowseMenuActivity.class);
        startActivity(intent);
    }
}
