package kenneth.jf.siaapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static android.R.attr.password;

public class signup extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    private String exceptionMessage = "";
    private boolean signUp = false;

    @InjectView(R.id.input_name) EditText _nameText;
    @InjectView(R.id.input_email) EditText _emailText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.btn_signup) Button _signupButton;
    @InjectView(R.id.link_login) TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        ButterKnife.inject(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }


        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(signup.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Implement your own signup logic here.
        new registerTask().execute(name,email,password);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        if ( signUp ){
                            onSignupSuccess();
                        }
                        else{
                            onSignupFailed();
                        }
                        progressDialog.dismiss();
                    }
                }, 2100);
    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Signup failed. " , Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 2) {
            _nameText.setError("Your name should contain at least 2 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("Please enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 12) {
            _passwordText.setError("between 4 and 12 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    private class registerTask extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... params) {
            Log.d("TAG", "DO IN BACKGROUND");
            try {
                RestTemplate restTemplate = ConnectionInformation.getInstance().getRestTemplate();
                restTemplate.setErrorHandler(new DefaultResponseErrorHandler(){
                    protected boolean hasError(HttpStatus statusCode) {
                        Log.d("STATUS CODE IS " , statusCode.toString());
                        if ( statusCode.equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
                            return false;
                        }
                        else if ( statusCode.equals(HttpStatus.OK)){
                            return false;
                        }
                        return true;
                    }});
                HttpHeaders headers2 = new HttpHeaders();
                List<MediaType> list = new ArrayList<MediaType>();
                list.add(MediaType.APPLICATION_JSON);
                headers2.setAccept(list);
                MappingJackson2HttpMessageConverter jsonHttpMessageConverter2 = new MappingJackson2HttpMessageConverter();
                restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
                restTemplate.getMessageConverters().add(jsonHttpMessageConverter2);
                restTemplate.getMessageConverters()
                        .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));


                String name = params[0];
                String username = params[1];
                String password = params[2];
                JSONObject request = new JSONObject();
                request.put("name", name);
                request.put("username", username);
                request.put("password", password);
                Log.d("TAG",request.toString());

                HttpEntity<String> request2 = new HttpEntity<String>(request.toString(),headers2);
                Log.d("TAGGGGGGGGREQUEST", headers2.getAccept().toString());
                String url2 = "https://" + ConnectionInformation.getInstance().getUrl() + "/registerNewUser";
                Log.d("TAGGGGGGGGREQUEST", request2.getBody().toString());
                ResponseEntity<String> responseEntity = restTemplate.exchange(url2, HttpMethod.POST, request2, String.class);
                Log.d("TAG",responseEntity.getStatusCode().toString());
                if ( responseEntity.getStatusCode().equals(HttpStatus.OK)){
                    Log.d("TAG","SIGNUP OK!");
                    signUp = true;
                }
                else if ( responseEntity.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR) ){
                    signUp = false;
                    Log.d("TAG",responseEntity.getBody().toString());
                    exceptionMessage = responseEntity.getBody().toString();
                }
                else{
                    Log.d("TAG",responseEntity.getBody().toString());
                }

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