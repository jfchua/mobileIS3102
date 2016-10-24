package kenneth.jf.siaapp;

import android.app.FragmentManager;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by User on 24/10/2016.
 */


//USELESS
public class PayPalActivity extends AppCompatActivity {
    FragmentManager fm = getFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        fm.beginTransaction().replace(R.id.contentFrame, new PayPalFrag()).commit();
    }
}
