package com.testmaps.testmaps;

import android.support.v4.app.ListFragment;
import android.view.Gravity;
import android.widget.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Past extends ListFragment implements OnItemClickListener {
    public Context context;
    //public Past(Context viewpageradaptercontext)
    //{
        //this.context = viewpageradaptercontext;
    //}
    String [] pastiee = new String[]{"past 1", "past 2", "past 3", "past 4"};

    private ListView mListView;
    public ListViewContactAdapter adapter;
    public ArrayList<ListViewContactItem> listContact;

    public Past()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.activity_past, container, false);
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

        String [] past = new String[]{"past 1", "past 2", "past 3", "past 4", "past 5", "past 6", "past 7", "past 8", "past 9", "past 10"};
        /*SharedPreferences sharedPref = this.getActivity().getSharedPreferences("usernamepref", Context.MODE_PRIVATE);
        int endloop = sharedPref.getInt("noofpastevents", 0);
        String[] eventsArray = new String[endloop];
        for(int i = 0; i<endloop; i++)
        {
            eventsArray[i] = sharedPref.getString("pasttitle"+i, "not available");

        }*/
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_dropdown_item, eventsArray);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_list_item_2, android.R.id.text1, eventsArray);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_list_item_2, android.R.id.text2, past);

        String[] from = new String[]{"atitle", "aswho"};


        //getListView().setAdapter(adapter);
        //getListView().setAdapter(adapter2);
        //ListView lv = getListView();
        //SimpleAdapter sa = new SimpleAdapter(getActivity(), listt, R.layout.activity_past, from, to);
        //lv.setAdapter(adapter);


        TextView text2 = (TextView) getActivity().findViewById(android.R.id.text2);
        //setListAdapter(sa, adapter2);
        //text2.setText("testing 'as who'");
        getListView().setOnItemClickListener(this);
        //setListAdapter(adapter2);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        // TODO Auto-generated method stub
        SharedPreferences sharedPref = getActivity().getSharedPreferences("usernamepref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        int positionplusone = position+1;
        String eventIdForViewIndiv = sharedPref.getString("pastid"+position, "pastidzer0");
        editor.putString("eventIdForViewIndiv", eventIdForViewIndiv);
        editor.putInt("pasteventkeyplusone", positionplusone);
        editor.commit();
        //Toast.makeText(this, "", Toast.LENGTH_LONG).show();
        String vieweventss = "";
        new FetchAttendantsAsyncTask(getActivity().getBaseContext()).execute(vieweventss);
        //new ViewIndividualAsync(context).execute();

    }


    private ArrayList<ListViewContactItem> GetlistContact(){


        ListViewContactItem contact = new ListViewContactItem();

        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("usernamepref", Context.MODE_PRIVATE);
        int endloop = sharedPref.getInt("noofpastevents", 0);
        int endloopminusone = endloop - 1;
        String[] eventsArray = new String[endloop];
        String[] asWhoArray = new String[endloop];
        ArrayList<ListViewContactItem> contactlist = new ArrayList<ListViewContactItem>(Collections.nCopies(endloop, contact));
        String loggedinuserr = sharedPref.getString("prefkeyforusername", "");


        for(int i = 0; i<=endloopminusone; i++)
        {
            eventsArray[i] = sharedPref.getString("pasttitle"+i, "not available");
            asWhoArray[i] = sharedPref.getString("pastaswho"+i, "");
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


    //String pasteventsasarray = sharedPref.getString("arraytopasttabkey", "");
    //String[] pasteventsassplitstring = pasteventsasarray.split("-");



    //String[] eventsArray = jsonArray0.toString().replace("[", "").replace("]", "").replace("\"", "").split(",");


    //ListView a = new ListView(context, null, android.R.id.list);




    //a.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);




    //return super.onCreateView(inflater, container, savedInstanceState);
    //return a;


}
/*public class Past extends ListFragment {

    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_past, container, false);
        ListView bc = v.findViewById(R.id.listviewpast);
        return v;
    }

    public static Past newInstance(String text) {

        Past f = new Past();
        Bundle b = new Bundle();


        f.setArguments(b);

        return f;
    }
}*/
	/*@Override
	public void onStart()
	{*/
		/*super.onStart();
		getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		getListView().setOnItemClickListener(new OnItemClickListener()
        {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
			{
				// TODO Auto-generated method stub
				SharedPreferences sharedPref = getActivity().getSharedPreferences("usernamepref", Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = sharedPref.edit();
				editor.putInt("pasteventkey", position);
				editor.commit();
				new ViewIndividualAsync(context).execute();

			}
		});*/
//}






