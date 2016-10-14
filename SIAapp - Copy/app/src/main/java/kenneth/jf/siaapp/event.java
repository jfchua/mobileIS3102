package kenneth.jf.siaapp;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by User on 13/10/2016.
 */

public class event extends Fragment{
    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.event,container,false);
        ListView listview;
        String[] foody;
        listview = (ListView) myView.findViewById(R.id.listView);
        // Array holding our data
        foody = new String[]{"kenneth", "sucks", "chocolate", "ice-cream", "banana", "apple"};
        //adapter which will convert each data item into view item.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_view_row, R.id.listText, foody);
        //place each view-item inside listview by setting adapter for our listview
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new ListClickHandler());
        return myView;
    }
    public class ListClickHandler implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {
            // TODO Auto-generated method stub
            TextView listText = (TextView) view.findViewById(R.id.listText);
            String text = listText.getText().toString();
            // create intent to start another activity
            Intent intent = new Intent(getActivity(), eventpopup.class);
            // add the selected text item to our intent.
            intent.putExtra("selected-item", text);
            startActivity(intent);

        }
    }
};
