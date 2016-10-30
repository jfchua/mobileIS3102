package kenneth.jf.siaapp;

import android.Manifest;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.paypal.android.sdk.payments.PayPalService;
import com.stripe.Stripe;

import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.powersave.BackgroundPowerSaver;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.paypal.android.sdk.payments.PayPalConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.R.attr.name;
import static android.R.attr.password;
import static kenneth.jf.siaapp.R.attr.homeLayout;


public class dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //POWER SAVER
    private BackgroundPowerSaver backgroundPowerSaver;
    private static String QRresult;
    private static final String TAG = "SIA APP";
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    private static final int REQUEST_COARSE_LOCATION_PERMISSIONS = 1;
    private RestTemplate restTemplate = ConnectionInformation.getInstance().getRestTemplate();
    private String url = ConnectionInformation.getInstance().getUrl();
    public static final String PUBLISHABLE_KEY = "pk_test_zeyJXfY34INxorNSshxu83Q7";
    FragmentManager fragmentManager = getFragmentManager();
    String value = ""; // for event Id

    public static final int PAYPAL_REQUEST_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            System.out.println("KEY2 VALUE IS:   " + extras.get("key2"));
            if (extras.getString("key2").equals("eventInfo")) {
                System.out.println("EVENT ID:             :" + extras.getString("eventId"));
                fragmentManager.beginTransaction().replace(R.id.contentFrame, new EventShowInfo()).commit();
                Log.d("TAG EVENT INFO", extras.getString("key2"));
            } else if (extras.getString("key2").equals("eventTicketing")) {
                fragmentManager.beginTransaction().replace(R.id.contentFrame, new viewTicketList()).commit();
            } else if (extras.getString("key2").equals("ticketSum")) {
                //from viewTicketList
                fragmentManager.beginTransaction().replace(R.id.contentFrame, new paymentSummary()).commit();
            } else if (extras.getString("key2").equals("passToPayPal")) {
                //from paymentSummary
                fragmentManager.beginTransaction().replace(R.id.contentFrame, new PayPalFrag()  ).commit();
            } else if (extras.getString("key2").equals("purchasedTix")) {
                //from paymentSummary
                fragmentManager.beginTransaction().replace(R.id.contentFrame, new purchasedTixList() ).commit();
            } else {
                //this key is inside confirmationActivity
                String value = extras.getString("key");
                System.out.println("Value of the QR code: " + value);
                fragmentManager.beginTransaction().replace(R.id.contentFrame, new QRcode()).commit();
                setResult(value);
            }
        } else {
            fragmentManager.beginTransaction().replace(R.id.contentFrame, new homeLayout()).commit();
            Toast.makeText(this, "HOME", Toast.LENGTH_LONG).show();
        }

        //setContentView(R.layout.login);
        //power saver
        backgroundPowerSaver = new BackgroundPowerSaver(this);


        //paypal

        Intent intent = new Intent(this, PayPalService.class);

        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        startService(intent);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Nothing here yet!!!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        verifyBluetooth();


        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Android M Permission check
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("This app needs location access");
                builder.setMessage("Please grant location access so this app can detect beacons in the background.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                    @TargetApi(23)
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                PERMISSION_REQUEST_COARSE_LOCATION);
                    }

                });
                builder.show();
            }
        }*/

        doDiscovery();
        Log.d("TAG", "Before dashboard task");

        Log.d("TAG", "After dashboard task");

    }

    private void setResult(String value) {
        QRresult = value;
    }

    public String getResult() {
        return QRresult;
    }

    //Paypal Configuration Object
    private static PayPalConfiguration config = new PayPalConfiguration()
            // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(PayPalConfig.PAYPAL_CLIENT_ID);


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_logout) {
            new doLogout().execute();

            Intent intent = new Intent(this, login.class);
            this.startActivity(intent);
        }

        return super.onOptionsItemSelected(item);


    /*    switch (item.getItemId()) {
            case R.id.home:
                return false; //The fragment will take care of it
            default:
                break;
        }*/
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.first_frag) {
            //this is for the profile setting
            fragmentManager.beginTransaction().replace(R.id.contentFrame, new test()).commit();
            Toast.makeText(this, "Going into making requests", Toast.LENGTH_LONG).show();
            // Handle the camera action
            //can do report feedback using the camera
        } else if (id == R.id.second_frag) {
            //gallery of items to order in flight
            fragmentManager.beginTransaction().replace(R.id.contentFrame, new secondFrag()).commit();
            Toast.makeText(this, "Going into Beacon Tracking", Toast.LENGTH_LONG).show();
        } else if (id == R.id.third_frag) {
            fragmentManager.beginTransaction().replace(R.id.contentFrame, new thirdFrag()).commit();
            Toast.makeText(this, "Going into Beacon Ranging", Toast.LENGTH_LONG).show();
            //
        } else if (id == R.id.inFlightOrderingFrag) {
            fragmentManager.beginTransaction().replace(R.id.contentFrame, new inFlightOrderingFrag()).commit();
            Toast.makeText(this, "Going Into In Flight Ordering Menu", Toast.LENGTH_LONG).show();
        } else if (id == R.id.home) {
            fragmentManager.beginTransaction().replace(R.id.contentFrame, new homeLayout()).commit();
            Toast.makeText(this, "Back Home", Toast.LENGTH_LONG).show();
        } else if (id == R.id.eventlisting) {
            fragmentManager.beginTransaction().replace(R.id.contentFrame, new eventlisting()).commit();
            Toast.makeText(this, "Event Listing", Toast.LENGTH_LONG).show();

        } else if (id == R.id.ticketing) {
            fragmentManager.beginTransaction().replace(R.id.contentFrame, new PayPalFrag()).commit();
            Toast.makeText(this, "Payment with PayPal", Toast.LENGTH_LONG).show();

        } else if (id == R.id.payment) {
            fragmentManager.beginTransaction().replace(R.id.contentFrame, new SupportWalletFragment()).commit();
            Toast.makeText(this, "Payment", Toast.LENGTH_LONG).show();
        } else if (id == R.id.qr_scanner) {
            fragmentManager.beginTransaction().replace(R.id.contentFrame, new QR_Scanner()).commit();
            Toast.makeText(this, "Scan For Discount Using QR Scanner", Toast.LENGTH_LONG).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "coarse location permission granted");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons when in the background.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }

                    });
                    builder.show();
                }
                return;
            }
        }
    }

    //checking for permission
    public void doDiscovery() {
        int hasPermission = ActivityCompat.checkSelfPermission(dashboard.this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (hasPermission == PackageManager.PERMISSION_GRANTED) {
            //continueDoDiscovery();
            return;
        }

        ActivityCompat.requestPermissions(dashboard.this,
                new String[]{
                        android.Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_COARSE_LOCATION_PERMISSIONS);
    }



    /*public void onRangingClicked(View view) {
        Intent myIntent = new Intent(this, RangingActivity.class);
        this.startActivity(myIntent);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((BeaconReferenceApplication) this.getApplicationContext()).setMonitoringActivity(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        ((BeaconReferenceApplication) this.getApplicationContext()).setMonitoringActivity(null);
    }*/


    //verify bluetooth is on
    private void verifyBluetooth() {

        try {
            if (!BeaconManager.getInstanceForApplication(this).checkAvailability()) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Bluetooth not enabled");
                builder.setMessage("Please enable bluetooth in settings and restart this application.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        finish();
                        System.exit(0);
                    }
                });
                builder.show();
            }
        } catch (RuntimeException e) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Bluetooth LE not available");
            builder.setMessage("Sorry, this device does not support Bluetooth LE.");
            builder.setPositiveButton(android.R.string.ok, null);
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {
                    finish();
                    System.exit(0);
                }

            });
            builder.show();

        }
    }


    Bundle result = null;

    public void saveData(int id, Bundle data) {
        // based on the id you'll know which fragment is trying to save data(see below)
        // the Bundle will hold the data
        result = new Bundle(data);

    }

    public Bundle getSavedData() {
        // here you'll save the data previously retrieved from the fragments and
        // return it in a Bundle
        return result;
    }

    //paypal
    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }


    private class doLogout extends AsyncTask<Void, Void, String> {

        protected String doInBackground(Void... params) {
            Log.d("TAG", "DO IN BACKGROUND");
            try {

                HttpEntity<String> request2 = new HttpEntity<String>(ConnectionInformation.getInstance().getHeaders());
                String url2 = "https://" + url + "/logout";
                Log.d("TAGTOSTRING ", request2.toString());
                ResponseEntity<Object> responseEntity = restTemplate.exchange(url2, HttpMethod.POST, request2, Object.class);
                Log.d("TAGGGGGGGGREQUEST", responseEntity.getStatusCode().toString());
                if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                    ConnectionInformation.getInstance().setIsAuthenticated(false);
                    Log.d("TAG", "Logged out inside async");
                }

            } catch (Exception e) {
                Log.e("TAG", e.getMessage(), e);
            }

            return null;
        }


        protected void onPostExecute(String greeting) {

            Log.d("TAG", "DO POST EXECUTE");
            if (ConnectionInformation.getInstance().getAuthenticated()) {
                Log.d("TAG", "SERVER LOG OUT DID NOT WORK");
            } else {
                Log.d("TAG", "LOG OUT ON SERVER OK");
            }
        }

    }

}
