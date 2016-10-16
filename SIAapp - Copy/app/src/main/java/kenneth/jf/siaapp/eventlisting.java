package kenneth.jf.siaapp;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ListFragment;
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

import static android.R.id.message;

/**
 * Created by User on 16/10/2016.
 */

public class eventlisting extends Fragment {
    public ViewPager viewPager;

    private OnFragmentInteractionListener mListener;

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


                String userContent = (String)adapterView.getItemAtPosition(position);
                int size = adapterView.getCount();

                if (mListener != null) {
                    mListener.onFragmentInteraction(userContent, size);
                }
                Toast.makeText(getActivity(),"HELLO, THE POSITION : " + position + " IS SELECTED...", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String userContent, int size);
    }





    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
