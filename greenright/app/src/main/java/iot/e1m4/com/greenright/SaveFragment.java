package iot.e1m4.com.greenright;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;


/**
 * A simple {@link Fragment} subclass.
 */
public class SaveFragment extends Fragment {


    public SaveFragment() {
        // Required empty public constructor
    }
    Bitmap mQRcode;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        if (mQRcode == null) {
                            QRCodeWriter writer = new QRCodeWriter();
                            try {
                                BitMatrix bitMatrix = writer.encode("yetolkun", BarcodeFormat.QR_CODE, 1000, 1000);
                                int width = bitMatrix.getWidth();
                                int height = bitMatrix.getHeight();
                                mQRcode = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                                for (int x = 0; x < width; x++) {
                                    for (int y = 0; y < height; y++) {
                                        mQRcode.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                                    }
                                }
                            } catch (WriterException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }
        ).start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout=inflater.inflate(R.layout.fragment_save, container, false);

        if (mQRcode!= null) {
            ((ImageView) layout.findViewById(R.id.qrCode)).setImageBitmap(mQRcode);
        }
        return layout;
    }

}
