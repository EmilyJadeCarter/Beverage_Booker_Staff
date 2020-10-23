package com.example.beverage_booker_staff.Staff_App.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.beverage_booker_staff.R;
import com.example.beverage_booker_staff.Staff_App.API.RetrofitClient;

import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateStaffActivity extends AppCompatActivity {

    private EditText editStaffLevel;
    private EditText editFirstName;
    private EditText editLastName;
    private Button registerButton;
    private Button returnButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_staff);

        editStaffLevel = findViewById(R.id.editStaffLevel);
        editFirstName = findViewById(R.id.editTextFirstName);
        editLastName = findViewById(R.id.editTextLastName);
        registerButton = findViewById(R.id.buttonRegister);
        returnButton = findViewById(R.id.buttonReturn);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createStaff();
            }
        });
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToMenu();
            }
        });
    }

    private void createStaff() {

        String staffLevelValue = editStaffLevel.getText().toString();
        String firstName = editFirstName.getText().toString();
        String lastName = editLastName.getText().toString();

        if (staffLevelValue.isEmpty()) {
            editStaffLevel.setError("Staff Level is required");
            editStaffLevel.requestFocus();
            return;
        }

        if (firstName.isEmpty()) {
            editFirstName.setError("First Name is required");
            editFirstName.requestFocus();
            return;
        }

        if (lastName.isEmpty()) {
            editLastName.setError("Last Name is required");
            editLastName.requestFocus();
            return;
        }

        int staffLevel = Integer.parseInt(staffLevelValue);

        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .createStaff(staffLevel, firstName, lastName);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 201) {
                    Toasty.Config.getInstance()
                            .setTextSize(40)
                            .apply();
                    Toast toast = Toasty.success(CreateStaffActivity.this, "Staff user created successfully", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
                    toast.show();
                    returnToMenu();
                } else if (response.code() == 422) {
                    Toasty.Config.getInstance()
                            .setTextSize(40)
                            .apply();
                    Toast toast = Toasty.error(CreateStaffActivity.this, "Staff user failed to be created", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
                    toast.show();
                } else if (response.code() == 403) {
                    Toasty.Config.getInstance()
                            .setTextSize(40)
                            .apply();
                    Toast toast = Toasty.error(CreateStaffActivity.this, "Staff user already exists", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toasty.Config.getInstance()
                        .setTextSize(40)
                        .apply();
                Toast toast = Toasty.error(CreateStaffActivity.this, "Error while creating staff member", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
                toast.show();
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
    }

    private void returnToMenu() {
        Intent intent = new Intent(this, ManageStaffActivity.class);
        startActivity(intent);
    }
}