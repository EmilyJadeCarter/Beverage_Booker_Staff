package com.example.beverage_booker_staff.Staff_App.Activities;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.beverage_booker_staff.R;
import com.example.beverage_booker_staff.Staff_App.API.RetrofitClient;
import com.example.beverage_booker_staff.Staff_App.Models.LoginResponse;

public class MainActivity extends AppCompatActivity {

    private EditText editTextStaffID;
    private Button loginStaffButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextStaffID = findViewById(R.id.editTextStaffID);
        loginStaffButton = findViewById(R.id.button_ValidateId);

        loginStaffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateID();
            }
        });
    }

    private void validateID() {

        String id = editTextStaffID.getText().toString().trim();

        if (id.isEmpty()) {
            editTextStaffID.setError("ID is required");
            editTextStaffID.requestFocus();
            return;
        }
        if (id.length() != 4) {
            editTextStaffID.setError("ID must be 4 digits");
            editTextStaffID.requestFocus();
            return;
        }

        Call<LoginResponse> call = RetrofitClient
                .getInstance().getApi().staffValidate(id);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();

                if (!loginResponse.isError()) {

                    Intent intent = new Intent(MainActivity.this, MainMenuActivity.class);
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

}


