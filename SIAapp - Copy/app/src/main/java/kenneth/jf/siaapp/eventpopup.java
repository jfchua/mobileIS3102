package kenneth.jf.siaapp;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by User on 13/10/2016.
 */

public class eventpopup extends Fragment{
    View myView;
    TextView textView;
    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.event,container,false);
        textView = (TextView) myView.findViewById(R.id.textView);
        // get the intent from which this activity is called.
        Intent intent = getActivity().getIntent();

        // fetch value from key-value pair and make it visible on TextView.
        String item = intent.getStringExtra("selected-item");
        textView.setText("you selected "+item);
        return myView;
    }




}
