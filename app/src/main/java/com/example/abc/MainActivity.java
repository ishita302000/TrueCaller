package com.example.abc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private TextView manual;
    private EditText phn;
    private Button btn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView manual = findViewById(R.id.textView1);
      final  EditText phn = findViewById(R.id.editTextPhone);
       final  Button btn1 = findViewById(R.id.button1);

        phn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if( validate( phn.getText().toString())) {
                    btn1.setEnabled(true);
                }
                else
                {
                    btn1.setEnabled(false);
                    phn.setError("Invalid Mobile Number");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(i);
                finish();
            }
        });

    }
    boolean validate(String input)
    {
        Pattern p = Pattern.compile("[6-9][0-9]{9}");
        Matcher m = p.matcher(input);
        return m.matches();
    }
}