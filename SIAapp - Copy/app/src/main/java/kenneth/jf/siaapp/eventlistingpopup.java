package kenneth.jf.siaapp;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 16/10/2016.
 */

public class eventlistingpopup extends Fragment{

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.eventpopup, container,false);

        ArrayAdapter<String> mArrayAdapter;


        dashboard activity = (dashboard)getActivity();
        Bundle savedData = activity.getSavedData();
        Toast.makeText(this.getContext(),"VALUE GOTTEN: "+ savedData.getString("value"),Toast.LENGTH_LONG).show();
        ListView detailsListView = (ListView) view.findViewById(R.id.ListViewCatalog);
        ArrayList<String> details = new ArrayList<String>();

        if(savedData != null){
            String value = savedData.getString("value");
            details.add(value);
        }
        mArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, details);

        detailsListView.setAdapter(mArrayAdapter);

        return view;
    }

}
