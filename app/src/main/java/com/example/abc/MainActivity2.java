package com.example.abc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity2 extends AppCompatActivity {
       private EditText aadhaar ;
       private Button adbtn;
       private TextView tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TextView tv2 = findViewById(R.id.textView2);
        final EditText aadhaar = findViewById(R.id.editTextaadhaar);
        final Button adbtn = findViewById(R.id.button2);

        aadhaar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (validateaadhar(aadhaar.getText().toString())) {
                    adbtn.setEnabled(true);
                } else {
                    adbtn.setEnabled(false);
                    aadhaar.setError("Invalid Aadhaar card number");

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });




        String aadhar_no = aadhaar.getEditableText().toString().trim(); // input

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {   // snapshot = aadhaar number
                    // check weather it is their or not
                    boolean check = snapshot.equals(aadhar_no);     // checking weather the aadhaar no. exist in database or not
                    if (check) {
                        String n = snapshot.child("Phn_no").getValue(String.class);

                        /// OTP Generation


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        adbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity2.this, OTP.class);
                startActivity(i);
                finish();
            }
        });

        //////////////////////////

//        for () {
//            Query check = (Query) reference.orderByChild("users").equalTo(aadhar_no).addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    if (dataSnapshot.exists()) {
//                        // value store in n
//                        int n = dataSnapshot.child("users").child("Phn_no").getValue(int.class);
//                        // n  otp generation code
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
//        }
//
        /////////////////
    }
    boolean validateaadhar(String input)
    {
        Pattern p = Pattern.compile("[0-9]{12}");
        Matcher m = p.matcher(input);
        return m.matches();
    }
}