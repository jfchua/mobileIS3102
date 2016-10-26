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
    private EditText feedbackBox;
    private String feedbackCategory;
    private String spin2;
    private String feedback;

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
        //spinner2 = (Spinner) myView.findViewById(R.id.spinner2);
        //spinner2.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    // get the selected dropdown list value
    public void addListenerOnButton() {

        spinner1 = (Spinner) myView.findViewById(R.id.spinner1);
        //spinner2 = (Spinner) myView.findViewById(R.id.spinner2);
        btnSubmit = (Button) myView.findViewById(R.id.btnSubmit);
        feedbackBox = (EditText) myView.findViewById(R.id.seat);


        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                feedback = feedbackBox.getText().toString();
                feedbackCategory = String.valueOf(spinner1.getSelectedItem());
//                spin2 = String.valueOf(spinner2.getSelectedItem());
                Toast.makeText(getActivity(),
                        "Feedback Category: " +
                                "\nSpinner 1 : "+ String.valueOf(spinner1.getSelectedItem())
                        +"\nFeedback: " + feedbackBox.getText(),
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
                //POST
                JSONObject requestJ = new JSONObject();
                feedback = feedbackCategory + " : " + feedback;
                requestJ.put("drink", spin2);
                requestJ.put("feedback", feedback);
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