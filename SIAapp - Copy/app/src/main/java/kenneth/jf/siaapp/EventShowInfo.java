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

import java.util.ArrayList;

/**
 * Created by User on 24/10/2016.
 */

public class EventShowInfo extends Fragment {
    View myView;
    ArrayList ticketList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.show_event_info, container, false);
      //  displayListView();
        return myView;
    }


    
    //MyCustomAdapter dataAdapter = null;
/*    private void displayListView() {

        //Array list of events
        //addStaticEvents();
        //addStaticEvents2();


        //create an ArrayAdaptar from the String Array
        dataAdapter = new EventShowInfo.MyCustomAdapter(this.getActivity(), R.layout.show_event_info, ticketList);
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

            EventShowInfo.MyCustomAdapter.ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.event_info, null);

                holder = new EventShowInfo.MyCustomAdapter.ViewHolder();
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

                        //  EventList2.remove(position);
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
                        intent.putExtra("key2", "eventInfo");
                        startActivity(intent);


                    }
                });

            }
            else {
                holder = (EventShowInfo.MyCustomAdapter.ViewHolder) convertView.getTag();
            }

            Event Event = EventList.get(position);
            holder.code.setText(" (" +  Event.getPrice() + ")");
            holder.name.setText(Event.getName());
            holder.name.setChecked(Event.isSelected());
            holder.name.setTag(Event);

            return convertView;

        }
    }*/
 /*   @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        EventShowInfo.MyCustomAdapter.ViewHolder holder = null;
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

                    //  EventList2.remove(position);
                    Toast.makeText(getActivity(), "Position: " + position, Toast.LENGTH_LONG);
                    //retrieve Event Details From Backend

                }
            });
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Button cb = (Button) view ;
                    Event event = (Event)cb.getTag();
                  //  int position = getPosition(event);
                    //send details using bundle to the next fragment
                    Intent intent = new Intent(getActivity(),  dashboard.class);
                    intent.putExtra("key2", "eventInfo");
                    startActivity(intent);


                }
            });

        }
        else {
            holder = (EventShowInfo.MyCustomAdapter.ViewHolder) convertView.getTag();
        }

        Event Event = ticketList.get(position);
        holder.code.setText(" (" +  Event.getPrice() + ")");
        holder.name.setText(Event.getName());
        holder.name.setChecked(Event.isSelected());
        holder.name.setTag(Event);

        return convertView;

    }*/

}
