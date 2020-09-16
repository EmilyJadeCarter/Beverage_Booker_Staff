package com.example.beverage_booker_staff.Staff_App.Activities;

import androidx.appcompat.app.AppCompatActivity;
import com.example.beverage_booker_staff.R;
import com.example.beverage_booker_staff.Staff_App.API.RetrofitClient;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateStaffActivity extends AppCompatActivity {

    private EditText editStaffLevel;
    private EditText editFirstName;
    private EditText editLastName;
    private Button registerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_staff);

        editStaffLevel = findViewById(R.id.editStaffLevel);
        editFirstName = findViewById(R.id.editTextFirstName);
        editLastName = findViewById(R.id.editTextLastName);
        registerButton = findViewById(R.id.buttonRegister);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createStaff();
            }
        });
    }

    private void createStaff(){
        int staffLevel = Integer.parseInt(editStaffLevel.toString());
        String firstName = editFirstName.toString();
        String lastName = editLastName.toString();
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .assignStaffToOrder(staffLevel, firstName, lastName);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 201) {
                    Toast.makeText(CreateStaffActivity.this, "Staff user created successfully", Toast.LENGTH_LONG).show();
                } else if (response.code() == 422) {
                    Toast.makeText(CreateStaffActivity.this, "Staff user failed to be created", Toast.LENGTH_LONG).show();
                } else if (response.code() == 403) {
                    Toast.makeText(CreateStaffActivity.this, "Staff user already exists", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(CreateStaffActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}