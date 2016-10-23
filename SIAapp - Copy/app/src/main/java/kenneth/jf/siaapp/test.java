package kenneth.jf.siaapp;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

import com.google.zxing.BarcodeFormat;

import static android.content.Context.WINDOW_SERVICE;

/**
 * Created by User on 21/10/2016.
 */

public class test extends Fragment implements View.OnClickListener {
    View myView;
    TableLayout tl;
    TableRow tr;
    TextView companyTV,valueTV,testTV;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.test,container,false);  tl = (TableLayout) myView.findViewById(R.id.maintable);
        qrInput = (TextView) myView.findViewById(R.id.qrInput);
        dashboard qr = (dashboard)getActivity();
        String qrcode = qr.getResult();
        qrInput.setText(qrcode);
        Button button1 = (Button) myView.findViewById(R.id.button1);
        button1.setOnClickListener(this);

        addHeaders();
        addData();

        return myView;
    }

    String test[] = {"Google","Windows","iPhone","Nokia","Samsung",
            "Google","Windows","iPhone","Nokia","Samsung",
            "Google","Windows","iPhone","Nokia","Samsung"};
    String os[]       =  {"Android","Mango","iOS","Symbian","Bada",
            "Android","Mango","iOS","Symbian","Bada",
            "Android","Mango","iOS","Symbian","Bada"};

    String companies[]={"1","2","3","4","5",
            "6","7","8","9","10",
            "11","12","13","14","15"};

    /** This function add the headers to the table **/
    public void addHeaders(){

        /** Create a TableRow dynamically **/
        tr = new TableRow(this.getActivity());
        tr.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.FILL_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));

        /** Creating a TextView to add to the row **/
        TextView companyTV = new TextView(this.getActivity());
        companyTV.setText("Index");
        companyTV.setTextColor(Color.GRAY);
        companyTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        companyTV.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        companyTV.setPadding(5, 5, 5, 0);
        tr.addView(companyTV);  // Adding textView to tablerow.

        /** Creating another textview **/
        TextView valueTV = new TextView(this.getActivity());
        valueTV.setText("Event");
        valueTV.setTextColor(Color.GRAY);
        valueTV.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        valueTV.setPadding(5, 5, 5, 0);
        valueTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(valueTV); // Adding textView to tablerow.


        /** Creating another textview **/
        TextView testTV = new TextView(this.getActivity());
        testTV.setText("Ticket");
        testTV.setTextColor(Color.GRAY);
        testTV.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        testTV.setPadding(5, 5, 5, 0);
        testTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(testTV); // Adding textView to tablerow.


        // Add the TableRow to the TableLayout
        tl.addView(tr, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));

        // we are adding two textviews for the divider because we have two columns
        tr = new TableRow(this.getActivity());
        tr.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));

        /** Creating another textview **/
        TextView divider = new TextView(this.getActivity());
        divider.setText("-----------------");
        divider.setTextColor(Color.BLACK);
        divider.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
        divider.setPadding(5, 0, 0, 0);
        divider.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(divider); // Adding textView to tablerow.

        TextView divider2 = new TextView(this.getActivity());
        divider2.setText("-------------------------");
        divider2.setTextColor(Color.BLACK);
        divider2.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
        divider2.setPadding(5, 0, 0, 0);
        divider2.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(divider2); // Adding textView to tablerow.

        TextView divider3 = new TextView(this.getActivity());
        divider3.setText("-------------------------");
        divider3.setTextColor(Color.BLUE);
        divider3.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
        divider3.setPadding(5, 0, 0, 0);
        divider3.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(divider3); // Adding textView to tablerow.




        // Add the TableRow to the TableLayout
        tl.addView(tr, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
    }

    /** This function add the data to the table **/
    public void addData(){

        for (int i = 0; i < companies.length; i++)
        {
            /** Create a TableRow dynamically **/
            tr = new TableRow(this.getActivity());
            tr.setLayoutParams(new LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));

            /** Creating a TextView to add to the row **/
            companyTV = new TextView(this.getActivity());
            companyTV.setText(companies[i]);
            companyTV.setTextColor(Color.RED);
            companyTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            companyTV.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
            companyTV.setPadding(5, 5, 5, 5);
            tr.addView(companyTV);  // Adding textView to tablerow.

            /** Creating another textview **/
            valueTV = new TextView(this.getActivity());
            valueTV.setText(os[i]);
            valueTV.setTextColor(Color.BLUE);
            valueTV.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
            valueTV.setPadding(5, 5, 5, 5);
            valueTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            tr.addView(valueTV); // Adding textView to tablerow.

            /** Creating another textview **/
            testTV = new TextView(this.getActivity());
            testTV.setText(test[i]);
            testTV.setTextColor(Color.BLUE);
            testTV.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
            testTV.setPadding(5, 5, 5, 5);
            testTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            tr.addView(testTV); // Adding textView to tablerow.

            // Add the TableRow to the TableLayout
            tl.addView(tr, new TableLayout.LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
        }
    }



    private String LOG_TAG = "GenerateQRCode";

    TextView qrInput;


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