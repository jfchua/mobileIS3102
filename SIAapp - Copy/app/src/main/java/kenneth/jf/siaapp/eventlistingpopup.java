package kenneth.jf.siaapp;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

/**
 * Created by User on 16/10/2016.
 */

public class eventlistingpopup extends Fragment{
    View view;
    ListView list;

    List<Event> detailsList = new ArrayList<Event>();
    ArrayList<String> listtest=new ArrayList <String>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.eventpopup, container,false);
        initializeCart();
        detailsList = SetAdapterList();

       /*ArrayAdapter adapter=new ArrayAdapter(this.getContext(),detailsList);

       list.setAdapter(adapter);*/
        return view;
    }
    private void initializeCart()
    {
        ListView cart =(ListView) view.findViewById(R.id.ListViewCatalog);
    }
    private List<Event> SetAdapterList()
    {
        // TODO Auto-generated method stub

        Event details;

        List<Event> detailsList = new ArrayList<Event>();

        try{
            Intent newintent = getActivity().getIntent();
            final String itemTitle = newintent.getStringExtra("title");
            final String itemDesc = newintent.getStringExtra("desc");

            final double itemPrice = newintent.getDoubleExtra("price", 0.0);
            String totprice=String.valueOf(itemPrice);
//      ArrayList <String> itemnames=new ArrayList<String>();
            listtest.add(itemTitle);
            Log.e("listtest",listtest+"");
            final int imagehandler=newintent.getIntExtra("image", 0);
            Log.e("image handler",imagehandler+"");
            for(int i = 0; i< listtest.size(); i++)
            {
                details = new Event(itemTitle,itemDesc,itemPrice);
                detailsList.add(details);
            }


        }
        catch(Exception e){
            e.printStackTrace();
        }
        return detailsList;
    }
}
