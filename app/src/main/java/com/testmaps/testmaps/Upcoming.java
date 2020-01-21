package com.testmaps.testmaps;

import android.content.Intent;
import android.support.v4.app.ListFragment;
import android.widget.AdapterView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;
import java.util.Collections;

/*public class Upcoming extends Fragment {
    String [] upcoming = new String[]{"upcoming 1", "upcoming 2", "upcoming 3"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("usernamepref", Context.MODE_PRIVATE);
        //String jsonEvents = sharedPref.getString("prefkey2forviewevents", "");
        //String pasteventsasarray = sharedPref.getString("arraytopasttabkey", "");
        //String[] pasteventsassplitstring = pasteventsasarray.split("-");



        //JSONArray jsonArray0 = new JSONArray(jsonEvents);
        //String[] eventsArray = jsonArray0.toString().replace("[", "").replace("]", "").replace("\"", "").split(",");

        int endloop = sharedPref.getInt("noofupcomingevents", 0);
        String[] eventsArray = new String[endloop];
        for(int i = 0; i<endloop; i++)
        {
            eventsArray[i] = sharedPref.getString("upcoming"+i, "not available");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_dropdown_item, eventsArray);
        //setListAdapter(adapter);
        Toast.makeText(getContext(), "3", Toast.LENGTH_LONG).show();
        return super.onCreateView(inflater, container, savedInstanceState);


    }
    public static Upcoming newInstance(String text)
    {
        Upcoming f = new Upcoming();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }
    @Override
    public void onStart()
    {
        super.onStart();
        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }


}*/
public class Upcoming extends ListFragment implements OnItemClickListener {
    public Context context;

    private ListView mListView;
    public ListViewContactAdapter adapter;
    public ArrayList<ListViewContactItem> listContact;

    public Upcoming()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.activity_upcoming, container, false);
        mListView = (ListView) view.findViewById(android.R.id.list);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        listContact = GetlistContact();
        adapter=new ListViewContactAdapter(getActivity(), listContact);
        mListView.setAdapter(adapter);

        String [] upcoming = new String[]{"upcoming 1", "upcoming 2", "upcoming 3"};
        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("usernamepref", Context.MODE_PRIVATE);
        int endloop = sharedPref.getInt("noofupcomingevents", 0);
        String[] eventsArray = new String[endloop];
        for(int i = 0; i<endloop; i++)
        {
            eventsArray[i] = sharedPref.getString("upcomingtitle"+i, "not available");
        }
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_dropdown_item, eventsArray);
        //setListAdapter(adapter);
        getListView().setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        // TODO Auto-generated method stub
        SharedPreferences sharedPref = getActivity().getSharedPreferences("usernamepref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        int positionplusone = position+1;
        String eventIdForViewIndiv = sharedPref.getString("upcomingid"+position, "upcomingidzer0");
        editor.putString("eventIdForViewIndiv", eventIdForViewIndiv);
        editor.putInt("upcomingeventkeyplusone", positionplusone);
        editor.commit();
        String vieweventss = "";
        new FetchAttendantsAsyncTask(getActivity().getBaseContext()).execute(vieweventss);

    }

    private ArrayList<ListViewContactItem> GetlistContact(){


        ListViewContactItem contact = new ListViewContactItem();

        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("usernamepref", Context.MODE_PRIVATE);
        int endloop = sharedPref.getInt("noofupcomingevents", 1);
        int endloopminusone = endloop - 1;
        String[] eventsArray = new String[endloop];
        String[] asWhoArray = new String[endloop];
        ArrayList<ListViewContactItem> contactlist = new ArrayList<ListViewContactItem>(Collections.nCopies(endloop, contact));
        String loggedinuserr = sharedPref.getString("prefkeyforusername", "");


        for(int i = 0; i<=endloopminusone; i++)
        {
            eventsArray[i] = sharedPref.getString("upcomingtitle"+i, "(No Upcoming Events)");
            asWhoArray[i] = sharedPref.getString("upcomingaswho"+i, "");
            contact = new ListViewContactItem();
            contact.setArea(eventsArray[i]);
            contact.setStreetName(asWhoArray[i]);
            contactlist.set(i, contact);
            if(i==endloopminusone){
                return contactlist;
            }
        }

        contact.setPickUpPoint("xyz");
        //contact.setArea("xyz City");
        contact.setLandmark("near xyz");
        //contact.setStreetName("xyz Road");
        return null;
    }

}