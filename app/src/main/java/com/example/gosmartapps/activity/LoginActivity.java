package com.example.gosmartapps.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gosmartapps.activity.MainActivity;
import com.example.gosmartapps.R;

public class LoginActivity extends AppCompatActivity {

    Button login;
    EditText username, password;

    protected void onCreate (Bundle saveInstanceState) {
        super.onCreate(saveInstanceState );

        setContentView(R.layout.login);

        login = (Button) findViewById(R.id.btnSign_in);
        username = (EditText) findViewById(R.id.etUsername);
        password = (EditText) findViewById(R.id.etPassword);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if  (password.getText().toString().equalsIgnoreCase("Test1")) {
                    login_sukses();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Password yang dimasukkan salah", Toast.LENGTH_LONG).show();
                }
            }
            
        });
    }

    private void login_sukses() {
        String user_signIn = username.getText().toString();
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("USERNAME",user_signIn);
        startActivity(i);
    }
}
