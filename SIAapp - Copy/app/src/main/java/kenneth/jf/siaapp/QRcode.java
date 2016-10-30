package kenneth.jf.siaapp;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;

import static android.content.Context.WINDOW_SERVICE;

/**
 * Created by User on 21/10/2016.
 */
//generate QR code
public class QRcode extends Fragment implements View.OnClickListener {

    private String LOG_TAG = "GenerateQRCode";
    View myView;
    TextView qrInput;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.first_frag,container,false);

        qrInput = (TextView) myView.findViewById(R.id.qrInput);
        dashboard qr = (dashboard)getActivity();
        String qrcode = qr.getResult();
        qrInput.setText(qrcode);
        Button button1 = (Button) myView.findViewById(R.id.button1);
        button1.setOnClickListener(this);
        return myView;
    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button1:


                String qrInputText = qrInput.getText().toString();
                Log.v(LOG_TAG, qrInputText);

                //Find screen size
                WindowManager manager = (WindowManager) getActivity().getSystemService(WINDOW_SERVICE);
                Display display = manager.getDefaultDisplay();
                Point point = new Point();
                display.getSize(point);
                int width = point.x;
                int height = point.y;
                int smallerDimension = width < height ? width : height;
                smallerDimension = smallerDimension * 3/4;

                //Encode with a QR Code image
                QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(qrInputText,
                        null,
                        Contents.Type.TEXT,
                        BarcodeFormat.QR_CODE.toString(),
                        smallerDimension);
                try {
                    Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
                    ImageView myImage = (ImageView) myView.findViewById(R.id.imageView1);
                    myImage.setImageBitmap(bitmap);

                } catch (Exception e) {
                    e.printStackTrace();
                }


                break;

            // More buttons go here (if any) ...

        }
    }

}