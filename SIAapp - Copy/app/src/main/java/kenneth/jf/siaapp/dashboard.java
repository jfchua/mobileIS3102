package kenneth.jf.siaapp;

import android.Manifest;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.powersave.BackgroundPowerSaver;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //POWER SAVER
    private BackgroundPowerSaver backgroundPowerSaver;

    private static final String TAG = "SIA APP";
    private static final int PERMISSION_REQUEST_COARSE_LOCATION =1 ;
    private static final int REQUEST_COARSE_LOCATION_PERMISSIONS = 1;
    private RestTemplate restTemplate = ConnectionInformation.getInstance().getRestTemplate();
    private String url = ConnectionInformation.getInstance().getUrl();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        //setContentView(R.layout.login);
        //power saver
        backgroundPowerSaver = new BackgroundPowerSaver(this);

       Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,drawer,toolbar,  R.string.navigation_drawer_open, R.string.navigation_drawer_close);
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
        Log.d("TAG","Before dashboard task");
        new HttpRequestTask2().execute();
        Log.d("TAG","After dashboard task");

    }




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
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();

        if (id == R.id.first_frag) {
            //this is for the profile setting
            fragmentManager.beginTransaction().replace(R.id.contentFrame, new firstFrag()).commit();
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
        }else if (id == R.id.home) {
            fragmentManager.beginTransaction().replace(R.id.contentFrame, new homeFrag()).commit();
            Toast.makeText(this, "Back Home", Toast.LENGTH_LONG).show();
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
        }
        catch (RuntimeException e) {
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
    private class HttpRequestTask2 extends AsyncTask<Void, Void, String> {

        protected String doInBackground(Void... params) {
            Log.d("TAG", "DO IN BACKGROUND");
            try {
                JSONObject requestJ2 = new JSONObject();
                HttpHeaders headers2 = new HttpHeaders();
                List<MediaType> list = new ArrayList<MediaType>();
                list.add(MediaType.APPLICATION_JSON);
                headers2.setAccept(list);
                // MappingJackson2HttpMessageConverter jsonHttpMessageConverter2 = new MappingJackson2HttpMessageConverter();
                HttpEntity<String> request2 = new HttpEntity<String>(headers2);
                String url2 = "https://" + url + "/user/notifications/findAllNotifications";
                Log.d("TAG", "BEFORE VERIFYING" + restTemplate.getMessageConverters().toString());
                Log.d("TAG",request2.toString());
                // Log.d("TAG",request2.getBody());
                ResponseEntity<Message[]> responseEntity = restTemplate.exchange(url2, HttpMethod.GET, request2, Message[].class);
           //   Message[] responseEntity = restTemplate.getForObject(url2,Message[].class);
                Log.d("TAGGGGGGGGREQUEST", responseEntity.getStatusCode().toString());
                for ( Message m : responseEntity.getBody()){
                 Log.d("TAGGGGGGGGREQUEST", m.getSenderName());
                }
             //   Log.d("TAGGGGGGGGREQUEST", responseEntity.getBody();
               // JSONObject userJson = new JSONObject(responseEntity.getBody().toString());
             //   JSONObject userJson = new JSONObject(responseEntity);
             //   Log.d("TAGGGGGGGGREQUEST", userJson.toString());

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