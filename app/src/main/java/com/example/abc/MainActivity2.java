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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity2 extends AppCompatActivity {
       private EditText aadhaar ;
       private TextView tv2;
       private  Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TextView tv2 = findViewById(R.id.textView2);
        final EditText aadhaar = findViewById(R.id.editTextaadhaar);
        Button button = (Button)findViewById(R.id.aadharbutton);

        aadhaar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (validateaadhar(aadhaar.getText().toString())) {
                    button.setEnabled(true);
                } else {
                    button.setEnabled(false);
                    aadhaar.setError("Invalid Aadhaar card number");
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(MainActivity2 .this, "12334" , Toast.LENGTH_SHORT).show();
                String aadhar_no = aadhaar.getEditableText().toString().trim(); // input
          //      Toast.makeText(MainActivity2 .this, aadhar_no+"qw" , Toast.LENGTH_SHORT).show();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users");
              //  Toast.makeText(MainActivity2 .this, reference+"abc" , Toast.LENGTH_SHORT).show();

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {   // snapshot = aadhaar number
                           String x = snapshot.getKey();
                         //  String y = (String) snapshot.getVa lue();
                        //   Toast.makeText(MainActivity2 .this, y +"  value" , Toast.LENGTH_SHORT).show();
                            // check weather it is their or not
                  //          Toast.makeText(MainActivity2 .this, snapshot +"  snapshot" , Toast.LENGTH_SHORT).show();
                            boolean check = x.equals(aadhar_no);     // checking weather the aadhaar no. exist in database or not
                           // Toast.makeText(MainActivity2 .this, check+" if" , Toast.LENGTH_SHORT).show();   //check = false
                            if (check) {
                                    String n = "7535960450";   // HardCode
                           //    Toast.makeText(MainActivity2 .this, n  +"  number" , Toast.LENGTH_SHORT).show();

                                Intent i = new Intent( MainActivity2.this , OTP.class);
                                    i.putExtra("n" , n);
                                startActivity(i);
                                finish();
                                break;
                                /// OTP Generation
                            }
                            else
                            {
                                Toast.makeText(MainActivity2 .this, "Incorrect Aadhar Number" , Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                      //  Toast.makeText(MainActivity2.this , "error" , Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


//        adbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String aadhar_no = aadhaar.getEditableText().toString().trim(); // input
//                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users").child("users");
//                reference.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {   // snapshot = aadhaar number
//                            // check weather it is their or not
//                            boolean check = snapshot.equals(aadhar_no);     // checking weather the aadhaar no. exist in database or not
//                            Toast.makeText(MainActivity2 .this, check+"abc" , Toast.LENGTH_SHORT).show();
//                            if (check) {
//                                String n = snapshot.child("Phn_no").getValue(String.class);
//                                Intent i = new Intent( getApplicationContext() , OTP.class);
//                                i.putExtra("n" , n);
//                                startActivity(i);
//                                finish();
//
//                                /// OTP Generation
//                            }
//                        }
//                    }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//                        Toast.makeText(MainActivity2.this , "error" , Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
/////
    }
    boolean validateaadhar(String input)
    {
        Pattern p = Pattern.compile("[0-9]{12}");
        Matcher m = p.matcher(input);
        return m.matches();
    }
}