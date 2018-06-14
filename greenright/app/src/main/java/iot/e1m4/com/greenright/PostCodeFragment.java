package iot.e1m4.com.greenright;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;


public class PostCodeFragment extends Fragment {
    private WebView webView;
    private Handler handler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_post_code, container, false);

        init_webView();
        handler = new Handler();
        return view;
    }

    public void init_webView() {
        // WebView 설정
        webView = (WebView) view.findViewById(R.id.postweb);
        // JavaScript 허용
        webView.getSettings().setJavaScriptEnabled(true);
        // JavaScript의 window.open 허용
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        // JavaScript이벤트에 대응할 함수를 정의 한 클래스를 붙여줌
        // 두 번째 파라미터는 사용될 php에도 동일하게 사용해야함
        webView.addJavascriptInterface(new AndroidBridge(), "Android");
        // web client 를 chrome 으로 설정
        webView.setWebChromeClient(new WebChromeClient());
        // webview url load
        webView.loadUrl("http://192.168.10.38:9090/greenRight_server/postcode.jsp");
    }

    private class AndroidBridge {
        @JavascriptInterface
        public void setAddress(final String arg1, final String arg2, final String arg3) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                   TextView tv= getFragmentManager().findFragmentByTag("ORDER_PAGE").getView().findViewById(R.id.deliveryAddress);
                    tv.setText(String.format("(%s) %s %s", arg1, arg2, arg3));
                    getFragmentManager().beginTransaction().remove(PostCodeFragment.this).commit();


                }
            });
        }
    }


}
