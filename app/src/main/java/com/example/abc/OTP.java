package com.example.abc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class OTP extends AppCompatActivity {
    private TextView verify;
    private TextView longtext;
    private EditText otp;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
         verify = findViewById(R.id.otp1);
         longtext=findViewById(R.id.otp2);
         otp = findViewById(R.id.otpet);
         btn = findViewById(R.id.otpbutton);

         int n=0;


    }
}