package kenneth.jf.siaapp;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.paypal.android.sdk.fw.v;

/**
 * Created by User on 16/10/2016.
 */

public class eventlisting extends Fragment {
    View myView;
    ArrayList<Event> EventList = new ArrayList<Event>();
    ArrayList<Event> EventList2 = new ArrayList<Event>();
    ArrayList<Event> EventList3 = new ArrayList<Event>();
    FragmentManager fragmentManager = getFragmentManager();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.event,container,false);
        displayListView();
        checkButtonClick();


        Button removeBtn = (Button) myView.findViewById(R.id.ButtonRemoveFromCart);

        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str=new String();
                for(int i=0;i<EventList2.size();i++) {
                    str=str.concat(EventList2.get(i).getName());

                }
                Toast.makeText(getActivity(), "EVENT LISTING 2: " + str, Toast.LENGTH_SHORT).show();
                dataAdapter = new MyCustomAdapter(getActivity(), R.layout.event_info, EventList2);
                ListView listView = (ListView) myView.findViewById(R.id.listView1);
                // Assign adapter to ListView
                listView.setAdapter(dataAdapter);

            }
        });

        Button proceedToCheckOut = (Button) myView.findViewById(R.id.proceedToCheckOut);
        proceedToCheckOut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), dashboard.class);
                // intent.putParcelableArrayListExtra();


            }
        });

        return myView;
    }

    MyCustomAdapter dataAdapter = null;

    /*private ActionBar actionBar;
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

        ListView mListView = (ListView) getActivity().findViewById(R.id.databaselist);

        mArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, values);

        mListView.setAdapter(mArrayAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg3) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.contentFrame, new eventlistingpopup()).commit();
                String index = (String) adapterView.getItemAtPosition(position);


                String userContent = (String) adapterView.getItemAtPosition(position);
                int size = adapterView.getCount();

                Bundle bundle = new Bundle();
                bundle.putString("value", userContent); //any string to be sent
                dashboard activity = (dashboard) getActivity();
                activity.saveData(1, bundle);


                Toast.makeText(getActivity(), "HELLO, THE POSITION : " + position + " IS SELECTED...", Toast.LENGTH_SHORT).show();
            }
        });
    }*/


    public void addStaticEvents(){
        Event Event = new Event("Jay Chou Concert","$111.00",false);
        EventList.add(Event);
        Event = new Event("Comex IT Fair","$11.00",false);
        EventList.add(Event);
        Event = new Event("Meet and Greet","$23.30",false);
        EventList.add(Event);
        Event = new Event("Great Singapore Sale","$132.10",false);
        EventList.add(Event);
        Event = new Event("DBS Annual Meeting","$123.45",false);
        EventList.add(Event);
        Event = new Event("JJ LIN Concert","$123.00",false);
        EventList.add(Event);
        Event = new Event("Job Fair","$11.00",false);
        EventList.add(Event);
    }
    public void addStaticEvents2(){
        Event Event = new Event("Jay Chou Concert","$111.00",false);
        EventList2.add(Event);
        Event = new Event("Comex IT Fair","$11.00",false);
        EventList2.add(Event);
        Event = new Event("Meet and Greet","$23.30",false);
        EventList2.add(Event);
        Event = new Event("Great Singapore Sale","$132.10",false);
        EventList2.add(Event);
        Event = new Event("DBS Annual Meeting","$123.45",false);
        EventList2.add(Event);
        Event = new Event("JJ LIN Concert","$123.00",false);
        EventList2.add(Event);
        Event = new Event("Job Fair","$11.00",false);
        EventList2.add(Event);
    }
    
    public void remove(int i){
        EventList2.remove(i);
    }

    //EVENT LISTING
    private void displayListView() {

        //Array list of events
        addStaticEvents();
        addStaticEvents2();


        //create an ArrayAdaptar from the String Array
        dataAdapter = new MyCustomAdapter(this.getActivity(), R.layout.event_info, EventList);
        ListView listView = (ListView) myView.findViewById(R.id.listView1);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Event event = (Event) parent.getItemAtPosition(position);
                Toast.makeText(getActivity(),
                        "Clicked on Row: " + event.getName(),
                        Toast.LENGTH_LONG).show();


            }
        });

    }

    private class MyCustomAdapter extends ArrayAdapter<Event> {

        private ArrayList<Event> EventList;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<Event> EventList) {
            super(context, textViewResourceId, EventList);
            this.EventList = new ArrayList<Event>();
            this.EventList.addAll(EventList);
        }

        private class ViewHolder {
            TextView code;
            CheckBox name;
            Button button;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.event_info, null);

                holder = new ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.code);
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                holder.button = (Button) convertView.findViewById(R.id.viewEventInfo);
                convertView.setTag(holder);

                holder.name.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        Event Event = (Event) cb.getTag();
                        Toast.makeText(getActivity(),
                                "Clicked on Checkbox: " + cb.getText() +
                                        " is " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();
                        Event.setSelected(cb.isChecked());
                        EventList2.remove(position);
                        Toast.makeText(getActivity(), "Position: " + position, Toast.LENGTH_LONG);
                        //retrieve Event Details From Backend

                    }
                });
                holder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Button cb = (Button) view ;
                        Event event = (Event)cb.getTag();
                        int position = getPosition(event);
                        //send details using bundle to the next fragment
                        Intent intent = new Intent(getActivity(),  dashboard.class);
                        intent.putExtra("key2", "eventPayment");
                        startActivity(intent);


                    }
                });

            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            Event Event = EventList.get(position);
            holder.code.setText(" (" +  Event.getPrice() + ")");
            holder.name.setText(Event.getName());
            holder.name.setChecked(Event.isSelected());
            holder.name.setTag(Event);

            return convertView;

        }
    }

    private void checkButtonClick() {

        Button myButton = (Button) myView.findViewById(R.id.findSelected);
        myButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.v("Indexremoved: ", "fkkkk");
                StringBuffer responseText = new StringBuffer();
                responseText.append("The following were selected...\n");
                //this list shows the events that are selected
                ArrayList<Event> EventList = dataAdapter.EventList;
                for(int i=0;i<EventList.size();i++){
                    Event Event = EventList.get(i);

                    if(Event.isSelected()){
                        responseText.append("\n" + Event.getName());
                    }
                }
                Toast.makeText(getActivity(),
                        responseText, Toast.LENGTH_LONG).show();

            }
        });


    }
}