package com.example.abc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class OTP extends AppCompatActivity {
    private TextView verify;
    private TextView longtext;
    private EditText otp;
    private Button btn;
    private FirebaseAuth mAuth;
    String verificationcodebysystem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
         verify = findViewById(R.id.otp1);
         longtext=findViewById(R.id.otp2);
         otp = findViewById(R.id.otpet);
         btn = findViewById(R.id.otpbutton);

         int n=0;
         String phone_no = getIntent().getStringExtra("n");

         sendVerificationToUser(phone_no);

         btn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String code = otp.getText().toString();

                 if(code.isEmpty())
                 {
                     otp.setError("Wrong OTP ...");
                     otp.requestFocus();
                     return;
                 }
                 verifyCode(code);
             }
         });
    }

    private void sendVerificationToUser(String phone_no ) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)              //
                        .setPhoneNumber( "+91" + phone_no )       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity((Activity) TaskExecutors.MAIN_THREAD)                 // Activity (for callback binding)  //
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        // for every device
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationcodebysystem = s;
        }

        // for same device
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            String code = phoneAuthCredential.getSmsCode();
            if(code!=null)
            {
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(OTP.this , e.getMessage() , Toast.LENGTH_SHORT).show();
        }
    };

    private  void  verifyCode(String codebyuser)
    {
        PhoneAuthCredential credential= PhoneAuthProvider.getCredential(verificationcodebysystem , codebyuser);
        signintheUser(credential);
    }
    private void  signintheUser(PhoneAuthCredential credential)
    {
        FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(OTP.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(OTP.this, display.class);
                            startActivity(i);
                            finish();
                        }
                    });
                }
                else
                {
                    Toast.makeText(OTP.this , task.getException().getMessage() ,Toast.LENGTH_SHORT ).show();
                }
            }
        });
    }
}