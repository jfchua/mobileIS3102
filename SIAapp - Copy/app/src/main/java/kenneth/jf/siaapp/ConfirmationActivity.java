package kenneth.jf.siaapp;

/**
 * Created by User on 17/10/2016.
 */

import android.app.Activity;
import android.app.FragmentManager;

import android.content.Intent;
import android.os.AsyncTask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class ConfirmationActivity extends AppCompatActivity {

    ArrayList<String> numTixList;
    ArrayList<Ticket> ticketList;
    int num;

    private RestTemplate restTemplate = ConnectionInformation.getInstance().getRestTemplate();
    private String url = ConnectionInformation.getInstance().getUrl();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        Intent intent = getIntent();


        Intent intents = getIntent();
        numTixList = intents.getExtras().getStringArrayList("spinList");
        ticketList = intents.getExtras().getParcelableArrayList("ticketList");

        //To View Ticket List
        Button button = (Button) findViewById(R.id.viewTicketList);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
                StringBuilder sb = new StringBuilder();
                Random random = new Random();
                for (int i = 0; i < 20; i++) {
                    char c = chars[random.nextInt(chars.length)];
                    sb.append(c);
                }

                //This is the string for the QR code, send to backend from here????
                String output = sb.toString();



                System.out.println(output);
                Intent intent = new Intent(ConfirmationActivity.this, dashboard.class);
                intent.putExtra("key",output);
                startActivity(intent);

                // Do something in response to button click
            }
        });




        try {
            JSONObject jsonDetails = new JSONObject(intent.getStringExtra("PaymentDetails"));

            //Displaying payment details
            showDetails(jsonDetails.getJSONObject("response"), intent.getStringExtra("PaymentAmount"));
        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        Button viewPurchasedTix = (Button)findViewById(R.id.viewTicketList);
        viewPurchasedTix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Intent i = new Intent(ConfirmationActivity.this, dashboard.class);
                i.putExtra("key2","purchasedTix");
                startActivity(i);


            }
        });



    }

    private void showDetails(JSONObject jsonDetails, String paymentAmount) throws JSONException {
        //Views
        TextView textViewId = (TextView) findViewById(R.id.paymentId);
        TextView textViewStatus= (TextView) findViewById(R.id.paymentStatus);
        TextView textViewAmount = (TextView) findViewById(R.id.paymentAmount);

        //Showing the details from json object
        textViewId.setText(jsonDetails.getString("id"));
        textViewStatus.setText(jsonDetails.getString("state"));
        textViewAmount.setText(paymentAmount+" USD");

        if(jsonDetails.getString("state").equals("approved")){

            try {
                new confirmPayment().execute().get();






            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }


        }


    }

    public class confirmPayment extends AsyncTask<Void, Void, String> {






        protected String doInBackground(Void... params) {
            Log.d("TAG", "DO IN BACKGROUND");
            try {

                JSONObject request = new JSONObject();

                JSONArray jarray = new JSONArray();



                for(int i=0;i<num;i++){

                    if(Integer.valueOf(numTixList.get(i)) == 0){
                        continue;
                    }

                    JSONObject jsonObject= new JSONObject();
                    jsonObject.put("categoryId", ticketList.get(i));
                    jsonObject.put("numTickets", numTixList.get(i));
                    jarray.put(jsonObject);
                }






                JSONObject jsonObjectw= new JSONObject();
                jsonObjectw.put("categoryId", 2);
                jsonObjectw.put("numTickets", 3);
                jarray.put(jsonObjectw);
                request.put("ticketsJSON", jarray);
                request.put("paymentId", "12345");
                HttpEntity<String> request2 = new HttpEntity<String>(request.toString(),ConnectionInformation.getInstance().getHeaders());
                Log.d("TAGGGGGGGGREQUEST", ConnectionInformation.getInstance().getHeaders().getAccept().toString());
                String url2 = "https://" + url + "/tixBuyTicket";


                Log.d("TAG", "BEFORE VERIFYING" + restTemplate.getMessageConverters().toString());
                Log.d("TAG",request2.toString());
                // Log.d("TAG",request2.getBody());
                ResponseEntity<eventDetailsObject> responseEntity = restTemplate.exchange(url2, HttpMethod.POST, request2, eventDetailsObject.class);
                Log.d("TAG",responseEntity.getStatusCode().toString());
                // ResponseEntity<CategoryObject[]> responseEntity2 = restTemplate.exchange("https://" + url + "/tixViewEventCat", HttpMethod.POST, request2, CategoryObject[].class);


                // Log.d("loopforeventlistobject", responseEntity.getBody().toString());
                //for( CategoryObject o : responseEntity2.getBody()){
                //    Log.d("loopforeventlistobject2", o.toString());
                // }


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