package iot.e1m4.com.greenright;

import android.content.Intent;
import android.media.MediaCas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import info.addon.SessionManager;

public class IntroLoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_login);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (new SessionManager(getApplicationContext()).isLoggedIn()){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }

    }

    public void btnLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        return;
    }

    public void btnRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        return;
    }
}
