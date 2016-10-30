package kenneth.jf.siaapp;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;

/**
 * Created by User on 24/10/2016.
 */

public class EventShowInfo extends Fragment {
    View myView;
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



        return myView;
    }


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


}
