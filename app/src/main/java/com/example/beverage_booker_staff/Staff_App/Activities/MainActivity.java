package com.example.beverage_booker_staff.Staff_App.Activities;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.beverage_booker_staff.R;
import com.example.beverage_booker_staff.Staff_App.API.RetrofitClient;
import com.example.beverage_booker_staff.Staff_App.Models.LoginResponse;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText staffID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        staffID = findViewById(R.id.editTextStaffID);

        findViewById(R.id.button_ValidateId).setOnClickListener(this);
    }

    private void validateID() {

        String id = staffID.getText().toString().trim();

        if (id.isEmpty()) {
            staffID.setError("ID is required");
            return;
        }
        if (id.length() < 5) {
            staffID.setError("ID length is incorrect");
        }
        Call<LoginResponse> call = RetrofitClient
                .getInstance().getApi().staffValidate(id);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();

                if (!loginResponse.isError()) {

                    Intent intent = new Intent(MainActivity.this, ViewActiveOrdersActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                } else {
                    //Show the error message
                    Toast.makeText(MainActivity.this, loginResponse.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        validateID();
        Intent intent = new Intent(MainActivity.this, ViewActiveOrdersActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }


}


