package kenneth.jf.siaapp;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
<<<<<<< HEAD
=======
import org.springframework.http.HttpStatus;
>>>>>>> origin/master
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.paypal.android.sdk.fw.v;
import static kenneth.jf.siaapp.R.layout.event;

/**
 * Created by User on 16/10/2016.
 */

public class eventlisting extends Fragment {
    View myView;
<<<<<<< HEAD
    boolean test = true;
    ArrayList<Event> list = new ArrayList<>();
    private RestTemplate restTemplate = ConnectionInformation.getInstance().getRestTemplate();
    private String url = ConnectionInformation.getInstance().getUrl();
=======
    boolean removeEvent = true;
>>>>>>> origin/master
    ArrayList<Event> EventList = new ArrayList<Event>();
    ArrayList<Event> EventList2 = new ArrayList<Event>();

    FragmentManager fragmentManager = getFragmentManager();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
<<<<<<< HEAD
=======
        myView = inflater.inflate(R.layout.event,container,false);
        displayListView();
        checkButtonClick();


        Button removeBtn = (Button) myView.findViewById(R.id.ButtonRemoveFromCart);

        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i=0;i<EventList.size();i++) {
                    if(EventList.get(i).isSelected()){
                        EventList.remove(i);
                    }
                }

                dataAdapter = new MyCustomAdapter(getActivity(), R.layout.event_info, EventList);
                ListView listView = (ListView) myView.findViewById(R.id.listView1);
                // Assign adapter to ListView
                listView.setAdapter(dataAdapter);

            }
        });
>>>>>>> origin/master

        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Retrieving the Events...");
        progressDialog.show();
        new viewAllEvents().execute();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        if ( ConnectionInformation.getInstance().getAuthenticated() ){
                            Log.d("TAG","After authenticated");
                            displayListView();
                            checkButtonClick();
                        }
                        else{
                            Log.d("TAG","After NOT authenticated");

                        }
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 5000);
        myView = inflater.inflate(event,container,false);

<<<<<<< HEAD

=======
            @Override
            public void onClick(View v) {
                int count=0;
                double price = 0.00;
                for(int i=0;i<EventList.size();i++){
                    if(EventList.get(i).isSelected())
                        count+=1;
                }
                if (count == 0){
                    Toast.makeText(getActivity(), "NO EVENTS SELECTED!!!!!!!!!!", Toast.LENGTH_LONG).show();
                }
                else{
                    for(int i=0;i<EventList.size();i++){
                        price += Double.valueOf(EventList.get(i).getPrice());
                    }
                }
                Intent intent = new Intent(getActivity(), dashboard.class);
                // intent.putParcelableArrayListExtra();
>>>>>>> origin/master


        return myView;
    }

    MyCustomAdapter dataAdapter = null;

    private class viewAllEvents extends AsyncTask<Void, Void, String> {


        protected String doInBackground(Void... params) {
            Log.d("TAG", "DO IN BACKGROUND");
            try {

                HttpEntity<String> request2 = new HttpEntity<String>(ConnectionInformation.getInstance().getHeaders());
                Log.d("TAGGGGGGGGREQUEST", ConnectionInformation.getInstance().getHeaders().getAccept().toString());
                String url2 = "https://" + url + "/tixViewAllEvents";

                Log.d("TAG", "BEFORE VERIFYING" + restTemplate.getMessageConverters().toString());
                Log.d("TAG",request2.toString());
                // Log.d("TAG",request2.getBody());
                ResponseEntity<EventListObject[]> responseEntity = restTemplate.exchange(url2, HttpMethod.GET, request2, EventListObject[].class);

                for ( EventListObject m : responseEntity.getBody()){
                    Event e = new Event();
                    e.setName(m.getEventName());
                    e.setCode(m.getId());
                    e.setSelected(false);
                    EventList.add(e);
                    //return list
                    Log.d("loopforeventlistobject", m.toString());
                }


            } catch (Exception e) {
                Log.e("TAG", e.getMessage(), e);
            }

            return null;
        }

        protected void onPostExecute(String greeting) {

<<<<<<< HEAD
            Log.d("TAG", "DO POST EXECUTE");
            Log.d("EVENT: " , String.valueOf(EventList.size()));
            test = false;
        }
=======
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
>>>>>>> origin/master
    }

    //EVENT LISTING
    private void displayListView() {
        //Array list of events
<<<<<<< HEAD
=======
        addStaticEvents();
        addStaticEvents2();
        new getEvent().execute();

>>>>>>> origin/master

        //create an ArrayAdaptar from the String Array
        // EventList = list;
        System.out.println("Size: " + EventList.size());
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
            this.EventList = new ArrayList<>();
            this.EventList.addAll(EventList);
        }

        private class ViewHolder {
            TextView code;
            CheckBox name;
            Button eventInfo;
            Button ticketList;
        }


        //suppose to populate the arraylist with eventlistobject
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
                holder.eventInfo = (Button) convertView.findViewById(R.id.viewEventInfo);
                holder.ticketList = (Button)convertView.findViewById(R.id.toTicketList);
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

