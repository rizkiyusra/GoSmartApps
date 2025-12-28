package com.example.gosmartapps.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gosmartapps.R;
import com.example.gosmartapps.model.User;
import com.example.gosmartapps.response.ResponseLogin;
import com.example.gosmartapps.rest.ApiClient;
import com.example.gosmartapps.rest.ApiInterface;
import com.example.gosmartapps.utils.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.btnSign_in)
    Button login;

    @BindView(R.id.etUsername)
    EditText etUsername;

    @BindView(R.id.etPassword)
    EditText etPassword;

    ApiInterface apiservice;
    SessionManager sessionManager;

    private static final String TAG = "LoginActivity";

    protected void onCreate (Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        setContentView(R.layout.login);
        ButterKnife.bind(this);

        apiservice = ApiClient.getClient().create(ApiInterface.class);
        sessionManager = new SessionManager(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void loginUser() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        apiservice.login(username,password).enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                if (response.isSuccessful()) {
                    User userLoggedIN = response.body().getUser();
                    sessionManager.createLoginSession(userLoggedIN);

                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                    finish();
                }
                else if (!response.isSuccessful()) {
                    Toast.makeText(LoginActivity.this,"User tidak ditemukan",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                Log.e(TAG,"onFailure" + t.getLocalizedMessage());
                Toast.makeText(LoginActivity.this,"Gagal terhubung ke server",Toast.LENGTH_SHORT).show();
            }
        });
    }
}