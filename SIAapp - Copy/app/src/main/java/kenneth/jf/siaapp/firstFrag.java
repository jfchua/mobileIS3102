package kenneth.jf.siaapp;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fasterxml.jackson.databind.SerializationFeature;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import static kenneth.jf.siaapp.R.id.btnSubmit;


/**
 * Created by User on 5/10/2016.
 */

public class firstFrag extends Fragment {
    View myView;
    private Button btnSubmitRequest;
    private EditText seat;
    private EditText request;
    private String seatInfo;
    private String requestInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.first_frag,container,false);
        //addListenerOnButton();
        return myView;
    }
 /*   public void addListenerOnButton() {

        btnSubmitRequest = (Button) myView.findViewById(R.id.btnSubmitRequest);
        seat = (EditText) myView.findViewById(R.id.seatInput);
        request = (EditText) myView.findViewById(R.id.requestInput);


        btnSubmitRequest.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                seatInfo =  seat.getText().toString();
                requestInfo = request.getText().toString();

                Toast.makeText(getActivity(),
                        "Made a request with info : " +
                                "\nRequest: "+ request.getText()
                                +"\nSeat: " + seat.getText(),
                        Toast.LENGTH_SHORT).show();

                new HttpRequestTask().execute();
                //Toast.makeText(this,"OnClickListener : ",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class HttpRequestTask extends AsyncTask<Void, Void,String> {

        protected String doInBackground(Void... params) {
            Log.d("TAG","DO IN BACKGROUND");
            try {
                JSONObject requestJ2 = new JSONObject();
                requestJ2.put("request", requestInfo);
                requestJ2.put("seat", seatInfo);

                Log.d("TAG",requestJ2.toString());
                HttpHeaders headers2 = new HttpHeaders();
                headers2.setContentType(MediaType.APPLICATION_JSON);
                RestTemplate restTemplate2 = new RestTemplate();
                MappingJackson2HttpMessageConverter jsonHttpMessageConverter2 = new MappingJackson2HttpMessageConverter();
                jsonHttpMessageConverter2.getObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

                HttpEntity<String> request2 = new HttpEntity<String>(
                        requestJ2.toString(), headers2);
                String url2 = "http://192.168.43.153/SIAPOSTREQUESTS.php";
                restTemplate2.getMessageConverters().add(jsonHttpMessageConverter2);
                restTemplate2.getMessageConverters().add(new FormHttpMessageConverter());
                restTemplate2.getMessageConverters().add(jsonHttpMessageConverter2);
                Log.d("TAG","BEFORE POSTING");
                String response2 = restTemplate2
                        .postForObject(url2, request2, String.class);
                Log.d("TAGGGGGGGGREQUEST",response2.toString());

            } catch (Exception e) {
                Log.e("TAG", e.getMessage(), e);
            }

            return null;
        }


        protected void onPostExecute(String greeting) {
            Log.d("TAG","DO POST EXECUTE");
        }

    }
*/



}