<<<<<<< HEAD
=======
                      //  EventList2.remove(position);
                        Toast.makeText(getActivity(), "Position: " + position, Toast.LENGTH_LONG);
>>>>>>> origin/master
                        //retrieve Event Details From Backend

                    }
                });
<<<<<<< HEAD
=======
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

>>>>>>> origin/master


            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            Event Event = EventList.get(position);
            holder.name.setText(Event.getName());
            holder.name.setChecked(Event.isSelected());
            holder.name.setTag(Event);
            holder.eventInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Button cb = (Button) view ;
                    Event event = (Event)cb.getTag();
                    int pos = position +1;
                    //send details using bundle to the next fragment
                    Intent intent = new Intent(getActivity(),  dashboard.class);
                    intent.putExtra("key2", "eventInfo");
                    intent.putExtra("eventId", pos);
                    startActivity(intent);


                }
            });
            holder.ticketList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Button cb = (Button) view ;
                    Event event = (Event)cb.getTag();
                    int pos = position +1;
                    //send details using bundle to the next fragment
                    Intent intent = new Intent(getActivity(),  dashboard.class);
                    intent.putExtra("key2", "eventTicketing");
                    intent.putExtra("eventId", String.valueOf(pos));
                    System.out.println("FROM POSITION in eventListing: " + pos);
                    startActivity(intent);


                }
            });
            return convertView;

        }
    }

    private void checkButtonClick() {

        Button myButton = (Button) myView.findViewById(R.id.findSelected);
        myButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
<<<<<<< HEAD
                Log.v("Index removed: ", "fkkkk");
=======

>>>>>>> origin/master
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

<<<<<<< HEAD
=======
    }

    private String url = ConnectionInformation.getInstance().getUrl();
    private RestTemplate restTemplate = ConnectionInformation.getInstance().getRestTemplate();

    private class getEvent extends AsyncTask<Void, Void, String> {

        protected String doInBackground(Void... params) {
            Log.d("TAG", "DO IN BACKGROUND");
            try {

                HttpEntity<String> request2 = new HttpEntity<String>(ConnectionInformation.getInstance().getHeaders());
                String url2 = "https://" + url + "/tixViewAllEvents";
                Log.d("TAG TO STRING ",request2.toString());
                ResponseEntity<Event[]> responseEntity = restTemplate.exchange(url2, HttpMethod.POST, request2, Event[].class);
                Log.d("EVENT REQUEST", responseEntity.getStatusCode().toString());
                if ( responseEntity.getStatusCode().equals(HttpStatus.OK)){
                    ConnectionInformation.getInstance().setIsAuthenticated(false);
                    Log.d("TAG","Get Events inside async");
                }

            } catch (Exception e) {
                Log.e("TAG", e.getMessage(), e);
            }

            return null;
        }


        protected void onPostExecute(String greeting) {

            Log.d("TAG", "DO POST EXECUTE");
            if ( ConnectionInformation.getInstance().getAuthenticated()){
                Log.d("TAG", "SERVER LOG OUT DID NOT WORK");
            }
            else{
                Log.d("TAG", "LOG OUT ON SERVER OK");
            }
        }

>>>>>>> origin/master
    }
}