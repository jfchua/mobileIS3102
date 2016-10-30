package kenneth.jf.siaapp;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


/**
 * Created by User on 28/10/2016.
 */

public class purchasedTixList extends Fragment{
    View myView;
    ListView lv;
    ArrayList<TicketObject> list = new ArrayList<>();
    private RestTemplate restTemplate = ConnectionInformation.getInstance().getRestTemplate();
    private String url = ConnectionInformation.getInstance().getUrl();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.purchased_ticketlist, container, false);
        try {
            new viewPurchasedTix().execute().get();
         /*   for(int i=0;i<list.size();i++){
                System.err.println("LISTVIEW: "+ list.size());
            }*/

            lv = (ListView)myView.findViewById(R.id.purchasedTixList);
           // TicketObject[] arr = (TicketObject[]) list.toArray();
            lv.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, list));


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return myView;
    }

    private class viewPurchasedTix extends AsyncTask<Void, Void, String> {

        protected String doInBackground(Void... params) {
            Log.d("TAG", "DO IN BACKGROUND");
            try {


                HttpEntity<String> request2 = new HttpEntity<String>(ConnectionInformation.getInstance().getHeaders());
                Log.d("TAGGGGGGGGREQUEST", ConnectionInformation.getInstance().getHeaders().getAccept().toString());
                String url2 = "https://" + url + "/tixGetTix";


                Log.d("TAG", "BEFORE VERIFYING" + restTemplate.getMessageConverters().toString());
                Log.d("TAG",request2.toString());
                // Log.d("TAG",request2.getBody());
                ResponseEntity<TicketObject[]> responseEntity = restTemplate.exchange(url2, HttpMethod.GET, request2, TicketObject[].class);
                Log.d("TAG",responseEntity.getStatusCode().toString());



                for ( TicketObject o : responseEntity.getBody()){
                    TicketObject ticketObject = o;
                    list.add(ticketObject);
                   // System.err.println("Tickets: " + o.getTicketDetails());
                }

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
