package iot.e1m4.com.greenright;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import info.addon.PointManager;
import info.app.AppConfig;
import info.app.AppController;


public class PaymentDetails extends AppCompatActivity{
    TextView txtId;
    TextView txtAmount;
    TextView txtStatus;
    TextView txtItem;

    Button goToOrderList;
    PaymentInfo mPaymentInfo;
    ProgressDialog pDialog;

    private final String TAG = getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);
        txtId=findViewById(R.id.txtId);
        txtAmount=findViewById(R.id.txtAmount);
        txtStatus=findViewById(R.id.txtStatus);
        txtItem = findViewById(R.id.txtItem);
        Intent intent = getIntent();

        mPaymentInfo = getIntent().getParcelableExtra("PaymentInfo");
        pDialog=new ProgressDialog(this);

        Log.e(TAG,mPaymentInfo.toString());
        Log.e(TAG, intent.getIntExtra("PurchasePoint",0) + "   " );
        try{
            JSONObject jsonObject =new JSONObject(intent.getStringExtra("PaymentDetails"));
            showDetails(jsonObject.getJSONObject("response"), intent.getStringExtra("PaymentAmount"));
            addPurchaseInfo(jsonObject.getJSONObject("response").getString("id"),
                    intent.getIntExtra("PurchasePoint",0));
            subtractPoint( intent.getIntExtra("PurchasePoint",0));
            ////포인트 - 도 할것

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void subtractPoint(int purchasePoint) {
        PointManager.addPointData(mPaymentInfo.getUserId(),purchasePoint * (-1),
        -1,mPaymentInfo.getProductName() + " 구매", this, TAG);
    }

    private void addPurchaseInfo(final String purchaseId, final int purchasePoint) {
        pDialog.setMessage("결제 정보 업로드 중입니다...");
        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,AppConfig.ADD_PURCHASE ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        hideDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ///리스트 가져오기 실패
                Toast.makeText(PaymentDetails.this, "실패", Toast.LENGTH_SHORT).show();
                hideDialog();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("purchaseId", purchaseId);
                map.put("purchaseCount", "1");
                map.put("productId", mPaymentInfo.getProductId());
                map.put("userId", mPaymentInfo.getUserId());
                map.put("purchaseStatus", "2"); //입금완료
                map.put("purchaseType", "PayPal");
                map.put("purchasePoint", String.valueOf(purchasePoint));
                map.put("compId", mPaymentInfo.getCompId());
                map.put("deliveryNumber", mPaymentInfo.getDeliveryPhone());
                map.put("deliveryName", mPaymentInfo.getDeliveryName());
                map.put("deliveryAddress", mPaymentInfo.getDeliveryAddress());
                return map;
            }
        };
        stringRequest.setTag(TAG);
        AppController.getInstance().
                addToRequestQueue(stringRequest);
    }

    private void showDetails(JSONObject response, String paymentAmount) {
        try {
            txtId.setText(response.getString("id"));
            txtAmount.setText(paymentAmount);
            txtItem.setText(mPaymentInfo.getProductName());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setAction("GOTOORDER");
        startActivity(intent);
        finish();
        return;
    }

    public void goToMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setAction("GOTOORDER");
        startActivity(intent);
        finish();
        return;
    }

    @Override
    protected void onStop() {
        super.onStop();
        AppController.getInstance().cancelPendingRequests(TAG);
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
}

