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
import android.widget.Spinner;
import android.widget.Toast;

import com.fasterxml.jackson.databind.SerializationFeature;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by User on 5/10/2016.
 */

public class inFlightOrderingFrag extends Fragment {
    View myView;
    private Spinner spinner1, spinner2;
    private Button btnSubmit;
    private EditText seat;
    private String spin1;
    private String spin2;
    private String seat1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.inflight, container, false);
        addListenerOnButton();
        addListenerOnSpinner1ItemSelection();
        addListenerOnSpinner2ItemSelection();
        return myView;
    }


    public void addListenerOnSpinner1ItemSelection() {
        spinner1 = (Spinner) myView.findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    public void addListenerOnSpinner2ItemSelection() {
        spinner2 = (Spinner) myView.findViewById(R.id.spinner2);
        spinner2.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    // get the selected dropdown list value
    public void addListenerOnButton() {

        spinner1 = (Spinner) myView.findViewById(R.id.spinner1);
        spinner2 = (Spinner) myView.findViewById(R.id.spinner2);
        btnSubmit = (Button) myView.findViewById(R.id.btnSubmit);
        seat = (EditText) myView.findViewById(R.id.seat);


        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                seat1 = seat.getText().toString();
                spin1 = String.valueOf(spinner1.getSelectedItem());
                spin2 = String.valueOf(spinner2.getSelectedItem());
                Toast.makeText(getActivity(),
                        "Ordered food: " +
                                "\nSpinner 1 : "+ String.valueOf(spinner1.getSelectedItem()) +
                                "\nSpinner 2 : "+ String.valueOf(spinner2.getSelectedItem())
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
                //GET
               /* final String url = "http://192.168.43.153/SIA.php";
                RestTemplate restTemplate = new RestTemplate();
                Log.d("TAG","CREATED NEW REST TEMPLATE");
                final MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
                mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
                restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
                Log.d("TAG","DO GET MESSAGE CONVERTERS");
            //    String greet = restTemplate.getForObject(url, String.class);
             //   restTemplate.get
                ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);
                Log.d("TAG", "GOT SOMETHING");
                Object object = responseEntity.getBody();
                MediaType contentType = responseEntity.getHeaders().getContentType();
                HttpStatus statusCode = responseEntity.getStatusCode();
                Log.d("TAG", "RESPONSEENTTIY: " + contentType + " " + statusCode +"ITEM IS : " + object.toString() );
                return object.toString();*/

                //POST
                JSONObject requestJ = new JSONObject();
                requestJ.put("food", spin1);
                requestJ.put("drink", spin2);
                requestJ.put("seat", seat1);
                // String data = "{\"name\": \"JFK\", \"passport\": \"W12345\"}";
                Log.d("TAG",requestJ.toString());
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                RestTemplate restTemplate = new RestTemplate();
                MappingJackson2HttpMessageConverter jsonHttpMessageConverter = new MappingJackson2HttpMessageConverter();
                jsonHttpMessageConverter.getObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

                HttpEntity<String> request = new HttpEntity<String>(
                        requestJ.toString(), headers);
                String url = "http://192.168.43.153/SIAPOSTFOOD.php";
                restTemplate.getMessageConverters().add(jsonHttpMessageConverter);
                restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
                restTemplate.getMessageConverters().add(jsonHttpMessageConverter);
                Log.d("TAG","BEFORE POSTING");
                String response = restTemplate
                        .postForObject(url, request, String.class);
                Log.d("TAGGGGGGGG",response.toString());

            } catch (Exception e) {
                Log.e("TAG", e.getMessage(), e);
            }

            return null;
        }


        protected void onPostExecute(String greeting) {
            Log.d("TAG","DO POST EXECUTE");
        }

    }




}