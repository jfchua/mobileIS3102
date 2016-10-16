package kenneth.jf.siaapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by User on 14/10/2016.
 */

public class SupportWalletFragment extends Fragment {
    View myView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.supportwalletfrag, container, false);
        return myView;
    }
}
