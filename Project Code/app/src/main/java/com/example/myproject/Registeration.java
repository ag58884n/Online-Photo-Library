package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registeration extends AppCompatActivity {
    EditText fname, lname, emailid, pswd;
    Button Register;
    DatabaseReference databaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registeration);
        databaseUser = FirebaseDatabase.getInstance().getReference("users");

        fname = findViewById(R.id.editText2);
        lname = findViewById(R.id.editText3);

        emailid = findViewById(R.id.editText7);
        pswd = findViewById(R.id.editText8);


        Register = findViewById(R.id.button);
        Register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String fname1 = fname.getText().toString();
                String lname1 = lname.getText().toString();
                String email = emailid.getText().toString();
                String password = pswd.getText().toString();
                boolean isError = false;
                if (fname1.isEmpty()) {
                    isError = true;
                    fname.setError("Required Field is Missing");
                    fname.requestFocus();
                }
                if (lname1.isEmpty()) {
                    isError = true;
                    lname.setError("Required Field is Missing");
                    lname.requestFocus();
                }
                if (email.isEmpty()) {
                    isError = true;
                    emailid.setError("Required Field is Missing");
                    emailid.requestFocus();
                }
                if (password.isEmpty()) {
                    isError = true;
                    pswd.setError("Required Field is Missing");
                    pswd.requestFocus();
                }
                if (!isError) {
                    String userId = databaseUser.push().getKey();
                    User user = new User(userId, fname1, lname1, email, password);
                    databaseUser.child(userId).setValue(user);
                    //     Toast.makeText(this,"User Added",Toast.LENGTH_LONG).show();
                    Intent k = new Intent(Registeration.this, Login.class);
                    startActivity(k);
                }
            }
        });
    }
}