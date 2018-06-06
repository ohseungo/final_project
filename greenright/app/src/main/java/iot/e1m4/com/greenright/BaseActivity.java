package iot.e1m4.com.greenright;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BaseActivity extends Activity {
    private static Typeface mTypeface=null;

    public void setContentView(int layoutResID){
        super.setContentView(layoutResID);

        if(mTypeface==null){
            mTypeface=Typeface.createFromAsset(this.getAssets(),"fonts/nanum.ttf");
        }
        setGlobalFont(getWindow().getDecorView());
    }

    private void setGlobalFont(View view){
        if(view!=null){
            if(view instanceof ViewGroup){
                ViewGroup vg= (ViewGroup) view;
                int vgCnt=vg.getChildCount();
                for(int i=0; i<vgCnt; i++){
                    View v=vg.getChildAt(i);
                    if(v instanceof TextView){
                        ((TextView) v).setTypeface(mTypeface);
                    }
                    setGlobalFont(v);
                }
            }
        }
    }
}
