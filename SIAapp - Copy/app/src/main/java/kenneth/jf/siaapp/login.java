package kenneth.jf.siaapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;

import butterknife.ButterKnife;
import butterknife.InjectView;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import static android.R.attr.handle;

public class login extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private static RestTemplate restTemplate;
    private static String username;
    private static String password;

    @InjectView(R.id.input_email) EditText _emailText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.link_login) Button _loginButton;
    @InjectView(R.id.btn_signup) TextView _signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
            @Override
            public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                return true;
            }
        };
        try {
            SSLContext sslContext = org.apache.http.conn.ssl.SSLContexts.custom()
                    .loadTrustMaterial(null, acceptingTrustStrategy)
                    .build();
            SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            CloseableHttpClient httpClient = HttpClientBuilder.create().setSSLSocketFactory(csf).build();

            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);

            RestTemplate restTemplate2 = new RestTemplate(requestFactory);
            //Set basic connection information
            ConnectionInformation.getInstance().setRestTemplate(restTemplate2);
            //SET ADDRESS OF THE SERVER
            ConnectionInformation.getInstance().setUrl("192.168.43.244:8443");
            restTemplate = restTemplate2;
        }
        catch ( Exception e){
            Log.d(TAG,"Error creating the rest template for connection");
        }


        setContentView(R.layout.login);
        ButterKnife.inject(this);
        Button mBtn1 = (Button) findViewById(R.id.goToNextPage);

        mBtn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), dashboard.class);
                startActivity(i);
            }
        });


        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), signup.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login!");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        username = _emailText.getText().toString();
        password = _passwordText.getText().toString();
        Log.d("TEST123","Before runnning task");
        // TODO: Implement your own authentication logic here.
        new HttpRequestTask().execute();
        // = new Handler();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        if ( ConnectionInformation.getInstance().getAuthenticated() ){
                            Log.d("TAG","After authenticated");
                            onLoginSuccess();

                        }
                        else{
                            Log.d("TAG","After NOT authenticated");
                            onLoginFailed();

                        }
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 1700);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        //finish();
        Intent intent = new Intent(getApplicationContext(),dashboard.class);
        startActivity(intent);
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        Log.d("TEST123",email);
        Log.d("TEST123",password);
// || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        if (email.isEmpty()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }
//|| password.length() < 1
        if (password.isEmpty() || password.length() > 10) {

            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }
        if ( !valid){
            Log.d("Test123","invalid");
        }
        return valid;
    }

    public void next(View view){

    }

    private class HttpRequestTask extends AsyncTask<Void, Void, String> {

        protected String doInBackground(Void... params) {
            Log.d("TAG", "DO IN BACKGROUND");
            try {
                String baseIpAddress = ConnectionInformation.getInstance().getUrl();
                String str = username + ":" + password;
                str = str.replace("\n", "");
                String strEncoded = new String(Base64.encodeToString(str.getBytes(), Base64.DEFAULT));
                strEncoded = strEncoded.replace("\n", "");
                JSONObject requestJ2 = new JSONObject();
                HttpHeaders headers2 = new HttpHeaders();
                List<MediaType> list = new ArrayList<MediaType>();
                list.add(MediaType.APPLICATION_JSON);
                headers2.setAccept(list);
                String authEncodedString = new String("Basic " + strEncoded);
                headers2.add("authorization", authEncodedString);
                MappingJackson2HttpMessageConverter jsonHttpMessageConverter2 = new MappingJackson2HttpMessageConverter();
                HttpEntity<String> request2 = new HttpEntity<String>(headers2);
                String url2 = "https://" + baseIpAddress + "/user/loginVerify";
                restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
                restTemplate.getMessageConverters().add(jsonHttpMessageConverter2);
                restTemplate.getMessageConverters()
                        .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
                Log.d("TAG", "BEFORE VERIFYING" + url2);
                // ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);
                //  Log.d("TAG", "GOT SOMETHING");
                //  Object object = responseEntity.getBody();

                //   ResponseEntity<Object> responseEntity = restTemplate2.exchange(url2, HttpMethod.POST, request2, Object.class);
                Log.d("TAG",request2.toString());
                // Log.d("TAG",request2.getBody());
                ResponseEntity<Object> responseEntity = restTemplate.exchange(url2, HttpMethod.GET, request2, Object.class);
                Log.d("TAGGGGGGGGREQUEST", responseEntity.toString());
                Log.d("TAGGGGGGGGREQUEST", responseEntity.getStatusCode().toString());
                String stoken = "";
                String xtoken = "";
                if ( responseEntity.getStatusCode().equals(HttpStatus.OK)){
                    Log.d("TAGRESPONSE", responseEntity.getHeaders().get("Set-Cookie").toString());
                    for ( String s : responseEntity.getHeaders().get("Set-Cookie")){
                        Log.d("STRT IS",s);
                        if ( !s.contains("XSRF-TOKEN=;") && !s.contains("JSESSION")){
                            String[] tokenInfo = s.split(";");
                             xtoken = tokenInfo[0].substring(11);
                            Log.d("TOKEN IS",xtoken);
                        }
                        else if ( s.contains("JSESSIONID") ){
                            String[] stokenInfo = s.split(";");
                             stoken = stokenInfo[0].substring(11);
                            Log.d("SESSION IS",stoken);
                        }
                    }
                    HttpHeaders tempH = new HttpHeaders();
                    List<MediaType> tempL = new ArrayList<MediaType>();
                    tempL.add(MediaType.APPLICATION_JSON);
                    tempH.setAccept(tempL);
                    tempH.add("Cookie", "XSRF-TOKEN=" + xtoken + "; JSESSIONID=" + stoken);
                    tempH.add("X-XSRF-TOKEN", xtoken);
                    Log.d("HEADER", tempH.toString());
                    ConnectionInformation.getInstance().setHeaders(tempH);
                    ConnectionInformation.getInstance().setIsAuthenticated(true);
                }
                else{
                    ConnectionInformation.getInstance().setIsAuthenticated(false);
                    Log.d("TAGRESPONSE",responseEntity.getStatusCode().toString());
                }


            } catch (Exception e) {
                Log.e("TAG", e.getMessage(), e);
            }

            return null;
        }


        protected void onPostExecute(String greeting) {

            Log.d("TAG", "DO POST EXECUTE");
            if ( ConnectionInformation.getInstance().getAuthenticated()){
                Log.d("TAG", "AUTHENTICATED");
            }
            else{
                Log.d("TAG", "NOT AUTHENTICATED");
            }
        }

    }

}