package kenneth.jf.siaapp;

import android.app.AlertDialog;
import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.ArrayList;
import java.util.Collection;



/**
 * Created by User on 5/10/2016.
 */
// for beacon connection using bluetooth Manager
public class secondFrag extends Fragment implements BeaconConsumer{
    private static final String TAG = "SECOND FRAGMENT FOR MONITORINH";

    /*Creating a beacon*/

    View myView;
    // ------------------------------------------------------------------------
    // members
    // ------------------------------------------------------------------------

    private static final String LOG_TAG = "MainActivity";
    private BluetoothManager btManager;
    private android.bluetooth.BluetoothAdapter btAdapter;
    private Handler scanHandler = new Handler();
    private int scan_interval_ms = 5000;
    private boolean isScanning = false;

    // ------------------------------------------------------------------------
    // default stuff...
    // ------------------------------------------------------------------------

    //Beacon Manager
     private BeaconManager mBeaconManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.second_frag,null,false);


        // init BLE --> need to add getActivity
        btManager = (BluetoothManager)getActivity().getSystemService(Context.BLUETOOTH_SERVICE);
        btAdapter = btManager.getAdapter();

        scanHandler.post(scanRunnable);
        return myView;
    }

    // ------------------------------------------------------------------------
    // public usage
    // ------------------------------------------------------------------------

    private Runnable scanRunnable = new Runnable()
    {
        @Override
        public void run() {

            if (isScanning)
            {
                if (btAdapter != null)
                {
                    btAdapter.stopLeScan(leScanCallback);
                }
            }
            else
            {
                if (btAdapter != null)
                {
                    btAdapter.startLeScan(leScanCallback);
                }
            }

            isScanning = !isScanning;

            scanHandler.postDelayed(this, scan_interval_ms);
        }
    };

    // ------------------------------------------------------------------------
    // Inner classes
    // ------------------------------------------------------------------------

    private android.bluetooth.BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback()
    {
        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord)
        {
            int startByte = 2;
            boolean patternFound = false;
            while (startByte <= 5)
            {
                if (    ((int) scanRecord[startByte + 2] & 0xff) == 0x02 && //Identifies an iBeacon
                        ((int) scanRecord[startByte + 3] & 0xff) == 0x15)
                { //Identifies correct data length
                    patternFound = true;
                    break;
                }
                startByte++;
            }

            if (patternFound)
            {
                //Convert to hex String
                byte[] uuidBytes = new byte[16];
                System.arraycopy(scanRecord, startByte + 4, uuidBytes, 0, 16);
                String hexString = bytesToHex(uuidBytes);

                //UUID detection
                String uuid =  hexString.substring(0,8) + "-" +
                        hexString.substring(8,12) + "-" +
                        hexString.substring(12,16) + "-" +
                        hexString.substring(16,20) + "-" +
                        hexString.substring(20,32);

                // major
                final int major = (scanRecord[startByte + 20] & 0xff) * 0x100 + (scanRecord[startByte + 21] & 0xff);

                // minor
                final int minor = (scanRecord[startByte + 22] & 0xff) * 0x100 + (scanRecord[startByte + 23] & 0xff);



                //Record in front
                Log.i(LOG_TAG,"UUID: " +uuid + "\\nmajor: " +major +"\\nminor" +minor);

                if(String.valueOf(minor).equals("36128")){
                      setText1("Welcome to IFMS");
                      Toast.makeText(getActivity(), "WELCOME TO IFMS!!!", Toast.LENGTH_LONG).show();
                }
                else if(String.valueOf(minor).equals("1219")){
                    setText1("There is a whopping 50% off for the second top purchased at H&M!");
                    Toast.makeText(getActivity(), "There is a whopping 50% off for the second top purchased at H&M!", Toast.LENGTH_LONG).show();
                }
                else{
                    setText1("Hurry Up! The 1 for 1 drink at Starbucks ends at 4pm!");
                    Toast.makeText(getActivity(), "Hurry Up! The 1 for 1 drink at Starbucks ends at 4pm!", Toast.LENGTH_LONG).show();
                }

                ArrayList<String> list = new ArrayList<>();
          /*      if(String.valueOf(minor).equals("36128")){
                    list.add(count+". "+ "UUID: " +uuid + "\\nmajor: " +major +"\\nminor" +minor);
                }
                if(String.valueOf(minor).equals("1219")){
                    setText1("There is a whopping 50% off for the second top purchased at H&M!");
                    Toast.makeText(getActivity(), "There is a whopping 50% off for the second top purchased at H&M!", Toast.LENGTH_LONG).show();
                }
                if(String.valueOf(minor).equals("13419")){
                    setText1("Hurry Up! The 1 for 1 drink at Starbucks ends at 4pm!");
                    Toast.makeText(getActivity(), "Hurry Up! The 1 for 1 drink at Starbucks ends at 4pm!", Toast.LENGTH_LONG).show();
                }*/
                setText("Nearest Beacon Found: " +uuid + "\\nmajor: " +major +"\\nminor" +minor);
                //Toast.makeText(getActivity(), "Your Beacon Is Found!", Toast.LENGTH_LONG).show();

            }

        }
    };

    /**
     * bytesToHex method
     */
    static final char[] hexArray = "0123456789ABCDEF".toCharArray();
    private static String bytesToHex(byte[] bytes)
    {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ )
        {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
    public void setText(String text){
        TextView textView = (TextView) myView.findViewById(R.id.beaconID);
        textView.setText(text);
    }
    public void setText1(String text){
        TextView textView = (TextView) myView.findViewById(R.id.message);
        textView.setText(text);
    }




//redundant from here onwards


    /*public void onDestroy() {
        super.onDestroy();

    }
    @Override
    public void onBeaconServiceConnect() {
        mBeaconManager.setMonitorNotifier(new MonitorNotifier() {
            @Override
            public void didEnterRegion(Region region) {
                Log.i(TAG, "I just saw an beacon for the first time!");
            }

            @Override
            public void didExitRegion(Region region) {
                Log.i(TAG, "I no longer see an beacon");
            }

            @Override
            public void didDetermineStateForRegion(int state, Region region) {
                Log.i(TAG, "I have just switched from seeing/not seeing beacons: "+state);
            }
        });

        try {
            mBeaconManager.startMonitoringBeaconsInRegion(new Region("myMonitoringUniqueId", null, null, null));
        } catch (RemoteException e) {    }
    }

    @Override
    public Context getApplicationContext() {
        return null;
    }

    @Override
    public void unbindService(ServiceConnection serviceConnection) {

    }

    @Override
    public boolean bindService(Intent intent, ServiceConnection serviceConnection, int i) {
        return false;
    }*/



    String textResult = "Scanning started";
    @Override
    public void onResume() {
        super.onResume();
        mBeaconManager = BeaconManager.getInstanceForApplication(this.getActivity());
        // Detect the main Eddystone-UID frame:
        mBeaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:0-3=4c000215,i:4-19,i:20-21,i:22-23,p:24-24"));
        mBeaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("x,s:0-1=feaa,m:2-2=20,d:3-3,d:4-5,d:6-7,d:8-11,d:12-15"));
        mBeaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("s:0-1=feaa,m:2-2=00,p:3-3:-41,i:4-13,i:14-19"));
        mBeaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("s:0-1=feaa,m:2-2=10,p:3-3:-41,i:4-20v"));
        mBeaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("s:0-1=fed8,m:2-2=00,p:3-3:-41,i:4-21v"));
        mBeaconManager.bind(this);
    }

    private static final String BEACON_UUID = "5C3F2F21-20D1-11E6";
    private static final int BEACON_MAJOR = 1000;


    @Override
    public void onBeaconServiceConnect() {
        final TextView text = (TextView) myView.findViewById(R.id.messageRange);
        text.setText("on Beacon Service Connect");
        mBeaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {

                try {
                    if (beacons.size() > 0) {
                        Log.i("ranging", "The first beacon I see is about " + beacons.iterator().next().getDistance() + " meters away.");
                        //textResult = "The first beacon I see is about "+beacons.iterator().next().getDistance()+" meters away.";
                        //text.setText("THE DISTANCE IS: " + beacons.iterator().next().getDistance());
                        setText3("The message came from about " + beacons.iterator().next().getDistance() + " meters away.");
                    }

                    if (beacons.iterator().next().getDistance() < 1) {
                        setText("Very Near!");
                        new AlertDialog.Builder(getApplicationContext())
                                .setTitle("Your luggage is near!")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                    if (beacons.iterator().next().getDistance() > 1) {
                        setText2("Some Distance Away!");
                    }
                /*for (Beacon beacon: beacons) {
                    Log.i(TAG, "This beacon has identifiers:"+beacon.getId1()+", "+beacon.getId2()+", "+beacon.getId3());
                    TextView text2 = (TextView) myView.findViewById(R.id.thirdResult);
                    text2.setText("This beacon has identifiers:"+beacon.getId1()+", "+beacon.getId2()+", "+beacon.getId3());
                }*/
                }
                catch(Exception e){
                    // Log.d("TAG",e.getMessage());
                }

            }


        });

        try {
            mBeaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {    }
        setText2(textResult);
    }


    @Override
    public Context getApplicationContext() {
        return getActivity().getApplicationContext();
    }

    @Override
    public void unbindService(ServiceConnection serviceConnection) {
        getActivity().unbindService(serviceConnection);
    }

    @Override
    public boolean bindService(Intent intent, ServiceConnection serviceConnection, int i) {
        return getActivity().bindService(intent, serviceConnection, i);
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onDestroy() {
        mBeaconManager.unbind(this);
        super.onDestroy();
    }

    public void setText3(String msg){
        final String str = msg;
        getActivity().runOnUiThread(new Runnable(){
            @Override
            public void run(){
                TextView result = (TextView) myView.findViewById(R.id.messageRange);
                result.setText(str);
            }
        });

    }
    public void setText2(String msg){
        final String str = msg;
        getActivity().runOnUiThread(new Runnable(){
            @Override
            public void run(){
                TextView result = (TextView) myView.findViewById(R.id.messageInfo);
                result.setText(str);
            }
        });

    }
}
