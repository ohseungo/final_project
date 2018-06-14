package iot.e1m4.com.greenright;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Instrumentation;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import info.addon.SessionManager;
import info.app.AppConfig;
import info.app.AppController;


/**
 * A simple {@link Fragment} subclass.
 */
public class PayFragment extends Fragment implements MainActivity.onKeyBackPressedListener{

    private Button cardBtn;
    private Button accountBtn;
    private Button simplePayBtn;

    private ViewPager pager;

    private Fragment cardFragment;
    private Fragment accountFragment;
    private Fragment simplePayFragment;

    FragmentTransaction transaction;
    private static Typeface typeface;


    private EditText pointUse;
    private int availableUse;
    private TextView availablePoint;
    private TextView checkUsePoint;

    private TextView orderPrice;
    private TextView useGreenPoint;
    private TextView deliveryPrice;
    private int totalPrice;
    private TextView finalPrice;

    private PaymentInfo mPaymentInfo;
    private SessionManager sessionManager;
    public static final int PAYPAL_REQUEST_CODE = 7171;

    private boolean pointChecked = true;
    private static PayPalConfiguration config =
            new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(AppConfig.PAYPAL_CLIENT_ID);

    Button payBtn;
    private final String TAG = getClass().getSimpleName();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        sessionManager = new SessionManager(getActivity());
        //paypal 서비스 이용하기
        Intent intent = new Intent(getActivity(), PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        getActivity().startService(intent);
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    private int point =0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout=inflater.inflate(R.layout.fragment_pay, container, false);


        if(typeface == null) {
            typeface = Typeface.createFromAsset(getActivity().getAssets(),
                    "fonts/yoon350.ttf");
        }
        setGlobalFont(layout);

        //각 버튼 눌렀을 때 옮겨지는 fragment 연결
        cardFragment=new CardFragment();
        accountFragment=new AccountFragment();
        simplePayFragment=new SimplePayFragment();

        transaction=getActivity().getSupportFragmentManager().beginTransaction();

        cardBtn=layout.findViewById(R.id.cardBtn);
        cardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pager.setCurrentItem(0);
                cardBtn.setBackgroundColor(getResources().getColor(R.color.orange));
                accountBtn.setBackgroundColor(getResources().getColor(R.color.basic));
                simplePayBtn.setBackgroundColor(getResources().getColor(R.color.basic));
                cardBtn.setTextColor(getResources().getColor(R.color.white));
                accountBtn.setTextColor(getResources().getColor(R.color.black));
                simplePayBtn.setTextColor(getResources().getColor(R.color.black));
            }
        });
        accountBtn=layout.findViewById(R.id.accountBtn);
        accountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               pager.setCurrentItem(1);
                cardBtn.setBackgroundColor(getResources().getColor(R.color.basic));
                accountBtn.setBackgroundColor(getResources().getColor(R.color.orange));
                simplePayBtn.setBackgroundColor(getResources().getColor(R.color.basic));
                cardBtn.setTextColor(getResources().getColor(R.color.black));
                accountBtn.setTextColor(getResources().getColor(R.color.white));
                simplePayBtn.setTextColor(getResources().getColor(R.color.black));
            }

        });
        simplePayBtn=layout.findViewById(R.id.simplePayBtn);
        simplePayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pager.setCurrentItem(2);
                cardBtn.setBackgroundColor(getResources().getColor(R.color.basic));
                accountBtn.setBackgroundColor(getResources().getColor(R.color.basic));
                simplePayBtn.setBackgroundColor(getResources().getColor(R.color.orange));
                cardBtn.setTextColor(getResources().getColor(R.color.black));
                accountBtn.setTextColor(getResources().getColor(R.color.black));
                simplePayBtn.setTextColor(getResources().getColor(R.color.white));
            }
        });

        pager=layout.findViewById(R.id.pager);
        pager.setAdapter(new PagerAdapter(getActivity().getSupportFragmentManager(),3));


        mPaymentInfo = getArguments().getParcelable("PaymentInfo");


       pointUse = layout.findViewById(R.id.pointUse);
       availablePoint = layout.findViewById(R.id.availablePoint);
       checkUsePoint = layout.findViewById(R.id.checkUsePoint);

        orderPrice = layout.findViewById(R.id.orderPrice);
        deliveryPrice = layout.findViewById(R.id.deliveryPrice);
        useGreenPoint = layout.findViewById(R.id.useGreenPoint);
        finalPrice = layout.findViewById(R.id.finalPrice);
       /////////////////////포인트를 바꿀 경우////////////////////////////////
       pointUse.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {
           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {
           }

           @Override
           public void afterTextChanged(Editable s) {
                try{
                    point = Integer.parseInt(pointUse.getText().toString().trim());
                    if (point > availableUse) {
                        pointChecked = false;
                        useGreenPoint.setText( "0원");
                        checkUsePoint.setText("포인트 부족");
                        point =0;
                        return;
                    }
                    pointChecked = true;
                    checkUsePoint.setText("");
                    useGreenPoint.setText(point + "원");
                    return;
                }catch(NumberFormatException e) {
                    if (pointUse.getText().toString().trim().equals("")){
                        point = 0;
                        pointChecked = true;
                        checkUsePoint.setText("");
                        useGreenPoint.setText(point + "원");
                        return;
                    }else {
                        pointChecked = false;
                        useGreenPoint.setText("0원");
                        checkUsePoint.setText("포인트 부족");
                        point = 0;
                        return;
                    }
                }finally {
                    getTotalPrice();
                }

           }
       });



        orderPrice.setText(String.format("%,d", Integer.parseInt( mPaymentInfo.getProductValue())  )
                + "원");

        ///final Price 계산
        getTotalPrice();

        getTotalPoint(sessionManager.getUserId());

        payBtn = layout.findViewById(R.id.payBtn);
        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pointChecked){
                    processPayment();
                }else {
                    Toast.makeText(getActivity(), "사용 포인트를 확인해주세요", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        });
        return layout;
    }

    private void getTotalPrice() {
        totalPrice = Integer.parseInt( mPaymentInfo.getProductValue()) - point;
        finalPrice.setText(String.format("%,d", totalPrice  )
                + "원");
    }

    private void getTotalPoint(final String userId) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.TOTAL_POINT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response ==null || response.equals("")) response = "0";
                        availablePoint.setText("가용포인트(" + response + ")");
                        availableUse = Integer.parseInt(response);
                        return;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userId", userId);
                return params;
            }
        };
        stringRequest.setTag(TAG);
        AppController.getInstance().
                addToRequestQueue(stringRequest);
    }


    private void processPayment() {
        //샀다에 구매 아이템 정보가 들어간다
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf( (double) (totalPrice/110) /10.0 )), "USD"
                                    ,mPaymentInfo.getProductName() + " 구매", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(getActivity(), PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==PAYPAL_REQUEST_CODE) {
            if (resultCode== getActivity().RESULT_OK) {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation !=null) {
                    try {
                        String paymentDetails = confirmation.toJSONObject().toString(4);
                        startActivity(new Intent(getActivity(),PaymentDetails.class)
                                        .putExtra("PaymentDetails", paymentDetails)
                                        .putExtra("PaymentAmount", String.format("%,d", totalPrice  ))
                                         .putExtra("PurchasePoint", point)
                                        .putExtra("PaymentInfo", mPaymentInfo));
                                            //마지막 구매 정보
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            else if(resultCode== Activity.RESULT_CANCELED){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("결제가 취소되었습니다")
                        .setCancelable(false)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
        else if(resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("결제 중 문제가 발생하였습니다")
                    .setCancelable(false)
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }



    private class PagerAdapter extends FragmentStatePagerAdapter{

        private int PAGE_NUMBER;
        public PagerAdapter(FragmentManager fm, int count){
            super(fm);
            PAGE_NUMBER=count;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return cardFragment;
                case 1:
                    return accountFragment;
                case 2:
                    return simplePayFragment;
                    default:
                        return null;
            }
        }

        @Override
        public int getCount() {
            return PAGE_NUMBER;
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }
    }

   private void setGlobalFont(View view) {
       if(view != null) {
           if(view instanceof ViewGroup) {
               ViewGroup viewGroup = (ViewGroup)view;
               int vgCnt = viewGroup.getChildCount();
               for(int i = 0; i<vgCnt; i++) {
                   View v = viewGroup.getChildAt(i);
                   if(v instanceof TextView) {
                       ((TextView) v).setTypeface(typeface);
                   }
                   setGlobalFont(v);
               }
           }
       }
   }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().stopService(new Intent(getActivity(), PayPalService.class));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity)context).setmOnKeyBackPressedListener(this);
    }

    @Override
    public void onBack() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("마켓 화면으로 돌아갑니다")
                .setCancelable(false)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getFragmentManager().beginTransaction().replace(R.id.contentContainer,new MarketFragment()).commit();
                        return;
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
        return;
    }
}
