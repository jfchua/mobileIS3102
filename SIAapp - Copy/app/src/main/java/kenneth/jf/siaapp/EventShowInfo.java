package kenneth.jf.siaapp;

import android.app.ActionBar;
import android.app.Fragment;
<<<<<<< HEAD
import android.app.ProgressDialog;
import android.os.AsyncTask;
=======
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
>>>>>>> origin/master
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
<<<<<<< HEAD
import android.widget.TextView;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;
=======
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
>>>>>>> origin/master

/**
 * Created by User on 24/10/2016.
 */

public class EventShowInfo extends Fragment {
    View myView;
<<<<<<< HEAD
    int i;
    private RestTemplate restTemplate = ConnectionInformation.getInstance().getRestTemplate();
    private String url = ConnectionInformation.getInstance().getUrl();
    TextView title;
    TextView type;
    TextView startDate;
    TextView endDate;
    TextView desc;
    TextView address;

    private eventDetailsObject eventDetail;

=======
    ArrayList ticketList;
>>>>>>> origin/master
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        try {
            final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.AppTheme);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Getting Event Info");
            progressDialog.show();
            eventDetail = new eventDetailsObject();
            new viewAnEvent().execute().get();
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            // On complete call either onLoginSuccess or onLoginFailed
                            if ( ConnectionInformation.getInstance().getAuthenticated() ){
                                Log.d("TAG","After authenticated");

                                title = (TextView)myView.findViewById(R.id.eventTitle123);
                                type = (TextView)myView.findViewById(R.id.eventType);
                                startDate = (TextView)myView.findViewById(R.id.eventStartDate);
                                endDate = (TextView)myView.findViewById(R.id.eventEndDate);
                                desc = (TextView)myView.findViewById(R.id.eventDescription);
                                address = (TextView)myView.findViewById(R.id.eventAddress);



                                System.out.println("REACHED HERE AT EVENT SHOW INFO: " + eventDetail.getTitle());

                                title.setText(eventDetail.getTitle());
                                type.setText(eventDetail.getType());
                                startDate.setText(eventDetail.getStartDate());
                                endDate.setText(eventDetail.getEndDate());
                                desc.setText(eventDetail.getDescription());
                                //address.setText(eventDetail.getAddress().toString());
                            }
                            else{
                                Log.d("TAG","After NOT authenticated");

                            }
                            // onLoginFailed();
                            progressDialog.dismiss();
                        }
                    }, 5000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        myView = inflater.inflate(R.layout.show_event_info, container, false);
<<<<<<< HEAD



=======
      //  displayListView();
>>>>>>> origin/master
        return myView;
    }


<<<<<<< HEAD
    private class viewAnEvent extends AsyncTask<Void, Void, String> {

        protected String doInBackground(Void... params) {
            Log.d("TAG", "DO IN BACKGROUND");
            try {
                JSONObject request = new JSONObject();
                //Event ID
                int value = getActivity().getIntent().getIntExtra("eventId",1);
                request.put("eventId", value);
                System.out.println("EVENT ID OF VIEWANEVENT in eventShowInfo: " + value);





                HttpEntity<String> request2 = new HttpEntity<String>(request.toString(),ConnectionInformation.getInstance().getHeaders());
                Log.d("TAGGGGGGGGREQUEST", ConnectionInformation.getInstance().getHeaders().getAccept().toString());
                String url2 = "https://" + url + "/tixViewEvent";

                Log.d("TAG", "BEFORE VERIFYING" + restTemplate.getMessageConverters().toString());
                Log.d("TAG",request2.toString());
                // Log.d("TAG",request2.getBody());
                ResponseEntity<eventDetailsObject> responseEntity = restTemplate.exchange(url2, HttpMethod.POST, request2, eventDetailsObject.class);

                eventDetail = responseEntity.getBody();
                Log.d("loopforeventlistobject", responseEntity.getBody().getTitle());
                Log.d("loopforeventlistobject", responseEntity.getBody().getAddress().toString());



            } catch (Exception e) {
                Log.e("TAG", e.getMessage(), e);
            }

            return null;
        }


        protected void onPostExecute(String greeting) {

            Log.d("TAG", "DO POST EXECUTE");
        }




    }

=======
    
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
>>>>>>> origin/master

}
