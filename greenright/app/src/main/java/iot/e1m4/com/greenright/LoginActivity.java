package iot.e1m4.com.greenright;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import info.addon.SessionManager;
import info.app.AppConfig;
import info.app.AppController;

import static com.android.volley.Request.*;

public class LoginActivity extends AppCompatActivity {
    private SessionManager session;

    private EditText idTv;
    private EditText pwTv;

    private TextView tv;

    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tv = findViewById(R.id.textView);
        idTv = findViewById(R.id.userid);
        pwTv = findViewById(R.id.password);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        session = new SessionManager(getApplicationContext());

        if (session.isLoggedIn()) {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    public void loginBtn(View view) {
        String userId = idTv.getText().toString().trim();
        String password = pwTv.getText().toString().trim();

        if (userId.isEmpty() || password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "빈 칸을 채워주세요", Toast.LENGTH_SHORT).show();
            return;
        }else {
            login(userId, password);
        }



    }

    private void login(final String userId, final String password) {
        pDialog.setMessage("로그인 중입니다");
        showDialog();

        StringRequest stringRequest;
        stringRequest = new StringRequest(Method.POST,
                AppConfig.URL_LOGIN,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        hideDialog();
                        if (response.equals("true")){
                            //로그인 성공했을때
                            session.setLogin(true, userId);


                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            //로그인 틀렸을때

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_SHORT).show();
                        hideDialog();
                    }
                }) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("id", userId);
                params.put("pw", password);
                return params;
            }
        };

        //서버로 보낸다
        AppController.getInstance().
                addToRequestQueue(stringRequest);
    }

    private void showDialog() {
        if (!pDialog.isShowing()){
            pDialog.show();
        }
    }

    private void hideDialog() {
        if (pDialog.isShowing()){
            pDialog.dismiss();
        }
    }

    public void goToRegister(View view) {
        //회원가입 하러 가자
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }
}
