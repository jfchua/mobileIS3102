package kenneth.jf.siaapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.w3c.dom.Text;

import java.util.Collection;

import static kenneth.jf.siaapp.R.id.text;

/**
 * Created by User on 5/10/2016.
 */

public class thirdFrag extends Fragment implements BeaconConsumer {
    View myView;
    private BeaconManager mBeaconManager;
    protected static final String TAG = "RangingActivity";
    String textResult = "Scanning started";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.third_frag,container,false);
        mBeaconManager = BeaconManager.getInstanceForApplication(getActivity());
        // To detect proprietary beacons, you must add a line like below corresponding to your beacon
        // type.  Do a web search for "setBeaconLayout" to get the proper expression.
        // beaconManager.getBeaconParsers().add(new BeaconParser().
        //        setBeaconLayout("m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));


        TextView text = (TextView) myView.findViewById(R.id.thirdResult);
        text.setText("BYE BYE WORLD");


        return myView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mBeaconManager = BeaconManager.getInstanceForApplication(this.getApplicationContext());
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
        final TextView text = (TextView) myView.findViewById(R.id.thirdResult);
        text.setText("on Beacon Service Connect");
        mBeaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {

                try {
                    if (beacons.size() > 0) {
                        Log.i(TAG, "The first beacon I see is about " + beacons.iterator().next().getDistance() + " meters away.");
                        //textResult = "The first beacon I see is about "+beacons.iterator().next().getDistance()+" meters away.";
                        //text.setText("THE DISTANCE IS: " + beacons.iterator().next().getDistance());
                        setText1("My luggage is about " + beacons.iterator().next().getDistance() + " meters away.");
                    }

                    if (beacons.iterator().next().getDistance() < 1) {
                        setText("Very Near!");
                        new AlertDialog.Builder(getApplicationContext())
                                .setTitle("Your luggage is near!")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                    if (beacons.iterator().next().getDistance() > 1) {
                        setText("Some Distance Away!");
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
        text.setText(textResult);
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

    public void setText1(String msg){
        final String str = msg;
        getActivity().runOnUiThread(new Runnable(){
           @Override
            public void run(){
               TextView result = (TextView) myView.findViewById(R.id.thirdResult);
               result.setText(str);
           }
        });

    }
    public void setText(String msg){
        final String str = msg;
        getActivity().runOnUiThread(new Runnable(){
            @Override
            public void run(){
                TextView result = (TextView) myView.findViewById(R.id.alert);
                result.setText(str);
            }
        });

    }
}
