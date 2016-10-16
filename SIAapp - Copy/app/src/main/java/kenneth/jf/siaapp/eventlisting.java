package kenneth.jf.siaapp;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

/**
 * Created by User on 16/10/2016.
 */

public class eventlisting extends Fragment {
    public ViewPager viewPager;

    private ActionBar actionBar;
    private String[] tabs = { "Tab1", "Tab2" };
    String[] values = new String[] { "Android List View",
            "Adapter implementation",
            "Simple List View In Android",
            "Create List View Android",
            "Android Example",
            "List View Source Code",
            "List View Array Adapter",
            "Android Example List View"
    };
    List<String> eventList = Arrays.asList(values);
    ArrayAdapter<String> mArrayAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View databaseview = inflater.inflate(R.layout.event, container,
                false);



        return databaseview;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

        ListView mListView = (ListView)getActivity().findViewById(R.id.databaselist);

        mArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, values);

        mListView.setAdapter(mArrayAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg3)
            {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.contentFrame, new eventlistingpopup()).commit();
                String index = (String)adapterView.getItemAtPosition(position);
                int pos = Integer.parseInt(index);
                Bundle bundle = new Bundle();
                bundle.putString("edttext", "From Activity");
// set Fragmentclass Arguments
                Fragmentclass fragobj = new Fragmentclass();
                fragobj.setArguments(bundle);
                Toast.makeText(getActivity(),"HELLO, THE POSITION : " + position + " IS SELECTED...", Toast.LENGTH_SHORT).show();
            }
        });




    }
}
