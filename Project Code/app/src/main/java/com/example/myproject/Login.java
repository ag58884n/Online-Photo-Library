package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    EditText emailid, pswd;
    Button Login;
    DatabaseReference databaseUser;
    boolean isMatch = false;
    String userId;
    String firstName;
    String lastName;
    String email1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        databaseUser = FirebaseDatabase.getInstance().getReference("users");
        isMatch = false;
        emailid = findViewById(R.id.editText);
        pswd = findViewById(R.id.editText4);
        Login = findViewById(R.id.button3);
        Login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final String email = emailid.getText().toString();
                final String password = pswd.getText().toString();
                boolean isError = false;
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
                    databaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                User user = userSnapshot.getValue(User.class);
                                if (user.getEmailId().equals(email) && user.getPassword().equals(password)) {
                                    userId = user.getUserId();
                                    firstName = user.getFirstName();
                                    lastName = user.getLastName();
                                    email1 = user.getEmailId();
                                    isMatch = true;
                                }
                            }
                            if (isMatch) {
                                Intent k = new Intent(Login.this, Display.class);
                                k.putExtra("userId", userId);
                                k.putExtra("firstName", firstName);
                                k.putExtra("lastName", lastName);
                                k.putExtra("emailId", email1);
                                startActivity(k);
                            } else {
                                Toast.makeText(Login.this, "Enter Correct UserName or Password", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }
            }
        });
    }
}