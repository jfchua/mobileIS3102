package kenneth.jf.siaapp;

import android.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static kenneth.jf.siaapp.R.layout.payment_summary;

/**
 * Created by User on 28/10/2016.
 */

public class paymentSummary extends Fragment {
    View myView;
    TextView tixSum;
    Button toPayPal;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(payment_summary,container,false);
        tixSum = (TextView)myView.findViewById(R.id.sum);
        final String sum = getActivity().getIntent().getStringExtra("price");
        tixSum.setText(sum);

        toPayPal = (Button)myView.findViewById(R.id.goToPayPal);
        toPayPal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), dashboard.class);
                intent.putExtra("key2", "passToPayPal");
                intent.putExtra("payPalSum", sum); //to editTextAmount in paypalFrag
                startActivity(intent);
            }
        });
        return myView;
    }




}
