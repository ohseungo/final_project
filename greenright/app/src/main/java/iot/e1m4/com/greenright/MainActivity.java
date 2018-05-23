package iot.e1m4.com.greenright;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import info.addon.SessionManager;

public class MainActivity extends AppCompatActivity {
    private TextView tv;
    private SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.mainText);

        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()){
            logoutUser();
        }

        tv.setText("Hello " + session.getUserId());
    }

    public void logoutBtn(View view) {
        logoutUser();
    }

    public void logoutUser() {
        session.setLogin(false, null);

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }



}
